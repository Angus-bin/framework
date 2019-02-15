package com.romaway.common.protocol.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * <p>
 * Title: 字符串分割、编码等工具
 * </p>
 * <p>
 * Description: KJava 平台
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public final class KUtils
{
    // private static final String Char_Enc0 = "ISO8859-1"; //字符编码方式1
    // private static final String Char_Enc1 = "ISO8859_1"; //字符编码方式2

    /**
     * 取得当前日期。
     * 
     * @return 字符串形式的当前日期（年、月、日）。
     */
    public static int getCurrentDate()
    {
        Calendar rightNow = Calendar.getInstance();
        int year = rightNow.get(Calendar.YEAR);
        int month = rightNow.get(Calendar.MONTH) + 1;
        int day = rightNow.get(Calendar.DATE);
        return (year * 10000 + month * 100 + day);
    }

    /**
     * 取得起始日期，和终止日期
     * 
     * @return
     */
    public static int[] getQSZZDate()
    {
        int[] date = new int[2];
        Calendar rightNow = Calendar.getInstance();
        int year = rightNow.get(Calendar.YEAR);
        int month = rightNow.get(Calendar.MONTH) + 1;
        int day = rightNow.get(Calendar.DATE);
        date[0] = year * 10000 + month * 100 + day;
        Date now = rightNow.getTime();
        now.setTime(now.getTime() - 6 * 24 * 3600 * 1000);
        rightNow.setTime(now);
        year = rightNow.get(Calendar.YEAR);
        month = rightNow.get(Calendar.MONTH) + 1;
        day = rightNow.get(Calendar.DATE);
        date[1] = year * 10000 + month * 100 + day;
        return date;
    }

    public static boolean checkDate(String data)
    {
        boolean rtn = false;

        return rtn;

    }

    /**
     * 求幂
     * @param num
     * @param m
     * @return
     */
    public static int pow(int num, int m)
    {
        int result = num;
        if (m == 0)
            return 1;
        for (int i = 1; i < m; i++)
            result = result * num;
        return result;
    }

    /**
     * 将UTF-8字节数据转化为Unicode字符串
     * 
     * @param utf_data
     *            byte[] - UTF-8编码字节数组
     * @param len
     *            int - 字节数组长度
     * @return String - 变换后的Unicode编码字符串
     */
    public static String UTF2Uni(byte[] utf_data, int len)
    {
        StringBuffer unis = new StringBuffer();
        char unic = 0;
        int ptr = 0;
        int cntBits = 0;
        for (; ptr < len;)
        {
            cntBits = getCntBits(utf_data[ptr]);
            if (cntBits == -1)
            {
                ++ptr;
                continue;
            } else if (cntBits == 0)
            {
                unic = UTFC2UniC(utf_data, ptr, cntBits);
                ++ptr;
            } else
            {
                unic = UTFC2UniC(utf_data, ptr, cntBits);
                ptr += cntBits;
            }
            unis.append(unic);
        }
        return unis.toString();
    }

    /**
     * 将指定的UTF-8字节组合成一个Unicode编码字符
     * 
     * @param utf
     *            byte[] - UTF-8字节数组
     * @param sptr
     *            int - 编码字节起始位置
     * @param cntBits
     *            int - 编码字节数
     * @return char - 变换后的Unicode字符
     */
    public static char UTFC2UniC(byte[] utf, int sptr, int cntBits)
    {
        /*
         * Unicode <-> UTF-8 U-00000000 - U-0000007F: 0xxxxxxx U-00000080 -
         * U-000007FF: 110xxxxx 10xxxxxx U-00000800 - U-0000FFFF: 1110xxxx
         * 10xxxxxx 10xxxxxx U-00010000 - U-001FFFFF: 11110xxx 10xxxxxx 10xxxxxx
         * 10xxxxxx U-00200000 - U-03FFFFFF: 111110xx 10xxxxxx 10xxxxxx 10xxxxxx
         * 10xxxxxx U-04000000 - U-7FFFFFFF: 1111110x 10xxxxxx 10xxxxxx 10xxxxxx
         * 10xxxxxx 10xxxxxx
         */
        int uniC = 0; // represent the unicode char
        byte firstByte = utf[sptr];
        int ptr = 0; // pointer 0 ~ 15
        // resolve single byte UTF-8 encoding char
        if (cntBits == 0)
            return (char) firstByte;
        // resolve the first byte
        firstByte &= (1 << (7 - cntBits)) - 1;
        // resolve multiple bytes UTF-8 encoding char(except the first byte)
        for (int i = sptr + cntBits - 1; i > sptr; --i)
        {
            byte utfb = utf[i];
            uniC |= (utfb & 0x3f) << ptr;
            ptr += 6;
        }
        uniC |= firstByte << ptr;
        return (char) uniC;
    }

    // 根据给定字节计算UTF-8编码的一个字符所占字节数
    // UTF-8规则定义，字节标记只能为0或2~6
    public static int getCntBits(byte b)
    {
        int cnt = 0;
        if (b == 0)
            return -1;
        for (int i = 7; i >= 0; --i)
        {
            if (((b >> i) & 0x1) == 1)
                ++cnt;
            else
                break;
        }
        return (cnt > 6 || cnt == 1) ? -1 : cnt;
    }

    public static String validateCode;

    /** 生成数字验证码 */
    public static void codeGenerator()
    {
        java.util.Random random = new java.util.Random(
                System.currentTimeMillis());
        validateCode = "";// 验证码
        for (int i = 0; i < 4; i++)
        {
            validateCode += Math.abs(random.nextInt()) % 10;
        }
    }

    /** 生成大小写混合字母验证码 */
    public static void UppercaseLowercaseLetterCodeGenerator()
    {
        Random ran = new Random();
        String X = "";
        for (int i = 0; i < 4; i++)
        {
            int choice = ran.nextInt(2) % 2 == 0 ? 65 : 97; // 类型:65大写,97小写
            char ch = (char) (choice + ran.nextInt(26)); // 用choice + (0-25)的随机数
                                                         // 就得到了大小写
            X += String.valueOf(ch);
        }
        validateCode = X.trim();
    }

    /** 生成纯大写字母验证码 */
    public static void UppercaseLetterCodeGenerators()
    {
        String X = "";
        for (int i = 0; i < 4; i++)
        {
            X += (char) (Math.random() * 26 + 'A');
        }
        validateCode = X.trim();
    }

    // #if shortname=='iq' && cpid==600337
    /**
     * 转换字符串为二进制数组，编码方式为UTF-8
     * 
     * @param s
     * @return
     */
    public static byte[] stringToBytes(String s, String enc)
    {

        if (s == null || s.length() == 0)
        {

            return null;
        }

        if (enc == null || enc.length() == 0)
            enc = "UTF-8";
        try
        {
            return s.getBytes("UTF-8");
        } catch (Exception e)
        {
            return null;
        }
    }

    /**
     * 转换一个二进制数组中指定长度数据为字符串
     * 
     * @param bytes
     *            需要转换的二进制数组
     * @param off
     *            开始转换的起始位置
     * @param len
     *            转换的长度
     * @return 转换后的字符串
     */
    public static String bytesToString(byte[] bytes, int off, int len,
            int maxLen)
    {
        return bytesToString(bytes, off, len, maxLen, "UTF-8");
    }

    /**
     * 转换一个二进制数组中指定长度数据为字符串
     * 
     * @param bytes
     *            需要转换的二进制数组
     * @param off
     *            开始转换的起始位置
     * @param len
     *            转换的长度
     * @return 转换后的字符串
     */
    public static String bytesToString(byte[] bytes, int off, int len,
            int maxLen, String enc)
    {
        String ret = null;
        if (enc == null)
            enc = "UTF-8";
        if (bytes != null)
        {
            if (len > maxLen)
                len = maxLen;

            try
            {
                ret = new String(bytes, off, len, enc);
            } catch (Exception e)
            {
            }

            if (ret == null)
            { // 否则使用缺省编码方式
                try
                {
                    ret = new String(bytes, off, len);
                } catch (Exception e)
                {
                }
            }
        }
        return ret;

    }

    public static boolean isWords(String s)
    {
        if (s == null || s.length() == 0)
            return false;
        for (int i = 0; i < s.length(); i++)
        {
            char cv = s.charAt(i);
            if (Character.isDigit(s.charAt(i)))
                if (!(cv >= 'a' && cv <= 'z' || cv >= 'A' && cv <= 'Z'))
                    return false;
        }
        return true;
    }

}
