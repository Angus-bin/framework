package com.romaway.android.phone.utils;

import com.romaway.commons.log.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by edward on 16/12/9.
 */
public class RegexpUtils {

    /**
     * 校验是否为有效手机号码, 判断标准: 以【1】开头, 第二位则则有【3,4,5,7,8】, 第三位则是【0-9】等
     * @param s     校验的手机号码字符串
     * @return
     */
    public static boolean isValidMobilePhone(String s) {
        Pattern p = Pattern.compile("^1[3|4|5|7|8][0-9]{9}");
        Matcher m = p.matcher(s);
        return m.matches();
    }

    /**
     * 校验是否为有效股票代码, 判断标准: 必须且只含有数字和26个字母
     * @param s     校验的字符串
     * @return
        [TAG]:  false
        [TAG]6:  true
        [TAG]60:  true
        [TAG]H:  true
        [TAG]HSI:  true
        [TAG]B6:  true
        [TAG]B6003A:  true
        [TAG]500002:  true
        [TAG]ASDASWQWEQWEQWEQWEQEQW:  true
        [TAG]@!@$#$%#:  false
        [TAG]中文:  false
        [TAG]中文21323:  false
        [TAG]BSAASD中文21323:  false
     */
    public static boolean isValidStockCode(String s) {
        Pattern p = Pattern.compile("^[A-Za-z0-9]+$");
        Matcher m = p.matcher(s);
        Logger.i("TAG", s + ":  " + m.matches());
        return m.matches();
    }
}
