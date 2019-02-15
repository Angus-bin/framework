package com.romaway.framework.app;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.actionbarsherlock.internal.widget.ActionBarView;
import com.actionbarsherlock.internal.widget.ActionBarView.OnCompleteMenuLayoutListener;
import com.actionbarsherlock.internal.widget.ActionBarView.OnLoadMenuActionListener;
import com.trevorpage.tpsvg.SVGParserRenderer;

import roma.romaway.abs.android.keyboard.RomaWayKeyBoardView;

public abstract class BaseActionBar {
    
    /**设置Title颜色*/
    public abstract void setTitleColor(int color);
    /**设置Title字体大小*/
    public abstract void setTitleTextSize(float size);
    /**设置Subtitle颜色*/
    public abstract void setSubtitleColor(int color);
    /**设置Subtitle字体大小*/
    public abstract void setSubTitleTextSize(float size);
    /**设置Rzrqtitle颜色*/
    public abstract void setSubTitleLeftColor(int color);
    /**设置Rzrqtitle颜色*/
    public abstract void setSubTitleLeftBGDrawable(int color);
    
     public abstract View findViewById(int resId);

     public abstract ActionBarView getActionBarView();
     
     /**显示顶部ActionBar*/
     public abstract void showTitle();
     /**隐藏顶部ActionBar*/
     public abstract void hideTitle();
     /**设置顶部ActionBar 中间默认Title部分的图片*/
     public abstract void setTitleCompoundDrawables(Drawable left, Drawable top, Drawable right, Drawable bottom);

     /**显示底部BottomBar*/
     public abstract void showBottomBar();
     
     /**隐藏底部BottomBar*/
     public abstract void hideBottomBar();
     
     /**设置底部BottomBar背景*/
     public abstract void setBottomBarBackgroundColor(int color);
     
     /**
      * 重新加载Title布局为新的自定义
      */
     public abstract void resetTitle(int layoutResId);
     
     /**
      * 重新加载Title布局为默认的
      */
     public abstract void resetTitleToDefault();
     
     /*****右上角的布局部分***/
     /**设置菜单的布局*/
     public abstract void setMenuLayout(int layoutResId,OnCompleteMenuLayoutListener onCompleteMenuLayoutListener);
     /**获取右上角菜单View*/
     public abstract View getMenuView();
     
     /**设置左上角的文本显示*/
     public abstract void setLeftText(CharSequence text);
     /**设置左上角的文本 颜色*/
     public abstract void setLeftTextColor(int Color);
     /**设置左上角的文本大小*/
     public abstract void setLeftTextSize(int size);
     /**设置左边SVG格式的图片*/
     public abstract void setLeftSvgIcon(SVGParserRenderer svgParserRenderer);
     /**设置右上角的文本显示*/
     public abstract void setRightText(CharSequence text);
     /**设置右上角的文本 颜色*/
     public abstract void setRightTextColor(int Color);
     /**设置右上角的文本大小*/
     public abstract void setRightTextSize(int size);
     /**设置右上角的图片*/
     public abstract void setRightBitmp(Bitmap mBitmap);

     /**
      * 隐藏左上角返回按钮
      */
     public abstract void hideIcon();
     /**
      * 显示左上角返回按钮
      */
     public abstract void showIcon();
     /**
      * 隐藏左上角返回按钮
      */
     public abstract void hideRightText();
     /**
      * 显示左上角返回按钮
      */
     public abstract void showRightText();
     /**
      * 显示右上角图片
      */
     public abstract void showRightBitmp();
     /**
      * 隐藏右上角图片
      */
     public abstract void hideRightBitmp();

     
     /**
      * 设置ActionBar背景颜色
      * @param color
      */
     public abstract void setBackgroundColor(int color);
     
     /**
      * 将EditText绑定到Roma键盘
      * @param editText
      * @param defKeyboardXmlId 设置自定义默认键盘
      */
     public abstract void bundingRomaKeyboard(EditText editText, int defKeyboardXmlId, OnClickListener hideButtonListener);
     
     /**
      * 将EditText绑定到Roma键盘同时加载展示键盘
      * @param editText
      * @param defKeyboardXmlId 设置自定义默认键盘
      */
     public abstract void bundingRomaKeyboardAndLoad(EditText editText, int defKeyboardXmlId,
             OnClickListener hideButtonListener);
     /**
      * 加载并显示键盘
      * @param defKeyboardXmlId 键盘的xml资源ID
      * @param editBottomY  输入框文本的底部Y坐标
      */
     public abstract void loadShowKeyboard(int defKeyboardXmlId, int editBottomY, OnKeyboardActionListener listener,OnClickListener hideButtonListener);
     
     /**设置隐藏按钮的可见性*/
     public abstract void setRomaKeyboardHideButtonVisibility(int visibility);
     /**设置隐藏按钮的监听器*/
     public abstract void setOnClickHideButtonListener(OnClickListener l);
     
     /**获取自定义键盘*/
     public abstract RomaWayKeyBoardView getRomaKeyBoardView();
     /**显示自定义键盘*/
     public abstract void showKeyboard();
     
     /**隐藏自定义键盘*/
     public abstract void hideKeyboard();
     
     /**键盘的可见性*/
     public abstract int getKeyboardVisibility();
     
     public abstract void setShowHideAnimationEnabled(boolean enabled);
     
     /**用于监听自定义菜单设置成功，可以在回调方法中来处理了*/
     public abstract void setOnLoadMenuActionListener(OnLoadMenuActionListener onLoadMenuActionListener);

     /******右上角的默认操作按钮*****/
     /**
      * 显示网络请求进度条，也即右上旋转进度条
      */
     public abstract void showNetReqProgress();
     /**
      * 隐藏网络请求进度条，也即右上旋转进度条
      */
     public abstract void hideNetReqProgress();
     /**
      * 隐藏右上角刷新按钮
      */
     public abstract void hideRefreshButton();
     /**
      * 隐藏右上角查找按钮
      */
     public abstract void hideSearchButton();
     /**
      * 显示右上角刷新按钮
      */
     public abstract void showRefreshButton();
     /**
      * 显示右上角查找按钮
      */
     public abstract void showSearchButton();
     /**
      * 由于点击编辑框会网上推一些位置，在此获取框架，以便返回之前位置，目前先用在预警设置
      */
     public abstract LinearLayout get_abs__root_content();
     /**
      * 由于点击编辑框会网上推一些位置，在此推动距离，以便返回之前位置，目前先用在预警设置
      */
     public abstract int get_tran_distance();

}
