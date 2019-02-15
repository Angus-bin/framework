package com.romaway.common.protocol.hq;

import android.support.annotation.Keep;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.ProtocolConstant;
import com.romaway.commons.lang.StringUtils;

public class HQZXGTBSelectProtocol extends AProtocol{
	/**
	 * 自选组名
	 */
	public String req_group;
	
	//返回数据
	/**
	 * 查询结果
	 */
	public String resp_favors;
	
	public HQZXGTBSelectProtocol(String flag) {
		super(flag, false);
		isJson = true;
		subFunUrl = "/api/system/favor/select/";
	}

	
	/**
     * 
     * @param hqzxgtb
     * @return  index 0：股票代码；index 1：股票市场ID
     */
    @Keep
    public static String[] parseZXGTBFavorsToArray(HQZXGTBSelectProtocol hqzxgtb){
        
        if(hqzxgtb.resp_favors!=null&&!StringUtils.isEmpty(hqzxgtb.resp_favors)){
            
            String[] zxgArray = new String[2];
                    
            //请求到有数据，就更新数据库
            String favors = hqzxgtb.resp_favors;
            String[] favors_array = favors.split(",");
            String codes = "";
            String marketIds = "";
            String[] codes_marketId;
            for(int i = 0;i<favors_array.length;i++){
                codes_marketId = favors_array[i].split(":");
                if(codes_marketId != null && !codes_marketId.equals("") && codes_marketId.length >= 2){  
                    if(codes.length()==0){
                        codes = codes_marketId[1];//stock_code
                        marketIds = codes_marketId[0];//market_id
                    }else{
                        codes += "," + codes_marketId[1];//stock_code
                        marketIds += "," + codes_marketId[0];//market_id
                    }
                }
            }
            
            zxgArray[0] = codes;
            zxgArray[1] = marketIds;
            
            return zxgArray;
        }
        
        return null;
    }
}
