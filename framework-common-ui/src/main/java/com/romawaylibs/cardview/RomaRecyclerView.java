package com.romawaylibs.cardview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by wanlh on 2016/7/21.
 */
public class RomaRecyclerView extends RecyclerView {

    public RomaRecyclerView(Context context) {
        this(context, null);
    }

    public RomaRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RomaRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
}
