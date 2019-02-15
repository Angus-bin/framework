package com.romaway.android.phone.utils;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;

import com.romaway.common.android.base.Res;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * Created by edward on 16/3/31.
 */
public class DrawableUtils {

    /**
     * 获取圆角背景图
     * @param bgColor       背景颜色
     * @param mCorner       圆角弧度(内部自动完成屏幕适配)
     */
    public static ShapeDrawable getShapeDrawable(int bgColor, int mCorner){
        int corner = AutoUtils.getPercentWidthSize(mCorner);
        ShapeDrawable mDrawable = new ShapeDrawable(new RoundRectShape(new float[] { corner, corner, corner,
                corner, corner, corner, corner, corner }, null, null));
        mDrawable.getPaint().setColor(bgColor);
        return mDrawable;
    }

    /**
     * 获取圆角背景图
     * @param bgColor       背景颜色
     * @param mCorner       圆角弧度(内部自动完成屏幕适配)
     */
    public static ShapeDrawable getShapeDrawableConvertsColor(int bgColor, int[] oldColors, int[] newColors, int mCorner){
        int corner = AutoUtils.getPercentWidthSize(mCorner);
        ShapeDrawable mDrawable = new ShapeDrawable(new RoundRectShape(new float[] { corner, corner, corner,
                corner, corner, corner, corner, corner }, null, null));
        for (int i = 0; i < oldColors.length; i++){
            if (oldColors[i] == bgColor){
                bgColor = newColors[i];
            }
        }
        mDrawable.getPaint().setColor(bgColor);
        return mDrawable;
    }

    /**
     * 获取矩形背景图
     * @param strokeColor   线框颜色
     */
    public static ShapeDrawable getStrokeDrawable(int shapeDrawableId, int strokeColor){
        try {
            ShapeDrawable drawable = (ShapeDrawable) Res.getDrawable(shapeDrawableId);
            drawable.getPaint().setColor(strokeColor);
            return drawable;
        }catch (Exception e){
            return null;
        }
    }
}
