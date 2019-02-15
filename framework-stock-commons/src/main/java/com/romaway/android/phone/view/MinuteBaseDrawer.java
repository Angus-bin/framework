package com.romaway.android.phone.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;

import com.romaway.common.android.base.Res;
import com.romaway.common.android.phone.ISubTabView;
import com.romaway.android.phone.R;
import com.romaway.android.phone.utils.DrawUtils;
import com.romaway.common.protocol.util.KFloat;
import com.romaway.common.protocol.util.KFloatUtils;

/**
 * 画盘口区
 * 
 * @author qinyn
 * 
 */
public class MinuteBaseDrawer
{
	// 公共颜色 黄、红、绿
	private static int clr_fs_yellow = MinuteViewTheme.clr_fs_yellow;
	private static int clr_fs_red = MinuteViewTheme.clr_fs_red;
	private static int clr_fs_green = MinuteViewTheme.clr_fs_green;
	private static int clr_fs_blue = MinuteViewTheme.clr_fs_blue;

	// 买卖五档
	private static int theme_fs_bs5_btn_width = MinuteViewTheme.theme_fs_bs5_btn_width;
	private static int theme_fs_bs5_btn_height = MinuteViewTheme.theme_fs_bs5_btn_height;
	private static int theme_fs_bs5_textsize = MinuteViewTheme.theme_fs_bs5_body_textsize;
	private static int theme_fs_bs5_sjl_textsize = MinuteViewTheme.theme_fs_bs5_sjl_textsize;
	/** 线条颜色 **/
	private static int clr_fs_line = MinuteViewTheme.clr_fs_line;
	/** 线条宽度 **/
	private static int theme_fs_line_width = MinuteViewTheme.theme_fs_line_width;

	/** 0为盘口，1为详情，2为明细 **/
	// private static int bs5_index = 0;
	private static int bs5_index;
	/** 浮动框背景色 **/
	private static int clr_fs_floatRect_bgn = MinuteViewTheme.clr_fs_floatRect_bgn;
	private static ISubTabView subView;

	public static void setSubView(ISubTabView subTabView)
	{
		subView = subTabView;
	}

	/** 画五档框顶部（盘，详，明） */
	public static void drawBS5Top(Canvas canvas, Rect rect, boolean event_t,
	        int eventX, int eventY)
	{
		// ****老的盘祥明**********************************************
		/*
		 * Paint[] p = new Paint[] { new Paint(), new Paint(), new Paint(), new
		 * Paint(), }; int x0 = rect.left + 5 + theme_fs_bs5_btn_width; int x1 =
		 * x0 + 5 + theme_fs_bs5_btn_width; int x2 = x1 + 5 +
		 * theme_fs_bs5_btn_width; int y0 = rect.top + theme_fs_bs5_btn_height +
		 * 5;
		 * 
		 * RectF rf[] = new RectF[3]; rf[0] = new RectF(rect.left + 5, rect.top
		 * + 5, x0, y0); rf[1] = new RectF(x0 + 5, rect.top + 5, x1, y0); rf[2]
		 * = new RectF(x1 + 5, rect.top + 5, x2, y0); float cx0 =
		 * rf[0].centerX(); float cx1 = rf[1].centerX(); float cx2 =
		 * rf[2].centerX(); // float cy = rf[0].bottom - 10; float cy =
		 * (rf[0].bottom - rf[0].centerY()) / 2 + rf[0].centerY();
		 * 
		 * p[2].setColor(Color.WHITE); p[2].setTextAlign(Paint.Align.CENTER);
		 * p[2].setTextSize(theme_fs_bs5_textsize);
		 * p[2].setFlags(Paint.ANTI_ALIAS_FLAG);// 去锯齿
		 * 
		 * p[1].setColor(clr_fs_red); p[1].setFlags(Paint.ANTI_ALIAS_FLAG);//
		 * 去锯齿 p[1].setAntiAlias(true); p[1].setFilterBitmap(true);
		 * 
		 * if (rf[0].contains(eventX, eventY)) { bs5_index = 0; } else if
		 * (rf[1].contains(eventX, eventY)) { bs5_index = 1; } else if
		 * (rf[2].contains(eventX, eventY)) { bs5_index = 2; } else if (event_t)
		 * { bs5_index++; if (bs5_index > 2) { bs5_index = 0; } }
		 * 
		 * switch (bs5_index) { case 0:// 盘 canvas.save();
		 * canvas.drawRoundRect(rf[0], 3.0f, 3.0f, p[1]); canvas.restore();
		 * break; case 1:// 详 canvas.save(); canvas.drawRoundRect(rf[1], 3.0f,
		 * 3.0f, p[1]); canvas.restore(); break; case 2:// 明 canvas.save();
		 * canvas.drawRoundRect(rf[2], 3.0f, 3.0f, p[1]); canvas.restore();
		 * break; } canvas.save(); canvas.drawText("盘", cx0, cy, p[2]);
		 * canvas.drawText("详", cx1, cy, p[2]); canvas.drawText("明", cx2, cy,
		 * p[2]); canvas.restore();
		 */

		// ****新的盘祥明****************************************
		Paint[] p = new Paint[] { new Paint(), new Paint(), new Paint(),
		        new Paint(), };
		int width = rect.width() / 3;
		int x0 = rect.left + width;

		Rect r[] = new Rect[3];
		r[0] = new Rect(rect.left, rect.top, x0, rect.bottom);
		r[1] = new Rect(x0, rect.top, x0 + width, rect.bottom);
		r[2] = new Rect(x0 + width, rect.top, x0 + width * 2, rect.bottom);
		float cx0 = r[0].centerX() - 2;
		float cx1 = r[1].centerX() - 2;
		float cx2 = r[2].centerX() - 2;
		// float cy = rf[0].bottom - 10;
		float cy = (r[0].bottom - r[0].centerY()) / 2 + r[0].centerY();

		p[2].setColor(Color.WHITE);
		p[2].setTextAlign(Paint.Align.CENTER);
		p[2].setTextSize(theme_fs_bs5_textsize);
		p[2].setFlags(Paint.ANTI_ALIAS_FLAG);// 去锯齿

		p[1].setColor(0xFF4D4D4D);
		p[1].setFlags(Paint.ANTI_ALIAS_FLAG);// 去锯齿
		p[1].setAntiAlias(true);
		p[1].setFilterBitmap(true);

		p[3].setColor(MinuteViewTheme.clr_fs_bgn);
		p[3].setFlags(Paint.ANTI_ALIAS_FLAG);// 去锯齿
		p[3].setAntiAlias(true);
		p[3].setFilterBitmap(true);

		if (r[0].contains(eventX, eventY))
		{
			bs5_index = 0;
		} else if (r[1].contains(eventX, eventY))
		{
			bs5_index = 1;
		} else if (r[2].contains(eventX, eventY))
		{
			bs5_index = 2;
		} else if (event_t)
		{
			bs5_index++;
			if (bs5_index > 2)
			{
				bs5_index = 0;
			}
		}

		switch (bs5_index)
		{
			case 0:// 盘
				canvas.save();
				canvas.drawRect(r[0], p[1]);
				canvas.drawRect(r[1], p[3]);
				canvas.drawRect(r[2], p[3]);
				canvas.restore();
				break;
			case 1:// 详
				canvas.save();
				canvas.drawRect(r[0], p[3]);
				canvas.drawRect(r[1], p[1]);
				canvas.drawRect(r[2], p[3]);
				canvas.restore();
				break;
			case 2:// 明
				canvas.save();
				canvas.drawRect(r[0], p[3]);
				canvas.drawRect(r[1], p[3]);
				canvas.drawRect(r[2], p[1]);
				canvas.restore();
				break;
		}
		canvas.save();
		canvas.drawText("五档", cx0, cy, p[2]);
		canvas.drawText("盘口", cx1, cy, p[2]);
		canvas.drawText("明细", cx2, cy, p[2]);
		canvas.restore();

	}

	/** 画盘口买卖五档 */
	public static void drawBS5Rect(Canvas canvas, Rect rect, KFloat kfZrsp,
	        KFloat[] kfBjg_s, KFloat[] kfBsl_s, KFloat[] kfSjg_s,
	        KFloat[] kfSsl_s, int[] nTime_s, int eventX, int eventY,
	        String stockName, String stockCode, byte bSuspended)
	{
		Paint[] p = new Paint[] { new Paint(), new Paint(), new Paint(),
		        new Paint(), };
		if (bs5_index == 0)
		{
			int height = rect.height() / 10;
			int lineY = rect.top + height * 5 + 3;
			p[3].setColor(0xff6f6f6f);
			// p[3].setStrokeWidth(theme_fs_line_width);
			canvas.drawLine(rect.left, lineY, rect.right, lineY, p[3]);

			String[] sale = Res.getStringArray(R.array.hq_stock_detail_sale_five_speed);
			String[] buy = Res.getStringArray(R.array.hq_stock_detail_buy_five_speed);
			int leftX = rect.left + 5;
			int y0 = rect.top + height - 7;
			int y1 = rect.top + height * 2 - 7;
			int y2 = rect.top + height * 3 - 7;
			int y3 = rect.top + height * 4 - 7;
			int y4 = rect.top + height * 5 - 7;
			int y5 = rect.top + height * 6 - 7;
			int y6 = rect.top + height * 7 - 7;
			int y7 = rect.top + height * 8 - 7;
			int y8 = rect.top + height * 9 - 7;
			int y9 = rect.top + height * 10 - 7;

			p[0].setTextSize(theme_fs_bs5_textsize);
			p[1].setTextSize(theme_fs_bs5_textsize);
			p[2].setTextSize(theme_fs_bs5_textsize);
			p[3].setTextSize(theme_fs_bs5_textsize);

			p[0].setColor(Color.WHITE);
			p[0].setAntiAlias(true);
			p[0].setFilterBitmap(true);
			p[0].setTextAlign(Paint.Align.LEFT);
			canvas.save();
			canvas.drawText(sale[0], leftX, y0, p[0]);
			canvas.drawText(sale[1], leftX, y1, p[0]);
			canvas.drawText(sale[2], leftX, y2, p[0]);
			canvas.drawText(sale[3], leftX, y3, p[0]);
			canvas.drawText(sale[4], leftX, y4, p[0]);

			canvas.drawText(buy[4], leftX, y5, p[0]);
			canvas.drawText(buy[3], leftX, y6, p[0]);
			canvas.drawText(buy[2], leftX, y7, p[0]);
			canvas.drawText(buy[1], leftX, y8, p[0]);
			canvas.drawText(buy[0], leftX, y9, p[0]);
			canvas.restore();

			float length = p[1].measureText(sale[0]);
			int priceX = leftX + (int) length + 5;
			p[1].setColor(clr_fs_red);
			p[1].setAntiAlias(true);
			p[1].setFilterBitmap(true);
			p[2].setColor(clr_fs_green);
			p[2].setAntiAlias(true);
			p[2].setFilterBitmap(true);
			p[3].setColor(Color.WHITE);
			p[3].setAntiAlias(true);
			p[3].setFilterBitmap(true);

			// 卖价格
			int zd_index = 0;
			int[] sy = new int[] { y0, y1, y2, y3, y4 };
			int[] by = new int[] { y5, y6, y7, y8, y9 };
			if (bSuspended == 1)
			{
				for (int i = 0; i < 5; i++)
				{
					canvas.drawText("---", priceX, sy[i], p[3]);
					canvas.drawText("---", priceX, by[i], p[3]);
				}
			} else
			{

				for (int i = 0; i < 5; i++)
				{
					canvas.save();
					/**
					 * 解决行情》港股》香港指数 点击一条数据进入分时界面，然后横屏，程序闪退问题。
					 */
					if (i>=kfSjg_s.length){
						canvas.drawText("---", priceX, sy[i],p[3]);
					}
					else{
						switch (KFloatUtils.compare(kfSjg_s[i], kfZrsp))
						{
							case -1:
								zd_index = 2;
								break;
							case 0:
								zd_index = 3;
								break;
							case 1:
								zd_index = 1;
								break;
						}
						canvas.drawText(kfSjg_s[i].toString(), priceX, sy[i],
						        kfSjg_s[i].toString().equals("---") ? p[3]
						                : p[zd_index]);
					}
					
					
					canvas.restore();
				}

				// 买价格
				for (int i = 0; i < 5; i++)
				{
					canvas.save();
					/**
					 * 解决行情》港股》香港指数 点击一条数据进入分时界面，然后横屏，程序闪退问题。
					 */
					if (i>=kfSjg_s.length){
						canvas.drawText("---", priceX, by[i],p[3]);
					}else{
					switch (KFloatUtils.compare(kfBjg_s[i], kfZrsp))
					{
						case -1:
							zd_index = 2;
							break;
						case 0:
							zd_index = 3;
							break;
						case 1:
							zd_index = 1;
							break;
					}
					canvas.drawText(kfBjg_s[i].toString(), priceX, by[i],
					        kfBjg_s[i].toString().equals("---") ? p[3]
					                : p[zd_index]);
					}
					
					
					canvas.restore();
				}
			}

			p[2].setTextAlign(Paint.Align.RIGHT);
			p[2].setColor(clr_fs_yellow);
			int saleY = rect.right - 5;

			if (bSuspended == 1)
			{
				canvas.save();
				canvas.drawText("---".toString(), saleY, y0, p[3]);
				canvas.drawText("---".toString(), saleY, y1, p[3]);
				canvas.drawText("---".toString(), saleY, y2, p[3]);
				canvas.drawText("---".toString(), saleY, y3, p[3]);
				canvas.drawText("---".toString(), saleY, y4, p[3]);
				canvas.drawText("---".toString(), saleY, y5, p[3]);
				canvas.drawText("---".toString(), saleY, y6, p[3]);
				canvas.drawText("---".toString(), saleY, y7, p[3]);
				canvas.drawText("---".toString(), saleY, y8, p[3]);
				canvas.drawText("---".toString(), saleY, y9, p[3]);
				canvas.restore();
			} else
			{
				canvas.save();
				// 卖数量
				int[] ys = new int[] { y0, y1, y2, y3, y4 };
				int[] yb = new int[] { y5, y6, y7, y8, y9 };
				p[3].setTextAlign(Paint.Align.RIGHT);
				for (int i = 0; i < 5; i++)
				{
					/**
					 * 解决行情》港股》香港指数 点击一条数据进入分时界面，然后横屏，程序闪退问题。
					 */
					if (i>=kfSjg_s.length){
						canvas.drawText("---", saleY, ys[i],p[3]);
						canvas.drawText("---", saleY, yb[i],p[3]);
					}else{
					canvas.drawText(kfSsl_s[i].toString(), saleY, ys[i],
					        kfSsl_s[i].toString().equals("---") ? p[3] : p[2]);
					canvas.drawText(kfBsl_s[i].toString(), saleY, yb[i],
					        kfBsl_s[i].toString().equals("---") ? p[3] : p[2]);
					}
				}
				canvas.restore();
			}
			
		}
	}
	
	/** 画盘口买卖五档 */
	public static void drawBS1Rect(Canvas canvas, Rect rect, KFloat kfZrsp,
	        KFloat[] kfBjg_s, KFloat[] kfBsl_s, KFloat[] kfSjg_s,
	        KFloat[] kfSsl_s, int[] nTime_s, int eventX, int eventY,
	        String stockName, String stockCode, byte bSuspended)
	{
		Paint[] p = new Paint[] { new Paint(), new Paint(), new Paint(),
		        new Paint(), };
		if (bs5_index == 0)
		{
			int height = rect.height() / 10;
			int lineY = rect.top + height * 5 + 3;
			p[3].setColor(0xff6f6f6f);
			// p[3].setStrokeWidth(theme_fs_line_width);
			canvas.drawLine(rect.left, lineY, rect.right, lineY, p[3]);

			String[] sale = Res.getStringArray(R.array.hq_stock_detail_sale_five_speed);
			String[] buy = Res.getStringArray(R.array.hq_stock_detail_buy_five_speed);
			int leftX = rect.left + 5;
			int y0 = rect.top + height - 7;
			int y1 = rect.top + height * 2 - 7;
			int y2 = rect.top + height * 3 - 7;
			int y3 = rect.top + height * 4 - 7;
			int y4 = rect.top + height * 5 - 7;
			int y5 = rect.top + height * 6 - 7;
			int y6 = rect.top + height * 7 - 7;
			int y7 = rect.top + height * 8 - 7;
			int y8 = rect.top + height * 9 - 7;
			int y9 = rect.top + height * 10 - 7;

			p[0].setTextSize(theme_fs_bs5_textsize);
			p[1].setTextSize(theme_fs_bs5_textsize);
			p[2].setTextSize(theme_fs_bs5_textsize);
			p[3].setTextSize(theme_fs_bs5_textsize);

			p[0].setColor(Color.WHITE);
			p[0].setAntiAlias(true);
			p[0].setFilterBitmap(true);
			p[0].setTextAlign(Paint.Align.LEFT);
			canvas.save();
			canvas.drawText(sale[0], leftX, y0, p[0]);
			canvas.drawText(sale[1], leftX, y1, p[0]);
			canvas.drawText(sale[2], leftX, y2, p[0]);
			canvas.drawText(sale[3], leftX, y3, p[0]);
			canvas.drawText(sale[4], leftX, y4, p[0]);

			canvas.drawText(buy[4], leftX, y5, p[0]);
			canvas.drawText(buy[3], leftX, y6, p[0]);
			canvas.drawText(buy[2], leftX, y7, p[0]);
			canvas.drawText(buy[1], leftX, y8, p[0]);
			canvas.drawText(buy[0], leftX, y9, p[0]);
			canvas.restore();

			float length = p[1].measureText(sale[0]);
			int priceX = leftX + (int) length + 5;
			p[1].setColor(clr_fs_red);
			p[1].setAntiAlias(true);
			p[1].setFilterBitmap(true);
			p[2].setColor(clr_fs_green);
			p[2].setAntiAlias(true);
			p[2].setFilterBitmap(true);
			p[3].setColor(Color.WHITE);
			p[3].setAntiAlias(true);
			p[3].setFilterBitmap(true);

			// 卖价格
			int zd_index = 0;
			int[] sy = new int[] { y0, y1, y2, y3, y4 };
			int[] by = new int[] { y5, y6, y7, y8, y9 };
			if (bSuspended == 1)
			{
				for (int i = 0; i < 5; i++)
				{
					canvas.drawText("---", priceX, sy[i], p[3]);
					canvas.drawText("---", priceX, by[i], p[3]);
				}
			} else
			{

				for (int i = 0; i < 4; i++)
				{
					canvas.save();
					/**
					 * 解决行情》港股》香港指数 点击一条数据进入分时界面，然后横屏，程序闪退问题。
					 */
					if (i>=kfSjg_s.length){
						canvas.drawText("---", priceX, sy[i],p[3]);
						canvas.drawText("---", priceX, sy[0],p[3]);
					}
					else{
						switch (KFloatUtils.compare(kfSjg_s[i], kfZrsp))
						{
							case -1:
								zd_index = 2;
								break;
							case 0:
								zd_index = 3;
								break;
							case 1:
								zd_index = 1;
								break;
						}
						//显示港股通一档价格，卖一
						if(kfSjg_s.length == 1){
							canvas.drawText(kfSjg_s[i].toString(), priceX, sy[4],
							        kfSjg_s[i].toString().equals("---") ? p[3]
							                : p[zd_index]);
						}
//						canvas.drawText(kfSjg_s[i].toString(), priceX, sy[i],
//						        kfSjg_s[i].toString().equals("---") ? p[3]
//						                : p[zd_index]);
					}
					
					
					canvas.restore();
				}

				// 买价格
				for (int i = 0; i < 5; i++)
				{
					canvas.save();
					/**
					 * 解决行情》港股》香港指数 点击一条数据进入分时界面，然后横屏，程序闪退问题。
					 */
					if (i>=kfSjg_s.length){
						canvas.drawText("---", priceX, by[i],p[3]);
					}else{
					switch (KFloatUtils.compare(kfBjg_s[i], kfZrsp))
					{
						case -1:
							zd_index = 2;
							break;
						case 0:
							zd_index = 3;
							break;
						case 1:
							zd_index = 1;
							break;
					}
					canvas.drawText(kfBjg_s[i].toString(), priceX, by[i],
					        kfBjg_s[i].toString().equals("---") ? p[3]
					                : p[zd_index]);
					}
					
					
					canvas.restore();
				}
			}

			p[2].setTextAlign(Paint.Align.RIGHT);
			p[2].setColor(clr_fs_yellow);
			int saleY = rect.right - 5;

			if (bSuspended == 1)
			{
				canvas.save();
				canvas.drawText("---".toString(), saleY, y0, p[3]);
				canvas.drawText("---".toString(), saleY, y1, p[3]);
				canvas.drawText("---".toString(), saleY, y2, p[3]);
				canvas.drawText("---".toString(), saleY, y3, p[3]);
				canvas.drawText("---".toString(), saleY, y4, p[3]);
				canvas.drawText("---".toString(), saleY, y5, p[3]);
				canvas.drawText("---".toString(), saleY, y6, p[3]);
				canvas.drawText("---".toString(), saleY, y7, p[3]);
				canvas.drawText("---".toString(), saleY, y8, p[3]);
				canvas.drawText("---".toString(), saleY, y9, p[3]);
				canvas.restore();
			} else
			{
				canvas.save();
				// 卖数量
				int[] ys = new int[] { y0, y1, y2, y3, y4 };
				int[] yb = new int[] { y5, y6, y7, y8, y9 };
				p[3].setTextAlign(Paint.Align.RIGHT);
				for (int i = 0; i < 4; i++)
				{
					/**
					 * 解决行情》港股》香港指数 点击一条数据进入分时界面，然后横屏，程序闪退问题。
					 */
					if (i>=kfSjg_s.length){
						canvas.drawText("---", saleY, ys[i],p[3]);
						canvas.drawText("---", saleY, yb[i],p[3]);
						canvas.drawText("---", saleY, ys[0],p[3]);
						canvas.drawText("---", saleY, yb[4],p[3]);
					}else{
						//显示港股通一档数量，卖一
						if(kfSjg_s.length == 1){
							canvas.drawText(kfSsl_s[i].toString(), saleY, ys[4],
							        kfSsl_s[i].toString().equals("---") ? p[3] : p[2]);
						}else{
							canvas.drawText(kfSsl_s[i].toString(), saleY, ys[i],
							        kfSsl_s[i].toString().equals("---") ? p[3] : p[2]);
						}
//					canvas.drawText(kfSsl_s[i].toString(), saleY, ys[i],
//					        kfSsl_s[i].toString().equals("---") ? p[3] : p[2]);
					canvas.drawText(kfBsl_s[i].toString(), saleY, yb[i],
					        kfBsl_s[i].toString().equals("---") ? p[3] : p[2]);
					}
				}
				canvas.restore();
			}
		}
	}

	/** 详情界面 */
	public static void drawXQRect(Canvas canvas, Rect rect, KFloat kfZjcj,
	        int[] nCjjj_s, KFloat kfZd, KFloat kfZdf, KFloat kfCjss,
	        int[] nCjss_s, String kfHsl, KFloat kfLb, KFloat kfBuyp,
	        KFloat kfSelp, KFloat kfZgcj, KFloat kfZdcj, KFloat kfZrsp,
	        int fsDataWCount, int[] nTime_s, byte bSuspended)
	{
		Paint[] p = new Paint[] { new Paint(), new Paint(), new Paint(),
		        new Paint(), new Paint() };
		p[0].setAntiAlias(true);//设置是否抗锯齿
		p[0].setFilterBitmap(true);
		p[1].setAntiAlias(true);
		p[1].setFilterBitmap(true);
		p[2].setAntiAlias(true);
		p[2].setFilterBitmap(true);
		p[3].setAntiAlias(true);
		p[3].setFilterBitmap(true);
		p[4].setAntiAlias(true);
		p[4].setFilterBitmap(true);
		if (bs5_index == 1)
		{
			int height = rect.height() / 13;

			int y0 = rect.top + height - 5;
			int y1 = rect.top + height * 2 - 5;
			int y2 = rect.top + height * 3 - 5;
			int y3 = rect.top + height * 4 - 5;
			int y4 = rect.top + height * 5 - 5;
			int y5 = rect.top + height * 6 - 5;
			int y6 = rect.top + height * 7 - 5;
			int y7 = rect.top + height * 8 - 5;
			int y8 = rect.top + height * 9 - 5;
			int y9 = rect.top + height * 10 - 5;
			int y10 = rect.top + height * 11 - 5;
			int y11 = rect.top + height * 12 - 5;
			int y12 = rect.top + height * 13 - 5;

			// 画俩条虚线
			p[0].setStrokeWidth(theme_fs_line_width);//设置画笔的笔触宽度
			p[0].setColor(clr_fs_line);
			float[] line1 = DrawUtils.getDottedLine(rect.left, y5 + 5, rect,
			        true);
			float[] line2 = DrawUtils.getDottedLine(rect.left, y9 + 5, rect,
			        true);
			canvas.save();
			canvas.drawLines(line1, p[0]);
			canvas.drawLines(line2, p[0]);
			canvas.restore();
			int left = rect.left + 5;

			p[0].setTextSize(theme_fs_bs5_textsize);
			p[0].setTextAlign(Paint.Align.LEFT);
			p[0].setColor(Color.WHITE);
			canvas.save();
			canvas.drawText("现价:", left, y0, p[0]);
			canvas.drawText("均价:", left, y1, p[0]);
			canvas.drawText("涨跌:", left, y2, p[0]);
			canvas.drawText("涨幅:", left, y3, p[0]);
			canvas.drawText("总手:", left, y4, p[0]);
			canvas.drawText("现手:", left, y5, p[0]);
			canvas.drawText("换手:", left, y6, p[0]);
			canvas.drawText("量比:", left, y7, p[0]);
			canvas.drawText("内盘:", left, y8, p[0]);
			canvas.drawText("外盘:", left, y9, p[0]);
			canvas.drawText("最高:", left, y10, p[0]);
			canvas.drawText("最低:", left, y11, p[0]);
			canvas.drawText("昨收:", left, y12, p[0]);
			canvas.restore();

			p[1].setTextSize(theme_fs_bs5_textsize);
			p[2].setTextSize(theme_fs_bs5_textsize);
			p[3].setTextSize(theme_fs_bs5_textsize);
			p[4].setTextSize(theme_fs_bs5_textsize);
			p[0].setColor(clr_fs_blue);
			p[1].setColor(clr_fs_green);
			p[2].setColor(clr_fs_red);
			p[3].setColor(Color.WHITE);
			p[4].setColor(clr_fs_yellow);
			p[0].setTextAlign(Paint.Align.RIGHT);
			p[1].setTextAlign(Paint.Align.RIGHT);
			p[2].setTextAlign(Paint.Align.RIGHT);
			p[3].setTextAlign(Paint.Align.RIGHT);
			p[4].setTextAlign(Paint.Align.RIGHT);

			int right = rect.right - 5;
			int zd_index = 0;

			if (bSuspended == 1)
			{// 停牌
				canvas.save();
				canvas.drawText("---", right, y0, p[3]);
				canvas.drawText("---", right, y1, p[3]);
				canvas.drawText("---", right, y2, p[3]);
				canvas.drawText("---", right, y3, p[3]);
				canvas.drawText("---", right, y4, p[3]);
				canvas.drawText("---", right, y5, p[3]);
				canvas.drawText("---", right, y6, p[3]);
				canvas.drawText("---", right, y7, p[3]);
				canvas.drawText("---", right, y8, p[3]);
				canvas.drawText("---", right, y9, p[3]);
				canvas.drawText("---", right, y10, p[3]);
				canvas.drawText("---", right, y11, p[3]);
				canvas.drawText("---", right, y12, p[3]);
				canvas.restore();
			} else
			{
				canvas.save();
				// 最新 现价
				switch (KFloatUtils.compare(kfZjcj, kfZrsp))
				{
					case -1:
						zd_index = 1;
						break;
					case 0:
						zd_index = 3;
						break;
					case 1:
						zd_index = 2;
						break;
				}
				canvas.drawText(kfZjcj.toString(), right, y0, p[zd_index]);

				KFloat s = new KFloat();
				int wCount = fsDataWCount - 1;
				// 均价
				if (nCjjj_s == null)
				{
					canvas.drawText("---", right, y1, p[zd_index]);
					// return;
				} else
				{
					switch (KFloatUtils
					        .compare(s.init(nCjjj_s[wCount]), kfZrsp))
					{
						case -1:
							zd_index = 1;
							break;
						case 0:
							zd_index = 3;
							break;
						case 1:
							zd_index = 2;
							break;
					}
					canvas.drawText(s.init(nCjjj_s[wCount]).toString(), right,
					        y1, p[zd_index]);
				}
				// 涨跌
				switch (KFloatUtils.compare(kfZjcj, kfZrsp))
				{
					case -1:
						zd_index = 1;
						break;
					case 0:
						zd_index = 3;
						break;
					case 1:
						zd_index = 2;
						break;
				}
				canvas.drawText(
				        kfZd.toString().equals("---") ? "0" : kfZd.toString(),
				        right, y2, p[zd_index]);
				// 涨幅
				if (kfZdf == null)
				{
					canvas.drawText("0.00%", right, y3, p[zd_index]);
				} else
				{
//					String zdf = s.init(nZdf_s[wCount]).toString("%");
//					canvas.drawText(zdf.equals("---%") ? "0.00%" : zdf, right,
//					        y3, p[zd_index]);
					canvas.drawText(kfZdf.toString("%"), right,
					        y3, p[zd_index]);
					
				}
				// 总手
				canvas.drawText(kfCjss.toString(), right, y4, p[4]);
				// 现手
				if (nCjss_s == null)
					canvas.drawText("---", right, y5, p[4]);
				else
					canvas.drawText(s.init(nCjss_s[nCjss_s.length - 1])
					        .toString(), right, y5, p[4]);

				// 換手
				p[0].setColor(Color.WHITE);
				if (kfHsl == null || kfHsl.equals("---") || kfHsl.equals(""))
				{
					canvas.drawText("0.00%", right, y6, p[0]);
				} else
				{
					canvas.drawText(kfHsl + "%", right, y6, p[0]);
				}
				// 量比
				if (kfLb.toString().compareTo("1") > 1)
				{
					p[0].setColor(clr_fs_red);
				} else if (kfLb.toString().compareTo("1") < 1)
				{
					p[0].setColor(clr_fs_green);
				} else
				{
					p[0].setColor(Color.WHITE);
				}
				canvas.drawText(kfLb.toString(), right, y7, p[0]);
				p[0].setColor(clr_fs_yellow);
				// 内盘=卖出
				canvas.drawText(kfSelp.toString(), right, y8, p[1]);
				// 外盘=买入
				canvas.drawText(kfBuyp.toString(), right, y9, p[2]);
				// 最高
				switch (KFloatUtils.compare(kfZgcj, kfZrsp))
				{
					case -1:
						zd_index = 1;
						break;
					case 0:
						zd_index = 3;
						break;
					case 1:
						zd_index = 2;
						break;
				}
				canvas.drawText(kfZgcj.toString(), right, y10, p[zd_index]);
				// 最低
				switch (KFloatUtils.compare(kfZdcj, kfZrsp))
				{
					case -1:
						zd_index = 1;
						break;
					case 0:
						zd_index = 3;
						break;
					case 1:
						zd_index = 2;
						break;
				}
				canvas.drawText(kfZdcj.toString(), right, y11, p[zd_index]);
				// 昨收
				canvas.drawText(kfZrsp.toString(), right, y12, p[3]);
				canvas.restore();
			}
		}
	}

	/** 明细界面 */
	public static void drawMXRect(Canvas canvas, Rect rect, int[][] mxData,
	        KFloat kfZrsp, int[] nTime_s)
	{

		if (bs5_index == 2)
		{
			Paint[] p = new Paint[] { new Paint(), new Paint(), new Paint(),
			        new Paint(), };
			if (mxData == null || mxData[0] == null)
			{
				return;
			}

			int mxTitleH = MinuteViewTheme.theme_fs_mx_title_height;
			p[0].setTextSize(MinuteViewTheme.theme_fs_mx_textsize);
			int y0 = rect.top - 5 + mxTitleH;
			p[0].setColor(Color.WHITE);
			// 顶部字体
			canvas.save();
			p[0].setTextAlign(Paint.Align.LEFT);
			canvas.drawText("时", rect.left + 5, y0, p[0]);
			p[0].setTextAlign(Paint.Align.CENTER);
			canvas.drawText("价", rect.centerX(), y0, p[0]);
			p[0].setTextAlign(Paint.Align.RIGHT);
			canvas.drawText("量", rect.right - 5, y0, p[0]);
			canvas.restore();

			// 顶部线
			p[0].setColor(clr_fs_line);
			p[0].setStrokeWidth(theme_fs_line_width);
			canvas.drawLine(rect.left, y0 + 5, rect.right, y0 + 5, p[0]);

			// 时间参数
			p[1].setTextSize(MinuteViewTheme.theme_fs_mx_textsize - 2);
			// p[1].setColor(clr_fs_yellow);
			p[1].setColor(Color.WHITE);
			p[1].setAntiAlias(true);
			p[1].setFilterBitmap(true);
			int mx_index = mxData[0].length;// 一共得到多少行数据
			int maxH = 17;// 每一行数据最大高度
			int rowCount = (rect.height() - mxTitleH) / maxH;// 最大可容纳多少行
			int left = rect.left + 5;
			// 价格参数
			p[2].setTextSize(MinuteViewTheme.theme_fs_mx_textsize - 2);
			p[2].setColor(clr_fs_red);
			p[2].setAntiAlias(true);
			p[2].setFilterBitmap(true);
			float prictX = p[2].measureText("00:00") + 5 + left; // 价格显示X坐标
			// 成交量参数
			p[3].setTextSize(MinuteViewTheme.theme_fs_mx_textsize - 2);
			p[3].setAntiAlias(true);
			p[3].setFilterBitmap(true);
			p[3].setTextAlign(Paint.Align.RIGHT);
			int right = rect.right - 5;
			// 数据显示区域
			if (mx_index <= rowCount)
			{//明细数据小于或等于显示区能容纳的数据
				if (mx_index == 0)
				{
					return;
				} else if (mx_index == 1)
				{// 在9.25时只有一笔成交的情况下，数据放在顶部
					int y = y0 + maxH + 10;
					// 时间
					canvas.drawText(DrawUtils.formatNTime(mxData[0][0]), left,
					        y, p[1]);
					// 价格
					KFloat kfZjcj = new KFloat(mxData[2][0]);
					switch (KFloatUtils.compare(kfZjcj, kfZrsp))
					{
						case -1:// 跌
							p[2].setColor(clr_fs_green);
							break;
						case 0:// 平
							p[2].setColor(Color.WHITE);
							break;
						case 1:// 红
							p[2].setColor(clr_fs_red);
							break;
					}
					canvas.drawText(kfZjcj.toString(), prictX, y, p[2]);

					// 成交量
					String sCjlb = String.valueOf((char) mxData[1][0]);
					KFloat kfCjss = new KFloat(mxData[3][0]);
					p[3].setColor(sCjlb.equals("B") ? clr_fs_red : clr_fs_green);
					canvas.drawText(kfCjss.toString(), right, y, p[3]);

					return;// 记得，不要往下走了
				}
				int rowH = (rect.height() - mxTitleH) / mx_index;
				int y1 = y0 + rowH;
				int index = mx_index-1;
				for (int index2=0; index >=0; index--,index2++)
				{
					int y = y1 + rowH * index2;
					// 时间
					canvas.drawText(DrawUtils.formatNTime(mxData[0][index]),
					        left, y, p[1]);

					// 价格
					KFloat kfZjcj = new KFloat(mxData[2][index]);
					switch (KFloatUtils.compare(kfZjcj, kfZrsp))
					{
						case -1:// 跌
							p[2].setColor(clr_fs_green);
							break;
						case 0:// 平
							p[2].setColor(Color.WHITE);
							break;
						case 1:// 红
							p[2].setColor(clr_fs_red);
							break;
					}
					canvas.drawText(kfZjcj.toString(), prictX, y, p[2]);

					// 成交量
					String sCjlb = String.valueOf((char) mxData[1][index]);
					KFloat kfCjss = new KFloat(mxData[3][index]);
					p[3].setColor(sCjlb.equals("B") ? clr_fs_red : clr_fs_green);
					canvas.drawText(kfCjss.toString(), right, y, p[3]);
				}
			} else
			{//明细数据条数大于显示区可容纳条数
				int i = mx_index - rowCount;
				int y1 = y0 + maxH;
				//for (int index = i; index < mx_index; index++)
				int index = mx_index - 1;
				for (int index2 = i; index >=i; index--,index2++)
				{
					int c = index2 - i;
					int y = y1 + maxH * c;
					// 时间
					canvas.drawText(DrawUtils.formatNTime(mxData[0][index]),
					        left, y, p[1]);
					// 价格
					KFloat kfZjcj = new KFloat(mxData[2][index]);
					switch (KFloatUtils.compare(kfZjcj, kfZrsp))
					{
						case -1:// 跌
							p[2].setColor(clr_fs_green);
							break;
						case 0:// 平
							p[2].setColor(Color.WHITE);
							break;
						case 1:// 红
							p[2].setColor(clr_fs_red);
							break;
					}
					canvas.drawText(kfZjcj.toString(), prictX, y, p[2]);

					// 成交量
					String sCjlb = String.valueOf((char) mxData[1][index]);
					KFloat kfCjss = new KFloat(mxData[3][index]);
					p[3].setColor(sCjlb.equals("B") ? clr_fs_red : clr_fs_green);
					canvas.drawText(kfCjss.toString(), right, y, p[3]);

				}
			}
		}
	}

	/** 画分时均线线的方法 */
	public static void drawFSLine(Canvas canvas, Paint p1, Path pathFS,
	        Path pathJX, int setData_success)
	{
		if (setData_success == 0)
		{
			return;
		}
		if (pathFS != null)
		{
			p1.setAntiAlias(true);
			p1.setFilterBitmap(true);
			p1.setStyle(Paint.Style.STROKE);// 不填充区域
			canvas.save();
			p1.setColor(Color.WHITE);
			canvas.drawPath(pathFS, p1);
			p1.setColor(clr_fs_yellow);
			canvas.drawPath(pathJX, p1);

			canvas.restore();
		}

	}

	/** 交易——画分时均线线的方法 */
	public static void drawFSLine_jy(Canvas canvas, Paint p1, Path pathFS,
	        Path pathJX, int setData_success)
	{
		if (setData_success == 0)
		{
			return;
		}
		if (pathFS != null)
		{
			p1.setAntiAlias(true);
			p1.setFilterBitmap(true);
			p1.setStyle(Paint.Style.STROKE);// 不填充区域
			canvas.save();
			p1.setStrokeWidth(2);
			p1.setColor(Color.BLUE);
			canvas.drawPath(pathFS, p1);
			p1.setColor(KLineTheme.A8);
			canvas.drawPath(pathJX, p1);
			canvas.restore();
		}
	}

	/** 成交量透明框 （在这上面画成交量） */
	public static void drawTpAmRect(Canvas canvas, Rect rect, Paint p2,
	        Path pathAM)
	{
		if (pathAM != null)
		{
			p2.setAntiAlias(true);
			p2.setFilterBitmap(true);
			p2.setStyle(Paint.Style.FILL_AND_STROKE);// 填充区域
			canvas.save();
			// p2.setColor(clr_fs_yellow);
			p2.setColor(0xaaFCEE21);
			canvas.drawPath(pathAM, p2);
			canvas.restore();
		}
	}

	/** 成交量透明框 （在这上面画成交量） */
	public static void drawTpAmRect(Canvas canvas, Rect rect,
	        Path[] pathAMColumns, int[] nZjcj_s, KFloat kfZrsp)
	{
		Paint p = new Paint();
		if (pathAMColumns != null && pathAMColumns.length != 0)
		{
			p.setAntiAlias(true);
			p.setFilterBitmap(true);
			p.setStyle(Paint.Style.FILL_AND_STROKE);// 填充区域
			canvas.save();
			// p2.setColor(clr_fs_yellow);
			p.setStrokeWidth(2);
			int length = pathAMColumns.length;
			for (int index = 0; index < length; index++)
			{
				setPathPaintColor(p, index, nZjcj_s, kfZrsp);
				if (pathAMColumns[index] != null)
					canvas.drawPath(pathAMColumns[index], p);
			}
			canvas.restore();
		}
	}

	private static Paint setPathPaintColor(Paint p, int index, int[] nZjcj_s,
	        KFloat kfZrsp)
	{
		if (index == 0)
		{
			KFloat kfZjcj = new KFloat(nZjcj_s[0]);
			switch (KFloatUtils.compare(kfZjcj, kfZrsp))
			{
				case -1:// 跌
					p.setColor(0xaa00A651);
					break;
				case 0:// 平
					p.setColor(0xaaEB2227);
					break;
				case 1:// 涨
					p.setColor(0xaaEB2227);
					break;
			}
		} else
		{
			int a1 = nZjcj_s[index];
			int a2 = nZjcj_s[index - 1];
			if (a1 > a2)
			{// 涨
				p.setColor(0xaaEB2227);
			} else if (a1 == a2)
			{// 平
				// p.setColor(0xaaFCEE21);
				p.setColor(0xaaEB2227);
			} else
			{// 跌
				p.setColor(0xaa00A651);
			}
		}
		return p;
	}

	/**
	 * 横屏画分时时间点
	 * 
	 * @param canvas
	 * @param rect
	 * @param p2
	 */
	// public static void drawFSTimePoint_ls(Canvas canvas, Rect rect, Paint p2)
	// {
	// p2 = new Paint();
	// p2.setAntiAlias(true);
	// p2.setFilterBitmap(true);
	// p2.setStyle(Paint.Style.STROKE);// 不填充区域
	// p2.setTextAlign(Paint.Align.LEFT);
	// p2.setColor(clr_fs_yellow);
	// p2.setTextSize(MinuteViewTheme.theme_fs_bs5_title_textsize);
	//
	// canvas.save();
	// canvas.drawText("9:30", 1, rect.bottom - 2, p2);
	// canvas.drawText("13:00", rect.width() / 2, rect.bottom + 1, p2);
	// canvas.restore();
	// p2.setTextAlign(Paint.Align.RIGHT);
	// canvas.save();
	// canvas.drawText("15:00", rect.right, rect.bottom + 1, p2);
	// canvas.restore();
	//
	// }

	/** 浮动框 */
	public static void drawFloatRect(Canvas canvas, Rect rect, int[] nTime_s,
	        int[] nZjcj_s, int[] nZdf_s, int[] nCjjj_s, int[] nCjss_s,
	        KFloat kfZrsp, boolean btn_press, int cursorIndex,
	        boolean needPaintCursor, int crossLine_disappear, Paint paint,
	        Paint p, Paint p2)
	{
		if (nTime_s == null)
		{
			return;
		} else if (btn_press)
		{
			return;
		} else if (crossLine_disappear == 1)
		{
			return;
		}
		if (needPaintCursor)
		{
			int x = rect.left + 2;
			int y = rect.height() / 5;
			Paint p1 = new Paint();
			p1.setColor(clr_fs_floatRect_bgn);
			p2.setTextAlign(Paint.Align.LEFT);
			// p2.setColor(clr_fs_yellow);
			p2.setColor(Color.WHITE);
			p2.setTextSize(MinuteViewTheme.theme_fs_floatRect_textsize);

			canvas.save();
			canvas.drawRect(rect, p1);
			canvas.drawRect(rect.left, rect.top, rect.right, rect.bottom, p1);
			canvas.drawLine(rect.left, rect.top, rect.right, rect.top, paint);
			canvas.drawLine(rect.left, rect.bottom, rect.right, rect.bottom,
			        paint);
			canvas.drawLine(rect.left, rect.top, rect.left, rect.bottom, paint);
			canvas.drawLine(rect.right, rect.top, rect.right, rect.bottom,
			        paint);

			canvas.drawText("时", x, y + rect.top - 2, p2);
			canvas.drawText("现", x, y * 2 + rect.top - 2, p2);
			canvas.drawText("幅", x, y * 3 + rect.top - 2, p2);
			canvas.drawText("均", x, y * 4 + rect.top - 2, p2);
			canvas.drawText("量", x, y * 5 + rect.top - 2, p2);
			canvas.restore();

			if (cursorIndex > nTime_s.length)
				return;
			int time = nTime_s[cursorIndex];
			int zjcj = nZjcj_s[cursorIndex];
			int zdf = nZdf_s[cursorIndex];
			int cjjj = nCjjj_s[cursorIndex];
			int cjss = nCjss_s[cursorIndex];
			KFloat s = new KFloat();
			canvas.save();
			p2.setTextAlign(Paint.Align.RIGHT);
			// p2.setColor(clr_fs_yellow);
			String newTime = DrawUtils.setFloatTime("" + time);
			canvas.drawText(newTime, rect.right, y + rect.top - 2, p2);

			// 现价、振幅
			if (s.init(zdf).toString().compareTo("0") > 0)
			{
				p2.setColor(clr_fs_red);
			} else if (s.init(zdf).toString().compareTo("0") == 0
			        || s.init(zdf).toString().compareTo("---") == 0)
			{
				p2.setColor(Color.WHITE);
			} else
			{
				p2.setColor(clr_fs_green);
			}
			s.init(zjcj);
			canvas.drawText(s.toString(), rect.right, y * 2 + rect.top - 2, p2);
			s.init(zdf);
			String s_zdf = s.toString().compareTo("---") == 0 ? "0.00%" : s
			        .toString("%");// 如果涨跌幅为0则输出0.00
			canvas.drawText(s_zdf, rect.right, y * 3 + rect.top - 2, p2);
			// 均价
			String average = kfZrsp.toString();
			if (s.init(cjjj).toString().compareTo(average) > 0)
			{
				p2.setColor(clr_fs_red);
			} else if (s.init(cjjj).toString().compareTo(average) == 0)
			{
				p2.setColor(Color.WHITE);
			} else
			{
				p2.setColor(clr_fs_green);
			}
			s.init(cjjj);
			canvas.drawText(s.toString(), rect.right, y * 4 + rect.top - 2, p2);

			s.init(cjss);// 现量
			String s_cjss = s.toString().compareTo("---") == 0 ? "0" : s
			        .toString();// 如果成交量为0则输出0
			p2.setColor(clr_fs_yellow);
			canvas.drawText(s_cjss, rect.right, y * 5 + rect.top - 2, p2);
			canvas.restore();
		}
	}

	/** 画十字游标 */
	public static void drawCrossLine(Canvas canvas, Rect avRect, Rect amRect,
	        boolean btn_press, boolean needPaintCursor,
	        int crossLine_disappear, float cursorX, float cursorY, Paint p1)
	{
		if (btn_press)
		{
			return;
		}
		if (needPaintCursor)
		{
			if (crossLine_disappear == 1)
			{
				return;
			}
			canvas.save();
			p1.setColor(MinuteViewTheme.clr_fs_crossline);
			// 分市区十字线
			canvas.drawLine(avRect.left + 1, cursorY, avRect.right, cursorY, p1);
			canvas.drawLine(cursorX, avRect.top + 1, cursorX, avRect.bottom, p1);
			// 成交量十字线
			if (amRect != null)
				canvas.drawLine(cursorX, amRect.top + 1, cursorX,
				        amRect.bottom, p1);
			canvas.restore();
		}
	}

	/** 画十字游标 */
	public static void drawJYMinCrossLine(Canvas canvas, Rect avRect,
	        Rect amRect, boolean btn_press, boolean needPaintCursor,
	        int crossLine_disappear, float cursorX, float cursorY, Paint p1)
	{
		if (btn_press)
		{
			return;
		}
		if (needPaintCursor)
		{
			if (crossLine_disappear == 1)
			{
				return;
			}
			canvas.save();
			p1.setColor(0xFFF6921D);
			// 分市区十字线
			canvas.drawLine(avRect.left + 1, cursorY, avRect.right, cursorY, p1);
			canvas.drawLine(cursorX, avRect.top + 1, cursorX, avRect.bottom, p1);
			// 成交量十字线
			if (amRect != null)
				canvas.drawLine(cursorX, amRect.top + 1, cursorX,
				        amRect.bottom, p1);
			canvas.restore();
		}
	}
	
	/**
	 * 画新股价格坐标。
	 * @param canvas
	 * @param avRect
	 * @param amRect
	 * @param kfZrsp
	 * @param p1
	 */
	public static void drawNewStockPrice(Canvas canvas, Rect avRect, Rect amRect,KFloat kfZrsp,
	        Paint p1)
	{
		KFloat maxPrice = new KFloat(kfZrsp.nValue+(int) Math.round(0.1 * kfZrsp.nDigit),kfZrsp.nDigit,kfZrsp.nUnit);
		
		KFloatUtils.add(maxPrice, kfZrsp);
		
		KFloat minPrice = new KFloat(kfZrsp.nValue-(int) Math.round(0.1 * kfZrsp.nDigit),kfZrsp.nDigit,kfZrsp.nUnit);

		// 分时区
		int x = avRect.left + 5;

		p1.setColor(clr_fs_red);
		p1.setTextSize(theme_fs_bs5_textsize);

		
		// 首行
		canvas.save();
		canvas.drawText(maxPrice.toString(), x, avRect.top
		        + theme_fs_bs5_textsize, p1);

		canvas.restore();
		// 第二行
		KFloat price1 = new KFloat();
		KFloatUtils.add(price1, kfZrsp, maxPrice);

		price1 = new KFloat(price1.nValue / 2, price1.nDigit, price1.nUnit);
		canvas.save();
		canvas.drawText(price1.toString(), x, avRect.top + avRect.height() / 4
		        + 5, p1);

		canvas.restore();
		// 第三行
		p1.setColor(Color.WHITE);
		canvas.save();
		canvas.drawText(kfZrsp.toString(), x, avRect.centerY(), p1);

		canvas.restore();
		// 第四行
		p1.setColor(clr_fs_green);
		KFloat price2 = new KFloat();

		KFloatUtils.add(price2, kfZrsp, minPrice);
		price2 = new KFloat(price2.nValue / 2, price2.nDigit, price2.nUnit);
		canvas.save();
		canvas.drawText(price2.toString(), x, avRect.top + avRect.height() / 4
		        * 3 + 5, p1);

		// 第五行
		canvas.drawText(minPrice.toString(), x, avRect.bottom - 5, p1);

		canvas.restore();

		
		
	}

	/** 分时区左侧标价 */
	public static void drawPrice(Canvas canvas, Rect avRect, Rect amRect,
	        KFloat maxPrice, KFloat minPrice, KFloat kfZrsp, KFloat kfMaxVol,int wtype,
	        Paint p1)
	{
		// 分时区
		int x = avRect.left + 5;

		p1.setColor(clr_fs_red);
		p1.setTextSize(theme_fs_bs5_textsize);

		// 首行
		canvas.save();
		canvas.drawText(maxPrice.toString(), x, avRect.top
		        + theme_fs_bs5_textsize, p1);

		canvas.restore();
		// 第二行
		KFloat price1 = new KFloat();
		KFloatUtils.add(price1, kfZrsp, maxPrice);

		price1 = new KFloat(price1.nValue / 2, price1.nDigit, price1.nUnit);
		canvas.save();
		canvas.drawText(price1.toString(), x, avRect.top + avRect.height() / 4
		        + 5, p1);

		canvas.restore();
		// 第三行
		p1.setColor(Color.WHITE);
		canvas.save();
		canvas.drawText(kfZrsp.toString(), x, avRect.centerY(), p1);

		canvas.restore();
		// 第四行
		p1.setColor(clr_fs_green);
		KFloat price2 = new KFloat();

		KFloatUtils.add(price2, kfZrsp, minPrice);
		price2 = new KFloat(price2.nValue / 2, price2.nDigit, price2.nUnit);
		canvas.save();
		canvas.drawText(price2.toString(), x, avRect.top + avRect.height() / 4
		        * 3 + 5, p1);

		// 第五行
		canvas.drawText(minPrice.toString(), x, avRect.bottom - 5, p1);

		canvas.restore();

		// 成交量区
		//if (!HQTitle.isNewStock(wtype))
		{
			p1.setColor(clr_fs_yellow);
			// 第六行
			canvas.save();
			canvas.drawText(kfMaxVol.toString(), x, amRect.top
			        + theme_fs_bs5_textsize, p1);
	
			canvas.restore();
			// 第七行
	
			KFloat vol = new KFloat(kfMaxVol.nValue / 2, kfMaxVol.nDigit,
			        kfMaxVol.nUnit);
			canvas.save();
			canvas.drawText(vol.toString(), x,
			        amRect.height() / 2 + amRect.top + 5, p1);
			canvas.restore();
		}
	}

	/** 分时区左侧标价 */
	public static void drawPrice_jy(Canvas canvas, Rect avRect,
	        KFloat maxPrice, KFloat minPrice, KFloat kfZrsp, Paint p1)
	{
		// 分时区
		int x = avRect.left + 5;

		p1.setColor(clr_fs_red);
		p1.setTextSize(theme_fs_bs5_textsize);

		// 首行
		canvas.save();
		canvas.drawText(maxPrice.toString(), x, avRect.top + 15, p1);

		canvas.restore();
		// 第二行
		KFloat price1 = new KFloat();
		KFloatUtils.add(price1, kfZrsp, maxPrice);

		price1 = new KFloat(price1.nValue / 2, price1.nDigit, price1.nUnit);
		canvas.save();
		canvas.drawText(price1.toString(), x, avRect.top + avRect.height() / 4
		        + 5, p1);

		canvas.restore();
		// 第三行
		p1.setColor(Color.BLACK);
		canvas.save();
		canvas.drawText(kfZrsp.toString(), x, avRect.centerY(), p1);

		canvas.restore();
		// 第四行
		p1.setColor(clr_fs_green);
		KFloat price2 = new KFloat();

		KFloatUtils.add(price2, kfZrsp, minPrice);
		price2 = new KFloat(price2.nValue / 2, price2.nDigit, price2.nUnit);
		canvas.save();
		canvas.drawText(price2.toString(), x, avRect.top + avRect.height() / 4
		        * 3 + 5, p1);

		// 第五行
		canvas.drawText(minPrice.toString(), x, avRect.bottom - 5, p1);

		canvas.restore();
	}

	/** 港股 1为详情，2为明细 （与A股盘口index分开，否则逻辑混乱易错） **/
	private static int bs5_index_hk = 1;

	/** 港股顶部（祥，明） */
	public static void drawHKBS5Top(Canvas canvas, Rect rect, boolean event_t,
	        int eventX, int eventY)
	{
		// ****老的***********************************
		/*
		 * Paint[] p = new Paint[] { new Paint(), new Paint(), new Paint(), };
		 * RectF rf[] = new RectF[2]; int x0 = rect.width() / 6 + rect.left; int
		 * x1 = x0 + theme_fs_bs5_btn_width; int x2 = rect.centerX() +
		 * rect.width() / 6; int x3 = x2 + theme_fs_bs5_btn_width; int y0 =
		 * rect.top + theme_fs_bs5_btn_height + 5; rf[0] = new RectF(x0,
		 * rect.top + 5, x1, y0); rf[1] = new RectF(x2, rect.top + 5, x3, y0);
		 * 
		 * p[1].setColor(clr_fs_red); p[1].setFlags(Paint.ANTI_ALIAS_FLAG);//
		 * 去锯齿 p[1].setAntiAlias(true); p[1].setFilterBitmap(true);
		 * 
		 * if (rf[0].contains(eventX, eventY)) { bs5_index_hk = 1; } else if
		 * (rf[1].contains(eventX, eventY)) { bs5_index_hk = 2; } else if
		 * (event_t) { bs5_index_hk += 1; if (bs5_index_hk > 2) { bs5_index_hk =
		 * 1; } }
		 * 
		 * switch (bs5_index_hk) { case 1:// 详 canvas.save();
		 * canvas.drawRoundRect(rf[0], 3.0f, 3.0f, p[1]); canvas.restore();
		 * break; case 2:// 明 canvas.save(); canvas.drawRoundRect(rf[1], 3.0f,
		 * 3.0f, p[1]); canvas.restore(); break; }
		 * 
		 * p[2].setColor(Color.WHITE); p[2].setTextAlign(Paint.Align.CENTER);
		 * p[2].setTextSize(theme_fs_bs5_textsize);
		 * p[2].setFlags(Paint.ANTI_ALIAS_FLAG);// 去锯齿 float cx0 =
		 * rf[0].centerX(); float cx1 = rf[1].centerX(); float cy = rf[0].bottom
		 * - 5; canvas.save(); canvas.drawText("详", cx0, cy, p[2]);
		 * canvas.drawText("明", cx1, cy, p[2]); canvas.restore();
		 */

		// *****新的**********************************************
		Paint[] p = new Paint[] { new Paint(), new Paint(), new Paint(), };
		Rect r[] = new Rect[2];
		// int x0 = rect.width() / 6 + rect.left;
		// int x1 = x0 + theme_fs_bs5_btn_width;
		// int x2 = rect.centerX() + rect.width() / 6;
		// int x3 = x2 + theme_fs_bs5_btn_width;
		// int y0 = rect.top + theme_fs_bs5_btn_height + 5;
		int width = rect.width() / 2;
		int x0 = rect.left;
		int x1 = x0 + width;
		int y0 = rect.bottom;

		r[0] = new Rect(x0, rect.top + 5, x1, y0);
		r[1] = new Rect(x1, rect.top + 5, x1 + width, y0);

		p[1].setColor(0xFF4D4D4D);
		p[1].setFlags(Paint.ANTI_ALIAS_FLAG);// 去锯齿
		p[1].setAntiAlias(true);
		p[1].setFilterBitmap(true);

		p[0].setColor(MinuteViewTheme.clr_fs_bgn);
		p[0].setFlags(Paint.ANTI_ALIAS_FLAG);// 去锯齿
		p[0].setAntiAlias(true);
		p[0].setFilterBitmap(true);

		if (r[0].contains(eventX, eventY))
		{
			bs5_index_hk = 1;
		} else if (r[1].contains(eventX, eventY))
		{
			bs5_index_hk = 2;
		} else if (event_t)
		{
			bs5_index_hk += 1;
			if (bs5_index_hk > 2)
			{
				bs5_index_hk = 1;
			}
		}

		switch (bs5_index_hk)
		{
			case 1:// 详
				canvas.save();
				// canvas.drawRoundRect(rf[0], 3.0f, 3.0f, p[1]);
				canvas.drawRect(r[0], p[1]);
				canvas.drawRect(r[1], p[0]);
				canvas.restore();
				break;
			case 2:// 明
				canvas.save();
				// canvas.drawRoundRect(rf[1], 3.0f, 3.0f, p[1]);
				canvas.drawRect(r[0], p[0]);
				canvas.drawRect(r[1], p[1]);
				canvas.restore();
				break;
		}

		p[2].setColor(Color.WHITE);
		p[2].setTextAlign(Paint.Align.CENTER);
		p[2].setTextSize(theme_fs_bs5_textsize);
		p[2].setFlags(Paint.ANTI_ALIAS_FLAG);// 去锯齿
		float cx0 = r[0].centerX();
		float cx1 = r[1].centerX();
		float cy = r[0].bottom - 5;
		canvas.save();
		canvas.drawText("详", cx0, cy, p[2]);
		canvas.drawText("明", cx1, cy, p[2]);
		canvas.restore();

	}

	/** 港股详情界面 */
	public static void drawHKXQRect(Canvas canvas, Rect rect, KFloat kfZjcj,
	        int[] nCjjj_s, KFloat kfZd, int[] nZdf_s, KFloat kfCjss,
	        int[] nCjss_s, String kfHsl, KFloat kfLb, KFloat kfBuyp,
	        KFloat kfSelp, KFloat kfZgcj, KFloat kfZdcj, KFloat kfZrsp,
	        int fsDataWCount, int[] nTime_s)
	{
		Paint[] p = new Paint[] { new Paint(), new Paint(), new Paint(),
		        new Paint(), };
		p[0].setAntiAlias(true);
		p[0].setFilterBitmap(true);
		p[1].setAntiAlias(true);
		p[1].setFilterBitmap(true);
		p[2].setAntiAlias(true);
		p[2].setFilterBitmap(true);
		p[3].setAntiAlias(true);
		p[3].setFilterBitmap(true);
		if (bs5_index_hk == 1)
		{
			int height = rect.height() / 13;

			int y0 = rect.top + height - 5;
			int y1 = rect.top + height * 2 - 5;
			int y2 = rect.top + height * 3 - 5;
			int y3 = rect.top + height * 4 - 5;
			int y4 = rect.top + height * 5 - 5;
			int y5 = rect.top + height * 6 - 5;
			int y6 = rect.top + height * 7 - 5;
			int y7 = rect.top + height * 8 - 5;
			int y8 = rect.top + height * 9 - 5;
			int y9 = rect.top + height * 10 - 5;
			int y10 = rect.top + height * 11 - 5;
			int y11 = rect.top + height * 12 - 5;
			int y12 = rect.top + height * 13 - 5;

			// 画俩条虚线
			p[0].setStrokeWidth(theme_fs_line_width);
			p[0].setColor(clr_fs_line);
			float[] line1 = DrawUtils.getDottedLine(rect.left, y5 + 5, rect,
			        true);
			float[] line2 = DrawUtils.getDottedLine(rect.left, y9 + 5, rect,
			        true);
			canvas.save();
			canvas.drawLine(rect.left, rect.top, rect.right, rect.top, p[0]);
			canvas.drawLines(line1, p[0]);
			canvas.drawLines(line2, p[0]);
			canvas.restore();
			int left = rect.left + 5;

			p[0].setTextSize(theme_fs_bs5_textsize);
			p[0].setTextAlign(Paint.Align.LEFT);
			p[0].setColor(Color.WHITE);
			canvas.save();
			canvas.drawText("现价:", left, y0, p[0]);
			canvas.drawText("均价:", left, y1, p[0]);
			canvas.drawText("涨跌:", left, y2, p[0]);
			canvas.drawText("涨幅:", left, y3, p[0]);
			canvas.drawText("总手:", left, y4, p[0]);
			canvas.drawText("现手:", left, y5, p[0]);
			canvas.drawText("换手:", left, y6, p[0]);
			canvas.drawText("量比:", left, y7, p[0]);
			canvas.drawText("内盘:", left, y8, p[0]);
			canvas.drawText("外盘:", left, y9, p[0]);
			canvas.drawText("最高:", left, y10, p[0]);
			canvas.drawText("最低:", left, y11, p[0]);
			canvas.drawText("昨收:", left, y12, p[0]);
			canvas.restore();

			p[0].setColor(clr_fs_blue);
			p[1].setColor(clr_fs_green);
			p[2].setColor(clr_fs_red);
			p[3].setColor(Color.WHITE);
			p[0].setTextAlign(Paint.Align.RIGHT);
			p[1].setTextAlign(Paint.Align.RIGHT);
			p[2].setTextAlign(Paint.Align.RIGHT);
			p[3].setTextAlign(Paint.Align.RIGHT);
			p[3].setTextSize(theme_fs_bs5_textsize);
			p[0].setTextSize(theme_fs_bs5_textsize);
			p[1].setTextSize(theme_fs_bs5_textsize);
			p[2].setTextSize(theme_fs_bs5_textsize);

			int right = rect.right - 5;
			int zd_index = 0;

			if (nTime_s == null)
			{
				canvas.save();
				canvas.drawText("---", right, y0, p[3]);
				canvas.drawText("---", right, y1, p[3]);
				canvas.drawText("---", right, y2, p[3]);
				canvas.drawText("---", right, y3, p[3]);
				canvas.drawText("---", right, y4, p[3]);
				canvas.drawText("---", right, y5, p[3]);
				canvas.drawText("---", right, y6, p[3]);
				canvas.drawText("---", right, y7, p[3]);
				canvas.drawText("---", right, y8, p[3]);
				canvas.drawText("---", right, y9, p[3]);
				canvas.drawText("---", right, y10, p[3]);
				canvas.drawText("---", right, y11, p[3]);
				canvas.drawText("---", right, y12, p[3]);
				canvas.restore();
			} else
			{

				canvas.save();
				// 最新 现价
				switch (KFloatUtils.compare(kfZjcj, kfZrsp))
				{
					case -1:
						zd_index = 1;
						break;
					case 0:
						zd_index = 3;
						break;
					case 1:
						zd_index = 2;
						break;
				}
				canvas.drawText(kfZjcj.toString(), right, y0, p[zd_index]);

				KFloat s = new KFloat();
				int wCount = fsDataWCount - 1;
				// 均价
				if (nCjjj_s == null)
				{
					return;
				}
				switch (KFloatUtils.compare(s.init(nCjjj_s[wCount]), kfZrsp))
				{
					case -1:
						zd_index = 1;
						break;
					case 0:
						zd_index = 3;
						break;
					case 1:
						zd_index = 2;
						break;
				}
				canvas.drawText(s.init(nCjjj_s[wCount]).toString(), right, y1,
				        p[zd_index]);
				// 涨跌
				switch (KFloatUtils.compare(kfZjcj, kfZrsp))
				{
					case -1:
						zd_index = 1;
						break;
					case 0:
						zd_index = 3;
						break;
					case 1:
						zd_index = 2;
						break;
				}
				canvas.drawText(kfZd.toString(), right, y2, p[zd_index]);
				// 涨幅
				canvas.drawText(s.init(nZdf_s[wCount]).toString("%"), right,
				        y3, p[zd_index]);
				p[0].setColor(clr_fs_yellow);
				// 总手
				canvas.drawText(kfCjss.toString(), right, y4, p[0]);
				// 现手
				if (nCjss_s == null)
				{
					canvas.drawText("---", right, y5, p[0]);
				} else
					canvas.drawText(s.init(nCjss_s[nCjss_s.length - 1])
					        .toString(), right, y5, p[0]);
				// 換手
				if (kfHsl == null || kfHsl.equals("") || kfHsl.equals("---"))
				{
					canvas.drawText("---", right, y6, p[0]);

				} else
				{
					canvas.drawText(kfHsl + "%", right, y6, p[0]);
				}
				// 量比
				canvas.drawText(kfLb.toString(), right, y7, p[0]);
				// 内盘=卖出
				canvas.drawText(kfSelp.toString(), right, y8, p[1]);
				// 外盘=买入
				canvas.drawText(kfBuyp.toString(), right, y9, p[2]);
				// 最高
				switch (KFloatUtils.compare(kfZgcj, kfZrsp))
				{
					case -1:
						zd_index = 1;
						break;
					case 0:
						zd_index = 3;
						break;
					case 1:
						zd_index = 2;
						break;
				}
				canvas.drawText(kfZgcj.toString(), right, y10, p[zd_index]);
				// 最低
				switch (KFloatUtils.compare(kfZdcj, kfZrsp))
				{
					case -1:
						zd_index = 1;
						break;
					case 0:
						zd_index = 3;
						break;
					case 1:
						zd_index = 2;
						break;
				}
				canvas.drawText(kfZdcj.toString(), right, y11, p[zd_index]);
				// 昨收
				canvas.drawText(kfZrsp.toString(), right, y12, p[3]);
				canvas.restore();
			}
		}
	}

	/** 港股明细界面 */
	public static void drawHKMXRect(Canvas canvas, Rect rect, int[][] mxData,
	        KFloat kfZrsp, int[] nTime_s)
	{

		if (bs5_index_hk == 2)
		{
			Paint[] p = new Paint[] { new Paint(), new Paint(), new Paint(),
			        new Paint(), };
			if (mxData == null || mxData[0] == null)
			{
				return;
			} else if (nTime_s == null)
			{
				return;
			}

			int mxTitleH = MinuteViewTheme.theme_fs_mx_title_height;
			p[0].setTextSize(MinuteViewTheme.theme_fs_mx_textsize);
			int y0 = rect.top - 5 + mxTitleH;
			p[0].setColor(Color.WHITE);
			// 顶部字体
			canvas.save();
			p[0].setTextAlign(Paint.Align.LEFT);
			canvas.drawText("时", rect.left + 5, y0, p[0]);
			p[0].setTextAlign(Paint.Align.CENTER);
			canvas.drawText("价", rect.centerX(), y0, p[0]);
			p[0].setTextAlign(Paint.Align.RIGHT);
			canvas.drawText("量", rect.right - 5, y0, p[0]);
			canvas.restore();

			// 顶部线
			p[0].setColor(clr_fs_line);
			p[0].setStrokeWidth(theme_fs_line_width);
			canvas.drawLine(rect.left, y0 + 5, rect.right, y0 + 5, p[0]);
			canvas.drawLine(rect.left, rect.top, rect.right, rect.top, p[0]);

			// 时间参数
			p[1].setTextSize(MinuteViewTheme.theme_fs_mx_textsize - 2);
			p[1].setColor(clr_fs_yellow);
			p[1].setAntiAlias(true);
			p[1].setFilterBitmap(true);
			p[1].setTextSize(theme_fs_bs5_sjl_textsize);
			int mx_index = mxData[0].length;// 一共得到多少行数据
			int maxH = 17;// 每一行数据最大高度
			int rowCount = (rect.height() - mxTitleH) / maxH;// 最大可容纳多少行
			int left = rect.left + 5;
			// 价格参数
			p[2].setTextSize(MinuteViewTheme.theme_fs_mx_textsize - 5);
			p[2].setColor(clr_fs_red);
			p[2].setAntiAlias(true);
			p[2].setFilterBitmap(true);

			// float prictX = p[2].measureText("00:00") + 12 + left; // 价格显示X坐标

			p[2].setTextSize(theme_fs_bs5_sjl_textsize);
			float prictX = p[2].measureText("00:00") + 5 + left; // 价格显示X坐标

			// 成交量参数
			p[3].setTextSize(MinuteViewTheme.theme_fs_mx_textsize - 5);
			p[3].setAntiAlias(true);
			p[3].setFilterBitmap(true);
			p[3].setTextAlign(Paint.Align.RIGHT);
			p[3].setTextSize(theme_fs_bs5_sjl_textsize);
			int right = rect.right - 5;
			// 数据显示区域
			if (mx_index <= rowCount)
			{
				if (mx_index == 0)
				{
					return;
				}
				int rowH = (rect.height() - mxTitleH) / mx_index;
				int y1 = y0 + rowH;
				for (int index = 0; index < mx_index; index++)
				{
					int y = y1 + rowH * index;
					// 时间
					canvas.drawText(DrawUtils.formatNTime(mxData[0][index]),
					        left, y, p[1]);

					// 价格
					KFloat kfZjcj = new KFloat(mxData[2][index]);
					switch (KFloatUtils.compare(kfZjcj, kfZrsp))
					{
						case -1:// 跌
							p[2].setColor(clr_fs_green);
							break;
						case 0:// 平
							p[2].setColor(Color.WHITE);
							break;
						case 1:// 红
							p[2].setColor(clr_fs_red);
							break;
					}
					canvas.drawText(kfZjcj.toString(), prictX, y, p[2]);

					// 成交量
					String sCjlb = String.valueOf((char) mxData[1][index]);
					KFloat kfCjss = new KFloat(mxData[3][index]);
					p[3].setColor(sCjlb.equals("B") ? clr_fs_red : clr_fs_green);
					canvas.drawText(kfCjss.toString(), right, y, p[3]);
				}
			} else
			{
				int i = mx_index - rowCount;
				int y1 = y0 + maxH;
				for (int index = i; index < mx_index; index++)
				{
					int c = index - i;
					int y = y1 + maxH * c;
					// 时间
					canvas.drawText(DrawUtils.formatNTime(mxData[0][index]),
					        left, y, p[1]);
					// 价格
					KFloat kfZjcj = new KFloat(mxData[2][index]);
					switch (KFloatUtils.compare(kfZjcj, kfZrsp))
					{
						case -1:// 跌
							p[2].setColor(clr_fs_green);
							break;
						case 0:// 平
							p[2].setColor(Color.WHITE);
							break;
						case 1:// 红
							p[2].setColor(clr_fs_red);
							break;
					}
					canvas.drawText(kfZjcj.toString(), prictX, y, p[2]);

					// 成交量
					String sCjlb = String.valueOf((char) mxData[1][index]);
					KFloat kfCjss = new KFloat(mxData[3][index]);
					p[3].setColor(sCjlb.equals("B") ? clr_fs_red : clr_fs_green);
					canvas.drawText(kfCjss.toString(), right, y, p[3]);

				}
			}
		}
	}

	/** 港股指数 */
	public static void drawHKZSRect(Canvas canvas, Rect rect, KFloat kfZjcj,
	        KFloat kfZd, KFloat kfZdf, KFloat kfCjje, KFloat kfZgcj,
	        KFloat kfZdcj, KFloat kfZrsp, int[] nTime_s)
	{
		Paint[] p = new Paint[] { new Paint(), new Paint(), new Paint(),
		        new Paint(), };
		p[0].setAntiAlias(true);
		p[0].setFilterBitmap(true);
		p[0].setTextSize(theme_fs_bs5_textsize);
		p[1].setTextSize(theme_fs_bs5_textsize);
		p[2].setTextSize(theme_fs_bs5_textsize);
		p[3].setTextSize(theme_fs_bs5_textsize);

		int h = rect.height() / 10 - 5;
		int left = rect.left + 5;
		int[] y = new int[] { h + rect.top, h * 2 + rect.top, h * 3 + rect.top,
		        h * 4 + rect.top, h * 5 + rect.top, h * 6 + rect.top, };
		String[] s = new String[] { "现价:", "涨跌:", "涨幅:", "成交额:", "最高:", "最低:", };
		p[0].setColor(Color.WHITE);
		p[0].setTextSize(theme_fs_bs5_textsize);
		for (int i = 0; i < 6; i++)
		{
			canvas.drawText(s[i], left, y[i], p[0]);
		}

		// 画虚线
		p[0].setStrokeWidth(theme_fs_line_width);
		p[0].setColor(clr_fs_line);
		float[] line1 = DrawUtils
		        .getDottedLine(rect.left, y[1] + 5, rect, true);
		float[] line2 = DrawUtils
		        .getDottedLine(rect.left, y[3] + 5, rect, true);
		float[] line3 = DrawUtils
		        .getDottedLine(rect.left, y[5] + 5, rect, true);
		canvas.save();
		canvas.drawLines(line1, p[0]);
		canvas.drawLines(line2, p[0]);
		canvas.drawLines(line3, p[0]);
		canvas.restore();

		int right = rect.right - 5;
		p[1].setColor(clr_fs_green);
		p[2].setColor(clr_fs_red);
		p[3].setColor(Color.WHITE);
		for (int i = 1; i < 4; i++)
		{
			p[i].setTextAlign(Paint.Align.RIGHT);
			p[i].setAntiAlias(true);
			p[i].setFilterBitmap(true);
		}

		// if (nTime_s == null)
		// {
		// for (int i = 0; i < 6; i++)
		// {
		//
		// canvas.drawText("---", right, y[i], p[3]);
		// }
		//
		// } else
		// {
		if (kfZrsp == null || kfZjcj == null)
			return;
		int zd_index = 0;
		// 最新 现价
		switch (KFloatUtils.compare(kfZjcj, kfZrsp))
		{
			case -1:
				zd_index = 1;
				break;
			case 0:
				zd_index = 3;
				break;
			case 1:
				zd_index = 2;
				break;
		}
		canvas.drawText(kfZjcj.toString(), right, y[0], p[zd_index]);
		// 涨跌
		canvas.drawText(kfZd.toString(), right, y[1], p[zd_index]);
		// 涨幅
		System.out.println("zdf" + kfZdf);
		canvas.drawText(kfZdf.toString("%"), right, y[2], p[zd_index]);
		// 成交额
		canvas.drawText(kfCjje.toString(), right, y[3], p[1]);
		// 最高
		switch (KFloatUtils.compare(kfZgcj, kfZrsp))
		{
			case -1:
				zd_index = 1;
				break;
			case 0:
				zd_index = 3;
				break;
			case 1:
				zd_index = 2;
				break;
		}
		canvas.drawText(kfZgcj.toString(), right, y[4], p[zd_index]);
		// 最低
		switch (KFloatUtils.compare(kfZdcj, kfZrsp))
		{
			case -1:
				zd_index = 1;
				break;
			case 0:
				zd_index = 3;
				break;
			case 1:
				zd_index = 2;
				break;
		}
		canvas.drawText(kfZdcj.toString(), right, y[5], p[zd_index]);
		// }
	}

	/** 指数盘口区 */
	public static void drawZSRect(Canvas canvas, Rect rect, KFloat kfZjcj,
	        KFloat kfZd, KFloat kfZdf, KFloat kfCjje, KFloat kfZgcj,
	        KFloat kfZdcj, String ZSZ, String LTP, String JJ, String JQJJ,
	        int Zjs, String PP, int Djs, KFloat kfZrsp, short zsCount,
	        int[] nTime_s)

	{
		Paint[] p = new Paint[] { new Paint(), new Paint(), new Paint(),
		        new Paint(), new Paint(), };
		p[0].setAntiAlias(true);
		p[0].setFilterBitmap(true);
		p[1].setAntiAlias(true);
		p[1].setFilterBitmap(true);
		p[2].setAntiAlias(true);
		p[2].setFilterBitmap(true);
		p[3].setAntiAlias(true);
		p[3].setFilterBitmap(true);
		p[4].setAntiAlias(true);
		p[4].setFilterBitmap(true);
		int height = rect.height() / 13;

		int y0 = rect.top + height - 5;
		int y1 = rect.top + height * 2 - 5;
		int y2 = rect.top + height * 3 - 5;
		int y3 = rect.top + height * 4 - 5;
		int y4 = rect.top + height * 5 - 5;
		int y5 = rect.top + height * 6 - 5;
		int y6 = rect.top + height * 7 - 5;
		int y7 = rect.top + height * 8 - 5;
		int y8 = rect.top + height * 9 - 5;
		int y9 = rect.top + height * 10 - 5;
		int y10 = rect.top + height * 11 - 5;
		int y11 = rect.top + height * 12 - 5;
		int y12 = rect.top + height * 13 - 5;

		// 画俩条虚线
		p[0].setStrokeWidth(theme_fs_line_width);
		p[0].setColor(clr_fs_line);
		float[] line1 = DrawUtils.getDottedLine(rect.left, y5 + 5, rect, true);
		float[] line2 = DrawUtils.getDottedLine(rect.left, y9 + 5, rect, true);
		canvas.save();
		canvas.drawLines(line1, p[0]);
		canvas.drawLines(line2, p[0]);
		canvas.restore();
		int left = rect.left + 5;

		p[0].setTextSize(theme_fs_bs5_textsize);
		p[0].setTextAlign(Paint.Align.LEFT);
		p[0].setColor(Color.WHITE);
		canvas.save();
		canvas.drawText("现价:", left, y0, p[0]);
		canvas.drawText("涨跌:", left, y1, p[0]);
		canvas.drawText("涨幅:", left, y2, p[0]);
		canvas.drawText("成交额:", left, y3, p[0]);
		canvas.drawText("最高:", left, y4, p[0]);
		canvas.drawText("最低:", left, y5, p[0]);
		canvas.drawText("总市值:", left, y6, p[0]);
		canvas.drawText("流通盘:", left, y7, p[0]);
		canvas.drawText("均价:", left, y8, p[0]);
		canvas.drawText("加权均价:", left, y9, p[0]);
		canvas.drawText("上涨:", left, y10, p[0]);
		canvas.drawText("平盘:", left, y11, p[0]);
		canvas.drawText("下跌:", left, y12, p[0]);
		canvas.restore();

		p[0].setColor(clr_fs_blue);
		p[1].setColor(clr_fs_green);
		p[2].setColor(clr_fs_red);
		p[3].setColor(Color.WHITE);
		p[4].setColor(clr_fs_yellow);
		p[0].setTextAlign(Paint.Align.RIGHT);
		p[1].setTextAlign(Paint.Align.RIGHT);
		p[2].setTextAlign(Paint.Align.RIGHT);
		p[3].setTextAlign(Paint.Align.RIGHT);
		p[4].setTextAlign(Paint.Align.RIGHT);
		p[1].setTextSize(theme_fs_bs5_textsize);
		p[2].setTextSize(theme_fs_bs5_textsize);
		p[3].setTextSize(theme_fs_bs5_textsize);
		p[4].setTextSize(theme_fs_bs5_textsize);

		int right = rect.right - 5;
		int zd_index = 0;
		if (nTime_s == null)
		{
			canvas.save();
			canvas.drawText("---", right, y0, p[3]);
			canvas.drawText("---", right, y1, p[3]);
			canvas.drawText("---", right, y2, p[3]);
			canvas.drawText("---", right, y3, p[3]);
			canvas.drawText("---", right, y4, p[3]);
			canvas.drawText("---", right, y5, p[3]);
			canvas.drawText("---", right, y6, p[3]);
			canvas.drawText("---", right, y7, p[3]);
			canvas.drawText("---", right, y8, p[3]);
			canvas.drawText("---", right, y9, p[3]);
			canvas.drawText("---", right, y10, p[3]);
			canvas.drawText("---", right, y11, p[3]);
			canvas.drawText("---", right, y12, p[3]);
			canvas.restore();
		} else
		{

			canvas.save();
			// 最新 现价
			switch (KFloatUtils.compare(kfZjcj, kfZrsp))
			{
				case -1:
					zd_index = 1;
					break;
				case 0:
					zd_index = 3;
					break;
				case 1:
					zd_index = 2;
					break;
			}
			canvas.drawText(kfZjcj.toString(), right, y0, p[zd_index]);

			// 涨跌
			switch (kfZd.toString().compareTo("0"))
			{
				case -1:
					zd_index = 1;
					break;
				case 0:
					zd_index = 3;
					break;
				case 1:
					zd_index = 2;
					break;
			}
			canvas.drawText(kfZd.toString(), right, y1, p[zd_index]);

			int wCount = zsCount - 1;
			KFloat s = new KFloat();
			// 涨幅
			if (kfZdf == null)
			{
				return;
			} else
			{
				// switch (s.init(nZdf_s[wCount]).toString().compareTo("0"))
				// {
				// case -1:
				// zd_index = 1;
				// break;
				// case 0:
				// zd_index = 3;
				// break;
				// case 1:
				// zd_index = 2;
				// break;
				// }
				// canvas.drawText(s.init(nZdf_s[wCount]).toString("%"), right,
				// y2, p[zd_index]);
				switch (kfZdf.toString().compareTo("0"))
				{
					case -1:
						zd_index = 1;
						break;
					case 0:
						zd_index = 3;
						break;
					case 1:
						zd_index = 2;
						break;
				}
				canvas.drawText(kfZdf.toString("%"), right, y2, p[zd_index]);

			}
			// 成交额
			canvas.drawText(kfCjje.toString(), right, y3, p[4]);
			// 最高
			switch (KFloatUtils.compare(kfZgcj, kfZrsp))
			{
				case -1:
					zd_index = 1;
					break;
				case 0:
					zd_index = 3;
					break;
				case 1:
					zd_index = 2;
					break;
			}
			canvas.drawText(kfZgcj.toString(), right, y4, p[zd_index]);
			// 最低
			switch (KFloatUtils.compare(kfZdcj, kfZrsp))
			{
				case -1:
					zd_index = 1;
					break;
				case 0:
					zd_index = 3;
					break;
				case 1:
					zd_index = 2;
					break;
			}
			canvas.drawText(kfZdcj.toString(), right, y5, p[zd_index]);
			// 总市值
			canvas.drawText(ZSZ, right, y6, p[4]);
			// 流通盘
			canvas.drawText(LTP, right, y7, p[4]);
			// 均价
			canvas.drawText(JJ, right, y8, p[2]);
			// 加权均价
			canvas.drawText(JQJJ, right, y9, p[2]);
			// 上涨
			canvas.drawText(String.valueOf(Zjs), right, y10, p[2]);
			// 平盘
			canvas.drawText(PP, right, y11, p[3]);
			// 下跌
			canvas.drawText(String.valueOf(Djs), right, y12, p[1]);
			// p[0].setColor(clr_fs_yellow);
			// // 总市值
			// canvas.drawText(kfZsz.toString(), right, y6, p[0]);
			// // 流通盘
			// canvas.drawText(kfLtp.toString(), right, y7, p[0]);
			// // 均价
			// canvas.drawText(kfJj.toString(), right, y8, p[1]);
			// // 加权均价
			// canvas.drawText(kfJqjj.toString(), right, y9, p[1]);
			// // 涨家数
			// canvas.drawText(String.valueOf(kfZjs).toString(), right, y10,
			// p[2]);
			// // 平盘
			// canvas.drawText(kfPp.toString(), right, y11, p[3]);
			// // 跌家数
			// canvas.drawText(String.valueOf(kfDjs).toString(), right, y12,
			// p[1]);
		}
	}

	/** 期货盘口切换索引 **/
	private static int qh_change = 0;

	/** 期货盘口 */
	public static void drawQHRect(Canvas canvas, Rect rect, boolean qh_event,
	        KFloat kfZrsp, KFloat[] kfBjg_s, KFloat[] kfBsl_s,
	        KFloat[] kfSjg_s, KFloat[] kfSsl_s, KFloat kfZjcj, KFloat kfZdf,
	        KFloat kfZd, KFloat kfCjjj, KFloat kfCjss, int[] nCjss_s,
	        KFloat kfCcl, int[][] mxData, KFloat kfCc, KFloat kfHsj,
	        KFloat kfZhsj, int fsDataWCount, KFloat kfZgcj, KFloat kfZdcj,
	        KFloat kfZf, KFloat kfLb, KFloat kfBuyp, KFloat kfSelp,
	        KFloat kfLimUp, KFloat kfLimDown, int[] nTime_s)
	{
		if (nTime_s == null)
		{
			return;
		}
		Paint[] p = new Paint[] { new Paint(), new Paint(), new Paint(),
		        new Paint(), new Paint(), new Paint(), };
		p[0].setAntiAlias(true);
		p[0].setFilterBitmap(true);
		p[1].setAntiAlias(true);
		p[1].setFilterBitmap(true);
		p[2].setAntiAlias(true);
		p[2].setFilterBitmap(true);
		p[3].setAntiAlias(true);
		p[3].setFilterBitmap(true);
		p[4].setAntiAlias(true);
		p[4].setFilterBitmap(true);
		p[5].setAntiAlias(true);
		p[5].setFilterBitmap(true);
		int height = rect.height() / 15;

		int[] y = new int[15];
		for (int i = 0; i < 15; i++)
		{
			y[i] = rect.top + height * i + height - 5;
		}

		// 画俩条虚线
		p[0].setStrokeWidth(theme_fs_line_width);
		p[0].setColor(clr_fs_line);
		float[] line1 = DrawUtils
		        .getDottedLine(rect.left, y[1] + 5, rect, true);
		float[] line2 = DrawUtils
		        .getDottedLine(rect.left, y[3] + 5, rect, true);
		float[] line3 = DrawUtils
		        .getDottedLine(rect.left, y[5] + 5, rect, true);
		float[] line4 = DrawUtils.getDottedLine(rect.left, y[10] + 5, rect,
		        true);
		canvas.save();
		canvas.drawLines(line1, p[0]);
		canvas.drawLines(line2, p[0]);
		canvas.drawLines(line3, p[0]);
		canvas.drawLines(line4, p[0]);
		canvas.restore();

		int right = rect.right;
		p[0].setColor(clr_fs_yellow);
		p[1].setColor(clr_fs_red);
		p[2].setColor(clr_fs_green);
		p[3].setColor(Color.WHITE);
		p[4].setColor(clr_fs_blue);
		p[5].setColor(clr_fs_yellow);
		p[0].setTextSize(theme_fs_bs5_textsize);
		p[1].setTextSize(theme_fs_bs5_textsize);
		p[2].setTextSize(theme_fs_bs5_textsize);
		p[3].setTextSize(theme_fs_bs5_textsize);
		p[4].setTextSize(theme_fs_bs5_textsize);
		p[5].setTextSize(theme_fs_bs5_textsize);
		for (int i = 1; i < 6; i++)
		{
			p[i].setAntiAlias(true);
			p[i].setFilterBitmap(true);
			p[i].setTextAlign(Paint.Align.RIGHT);
		}

		// 卖出
		int zd_index = 0;
		switch (KFloatUtils.compare(kfSjg_s[0], kfZhsj))
		{
			case -1:
				zd_index = 2;
				break;
			case 0:
				zd_index = 3;
				break;
			case 1:
				zd_index = 1;
				break;
		}
		canvas.save();
		canvas.drawText(kfSjg_s[0].toString(), right, y[0], p[zd_index]);
		canvas.drawText(kfSsl_s[0].toString(), right, y[1], p[5]);
		canvas.restore();
		// 买入
		switch (KFloatUtils.compare(kfBjg_s[0], kfZhsj))
		{
			case -1:
				zd_index = 2;
				break;
			case 0:
				zd_index = 3;
				break;
			case 1:
				zd_index = 1;
				break;
		}
		canvas.save();
		canvas.drawText(kfBjg_s[0].toString(), right, y[2], p[zd_index]);
		canvas.drawText(kfBsl_s[0].toString(), right, y[3], p[5]);
		canvas.restore();
		// 最新 现价
		switch (KFloatUtils.compare(kfZjcj, kfZhsj))
		{
			case -1:
				zd_index = 2;
				break;
			case 0:
				zd_index = 3;
				break;
			case 1:
				zd_index = 1;
				break;
		}
		canvas.drawText(kfZjcj.toString(), right, y[4], p[zd_index]);
		// 涨幅
		canvas.drawText(kfZdf.toString("%"), right, y[5], p[zd_index]);

		p[0].setTextSize(theme_fs_bs5_textsize);
		p[0].setTextAlign(Paint.Align.LEFT);
		p[0].setColor(Color.WHITE);
		p[1].setTextSize(theme_fs_bs5_textsize);
		p[2].setTextSize(theme_fs_bs5_textsize);
		p[3].setTextSize(theme_fs_bs5_textsize);

		if (qh_event)
		{
			qh_change++;
			if (qh_change > 1)
			{
				qh_change = 0;
			}
		}
		switch (qh_change)
		{// 点击切换
			case 0:
				// 标题
				String[] t1 = new String[] { "卖出:", "买入:", "现价:", "涨幅:", "涨跌:",
				        "均价:", "总手:", "现手:", "持仓:", "增仓:", "仓差:", "结算:", "前结:", };
				canvas.drawText(t1[0], rect.left, y[0], p[0]);
				canvas.drawText(t1[1], rect.left, y[2], p[0]);
				for (int i = 0; i < 11; i++)
				{
					canvas.drawText(t1[i + 2], rect.left, y[i + 4], p[0]);
				}

				// 涨跌
				canvas.drawText(kfZd.toString(), right, y[6], p[zd_index]);
				// 均价
				canvas.drawText(kfCjjj.toString(), right, y[7], p[zd_index]);
				// 总手
				p[4].setColor(clr_fs_yellow);
				canvas.drawText(kfCjss.toString(), right, y[8], p[4]);
				// 现手

				canvas.drawText("---", right, y[9], p[4]);
				// 持仓
				canvas.drawText(kfCcl.toString(), right, y[10], p[3]);
				// 增仓
				// if (mxData == null || mxData[0] == null)
				// {
				// return;
				// }
				// int mx_index = mxData[4].length;
				// KFloat kfFBCc = new KFloat(mxData[4][mx_index - 1]);
				canvas.drawText("---", right, y[11], p[3]);
				// 仓差
				canvas.drawText(kfCc.toString(), right, y[12], p[3]);
				// 结算
				canvas.drawText(kfHsj.toString(), right, y[13], p[3]);
				// 前结
				canvas.drawText(kfZhsj.toString(), right, y[14], p[3]);
				break;
			case 1:
				String[] t2 = new String[] { "卖出:", "买入:", "现价:", "涨幅:", "最高:",
				        "最低:", "昨收:", "振幅:", "量比:", "内盘:", "外盘:", "涨停:", "跌停:", };
				canvas.drawText(t2[0], rect.left, y[0], p[0]);
				canvas.drawText(t2[1], rect.left, y[2], p[0]);
				for (int i = 0; i < 11; i++)
				{
					canvas.drawText(t2[i + 2], rect.left, y[i + 4], p[0]);
				}
				// 最高
				switch (KFloatUtils.compare(kfZgcj, kfZhsj))
				{
					case -1:
						zd_index = 2;
						break;
					case 0:
						zd_index = 3;
						break;
					case 1:
						zd_index = 1;
						break;
				}
				canvas.drawText(kfZgcj.toString(), right, y[6], p[zd_index]);
				// 最低
				switch (KFloatUtils.compare(kfZdcj, kfZhsj))
				{
					case -1:
						zd_index = 2;
						break;
					case 0:
						zd_index = 3;
						break;
					case 1:
						zd_index = 1;
						break;
				}
				canvas.drawText(kfZdcj.toString(), right, y[7], p[zd_index]);
				// 昨收
				canvas.drawText(kfZrsp.toString(), right, y[8], p[1]);
				// 振幅
				canvas.drawText(kfZf.toString(), right, y[9], p[1]);
				// 量比
				canvas.drawText(kfLb.toString(), right, y[10], p[1]);
				// 内盘
				canvas.drawText(kfBuyp.toString(), right, y[11], p[2]);
				// 外盘
				canvas.drawText(kfSelp.toString(), right, y[12], p[1]);
				// 涨停
				canvas.drawText(kfLimUp.toString(), right, y[13], p[1]);
				// 跌停
				canvas.drawText(kfLimDown.toString(), right, y[14], p[2]);

				break;

		}
	}
	
	
}
