package com.romaway.android.phone.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import com.romaway.common.android.base.OriginalContext;
import com.romaway.commons.lang.Base64;
import com.romaway.commons.log.Logger;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;

/**
 * Created by edward on 16/8/30.
 */
public class BitmapUtils {

    private final static String TAG = "BitmapUtils";

    /**
     * 将Drawable图片 转 Base64字符串
     */
    public static String drawableToBase64(int drawableId) {
        return bitmapToBase64(BitmapFactory.decodeResource(OriginalContext.getContext().getResources(), drawableId));
    }

    /**
     * 将Base64字符串 转 Bitmap
     */
    public static Bitmap base64ToBitmap(String base64Str) {
        try {
            // Base64编码默认采用 Base64.DEFAULT, 当字符串过长(一般超过76)时会自动添加换行符(\n等);
            byte[] bytes;
            if (base64Str.contains("\n"))
                bytes = Base64.decode(base64Str, Base64.DEFAULT);
            else
                bytes = Base64.decode(base64Str, Base64.NO_WRAP);       // 指定采用 NO_WRAP 省略所有行结束符
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }catch (Exception e){
            Logger.e(TAG, e.getMessage());
            return null;
        }
    }

    /**
     * 将Base64字符串 转 Bitmap
     * @param base64Str         图片Base64字符串
     * @param bitmapSize        图片压缩大小
     * @return
     */
    public static Bitmap base64ToBitmap(String base64Str, int bitmapSize) {
        // 1. base64转Bitmap对象; 2. 将Bitmap中透明色替换为白色(需优先于压缩); 3. 压缩Bitmap对象, 避免微信分享等失败;
        return compressBitmapSize(BitmapUtils.changeColor(base64ToBitmap(base64Str)), bitmapSize);
    }

    /**
     * 将Bitmap 转 Base64字符串
     * @param bitmap            图标Bitmap对象
     * @return
     */
    public static String bitmapToBase64(Bitmap bitmap) {
        String result = "";
        ByteArrayOutputStream bos = null;
        try {
            if (null != bitmap) {
                bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, bos);//将bitmap放入字节数组流中

                bos.flush();//将bos流缓存在内存中的数据全部输出，清空缓存
                bos.close();

                byte[] bitmapByte = bos.toByteArray();
                result = Base64.encodeToString(bitmapByte, Base64.NO_WRAP);
            }
        } catch (Exception e) {
            Logger.e(TAG, e.getMessage());
        } finally {
            closeFileStream(bos);
        }
        return result;
    }

    /**
     * 压缩Bitmap至指定大小(例: 微信分享用32kb)
     * @param bitmap
     * @param bitmapSize
     * @return
     */
    public static Bitmap compressBitmapSize(Bitmap bitmap, int bitmapSize) {
        Bitmap compressBitmap = null;
        ByteArrayOutputStream bos = null;
        ByteArrayInputStream bis = null;
        if (bitmap != null) {
            try {
                bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                int options = 100;
                while (bos.toByteArray().length / 1024 > bitmapSize) {
                    bos.reset();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, options, bos);
                    options -= 10;//每次都减少10
                }
                bitmap.recycle();
                bis = new ByteArrayInputStream(bos.toByteArray());
                compressBitmap = BitmapFactory.decodeStream(bis, null, null);
            } catch (Exception e) {
                Logger.e(TAG, e.getMessage());
            } finally {
                closeFileStream(bos);
                closeFileStream(bis);
            }
        }
        return compressBitmap;
    }

    /**
     * 将BitmapFile转换成base64字符串
     *
     * @param file
     * @param bitmapSize
     * @return
     */
    public static String decodeBitmapFiletoString(File file, int bitmapSize) {
        // 将Bitmap转换成字符串
        String baseStr = null;
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        int option = 100;
        try {
            bitmap.compress(Bitmap.CompressFormat.JPEG, option, bStream);
            while (bStream.size() / 1024 >= bitmapSize) {
                option -= 10;
                bitmap.compress(Bitmap.CompressFormat.JPEG, option, bStream);
            }
            byte[] bytes = bStream.toByteArray();
            baseStr = Base64.encodeToString(bytes, Base64.DEFAULT);
            bitmap.recycle();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            closeFileStream(bStream);
        }
        return baseStr;
    }

    /** 关闭流 */
    public static void closeFileStream(Closeable fileStream) {
        if (null != fileStream) {
            try {
                fileStream.close();
            } catch (IOException e) {
                Logger.e(TAG, e.getMessage());
            }
        }
    }


    /**
     * bitmap中的透明色用白色替换
     *
     * @param bitmap
     * @return
     */
    public static Bitmap changeColor(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        int[] colorArray = new int[w * h];
        int n = 0;
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                int color = getMixtureWhite(bitmap.getPixel(j, i));
                colorArray[n++] = color;
            }
        }
        return Bitmap.createBitmap(colorArray, w, h, Bitmap.Config.ARGB_8888);
    }

    /**
     * 获取和白色混合颜色
     *
     * @return
     */
    private static int getMixtureWhite(int color) {
        int alpha = Color.alpha(color);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.rgb(getSingleMixtureWhite(red, alpha), getSingleMixtureWhite

                        (green, alpha),
                getSingleMixtureWhite(blue, alpha));
    }

    /**
     * 获取单色的混合值
     *
     * @param color
     * @param alpha
     * @return
     */
    private static int getSingleMixtureWhite(int color, int alpha) {
        int newColor = color * alpha / 255 + 255 - alpha;
        return newColor > 255 ? 255 : newColor;
    }
}
