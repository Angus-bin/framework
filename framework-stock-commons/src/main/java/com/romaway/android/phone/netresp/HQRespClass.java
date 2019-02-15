package com.romaway.android.phone.netresp;

import com.romaway.android.phone.view.Theme;
import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.ProtocolConstant;
import com.romaway.common.protocol.hq.HQZXProtocol;
import com.romaway.common.protocol.util.KFloat;
import com.romaway.common.protocol.util.KFloatUtils;
import com.romaway.commons.lang.ArrayUtils;

public class HQRespClass {

	public static ValueClass getValueClass(AProtocol ptl, HQEnumValue[] titles){
		if(titles == null)
			return null;
		
		//必备的两个字段：市场ID，股票类型
		//HQEnumValue[] necessary = {HQEnumValue.STOCK_MARKET_ID, HQEnumValue.STOCK_CODE_TYPE};
		//HQEnumValue[] allEnum = (HQEnumValue[]) ArrayUtils.mergerArray(titles, necessary);
		
		ValueClass mValueClass = new ValueClass(titles/*allEnum*/);
		
		//自选行情协议 功能号:3
		if(ptl instanceof  HQZXProtocol){
			
			HQZXProtocol mHQZXProtocol = (HQZXProtocol)ptl;
		  
			mValueClass.mDataValue = new String[mHQZXProtocol.resp_wCount][titles.length];
			mValueClass.mDataColor = new int[mHQZXProtocol.resp_wCount][titles.length];
			
			for (int index = 0; index < mHQZXProtocol.resp_wCount; index++){
				
				int Color = Theme.hqDPZColors[1];
				int fontColor = Theme.hqDPZColors[KFloatUtils.compare(new KFloat(
						mHQZXProtocol.resp_nZjcj_s[index]), new KFloat(
								mHQZXProtocol.resp_nZrsp_s[index])) + 1];
				
				int tpbz = mHQZXProtocol.resp_bSuspended_s[index];
				short wType = mHQZXProtocol.resp_wType_s[index]; // 获取商品类型 判断
				
				for(int j = 0; j < titles.length;j++){
					
			        switch(titles[j]){
				      	//市场ID
			        	case STOCK_MARKET_ID:
			        		mValueClass.mDataValue[index][j] = String.valueOf(mHQZXProtocol.resp_wMarketID_s[index]);
			        		break;
			        	//商品类型
			        	case STOCK_CODE_TYPE:
			        		mValueClass.mDataValue[index][j] = String.valueOf(mHQZXProtocol.resp_wType_s[index]);
		        		break;
		        		//停牌标志
			        	case STOCK_BPUSPENDED_S:
			        		mValueClass.mDataValue[index][j] = String.valueOf(mHQZXProtocol.resp_bSuspended_s[index]);
			        	break;
			        	//特殊股票标志
			        	case STOCK_PSZMARK_S:
			        		mValueClass.mDataValue[index][j] = String.valueOf(mHQZXProtocol.resp_pszMark_s[index]);
			        		break;
			        		
			        	//股票名称
			        	case STOCK_NAME:
			        		mValueClass.mDataValue[index][j] = mHQZXProtocol.resp_pszName_s[index];
			        		mValueClass.mDataColor[index][j] = fontColor;
			        	break;
			        	//股票代码
			        	case STOCK_CODE:
			        		mValueClass.mDataValue[index][j] = mHQZXProtocol.resp_pszCode_s[index];
			        		mValueClass.mDataColor[index][j] = fontColor;
			        	break;
			        	//最新价
			        	case STOCK_XIANJIA:
			        		mValueClass.mDataValue[index][j] =  getString(new KFloat(
			        				mHQZXProtocol.resp_nZjcj_s[index]).toString());
			        		mValueClass.mDataColor[index][j] = tpbz == 0 ? fontColor : Color;
			        		break;
			        		//涨跌幅
			        	case STOCK_ZDF:
			        		mValueClass.mDataValue[index][j] = tpbz == 0 ? (new KFloat(mHQZXProtocol.resp_nZdf_s[index])
			                	.toString().equals("--") ? "0.00"
			                			: (new KFloat(mHQZXProtocol.resp_nZdf_s[index]))
			                			.toString()+"%") : "--";
			        		mValueClass.mDataColor[index][j] = tpbz == 0 ? fontColor : Color;
			        		break;
			        		//涨跌
			        	case STOCK_ZD:
			        		mValueClass.mDataValue[index][j] = tpbz == 0 ? (new KFloat(mHQZXProtocol.resp_nZd_s[index])
		                	.toString().equals("--") ? "0.00"
		                			: (new KFloat(mHQZXProtocol.resp_nZd_s[index]))
		                			.toString()+"%") : "--";
			        		mValueClass.mDataColor[index][j] = tpbz == 0 ? fontColor : Color;
			        		break;
			        		//昨收
			        	case STOCK_ZSJ:
			        		mValueClass.mDataValue[index][j] = new KFloat(
			        				mHQZXProtocol.resp_nZrsp_s[index]).toString();
			        		mValueClass.mDataColor[index][j] = Color;
			        		break;
			        		//总量
			        	case STOCK_ZL:
			        		mValueClass.mDataValue[index][j] = new KFloat(
			        				mHQZXProtocol.resp_nCjss_s[index]).toString();
			        		mValueClass.mDataColor[index][j] = Color;
			        		break;
			        		//成交金额
			        	case STOCK_CJJE:
			        		mValueClass.mDataValue[index][j] = new KFloat(
			        				mHQZXProtocol.resp_nCjje_s[index]).toString();
			        		mValueClass.mDataColor[index][j] = Color;
			        		break;
			        		//今开
			        	case STOCK_JKJ:
			        		mValueClass.mDataValue[index][j] = getString(new KFloat(mHQZXProtocol.resp_nJrkp_s[index]).toString());
			        		mValueClass.mDataColor[index][j] = tpbz == 0 ? Theme.hqDPZColors[KFloatUtils.compare(
			                        new KFloat(
			                        		mHQZXProtocol.resp_nJrkp_s[index]),
			                        new KFloat(
			                        		mHQZXProtocol.resp_nZrsp_s[index])) + 1]
			                        				: Color;
			        		break;
			        		//最高
			        	case STOCK_ZGJ:
			        		mValueClass.mDataValue[index][j] = getString(new KFloat(mHQZXProtocol.resp_nZgcj_s[index]).toString());
			        		mValueClass.mDataColor[index][j] =  tpbz == 0 ? Theme.hqDPZColors[KFloatUtils
			                .compare(
			                        new KFloat(
			                        		mHQZXProtocol.resp_nZgcj_s[index]),
			                        new KFloat(
			                        		mHQZXProtocol.resp_nZrsp_s[index])) + 1]
			                : Color;
			        		break;
			        		//最低
			        	case STOCK_ZDJ:
			        		mValueClass.mDataValue[index][j] = getString(new KFloat(mHQZXProtocol.resp_nZdcj_s[index]).toString());
			        		mValueClass.mDataColor[index][j] =  tpbz == 0 ? Theme.hqDPZColors[KFloatUtils
			                .compare(
			                        new KFloat(
			                        		mHQZXProtocol.resp_nZdcj_s[index]),
			                        new KFloat(
			                        		mHQZXProtocol.resp_nZrsp_s[index])) + 1]
			                : Color;
			        		break;
			        		//市盈率
			        	case STOCK_SYL:
			        		mValueClass.mDataValue[index][j] = getString(new KFloat(
			        				mHQZXProtocol.resp_nSyl_s[index]).toString());
			        		mValueClass.mDataColor[index][j] =  Color;
			        		break;
			        		//换手率
			        	case STOCK_HSL:
			        		String hsy = getString(new KFloat(mHQZXProtocol.resp_nHsl_s[index])
			                .toString(), wType);
			        		mValueClass.mDataValue[index][j] = hsy + (hsy.equals("---")? "" : "%");
			        		mValueClass.mDataColor[index][j] =  Color;
			        		break;
			        	default:
			        		break;
			        }
		        }
			 }
		}
		
		return mValueClass;
	}

	
	private static String getString(String str)
	{
		String tempString = "";
		if (str.equals("0") || str.equals("0.00"))
		{
			tempString = "---";
		} else
		{
			tempString = str;
		}
		return tempString;
	}
	
	private static String getString(String str, short type)
	{
		String tempString = "";
		if ((type == ProtocolConstant.STOCKTYPES_ST_FUND
		        || type == ProtocolConstant.STOCKTYPES_ST_INDEX || type == ProtocolConstant.STOCKTYPES_ST_BOND)
		        && (str.equals("0") || str.equals("0.00")))
		{
			tempString = "---";
		} else
		{
			tempString = str;
		}
		return tempString;
	}
}
