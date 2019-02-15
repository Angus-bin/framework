package com.romaway.framework.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.CompoundButton;

public class RomaWayTextSizeSlipButton extends CompoundButton implements OnTouchListener
{
    private boolean NowChoose = false;

    private boolean isChecked;

    private boolean OnSlip = false;

    private float DownX, NowX;

    private RectF Btn_On, Btn_Off;

    //private Bitmap  slip_btn;
    
    //private Bitmap bg_on_off; 
    private int color_on_off;
    private int slip_Color = 0xffff3434;
    private int[] color_on_offArr = {0xff5e5e5e, 0xff5e5e5e};
    private boolean isTouch = false;
    private float xishu;//适配系数
    private Resources res;
    private float densityXushu;
    private int textPaintColor = Color.WHITE;
    private int textSmallPaintColor = Color.WHITE;
    
    public RomaWayTextSizeSlipButton(Context context)
    {
        this(context, null);
    }

    public RomaWayTextSizeSlipButton(Context context, AttributeSet attrs)
    {
    	this(context, attrs, 0);      
    }

    public RomaWayTextSizeSlipButton(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    	updataBitmapResource();      
        setOnTouchListener(this);
        res = context.getResources();
        densityXushu = getDensityXishu(res);
        diameter = diameter*densityXushu;
        radius = (diameter / 2f);//半径
        onOff_radius = radius+space;
        onOffwidth = onOff_radius * 4f;
        onOffHeight = onOff_radius * 2f;
    }

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint paintLine = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint textSmallPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    
    private float diameter = 66f;//直径
//    private float radius = (diameter / 2f)*densityXushu;//半径
    private float radius;//半径
    private float space = 2f;
    private float onOff_radius;
    private float onOffwidth;
    private float onOffHeight;
    
    private void updataBitmapResource(){
        
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(2);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setStrokeWidth(2);
        textPaint.setColor(textPaintColor);
        textSmallPaint.setStyle(Paint.Style.FILL);
        textSmallPaint.setStrokeWidth(2);
        textSmallPaint.setColor(textSmallPaintColor);
        paintLine.setStyle(Paint.Style.FILL);
        paintLine.setStrokeWidth(2);
        paintLine.setColor(Color.WHITE);
    	if(isChecked() == true)
    	    color_on_off = color_on_offArr[0] == 0 ?0xff5e5e5e : color_on_offArr[0];
        else
            color_on_off = color_on_offArr[1] == 0 ?0xff5e5e5e : color_on_offArr[1];
        	    	
        Btn_On = new RectF(space, space, diameter, diameter);
        Btn_Off = new RectF(onOffwidth - diameter-space, 0, onOffwidth-space,diameter);
    }
    
    /**
     * 设置开关背景颜色
     * @param color
     */
    public void setOnOffBackground(int[] color){
        color_on_offArr = null;
        color_on_offArr = color;
        updataBitmapResource();
        invalidate();
    }
    
    public void setSlipBackground(int color){
        slip_Color = color;
        invalidate();
    }
    
    public void setTextPaintColor(int color) {
		this.textPaintColor = color;
		updataBitmapResource();
		invalidate();
	}

	public void setTextSmallPaintColor(int color) {
		this.textSmallPaintColor = color;
		updataBitmapResource();
		invalidate();
	}

	@Override
    public void setButtonDrawable(Drawable d){

        return;
     }
    
    @Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height;
        if (widthMode == MeasureSpec.EXACTLY)
        {
            width = widthSize;
        } else
        {
            
            width = (int) onOffwidth;
        }

        if (heightMode == MeasureSpec.EXACTLY)
        {
            height = heightSize;
        } else
        {
           
            height = (int) onOffHeight;
        }

        setMeasuredDimension(width, height);
	}

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        float x;
        
        updataBitmapResource();
        
        if (NowX < ((onOffwidth-space) / 2))
            x = NowX - diameter / 2; 
        else     
            x = (onOffwidth-space) - (onOffwidth-space) / 2;            
        
        drawOnOff(canvas);
        
        if (OnSlip)
        {
            if (NowX >= onOffwidth)
                x = (onOffwidth-space) - diameter / 2;

            else if (NowX < 0)
            {
                x = 0;
            }
            else
            {
                x = NowX - diameter / 2;
            }
        }
        else
        {

            if (NowChoose)
            {
                x = Btn_Off.left;
                drawOnOff(canvas);
            }
            else
                x = Btn_On.left;
        }
        if (isChecked)
        {
            drawOnOff(canvas);
            x = Btn_Off.left;
            isChecked = !isChecked;
        }
        if (x < 0){
            x = 0;
        }else if (x > (onOffwidth-space) - diameter){
            x = (onOffwidth-space) - onOffHeight;           
        }
        
        paint.setColor(slip_Color);
        if(isTouch){
        	isTouch = false;
        	canvas.drawCircle(x+onOff_radius-space, 0+onOff_radius, radius, paint);
        }else{
        	if(isChecked() == true){
        		x = onOffwidth - onOffHeight; 
        		canvas.drawCircle(x+onOff_radius , 0+onOff_radius, radius, paint);
        		//右边白球上大T的横线
        		canvas.drawLine(x+onOff_radius-radius/3, 0+onOff_radius-radius/2,
        				x+onOff_radius+radius/3, 0+onOff_radius-radius/2, textPaint);
        		//右边白球上大T的竖线
        		canvas.drawLine(x+onOff_radius, 0+onOff_radius-radius/2,
        				x+onOff_radius, 0+onOff_radius+radius/2, textPaint);
        		//左边小T的横线
        		canvas.drawLine(Btn_On.left+onOff_radius-radius/4, 0+onOff_radius-radius/3,
        				Btn_On.left+onOff_radius+radius/4, 0+onOff_radius-radius/3, textSmallPaint);
        		//左边小T的竖线
        		canvas.drawLine(Btn_On.left+onOff_radius, 0+onOff_radius-radius/3,
        				Btn_On.left+onOff_radius, 0+onOff_radius+radius/3, textSmallPaint);
        		
            }else{
                canvas.drawCircle(x+onOff_radius, 0+onOff_radius, radius, paint);
                //左边白球上小T的横线
        		canvas.drawLine(x+onOff_radius-radius/4, 0+onOff_radius-radius/3,
        				x+onOff_radius+radius/4, 0+onOff_radius-radius/3, textPaint);
        		//左边白球上小T的竖线
        		canvas.drawLine(x+onOff_radius, 0+onOff_radius-radius/3,
        				x+onOff_radius, 0+onOff_radius+radius/3, textPaint);
        		//右边大T的横线
        		canvas.drawLine(onOffwidth-onOffHeight+onOff_radius-radius/3, 0+onOff_radius-radius/2,
        				onOffwidth - onOffHeight+onOff_radius+radius/3, 0+onOff_radius-radius/2, textSmallPaint);
        		//右边大T的竖线
        		canvas.drawLine(onOffwidth-onOffHeight+onOff_radius, 0+onOff_radius-radius/2,
        				onOffwidth-onOffHeight+onOff_radius, 0+onOff_radius+radius/2, textSmallPaint);
                
            }
        }
    }
    
    public boolean getIsChecked(){
    	return isChecked;
    }

	/**
	 * 画开关控件背景
	 * @param canvas
	 */
	private void drawOnOff(Canvas canvas){
	    paint.setColor(color_on_off);
	    //边缘线
	    float lineWidth = 1.5f;
        //画内容
        canvas.drawArc(new RectF(0,0,onOffHeight,onOffHeight), 90.0F, 180.0F, false, paintLine/*paint*/); 
        canvas.drawRect(new RectF(onOff_radius,0,onOffwidth - onOff_radius, onOffHeight), paintLine/*paint*/);
        canvas.drawArc(new RectF(onOffwidth - onOffHeight-2,0,onOffwidth,onOffHeight), 90, -180, false, paintLine/*paint*/);
        
        canvas.drawArc(new RectF(lineWidth,lineWidth,onOffHeight,onOffHeight-lineWidth), 90.0F, 180.0F, false, paint); 
        canvas.drawRect(new RectF(onOff_radius,0 + lineWidth,onOffwidth - onOff_radius, onOffHeight -lineWidth), paint);
        canvas.drawArc(new RectF(onOffwidth - onOffHeight-2,0 + lineWidth,onOffwidth - lineWidth,onOffHeight - lineWidth), 90, -180, false, paint);
	}
	@Override
	public void setChecked(boolean checked){
		super.setChecked(checked);
		invalidate();
	}
    public boolean onTouch(View v, MotionEvent event)
    {
    	
    	isTouch = true;
        switch (event.getAction())
        {
            case MotionEvent.ACTION_MOVE:
                NowX = event.getX();
                break;

            case MotionEvent.ACTION_DOWN:

                if (event.getX() > onOffwidth || event.getY() > onOffwidth)
                    return false;
                OnSlip = true;
                DownX = event.getX();
                NowX = DownX;
                break;

            case MotionEvent.ACTION_CANCEL:

                OnSlip = false;
                if (NowX >= (onOffwidth / 2))
                {
                    NowX = onOffwidth - diameter / 2;
                    NowChoose = true;
                }
                else
                {
                    NowX = NowX - diameter / 2;
                    NowChoose = false;
                }
                	setChecked(NowChoose);
                	
                break;
            case MotionEvent.ACTION_UP:

                OnSlip = false;
                isTouch = false;//滑动状态结束2015.4.29zhaoxk
                if (event.getX() >= (onOffwidth / 2))
                {//为开启状态
                    NowX = onOffwidth - diameter / 2;
                    NowChoose = true;
                }
                else
                {//为关闭状态
                    NowX = NowX - diameter / 2;
                    NowChoose = false;
                }
                	setChecked(NowChoose);
                	
                break;
            default:
        }
        invalidate();
        return true;
    }

    public float getDensityXishu(Resources res){
        //Logger.d("tag", "res.getDisplayMetrics().widthPixels = "+res.getDisplayMetrics().widthPixels);
    	xishu = 3.0f;//适配系数
        if(res.getDisplayMetrics().widthPixels > 1000)
            xishu = 2.85f;
        
        return res.getDisplayMetrics().density / xishu;
    }
}
