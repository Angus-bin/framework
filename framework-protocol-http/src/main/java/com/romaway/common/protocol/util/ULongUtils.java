package com.romaway.common.protocol.util;


import com.romaway.commons.log.Logger;

/**
 * Created by edward on 16/7/26.
 */
public class ULongUtils {
    private final static String TAG = "ULongUtils";

    /** 获取BitMap值(移位算值) */
    public static ULong get(int index){
        ULong bit = ULong.valueOf(1);
        return ULong.valueOf(bit.longValue()<<index);
    }

    public static ULong merge(ULong u1, ULong u2){
        return ULong.valueOf((u1.longValue() | u2.longValue()));
    }

    /** 计算总BitMap值(通过 或| 运算, 避免重复add导致数值计算错误) */
    public static ULong merge(ULong[] ulongs){
        ULong tmp = ULong.valueOf(0);
        for (ULong ulong: ulongs) {
            tmp = ULong.valueOf(tmp.longValue() | ulong.longValue());
        }
        return tmp;
    }

    /** 根据请求字段资源ID数组, 计算请求总Bitmap值 */
    public static long getWholeBitMap(int[] reqFieldRes) {
        ULong[] uLongs = new ULong[reqFieldRes.length];
        for (int i=0; i<reqFieldRes.length; i++){
            uLongs[i] = ULongUtils.get(reqFieldRes[i]);
            Logger.i(TAG, "reqFields >> " + uLongs[i]);
        }
        long whole = ULongUtils.merge(uLongs).longValue();
        Logger.i(TAG, "reqFields whole >> " + whole);
        return whole;
    }
}
