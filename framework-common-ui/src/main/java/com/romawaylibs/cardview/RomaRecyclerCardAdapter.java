package com.romawaylibs.cardview;

import android.content.Context;
import android.view.LayoutInflater;

/**
 * 基于 Recycler 的卡片适配器
 * Created by wanlh on 2016/7/25.
 */
public class RomaRecyclerCardAdapter extends RomaRecyclerAdapter {
    private LayoutInflater inflater;
    private Context context;

    public RomaRecyclerCardAdapter(Context context) {
        super(context);
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }
}
