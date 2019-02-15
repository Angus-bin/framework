/**
 * 
 */
package com.romaway.android.phone.view;

import com.romaway.common.android.base.Res;
import com.romaway.android.phone.R;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * 分时界面宽、高、颜色等参数<br>
 * 
 * @author duminghui
 * 
 * 
 */
public class MinuteViewTheme
{
	public static int list_item_width_stock = 70;

	/** 分时区高度，包括成交量区 **/
	public static int theme_fs_height;

	/** 分时框与成交量的间隙 **/
	public static int theme_fs_regional_gap;

	/** 分时区底部时间的高度 **/
	public static int theme_fs_time_height;

	/** 分时区背景颜色 **/
	public static int clr_fs_bgn;

	public static int clr_fs_yellow;
	public static int clr_fs_red;
	public static int clr_fs_green;
	public static int clr_fs_blue;

	/** 十字線顏色 **/
	public static int clr_fs_crossline;
	/** 分时区线颜色 **/
	public static int clr_fs_line;
	/** 分时区线宽度 **/
	public static int theme_fs_line_width;

	/** 分时区价格内边距 **/
	public static int theme_fs_bs5_text_padding;

	/** 浮动框高 **/
	public static int theme_fs_floatRect_height;
	/** 浮动框宽 **/
	public static int theme_fs_floatRect_width;
	/** 浮动框字体大小 **/
	public static int theme_fs_floatRect_textsize;
	/** 浮动框背景色 **/
	public static int clr_fs_floatRect_bgn;

	/** 买卖五档标题宽 **/
	public static int theme_fs_bs5_title_width;
	/** 买卖五档标题高 **/
	public static int theme_fs_bs5_title_height;
	/** 买卖五档标题字体 **/
	public static int theme_fs_bs5_title_textsize;
	/** 买卖五档主体宽 **/
	public static int theme_fs_bs5_body_width;
	/** 买卖五档主体高 **/
	public static int theme_fs_bs5_body_height;
	/** 买卖五档主体字体 **/
	public static int theme_fs_bs5_body_textsize;
	/** 买卖五档港股时价量字体 **/
	public static int theme_fs_bs5_sjl_textsize;
	/** 买卖五档按钮宽 **/
	public static int theme_fs_bs5_btn_width;
	/** 买卖五档按钮高 **/
	public static int theme_fs_bs5_btn_height;

	/** 明细标题高度 **/
	public static int theme_fs_mx_title_height;
	/** 明细标题字体大小 **/
	public static int theme_fs_mx_textsize;

	/** 顶部标题高 **/
	public static int theme_fs_title_height;
	/** 标题左边宽 **/
	public static int theme_fs_title_leftrect_width;
	/** 标题 增加自选按钮长度 **/
	public static int theme_fs_title_addstock_btn_width;
	// *********************************************
	/** 现价 字体大小 **/
	public static int theme_fs_title_zjcj_textsize;
	/** 涨跌幅 字体大小 **/
	public static int theme_fs_title_zdf_textsize;
	/** 右部 字体大小 **/
	public static int theme_fs_title_right_textsize;
	/** 香港指数字体大小（需小） **/
	public static int theme_fs_title_right_hkzs_textsize;
	/** 右部 背景颜色 **/
	public static int clr_fs_title_leftrect_bgn;
	/** 标题区 平 **/
	public static int clr_fs_title_leftrect_ping;

	/** -------------------------以下为横屏------------------------------------ */
	/** 横屏标题高 **/
	public static int theme_fs_landscape_title_height;
	/** 横屏盘口宽 **/
	public static int theme_fs_landscape_bs5_width;
	/** 标题 证券名称大小 **/
	public static int theme_fs_landscape_title_stocksize;
	/** 标题 按钮长 **/
	public static int theme_fs_landscape_title_btn_height;
	/** 标题 按钮宽 **/
	public static int theme_fs_landscape_title_btn_width;

	public final static void initTheme()
	{
		// 公共颜色 黄、红、绿
		clr_fs_yellow = Res.getColor(R.color.clr_fs_yellow);
		clr_fs_red = Res.getColor(R.color.clr_fs_red);
		clr_fs_green = Res.getColor(R.color.clr_fs_green);
		clr_fs_blue = Res.getColor(R.color.clr_fs_blue);

		clr_fs_bgn = Res.getColor(R.color.clr_fs_bgn);
		clr_fs_crossline = Res.getColor(R.color.clr_fs_crossline);

		// 线条颜色
		clr_fs_line = Res.getColor(R.color.clr_fs_line);
		// 线条宽度
		theme_fs_line_width = Res.getDimen(R.dimen.theme_fs_line_width);
		// 分时区价格内边距
		theme_fs_bs5_text_padding = Res.getDimen(R.dimen.theme_fs_bs5_text_padding);

		clr_fs_floatRect_bgn = Res.getColor(R.color.clr_fs_floatRect_bgn);

		// 分时区高度，包括成交量区
		theme_fs_height = Res.getDimen(R.dimen.theme_fs_height);

		// 分时框与成交量的间隙
		theme_fs_regional_gap = Res.getDimen(R.dimen.theme_fs_regional_gap);

		// 分时区底部时间高度
		theme_fs_time_height = Res.getDimen(R.dimen.theme_fs_time_height);

		// 买卖五档 标题宽、高、字体大小
		theme_fs_bs5_title_width = AutoUtils.getPercentWidthSize(Res
		        .getDimen(R.dimen.theme_fs_bs5_title_width));
		theme_fs_bs5_title_height = Res
		        .getDimen(R.dimen.theme_fs_bs5_title_height);
		theme_fs_bs5_title_textsize = Res
		        .getDimen(R.dimen.theme_fs_bs5_title_textsize);

		// 买卖五档 主体宽、高、字体大小
		theme_fs_bs5_body_width = Res.getDimen(R.dimen.theme_fs_bs5_body_width);
		theme_fs_bs5_body_height = Res
		        .getDimen(R.dimen.theme_fs_bs5_body_height);
		theme_fs_bs5_body_textsize = Res
		        .getDimen(R.dimen.theme_fs_bs5_body_textsize);
		theme_fs_bs5_sjl_textsize = Res
		        .getDimen(R.dimen.theme_fs_bs5_sjl_textsize);
		theme_fs_bs5_btn_width = Res.getDimen(R.dimen.theme_fs_bs5_btn_width);
		theme_fs_bs5_btn_height = Res.getDimen(R.dimen.theme_fs_bs5_btn_height);

		theme_fs_mx_title_height = Res
		        .getDimen(R.dimen.theme_fs_mx_title_height);
		theme_fs_mx_textsize = Res.getDimen(R.dimen.theme_fs_mx_textsize);

		// 浮动框 宽、高、字体大小
		theme_fs_floatRect_height = Res
		        .getDimen(R.dimen.theme_fs_floatRect_height);
		theme_fs_floatRect_width = Res
		        .getDimen(R.dimen.theme_fs_floatRect_width);
		theme_fs_floatRect_textsize = Res
		        .getDimen(R.dimen.theme_fs_floatRect_textsize);

		// 横屏标题高
		theme_fs_landscape_title_height = Res
		        .getDimen(R.dimen.theme_fs_landscape_title_height);
		theme_fs_landscape_bs5_width = Res
		        .getDimen(R.dimen.theme_fs_landscape_bs5_width);
		theme_fs_landscape_title_stocksize = Res
		        .getDimen(R.dimen.theme_fs_landscape_title_stocksize);
		theme_fs_landscape_title_btn_height = Res
		        .getDimen(R.dimen.theme_fs_landscape_title_btn_height);
		theme_fs_landscape_title_btn_width = Res
		        .getDimen(R.dimen.theme_fs_landscape_title_btn_width);

		// 标题区
		theme_fs_title_height = Res.getDimen(R.dimen.theme_fs_title_height);
		theme_fs_title_leftrect_width = Res
		        .getDimen(R.dimen.theme_fs_title_leftrect_width);
		theme_fs_title_addstock_btn_width = Res
		        .getDimen(R.dimen.theme_fs_title_addstock_btn_width);

		theme_fs_title_zjcj_textsize = Res
		        .getDimen(R.dimen.theme_fs_title_zjcj_textsize);
		theme_fs_title_zdf_textsize = Res
		        .getDimen(R.dimen.theme_fs_title_zdf_textsize);
		theme_fs_title_right_textsize = Res
		        .getDimen(R.dimen.theme_fs_title_right_textsize);
		theme_fs_title_right_hkzs_textsize = Res
		        .getDimen(R.dimen.theme_fs_title_right_hkzs_textsize);

		clr_fs_title_leftrect_ping = Res
		        .getColor(R.color.clr_fs_title_leftrect_ping);
		clr_fs_title_leftrect_bgn = Res
		        .getColor(R.color.clr_fs_title_leftrect_bgn);
		// ************************************************************
	}
}
