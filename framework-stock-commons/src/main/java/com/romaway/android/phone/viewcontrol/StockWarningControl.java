package com.romaway.android.phone.viewcontrol;

import com.romaway.common.protocol.xt.XTYJDZResourceKey;
import com.romaway.common.protocol.xt.YJDZCXProtocol;
import com.romaway.common.protocol.yj.YuJingCXProtocol;
import com.romaway.commons.lang.StringUtils;

/**
 * 股票预警数据处理
 * 
 * @author qinyn
 * 
 */
public class StockWarningControl
{
	/**
	 * dzcx.resp_sParam2_s 股票价格或涨跌 dzcx.resp_sParam3_s 1上涨，2下跌
	 * dzcx.resp_wChannel_s 1数据 ，2短信
	 * 
	 * @param msg
	 * @param channel
	 * @param ptl
	 */
	public static void dycxData(String[] msg, String[] channel,
	        String[] stockName, YuJingCXProtocol ptl)
	{
		for (int i = 0; i < ptl.resp_count; i++)
		{
			StringBuffer sb = new StringBuffer();
			if (ptl.resp_up[i].equals("1"))
			{// ************* 上涨***********************************
				if (ptl.resp_serviceId[i].equals(XTYJDZResourceKey.ServiceId_STOCK_PRICE))
				{// 股价
					sb.append("股价上涨到");
					sb.append(StringUtils.trim(ptl.resp_price[i])).append("元");
				}else if (ptl.resp_serviceId[i].equals(XTYJDZResourceKey.ServiceId_STOCK_ZDF))
				{// 股价涨幅
					sb.append("股价日涨幅到");
					if (!ptl.resp_price[i].equals("")
					        && !ptl.resp_price[i].equals("---"))
					{
						// float f_p2 = Float.parseFloat(ptl.resp_sParam2_s[i])
						// * 100;
						String d_p2 = StringUtils.multoString(ptl.resp_price[i],
						        "100");
						sb.append(StringUtils.trim(d_p2)).append("%");
					}
				}
			} else if (ptl.resp_up[i].equals("2"))
			{// ****************** 下跌*********************************
				if (ptl.resp_serviceId[i].equals(XTYJDZResourceKey.ServiceId_STOCK_PRICE))
				{// 股价
					sb.append("股价下跌到");
					sb.append(StringUtils.trim(ptl.resp_price[i])).append("元");
				}else if (ptl.resp_serviceId[i].equals(XTYJDZResourceKey.ServiceId_STOCK_ZDF))
				{// 股价涨跌幅
					sb.append("股价日跌幅到");
					if (!ptl.resp_price[i].equals("")
					        && !ptl.resp_price[i].equals("---"))
					{
						// float f_p2 = Float.parseFloat(ptl.resp_sParam2_s[i])
						// * 100;
						String d_p2 = StringUtils.multoString(ptl.resp_price[i],
						        "100");
						sb.append(StringUtils.trim(d_p2)).append("%");
					}
				}
			} else if (ptl.resp_serviceId[i].equals(XTYJDZResourceKey.ServiceId_STOCK_ZDT))
			{
				sb.append("股价涨跌停");
			}
			msg[i] = sb.toString();
			channel[i] = "程序通知";

			// ********股票名+代码******************
			StringBuffer sb1 = new StringBuffer();
			sb1.append(ptl.resp_stockName[i] + "  ").append(
			        ptl.resp_stockCode[i]);
			stockName[i] = sb1.toString();

		}
	}
}
