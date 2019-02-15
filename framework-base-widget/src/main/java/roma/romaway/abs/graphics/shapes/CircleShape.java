package roma.romaway.abs.graphics.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.shapes.RectShape;

/**
 * 画圆的Shape  在android系统中没有实现画圆的api
 * 通过查看系统源码发现只有以下API：
 * RectShape 画矩形
 * RoundRectShape 圆角矩形
 * OvalShape 椭圆形
 * 
 * @author 万籁唤
 * @version 2015/02/11
 */
public class CircleShape extends RectShape{
    private float cx; 
    private float cy; 
    private float radius;
    
    public CircleShape(float cx, float cy, float radius) {
        this.cx = cx;
        this.cy = cy;
        this.radius = radius;
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        // TODO Auto-generated method stub
        canvas.drawCircle(cx, cy, radius, paint);
    }

}
