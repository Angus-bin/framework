package com.romaway.android.phone.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewConfiguration;
import android.widget.ListView;

/**
 * Created by hongrb on 2017/8/29.
 */
public class DragListView extends ListView {

    private int scaledTouchSlop;//判断滑动的一个距离,scroll的时候会用到

    public DragListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        scaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }
}
