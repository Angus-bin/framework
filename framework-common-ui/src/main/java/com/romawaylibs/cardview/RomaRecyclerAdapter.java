package com.romawaylibs.cardview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by wanlh on 2016/7/21.
 */
class RomaRecyclerAdapter extends Adapter<ViewHolder> {
    private LayoutInflater inflater;
    private Context context;

    private ArrayList<Object> datas;
    private Card currentCard;
    private RomaBean currentRomaBean;

    public RomaRecyclerAdapter(Context context) {
        super();
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setDatas(ArrayList<Object> datas) {
        this.datas = datas;
    }

    /**
     * 设置当前加载的 card
     *
     * @param card
     */
    public void initCurrentCard(Card card) {
        currentCard = card;
    }

    /**
     * 设置当前加载卡片数据 bean
     *
     * @param romaBean
     */
    public void setCurrentRomaBean(RomaBean romaBean) {
        currentRomaBean = romaBean;
    }

    /**
     * 获取某个选项的数据
     *
     * @param position
     * @return 一般为json数据
     */
    public Object getItemDatas(int position) {
        return datas == null ? null : datas.get(position);
    }

    @Override
    public int getItemCount() {

        return datas == null ? 0 : datas.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (currentRomaBean != null) {
            RomaViewHolder holder = (RomaViewHolder) viewHolder;
            holder.push(currentRomaBean);
        }
    }

    @Override
    public RomaViewHolder onCreateViewHolder(ViewGroup viewHolder, int viewType) {
        if (currentCard == null)
            return null;
        currentCard.createCardView();// 创建卡片视图
        return currentCard.getViewHolder();
    }
}
