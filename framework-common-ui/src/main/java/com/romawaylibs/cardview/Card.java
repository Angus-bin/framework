package com.romawaylibs.cardview;

import android.view.View;

/**
 * Created by wanlh on 2016/7/21.
 */
public interface Card {
    /**
     * 创建卡片视图
     * @return
     */
    public View createCardView();

    /**
     * 获取卡片 holder
     * @return
     */
    public RomaViewHolder getViewHolder();
}
