package com.actionbarsherlock.app;


/**
 * Created by edward on 16/4/14.
 * 用于支持Fragment监听返回键等点击处理:
 */
public interface BackPressedInterface {

    /**
     * 所有实现BackPressedInterface的子类都在此方法中实现物理Back键按下后的逻辑
     * FragmentActivity捕捉到物理返回键点击事件后会首先询问Fragment是否消费该事件
     * 如果没有Fragment消息时FragmentActivity自己才会消费该事件
     */
    boolean backKeyCallBack();
}
