package roma.romaway.commons.android.tougu.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

import com.romaway.android.phone.R;
import com.romaway.android.phone.utils.DensityUtil;
import com.romaway.common.android.base.Res;
import com.romaway.commons.log.Logger;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * Created by hongrb on 2016/7/9 17:38
 */
public class ScrollBarView extends RomaHorizontalScrollView {
    private int bottomLineColor = 0xff000000;
    private int indicatorColor = 0xff000000;
    public ScrollBarView(Context context) {
        super(context);
    }
    public ScrollBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(bottomLineColor);
        paint.setAntiAlias(true);
        width = canvas.getWidth();
        int round = AutoUtils.getPercentWidthSize(Res.getInteger(R.integer.homepage_tougu_scrollbar_slider_corner));
        float radius = AutoUtils.getPercentWidthSize(Res.getInteger(R.integer.homepage_tougu_scrollbar_slider_height));
        float Size1 = DensityUtil.dip2px(getContext(), 17f);
        float Size2 = DensityUtil.dip2px(getContext(), 17f);
        float height = DensityUtil.dip2px(getContext(), 3.5f);
        RectF rect = new RectF(width / 2f - Size1, canvas.getHeight() / 2f - radius, width / 2f + Size2, canvas.getHeight() / 2f + radius);
        canvas.drawRoundRect(rect, round, round, paint);
        float Size3 = DensityUtil.dip2px(getContext(), 4f);
        Logger.d("radius", "radius:   " + radius + "   Size1:   " + Size1 + "   Size2:   " + Size2 + "   height:   " + height
                + "   Size3:   " + Size3 + "   offset:   " + offset);
        float tempWidth = (Size1 + Size2 - (Size1 - Size3) ) * offset;
        RectF rect2 = new RectF(width / 2f - Size1 + tempWidth, canvas.getHeight() / 2f - radius, width / 2f - Size3 + tempWidth, canvas.getHeight() / 2f + radius);
        paint.setColor(indicatorColor);
        canvas.drawRoundRect(rect2, round, round, paint);
    }

    private int width = 0;

    public float getOffset() {
        return offset;
    }

    public void setOffset(float offset) {
        this.offset = offset;
    }

    private float offset = 0;



    @Override
    protected void onScrollChanged(int x, int y, int oldX, int oldY) {
        super.onScrollChanged(x, y, oldX, oldY);

    }

    public void setBottomLineColor(int color){
        bottomLineColor = color;
    }

    public void setIndicatorColor(int color){
        indicatorColor = color;
    }
}
