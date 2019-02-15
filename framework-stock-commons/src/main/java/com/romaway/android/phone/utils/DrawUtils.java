package com.romaway.android.phone.utils;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Rect;

import com.romaway.common.protocol.util.KFloat;
import com.romaway.common.protocol.util.KFloatUtils;
import com.romaway.commons.lang.ArrayUtils;
import com.romaway.commons.lang.StringUtils;
import com.romawaylibs.theme.ROMA_SkinManager;
import com.zhy.autolayout.utils.AutoUtils;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.TimeZone;

import roma.romaway.commons.android.theme.SkinManager;

/**
 * 画线工具类
 * 
 * @author qinyn
 * 
 */
public class DrawUtils
{
	public static final int req0 = 0;
	public static final int req1 = 1;
	public static final int req2 = 2;
	public static final int req3 = 3;
	public static final int req4 = 4;
	public static final int req5 = 5;

	/**
	 * 获取虚线
	 * 
	 * @param x
	 * @param y
	 * @param r
	 * @param isHorizontal
	 *            横线或纵线
	 * @return
	 */
	public static float[] getDottedLine(int x, int y, Rect r,
	        boolean isHorizontal)
	{
		if (r == null)
			return null;
		final int section = 6;// 段长
		final int gap = 3;// 间隔
		final int end = isHorizontal ? r.right : r.bottom;
		final int len = isHorizontal ? r.width() : r.height();
		final int fix = isHorizontal ? 1 : 0;
		int xSection = 0;
		int xGap = 0;
		int ySection = 0;
		int yGap = 0;
		int count = len / section;
		if (isHorizontal)
		{
			xSection = section;
			xGap = gap;
		} else
		{
			ySection = section;
			yGap = gap;
		}
		float[] line = new float[(count + fix) * 4];
		for (int i = 0; i < count; i++)
		{
			line[i * 4] = x;
			line[i * 4 + 1] = y;
			line[i * 4 + 2] = x + xSection - xGap;
			line[i * 4 + 3] = y + ySection - yGap;
			x += xSection;
			y += ySection;
		}
		if (isHorizontal)
		{
			line[count * 4] = x;
			line[count * 4 + 1] = y;
			line[count * 4 + 2] = end;
			line[count * 4 + 3] = y;
		}

		return line;
	}
	
	/**
	 * 画新股均线
	 * @param zrsp
	 * @param maxColumnCount
	 * @param rect
	 * @return
	 */
	public static Path buildNewStockAVL(KFloat zrsp,int maxColumnCount, Rect rect){
		int drawTimeCount = 0;
		//获取当前时间
		Calendar calendar = Calendar.getInstance(TimeZone
		        .getTimeZone("GMT+08:00"));
		// int weekDay = calendar.get((Calendar.DAY_OF_WEEK));
		
		int hour = (calendar.get(Calendar.HOUR_OF_DAY)) % 24;
		int minute = calendar.get(Calendar.MINUTE);

		Path p = new Path();
		float y = rect.bottom - rect.height() / 2 + AutoUtils.getPercentWidthSize(2);
		p.moveTo(rect.left, y);
		fenshiFirstPositionX = rect.left;
		fenshiFirstPositionY = y;
		fenshiLastPositionY = y;
		if (hour<9){
		    drawTimeCount = 0;
			return null;
			
		}else if (hour >= 9 && minute<=30 && hour<10){
		    drawTimeCount = 0;
			return null;
		}else if (hour>=15){
			p.lineTo(rect.left + rect.width(), y);
			fenshiLastPositionX = rect.left + rect.width();
			
		}else if ((hour == 9 && minute > 30 && hour<10) || (hour >= 10 && hour < 11) || (hour >= 11 && minute < 30 && hour<12)){
			//9:30至11:30之间
		    drawTimeCount = (int) ((hour - 9.5f) * 60) + minute;//hour*60 + minute - 9*60 - 30 + 1;
			p.lineTo(rect.left + rect.width()*drawTimeCount/maxColumnCount, y);
			fenshiLastPositionX = rect.left + rect.width()*drawTimeCount/maxColumnCount;
			
		}else if ((hour >= 11 && minute >= 30 && hour<12) || (hour>=12 && hour<13)){
			//11:30至13：00之间

			p.lineTo(rect.left + rect.width()/2, y);
			fenshiLastPositionX = rect.left + rect.width()/2;
		}else if (hour>=13 && hour<15){
		    //下午13：00 至 15：00之间
		    //计算需要画的宽度：上午2小时 + 下午所占用的时间分钟，一天只有4个小时，需要计算当前占用4个小时的比例来计算得到需要画的宽度
		    drawTimeCount = (2 + (hour - 13)) * 60 +  minute;
		    float drawWidth = rect.width()*drawTimeCount/(maxColumnCount);
			p.lineTo(rect.left + drawWidth, y);
			fenshiLastPositionX = rect.left + drawWidth;
		}
		
		return p;
	}

	/**
	 * 画新股均线
	 * @param zjcj
	 * @param maxColumnCount
	 * @param rect
	 * @return
	 */
	public static Path buildNewStockAVL(int[] zjcj,int maxColumnCount, Rect rect){
		int drawTimeCount = zjcj.length;

		Path p = new Path();
		float y = rect.bottom - rect.height() / 2;
		p.moveTo(rect.left, y);
		fenshiFirstPositionX = rect.left;
		fenshiFirstPositionY = y;
		fenshiLastPositionY = y;

		if (drawTimeCount == 0){
			return null;
		} else {
			fenshiLastPositionX = rect.left + rect.width()*drawTimeCount/maxColumnCount;
			p.lineTo(fenshiLastPositionX, y);
		}
		return p;
	}

	/**
	 * 生成均线
	 * 
	 * @param data
	 * @param maxValueOfTop
	 * @param minValueOfBottom
	 * @param maxColumnCount
	 * @return
	 */
	public static Path buildAVL(int[] data, KFloat maxValueOfTop,
	        KFloat minValueOfBottom, int maxColumnCount, Rect rect, String type)
	{
	    
		if (data == null || data.length==0)
			return null;
		
		//Logger.d("tag", "buildAVL data.length : "+data.length);
		 
		KFloat startKFloat = new KFloat();
		Path p = new Path();
		int drawcount = data.length;
		KFloatUtils.sub(startKFloat, maxValueOfTop, minValueOfBottom);
		long maxZf = startKFloat.nValue;
		if (maxZf == 0)
		{
			return p;
		}
		float newx = 0;
		float newy = 0;
		float xfix = 0;//perX / 100;
		float perX = 1002 * (rect.width() - xfix) / maxColumnCount;// 水平的间隔点
		startKFloat.init(data[0]);
		KFloatUtils.sub(startKFloat, minValueOfBottom);
		long zf = startKFloat.nValue;
		float sy = rect.bottom - zf * rect.height() / maxZf; 

		p.moveTo(0, sy);

		// 【BUG】修复下发数据不符合标准导致显示超出分时框范围;
		if(drawcount > maxColumnCount)
			drawcount = maxColumnCount;
		for (int i = 0; i < drawcount; i++)
		{
			int iTem = data[i];
			newx = rect.left + perX * i / 1000 + xfix;
			startKFloat.init(iTem);
			KFloatUtils.sub(startKFloat, minValueOfBottom);
			zf = startKFloat.nValue;
			newy = rect.bottom - zf * rect.height() / maxZf;
			if (i == 0)
			{
				p.moveTo(newx, newy);
				//保存第一个点
				fenshiFirstPositionX = newx;
				fenshiFirstPositionY = newy;
				
			} else
			{
				p.lineTo(newx, newy);
				//保存最后一个点
				/*if(i >= drawcount - 1){
				    fenshiLastPositionX = newx;
				    fenshiLastPositionY = newy;
				}*/
			}
			//20151123,chenjp解决只有一个点时，画的分时线错误
			//保存最后一个点
			if(i >= drawcount - 1){
			    fenshiLastPositionX = newx;
			    fenshiLastPositionY = newy;
				if (type.equals("FS")) {
					fenshiBreathingX = newx;
					fenshiBreathingY = newy;
				}
			}
		}
		return p;
	}

	public static float fenshiFirstPositionX = 0.0f;
	public static float fenshiFirstPositionY = 0.0f;
	public static float fenshiLastPositionX = 0.0f;
    public static float fenshiLastPositionY = 0.0f;
    public static float fenshiBreathingX = 0.0f;
    public static float fenshiBreathingY = 0.0f;

	
	/**
	 * 生成均线
	 * 
	 * @param data
	 * @param maxValueOfTop
	 * @param minValueOfBottom
	 * @param start
	 * @param maxColumnCount
	 * @param rect
	 * @return
	 */
	public static Path buildAVL(int[] data, KFloat maxValueOfTop,
	        KFloat minValueOfBottom, int start, int maxColumnCount, Rect rect)
	{
		KFloat temKFloat = new KFloat();
		KFloat floator = new KFloat();
		int drawcount = data.length;
		KFloatUtils.sub(temKFloat, maxValueOfTop, minValueOfBottom);
		int maxZf = temKFloat.nValue;
		float perX = ((float) (1000 * rect.width())) / maxColumnCount;// 水平的间隔点
		float newx = 0;
		float newy = 0;
		int zf = 0;
		Path p = new Path();
		boolean hasStart = false;
		if (maxZf == 0)
			return null;
		for (int i = start; i < drawcount; i++)
		{
			newx = rect.left + perX * (i - start) / 1000;
			KFloatUtils.sub(temKFloat, floator.init(data[i]), minValueOfBottom);
			zf = temKFloat.nValue;
			newy = rect.bottom - zf * rect.height() / maxZf;
			if (hasStart && zf >= 0 && maxZf >= zf)
			{
				p.lineTo(newx, newy);
			} else if (!hasStart)
			{
				hasStart = floator.nValue >= minValueOfBottom.nValue;
				p.moveTo(newx, newy);
			}
		}
		return p;
	}

	/**
	 * 成交量
	 * 
	 * @param data
	 * @param maxValueOfTop
	 * @param maxColumnCount
	 * @param rect
	 * @return
	 */
	public static Path getVolPath(int[] data, KFloat maxValueOfTop,
	        int maxColumnCount, Rect rect)
	{
		Path path = new Path();
		int drawcount = data.length;
		int maxZf = maxValueOfTop.nValue;
		KFloat startKFloat = new KFloat();
		float newx = 0;
		float newy = 0;
		float perX = 100 * rect.width() / maxColumnCount; // 水平的间隔点
		float xFix = perX / 100;
		int zf = startKFloat.init(data[0]).nValue;
		perX = 100 * (rect.width() - xFix) / maxColumnCount;

		if (maxZf == 0)
		{
			return path;
		}
		// //从上往下画成交量
		// for (int i = 0; i < drawcount; i++)
		// {
		// int iTem = 0;
		// iTem = data[i];
		// newx = rect.left + perX * i / 100 + xFix;
		// zf = startKFloat.init(iTem).nValue;
		// newy = rect.bottom - zf * rect.height() / maxZf;
		// path.moveTo(newx, newy);
		// path.lineTo(newx, rect.bottom);
		// }
		for (int i = 0; i < drawcount; i++)
		{
			int iTem = 0;
			iTem = data[i];
			newx = rect.left + perX * i / 100 + xFix;
			zf = startKFloat.init(iTem).nValue;
			// newy = (rect.bottom-15) - zf * (rect.height()-15) / maxZf;
			newy = rect.bottom - zf * rect.height() / maxZf;
			if (i == 0)
			{
				path.moveTo(newx, newy);
			} else
			{
				path.lineTo(newx, newy);
			}

		}
		// path.lineTo(newx, rect.bottom-15);
		// path.lineTo(rect.left + xFix, rect.bottom-15);
		path.lineTo(newx, rect.bottom);
		path.lineTo(rect.left + xFix, rect.bottom);
		path.close();
		return path;
	}

	public static Path[] getVolPathColumns(int[] data, KFloat maxValueOfTop,
	        int maxColumnCount, Rect rect)
	{
		if (data == null || data.length==0){
			return null;
		}
		int drawcount = data.length;
		Path[] paths = new Path[drawcount];
		int maxZf = maxValueOfTop.nValue;
		KFloat startKFloat = new KFloat();
		float newx = 0;
		float newy = 0;
		float perX = 100 * rect.width() / maxColumnCount; // 水平的间隔点
		float xFix = perX / 100;
		int zf = startKFloat.init(data[0]).nValue;
		perX = 100 * (rect.width() - xFix) / maxColumnCount;

		if (maxZf == 0)
		{
			return paths;
		}
		// 从上往下画成交量
		for (int i = 0; i < drawcount; i++)
		{
			paths[i] = new Path();
			int iTem = 0;
			iTem = data[i];
			newx = rect.left + perX * i / 100 + xFix;
			zf = startKFloat.init(iTem).nValue;
			newy = rect.bottom - zf * rect.height() / maxZf;
			paths[i].moveTo(newx, newy);
			paths[i].lineTo(newx, rect.bottom);
			paths[i].close();
		}
		return paths;
	}

	/**
	 * 返回从开始数到结束数之间的平均数据集
	 * 
	 * @param kfStart
	 *            开始
	 * @param kfEnd
	 *            结束
	 * @param intervalCount
	 *            个数
	 * @return
	 */
	public static KFloat[] initIntervalData(KFloat kfStart, KFloat kfEnd,
	        int intervalCount)
	{
		if (intervalCount <= 0)
		{
			return null;
		}
		KFloat kfInterval = new KFloat();
		KFloatUtils.sub(kfInterval, kfEnd, kfStart);
		KFloatUtils.div(kfInterval, intervalCount + 1);
		KFloat[] result = new KFloat[intervalCount];
		KFloat tmp = new KFloat(kfStart);
		for (int index = 0; index < intervalCount; index++)
		{
			KFloatUtils.add(tmp, kfInterval);
			result[index] = new KFloat(tmp);
		}
		return result;
	}

	/**
	 * 获得游标x
	 * 
	 * @param index
	 * @param rect
	 * @param maxColumnCount
	 * @return
	 */
	public static float getCursorX(int index, Rect rect, int maxColumnCount)
	{
		float perX = 100 * rect.width() / maxColumnCount;
		float xFix = perX / 100;
		perX = 100 * (rect.width() - xFix) / maxColumnCount;
		float lineX = xFix + rect.left + (index) * perX / 100;
		return lineX;
	}

	/**
	 * 获得游标y
	 * 
	 * @param index
	 * @param rect
	 * @param maxValueOfTop
	 * @param minValueOfBottom
	 * @param data
	 * @return
	 */
	public static float getCursorY(int index, Rect rect, KFloat maxValueOfTop,
	        KFloat minValueOfBottom, int[] data)
	{
		int iTem = ArrayUtils.getValue(data, index, 0);// data[index];
		KFloat startKFloat = new KFloat();
		KFloatUtils.sub(startKFloat, maxValueOfTop, minValueOfBottom);
		long maxZf = startKFloat.nValue;
		startKFloat.init(iTem);
		KFloatUtils.sub(startKFloat, minValueOfBottom);
		float zf = startKFloat.nValue;
		float lineY = rect.bottom - zf * rect.height() / maxZf;
		return lineY;
	}

	/**
	 * 将xxxx类型的时间变成xx：xx
	 *
	 * @param value
	 * @return
	 */
	public static String setFloatTime(String value)
	{
		value = value.length() < 4 ? "0" + value : value;
		value = value.substring(0, 2) + ":" + value.substring(2);
		return value;
	}
	
	public static String substringTime(String value){
		if (StringUtils.isEmpty(value)) {
			return value;
		}
		if (value.length() <= 4) {
			value = setFloatTime(value);
		} else {
			value = value.length() < 8 ? "0" + value : value;
			value = value.substring(4, 6) + ":" + value.substring(6);
		}
		return value;
	}

	/**
	 * 将xxxx类型的时间变成xx-xx
	 *
	 * @param value
	 * @return
	 */
	public static String setFloatDate(String value)
	{
		value = value.length() < 4 ? "0" + value : value;
		value = value.substring(0, 2) + "-" + value.substring(2);
		return value;
	}

	public static String substringDate(String value){
		if (StringUtils.isEmpty(value)) {
			return value;
		}
		if (value.length() <= 4) {
			value = setFloatDate(value);
		} else {
			value = value.length() < 8 ? "0" + value : value;
			value = value.substring(0, 2) + "-" + value.substring(2, 4);
		}
		return value;
	}
	
	/**
	 * 将xxxxxxxx类型的时间变成xx-xx xx:xx
	 * @param value
	 * @return
	 */
	public static String substringDateAndTime(String value){
		if (StringUtils.isEmpty(value)) {
			return value;
		}
		if (value.length() <= 4) {
			value = setFloatTime(value);
		} else {
			value = value.length() < 8 ? "0" + value : value;
			value = value.substring(0, 2) + "-" + value.substring(2, 4) + " " + value.substring(4, 6) + ":" + value.substring(6);
		}
		return value;
	}

	/**
	 * 格式化四位时间
	 * 
	 * @param nTime
	 *            [注意9点之前的数据，9点之前数值是5位数]
	 * @return
	 */
	public static String formatNTime(int nTime)
	{
		String sFormat = "000000";
		if (nTime <= 9999)
		{
			sFormat = "0000";
		}
		DecimalFormat dFormat = new DecimalFormat(sFormat);
		String s_format = dFormat.format(nTime);
		return String.format("%s:%s", s_format.substring(0, 2),
		        s_format.substring(2, 4));
	}

	private final static int clr_fs_crossline = 0xffc4f0ff;
	/**
	 * 
	 * @param canvas
	 * @param text 框中显示的文本
	 * @param centerY 中间的y坐标
	 * @param rightX 右边线的坐标
	 * @param p
	 */
	public static void drawFloatText(String text, Canvas canvas, float centerY, float rightX, Paint p){
        
        float pandingtb = 3;
        float pandinglr = 6;
        Paint p2 = new Paint();
		p2.set(p);
        p2.setColor(SkinManager.getColor("FloatFillColor")/*0xff000000*/);
		p2.setAlpha(230);		// 90%透明度
        p2.setStyle(Style.FILL);

        int rh = (int) (p2.getTextSize()+pandingtb * 2);
        int rw = (int) (p2.measureText(text)+pandinglr * 2);

		int strokeWidth = AutoUtils.getPercentWidthSize(1);
        int leftx = strokeWidth /*(int) (rightX-p.measureText(text)-pandinglr * 2)*/;
        Rect leftRect = new Rect(leftx, (int)(centerY - rh/2), (int)rightX - strokeWidth/*leftx + rw*/, (int)(centerY+rh/2+pandingtb));
        
        canvas.drawRect(leftRect, p2);
        //是为了画边框
        p2.setColor(SkinManager.getColor("FloatStrokeColor"));
        p2.setStrokeWidth(strokeWidth);
        p2.setStyle(Style.STROKE);
        canvas.drawRect(leftRect, p2);

		p2.setTextAlign(Paint.Align.CENTER);
		p2.setColor(ROMA_SkinManager.getColor("FloatTextColor", 0xff444444));
        canvas.drawText(text, leftRect.left + rightX/2 /* + pandinglr*/,
                centerY+p2.getTextSize()/2-pandingtb, p2);
    }

	/**
	 *
	 * @param canvas
	 * @param text 框中显示的文本
	 * @param centerY 中间的y坐标
	 * @param rightX 右边线的坐标
	 * @param p
	 */
	public static void drawRightFloatText(String text, Canvas canvas, float centerY, float rightX, Paint p){

		float pandingtb = 3;
		float pandinglr = 6;
		Paint p2 = new Paint();
		p2.set(p);
		p2.setColor(SkinManager.getColor("FloatFillColor"));
		p2.setAlpha(230);		// 90%透明度
		p2.setStyle(Style.FILL);

		int rh = (int) (p2.getTextSize()+pandingtb * 2);
		int rw = (int) (p2.measureText(text)+pandinglr * 2);

		int leftx = (int)rightX; /*(int) (rightX-p.measureText(text)-pandinglr * 2)*/;
		Rect leftRect = new Rect(leftx, (int)(centerY - rh/2), leftx + rw, (int)(centerY+rh/2+pandingtb));

		canvas.drawRect(leftRect, p2);
		//是为了画边框
		p2.setColor(SkinManager.getColor("FloatStrokeColor"));
		p2.setStrokeWidth(AutoUtils.getPercentWidthSize(1));
		p2.setStyle(Style.STROKE);
		canvas.drawRect(leftRect, p2);

		p2.setTextAlign(Paint.Align.LEFT);
		p2.setColor(ROMA_SkinManager.getColor("FloatTextColor", 0xff444444));
		canvas.drawText(text, leftRect.left + pandinglr,
				centerY+p2.getTextSize()/2-pandingtb, p2);
	}
	
	/**
	 * 计算百分比 val2 减去 val1 除于 val1 所占的百分比
	 * @param val1
	 * @param val2
	 * @return
	 * @throws Exception 
	 */
	public static String toPercent(KFloat val1, KFloat val2) throws Exception{
	    String m = "";
        if(KFloat.compare(val2, val1) > 0){
            m = "+";
        }else if(KFloat.compare(val2, val1) < 0){
            m = "";
        }else{
            m = "";
        }
        
        float rightValue = (val2.toFloat() - val1.toFloat()) / val1.toFloat() * 100;

        //手动抛出异常
        if(rightValue == Float.NaN)
            throw new Exception();
        
        String result = m+String.format("%.2f", rightValue) + "%";
        
        return result;
	}
	
	/**
     * 计算百分比 val2 减去 val1 除于 val1 所占的百分比
     * @param val1
     * @param val2
     * @return
	 * @throws Exception 
     */
    public static String toPercent(KFloat val1, float val2) throws Exception{
        String m = "";
        if(val2 - val1.toFloat() > 0){
            m = "+";
        }else if(val2 - val1.toFloat() < 0){
            m = "";
        }else{
            m = "";
        }
        
        float rightValue = (val2 - val1.toFloat()) / val1.toFloat() * 100;
        
      //手动抛出异常
        if(rightValue == Float.NaN)
            throw new Exception();
        
        String result = m+String.format("%.2f", rightValue) + "%";
        
        return result;
    }
}
