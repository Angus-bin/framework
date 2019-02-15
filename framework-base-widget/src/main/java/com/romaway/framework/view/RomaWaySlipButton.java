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

public class RomaWaySlipButton extends CompoundButton implements OnTouchListener
{
    protected boolean NowChoose = false;

    protected boolean isChecked;

    protected boolean OnSlip = false;

    private float DownX;

	protected float NowX;

    protected RectF Btn_On, Btn_Off;

    //private Bitmap  slip_btn;
    
    //private Bitmap bg_on_off; 
    protected int color_on_off;
    protected int slip_Color = 0xffffffff;
    private int[] color_on_offArr = {Color.GREEN, 0xff5e5e5e};
    protected boolean isTouch = false;
    private float xishu;//适配系数
    private Resources res;
    private float densityXushu;
    
    public RomaWaySlipButton(Context context)
    {
        this(context, null);
    }

    public RomaWaySlipButton(Context context, AttributeSet attrs)
    {
    	this(context, attrs, 0);      
    }

    public RomaWaySlipButton(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    	updataBitmapResource();      
        setOnTouchListener(this);
        res = context.getResources();
        densityXushu = getDensityXishu(res);
        diameter = diameter*densityXushu;
        radius = diameter / 2f;//半径
        space = 2f;
        onOff_radius = radius+space;
        onOffwidth = onOff_radius * 4f;
        onOffHeight = onOff_radius * 2f;
    }

    protected Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    
    protected float diameter = 66f;//直径
    protected float radius;//半径
    
    protected float space;
    protected float onOff_radius;
    protected float onOffwidth;
    protected float onOffHeight;
    
    protected void updataBitmapResource(){
        
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(2);
        
    	if(isChecked() == true)
    	    color_on_off = color_on_offArr[0] == 0 ?Color.GREEN : color_on_offArr[0];
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
            }else{
                canvas.drawCircle(x+onOff_radius, 0+onOff_radius, radius, paint);
            }
        }
    }

	/**
	 * 画开关控件背景
	 * @param canvas
	 */
	protected void drawOnOff(Canvas canvas){
	    paint.setColor(color_on_off);
        canvas.drawArc(new RectF(0,0,onOffHeight,onOffHeight), 90.0F, 180.0F, false, paint); 
        canvas.drawRect(new RectF(onOff_radius,0,onOffwidth - onOff_radius, onOffHeight), paint);
        canvas.drawArc(new RectF(onOffwidth - onOffHeight-2,0,onOffwidth,onOffHeight), 90, -180, false, paint);
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

                if (event.getX() >= (onOffwidth / 2))
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
