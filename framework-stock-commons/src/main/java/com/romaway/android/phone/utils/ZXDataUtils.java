package com.romaway.android.phone.utils;

import com.romaway.commons.lang.DateUtils;
import com.romaway.commons.log.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by edward on 16/3/29.
 */
public class ZXDataUtils {

    /** 格式化资讯中心等时间 */
    public static String handleDateFormat(String date, int formatType){
        switch (formatType){
            case 1:
                return sortFormatDate(date);
            case 0:
                return formatSimpleDate(date);
            case 2:

            default:
                return formatSimpleDate(date);
        }
    }

    /** 格式化资讯详情时间 */
    public static String formatZXDetailDate(String date, int formatType){
        switch (formatType){
            case 1:
                return formatYYMMDD_HHMM(date);
            case 0:
            default:
                return date;
        }
    }

    /** 格式化用户反馈时间 */
    public static String formatUserFeedBackDate(String date, int formatType){
        switch (formatType){
            case 1:
                return formatYYMMDD_HHMMSS(date);
            case 0:
            default:
                return date;
        }
    }

    /** 格式化时间轴Title时间 */
    public static String formatTimeLineDate(String date, int formatType){
        switch (formatType){
            case 1:
                return formatYYMMDDAsCN(date);
            case 0:
            default:
                return date;
        }
    }

    public static String formatSimpleDate(String date){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("MM-dd");
        String formattedDate = df.format(c.getTime());
        if(date.contains(formattedDate)){
            return date.substring(11);
        }else{
            return date.substring(0,10);
        }
    }

    /**
     * @param date
     * @return
            "17:20"                      ---> 17:20
            "2016-03-31 19:20"(当天)      ---> 19:20
            "2016-03-30 19:20"           ---> 昨天
            "2016-03-27 19:20"           ---> 16/03/27
     */
    public static String sortFormatDate(String date){
        if(date.length() <= 5){
            return date;
        }

        try {
            Date mDate = DateUtils.parse(new SimpleDateFormat("yyyy-MM-dd HH:mm"), date);
            switch (isYeaterday(mDate, null)){
                case 0:     // 昨天
                    date = /*"昨天";*/DateUtils.format(new SimpleDateFormat("yyyy-MM-dd"), mDate);
                    if (date.contains("2019")) {
                        date = DateUtils.format(new SimpleDateFormat("MM-dd"), mDate);
                    }
                    break;
                case -1:    // 今天
                    date = DateUtils.format(new SimpleDateFormat("HH:mm"), mDate);
                    break;
                case 1:     // 前天
                    date = DateUtils.format(new SimpleDateFormat("yyyy-MM-dd"), mDate);
                    if (date.contains("2019")) {
                        date = DateUtils.format(new SimpleDateFormat("MM-dd"), mDate);
                    }
                    break;
            }
        }catch (Exception e){
            Logger.e("ZXDataUtils", e.getMessage());
        }finally {
            return date;
        }
    }

    public static String sortFormatDateHHmm(String date){
        if(date.length() <= 5){
            return date;
        }
        try {
            Date mDate = DateUtils.parse(new SimpleDateFormat("yyyy-MM-dd HH:mm"), date);
            date = DateUtils.format(new SimpleDateFormat("HH:mm"), mDate);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return date;
        }
    }

    public static String sortFormatDateDay(String date){
        if(date.length() <= 5){
            return date;
        }

        try {
            Date mDate = DateUtils.parse(new SimpleDateFormat("yyyy-MM-dd"), date);
            switch (isYeaterday(mDate, null)){
                case 0:     // 昨天
                    date = "昨天";/*DateUtils.format(new SimpleDateFormat("yyyy-MM-dd"), mDate);*/
                    break;
                case -1:    // 今天
                    date = "今天"/*DateUtils.format(new SimpleDateFormat("HH:mm"), mDate)*/;
                    break;
                case 1:     // 前天
                    date = DateUtils.format(new SimpleDateFormat("yyyy-MM-dd"), mDate);
                    break;
            }
        }catch (Exception e){
            Logger.e("ZXDataUtils", e.getMessage());
        }finally {
            return date;
        }
    }

    /**
     * @param date      "2016-03-30 19:20"
     * @return          "16/03/30 19:20"
     */
    public static String formatYYMMDD_HHMM(String date){
        try {
            Date mDate = DateUtils.parse(new SimpleDateFormat("yyyy-MM-dd HH:mm"), date);
            date = DateUtils.format(new SimpleDateFormat("yy/MM/dd HH:mm"), mDate);
        }catch (Exception e){
            Logger.e("ZXDataUtils", e.getMessage());
        }finally {
            return date;
        }
    }

    /**
     * @param date      "2016-03-30 19:20:07"
     * @return          "16/03/30 19:20:07"
     */
    public static String formatYYMMDD_HHMMSS(String date){
        try {
            Date mDate = DateUtils.parse(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), date);
            date = DateUtils.format(new SimpleDateFormat("yy/MM/dd HH:mm:ss"), mDate);
        }catch (Exception e){
            Logger.e("ZXDataUtils", e.getMessage());
        }finally {
            return date;
        }
    }

    /**
     * @param date      "2016-03-30", "2016-03-30 19:20"
     * @return          "2016年3月30日"
     */
    public static String formatYYMMDDAsCN(String date){
        try {
            Date mDate;
            if (date.contains(":") && date.length() > "2016-03-30".length()){
                mDate = DateUtils.parse(new SimpleDateFormat("yyyy-MM-dd HH:mm"), date);
            }else{
                mDate = DateUtils.parse(new SimpleDateFormat("yyyy-MM-dd"), date);
            }

            date = DateUtils.format(new SimpleDateFormat("yyyy年M月d日"), mDate);
        }catch (Exception e) {
            Logger.e("ZXDataUtils", e.getMessage());
        }
        return date;
    }

    /**
     * @author LuoB.
     * @param oldTime 较小的时间
     * @param newTime 较大的时间 (如果为空   默认当前时间 ,表示和当前时间相比)
     * @return -1 ：同一天.    0：昨天 .   1 ：至少是前天.
     * @throws ParseException 转换异常
     */
    private static int isYeaterday(Date oldTime,Date newTime) throws ParseException {
        if(newTime==null){
            newTime=new Date();
        }
        //将下面的 理解成  yyyy-MM-dd 00：00：00 更好理解点
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String todayStr = format.format(newTime);
        Date today = format.parse(todayStr);
        //昨天 86400000=24*60*60*1000 一天
        if((today.getTime()-oldTime.getTime())>0 && (today.getTime()-oldTime.getTime())<=86400000) {
            return 0;
        } else if((today.getTime()-oldTime.getTime())<=0){ //至少是今天
            return -1;
        } else{ //至少是前天
            return 1;
        }
    }

    public static boolean isToday(Date oldTime) {
        boolean isToday = false;
        try {
            switch (isYeaterday(oldTime, null)) {
                case 0:     // 昨天
                    isToday = false;
                    break;
                case -1:    // 今天
                    isToday = true;
                    break;
                case 1:     // 前天
                    isToday = false;
                    break;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return isToday;
    }

    /**
     * @param date      "2016-03-30 19:20:07"
     * @return          "03-30 19:20:07"
     */
    public static String formatMMDD_HHMM(String date){
        try {
            Date mDate = DateUtils.parse(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), date);
            date = DateUtils.format(new SimpleDateFormat("MM-dd HH:mm"), mDate);
        }catch (Exception e){
            Logger.e("ZXDataUtils", e.getMessage());
        }finally {
            return date;
        }
    }

    private final static long minute = 60 * 1000;// 1分钟
    private final static long hour = 60 * minute;// 1小时
    private final static long day = 24 * hour;// 1天
    private final static long month = 31 * day;// 月
    private final static long year = 12 * month;// 年

    /**
     * 返回文字描述的日期
     *
     * @param date
     * @return
     */
    public static String getTimeFormatText(Date date, String time, boolean ss) {
        if (date == null) {
            return null;
        }
        long diff = new Date().getTime() - date.getTime();
        long r = 0;
//        if (diff > year) {
//            r = (diff / year);
//            return r + "年前";
//        }
//        if (diff > month) {
//            r = (diff / month);
//            return r + "个月前";
//        }
        if (diff > day) {
            r = (diff / day);
            if (r > 3) {
                if (ss) {
                    return getStrTime(time);
                } else {
                    return getStrTime2(time);
                }
            }
            return r + "天前";
        }
        if (diff > hour) {
            r = (diff / hour);
            return r + "小时前";
        }
        if (diff > minute) {
            r = (diff / minute);
            return r + "分钟前";
        }
        return "刚刚";
    }

    public static String getStrTime(String timeStamp){
        String timeString = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        long  l = Long.valueOf(timeStamp);
        timeString = sdf.format(new Date(l * 1000));//单位秒
        if (timeString.contains("2018")) {
            sdf = new SimpleDateFormat("MM-dd");
            timeString = sdf.format(new Date(l * 1000));//单位秒
        }
        return timeString;
    }

    public static String getStrTime2(String timeStamp){
        String timeString = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        long  l = Long.valueOf(timeStamp);
        timeString = sdf.format(new Date(l));//单位秒
        if (timeString.contains("2018")) {
            sdf = new SimpleDateFormat("MM-dd");
            timeString = sdf.format(new Date(l * 1000));//单位秒
        }
        return timeString;
    }

}
