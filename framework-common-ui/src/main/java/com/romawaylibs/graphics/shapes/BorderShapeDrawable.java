package com.romawaylibs.graphics.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;

/**
 *用于设置带边框的图片 
 */
public class BorderShapeDrawable extends ShapeDrawable {
    //Paint.ANTI_ALIAS_FLAG代表这个画笔的图形是光滑的
    private Paint mStrokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    
    private int CanvasColor;
    
    /**
     * 
     * @param s
     * @param color 边框的颜色
     * @param strokeWidth 边框的粗细大小
     */
    public BorderShapeDrawable(Shape s, int color, float strokeWidth) {
        super(s);
        mStrokePaint.setAntiAlias(true);
        mStrokePaint.setStyle(Paint.Style.STROKE);
        mStrokePaint.setColor(color);
        mStrokePaint.setStrokeWidth(strokeWidth);
    }
    
    public Paint getStrokePaint() {
        return mStrokePaint;
    }
    
    @Override 
    protected void onDraw(Shape s, Canvas c, Paint p) {
        super.onDraw(s, c, p);
        //绘制填充效果的图形
        //s.draw(c, p);
        //绘制红边
        c.drawColor(CanvasColor);
        s.draw(c, mStrokePaint);
    }
    
    public void setCanvasColor(int color){
    	CanvasColor=color;
    }

}
