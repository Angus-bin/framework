package com.romaway.android.phone.share;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * Created by edward on 16/4/11.
 */
public class RomaPopupWindow extends PopupWindow {

    private Activity mActivity;
    public RomaPopupWindow(Activity activity, View contentView, int width, int height) {
        super(contentView, width, height);
        this.mActivity = activity;
    }
    
    @Override
    public void dismiss() {
        // 窗口背景变亮:
        dimBackground(0.5f, 1.0f);
        super.dismiss();
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        // 窗口背景变暗:
        dimBackground(1.0f, 0.5f);
        super.showAtLocation(parent, gravity, x, y);
    }

    /**
     * 调整窗口的透明度
     * @param from>=0&&from<=1.0f
     * @param to>=0&&to<=1.0f
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void dimBackground(final float from, final float to) {
        final Window window = mActivity.getWindow();
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(from, to);
        valueAnimator.setDuration(200);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                WindowManager.LayoutParams params = window.getAttributes();
                params.alpha = (Float) animation.getAnimatedValue();
                window.setAttributes(params);
            }
        });
        valueAnimator.start();
    }
}