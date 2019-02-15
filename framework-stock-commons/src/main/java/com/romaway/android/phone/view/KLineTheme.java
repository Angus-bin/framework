package com.romaway.android.phone.view;
import com.romaway.common.android.base.Res;
import com.romaway.android.phone.R;

/**
 * k线高度，颜色设置
 * @author xueyan
 *
 */  
public class KLineTheme
{
	//------------------
	public static int theme_kline_height_timeCycle;
	public static int theme_kline_height_avLineTitle;
	public static int theme_kline_height_kLineBody;
	public static int theme_kline_height_techTitle;
	public static int theme_kline_height_techBody;
	public static int theme_kline_wide_techItem_w;
	public static int theme_kline_wide_priceBox;
	public static int theme_kline_wide_techData;
	public static int theme_kline_height_techItem_h;
	public static int kline_wide_timeCycleText;
	public static int kline_wide_avLineDataText;
	public static int kline_wide_priceDataText;
	public static int kline_wide_techDataText;
	public static int clr_kline_background;
	public static int clr_kline_cycleTextOn;
	public static int clr_kline_cycleTextOff;
	public static int theme_kline_height_floatRect;
	public static int theme_kline_wide_floatRect;
	public static int clr_kline_lineColor;
	public static int theme_kline_wide_realline;
	public static int theme_kline_wide_dottedline;
	public static int theme_kline_wide_techDataText;
	public static int clr_kline_crosslineColor;
	public static int theme_kline_wide_crossline;
	public static int jy_popwindow_begin_height;
	public static int kline_menu_width;
	/**
	 * 白色
	 */
	public static int A0;
	/**
	 * 红色
	 */
	public static int A1;
	/**
	 * 绿色
	 */
	public static int A2;
	/**
	 * 黄色
	 */
	public static int A3;
	/**
	 * 浅黑色
	 */
	public static int A4;
	/**
	 * 蓝色
	 */
	public static int A5;
	/**
	 * 浅灰色
	 */
	public static int A6;
	/**
	 * 灰色
	 */
	public static int A7;
	/**
	 * 橘黄色
	 */
	public static int A8;
	/**
	 * 紫色
	 */
	public static int A9;
	
	//-------------
	public static int kline_scape_height_Title;
	public static int kline_scape_height_Cycle;
	public static int kline_scape_height_klineData;
	public static int kline_scape_height_kline;
	public static int kline_scape_height_linescape;
	public static int kline_scape_height_date;
	public static int kline_scape_height_tech;
	public static int kline_scape_wide_timeCycleText;
	public static int kline_scape_height_step;
	public static int kline_scape_wide_klineData;
	public static int clr_kline_name;
	public static int clr_kline_code;
	public static int clr_kline_up;
	public static int clr_kline_down;
	public static int clr_kline_tie;
	public static int kline_scape_height_jumpH;
	public static int kline_scape_height_jumpW;
	public static int theme_kline_floatText;
	public static int  theme_kline_wide_avLineDataText;
	public static int kline_scape_name;
	public static int kline_scape_code;
	public static int kline_scape_ma;
	
	public final static void initKLTheme()
	{
		theme_kline_height_timeCycle = Res.getDimen(R.dimen.theme_kline_height_timeCycle);
		theme_kline_height_avLineTitle = Res.getDimen(R.dimen.theme_kline_height_avLineTitle);
		theme_kline_height_kLineBody = Res.getDimen(R.dimen.theme_kline_height_kLineBody);
		theme_kline_height_techTitle = Res.getDimen(R.dimen.theme_kline_height_techTitle);
		theme_kline_height_techBody = Res.getDimen(R.dimen.theme_kline_height_techBody);
		theme_kline_wide_techItem_w = Res.getDimen(R.dimen.theme_kline_wide_techItem_w);
		theme_kline_wide_priceBox = Res.getDimen(R.dimen.theme_kline_wide_priceBox);
		theme_kline_wide_techData = Res.getDimen(R.dimen.theme_kline_wide_techData);
		theme_kline_height_techItem_h = Res.getDimen(R.dimen.theme_kline_height_techItem_h);
		kline_wide_timeCycleText = Res.getDimen(R.dimen.theme_kline_wide_timeCycleText);
		kline_wide_avLineDataText = Res.getDimen(R.dimen.theme_kline_wide_avLineDataText);
		kline_wide_priceDataText = Res.getDimen(R.dimen.theme_kline_wide_priceDataText);
		kline_wide_techDataText = Res.getDimen(R.dimen.theme_kline_wide_techDataText);
		clr_kline_background = Res.getColor(R.color.clr_kline_background);
		clr_kline_cycleTextOn = Res.getColor(R.color.clr_kline_cycleTextOn);
		clr_kline_cycleTextOff = Res.getColor(R.color.clr_kline_cycleTextOff);
		theme_kline_height_floatRect = Res.getDimen(R.dimen.theme_kline_height_floatRect);
		theme_kline_wide_floatRect = Res.getDimen(R.dimen.theme_kline_wide_floatRect);
		kline_scape_height_Title = Res.getDimen(R.dimen.kline_scape_height_Title);
		kline_scape_height_Cycle = Res.getDimen(R.dimen.kline_scape_height_Cycle);
		kline_scape_height_klineData = Res.getDimen(R.dimen.kline_scape_height_klineData);
		kline_scape_height_kline = Res.getDimen(R.dimen.kline_scape_height_kline);
		kline_scape_height_linescape = Res.getDimen(R.dimen.kline_scape_height_linescape);
		kline_scape_height_date = Res.getDimen(R.dimen.kline_scape_height_date);
		kline_scape_height_tech = Res.getDimen(R.dimen.kline_scape_height_tech);
		kline_scape_wide_timeCycleText = Res.getDimen(R.dimen.kline_scape_wide_timeCycleText);
		kline_scape_height_step = Res.getDimen(R.dimen.kline_scape_height_step);
		kline_scape_wide_klineData = Res.getDimen(R.dimen.kline_scape_wide_klineData);
		clr_kline_name = Res.getColor(R.color.clr_kline_name);
		clr_kline_code = Res.getColor(R.color.clr_kline_code);
		clr_kline_up = Res.getColor(R.color.clr_kline_up);
		clr_kline_down = Res.getColor(R.color.clr_kline_down);
		clr_kline_tie = Res.getColor(R.color.clr_kline_tie);
		clr_kline_lineColor = Res.getColor(R.color.clr_kline_lineColor);
		theme_kline_wide_realline = Res.getDimen(R.dimen.theme_kline_wide_realline);
		theme_kline_wide_dottedline = Res.getDimen(R.dimen.theme_kline_wide_realline);
		theme_kline_wide_techDataText = Res.getDimen(R.dimen.theme_kline_wide_techDataText);
		clr_kline_crosslineColor = Res.getColor(R.color.clr_fs_crossline);
		theme_kline_wide_crossline = Res.getDimen(R.dimen.theme_kline_wide_crossline);
		A0 = Res.getColor(R.color.KL_A0);
		A1 = Res.getColor(R.color.KL_A1);
		A2 = Res.getColor(R.color.KL_A2);
		A3 = Res.getColor(R.color.KL_A3);
		A4 = Res.getColor(R.color.KL_A4);
		A5 = Res.getColor(R.color.KL_A5);
		A6 = Res.getColor(R.color.KL_A6);
		A7 = Res.getColor(R.color.KL_A7);
		A8 = Res.getColor(R.color.KL_A8);
		A9 = Res.getColor(R.color.KL_A9);
		kline_scape_height_jumpW = Res.getDimen(R.dimen.kline_scape_height_jumpW);
		kline_scape_height_jumpH = Res.getDimen(R.dimen.kline_scape_height_jumpH);
		theme_kline_floatText =  Res.getDimen(R.dimen.theme_kline_floatText);
		theme_kline_wide_avLineDataText = Res.getDimen(R.dimen.theme_kline_wide_avLineDataText);
		kline_scape_name = Res.getDimen(R.dimen.kline_scape_name);
		kline_scape_code = Res.getDimen(R.dimen.kline_scape_code);
		kline_scape_ma = Res.getDimen(R.dimen.kline_scape_ma);
		jy_popwindow_begin_height = Res.getDimen(R.dimen.jy_popwindow_begin_height);
		kline_menu_width = Res.getDimen(R.dimen.kline_menu_width);
		
	}
}
