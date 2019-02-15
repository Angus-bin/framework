/**
 * 
 */
package com.romaway.android.phone.view;

import com.romaway.android.phone.R;
import com.romaway.common.android.base.Res;
import com.romawaylibs.theme.ROMA_SkinManager;

/**
 * @author duminghui
 * 
 */
public class Theme
{
	/**
	 * 主页自选股涨、平、跌颜色
	 */
	public static int[] homeUserStockDPZColors;

	/**
	 * 行情列表涨、平、跌颜色
	 */
	public static int[] hqDPZColors;

	public static int[] hqListDPZColors;

	/**
	 * 交易--查询列表涨、跌、平颜色
	 */
	public static int[] jyZDPColor;
	
	public final static void init()
	{

		homeUserStockDPZColors = new int[] {
		        Res.getColor(R.color.home_userstock_d),
		        Res.getColor(R.color.home_userstock_p),
		        Res.getColor(R.color.home_userstock_z), };

//		hqDPZColors = new int[] { Res.getColor(R.color.hq_d),
//		        Res.getColor(R.color.hq_p), Res.getColor(R.color.hq_z), };
		hqDPZColors = new int[] {
				ROMA_SkinManager.getColor("HqDColor", 0xff0db14b),
				ROMA_SkinManager.getColor("HqPColor", 0xff626262),
				ROMA_SkinManager.getColor("HqZColor", 0xffff3d00)};
		hqListDPZColors = new int[] {
				ROMA_SkinManager.getColor("HqStockListZdfBgDColor", 0xff0db14b),
		        ROMA_SkinManager.getColor("HqPColor", 0x626262),
				ROMA_SkinManager.getColor("HqZColor", 0xffff3d00)};

		jyZDPColor = new int[] { Res.getColor(R.color.jy_d),
		        Res.getColor(R.color.jy_p), Res.getColor(R.color.jy_z), };
	}
}
