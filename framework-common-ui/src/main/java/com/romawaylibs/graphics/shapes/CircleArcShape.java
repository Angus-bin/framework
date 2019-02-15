package com.romawaylibs.graphics.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.shapes.RectShape;

public class CircleArcShape extends RectShape {
    private float mStart;
    private float mSweep;

    public CircleArcShape(float startAngle, float sweepAngle) {
                mStart = startAngle;
                mSweep = sweepAngle;
            }
           
   @Override
    public void draw(Canvas canvas, Paint paint) {
        canvas.drawArc(new RectF(0,0,100,100), mStart, mSweep, false, paint);
   }
}
