package com.romaway.android.phone.utils;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.widget.DatePicker;
import com.romaway.commons.lang.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import roma.romaway.commons.android.webkit.RomaWebView;

/**
 * Created by edward on 16/5/31.
 */
public class DateDialogUtils {

    /**
     * 弹出原生系统日期框:
     * @param cutDate		当前默认选中日期
     * @param minDate		最小可选日期, 可空
     * @param maxDate		最大可选日期, 可空
     * @param callbackFunction	确定后回调的JS函数
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void showDateDialog(final RomaWebView mWebView, String cutDate, String minDate, String maxDate, final String callbackFunction){
        try {
            Date mDate = DateUtils.parse(new SimpleDateFormat("yyyy-MM-dd"), cutDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(mDate);
            DatePickerDialog dialog = new DatePickerDialog(mWebView.getContext(), AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    String[] funcAndParam = parseCallBackFunction(callbackFunction);
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(year, monthOfYear, dayOfMonth);

                    String date = DateUtils.format_YYYY_MM_DD(calendar.getTime());
                    String executeFunc = String.format(funcAndParam[0]+"('%s')", date);

                    System.out.println("executeFunc:  "+ executeFunc);
                    mWebView.loadJsMethodUrl("javascript:"+executeFunc);
                }
            }, getYear(calendar), getMonth(calendar)-1, getDayOfMonth(calendar));
            DatePicker datePicker = dialog.getDatePicker();
            // 设置拦截DatePicker子选择框获取焦点, 即不弹出系统键盘:
            datePicker.setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);
            if(!TextUtils.isEmpty(minDate))
                datePicker.setMinDate(DateUtils.parse(new SimpleDateFormat("yyyy-MM-dd"), minDate).getTime());
            if(!TextUtils.isEmpty(maxDate))
                datePicker.setMaxDate(DateUtils.parse(new SimpleDateFormat("yyyy-MM-dd"), maxDate).getTime());
            dialog.show();
        }catch(Exception e){
            Log.e("DateDialogUtils", e.getMessage());
        }
    }

    public static int getYear(Calendar calendar) {
        return calendar.get(Calendar.YEAR);
    }

    public static int getMonth(Calendar calendar) {
        return calendar.get(Calendar.MONTH) + 1;
    }

    public static int getDayOfMonth(Calendar calendar) {
        return calendar.get(Calendar.DAY_OF_MONTH);
    }


    /**
     * 解析JS回调函数:
     * @param callbackFunction      handleShowDateDialog(value) 或 handleShowDateDialog(value, value1, value2)
     * @return
     */
    public static String[] parseCallBackFunction(String callbackFunction){
        String[] funcAndParam = null;
        if(callbackFunction.contains("(") && callbackFunction.contains(")")){
            String funcStr = callbackFunction.substring(0, callbackFunction.indexOf("("));

            String paramStr = callbackFunction.substring(callbackFunction.indexOf("("), callbackFunction.indexOf(")"));
            String[] params = paramStr.split(",");

            funcAndParam = new String[params.length + 1];

            funcAndParam[0] = funcStr;
            for(int i = 0; i < params.length; i++){
                funcAndParam[i+1] = params[i];
            }
        }
        return funcAndParam;
    }
}
