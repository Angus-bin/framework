/**
 * 
 */
package com.romaway.android.phone.viewcontrol;

import java.text.DecimalFormat;

import com.romaway.android.phone.R;
import com.romaway.android.phone.view.Theme;
import com.romaway.common.android.base.Res;
import com.romaway.common.protocol.ProtocolConstant;
import com.romaway.common.protocol.hq.HQZXProtocol;
import com.romaway.common.protocol.util.KFloat;
import com.romaway.common.protocol.util.KFloatUtils;
import com.romaway.commons.lang.StringUtils;

/**
 * 自选行情数据转换工具
 * 
 * @author duminghui
 * 
 */
public class UserStockViewControl
{

	private static int[] hqTitlesClickIndexs = new int[] {
	        ProtocolConstant.PX_NONE, ProtocolConstant.PX_ZJCJ,
	        ProtocolConstant.PX_ZDF, ProtocolConstant.PX_ZD,
	        ProtocolConstant.PX_ZRSP, ProtocolConstant.PX_CJSL,
	        ProtocolConstant.PX_CJJE, ProtocolConstant.PX_JK,
	        ProtocolConstant.PX_ZGCJ, ProtocolConstant.PX_ZDCJ,
	        ProtocolConstant.PX_SY, ProtocolConstant.PX_HS };

	/*********************** 自选股行情 ***********************************/
	public static String[] zx_hq_title;
	public static int[] zx_hq_id;

	public static String[] userStockDataTitle()
	{
		//zx_hq_title = Res.getStringArray(R.array.hq_zx_titles);
		return zx_hq_title;
	}

	public static void userStockData(HQZXProtocol ptl, final String[][] datas,
	        final int[][] colors)
	{
		/*
		 * 2013.10.09修改： （1）字段：”现价，买入价，卖出价，今天，最高，最低，市盈率“服务器下为0或000全部转换为“---”显示，
		 * 非0时服务器下发什么就显示什么数值； （2）字段：“昨收，总量，金额”服务器下发什么数值（包括0或非0）就显示什么数值；
		 * （3）字段：“涨跌幅，涨跌”除停盘的股票显示为“---”其他任何时刻服务器下发什么数值（包括0或非0）就显示什么数值；
		 * （4）字段：“换手率
		 * ”（上证，深证股票)任何时刻服务器下发什么数值（包括0或非0）就显示什么数值；(指数，债券，基金)服务器下为0或0.00
		 * 全部转换为“---”显示，非0时服务器下发什么就显示什么数值；
		 */
		zx_hq_id = Res.getIngegerArray(R.array.hq_zx_id);
		// 停牌标志
		int tpbz;
		for (int index = 0; index < datas.length; index++)
		{
			int Color = Theme.hqDPZColors[1];
			int fontColor = Theme.hqDPZColors[KFloatUtils.compare(new KFloat(
			        ptl.resp_nZjcj_s[index]), new KFloat(
			        ptl.resp_nZrsp_s[index])) + 1];
			datas[index][15] = String.valueOf(ptl.resp_wMarketID_s[index]);
			datas[index][16] = String.valueOf(ptl.resp_wType_s[index]);
			datas[index][17] = String.valueOf(ptl.resp_bSuspended_s[index]);//停牌标识
			datas[index][18] = ptl.resp_pszMark_s[index];
			tpbz = ptl.resp_bSuspended_s[index];
			short wType = ptl.resp_wType_s[index]; // 获取商品类型 判断
			String addStr =  Res.getString(R.string.add_str);
			for (int i = 0; i < zx_hq_id.length; i++)
			{
				switch (zx_hq_id[i])
				{
					case 0:

						setData(datas, colors, index, i,
						        String.valueOf(ptl.resp_wMarketID_s[index]),
						        fontColor);
						break;
					case 1:
						setData(datas, colors, index, i,
						        String.valueOf(ptl.resp_wType_s[index]),
						        fontColor);
						break;
					case 2:
						setData(datas, colors, index, i,
						        ptl.resp_pszCode_s[index], fontColor);
						break;
					case 3:
						setData(datas, colors, index, i,
						        ptl.resp_pszName_s[index], fontColor);
						break;
					case 4:
						String zrsp = "";
						if (ptl.resp_wMarketID_s[index] == 9) {// 调节个股期权价格
							zrsp = new DecimalFormat("##0.0000").format(StringUtils.stringToFloat(new KFloat(
											ptl.resp_nZrsp_s[index]).toString()) / 10);
						} else {
							zrsp = new KFloat(ptl.resp_nZrsp_s[index]).toString();
						}
						setData(datas, colors, index, i, zrsp, Color);
						break;
					case 5:
						setData(datas, colors, index, i, new KFloat(
						        ptl.resp_nZhsj_s[index]).toString(), fontColor);
						break;
					case 6:
						if (ptl.resp_wMarketID_s[index] == 9) {// 调节个股期权价格
							String Jrkp = new DecimalFormat("##0.0000").format(StringUtils.stringToFloat(new KFloat(
									ptl.resp_nJrkp_s[index]).toString()) / 10);
							setData(datas,
									colors,
									index,
									i,
									getString(Jrkp),
									tpbz == 0 ? Theme.hqDPZColors[KFloatUtils.compare(
											new KFloat(ptl.resp_nJrkp_s[index]),
											new KFloat(ptl.resp_nZrsp_s[index])) + 1]
											: Color);
						} else {
							setData(datas,
									colors,
									index,
									i,
									getString(new KFloat(ptl.resp_nJrkp_s[index])
									.toString()),
									tpbz == 0 ? Theme.hqDPZColors[KFloatUtils.compare(
									        new KFloat(ptl.resp_nJrkp_s[index]),
									        new KFloat(ptl.resp_nZrsp_s[index])) + 1]
									                : Color);
						}

						break;
					case 7:
						if (ptl.resp_wMarketID_s[index] == 9) {// 调节个股期权价格
							String Zgcj = new DecimalFormat("##0.0000").format(StringUtils.stringToFloat(new KFloat(
												ptl.resp_nZgcj_s[index]).toString()) / 10);
							setData(datas,
									colors,
									index,
									i,
									getString(Zgcj),
									tpbz == 0 ? Theme.hqDPZColors[KFloatUtils.compare(
											new KFloat(ptl.resp_nZgcj_s[index]),
											new KFloat(ptl.resp_nZrsp_s[index])) + 1]
											: Color);
						} else {
							setData(datas,
									colors,
									index,
									i,
									getString(new KFloat(ptl.resp_nZgcj_s[index])
									.toString()),
									tpbz == 0 ? Theme.hqDPZColors[KFloatUtils.compare(
											new KFloat(ptl.resp_nZgcj_s[index]),
											new KFloat(ptl.resp_nZrsp_s[index])) + 1]
													: Color);
						}
						break;
					case 8:
						if (ptl.resp_wMarketID_s[index] == 9) {// 调节个股期权价格
							String Zdcj = new DecimalFormat("##0.0000").format(StringUtils.stringToFloat(new KFloat(
												ptl.resp_nZdcj_s[index]).toString()) / 10);
							setData(datas,
									colors,
									index,
									i,
									getString(Zdcj),
									tpbz == 0 ? Theme.hqDPZColors[KFloatUtils.compare(
											new KFloat(ptl.resp_nZdcj_s[index]),
											new KFloat(ptl.resp_nZrsp_s[index])) + 1]
											: Color);
						} else {
							setData(datas,
									colors,
									index,
									i,
									getString(new KFloat(ptl.resp_nZdcj_s[index])
									.toString()),
									tpbz == 0 ? Theme.hqDPZColors[KFloatUtils.compare(
											new KFloat(ptl.resp_nZdcj_s[index]),
											new KFloat(ptl.resp_nZrsp_s[index])) + 1]
													: Color);
						}

						break;
					case 9:
						if (ptl.resp_wMarketID_s[index] == 9) {// 调节个股期权价格
							String Zjcj = new DecimalFormat("##0.0000").format(StringUtils.stringToFloat(new KFloat(
									ptl.resp_nZjcj_s[index]).toString())/10);
							setData(datas, colors, index, i, getString(Zjcj),
									tpbz == 0 ? fontColor : Color);
						} else {
							setData(datas, colors, index, i, getString(new KFloat(
									ptl.resp_nZjcj_s[index]).toString()),
									tpbz == 0 ? fontColor : Color);
						}

						break;
					case 10:
						setData(datas, colors, index, i, new KFloat(
						        ptl.resp_nCjss_s[index]).toString(), Color);
						break;
					case 11:
						setData(datas, colors, index, i, new KFloat(
						        ptl.resp_nCjje_s[index]).toString(), Color);
						break;
					case 12:
						setData(datas, colors, index, i, new KFloat(
						        ptl.resp_nCcl_s[index]).toString(), Color);
						break;
					case 13:
						setData(datas, colors, index, i, new KFloat(
						        ptl.resp_nHsj_s[index]).toString(), fontColor);
						break;
					case 14:
						setData(datas, colors, index, i, new KFloat(
						        ptl.resp_nLimUp_s[index]).toString(), fontColor);
						break;
					case 15:
						setData(datas, colors, index, i, new KFloat(
						        ptl.resp_nLimDown_s[index]).toString(),
						        fontColor);
						break;
					case 16:
						if (tpbz == 0)
						{
							setData(datas,
							        colors,
							        index,
							        i,
							        getString(new KFloat(
							                ptl.resp_nBjg1_s[index]).toString()),
							        Theme.hqDPZColors[KFloatUtils
							                .compare(
							                        new KFloat(
							                                ptl.resp_nBjg1_s[index]),
							                        new KFloat(
							                                ptl.resp_nZrsp_s[index])) + 1]);
						} else
						{
							setData(datas,
							        colors,
							        index,
							        i,
							        getString(new KFloat(
							                ptl.resp_nBjg1_s[index]).toString()),
							        Color);
						}
						break;
					case 17:
						setData(datas, colors, index, i, new KFloat(
						        ptl.resp_nBss1_s[index]).toString(), fontColor);
						break;
					case 18:
						setData(datas, colors, index, i, new KFloat(
						        ptl.resp_nBjg2_s[index]).toString(), fontColor);
						break;
					case 19:
						setData(datas, colors, index, i, new KFloat(
						        ptl.resp_nBss2_s[index]).toString(), fontColor);
						break;
					case 20:
						setData(datas, colors, index, i, new KFloat(
						        ptl.resp_nBjg3_s[index]).toString(), fontColor);
						break;
					case 21:
						setData(datas, colors, index, i, new KFloat(
						        ptl.resp_nBss3_s[index]).toString(), fontColor);
						break;
					case 22:
						setData(datas, colors, index, i, new KFloat(
						        ptl.resp_nBjg4_s[index]).toString(), fontColor);
						break;
					case 23:
						setData(datas, colors, index, i, new KFloat(
						        ptl.resp_nBss4_s[index]).toString(), fontColor);
						break;
					case 24:
						setData(datas, colors, index, i, new KFloat(
						        ptl.resp_nBjg5_s[index]).toString(), fontColor);
						break;
					case 25:
						if (tpbz == 0)
						{
							setData(datas, colors, index, i, new KFloat(
							        ptl.resp_nBss5_s[index]).toString(),
							        Theme.hqDPZColors[1]);
						} else
						{
							setData(datas,
							        colors,
							        index,
							        i,
							        new KFloat(ptl.resp_nBss5_s[index])
							                .toString(),
							        Theme.hqDPZColors[KFloatUtils
							                .compare(
							                        new KFloat(
							                                ptl.resp_nBss5_s[index]),
							                        new KFloat(
							                                ptl.resp_nBss5_s[index])) + 1]);
						}
						break;
					case 26:
						if (tpbz == 0)
						{
							setData(datas,
							        colors,
							        index,
							        i,
							        getString(new KFloat(
							                ptl.resp_nSjg1_s[index]).toString()),
							        Theme.hqDPZColors[KFloatUtils
							                .compare(
							                        new KFloat(
							                                ptl.resp_nSjg1_s[index]),
							                        new KFloat(
							                                ptl.resp_nZrsp_s[index])) + 1]);
						} else
						{
							setData(datas,
							        colors,
							        index,
							        i,
							        getString(new KFloat(
							                ptl.resp_nSjg1_s[index]).toString()),
							        Color);
						}
						break;
					case 27:
						setData(datas, colors, index, i, new KFloat(
						        ptl.resp_nSss1_s[index]).toString(), fontColor);
						break;
					case 28:
						setData(datas, colors, index, i, new KFloat(
						        ptl.resp_nSjg2_s[index]).toString(), fontColor);
						break;
					case 29:
						setData(datas, colors, index, i, new KFloat(
						        ptl.resp_nSss2_s[index]).toString(), fontColor);
						break;
					case 30:
						setData(datas, colors, index, i, new KFloat(
						        ptl.resp_nSjg3_s[index]).toString(), fontColor);
						break;
					case 31:
						setData(datas, colors, index, i, new KFloat(
						        ptl.resp_nSss3_s[index]).toString(), fontColor);
						break;
					case 32:
						setData(datas, colors, index, i, new KFloat(
						        ptl.resp_nSjg4_s[index]).toString(), fontColor);
						break;
					case 33:
						setData(datas, colors, index, i, new KFloat(
						        ptl.resp_nSss4_s[index]).toString(), fontColor);
						break;
					case 34:
						setData(datas, colors, index, i, new KFloat(
						        ptl.resp_nSjg5_s[index]).toString(), fontColor);
						break;
					case 35:
						setData(datas, colors, index, i, new KFloat(
						        ptl.resp_nSss5_s[index]).toString(), fontColor);
						break;
					case 36:
						if (ptl.resp_wMarketID_s[index] == 9) {// 调节个股期权价格
							String Zd = new DecimalFormat("##0.0000").format(StringUtils.stringToFloat(new KFloat(
									ptl.resp_nZd_s[index]).toString())/10);
							Zd = tpbz == 0 ? (Zd.equals("--") ? "0.00"
					                : Zd) : "--";
							if (!Zd.contains("-") && !Zd.equals("0.00") && !Zd.equals("--")) {
								Zd = addStr + Zd;
							}
							setData(datas,
							        colors,
							        index,
							        i,
							        Zd,
							        tpbz == 0 ? fontColor : Color);
						} else {
							String zd = tpbz == 0 ? (new KFloat(ptl.resp_nZd_s[index])
							.toString().equals("--") ? "0.00"
									: new KFloat(ptl.resp_nZd_s[index])
							.toString()) : "--";
							if (!zd.contains("-") && !zd.equals("0.00") && !zd.equals("--")) {
								zd = addStr + zd;
							}
							setData(datas,
									colors,
									index,
									i,
									zd,
									tpbz == 0 ? fontColor : Color);
						}
						break;
					case 37:
						String zdf = tpbz == 0 ? (new KFloat(ptl.resp_nZdf_s[index])
						.toString().equals("--") ? "0.00%"
						: new KFloat(ptl.resp_nZdf_s[index])
								.toString() + "%") : "--";
						if (!zdf.contains("-") && !zdf.equals("0.00%") && !zdf.equals("--")) {
							zdf = "+" + zdf;
						}
						setData(datas, colors, index, i, zdf, tpbz == 0 ? fontColor
								: Color);
						break;
					case 38:
						setData(datas, colors, index, i, new KFloat(
						        ptl.resp_nZl_s[index]).toString(), fontColor);
						break;
					case 39:
						setData(datas, colors, index, i, new KFloat(
						        ptl.resp_nWb_s[index]).toString(), fontColor);
						break;
					case 40:
						setData(datas, colors, index, i, new KFloat(
						        ptl.resp_nLb_s[index]).toString(), fontColor);
						break;
					case 41:
						setData(datas, colors, index, i, new KFloat(
						        ptl.resp_n5Min_s[index]).toString(), fontColor);
						break;
					case 42:
						setData(datas, colors, index, i, new KFloat(
						        ptl.resp_nCjjj_s[index]).toString(), fontColor);
						break;
					case 43:
						setData(datas, colors, index, i, new KFloat(
						        ptl.resp_bSuspended_s[index]).toString(),
						        fontColor);
						break;
					case 44:
						String hsy = getString(new KFloat(ptl.resp_nHsl_s[index]).toString(), wType);
						setData(datas,
						        colors,
						        index,
						        i,
						        hsy + (hsy.equals("---")? "" : "%"),
								Color);
						break;
					case 45:
						setData(datas, colors, index, i, getString(new KFloat(
						        ptl.resp_nSyl_s[index]).toString()), Color);
						break;
					case 46:
						setData(datas, colors, index, i, new KFloat(
						        ptl.resp_nBP_s[index]).toString(), fontColor);
						break;
					case 47:
						setData(datas, colors, index, i, new KFloat(
						        ptl.resp_nSP_s[index]).toString(), fontColor);
						break;

					case 48:
						setData(datas, colors, index, i,
						        ptl.resp_sLinkFlag_s[index], fontColor);
						break;
					case 49:
						setData(datas, colors, index, i, new KFloat(
						        ptl.resp_nZZZQValPrice[index]).toString()
						        .toString(), fontColor);
						break;
					case 50:
						setData(datas, colors, index, i,
						        ptl.resp_sZZZQValPrice[index], fontColor);
						break;
				}
			}
		}
	}
	
	/**
	 * 港股通自选行情
	 * @param ptl
	 * @param datas
	 * @param colors
	 */
	public static void userStockDataGGT(HQZXProtocol ptl, final String[][] datas,
	        final int[][] colors)
	{
		/*
		 * 2013.10.09修改： （1）字段：”现价，买入价，卖出价，今天，最高，最低，市盈率“服务器下为0或000全部转换为“---”显示，
		 * 非0时服务器下发什么就显示什么数值； （2）字段：“昨收，总量，金额”服务器下发什么数值（包括0或非0）就显示什么数值；
		 * （3）字段：“涨跌幅，涨跌”除停盘的股票显示为“---”其他任何时刻服务器下发什么数值（包括0或非0）就显示什么数值；
		 * （4）字段：“换手率
		 * ”（上证，深证股票)任何时刻服务器下发什么数值（包括0或非0）就显示什么数值；(指数，债券，基金)服务器下为0或0.00
		 * 全部转换为“---”显示，非0时服务器下发什么就显示什么数值；
		 */
		zx_hq_id =  Res.getIngegerArray(R.array.hq_ggt_zx_id);
		// 停牌标志
		int tpbz;
		for (int index = 0; index < datas.length; index++)
		{
			int Color = Theme.hqDPZColors[1];
			int fontColor = Theme.hqDPZColors[KFloatUtils.compare(new KFloat(
			        ptl.resp_nZjcj_s[index]), new KFloat(
			        ptl.resp_nZrsp_s[index])) + 1];
			datas[index][15] = String.valueOf(ptl.resp_wMarketID_s[index]);
			datas[index][16] = String.valueOf(ptl.resp_wType_s[index]);
			tpbz = ptl.resp_bSuspended_s[index];
			short wType = ptl.resp_wType_s[index]; // 获取商品类型 判断
			for (int i = 0; i < zx_hq_id.length; i++)
			{
				switch (zx_hq_id[i])
				{
					case 0:

						setData(datas, colors, index, i,
						        String.valueOf(ptl.resp_wMarketID_s[index]),
						        fontColor);
						break;
					case 1:
						setData(datas, colors, index, i,
						        String.valueOf(ptl.resp_wType_s[index]),
						        fontColor);
						break;
					case 2:
						setData(datas, colors, index, i,
						        ptl.resp_pszCode_s[index], fontColor);
						break;
					case 3:
						setData(datas, colors, index, i,
						        ptl.resp_pszName_s[index], fontColor);
						break;
					case 4:
						setData(datas, colors, index, i, new KFloat(
						        ptl.resp_nZrsp_s[index]).toString(), Color);
						break;
					case 5:
						setData(datas, colors, index, i, new KFloat(
						        ptl.resp_nZhsj_s[index]).toString(), fontColor);
						break;
					case 6:
						setData(datas,
						        colors,
						        index,
						        i,
						        getString(new KFloat(ptl.resp_nJrkp_s[index])
						                .toString()),
						        tpbz == 0 ? Theme.hqDPZColors[KFloatUtils
						                .compare(
						                        new KFloat(
						                                ptl.resp_nJrkp_s[index]),
						                        new KFloat(
						                                ptl.resp_nZrsp_s[index])) + 1]
						                : Color);

						break;
					case 7:

						setData(datas,
						        colors,
						        index,
						        i,
						        getString(new KFloat(ptl.resp_nZgcj_s[index])
						                .toString()),
						        tpbz == 0 ? Theme.hqDPZColors[KFloatUtils
						                .compare(
						                        new KFloat(
						                                ptl.resp_nZgcj_s[index]),
						                        new KFloat(
						                                ptl.resp_nZrsp_s[index])) + 1]
						                : Color);
						break;
					case 8:

						setData(datas,
						        colors,
						        index,
						        i,
						        getString(new KFloat(ptl.resp_nZdcj_s[index])
						                .toString()),
						        tpbz == 0 ? Theme.hqDPZColors[KFloatUtils
						                .compare(
						                        new KFloat(
						                                ptl.resp_nZdcj_s[index]),
						                        new KFloat(
						                                ptl.resp_nZrsp_s[index])) + 1]
						                : Color);

						break;
					case 9:
						setData(datas, colors, index, i, getString(new KFloat(
						        ptl.resp_nZjcj_s[index]).toString()),
						        tpbz == 0 ? fontColor : Color);

						break;
					case 10:
						setData(datas, colors, index, i, new KFloat(
						        ptl.resp_nCjss_s[index]).toString(), Color);
						break;
					case 11:
						setData(datas, colors, index, i, new KFloat(
						        ptl.resp_nCjje_s[index]).toString(), Color);
						break;
					case 12:
						setData(datas, colors, index, i, new KFloat(
						        ptl.resp_nCcl_s[index]).toString(), Color);
						break;
					case 13:
						setData(datas, colors, index, i, new KFloat(
						        ptl.resp_nHsj_s[index]).toString(), fontColor);
						break;
					case 14:
						setData(datas, colors, index, i, new KFloat(
						        ptl.resp_nLimUp_s[index]).toString(), fontColor);
						break;
					case 15:
						setData(datas, colors, index, i, new KFloat(
						        ptl.resp_nLimDown_s[index]).toString(),
						        fontColor);
						break;
					case 16:
						if (tpbz == 0)
						{
							setData(datas,
							        colors,
							        index,
							        i,
							        getString(new KFloat(
							                ptl.resp_nBjg1_s[index]).toString()),
							        Theme.hqDPZColors[KFloatUtils
							                .compare(
							                        new KFloat(
							                                ptl.resp_nBjg1_s[index]),
							                        new KFloat(
							                                ptl.resp_nZrsp_s[index])) + 1]);
						} else
						{
							setData(datas,
							        colors,
							        index,
							        i,
							        getString(new KFloat(
							                ptl.resp_nBjg1_s[index]).toString()),
							        Color);
						}
						break;
					case 17:
						setData(datas, colors, index, i, new KFloat(
						        ptl.resp_nBss1_s[index]).toString(), fontColor);
						break;
					case 18:
						setData(datas, colors, index, i, new KFloat(
						        ptl.resp_nBjg2_s[index]).toString(), fontColor);
						break;
					case 19:
						setData(datas, colors, index, i, new KFloat(
						        ptl.resp_nBss2_s[index]).toString(), fontColor);
						break;
					case 20:
						setData(datas, colors, index, i, new KFloat(
						        ptl.resp_nBjg3_s[index]).toString(), fontColor);
						break;
					case 21:
						setData(datas, colors, index, i, new KFloat(
						        ptl.resp_nBss3_s[index]).toString(), fontColor);
						break;
					case 22:
						setData(datas, colors, index, i, new KFloat(
						        ptl.resp_nBjg4_s[index]).toString(), fontColor);
						break;
					case 23:
						setData(datas, colors, index, i, new KFloat(
						        ptl.resp_nBss4_s[index]).toString(), fontColor);
						break;
					case 24:
						setData(datas, colors, index, i, new KFloat(
						        ptl.resp_nBjg5_s[index]).toString(), fontColor);
						break;
					case 25:
						if (tpbz == 0)
						{
							setData(datas, colors, index, i, new KFloat(
							        ptl.resp_nBss5_s[index]).toString(),
							        Theme.hqDPZColors[1]);
						} else
						{
							setData(datas,
							        colors,
							        index,
							        i,
							        new KFloat(ptl.resp_nBss5_s[index])
							                .toString(),
							        Theme.hqDPZColors[KFloatUtils
							                .compare(
							                        new KFloat(
							                                ptl.resp_nBss5_s[index]),
							                        new KFloat(
							                                ptl.resp_nBss5_s[index])) + 1]);
						}
						break;
					case 26:
						if (tpbz == 0)
						{
							setData(datas,
							        colors,
							        index,
							        i,
							        getString(new KFloat(
							                ptl.resp_nSjg1_s[index]).toString()),
							        Theme.hqDPZColors[KFloatUtils
							                .compare(
							                        new KFloat(
							                                ptl.resp_nSjg1_s[index]),
							                        new KFloat(
							                                ptl.resp_nZrsp_s[index])) + 1]);
						} else
						{
							setData(datas,
							        colors,
							        index,
							        i,
							        getString(new KFloat(
							                ptl.resp_nSjg1_s[index]).toString()),
							        Color);
						}
						break;
					case 27:
						setData(datas, colors, index, i, new KFloat(
						        ptl.resp_nSss1_s[index]).toString(), fontColor);
						break;
					case 28:
						setData(datas, colors, index, i, new KFloat(
						        ptl.resp_nSjg2_s[index]).toString(), fontColor);
						break;
					case 29:
						setData(datas, colors, index, i, new KFloat(
						        ptl.resp_nSss2_s[index]).toString(), fontColor);
						break;
					case 30:
						setData(datas, colors, index, i, new KFloat(
						        ptl.resp_nSjg3_s[index]).toString(), fontColor);
						break;
					case 31:
						setData(datas, colors, index, i, new KFloat(
						        ptl.resp_nSss3_s[index]).toString(), fontColor);
						break;
					case 32:
						setData(datas, colors, index, i, new KFloat(
						        ptl.resp_nSjg4_s[index]).toString(), fontColor);
						break;
					case 33:
						setData(datas, colors, index, i, new KFloat(
						        ptl.resp_nSss4_s[index]).toString(), fontColor);
						break;
					case 34:
						setData(datas, colors, index, i, new KFloat(
						        ptl.resp_nSjg5_s[index]).toString(), fontColor);
						break;
					case 35:
						setData(datas, colors, index, i, new KFloat(
						        ptl.resp_nSss5_s[index]).toString(), fontColor);
						break;
					case 36:
						setData(datas,
						        colors,
						        index,
						        i,
						        tpbz == 0 ? (new KFloat(ptl.resp_nZd_s[index])
						                .toString().equals("---") ? "0.00"
						                : new KFloat(ptl.resp_nZd_s[index])
						                        .toString()) : "---",
						        tpbz == 0 ? fontColor : Color);
						break;
					case 37:
						setData(datas,
						        colors,
						        index,
						        i,
						        tpbz == 0 ? (new KFloat(ptl.resp_nZdf_s[index])
						                .toString().equals("---") ? "0.00"
						                : new KFloat(ptl.resp_nZdf_s[index])
						                        .toString()) : "---",
						        tpbz == 0 ? fontColor : Color);
						break;
					case 38:
						setData(datas, colors, index, i, new KFloat(
						        ptl.resp_nZl_s[index]).toString(), fontColor);
						break;
					case 39:
						setData(datas, colors, index, i, new KFloat(
						        ptl.resp_nWb_s[index]).toString(), fontColor);
						break;
					case 40:
						setData(datas, colors, index, i, new KFloat(
						        ptl.resp_nLb_s[index]).toString(), fontColor);
						break;
					case 41:
						setData(datas, colors, index, i, new KFloat(
						        ptl.resp_n5Min_s[index]).toString(), fontColor);
						break;
					case 42:
						setData(datas, colors, index, i, new KFloat(
						        ptl.resp_nCjjj_s[index]).toString(), fontColor);
						break;
					case 43:
						setData(datas, colors, index, i, new KFloat(
						        ptl.resp_bSuspended_s[index]).toString(),
						        fontColor);
						break;
					case 44:
						setData(datas,
						        colors,
						        index,
						        i,
						        getString(new KFloat(ptl.resp_nHsl_s[index])
						                .toString(), wType), Color);
						break;
					case 45:
						setData(datas, colors, index, i, getString(new KFloat(
						        ptl.resp_nSyl_s[index]).toString()), Color);
						break;
					case 46:
						setData(datas, colors, index, i, new KFloat(
						        ptl.resp_nBP_s[index]).toString(), fontColor);
						break;
					case 47:
						setData(datas, colors, index, i, new KFloat(
						        ptl.resp_nSP_s[index]).toString(), fontColor);
						break;

					case 48:
						setData(datas, colors, index, i,
						        ptl.resp_sLinkFlag_s[index], fontColor);
						break;
					case 49:
						setData(datas, colors, index, i, new KFloat(
						        ptl.resp_nZZZQValPrice[index]).toString()
						        .toString(), fontColor);
						break;
					case 50:
						setData(datas, colors, index, i,
						        ptl.resp_sZZZQValPrice[index], fontColor);
						break;

				}
			}
		}
	}

	/**
	 * 得到排序的字段数组
	 * 
	 * @return
	 */
	public static int[] getPXTitleFields()
	{
		return hqTitlesClickIndexs;
	}

	private static void setData(final String[][] rows, final int[][] colors,
	        final int rowNum, final int index, String value, int color)
	{
		rows[rowNum][index] = value;
		colors[rowNum][index] = color;
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
