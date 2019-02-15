package com.romaway.android.phone;

import com.romaway.commons.log.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

/**
 * Created by hongrb on 2017/5/11.
 */
public class TimasManger {

    /**
     * 获取两个日期之间的间隔天数
     * type 回传方式：0 天数，1 小时数
     * @return
     */
    public static int getGapCount(Date startDate, Date endDate, int type) {
        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(startDate);
        fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
        fromCalendar.set(Calendar.MINUTE, 0);
        fromCalendar.set(Calendar.SECOND, 0);
        fromCalendar.set(Calendar.MILLISECOND, 0);

        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(endDate);
        toCalendar.set(Calendar.HOUR_OF_DAY, 0);
        toCalendar.set(Calendar.MINUTE, 0);
        toCalendar.set(Calendar.SECOND, 0);
        toCalendar.set(Calendar.MILLISECOND, 0);
        if (type == 0) {
            Logger.d("WoUserInfoSelectProtocolCoder", "longDays = " + ((toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24)));
            return (int) ((toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24));
        } else {
            Logger.d("WoUserInfoSelectProtocolCoder", "longHours1 = " + ((toCalendar.getTime().getTime())));
            Logger.d("WoUserInfoSelectProtocolCoder", "longHours2 = " + ((fromCalendar.getTime().getTime())));
            Logger.d("WoUserInfoSelectProtocolCoder", "longHours3 = " + ((toCalendar.getTime().getTime() - fromCalendar.getTime().getTime())));
            Logger.d("WoUserInfoSelectProtocolCoder", "longHours4 = " + ((toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60)));
            return (int) ((toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60));
        }
    }

    public static long getTimeMillis(String strTime) {
        long returnMillis = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Date d = null;
        try {
            d = sdf.parse(strTime);
            returnMillis = d.getTime();
        } catch (ParseException e) {
//            Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
        }
        return returnMillis;
    }

    public static String getTimeExpend(String startTime, String endTime){
        //传入字串类型 2016/06/28 08:30
        long longStart = getTimeMillis(startTime); //获取开始时间毫秒数
        long longEnd = getTimeMillis(endTime);  //获取结束时间毫秒数
        long longExpend = longEnd - longStart;  //获取时间差

        long longHours = longExpend / (60 * 60 * 1000); //根据时间差来计算小时数
        long longMinutes = (longExpend - longHours * (60 * 60 * 1000)) / (60 * 1000);   //根据时间差来计算分钟数
        Logger.d("WoUserInfoSelectProtocolCoder", "longHours = " + longHours);
        return longHours + ""/* + ":" + longMinutes*/;
    }

    public static String getTimeString(String endTime, String expendTime){
        //传入字串类型 end:2016/06/28 08:30 expend: 03:25
        long longEnd = getTimeMillis(endTime);
        String[] expendTimes = expendTime.split(":");   //截取出小时数和分钟数
        long longExpend = Long.parseLong(expendTimes[0]) * 60 * 60 * 1000 + Long.parseLong(expendTimes[1]) * 60 * 1000;
        SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        return sdfTime.format(new Date(longEnd - longExpend));
    }

    /*
    *计算time2减去time1的差值 差值只设置 几天 几个小时 或 几分钟
    * 根据差值返回多长之间前或多长时间后
    * */
    public static String getDistanceTime(long  time1,long time2 ) {
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        long diff ;
        String flag;
        Logger.d("WoUserInfoSelectProtocolCoder", "time1 = " + time1 + "  time2 = " + time2);
        if(time1<time2) {
            diff = time2 - time1;
            flag="前";
        } else {
            diff = time1 - time2;
            flag="后";
            return 0 + "";
        }
        Logger.d("WoUserInfoSelectProtocolCoder", "longHours1 = " + diff);
        day = diff / (24 * 60 * 60 * 1000);
        Logger.d("WoUserInfoSelectProtocolCoder", "longHours2 = " + day);
        hour = (diff / (60 * 60 * 1000) - day * 24);
        Logger.d("WoUserInfoSelectProtocolCoder", "longHours3 = " + hour);
//        min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
        sec = (diff/1000-day*24*60*60-hour*60*60-min*60);
        if(day!=0)return day+"";
        if(hour!=0)return hour+"";
//        if(min!=0)return min+"分钟"+flag;
        return "刚刚";
    }

    public static String getDistanceDay(long  time1,long time2 ) {
        long day = 0;
        long diff ;
        Logger.d("WoUserInfoSelectProtocolCoder", "time1 = " + time1 + "  time2 = " + time2);
        if(time1<time2) {
            diff = time2 - time1;
        } else {
            diff = time1 - time2;
            return 0 + "";
        }
        Logger.d("WoUserInfoSelectProtocolCoder", "longHours1 = " + diff);
        day = diff / (24 * 60 * 60 * 1000);
        Logger.d("WoUserInfoSelectProtocolCoder", "longHours2 = " + day);
        return day + "";
    }

    public static String getDistanceDay2(long  time1,long time2 ) {
        long day = 0;
        long diff ;
        Logger.d("RomaLimitUpHistoryAdapter", "time1 = " + time1 + "  time2 = " + time2);
        if(time1>time2) {
            diff = time1 - time2;
        } else {
            diff = time1 - time2;
            return 0 + "";
        }
        Logger.d("RomaLimitUpHistoryAdapter", "longHours1 = " + diff);
        day = diff / (24 * 60 * 60 * 1000);
        Logger.d("RomaLimitUpHistoryAdapter", "longHours2 = " + day);
        return day + "";
    }

}
