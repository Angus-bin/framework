package com.romaway.common.protocol.hq;

import com.google.protobuf.InvalidProtocolBufferException;
import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;

import java.util.List;

import protobuf.Protobuf;
import protobuf.Protobuf.codeList_add_repeated;
import protobuf.Protobuf.codeList_del_repeated;
import protobuf.Protobuf.codeList_req;

/**
 * 新键盘精灵
 * 
 * @author xueyan
 * 
 */
public class HQNewCodeListProtocolCoder extends
		AProtocolCoder<HQNewCodeListProtocol> {

	@Override
	protected byte[] encode(HQNewCodeListProtocol hqncl) {
		//通用排序控制参数
		codeList_req.Builder codeList_reqbuild=codeList_req.newBuilder();
		codeList_reqbuild.setVersion(hqncl.req_nDate);
		codeList_reqbuild.setUuidVersion(hqncl.req_sUuid);
		return codeList_reqbuild.build().toByteArray();
	}

	@Override
	protected void decode(HQNewCodeListProtocol hqncl)
			throws ProtocolParserException {
		if (hqncl.getReceiveData() != null) {
         	Protobuf.codeList_rep rep;
			try {
				rep = Protobuf.codeList_rep.parseFrom(hqncl.getReceiveData());
				List<codeList_add_repeated> addArray=rep.getAddArrList();
				List<codeList_del_repeated> delArray=rep.getDelArrList();
				int addSize=addArray.size();
				int delSize=delArray.size();
				hqncl.resp_nDate = rep.getVersion();
				hqncl.resp_sUuid = rep.getUuidVersion();
				hqncl.resp_nAddCodeNum = addSize;
				hqncl.resp_nDelCodeNum = delSize;
				if(addSize>0){
					hqncl.resp_wMarketID_s = new int[addSize];
					hqncl.resp_wCodeType_s = new int[addSize];
					hqncl.resp_pszAddCode_s = new String[addSize];
					hqncl.resp_pszPYCode_s = new String[addSize];
					hqncl.resp_pszMark_s = new String[addSize];
					hqncl.resp_pszName_s = new String[addSize];
					for(int i=0;i<addArray.size();i++){
						codeList_add_repeated mcodeList_add_repeated=addArray.get(i);
						hqncl.resp_wMarketID_s[i]= mcodeList_add_repeated.getMarketId();
						hqncl.resp_wCodeType_s[i]= mcodeList_add_repeated.getCodeType();
						hqncl.resp_pszAddCode_s[i]=mcodeList_add_repeated.getCode();
						hqncl.resp_pszPYCode_s[i]=mcodeList_add_repeated.getPinyin();
						hqncl.resp_pszMark_s[i]=mcodeList_add_repeated.getPszMark();
						hqncl.resp_pszName_s[i]=mcodeList_add_repeated.getName();
					}
				}
				if(delSize>0){
					hqncl.resp_pszDelCode_s = new String[delSize];
					hqncl.resp_del_wMarketID = new int[delSize];
					for(int j=0;j<delArray.size();j++){
						codeList_del_repeated mcodeList_del_repeated=delArray.get(j);
						hqncl.resp_pszDelCode_s[j]=mcodeList_del_repeated.getCode();
						hqncl.resp_del_wMarketID[j]= mcodeList_del_repeated.getMarketId();
					}
					
				}
				
			} catch (InvalidProtocolBufferException e) {
				e.printStackTrace();
			}         	
         }
	}

}
