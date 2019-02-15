package com.romaway.common.protocol.hq;

import com.google.protobuf.InvalidProtocolBufferException;
import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;

import java.util.List;

import protobuf.Protobuf;
import protobuf.Protobuf.blockRank_rep;
import protobuf.Protobuf.blockRank_repeated;
import protobuf.Protobuf.blockRank_req;
import protobuf.Protobuf.multi_blockRank_req;
import protobuf.Protobuf.rank_option;

/**
 * 板块行情分析协议
 * 
 * @author xueyan
 * 
 */
public class HQBKProtocolCoder extends AProtocolCoder<HQBKProtocol> {

	@Override
	protected byte[] encode(HQBKProtocol bkhqProtocol) {
		//通用排序控制参数
    	rank_option.Builder rank_optionbuild=rank_option.newBuilder();
    	rank_optionbuild.setWType(bkhqProtocol.req_wType);
    	rank_optionbuild.setBSort(bkhqProtocol.req_bSort);
    	if(bkhqProtocol.req_bDirect==0){
    		rank_optionbuild.setBDirect(false);
    	}else{
    		rank_optionbuild.setBDirect(true);
    	}  	
    	rank_optionbuild.setWFrom(bkhqProtocol.req_wFrom);
    	rank_optionbuild.setWCount(bkhqProtocol.req_wCount);
    	
    	rank_optionbuild.setFieldsBitMap(0L);
    	
    	//板块请求
    	
    	blockRank_req.Builder blockRank_reqbuild=blockRank_req.newBuilder();
    	blockRank_reqbuild.setOptions(rank_optionbuild.build());
    	
    	multi_blockRank_req.Builder multi_blockRank_reqbuild=multi_blockRank_req.newBuilder();
    	multi_blockRank_reqbuild.addReqs(blockRank_reqbuild.build());
    	multi_blockRank_req mmulti_blockRank_req=multi_blockRank_reqbuild.build();
		return mmulti_blockRank_req.toByteArray();
	}

	@Override
	protected void decode(HQBKProtocol hqbk) throws ProtocolParserException {
		 if (hqbk.getReceiveData() != null) {
         	Protobuf.multi_blockRank_rep rep;
			try {
				rep = Protobuf.multi_blockRank_rep.parseFrom(hqbk.getReceiveData());
				for(int i=0;i<rep.getRepsCount();i++){
	         		blockRank_rep mblockRank_rep=rep.getReps(i);
	         		rank_option rank_option=mblockRank_rep.getOptions();
	         		hqbk.resp_wFrom=rank_option.getWFrom();
	         		List<blockRank_repeated> array=mblockRank_rep.getDataArrList();
	         		hqbk.resp_wCount=rank_option.getWCount();
	         		hqbk.resp_wTotalCount=rank_option.getWTotalCount();
	         		if(hqbk.resp_wCount>0){	    
	         			hqbk.resp_wMarketID_s = new int[hqbk.resp_wCount];
	         			hqbk.resp_wType_s = new int[hqbk.resp_wCount];
	         			hqbk.resp_nBKzdf_s = new int[hqbk.resp_wCount];
	         			hqbk.resp_pszBKCode_s = new String[hqbk.resp_wCount];
	         			hqbk.resp_pszBKName_s = new String[hqbk.resp_wCount];

	         			hqbk.resp_nLzStockCode_s = new String[hqbk.resp_wCount];
	         			hqbk.resp_nLZStockName_s = new String[hqbk.resp_wCount];
	         			hqbk.resp_nFirstZdf_s = new int[hqbk.resp_wCount];
	         			hqbk.resp_nFirstPrice_s = new int[hqbk.resp_wCount];
	         			for(int j=0;j<array.size();j++){
		         			blockRank_repeated blockRank_repeatedbulid=array.get(j);
		         			hqbk.resp_wMarketID_s[j]=hqbk.req_wMarketID;
		         			hqbk.resp_wType_s[j]=hqbk.req_wType;
		         			hqbk.resp_nBKzdf_s[j]=blockRank_repeatedbulid.getFZdf();
		         			hqbk.resp_pszBKCode_s[j]=blockRank_repeatedbulid.getSCode();
		         			hqbk.resp_pszBKName_s[j]=blockRank_repeatedbulid.getPszBkName();
		         			
		         			hqbk.resp_nLzStockCode_s[j]=blockRank_repeatedbulid.getSStockCode();
		         			hqbk.resp_nLZStockName_s[j]=blockRank_repeatedbulid.getPszStockName();
		         			hqbk.resp_nFirstZdf_s[j]=blockRank_repeatedbulid.getFirstZdf();
		         			hqbk.resp_nFirstPrice_s[j]=blockRank_repeatedbulid.getFirstPrice();
		         		}
	         		}
	         		
	         	}
			} catch (InvalidProtocolBufferException e) {
				e.printStackTrace();
			}         	
         }

	}
}
