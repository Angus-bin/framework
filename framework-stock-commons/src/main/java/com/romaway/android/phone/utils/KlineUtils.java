package com.romaway.android.phone.utils;

import java.text.DecimalFormat;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;

import com.romaway.android.phone.view.KLineTheme;
import com.romaway.common.protocol.ProtocolConstant;
import com.romaway.common.protocol.util.KFloat;
import com.romaway.common.protocol.util.KFloatUtils;
import com.romaway.commons.lang.ArrayUtils;

/**
 * 
 * @author xueyan 画k线工具类
 */
public class KlineUtils
{

	public static final int PREF_TYPE_BOOL = 0;
	public static final int PREF_TYPE_INT = 1;
	public static final int PREF_TYPE_STRING = 2;
	public static final String PREF_NAME_USER = "mf_user_data";
	/**
	 * 白色
	 */
	public static int A0 = KLineTheme.A0;
	/**
	 * 红色
	 */
	public static int A1 = KLineTheme.A1;
	/**
	 * 绿色
	 */
	public static int A2 = KLineTheme.A2;
	/**
	 * 黄色
	 */
	public static int A3 = KLineTheme.A3;
	/**
	 * 浅黑色
	 */
	public static int A4 = KLineTheme.A4;
	/**
	 * 蓝色
	 */
	public static int A5 = KLineTheme.A5;
	/**
	 * 浅灰色
	 */
	public static int A6 = KLineTheme.A6;
	/**
	 * 灰色
	 */
	public static int A7 = KLineTheme.A7;
	/**
	 * 橘黄色
	 */
	public static int A8 = KLineTheme.A8;
	/**
	 * 紫色
	 */
	public static int A9 = KLineTheme.A9;

	/**
	 * k线数据字体大小
	 */
	private static final int theme_kline_floatText = KLineTheme.theme_kline_floatText;
	private static final int KLINE_WIDE_PRICEBOX = KLineTheme.theme_kline_wide_priceBox;
	/**
	 * 背景颜色
	 */
	private static final int CLR_KLINE_BACKGROUND = KLineTheme.clr_kline_background;
	/**
	 * 十字线颜色
	 */
	private static final int CROSSLINECOLOR = KLineTheme.clr_kline_crosslineColor;
	private static final int theme_kline_wide_avLineDataText = KLineTheme.theme_kline_wide_avLineDataText;
	// -------------------------------------------------------------------------------竖屏

	// -------------------------------------------------------------------------------横屏
	private static int SCAPE_HEIGHT_TITLE = KLineTheme.kline_scape_height_Title;
	private static int KLINE_SCAPE_HEIGHT_KLINE = KLineTheme.kline_scape_height_kline;
	private static int SCAPE_HEIGHT_LINESCAPE = KLineTheme.kline_scape_height_linescape;
	public static int CLR_KLINE_NAME = KLineTheme.clr_kline_name;
	public static int CLR_KLINE_CODE = KLineTheme.clr_kline_code;
	public static int CLR_KLINE_UP = KLineTheme.clr_kline_up;
	public static int CLR_KLINE_DOWN = KLineTheme.clr_kline_down;
	public static int CLR_KLINE_TIE = KLineTheme.clr_kline_tie;
	public static int kline_scape_name = KLineTheme.kline_scape_name;
	public static int kline_scape_code = KLineTheme.kline_scape_code;
	public static int kline_scape_ma = KLineTheme.kline_scape_ma;

	// -------------------------------------------------------------------------------横屏

	/**
	 * 画十字线竖屏
	 * 
	 * @param index
	 * @param current
	 * @param data
	 * @param dataLength
	 * @param max
	 * @param min
	 * @param rectOne
	 * @param canvas
	 * @param paint
	 * @param flag
	 */
	public static void drawCrossLine(int index, int current, int[] data,
	        int dataLength, KFloat max, KFloat min, Rect rectKline,
	        Rect rectTech, Canvas canvas, Paint paint, boolean flag)
	{
		int newY_c;
		if (flag && (data.length > current))
		{
			int step = (rectKline.bottom - rectKline.top) / 15;
			int bottomOne = rectKline.bottom;
			int bottomTow = rectTech.bottom;
			if (max == null || min == null)
				return;
			int price = max.nValue - min.nValue;
			KFloat startKFloat = new KFloat();
			Paint p = paint;
			Canvas cv = canvas;
			
			//
		
			p.setColor(CROSSLINECOLOR);
			p.setStrokeWidth(0.5f);
			//p.setStrokeWidth(WIDE_CROSSLINE);
			p.setStyle(Paint.Style.FILL);
			p.setFlags(Paint.ANTI_ALIAS_FLAG);
			p.setAntiAlias(true);
			p.setFilterBitmap(true);
			cv.save();
			if (price == 0)
			{

				return;
			}
			if (current < data.length)
			{
				newY_c = rectKline.bottom - step
				        - (startKFloat.init(data[current]).nValue - min.nValue)
				        * (rectKline.bottom - rectKline.top - step) / price;
				cv.drawLine(KLINE_WIDE_PRICEBOX, newY_c, rectKline.right,
				        newY_c, p);
			}

			cv.drawLine(index, rectKline.top, index, rectKline.bottom + 1, p);
			// cv.drawLine(index, rectKline.top, index, rectKline.bottom + 1,
			// p);
			cv.drawLine(index, bottomOne, index, bottomTow + 1, p);

			cv.restore();
		}
	}

	/**
	 * 画k线数据浮动框
	 * 
	 * @param canvas
	 * @param paint
	 * @param rect
	 * @param flag
	 */
	public static void drawFloatRect(Canvas canvas, Paint paint, Rect rect,
	        boolean flag)
	{
		Paint p = new Paint();
		Canvas c = canvas;

		if (flag)
		{
			p.setStyle(Paint.Style.FILL);
			p.setColor(A4);
			p.setFlags(Paint.ANTI_ALIAS_FLAG);
			p.setAntiAlias(true);
			p.setFilterBitmap(true);
			c.save();
			c.drawRect(rect, p);
			c.restore();
			p.setColor(Color.GRAY);
			p.setFlags(Paint.ANTI_ALIAS_FLAG);
			p.setAntiAlias(true);
			p.setFilterBitmap(true);

			c.save();
			c.drawLine(1, rect.top + 1, rect.right - 1, rect.top + 1, p);
			c.drawLine(1, rect.top, 1, rect.bottom, p);
			c.drawLine(rect.right - 1, rect.top + 1, rect.right - 1,
			        rect.bottom, p);
			c.drawLine(1, rect.bottom, rect.right - 1, rect.bottom, p);
			c.restore();

		}

	}

	public static int changeDate(int time)
	{
		int data = 0;
		data = time;
		String str = String.valueOf(data);
		String subString = str.substring(2);
		data = Integer.valueOf(subString);
		return data;
	}
	
	/**
	 * 格式化K线日期
	 * @param time  日期时间
	 * @param kLineType  k线周期类型
	 * @return
	 */
	public static String formatDate(int time,short kLineType){
		if (time == 0)
			return "";
		StringBuffer tmp = new StringBuffer();

		if (kLineType == ProtocolConstant.KX_DAY
				|| kLineType == ProtocolConstant.KX_WEEK 
				|| kLineType == ProtocolConstant.KX_MONTH){
			int year = time / 10000;
			int month = (time - year*10000) / 100;
			int day = time - year*10000 - month * 100;
			tmp.append(year); //年
			tmp.append("/");
			if (month<10){
				tmp.append("0");
			}
			tmp.append(month);
			tmp.append("/");
			if (day<10){
				tmp.append("0");
			}
			tmp.append(day);
			return tmp.toString();
		}else{
			//分钟线
		
			int month = time / 1000000;
			int day = (time - month*1000000) / 10000;
			int hour = (time  - month*1000000 - day * 10000) / 100;
			int minute = time  - month*1000000 - day * 10000 - hour * 100;
			tmp.append(month); //年
			tmp.append("/");
			if (day<10){
				tmp.append("0");
			}
			tmp.append(day);
			tmp.append(" ");
			if (hour<10){
				tmp.append("0");
			}
			tmp.append(hour);
			tmp.append(":");
			if (minute<10){
				tmp.append("0");
			}
			tmp.append(minute);
			return tmp.toString();
		}
		
	}

	/**
	 * 画k线信息（显示在浮动框中）
	 * 
	 * @param canvas
	 * @param paint
	 * @param rect
	 * @param currentIndex
	 * @param nTime_s
	 * @param nOpen_s
	 * @param nZgcj_s
	 * @param nZdcj_s
	 * @param nClose_s
	 * @param nCjss_s
	 * @param nCjje_s
	 * @param nZdf_s
	 * @param nHsl_s
	 * @param flag
	 * @param kLineType K线周期
	 */
	public static void drawFlaotText(Canvas canvas, Rect rect,
	        int currentIndex, int[] nTime_s, int[] nOpen_s, int[] nZgcj_s,
	        int[] nZdcj_s, int[] nClose_s, int[] nCjss_s, int[] nCjje_s,
	        int[] nZdf_s, int[] nHsl_s, boolean flag, KFloat kfZrsp,
	        int[] nYClose_s,short kLineType)
	{
		if (nTime_s != null && nTime_s.length > currentIndex)
		{

			KFloat tempFloat = new KFloat();
			Paint p = new Paint();
			p.setTextSize(theme_kline_floatText);
			// p.setColor(A3);
			p.setColor(Color.WHITE);
			p.setTextAlign(Paint.Align.LEFT);
			p.setStyle(Paint.Style.FILL);
			p.setFlags(Paint.ANTI_ALIAS_FLAG);
			p.setAntiAlias(true);
			p.setFilterBitmap(true);
			canvas.save();
			int height = rect.height() / 9;
			String[] texts = { "时", "开", "高", "低", "收", "量", "额", "幅", "换" };
			for (int i = 0; i < 9; i++)
			{
				canvas.drawText(texts[i], rect.left + 4, rect.top + (i + 1)
				        * height - 3, p);
			}
			canvas.restore();

			p.setTextAlign(Paint.Align.RIGHT);
			canvas.save();
//			canvas.drawText(String.valueOf(changeDate(nTime_s[currentIndex])),
//			        rect.right - 4, rect.top + 1 * height - 3, p);// 时
			canvas.drawText(formatDate(nTime_s[currentIndex],kLineType),
			        rect.right - 4, rect.top + 1 * height - 3, p);// 时
			p.setColor(A3);// 量用黄色
			canvas.drawText(
			        String.valueOf(tempFloat.init(nCjss_s[currentIndex])),
			        rect.right - 4, rect.top + 6 * height - 3, p);// 量
			p.setColor(Color.WHITE);
			canvas.drawText(
			        String.valueOf(tempFloat.init(nCjje_s[currentIndex])),
			        rect.right - 4, rect.top + 7 * height - 3, p);// 额
			canvas.drawText(
			        String.valueOf(tempFloat.init(nHsl_s[currentIndex])) + "%",
			        rect.right - 4, rect.top + 9 * height - 3, p);// 换
			canvas.restore();

			if (nZgcj_s[currentIndex] > nYClose_s[currentIndex])
			{// "高"跟"昨收"比
				p.setColor(A1);
				canvas.drawText(
				        String.valueOf(tempFloat.init(nZgcj_s[currentIndex])),
				        rect.right - 4, rect.top + 3 * height - 3, p);
			} else if (nZgcj_s[currentIndex] == nYClose_s[currentIndex])
			{
				p.setColor(A0);
				canvas.drawText(
				        String.valueOf(tempFloat.init(nZgcj_s[currentIndex])),
				        rect.right - 4, rect.top + 3 * height - 3, p);
			} else
			{
				p.setColor(A2);
				canvas.drawText(
				        String.valueOf(tempFloat.init(nZgcj_s[currentIndex])),
				        rect.right - 4, rect.top + 3 * height - 3, p);
			}

			if (nZdcj_s[currentIndex] > nYClose_s[currentIndex])
			{// "低"跟"昨收"比
				p.setColor(A1);
				canvas.drawText(
				        String.valueOf(tempFloat.init(nZdcj_s[currentIndex])),
				        rect.right - 4, rect.top + 4 * height - 3, p);

			} else if (nZdcj_s[currentIndex] == nYClose_s[currentIndex])
			{
				p.setColor(A0);
				canvas.drawText(
				        String.valueOf(tempFloat.init(nZdcj_s[currentIndex])),
				        rect.right - 4, rect.top + 4 * height - 3, p);
			} else
			{
				p.setColor(A2);
				canvas.drawText(
				        String.valueOf(tempFloat.init(nZdcj_s[currentIndex])),
				        rect.right - 4, rect.top + 4 * height - 3, p);
			}

			// int i =
			// KFloatUtils.compare(tempFloat.init(nOpen_s[currentIndex]),
			// kfZrsp);

			if (nClose_s[currentIndex] < nYClose_s[currentIndex])
			{// 收盘与昨收比
				p.setColor(A2);
				canvas.save();
				canvas.drawText(
				        String.valueOf(tempFloat.init(nClose_s[currentIndex])),
				        rect.right - 4, rect.top + 5 * height - 3, p);
				canvas.drawText(
				        String.valueOf(tempFloat.init(nZdf_s[currentIndex]))
				                + "%", rect.right - 4, rect.top + 8 * height
				                - 3, p);
				canvas.restore();
			} else if (nClose_s[currentIndex] > nYClose_s[currentIndex])
			{
				p.setColor(A1);
				canvas.save();
				canvas.drawText(
				        String.valueOf(tempFloat.init(nClose_s[currentIndex])),
				        rect.right - 4, rect.top + 5 * height - 3, p);
				canvas.drawText(
				        String.valueOf(tempFloat.init(nZdf_s[currentIndex]))
				                + "%", rect.right - 4, rect.top + 8 * height
				                - 3, p);
				canvas.restore();
			} else if (nClose_s[currentIndex] == nYClose_s[currentIndex])
			{
				p.setColor(A0);
				canvas.save();
				canvas.drawText(
				        String.valueOf(tempFloat.init(nClose_s[currentIndex])),
				        rect.right - 4, rect.top + 5 * height - 3, p);
				canvas.drawText(
				        String.valueOf(tempFloat.init(nZdf_s[currentIndex]))
				                + "%", rect.right - 4, rect.top + 8 * height
				                - 3, p);
				canvas.restore();
			}
			if (nOpen_s[currentIndex] < nYClose_s[currentIndex])
			{// 开盘 昨收比
				p.setColor(A2);
				canvas.save();
				canvas.drawText(
				        String.valueOf(tempFloat.init(nOpen_s[currentIndex])),
				        rect.right - 4, rect.top + 2 * height - 3, p);

				canvas.restore();
			} else if (nOpen_s[currentIndex] == nYClose_s[currentIndex])
			{
				p.setColor(A0);
				canvas.save();
				canvas.drawText(
				        String.valueOf(tempFloat.init(nOpen_s[currentIndex])),
				        rect.right - 4, rect.top + 2 * height - 3, p);
				canvas.restore();
			} else
			{
				p.setColor(A1);
				canvas.save();
				canvas.drawText(
				        String.valueOf(tempFloat.init(nOpen_s[currentIndex])),
				        rect.right - 4, rect.top + 2 * height - 3, p);
				canvas.restore();
			}
		}
	}

	/**
	 * 画均线数据
	 * 
	 * @param canvas
	 * @param paint
	 * @param rect
	 * @param currentIndex
	 * @param ma1
	 * @param ma2
	 * @param ma3
	 * @param ma4
	 * @param flag
	 */
//	public static void drawAMTexts(Canvas canvas, Rect rect, int currentIndex,
//	        int[] ma1, int[] ma2, int[] ma3, int[] ma4, boolean flag)
//	{
//		// 需要先重回数据显示框
//		Paint paint = new Paint();
//		paint.setColor(CLR_KLINE_BACKGROUND);
//		paint.setStyle(Paint.Style.FILL);
//		paint.setFlags(Paint.ANTI_ALIAS_FLAG);
//		paint.setAntiAlias(true);
//		paint.setFilterBitmap(true);
//		paint.setTextSize(theme_kline_wide_avLineDataText);
//		canvas.save();
//		canvas.drawRect(rect, paint);
//		canvas.restore();
//		KFloat tempKFloat = new KFloat();
//		int subWidth = rect.width() * 1 / 3;
//		int step_w = subWidth * 1 / 5;
//		int step_h = rect.height() * 1 / 5;
//		int bottom = rect.bottom;
//		if (flag)
//		{
//			paint.setColor(A0);
//			paint.setFlags(Paint.ANTI_ALIAS_FLAG);
//			paint.setAntiAlias(true);
//			paint.setFilterBitmap(true);
//			paint.setTextAlign(Paint.Align.LEFT);
//			canvas.save();
//
//			if (ma1 != null && currentIndex <= ma1.length)
//			{
//				canvas.drawText(
//				        "MA5:"
//				                + String.valueOf(tempKFloat
//				                        .init(ma1[currentIndex])), 0 * subWidth
//				                + step_w, bottom - step_h - 2, paint);
//			} else
//			{
//				canvas.drawText("MA5:- -", 0 * subWidth + step_w, bottom
//				        - step_h - 2, paint);
//			}
//			canvas.restore();
//			paint.setColor(A3);
//			paint.setFlags(Paint.ANTI_ALIAS_FLAG);
//			paint.setAntiAlias(true);
//			paint.setFilterBitmap(true);
//			paint.setTextAlign(Paint.Align.LEFT);
//			canvas.save();
//			if (ma2 != null && currentIndex <= ma2.length)
//			{
//				canvas.drawText(
//				        "MA10:"
//				                + String.valueOf(tempKFloat
//				                        .init(ma2[currentIndex])), 1 * subWidth
//				                + step_w, bottom - step_h - 2, paint);
//			} else
//			{
//				canvas.drawText("MA10:- -", 1 * subWidth + step_w, bottom
//				        - step_h - 2, paint);
//			}
//			canvas.restore();
//
//			// paint.setColor(A2);
//			// paint.setTextAlign(Paint.Align.LEFT);
//			// paint.setFlags(Paint.ANTI_ALIAS_FLAG);
//			// paint.setAntiAlias(true);
//			// paint.setFilterBitmap(true);
//			// canvas.save();
//			// if (ma3 != null && currentIndex <= ma3.length)
//			// {
//			// canvas.drawText(
//			// "20:"
//			// + String.valueOf(tempKFloat
//			// .init(ma3[currentIndex])), 2 * subWidth
//			// + step_w, bottom - step_h - 2, paint);
//			// } else
//			// {
//			// canvas.drawText("20:- -", 2 * subWidth + step_w, bottom
//			// - step_h - 2, paint);
//			// }
//			// canvas.restore();
//			paint.setColor(A9);
//			paint.setFlags(Paint.ANTI_ALIAS_FLAG);
//			paint.setAntiAlias(true);
//			paint.setFilterBitmap(true);
//			paint.setTextAlign(Paint.Align.LEFT);
//			canvas.save();
//			if (ma4 != null && currentIndex <= ma4.length)
//			{
//				canvas.drawText(
//				        "MA30:"
//				                + String.valueOf(tempKFloat
//				                        .init(ma4[currentIndex])), 2 * subWidth
//				                + step_w, bottom - step_h - 2, paint);
//			} else
//			{
//				canvas.drawText("MA30:- -", 2 * subWidth + step_w, bottom
//				        - step_h - 2, paint);
//			}
//			canvas.restore();
//		}
//
//	}

	/**
     * 画均线数据
     * 
     * @param canvas
     * @param paint
     * @param rect
     * @param currentIndex
     * @param ma1
     * @param ma2
     * @param ma3
     * @param ma4
     * @param ma5
     * @param maDays
     * @param flag
     */
	public static void drawAMTexts(Canvas canvas, Rect rect, int currentIndex,
            int[] ma1, int[] ma2, int[] ma3, int[] ma4,int[] ma5,int[] maDays, boolean flag)
    {
        // 需要先重回数据显示框
        Paint paint = new Paint();
        paint.setColor(CLR_KLINE_BACKGROUND);
        paint.setStyle(Paint.Style.FILL);
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setTextSize(theme_kline_wide_avLineDataText);
        canvas.save();
        canvas.drawRect(rect, paint);
        canvas.restore();
        KFloat tempKFloat = new KFloat();
        int subWidth = rect.width() * 1 / 3;
        int step_w = subWidth * 1 / 5;
        int step_h = rect.height() * 1 / 5;
        int bottom = rect.bottom;
        if (flag)
        {
            int tmpIndex = -1;
            int[] tmpMALines = null;
        
            for (int i = 0;i<maDays.length;i++){
                if (maDays[i]>0){
                    tmpIndex++;
                    if (i == 0){
                        paint.setColor(A0); //第1根，白色
                        tmpMALines = ma1;
                    }else if (i==1){
                        paint.setColor(A3); //第2根，黄色
                        tmpMALines = ma2;
                    }else if (i == 2){
                        paint.setColor(A9); //第3根，紫色
                        tmpMALines = ma3;
                    }else if (i == 3){
                        paint.setColor(A2); //第4根，绿色
                        tmpMALines = ma4;
                    }else if (i == 4){
                        paint.setColor(A5); //第5根，蓝色
                        tmpMALines = ma5;
                    } //目前最多支持5根均线
                    
                    paint.setStyle(Paint.Style.FILL);
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setAntiAlias(true);
                    paint.setFilterBitmap(true);
                    paint.setTextAlign(Paint.Align.LEFT);
            
                    if (tmpMALines != null)
                    {
                        canvas.save();
                        canvas.drawText(String.format("MA%s:%s", maDays[i],String.valueOf(tempKFloat
                                                .init(tmpMALines[currentIndex]))), tmpIndex
                                        * subWidth + step_w, bottom - step_h - 2, paint);
                        canvas.restore();
                    } else
                    {
                        canvas.save();
                        canvas.drawText(String.format("MA%s:--", maDays[i]), tmpIndex
                        * subWidth + step_w, bottom - step_h - 2, paint);
                        canvas.restore();
                    }
                }
            }
                    
//            paint.setColor(A0);
//            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
//            paint.setAntiAlias(true);
//            paint.setFilterBitmap(true);
//            paint.setTextAlign(Paint.Align.LEFT);
//            canvas.save();
//
//            if (ma1 != null && currentIndex <= ma1.length)
//            {
//                canvas.drawText(
//                        "MA5:"
//                                + String.valueOf(tempKFloat
//                                        .init(ma1[currentIndex])), 0 * subWidth
//                                + step_w, bottom - step_h - 2, paint);
//            } else
//            {
//                canvas.drawText("MA5:- -", 0 * subWidth + step_w, bottom
//                        - step_h - 2, paint);
//            }
//            canvas.restore();
//            paint.setColor(A3);
//            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
//            paint.setAntiAlias(true);
//            paint.setFilterBitmap(true);
//            paint.setTextAlign(Paint.Align.LEFT);
//            canvas.save();
//            if (ma2 != null && currentIndex <= ma2.length)
//            {
//                canvas.drawText(
//                        "MA10:"
//                                + String.valueOf(tempKFloat
//                                        .init(ma2[currentIndex])), 1 * subWidth
//                                + step_w, bottom - step_h - 2, paint);
//            } else
//            {
//                canvas.drawText("MA10:- -", 1 * subWidth + step_w, bottom
//                        - step_h - 2, paint);
//            }
//            canvas.restore();
//
//            // paint.setColor(A2);
//            // paint.setTextAlign(Paint.Align.LEFT);
//            // paint.setFlags(Paint.ANTI_ALIAS_FLAG);
//            // paint.setAntiAlias(true);
//            // paint.setFilterBitmap(true);
//            // canvas.save();
//            // if (ma3 != null && currentIndex <= ma3.length)
//            // {
//            // canvas.drawText(
//            // "20:"
//            // + String.valueOf(tempKFloat
//            // .init(ma3[currentIndex])), 2 * subWidth
//            // + step_w, bottom - step_h - 2, paint);
//            // } else
//            // {
//            // canvas.drawText("20:- -", 2 * subWidth + step_w, bottom
//            // - step_h - 2, paint);
//            // }
//            // canvas.restore();
//            paint.setColor(A9);
//            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
//            paint.setAntiAlias(true);
//            paint.setFilterBitmap(true);
//            paint.setTextAlign(Paint.Align.LEFT);
//            canvas.save();
//            if (ma4 != null && currentIndex <= ma4.length)
//            {
//                canvas.drawText(
//                        "MA30:"
//                                + String.valueOf(tempKFloat
//                                        .init(ma4[currentIndex])), 2 * subWidth
//                                + step_w, bottom - step_h - 2, paint);
//            } else
//            {
//                canvas.drawText("MA30:- -", 2 * subWidth + step_w, bottom
//                        - step_h - 2, paint);
//            }
//            canvas.restore();
        }

    }
	/**
	 * 画左右移动按钮框
	 */
	public static Rect[] drawLandRRects(Canvas canvas, Rect rect){
		int width = rect.width() / 8;
		Rect[] rts = new Rect[2];
		rts[0] = new Rect(rect.left + 5 * width - 20, rect.bottom - width - 14,
		        rect.left + 6 * width - 20, rect.bottom - 14);
		rts[1] = new Rect(rect.left + 7 * width - 20, rect.bottom - width - 14,
		        rect.left + 8 * width - 20, rect.bottom - 14);
		return rts;

	}

	/**
	 * 画左右移动按钮图片
	 * 
	 * @param canvas
	 * @param paint
	 * @param leftRect
	 * @param rightRect
	 * @param bitmap
	 */
	public static void drawMoveButton(Canvas canvas, Paint paint,
	        Rect leftrect, Rect righttrect, Bitmap[] bitmap)
	{

		Rect soureLeft = new Rect(0, 0, bitmap[0].getWidth(),
		        bitmap[0].getHeight());
		Rect soureRight = new Rect(0, 0, bitmap[1].getWidth(),
		        bitmap[1].getHeight());
		bitmap_left = Bitmap.createBitmap(bitmap[0], 0, 0,
		        bitmap[0].getWidth(), bitmap[0].getHeight());
		bitmap_right = Bitmap.createBitmap(bitmap[1], 0, 0,
		        bitmap[1].getWidth(), bitmap[1].getHeight());

		canvas.save();
		canvas.drawBitmap(bitmap_left, soureLeft, leftrect, paint);
		canvas.drawBitmap(bitmap_right, soureRight, righttrect, paint);
		canvas.restore();

	}

	private static Bitmap bitmap_left;
	private static Bitmap bitmap_right;

	/** 回收bitmap，否则内存可能会溢出（必要） */
	public static void bitmapRecycle()
	{
		if (bitmap_left != null && bitmap_right != null)
		{
			bitmap_left.recycle();
			bitmap_right.recycle();
		}
	}

	/**
	 * get Preference
	 * 
	 * @param pName
	 * @param key
	 * @param keytype
	 *            : See Also KingHelper defines
	 * @return
	 */
	public static Object getPreference(String pName, String key, int keytype,
	        Context context)
	{
		final String preName = pName;
		SharedPreferences sp = context.getSharedPreferences(preName, 0);
		switch (keytype)
		{
			case PREF_TYPE_BOOL:
				return sp.getBoolean(key, true);
			case PREF_TYPE_INT:
				return sp.getInt(key, 0);
			case PREF_TYPE_STRING:
				return sp.getString(key, "");
			default:
				break;
		}
		return null;
	}

	/**
	 * save Preference
	 * 
	 * @param preName
	 * @param key
	 * @param value
	 */
	public static void setPreference(String preName, String key, Object value,
	        Context context)
	{
		SharedPreferences sp = context.getSharedPreferences(preName, 0);
		SharedPreferences.Editor editor = sp.edit();
		if (value instanceof Boolean)
		{
			editor.putBoolean(key, (Boolean) value);
		} else if (value instanceof Integer)
		{
			editor.putInt(key, (Integer) value);
		} else if (value instanceof String)
		{
			editor.putString(key, (String) value);
		} else if (value instanceof Long)
		{
			editor.putLong(key, (Long) value);
		}
		editor.commit();
	}

	/**
	 * 画交易量数据
	 * 
	 * @param canvas
	 * @param paint
	 * @param r
	 * @param cjsl
	 * @param startIndex
	 */
	public static void drawVOLText(Canvas canvas, Rect r, int[] cjsl,
	        int startIndex, int screenCount)
	{

		KFloat kFloat = new KFloat();
		String max;
		// kFloat = getMax(startIndex, cjsl, screenCount);
		kFloat = getMax(startIndex, cjsl.length - 1, cjsl);
		max = String.valueOf(kFloat);
		if (kFloat == null)
		{
			return;
		}
		KFloatUtils.div(kFloat, 2);
		String mid = String.valueOf(kFloat);

		Paint p = new Paint();
		p.setTextSize(theme_kline_wide_avLineDataText);
		p.setFlags(Paint.ANTI_ALIAS_FLAG);
		p.setAntiAlias(true);
		p.setFilterBitmap(true);
		p.setColor(A1);
		p.setTextAlign(Paint.Align.RIGHT);
		Canvas c = canvas;
		c.save();
		int top = r.top;
		int height = r.height();
		int right = r.right;
		int half = top + height / 2;
		int bottom = r.bottom;
		c.drawText(max, right - 4, top + theme_kline_wide_avLineDataText, p);
		c.drawText(String.valueOf(mid), right - 4, half + 5, p);
		c.drawText("0", right - 4, bottom - 2, p);
		c.restore();
	}

	/**
	 * 画KDJ数据；画WR数据；,OBＶ，ＣＲ，BOLL
	 * 
	 * @param canvas
	 * @param paint
	 * @param r
	 * @param maxTech
	 * @param startIndex
	 */
	public static void drawText_One(Canvas canvas, Paint paint, Rect r,
	        KFloat maxTech, int startIndex)
	{

		KFloat tempKFloat = new KFloat();
		String max = String.valueOf(maxTech);
		tempKFloat = maxTech;
		KFloatUtils.div(tempKFloat, 2);
		Paint p = paint;
		p.setStyle(Paint.Style.FILL);
		p.setTextAlign(Paint.Align.RIGHT);
		p.setColor(A1);
		p.setFlags(Paint.ANTI_ALIAS_FLAG);
		p.setAntiAlias(true);
		p.setFilterBitmap(true);
		Canvas c = canvas;
		c.save();
		int bottom = r.bottom;
		int top = r.top;
		int height = r.height();
		int right = r.right;
		int half = top + height / 2;
		c.drawText(max, right - 4, top + p.getTextSize(), p);
		c.drawText(String.valueOf(tempKFloat), right - 4, half + 5, p);
		c.drawText("0", right - 4, bottom - 2, p);
		c.restore();
	}

	/**
	 * 画MACD数据
	 * 
	 * @param canvas
	 * @param paint
	 * @param r
	 * @param maxTech
	 * @param minTech
	 * @param startIndex
	 */
	public static void drawMACDText(Canvas canvas, Paint paint, Rect r,
	        KFloat maxTech, KFloat minTech, int startIndex)
	{

		String max = String.valueOf(maxTech);
		String min = String.valueOf(minTech);
		KFloatUtils.add(maxTech, minTech);
		KFloatUtils.div(maxTech, 2);
		String mid = String.valueOf(maxTech);
		Paint p = paint;
		p.setStyle(Paint.Style.FILL);
		p.setFlags(Paint.ANTI_ALIAS_FLAG);
		p.setAntiAlias(true);
		p.setFilterBitmap(true);
		p.setTextAlign(Paint.Align.RIGHT);
		p.setColor(A1);
		Canvas c = canvas;
		c.save();
		int top = r.top;
		int height = r.height();
		int right = r.right;
		int half = top + height / 2;
		int bottom = r.bottom;
		c.drawText(max, right - 4, top + p.getTextSize(), p);
		c.drawText(mid, right - 4, half + 5, p);
		c.drawText(min, right - 4, bottom, p);
		c.restore();
	}

	/**
	 * 画DMA数据;画VR数据
	 * 
	 * @param canvas
	 * @param paint
	 * @param r
	 * @param maxTech
	 * @param minTech
	 * @param startIndex
	 */
	public static void drawText_Two(Canvas canvas, Paint paint, Rect r,
	        KFloat maxTech, KFloat minTech, int startIndex)
	{

		String max = String.valueOf(maxTech);
		String min = String.valueOf(minTech);
		KFloatUtils.add(maxTech, minTech);
		KFloatUtils.div(maxTech, 2);
		String mid = String.valueOf(maxTech);
		Paint p = paint;
		p.setStyle(Paint.Style.FILL);
		p.setTextAlign(Paint.Align.RIGHT);
		p.setFlags(Paint.ANTI_ALIAS_FLAG);
		p.setAntiAlias(true);
		p.setFilterBitmap(true);
		p.setColor(A1);
		Canvas c = canvas;
		c.save();
		int top = r.top;
		int height = r.height();
		int right = r.right;
		int half = top + height / 2;
		int bottom = r.bottom;
		c.drawText(max, right - 4, top + p.getTextSize(), p);
		c.drawText(mid, right - 4, half + 5, p);
		c.drawText(min, right - 4, bottom, p);
		c.restore();
	}

	/**
	 * 画k线的起始日期及结束日期
	 * 
	 * @param canvas
	 * @param r  技术指标Label显示区
	 * @param consultRect K线显示区
	 * @param nTime_s
	 * @param startIndex 起始索引
	 * @param maxNums 终止日期偏移索引数
	 * @param kLineType k线周期类型
	 */
	public static void drawStartEndDate(Canvas canvas, Rect r, Rect consultRect,
	        int[] nTime_s, int startIndex,int maxNums,short kLineType)
	{
		if (nTime_s == null || maxNums == 0){
			return;
		}
		int left = consultRect.left + 2;
	
		//int bottom = r.bottom - 2;
		int bottom = consultRect.bottom ;
		Canvas c = canvas;
		Paint p = new Paint();
		p.setFlags(Paint.ANTI_ALIAS_FLAG);
		p.setAntiAlias(true);
		p.setFilterBitmap(true);
		p.setTextSize(kline_scape_ma);
		p.setStyle(Paint.Style.FILL);
		p.setTextAlign(Paint.Align.LEFT);
		p.setColor(A3);
		c.save();

		
		String startDate = formatDate(nTime_s[startIndex],kLineType);
		
		int endIndex = startIndex + maxNums - 1;
		if (endIndex > (nTime_s.length-1))
			endIndex = nTime_s.length-1;
		String endDate = formatDate(nTime_s[endIndex],kLineType);;
		
//		if (kLineType == ProtocolConstant.KX_DAY
//				|| kLineType == ProtocolConstant.KX_WEEK 
//				|| kLineType == ProtocolConstant.KX_MONTH){
//			int len = 0;
//			StringBuffer tmp = null;
//			if (!StringUtils.isEmpty(startDate))
//			{
//				len = startDate.length();
//				tmp = new StringBuffer(startDate);
//				tmp.insert(len-4, "/");
//				tmp.insert(len-1, "/");
//				startDate = tmp.toString();
//				tmp = null;
//			}
//			
//			if (!StringUtils.isEmpty(endDate))
//			{
//				len = endDate.length();
//				tmp = new StringBuffer(endDate);
//				tmp.insert(len-4, "/");
//				tmp.insert(len-1, "/");
//				endDate = tmp.toString();
//				tmp = null;
//			}
//			
//			 
//		}else{
//			//分钟周期
//			int len = 0;
//			StringBuffer tmp = null;
//			if (!StringUtils.isEmpty(startDate))
//			{
//				len = startDate.length();
//				tmp = new StringBuffer(startDate);
//				tmp.insert(len-6, "/");
//				tmp.insert(len-3, "/");
//				tmp.insert(len, ":");
//				startDate = tmp.toString();
//				tmp = null;
//			}
//			
//			if (!StringUtils.isEmpty(endDate))
//			{
//				len = endDate.length();
//				tmp = new StringBuffer(endDate);
//				tmp.insert(len-6, "/");
//				tmp.insert(len-3, "/");
//				tmp.insert(len, ":");
//				endDate = tmp.toString();
//				tmp = null;
//			}
//		}
//		
		
		c.drawText(startDate, left, bottom - 2, p);
		
		
		float w = p.measureText(endDate);		
		c.drawText(endDate, consultRect.right - w - 2,bottom - 2, p);
		c.restore();
	}
	
	
	
	/**
	 * 画技术指标类型名称说明，竖屏使用
	 * @param canvas
	 * @param r
	 * @param flag 指标类型索引
	 */
	public static void drawTechTypeText_sp(Canvas canvas, Rect r, int[] values, 
			int[] teche1,int[] tech2,int[] tech3,int[] tech4,int[] tech5,
			int startIndex,int endIndex, int flag)
	{
		/** 技术指标类型名称说明 */
		String[] m_zbNames = { "VOL(5,10,30)", "KDJ(9,3,3)", "MACD(12,26,9)",
		        "DMA(10,50,10)", "WR(10,6)", "VR(26,6)", "OBV(30)",
		        "CR(26,10,20,40,62)", "BOLL(10)" };

		// int left = r.left + 2;
		int left = r.left + 50;
		int bottom = r.bottom - 2;
		Canvas c = canvas;
		Paint p = new Paint();
		p.setFlags(Paint.ANTI_ALIAS_FLAG);
		p.setAntiAlias(true);
		p.setFilterBitmap(true);
		p.setTextSize(kline_scape_ma);
		p.setStyle(Paint.Style.FILL);
		p.setTextAlign(Paint.Align.LEFT);
		
		//p.setColor(Color.YELLOW);
		p.setColor(Color.WHITE);
		c.save();
		switch (flag)
		{
			case 0:
				c.drawText(m_zbNames[0], left, bottom - 2, p);
				
				break;
			case 1:
				c.drawText(m_zbNames[1], left, bottom - 2, p);
				break;
			case 2:
				c.drawText(m_zbNames[2], left, bottom - 2, p);
				break;
			case 3:
				c.drawText(m_zbNames[3], left, bottom - 2, p);
				break;
			case 4:
				c.drawText(m_zbNames[4], left, bottom - 2, p);
				break;
			case 5:
				c.drawText(m_zbNames[5], left, bottom - 2, p);
				break;
			case 6:
				c.drawText(m_zbNames[6], left, bottom - 2, p);
				break;
			case 7:
				c.drawText(m_zbNames[7], left, bottom - 2, p);
				break;
			case 8:
				c.drawText(m_zbNames[8], left, bottom - 2, p);
				break;
			default:
				break;
		}
		c.restore();
	}

	/**
	 * 画技术指标类型名称说明，竖屏使用
	 * @param canvas
	 * @param r
	 * @param flag 指标类型索引
	 */
	public static void drawTechTypeText_sp(Canvas canvas, Rect r, int flag)
	{
		/** 技术指标类型名称说明 */
		String[] m_zbNames = { "VOL(5,10,30)", "KDJ(9,3,3)", "MACD(12,26,9)",
		        "DMA(10,50,10)", "WR(10,6)", "VR(26,6)", "OBV(30)",
		        "CR(26,10,20,40,62)", "BOLL(10)" };

		// int left = r.left + 2;
		int left = r.left + 150;
		int bottom = r.bottom - 2;
		Canvas c = canvas;
		Paint p = new Paint();
		p.setFlags(Paint.ANTI_ALIAS_FLAG);
		p.setAntiAlias(true);
		p.setFilterBitmap(true);
		p.setTextSize(kline_scape_ma);
		p.setStyle(Paint.Style.FILL);
		p.setTextAlign(Paint.Align.LEFT);
		// p.setColor(A1);
		p.setColor(Color.YELLOW);
		c.save();
		switch (flag)
		{
			case 0:
				c.drawText(m_zbNames[0], left, bottom - 2, p);
				break;
			case 1:
				c.drawText(m_zbNames[1], left, bottom - 2, p);
				break;
			case 2:
				c.drawText(m_zbNames[2], left, bottom - 2, p);
				break;
			case 3:
				c.drawText(m_zbNames[3], left, bottom - 2, p);
				break;
			case 4:
				c.drawText(m_zbNames[4], left, bottom - 2, p);
				break;
			case 5:
				c.drawText(m_zbNames[5], left, bottom - 2, p);
				break;
			case 6:
				c.drawText(m_zbNames[6], left, bottom - 2, p);
				break;
			case 7:
				c.drawText(m_zbNames[7], left, bottom - 2, p);
				break;
			case 8:
				c.drawText(m_zbNames[8], left, bottom - 2, p);
				break;
			default:
				break;
		}
		c.restore();
	}

	/**
	 * 横屏k线起始时间
	 * 
	 * @param canvas
	 * @param r
	 * @param nTime_s
	 * @param startIndex
	 */
	public static void drawStartDate_ls(Canvas canvas, Rect r, int[] nTime_s,
	        int startIndex)
	{
		int left = r.left + r.width() / 4;
		int bottom = r.bottom - 2;
		Canvas c = canvas;
		Paint p = new Paint();
		p.setFlags(Paint.ANTI_ALIAS_FLAG);
		p.setAntiAlias(true);
		p.setFilterBitmap(true);
		p.setTextSize(kline_scape_ma);
		p.setStyle(Paint.Style.FILL);
		p.setTextAlign(Paint.Align.LEFT);
		p.setColor(A3);
		c.save();

		c.drawText("" + nTime_s[startIndex], left, bottom, p);
		c.restore();
	}

	/**
	 * 画技术指标类型名称，横屏使用
	 * @param canvas
	 * @param r
	 * @param flag
	 */
	public static void drawTechTypeText(Canvas canvas, Rect r, int flag)
	{
		/** 技术指标类型名称说明 */
		String[] m_zbNames = { "VOL(5,10,30)", "KDJ(9,3,3)", "MACD(12,26,9)",
		        "DMA(10,50,10)", "WR(10,6)", "VR(26,6)", "OBV(30)",
		        "CR(26,10,20,40,62)", "BOLL(10)" };

		int left = r.left + 2;
		int bottom = r.bottom - 2;
		Canvas c = canvas;
		Paint p = new Paint();
		p.setFlags(Paint.ANTI_ALIAS_FLAG);
		p.setAntiAlias(true);
		p.setFilterBitmap(true);
		p.setTextSize(kline_scape_ma);
		p.setStyle(Paint.Style.FILL);
		p.setTextAlign(Paint.Align.LEFT);
		p.setColor(A1);
		c.save();
		switch (flag)
		{
			case 0:
				c.drawText(m_zbNames[0], left, bottom, p);
				break;
			case 1:
				c.drawText(m_zbNames[1], left, bottom, p);
				break;
			case 2:
				c.drawText(m_zbNames[2], left, bottom, p);
				break;
			case 3:
				c.drawText(m_zbNames[3], left, bottom, p);
				break;
			case 4:
				c.drawText(m_zbNames[4], left, bottom - 2, p);
				break;
			case 5:
				c.drawText(m_zbNames[5], left, bottom, p);
				break;
			case 6:
				c.drawText(m_zbNames[6], left, bottom, p);
				break;
			case 7:
				c.drawText(m_zbNames[7], left, bottom, p);
				break;
			case 8:
				c.drawText(m_zbNames[8], left, bottom, p);
				break;
			default:
				break;
		}
		c.restore();
	}

	// /**
	// * 得到最高值
	// *
	// * @param pos
	// * @param data
	// * @return
	// */
	// public static KFloat getMax(int pos, int[] data, int screenCount)
	// {
	// // KFloat temp = null;
	// // if (data != null && pos < data.length)
	// // {
	// // temp = new KFloat(data[pos]);
	// //
	// // KFloat tf = new KFloat();
	// // for (int i = pos; i < data.length; i++)
	// // {
	// //
	// // tf.init(data[i]);
	// // if (KFloat.compare(temp, tf) == -1)
	// // temp.init(data[i]);
	// //
	// // }
	// //
	// // }
	// KFloat temp = null;
	// Logger.i("", "--------pos---" + pos);
	// Logger.i("", "--------data.length---" + data.length);
	// Logger.i("", "--------screenCount---" + screenCount);
	// if (data != null && pos <= data.length
	// && data.length >= screenCount + pos)
	// {// 一屏内的最大值
	// temp = new KFloat(data[pos]);
	// KFloat tf = new KFloat();
	// for (int i = pos; i < pos + screenCount; i++)
	// {
	// tf.init(data[i]);
	// if (KFloat.compare(temp, tf) == -1)
	// temp.init(data[i]);
	// }
	//
	// } else if (data != null && pos < data.length)
	// {
	// temp = new KFloat(data[pos]);
	// KFloat tf = new KFloat();
	// for (int i = pos; i < data.length; i++)
	// {
	// tf.init(data[i]);
	// if (KFloat.compare(temp, tf) == -1)
	// temp.init(data[i]);
	// }
	// }
	// return temp;
	// }

	/**
	 * 得到最高值
	 * 
	 * @param pos
	 * @param data
	 * @return
	 */
	public static KFloat getMax(int formPostion, int toPosition, int[] data)
	{
		KFloat temp = new KFloat();
		if (data != null && formPostion < data.length && toPosition < data.length)
		{
			temp = new KFloat(data[formPostion]);
			KFloat tf = new KFloat();
			for (int i = formPostion; i <= toPosition; i++)
			{
				tf.init(data[i]);
				if (KFloat.compare(temp, tf) == -1)
					temp.init(data[i]);

			}
		}
		return temp;
	}

	// /**
	// * 得到最高值
	// *
	// * @param pos
	// * @param data
	// * @return
	// */
	// public static KFloat getMax(int pos, int[] data,int[] av1,int[] av2,int[]
	// av3)
	// {
	// KFloat temp = null;
	// if (data != null && pos < data.length)
	// {
	// temp = new KFloat(data[pos]);
	//
	// KFloat tf = new KFloat();
	// for (int i = pos; i < data.length; i++)
	// {
	//
	// //tf.init(data[i]);
	// tf.init(getMaxValue(data[i], av1[i], av2[i], av3[i]));
	//
	// if (KFloat.compare(temp, tf) == -1)
	// temp.init(data[i]);
	//
	// }
	//
	// }
	// return temp;
	// }
	//
	// /**
	// * bi
	// * @param a
	// * @param b
	// * @param c
	// * @param d
	// * @return
	// */
	// private static int getMaxValue(int a,int b,int c,int d)
	// {
	// int temp1,temp2;
	// temp1=Math.max(a, b);
	// temp2=Math.max(c, d);
	// return Math.max(temp1, temp2);
	//
	// }
	// private static int getMinValue(int a,int b,int c,int d)
	// {
	// int temp1,temp2;
	// temp1=Math.min(a, b);
	// temp2=Math.min(c, d);
	// return Math.min(temp1, temp2);
	//
	// }
	// /**
	// * 得到最低值
	// *
	// * @param pos
	// * @param data
	// * @return
	// */
	// public static KFloat getMin(int pos, int[] data, int screenCount)
	// {
	// // KFloat temp = null;
	// // if (data != null && pos < data.length)
	// // {
	// // temp = new KFloat(data[pos]);
	// // KFloat tf = new KFloat();
	// // for (int i = pos; i < data.length; i++)
	// // {
	// // if (temp.nValue == 0)
	// // {
	// // i++;
	// // }
	// // if (i < data.length)
	// // {
	// // tf.init(data[i]);
	// // }
	// //
	// // if (KFloatUtils.compare(temp, tf) == 1)
	// // temp.init(data[i]);
	// // }
	// // }
	// KFloat temp = null;
	// if (data != null && pos < data.length
	// && data.length >= screenCount + pos)
	// {
	// temp = new KFloat(data[pos]);
	// KFloat tf = new KFloat();
	// for (int i = pos; i < pos + screenCount; i++)
	// {// 一屏内的最小值
	// if (temp.nValue == 0)
	// {
	// i++;
	// }
	// if (i < data.length)
	// {//
	// tf.init(data[i]);
	// }
	// if (KFloatUtils.compare(temp, tf) == 1)
	// temp.init(data[i]);
	// }
	// }else if (data != null && pos < data.length)
	// {
	// temp = new KFloat(data[pos]);
	// KFloat tf = new KFloat();
	// for (int i = pos; i < data.length; i++)
	// {
	// if (temp.nValue == 0)
	// {
	// i++;
	// }
	// if (i < data.length)
	// {
	// tf.init(data[i]);
	// }
	//
	// if (KFloatUtils.compare(temp, tf) == 1)
	// temp.init(data[i]);
	// }
	// }
	// return temp;
	// }

	/**
	 * 得到最低值
	 * 
	 * @param pos
	 * @param data
	 * @return
	 */
	public static KFloat getMin(int pos, int[] data)
	{
		KFloat temp = null;
		if (data != null && pos < data.length)
		{
			temp = new KFloat(data[pos]);
			KFloat tf = new KFloat();
			for (int i = pos; i < data.length; i++)
			{
				if (temp.nValue == 0)
				{
					i++;
				}
				if (i < data.length)
				{
					tf.init(data[i]);
				}

				if (KFloatUtils.compare(temp, tf) == 1)
					temp.init(data[i]);
			}
		}
		return temp;
	}

	// public static KFloat getMin(int pos,int[] data,int[] av1,int[] av2,int[]
	// av3)
	// {
	// KFloat temp = null;
	// if (data != null && pos < data.length)
	// {
	// temp = new KFloat(data[pos]);
	// KFloat tf = new KFloat();
	// for (int i = pos; i < data.length; i++)
	// {
	// if (temp.nValue == 0)
	// {
	// i++;
	// }
	// if (i < data.length)
	// {
	// // tf.init(data[i]);
	// tf.init(getMinValue(data[i], av1[i], av2[i], av3[i]));
	// }
	//
	// if (KFloatUtils.compare(temp, tf) == 1)
	// temp.init(data[i]);
	// }
	// }
	// return temp;
	// }
	// --------------------------------------------------------------------横屏 横屏

	/**
	 * 计算最高价与最低价之间的价格
	 * 
	 * @param max
	 *            交易最高价
	 * @param min
	 *            交易最低价
	 * @return
	 */
	public static String[] calculateStepData(KFloat max, KFloat min)
	{
		if (max == null || min == null)
		{
			return null;
		}
		String[] stepData = { "---", "---", "---", "---", "---" };
		try
		{
			float ma = Float.valueOf(max.toString());
			float mi = Float.valueOf(min.toString());
			float step = 0;
			float temp = mi;
			for (int i = 0; i < 5; i++)
			{
				step = (ma - mi) / 6;
				temp += step;
				DecimalFormat df = new DecimalFormat("0.00");// 格式化小数，不足的补0
				String filesize = df.format(temp);// 返回的是String类型的
				stepData[i] = filesize;
			}
		} catch (Exception e)
		{
			// TODO: handle exception
		}

		return stepData;
	}

	/**
	 * 画k线对应的价格数据--横屏
	 * 
	 * @param c
	 * @param p
	 * @param r
	 * @param suc
	 */

	public static void drawPriceData(Canvas c, Paint p, Rect r, KFloat maxP,
	        KFloat minP, String[] stepData, boolean suc)
	{
		if (suc)
		{
			Canvas canvas = c;
			Paint paint = p;
			Rect rect = r;
			p.setStyle(Paint.Style.FILL);
			p.setTextAlign(Align.RIGHT);
			p.setColor(A1);
			p.setTextSize(kline_scape_ma);
			int height = KLINE_SCAPE_HEIGHT_KLINE;
			int step = 15;//
			int subHeight = (height - step) * 1 / 6;// 均线框中虚线之间的高度
			int nbottom = rect.bottom - step;
			canvas.save();
			if (maxP == null)
				return;
			canvas.drawText(maxP.toString(), rect.left - 2, rect.top + 15,
			        paint);
			for (int i = 0; i < 5; i++)
			{
				canvas.drawText(String.valueOf(ArrayUtils.getValue(stepData, i, "")), rect.left - 2,
				        nbottom - ((i + 1) * subHeight), paint);

			}
			canvas.drawText(minP.toString(), rect.left - 2, nbottom - 2, paint);
			canvas.restore();
		}
	}

	/**
	 * 画十字线横屏
	 * 
	 * @param index
	 * @param current
	 * @param data
	 * @param dataLength
	 * @param max
	 * @param min
	 * @param rectOne
	 * @param canvas
	 * @param paint
	 * @param flag
	 */
	public static void drawCrossLineLandscape(int index, int current,
	        int[] data, int dataLength, KFloat max, KFloat min, Rect rectOne,
	        Rect rectTwo, Canvas canvas, Paint paint, boolean flag)
	{
		int newY_c;
		if (flag)
		{

			int bottom = rectOne.bottom - 15;
			int price = max.nValue - min.nValue;
			KFloat startKFloat = new KFloat();

			Paint p = paint;
			p.setStyle(Paint.Style.FILL);
			p.setFlags(Paint.ANTI_ALIAS_FLAG);
			p.setAntiAlias(true);
			p.setFilterBitmap(true);
			p.setStrokeWidth(SCAPE_HEIGHT_LINESCAPE);

			p.setColor(CROSSLINECOLOR);
			p.setStrokeWidth(0.5f);
			p.setStyle(Paint.Style.FILL);
			Canvas cv = canvas;
			cv.save();
			if (price == 0)
			{

				return;
			}
			if (current < dataLength)
			{
				newY_c = bottom
				        - (startKFloat.init(data[current]).nValue - min.nValue)
				        * (KLINE_SCAPE_HEIGHT_KLINE - 15) / price;
				cv.drawLine(rectOne.left, newY_c, rectOne.right, newY_c, p);
			}
			cv.drawLine(index, rectOne.top, index, rectTwo.bottom - 1, p);
			cv.restore();
		}
	}

	/**
	 * 画横屏均线数据
	 * 
	 * @param canvas
	 * @param paint
	 * @param rect
	 * @param currentIndex
	 * @param ma1
	 * @param ma2
	 * @param ma3
	 * @param ma4
	 * @param flag
	 */
	public static void drawAMTextsLandscape(Canvas canvas, Paint p, Rect rect,
	        int currentIndex, int[] ma1, int[] ma2, int[] ma3, int[] ma4,int[] ma5,int[] maDays,
	        boolean flag)
	{

		KFloat tempKFloat = new KFloat();
		int subWidth = rect.width() * 1 / 5;
		int step_w = subWidth * 1 / 4;
		int step_h = rect.top + 15;
		Paint paint = p;

		p.setTextSize(kline_scape_ma);

		int bottom = rect.bottom;
		if (flag)
		{
		    
		    int tmpIndex = -1;
            int[] tmpMALines = null;
        
            for (int i = 0;i<maDays.length;i++){
                if (maDays[i]>0){
                    tmpIndex++;
                    if (i == 0){
                        paint.setColor(A0); //第1根，白色
                        tmpMALines = ma1;
                    }else if (i==1){
                        paint.setColor(A3); //第2根，黄色
                        tmpMALines = ma2;
                    }else if (i == 2){
                        paint.setColor(A9); //第3根，紫色
                        tmpMALines = ma3;
                    }else if (i == 3){
                        paint.setColor(A2); //第4根，绿色
                        tmpMALines = ma4;
                    }else if (i == 4){
                        paint.setColor(A5); //第5根，蓝色
                        tmpMALines = ma5;
                    } //目前最多支持5根均线
                    
                    paint.setStyle(Paint.Style.FILL);
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setTextAlign(Paint.Align.LEFT);
            
                    if (tmpMALines != null)
                    {
                        canvas.save();
                        canvas.drawText(String.format("MA%s:%s", maDays[i],String.valueOf(tempKFloat
                                                .init(tmpMALines[currentIndex]))), tmpIndex
                                        * subWidth + rect.left + 5, step_h, paint);
                        canvas.restore();
                    } else
                    {
                        canvas.save();
                        canvas.drawText(String.format("MA%s:--", maDays[i]), tmpIndex
                        * subWidth + rect.left + 5, step_h, paint);
                        canvas.restore();
                    }
                }
            }
		    
//			paint.setColor(A0);
//			paint.setTextAlign(Paint.Align.LEFT);
//			canvas.save();
//
//			if (ma1 != null)
//			{
//				canvas.drawText(
//				        "MA5:"
//				                + String.valueOf(tempKFloat
//				                        .init(ma1[currentIndex])), 0 * step_w
//				                + rect.left + 5, step_h, paint);
//			} else
//			{
//				canvas.drawText("MA5:- -", 0 * step_w + rect.left + 5, step_h,
//				        paint);
//			}
//			canvas.restore();
//			paint.setColor(A3);
//			paint.setStyle(Paint.Style.FILL);
//			paint.setFlags(Paint.ANTI_ALIAS_FLAG);
//			paint.setTextAlign(Paint.Align.LEFT);
//			canvas.save();
//			if (ma2 != null)
//			{
//				canvas.drawText(
//				        "MA10:"
//				                + String.valueOf(tempKFloat
//				                        .init(ma2[currentIndex])), 1 * step_w
//				                + rect.left + 5, step_h, paint);
//			} else
//			{
//				canvas.drawText("MA10:- -", 1 * step_w + rect.left + 5, step_h,
//				        paint);
//			}
//			canvas.restore();
//
//			// paint.setColor(A2);
//			// paint.setStyle(Paint.Style.FILL);
//			// paint.setFlags(Paint.ANTI_ALIAS_FLAG);
//			// paint.setTextAlign(Paint.Align.LEFT);
//			// canvas.save();
//			// if (ma3 != null)
//			// {
//			// canvas.drawText(
//			// "20:"
//			// + String.valueOf(tempKFloat
//			// .init(ma3[currentIndex])), 2 * step_w
//			// + rect.left + 5, step_h, paint);
//			// } else
//			// {
//			// canvas.drawText("20:- -", 2 * step_w + rect.left + 5, step_h,
//			// paint);
//			// }
//			//
//			// canvas.restore();
//			paint.setColor(A9);
//			paint.setStyle(Paint.Style.FILL);
//			paint.setFlags(Paint.ANTI_ALIAS_FLAG);
//			paint.setTextAlign(Paint.Align.LEFT);
//			canvas.save();
//			if (ma4 != null)
//			{
//				canvas.drawText(
//				        "MA30:"
//				                + String.valueOf(tempKFloat
//				                        .init(ma4[currentIndex])), 2 * step_w
//				                + rect.left + 5, step_h, paint);
//			} else
//			{
//				canvas.drawText("MA30:- -", 2 * step_w + rect.left + 5, step_h,
//				        paint);
//			}
//			canvas.restore();
		}

	}

	/**
	 * 画交易量数据
	 * 
	 * @param canvas
	 * @param paint
	 * @param r
	 * @param cjsl
	 * @param startIndex
	 */
	public static void drawVOLText_LS(Canvas canvas, Paint paint, Rect r,
	        int[] cjsl, int startIndex, int screenCount)
	{
		KFloat kFloat = new KFloat();
		String max;
		// kFloat = getMax(startIndex, cjsl, screenCount);
		kFloat = getMax(startIndex, cjsl.length - 1, cjsl);
		max = String.valueOf(kFloat);
		KFloatUtils.div(kFloat, 2);
		String mid = String.valueOf(kFloat);

		Paint p = paint;
		p.setStyle(Paint.Style.FILL);
		p.setTextAlign(Paint.Align.RIGHT);
		p.setColor(A1);
		p.setTextSize(kline_scape_ma);
		Canvas c = canvas;
		c.save();
		int top = r.top;
		int height = r.height();
		int right = r.left;
		int half = top + height / 2;
		c.drawText(max, right - 4, top + 12, p);
		c.drawText(String.valueOf(mid), right - 4, half + 5, p);
		c.restore();
	}

	public static void drawMACDText_LS(Canvas canvas, Paint paint, Rect r,
	        KFloat maxTech, KFloat minTech, int startIndex)
	{

		String max = String.valueOf(maxTech);
		String min = String.valueOf(minTech);
		KFloatUtils.add(maxTech, minTech);
		KFloatUtils.div(maxTech, 2);
		String mid = String.valueOf(maxTech);
		Paint p = paint;
		p.setStyle(Paint.Style.FILL);
		p.setTextAlign(Paint.Align.RIGHT);
		p.setTextSize(kline_scape_ma);
		p.setColor(A1);
		Canvas c = canvas;
		c.save();
		int top = r.top;
		int height = r.height();
		int right = r.left;
		int half = top + height / 2;
		int bottom = r.bottom;
		c.drawText(max, right - 4, top + 12, p);
		c.drawText(mid, right - 4, half + 5, p);
		c.drawText(min, right - 4, bottom, p);
		c.restore();
	}

	/**
	 * 画KDJ数据；画WR数据；,OBＶ，ＣＲ，BOLL
	 * 
	 * @param canvas
	 * @param paint
	 * @param r
	 * @param maxTech
	 * @param startIndex
	 */
	public static void drawText_One_LS(Canvas canvas, Paint paint, Rect r,
	        KFloat maxTech, int startIndex)
	{

		KFloat tempKFloat = new KFloat();
		String max = String.valueOf(maxTech);
		tempKFloat = maxTech;
		KFloatUtils.div(tempKFloat, 2);
		Paint p = paint;
		p.setStyle(Paint.Style.FILL);
		p.setTextAlign(Paint.Align.RIGHT);
		p.setTextSize(kline_scape_ma);
		p.setColor(A1);
		Canvas c = canvas;
		c.save();
		int top = r.top;
		int height = r.height();
		int right = r.left;
		int half = top + height / 2;
		c.drawText(max, right - 4, top + 12, p);
		c.drawText(String.valueOf(tempKFloat), right - 4, half + 5, p);
		c.restore();
	}

	/**
	 * 画DMA数据;画VR数据
	 * 
	 * @param canvas
	 * @param paint
	 * @param r
	 * @param maxTech
	 * @param minTech
	 * @param startIndex
	 */
	public static void drawText_Two_LS(Canvas canvas, Paint paint, Rect r,
	        KFloat maxTech, KFloat minTech, int startIndex)
	{

		String max = String.valueOf(maxTech);
		String min = String.valueOf(minTech);
		KFloatUtils.add(maxTech, minTech);
		KFloatUtils.div(maxTech, 2);
		String mid = String.valueOf(maxTech);
		Paint p = paint;
		p.setStyle(Paint.Style.FILL);
		p.setTextAlign(Paint.Align.RIGHT);
		p.setTextSize(kline_scape_ma);
		p.setColor(A1);
		Canvas c = canvas;
		c.save();
		int top = r.top;
		int height = r.height();
		int right = r.left;
		int half = top + height / 2;
		int bottom = r.bottom;
		c.drawText(max, right - 4, top + 12, p);
		c.drawText(mid, right - 4, half + 5, p);
		c.drawText(min, right - 4, bottom, p);
		c.restore();
	}

	float temp = 0;
	float codeLength = 0;
	float xjLength = 0;
	float zdLength = 0;

	/**
	 * 
	 * @param canvas
	 * @param currentIndex
	 * @param name
	 * @param code
	 * @param xj
	 * @param zd
	 * @param zdf
	 * @param flag 涨跌判断 0:涨，1：跌，2：平
	 */
	public static void drawTitleText(Canvas canvas, int currentIndex,
	        String name, String code, int xj, int zd, int zdf, int flag)
	{
		Paint paint = new Paint();
		Canvas cv = canvas;
		KFloat temFloat = new KFloat();
		float temp = 0;
		float codeLength = 0;
		float xjLength = 0;
		float zdLength = 0;

		if (name != null)
		{
			paint.setTextSize(kline_scape_name);
			paint.setColor(CLR_KLINE_NAME);
			paint.setStyle(Paint.Style.FILL);
			float width_name = paint.measureText(name);
			temp = width_name;
			cv.save();
			cv.drawText(name, 3, SCAPE_HEIGHT_TITLE - 8, paint);
			cv.restore();
		}

		if (code != null)
		{
			paint.setTextSize(kline_scape_code);
			paint.setColor(CLR_KLINE_CODE);
			paint.setStyle(Paint.Style.FILL);
			codeLength = paint.measureText(code);
			cv.save();
			cv.drawText(code, temp + 15, SCAPE_HEIGHT_TITLE - 8, paint);
			cv.restore();
		}
		if (flag == 0 || flag==1 || flag==2)
		{
			paint.setTextSize(kline_scape_code);
			if (flag == 0){
				paint.setColor(CLR_KLINE_UP);
			}else if(flag == 1){
				paint.setColor(CLR_KLINE_DOWN);
			}else{
				paint.setColor(CLR_KLINE_TIE);
			}
			if (zd==8){
				paint.setColor(CLR_KLINE_TIE);
			}
			paint.setStyle(Paint.Style.FILL);
			
			xjLength = paint.measureText(String.valueOf(temFloat.init(xj)));
			zdLength = paint.measureText(zd==8?"0.00":String.valueOf(temFloat.init(zd)));
			cv.save();
			
			cv.drawText(String.valueOf(temFloat.init(xj)), temp + 15
			        + codeLength + 15, SCAPE_HEIGHT_TITLE - 8, paint);
			cv.drawText(zd==8?"0.00":(String.valueOf(temFloat.init(zd))), temp + 15
			        + codeLength + 15 + xjLength + 15, SCAPE_HEIGHT_TITLE - 8,
			        paint);
			cv.drawText(zdf==8?"0.00":(String.valueOf(temFloat.init(zdf)) + "%"), temp
			        + 15 + codeLength + 15 + xjLength + 15 + zdLength + 15,
			        SCAPE_HEIGHT_TITLE - 8, paint);
			cv.restore();

		}
//		if (flag == 1)
//		{
//
//			paint.setTextSize(kline_scape_code);
//			paint.setColor(CLR_KLINE_DOWN);
//			paint.setStyle(Paint.Style.FILL);
//			xjLength = paint.measureText(String.valueOf(temFloat.init(xj)));
//			zdLength = paint.measureText(String.valueOf(temFloat.init(zd)));
//			cv.save();
//			cv.drawText(String.valueOf(temFloat.init(xj)), temp + 15
//			        + codeLength + 15, SCAPE_HEIGHT_TITLE - 8, paint);
//			cv.drawText(String.valueOf(temFloat.init(zd)), temp + 15
//			        + codeLength + 15 + xjLength + 15, SCAPE_HEIGHT_TITLE - 8,
//			        paint);
//			cv.drawText(String.valueOf(temFloat.init(zdf) + "%"), temp + 15
//			        + codeLength + 15 + xjLength + 15 + zdLength + 15,
//			        SCAPE_HEIGHT_TITLE - 8, paint);
//			cv.restore();
//		}
//		if (flag == 2)
//		{
//
//			paint.setTextSize(kline_scape_code);
//			paint.setColor(CLR_KLINE_TIE);
//			paint.setStyle(Paint.Style.FILL);
//			xjLength = paint.measureText(String.valueOf(temFloat.init(xj)));
//			zdLength = paint.measureText(String.valueOf(temFloat.init(zd)));
//			cv.save();
//			cv.drawText(String.valueOf(temFloat.init(xj)), temp + 15
//			        + codeLength + 15, SCAPE_HEIGHT_TITLE - 8, paint);
//			cv.drawText(String.valueOf(temFloat.init(zd)), temp + 15
//			        + codeLength + 15 + xjLength + 15, SCAPE_HEIGHT_TITLE - 8,
//			        paint);
//			cv.drawText(String.valueOf(temFloat.init(zdf) + "%"), temp + 15
//			        + codeLength + 15 + xjLength + 15 + zdLength + 15,
//			        SCAPE_HEIGHT_TITLE - 8, paint);
//			cv.restore();
//		}

	}

	public static int KlinecycleType;

	public static void setCycleType(int type)
	{
		KlinecycleType = type;
	}

	public static int getCycleType()
	{
		return KlinecycleType;
	}
	
	public  static short KlineType = 0x201;
	public static void setKlineType(short type){
		KlineType = type;
	}
	public static short getKlineType(){
		return KlineType;
	}
	
	public  static int KlineTechType;
	public static void setKlineTechType(int type){
		KlineTechType = type;
	}
	public static int getKlineTechType(){
		return KlineTechType;
	}
	
	public  static int KlineTechIndex = 0;
	public static void setKlineTechIndex(int index){
		KlineTechIndex = index;
	}
	public static int getKlineTechIndex(){
		return KlineTechIndex;
	}
	
	public  static int KlineFuQuanIndex = 0;
	public static void setKlineFuQuanIndex(int index){
		KlineFuQuanIndex = index;
	}
	public static int getKlineFuQuanIndex(){
		return KlineFuQuanIndex;
	}
	private static boolean isCustomAvg = false;

	/**
	 * 计算MA的值
	 * 
	 * @param days  MA天数数组
	 * @param shoupan 收盘价数组
	 * @param defaultMA5 默认5日均线值
	 * @param defaultMA10 默认10日均线值
	 * @param defaultMA30 默认30日均线值
	 * @param defaultMA40 默认40日均线值
	 * @param defaultMA60 默认60日均线值
	 * @return MA均线数组. values[均线类型][每条均线的均线数据]
	 */
	 public static int[][] getMAValues(int[] days,int[] shoupan,int[] defaultMA5,int[] defaultMA10,int[] defaultMA30,
			 int[] defaultMA40, int[] defaultMA60){
	        if (days == null || days.length==0){
	            return null; //若天数数组为空，则返回空
	        }
	        
	        if (shoupan == null || shoupan.length==0){
	            return null; //若收盘价数组为空
	        }
	        
	        int daysLen = days.length;
	        int shoupanLen = shoupan.length;
	        int[][] maValues = new int[daysLen][shoupanLen];

		 if(defaultMA5 != null || defaultMA10 != null || defaultMA30 != null) {
			 boolean useAllDefault = true;  //是否全部采用默认值
			 //先计算默认值（1,5，10，30）
			 for (int j = 0; j < daysLen; j++) {
				 if (days[j] <= 0) {
					 maValues[j] = null; //未定义均线
				 } else if (days[j] == 1) {
					 maValues[j] = shoupan; //均线定义为1日，直接用收盘价表示
				 } else if (days[j] == 5) {
					 maValues[j] = defaultMA5;  //5日均线，用默认值
				 } else if (days[j] == 10) {
					 maValues[j] = defaultMA10; //10日均线，用默认值
				 } else if (days[j] == 30) {
					 maValues[j] = defaultMA30; //30日均线，用默认值
				 } else if (days[j] > shoupanLen) {
					 maValues[j] = null;  //设定的均线日期大于实际取得的收盘价天数，则无均线值
				 } else {
					 useAllDefault = false;
				 }
			 }

			 if (useAllDefault) {
				 return maValues; //全部采用默认值，直接返回
			 }
		 }else{
			 isCustomAvg = true;// 当服务器没有均线数据时采用自定义计算的
		 }
	        
	        boolean[] beginAverage = new boolean[daysLen]; //是否开始计算MA值，当计算n日均线时，前n-1天的数据是不需要计算的，默认值为0（即模拟浮点值为8）
	        KFloat[] tmpSumValue = new KFloat[daysLen]; //临时和
	        KFloat[] tmpKFloat = new KFloat[daysLen]; //临时中间值
	        for (int i = 0;i<daysLen;i++){
	            tmpSumValue[i] = new KFloat();
	            tmpKFloat[i] = new KFloat();
	        }
	        
	        int tmpDay =  0; //计算的天数
	        for (int i =0;i<shoupanLen;i++){  //先循环收盘价格
	           //tmpSumValue = new KFloat(); //临时和
	            
	           
	            for (int j=0;j<daysLen;j++){ //循环天数

	                if (days[j]==0 || days[j] == 1 || !isCustomAvg  || days[j]>shoupanLen){
	                   //空或默认值
	                }else{//计算均线值
	                  
    	                tmpDay =  days[j]; //计算的天数临时值    	                    
    	               
    	                        
                        if (!beginAverage[j] && i == tmpDay - 1) {
                            beginAverage[j] = true;
                        }
    
                        // 求和
                        
                        tmpKFloat[j] = new KFloat(shoupan[i]);
                       
                        if (i == 0){
                            tmpSumValue[j] = tmpKFloat[j];
                        }else{
                            KFloatUtils.add(tmpSumValue[j], tmpKFloat[j]);
                        }
    
                        if (i < tmpDay - 1) {
                            maValues[j][i] = 8; // 模拟浮点0值
                        } else {
                            if (beginAverage[j]) {
                                KFloatUtils.div(tmpKFloat[j], tmpSumValue[j], tmpDay);
                                maValues[j][i] = tmpKFloat[j].float2int();
    
                                tmpKFloat[j] = new KFloat(shoupan[i - tmpDay + 1]); 
                                KFloatUtils.sub(tmpSumValue[j], tmpKFloat[j]); // 临时和减去days天前的数据
                            }
                        }            
	                   
	                    
	                }//计算均线值 ~ else end
	            }//均线天数for循环 end
	            
	        }//收盘价for循环end
	        
	        return maValues;
	        
	    }
	    
	 
	    
	    
	    /**
	     * 计算MA值
	     * @param maDays 计算周期天数数组
	     * @param shoupan 收盘价数组
	     * @param defalutMA5 默认5日均线值
	     * @param defaultMA10 默认10日均线值
	     * @param defaultMA30 默认30日均线值
	     * @return
	     */
	    public static int[] getMAValues(int maDays,int[] shoupan,int[] defalutMA5,int[] defaultMA10,int[] defaultMA30){
	        if (maDays == 0){
	            return null;
	        }else if (maDays == 1){	            
	            return shoupan;
	        }else if (maDays == 5){
	            return defalutMA5;
	        }else if (maDays == 10){
	            return defaultMA10;
	        }else if (maDays == 30){
	            return defaultMA30;
	        }else{
	            return getMAValues(shoupan,maDays);
	        }
	    }
	    
	    /**
	     * 计算K线MA的值
	     * @param shoupan 收盘价
	     * @param days 计算天数
	     * @return
	     */
	    public static int[] getMAValues(int[] shoupan,int days){
	        if (shoupan==null || shoupan.length==0){
	            return null; //不存在昨收数据，则直接返回null
	        }else{
	            
	           int maxDays = shoupan.length;
	           if (maxDays>days){
	               return null; //若实际数据小于计算天数days,则直接返回null
	           }else {
	               int[] rValues = new int[maxDays];
	               boolean beginAverage = false;
	               KFloat tmpMaxValue = new KFloat();
	               KFloat tmpKFloat;
	               for (int i=0;i<maxDays-1;i++){
	                   if (!beginAverage && i == days-1){
	                       beginAverage = true;
	                   }
	                   //求和
	                   tmpKFloat = new KFloat(shoupan[i]);
	                   KFloatUtils.add(tmpMaxValue, tmpKFloat);
	                   
	                   if (i<days-1){                       
	                       rValues[i] = 8;
	                   }else{
	                      if (beginAverage){
	                          KFloatUtils.div(tmpKFloat,tmpMaxValue, days);
	                          rValues[i] = tmpKFloat.float2int();
	                          
	                          tmpKFloat = new KFloat(shoupan[i-days+1]); //减去days+1天前的数据
	                          KFloatUtils.sub(tmpMaxValue, tmpKFloat);
	                      }
	                   }
	               }
	               
	               return rValues;
	           }
	            
	        }
	    }
	
}
