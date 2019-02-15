/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.actionbarsherlock.internal.widget;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.actionbarsherlock.ResourcesCompat;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.OnNavigationListener;
import com.actionbarsherlock.internal.view.menu.ActionMenuItem;
import com.actionbarsherlock.internal.view.menu.ActionMenuPresenter;
import com.actionbarsherlock.internal.view.menu.ActionMenuView;
import com.actionbarsherlock.internal.view.menu.MenuBuilder;
import com.actionbarsherlock.internal.view.menu.MenuItemImpl;
import com.actionbarsherlock.internal.view.menu.MenuPresenter;
import com.actionbarsherlock.internal.view.menu.MenuView;
import com.actionbarsherlock.internal.view.menu.SubMenuBuilder;
import com.actionbarsherlock.view.CollapsibleActionView;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.android.basephone.widget.R;
import com.romalibs.utils.Res;
import com.trevorpage.tpsvg.SVGParserRenderer;
import com.trevorpage.tpsvg.SVGView;
import com.romawaylibs.picasso.PicassoHelper;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * @hide
 */
public class ActionBarView extends AbsActionBarView {
    private static final String TAG = "ActionBarView";

    /**
     * Display options applied by default
     */
    public static final int DISPLAY_DEFAULT = 0;

    /**
     * Display options that require re-layout as opposed to a simple invalidate
     */
    private static final int DISPLAY_RELAYOUT_MASK =
            ActionBar.DISPLAY_SHOW_HOME |
            ActionBar.DISPLAY_USE_LOGO |
            ActionBar.DISPLAY_HOME_AS_UP |
            ActionBar.DISPLAY_SHOW_CUSTOM |
            ActionBar.DISPLAY_SHOW_TITLE;

    private static final int DEFAULT_CUSTOM_GRAVITY = Gravity.LEFT | Gravity.CENTER_VERTICAL;

    private int mNavigationMode;
    private int mDisplayOptions = -1;
    private CharSequence mTitle;
    private CharSequence mSubtitle;
    private CharSequence mRzrqtitle;
    //private SVGParserRenderer mSVGParserRenderer;
    private Drawable mIcon;
    private Drawable mLogo;
    
    private int mTitleResId = 0;
    private HomeView mHomeLayout;
    private View mMenuLayoutView;
    private HomeView mExpandedHomeLayout;
    private View mTitleLayout;
    private TextView mTitleView;
    private TextView mSubtitleView;
    private TextView mGpbztitleView;
    private ImageView mGpbztitleViewImg;
    //private View mTitleUpView;

    private IcsSpinner mSpinner;
    private IcsLinearLayout mListNavLayout;
    private ScrollingTabContainerView mTabScrollView;
    private View mCustomNavView;
    private IcsProgressBar mProgressView;
    private IcsProgressBar mIndeterminateProgressView;

    private int mProgressBarPadding;
    private int mItemPadding;

    private int mTitleStyleRes;
    private int mSubtitleStyleRes;
    private int mProgressStyle;
    private int mIndeterminateProgressStyle;

    private boolean mUserTitle;
    private boolean mIncludeTabs;
    private boolean mIsCollapsable;
    private boolean mIsCollapsed;

    private MenuBuilder mOptionsMenu;

    private ActionBarContextView mContextView;

    private ActionMenuItem mLogoNavItem;

    private SpinnerAdapter mSpinnerAdapter;
    private OnNavigationListener mCallback;

    //UNUSED private Runnable mTabSelector;

    private ExpandedActionViewMenuPresenter mExpandedMenuPresenter;
    View mExpandedActionView;

    Window.Callback mWindowCallback;

    private RightMenuLayout mRightMenuLayout;
    
    @SuppressWarnings("rawtypes")
    private final IcsAdapterView.OnItemSelectedListener mNavItemSelectedListener =
            new IcsAdapterView.OnItemSelectedListener() {
        public void onItemSelected(IcsAdapterView parent, View view, int position, long id) {
            if (mCallback != null) {
                mCallback.onNavigationItemSelected(position, id);
            }
        }
        public void onNothingSelected(IcsAdapterView parent) {
            // Do nothing
        }
    };

    private final OnClickListener mExpandedActionViewUpListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            final MenuItemImpl item = mExpandedMenuPresenter.mCurrentExpandedItem;
            if (item != null) {
                item.collapseActionView();
            }
        }
    };

    private final OnClickListener mUpClickListener = new OnClickListener() {
        public void onClick(View v) {
            mWindowCallback.onMenuItemSelected(Window.FEATURE_OPTIONS_PANEL, mLogoNavItem);
        }
    };

    Context mContext;
    public ActionBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        // Background is always provided by the container.
        setBackgroundResource(0);
        
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SherlockActionBar,
                R.attr.ROMAactionBarStyle, 0);

        ApplicationInfo appInfo = context.getApplicationInfo();
        PackageManager pm = context.getPackageManager();
        mNavigationMode = a.getInt(R.styleable.SherlockActionBar_ROMAnavigationMode,
                ActionBar.NAVIGATION_MODE_STANDARD);
        mTitle = a.getText(R.styleable.SherlockActionBar_ROMAtitle);
        mSubtitle = a.getText(R.styleable.SherlockActionBar_ROMAsubtitle);

        mLogo = a.getDrawable(R.styleable.SherlockActionBar_ROMAlogo);
        if (mLogo == null) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                if (context instanceof Activity) {
                    //Even though native methods existed in API 9 and 10 they don't work
                    //so just parse the manifest to look for the logo pre-Honeycomb
                    final int resId = ResourcesCompat.loadLogoFromManifest((Activity) context);
                    if (resId != 0) {
                        mLogo = context.getResources().getDrawable(resId);
                    }
                }
            } else {
                if (context instanceof Activity) {
                    try {
                        mLogo = pm.getActivityLogo(((Activity) context).getComponentName());
                    } catch (NameNotFoundException e) {
                        Log.e(TAG, "Activity component name not found!", e);
                    }
                }
                if (mLogo == null) {
                    mLogo = appInfo.loadLogo(pm);
                }
            }
        }

        mIcon = a.getDrawable(R.styleable.SherlockActionBar_ROMAicon);
        if (mIcon == null) {
            if (context instanceof Activity) {
                try {
                    mIcon = pm.getActivityIcon(((Activity) context).getComponentName());
                } catch (NameNotFoundException e) {
                    Log.e(TAG, "Activity component name not found!", e);
                }
            }
            if (mIcon == null) {
                mIcon = appInfo.loadIcon(pm);
            }
        }

        final LayoutInflater inflater = LayoutInflater.from(context);

        mTitleResId = a.getResourceId(
                R.styleable.SherlockActionBar_ROMAtitleLayout,
                R.layout.abs__action_bar_title_item);
        
        final int homeResId = a.getResourceId(
                R.styleable.SherlockActionBar_ROMAhomeLayout,
                R.layout.abs__action_bar_home);

        mHomeLayout = (HomeView) inflater.inflate(homeResId, this, false);

        mExpandedHomeLayout = (HomeView) inflater.inflate(homeResId, this, false);
        mExpandedHomeLayout.setUp(true);
        mExpandedHomeLayout.setOnClickListener(mExpandedActionViewUpListener);
        mExpandedHomeLayout.setContentDescription(getResources().getText(
                R.string.abs__action_bar_up_description));

        mTitleStyleRes = a.getResourceId(R.styleable.SherlockActionBar_ROMAtitleTextStyle, 0);
        mSubtitleStyleRes = a.getResourceId(R.styleable.SherlockActionBar_ROMAsubtitleTextStyle, 0);
        mProgressStyle = a.getResourceId(R.styleable.SherlockActionBar_ROMAprogressBarStyle, 0);
        mIndeterminateProgressStyle = a.getResourceId(
                R.styleable.SherlockActionBar_ROMAindeterminateProgressStyle, 0);

        mProgressBarPadding = a.getDimensionPixelOffset(R.styleable.SherlockActionBar_ROMAprogressBarPadding, 0);
        mItemPadding = a.getDimensionPixelOffset(R.styleable.SherlockActionBar_ROMAitemPadding, 0);

        setDisplayOptions(a.getInt(R.styleable.SherlockActionBar_ROMAdisplayOptions, DISPLAY_DEFAULT));

        final int customNavId = a.getResourceId(R.styleable.SherlockActionBar_ROMAcustomNavigationLayout, 0);
        if (customNavId != 0) {
            mCustomNavView = inflater.inflate(customNavId, this, false);
            mNavigationMode = ActionBar.NAVIGATION_MODE_STANDARD;
            setDisplayOptions(mDisplayOptions | ActionBar.DISPLAY_SHOW_CUSTOM);
        }

        mContentHeight = a.getLayoutDimension(R.styleable.SherlockActionBar_ROMAheight, 0);

        a.recycle();

        mLogoNavItem = new ActionMenuItem(context, 0, android.R.id.home, 0, 0, mTitle);
        mHomeLayout.setOnClickListener(mUpClickListener);
        mHomeLayout.setClickable(true);
        mHomeLayout.setFocusable(true);
        
    }

    /**
     * 右上角菜单处理类
     * @author 万籁唤
     *
     */
    private class RightMenuLayout {
        
        private Context mContext;
        private SVGView refreshButton;
        private ImageButton mProgressBar;
        private Animation mRotateAnimation;
        private RelativeLayout rl_refresh;
        private SVGView searchButton;//搜索按钮

        private TextView mRightTextView;
        private ImageView mRightImageView;

        private boolean showFlag = false;
        private boolean hideRefreshButtonFlag = true;
        private boolean hideSearchButtonFlag = true;
        private boolean hideRightTextFlag = true;
        
        public RightMenuLayout(Context context){
            
            mContext = context;
            init(getMenuView());
        }
        
        public void init(View view) {
            if(view == null)
                return;
            mRightTextView = (TextView) view.findViewById(R.id.tv_rightBtn);
            mRightTextView.setOnClickListener(new OnClickListener(){
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    if(mOnLoadMenuActionListener != null)
                        mOnLoadMenuActionListener.onLoadMenuAction(mRightTextView);
                }
            });
            mRightImageView = (ImageView) view.findViewById(R.id.img_rightBtn);
            mRightImageView.setOnClickListener(new OnClickListener(){
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    if(mOnLoadMenuActionListener != null)
                        mOnLoadMenuActionListener.onLoadMenuAction(mRightImageView);
                }
            });
            //搜索按钮
            searchButton = (SVGView)view.findViewById(R.id.sb_search);
            searchButton.setSVGRenderer(new SVGParserRenderer(mContext,
                    R.drawable.abs__nav_right_search), null);
            searchButton.setOnClickListener(new OnClickListener(){
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    if(mOnLoadMenuActionListener != null)
                        mOnLoadMenuActionListener.onLoadMenuAction(searchButton);
                }
            });
            
            //刷新按钮
            refreshButton = (SVGView)view.findViewById(R.id.sb_refresh);
            refreshButton.setSVGRenderer(new SVGParserRenderer(mContext,
            		R.drawable.abs__nav_right_refresh), null);
            refreshButton.setOnClickListener(new OnClickListener(){
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    if(mOnLoadMenuActionListener != null)
                        mOnLoadMenuActionListener.onLoadMenuAction(refreshButton);
                }
            });
            
            //旋转进度条
            mRotateAnimation = AnimationUtils.loadAnimation(mContext, 
                    R.anim.roma_refresh_rotate_loading_anim);
            mRotateAnimation.setInterpolator(new LinearInterpolator());
            //mRotateAnimation.start();//启动动画
            
            mProgressBar = (ImageButton) view.findViewById(R.id.sb_progressBar); 
//            mProgressBar.setBackgroundDrawable(
//                    SvgRes.getDrawable(mContext, R.drawable.roma_common_refresh_progress_btn));
            mProgressBar.setBackgroundResource(R.drawable.roma_common_refresh_progress_btn);
           
            rl_refresh = (RelativeLayout) view.findViewById(R.id.rl_refresh);
            
            if(showFlag)
                showNetReqProgress();
            else 
                hideNetReqProgress();
            
            if(hideRefreshButtonFlag)
                hideRefreshButton();
            
            if(hideSearchButtonFlag)
                hideSearchButton();

            if (hideRightTextFlag)
                hideRightText();
            else
                showRightText();
        }

        /**
         * 设置搜索按钮点击监听器
         * @param onClickListener
         */
        public void setOnClickSearchListener(OnClickListener onClickListener){
            searchButton.setOnClickListener(onClickListener);
        }
        /**
         * 设置刷新按钮点击监听器
         * @param onClickListener
         */
        public void setOnClickRefreshListener(OnClickListener onClickListener){
            refreshButton.setOnClickListener(onClickListener);
        }
        
        /**
         * 开始刷新
         */
        public void showNetReqProgress(){
            showFlag = true;
            if(mProgressBar != null && refreshButton != null){
                refreshButton.setVisibility(View.GONE);
                mProgressBar.setVisibility(View.VISIBLE);
                mProgressBar.setAnimation(mRotateAnimation);
                mRotateAnimation.start();//启动动画
            }
        }
        
        /**
         * 停止刷新
         */
        public void hideNetReqProgress(){
            showFlag = false;
            if(mProgressBar != null && refreshButton != null){
                mProgressBar.clearAnimation();
                mProgressBar.setVisibility(View.GONE);
                refreshButton.setVisibility(View.VISIBLE);
            }
        }
        
        /**
         * 隐藏刷新按钮
         */
        public void hideRefreshButton(){
            hideRefreshButtonFlag = true;
            if(rl_refresh != null)
                rl_refresh.setVisibility(View.INVISIBLE);
        }
        /**
         * 隐藏查找按钮
         */
        public void hideSearchButton(){
            hideSearchButtonFlag = true;
            if(searchButton!=null)
                searchButton.setVisibility(View.INVISIBLE);
        }
        /**
         * 显示刷新按钮
         */
        public void showRefreshButton(){
            hideRefreshButtonFlag = false;
            if(rl_refresh != null)
                rl_refresh.setVisibility(View.VISIBLE);
        }
        /**
         * 显示查找按钮
         */
        public void showSearchButton(){
            hideSearchButtonFlag = false;
            if(searchButton!=null)
                searchButton.setVisibility(View.VISIBLE);
        }

        /**
         * 显示右边文本
         */
        public void showRightText(){
            hideRightTextFlag = false;
            if(mRightTextView!=null)
                mRightTextView.setVisibility(View.VISIBLE);
        }

        /**
         * 隐藏右边文本
         */
        public void hideRightText(){
            hideRightTextFlag = true;
            if(mRightTextView!=null)
                mRightTextView.setVisibility(View.GONE);
        }

        public void showRightBitmp() {
            if (mRightImageView != null) {
                mRightImageView.setVisibility(View.VISIBLE);
            }
        }

        public void hideRightBitmp() {
            if (mRightImageView != null) {
                mRightImageView.setVisibility(View.GONE);
            }
        }

        public void setRightText(CharSequence text){
            if (text != null){
//                searchButton.setVisibility(View.GONE);
//                refreshButton.setVisibility(View.GONE);
//                rl_refresh.setVisibility(View.GONE);
                mRightTextView.setText(text);
            }
        }

        public void setRightTextColor(int color){
            mRightTextView.setTextColor(color);
        }

        public void setRightTextSize(int size){
            mRightTextView.setTextSize(size);
        }

        public void setRightBitmp(Bitmap mBitmap) {
            mRightImageView.setImageBitmap(mBitmap);
        }

    }
    
    /**
     * 设置搜索按钮点击监听器
     * @param onClickListener
     */
    public void setOnClickSearchListener(OnClickListener onClickListener){
        mRightMenuLayout.setOnClickSearchListener(onClickListener);
    }
    /**
     * 设置刷新按钮点击监听器
     * @param onClickListener
     */
    public void setOnClickRefreshListener(OnClickListener onClickListener){
        mRightMenuLayout.setOnClickRefreshListener(onClickListener);
    }
    
    public void showNetReqProgress() {
        // TODO Auto-generated method stub
        if(mRightMenuLayout != null)
            mRightMenuLayout.showNetReqProgress();
    }

    public void hideNetReqProgress() {
        if(mRightMenuLayout != null)
        mRightMenuLayout.hideNetReqProgress();
    }

    public void hideRefreshButton() {
        if(mRightMenuLayout != null)
        mRightMenuLayout.hideRefreshButton();
    }

    public void hideSearchButton() {
        if(mRightMenuLayout != null)
        mRightMenuLayout.hideSearchButton();
    }
    
    public void showRefreshButton() {
        if(mRightMenuLayout != null)
        mRightMenuLayout.showRefreshButton();
    }

    public void showSearchButton() {
        if(mRightMenuLayout != null)
        mRightMenuLayout.showSearchButton();
    }
    
    
    /*
     * Must be public so we can dispatch pre-2.2 via ActionBarImpl.
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        mTitleView = null;
        mSubtitleView = null;
        //mTitleUpView = null;
        if (mTitleLayout != null && mTitleLayout.getParent() == this) {
            removeView(mTitleLayout);
        }
        mTitleLayout = null;
        if ((mDisplayOptions & ActionBar.DISPLAY_SHOW_TITLE) != 0) {
            initTitle();
        }

        if (mTabScrollView != null && mIncludeTabs) {
            ViewGroup.LayoutParams lp = mTabScrollView.getLayoutParams();
            if (lp != null) {
                lp.width = LayoutParams.WRAP_CONTENT;
                lp.height = LayoutParams.MATCH_PARENT;
            }
            mTabScrollView.setAllowCollapse(true);
        }
    }

    /*++ add by wanlaihuan */
    public void resetTitleToDefault(){
    	resetTitle(R.layout.abs__action_bar_title_item);
    }
    
    public void resetTitle(int layoutResId){
    	
    	mTitleResId = layoutResId;
    	
    	mTitleView = null;
        mSubtitleView = null;
        mGpbztitleView = null;
        //mTitleUpView = null;
        if (mTitleLayout != null && mTitleLayout.getParent() == this) {
            removeView(mTitleLayout);
        }
        mTitleLayout = null;
        if ((mDisplayOptions & ActionBar.DISPLAY_SHOW_TITLE) != 0) {
            initTitle();
        }

        if (mTabScrollView != null && mIncludeTabs) {
            ViewGroup.LayoutParams lp = mTabScrollView.getLayoutParams();
            if (lp != null) {
                lp.width = LayoutParams.WRAP_CONTENT;
                lp.height = LayoutParams.MATCH_PARENT;
            }
            mTabScrollView.setAllowCollapse(true);
        }
    }
    
    public View findViewByIdFromTitle(int resId){
    	return mTitleLayout.findViewById(resId);
    }
	public void showTitle() {
		mTitleLayout.setVisibility(View.VISIBLE);
	}

	public void hideTitle() {
		mTitleLayout.setVisibility(View.GONE);
	}
	
	public void setTitleCompoundDrawables(Drawable left, Drawable top, Drawable right, Drawable bottom){
		mTitleView.setCompoundDrawables(left, top, right, bottom);
	}
	
	 /*-- add by wanlaihuan */
	
    /**
     * Set the window callback used to invoke menu items; used for dispatching home button presses.
     * @param cb Window callback to dispatch to
     */
    public void setWindowCallback(Window.Callback cb) {
        mWindowCallback = cb;
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        //UNUSED removeCallbacks(mTabSelector);
        if (mActionMenuPresenter != null) {
            mActionMenuPresenter.hideOverflowMenu();
            mActionMenuPresenter.hideSubMenus();
        }
    }

    @Override
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    public void initProgress() {
        mProgressView = new IcsProgressBar(mContext, null, 0, mProgressStyle);
        mProgressView.setId(R.id.abs__progress_horizontal);
        mProgressView.setMax(10000);
        addView(mProgressView);
    }

    public void initIndeterminateProgress() {
        mIndeterminateProgressView = new IcsProgressBar(mContext, null, 0, mIndeterminateProgressStyle);
        mIndeterminateProgressView.setId(R.id.abs__progress_circular);
        addView(mIndeterminateProgressView);
    }

    @Override
    public void setSplitActionBar(boolean splitActionBar) {
        if (mSplitActionBar != splitActionBar) {
            if (mMenuView != null) {
                final ViewGroup oldParent = (ViewGroup) mMenuView.getParent();
                if (oldParent != null) {
                    oldParent.removeView(mMenuView);
                }
                if (splitActionBar) {
                    if (mSplitView != null) {
                        mSplitView.addView(mMenuView);
                    }
                } else {
                    addView(mMenuView);
                }
            }
            if (mSplitView != null) {
                mSplitView.setVisibility(splitActionBar ? VISIBLE : GONE);
            }
            super.setSplitActionBar(splitActionBar);
        }
    }

    public boolean isSplitActionBar() {
        return mSplitActionBar;
    }

    public boolean hasEmbeddedTabs() {
        return mIncludeTabs;
    }

    public void setEmbeddedTabView(ScrollingTabContainerView tabs) {
        if (mTabScrollView != null) {
            removeView(mTabScrollView);
        }
        mTabScrollView = tabs;
        mIncludeTabs = tabs != null;
        if (mIncludeTabs && mNavigationMode == ActionBar.NAVIGATION_MODE_TABS) {
            addView(mTabScrollView);
            ViewGroup.LayoutParams lp = mTabScrollView.getLayoutParams();
            lp.width = LayoutParams.WRAP_CONTENT;
            lp.height = LayoutParams.MATCH_PARENT;
            tabs.setAllowCollapse(true);
        }
    }

    public void setCallback(OnNavigationListener callback) {
        mCallback = callback;
    }

    public void setMenu(Menu menu, MenuPresenter.Callback cb) {
        if (menu == mOptionsMenu) return;

        if (mOptionsMenu != null) {
            mOptionsMenu.removeMenuPresenter(mActionMenuPresenter);
            mOptionsMenu.removeMenuPresenter(mExpandedMenuPresenter);
        }

        MenuBuilder builder = (MenuBuilder) menu;
        mOptionsMenu = builder;
        if (mMenuView != null) {
            final ViewGroup oldParent = (ViewGroup) mMenuView.getParent();
            if (oldParent != null) {
                oldParent.removeView(mMenuView);
            }
        }
        if (mActionMenuPresenter == null) {
            mActionMenuPresenter = new ActionMenuPresenter(mContext);
            mActionMenuPresenter.setCallback(cb);
            mActionMenuPresenter.setId(R.id.abs__action_menu_presenter);
            mExpandedMenuPresenter = new ExpandedActionViewMenuPresenter();
        }

        ActionMenuView menuView;
        final LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.MATCH_PARENT);
        if (!mSplitActionBar) {
            mActionMenuPresenter.setExpandedActionViewsExclusive(
            		ResourcesCompat.getResources_getBoolean(getContext(),
                    R.bool.abs__action_bar_expanded_action_views_exclusive));
            configPresenters(builder);
            menuView = (ActionMenuView) mActionMenuPresenter.getMenuView(this);
            final ViewGroup oldParent = (ViewGroup) menuView.getParent();
            if (oldParent != null && oldParent != this) {
                oldParent.removeView(menuView);
            }
           // by wanlaihuan add 支持以布局方式显示操作按钮
            menuView.removeAllViews();
            if(mMenuLayoutView != null){
                menuView.addView(mMenuLayoutView);//wanlaihuan 2015-03-16
                //if(mOnLoadMenuActionListener != null)
                //    mOnLoadMenuActionListener.onLoadMenuAction(mMenuLayoutView);
            }
            
            addView(menuView, layoutParams);
        } else {
            mActionMenuPresenter.setExpandedActionViewsExclusive(false);
            // Allow full screen width in split mode.
            mActionMenuPresenter.setWidthLimit(
                    getContext().getResources().getDisplayMetrics().widthPixels, true);
            // No limit to the item count; use whatever will fit.
            mActionMenuPresenter.setItemLimit(Integer.MAX_VALUE);
            // Span the whole width
            layoutParams.width = LayoutParams.MATCH_PARENT;
            configPresenters(builder);
            menuView = (ActionMenuView) mActionMenuPresenter.getMenuView(this);
            if (mSplitView != null) {
                final ViewGroup oldParent = (ViewGroup) menuView.getParent();
                if (oldParent != null && oldParent != mSplitView) {
                    oldParent.removeView(menuView);
                }
                menuView.setVisibility(getAnimatedVisibility());
                mSplitView.addView(menuView, layoutParams);
            } else {
                // We'll add this later if we missed it this time.
                menuView.setLayoutParams(layoutParams);
            }
        }
        mMenuView = menuView;
    }

    private void configPresenters(MenuBuilder builder) {
        if (builder != null) {
            builder.addMenuPresenter(mActionMenuPresenter);
            builder.addMenuPresenter(mExpandedMenuPresenter);
        } else {
            mActionMenuPresenter.initForMenu(mContext, null);
            mExpandedMenuPresenter.initForMenu(mContext, null);
            mActionMenuPresenter.updateMenuView(true);
            mExpandedMenuPresenter.updateMenuView(true);
        }
    }

    public boolean hasExpandedActionView() {
        return mExpandedMenuPresenter != null &&
                mExpandedMenuPresenter.mCurrentExpandedItem != null;
    }

    public void collapseActionView() {
        final MenuItemImpl item = mExpandedMenuPresenter == null ? null :
                mExpandedMenuPresenter.mCurrentExpandedItem;
        if (item != null) {
            item.collapseActionView();
        }
    }

    public void setCustomNavigationView(View view) {
        final boolean showCustom = (mDisplayOptions & ActionBar.DISPLAY_SHOW_CUSTOM) != 0;
        if (mCustomNavView != null && showCustom) {
            removeView(mCustomNavView);
        }
        mCustomNavView = view;
        if (mCustomNavView != null && showCustom) {
            addView(mCustomNavView);
        }
    }

    public CharSequence getTitle() {
        return mTitle;
    }

    /**
     * Set the action bar title. This will always replace or override window titles.
     * @param title Title to set
     *
     * @see #setWindowTitle(CharSequence)
     */
    public void setTitle(CharSequence title) {
        mUserTitle = true;
        setTitleImpl(title);
    }

    /**
     * Set the window title. A window title will always be replaced or overridden by a user title.
     * @param title Title to set
     *
     * @see #setTitle(CharSequence)
     */
    public void setWindowTitle(CharSequence title) {
        if (!mUserTitle) {
            setTitleImpl(title);
        }
    }

    private void setTitleImpl(CharSequence title) {
        mTitle = title;
        if (mTitleView != null) {
            mTitleView.setText(title);
            final boolean visible = mExpandedActionView == null &&
                    (mDisplayOptions & ActionBar.DISPLAY_SHOW_TITLE) != 0 &&
                    (!TextUtils.isEmpty(mTitle) || !TextUtils.isEmpty(mSubtitle));
            //mTitleLayout.setVisibility(visible ? VISIBLE : GONE);
        }
        if (mLogoNavItem != null) {
            mLogoNavItem.setTitle(title);
        }
    }

    public void setTitleColor(int color) {
        if (mTitleView != null) 
            mTitleView.setTextColor(color);
    }
    
    public void setTitleTextSize(float size){
    	if (mTitleView != null) 
    		mTitleView.setTextSize(size);
    }
    
    public CharSequence getSubtitle() {
        return mSubtitle;
    }

    public void setSubtitle(CharSequence subtitle) {
        mSubtitle = subtitle;
        if (mSubtitleView != null) {
            mSubtitleView.setText(subtitle);
            mSubtitleView.setVisibility(subtitle != null ? VISIBLE : GONE);
            final boolean visible = mExpandedActionView == null &&
                    (mDisplayOptions & ActionBar.DISPLAY_SHOW_TITLE) != 0 &&
                    (!TextUtils.isEmpty(mTitle) || !TextUtils.isEmpty(mSubtitle));
            //mTitleLayout.setVisibility(visible ? VISIBLE : GONE);
        }
    }

    public void setSubtitleColor(int color) {
        if (mSubtitleView != null) 
            mSubtitleView.setTextColor(color);
    }
    
    public void setSubTitleTextSize(float size){
    	if (mSubtitleView != null)
    		mSubtitleView.setTextSize(size);
    }
    
    public CharSequence getRzrqtitle() {
    	return mRzrqtitle;
    }
    
    public void setSubTitleLeft(CharSequence SubTitleLeft){
    	mRzrqtitle = SubTitleLeft;
    	if (mGpbztitleView != null) {
            if(Res.getBoolean(R.bool.is_roma_stock_mark_use_img)) {
                if ("HK".equalsIgnoreCase(SubTitleLeft.toString())) {
                    PicassoHelper.load(mContext,mGpbztitleViewImg, R.drawable.roma_stock_mark_hk);
                } else if ("SGT".equalsIgnoreCase(SubTitleLeft.toString())) {
                    PicassoHelper.load(mContext, mGpbztitleViewImg, R.drawable.roma_stock_mark_sgt);
                } else if ("HGT".equalsIgnoreCase(SubTitleLeft.toString())) {
                    PicassoHelper.load(mContext, mGpbztitleViewImg, R.drawable.roma_stock_mark_hgt);
                } else if ("R".equalsIgnoreCase(SubTitleLeft.toString())) {
                    PicassoHelper.load(mContext, mGpbztitleViewImg, R.drawable.roma_stock_mark_r);
                }
                mGpbztitleViewImg.setVisibility(!TextUtils.isEmpty(SubTitleLeft) ? VISIBLE : GONE);
            }else {
                mGpbztitleView.setText(SubTitleLeft);
                mGpbztitleView.setVisibility(!TextUtils.isEmpty(SubTitleLeft) ? VISIBLE : GONE);
            }
		}
    }

    /** 设置个股详情界面 ActionBar股票类型标识背景色 */
    public void setSubTitleLeftBGDrawable(int color){
        int mCorner = AutoUtils.getPercentWidthSize(getResources().getInteger(R.integer.abs__action_bar_subTitle_stockType_mark_bg_corner));
    	ShapeDrawable mDrawable = new ShapeDrawable(new RoundRectShape(new float[] { mCorner, mCorner, mCorner,
                mCorner, mCorner, mCorner, mCorner, mCorner }, null, null));
    	mDrawable.getPaint().setColor(color);
		mGpbztitleView.setBackgroundDrawable(mDrawable);
    }
    
    public void setSubTitleLeftColor(int color){
    	if (mGpbztitleView != null) {
			mGpbztitleView.setTextColor(color);
		}
    }
    
    public void setHomeButtonEnabled(boolean enable) {
        mHomeLayout.setEnabled(enable);
        mHomeLayout.setFocusable(enable);
        // Make sure the home button has an accurate content description for accessibility.
        if (!enable) {
            mHomeLayout.setContentDescription(null);
        } else if ((mDisplayOptions & ActionBar.DISPLAY_HOME_AS_UP) != 0) {
            mHomeLayout.setContentDescription(mContext.getResources().getText(
                    R.string.abs__action_bar_up_description));
        } else {
            mHomeLayout.setContentDescription(mContext.getResources().getText(
                    R.string.abs__action_bar_home_description));
        }
    }

    public void setDisplayOptions(int options) {
        final int flagsChanged = mDisplayOptions == -1 ? -1 : options ^ mDisplayOptions;
        mDisplayOptions = options;
        
        if ((flagsChanged & DISPLAY_RELAYOUT_MASK) != 0) {
            final boolean showHome = (options & ActionBar.DISPLAY_SHOW_HOME) != 0;
            final int vis = showHome && mExpandedActionView == null ? VISIBLE : GONE;
            mHomeLayout.setVisibility(vis);

            if ((flagsChanged & ActionBar.DISPLAY_HOME_AS_UP) != 0) {
                final boolean setUp = (options & ActionBar.DISPLAY_HOME_AS_UP) != 0;
                mHomeLayout.setUp(setUp);

                // Showing home as up implicitly enables interaction with it.
                // In honeycomb it was always enabled, so make this transition
                // a bit easier for developers in the common case.
                // (It would be silly to show it as up without responding to it.)
                if (setUp) {
                    setHomeButtonEnabled(true);
                }
            }

            if ((flagsChanged & ActionBar.DISPLAY_USE_LOGO) != 0) {
                final boolean logoVis = mLogo != null && (options & ActionBar.DISPLAY_USE_LOGO) != 0;
                mHomeLayout.setIcon(logoVis ? mLogo : mIcon);
            }

            if ((flagsChanged & ActionBar.DISPLAY_SHOW_TITLE) != 0) {
                if ((options & ActionBar.DISPLAY_SHOW_TITLE) != 0) {
                    initTitle();
                } else {
                    removeView(mTitleLayout);
                }
            }

            if (mTitleLayout != null && (flagsChanged &
                    (ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_HOME)) != 0) {
                final boolean homeAsUp = (mDisplayOptions & ActionBar.DISPLAY_HOME_AS_UP) != 0;
                //mTitleUpView.setVisibility(!showHome ? (homeAsUp ? VISIBLE : INVISIBLE) : GONE);
                mTitleLayout.setEnabled(!showHome && homeAsUp);
            }

            if ((flagsChanged & ActionBar.DISPLAY_SHOW_CUSTOM) != 0 && mCustomNavView != null) {
                if ((options & ActionBar.DISPLAY_SHOW_CUSTOM) != 0) {
                    addView(mCustomNavView);
                } else {
                    removeView(mCustomNavView);
                }
            }

            requestLayout();
        } else {
            invalidate();
        }

        // Make sure the home button has an accurate content description for accessibility.
        if (!mHomeLayout.isEnabled()) {
            mHomeLayout.setContentDescription(null);
        } else if ((options & ActionBar.DISPLAY_HOME_AS_UP) != 0) {
            mHomeLayout.setContentDescription(mContext.getResources().getText(
                    R.string.abs__action_bar_up_description));
        } else {
            mHomeLayout.setContentDescription(mContext.getResources().getText(
                    R.string.abs__action_bar_home_description));
        }
    }

    public void setIcon(Drawable icon) {
        mIcon = icon;
        if (icon != null &&
                ((mDisplayOptions & ActionBar.DISPLAY_USE_LOGO) == 0 || mLogo == null)) {
            mHomeLayout.setIcon(icon);
        }
    }

    public void setIcon(int resId) {
        setIcon(mContext.getResources().getDrawable(resId));
    }
    
    /**
     * 设置SVG格式图片的接口
     * @param svgParserRenderer 
     * 可通过 new SVGParserRenderer(mContext, 
        		R.drawable.abs__navigation_back)得到该对象进行传递
     */
    public void setLeftSvgIcon(SVGParserRenderer svgParserRenderer) {
    	//mSVGParserRenderer = svgParserRenderer;
        if (svgParserRenderer != null &&
                ((mDisplayOptions & ActionBar.DISPLAY_USE_LOGO) == 0 || mLogo == null)) {
            mHomeLayout.setSVGIcon(svgParserRenderer);
        }
    }
    /**
     * 隐藏左边的返回按钮
     */
    public void hideIcon(){
    	//mHomeLayout.setVisibility(View.GONE);
    }
    /**
     * 显示左边的返回按钮
     */
    public void showIcon(){
    	mHomeLayout.setVisibility(View.VISIBLE);
    }
    /**
     * 隐藏右边的返回按钮
     */
    public void hideRightText(){
        mRightMenuLayout.hideRightText();
    }
    /**
     * 显示右边的返回按钮
     */
    public void showRightText(){
        mRightMenuLayout.showRightText();
    }

    public void showRightBitmp() {
        mRightMenuLayout.showRightBitmp();
    }

    public void hideRightBitmp() {
        mRightMenuLayout.hideRightBitmp();
    }

    public void setLogo(Drawable logo) {
        mLogo = logo;
        if (logo != null && (mDisplayOptions & ActionBar.DISPLAY_USE_LOGO) != 0) {
            mHomeLayout.setIcon(logo);
        }
    }

    public void setLogo(int resId) {
        setLogo(mContext.getResources().getDrawable(resId));
    }

    OnLoadMenuActionListener mOnLoadMenuActionListener = null;
    /**用于监听自定义菜单设置成功，可以在回调方法中来处理了*/
     public void setOnLoadMenuActionListener(OnLoadMenuActionListener onLoadMenuActionListener){
         mOnLoadMenuActionListener = onLoadMenuActionListener;
     }
     public interface OnLoadMenuActionListener{
         void onLoadMenuAction(View menuItemView);
     }
     
     OnCompleteMenuLayoutListener mOnCompleteMenuLayoutListener = null;
     /**设置完成右上角菜单布局*/
      public void setOnLoadMenuActionListener(OnCompleteMenuLayoutListener onCompleteMenuLayoutListener){
          mOnCompleteMenuLayoutListener = onCompleteMenuLayoutListener;
      }
      public interface OnCompleteMenuLayoutListener{
          void onCompleted(View menuView);
      }
      
    /**设置菜单的布局*/
    public void setMenuLayout(int layoutResId, OnCompleteMenuLayoutListener onCompleteMenuLayoutListener){
        mOnCompleteMenuLayoutListener = onCompleteMenuLayoutListener;
        if(layoutResId > 0){
            final LayoutInflater inflater = LayoutInflater.from(mContext);
            mMenuLayoutView = inflater.inflate(layoutResId, this, false);
        }else
            mMenuLayoutView = null;
        
        if(mOnCompleteMenuLayoutListener != null)
            mOnCompleteMenuLayoutListener.onCompleted(mMenuLayoutView);
        
    }
    /**
     * 设置默认的菜单布局
     * @param layoutResId
     */
    public void setMenuDefaultLayout(int layoutResId){
        
        mOnCompleteMenuLayoutListener = null;
        
        if(layoutResId > 0){
            final LayoutInflater inflater = LayoutInflater.from(mContext);
            mMenuLayoutView = inflater.inflate(layoutResId, this, false);
        }else
            mMenuLayoutView = null;
        
        mRightMenuLayout = new RightMenuLayout(mContext);
        
    }
    
    /**获取右上角菜单View*/
    public View getMenuView(){
        return mMenuLayoutView;
    }
    
    /**设置左边文本*/
    public void setLeftText(CharSequence text){
        mHomeLayout.setText(text);
    }
    
    /**设置左边文本颜色*/
    public void setLeftTextColor(int color){
        mHomeLayout.setTextColor(color);
    }
    
    /**设置左边文本大小*/
    public void setLeftTextSize(int size){
        mHomeLayout.setTextSize(size);
    }

    /**设置右边文本*/
    public void setRightText(CharSequence text){
        mRightMenuLayout.setRightText(text);
    }

    /**设置右边文本颜色*/
    public void setRightTextColor(int color){
        mRightMenuLayout.setRightTextColor(color);
    }

    /**设置右边文本大小*/
    public void setRightTextSize(int size){
        mRightMenuLayout.setRightTextSize(size);
    }

    public void setRightBitmp(Bitmap mBitmap) {
        mRightMenuLayout.setRightBitmp(mBitmap);
    }
    
    public void hideLeftIcon(){
        mHomeLayout.hideLeftIcon();
    }
    
    public void setNavigationMode(int mode) {
        final int oldMode = mNavigationMode;
        if (mode != oldMode) {
            switch (oldMode) {
            case ActionBar.NAVIGATION_MODE_LIST:
                if (mListNavLayout != null) {
                    removeView(mListNavLayout);
                }
                break;
            case ActionBar.NAVIGATION_MODE_TABS:
                if (mTabScrollView != null && mIncludeTabs) {
                    removeView(mTabScrollView);
                }
            }

            switch (mode) {
            case ActionBar.NAVIGATION_MODE_LIST:
                if (mSpinner == null) {
                    mSpinner = new IcsSpinner(mContext, null,
                            R.attr.ROMAactionDropDownStyle);
                    mListNavLayout = (IcsLinearLayout) LayoutInflater.from(mContext)
                            .inflate(R.layout.abs__action_bar_tab_bar_view, null);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
                    params.gravity = Gravity.CENTER;
                    mListNavLayout.addView(mSpinner, params);
                }
                if (mSpinner.getAdapter() != mSpinnerAdapter) {
                    mSpinner.setAdapter(mSpinnerAdapter);
                }
                mSpinner.setOnItemSelectedListener(mNavItemSelectedListener);
                addView(mListNavLayout);
                break;
            case ActionBar.NAVIGATION_MODE_TABS:
                if (mTabScrollView != null && mIncludeTabs) {
                    addView(mTabScrollView);
                }
                break;
            }
            mNavigationMode = mode;
            requestLayout();
        }
    }

    public void setDropdownAdapter(SpinnerAdapter adapter) {
        mSpinnerAdapter = adapter;
        if (mSpinner != null) {
            mSpinner.setAdapter(adapter);
        }
    }

    public SpinnerAdapter getDropdownAdapter() {
        return mSpinnerAdapter;
    }

    public void setDropdownSelectedPosition(int position) {
        mSpinner.setSelection(position);
    }

    public int getDropdownSelectedPosition() {
        return mSpinner.getSelectedItemPosition();
    }

    public View getCustomNavigationView() {
        return mCustomNavView;
    }

    public int getNavigationMode() {
        return mNavigationMode;
    }

    public int getDisplayOptions() {
        return mDisplayOptions;
    }

    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        // Used by custom nav views if they don't supply layout params. Everything else
        // added to an ActionBarView should have them already.
        return new ActionBar.LayoutParams(DEFAULT_CUSTOM_GRAVITY);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        addView(mHomeLayout);

        if (mCustomNavView != null && (mDisplayOptions & ActionBar.DISPLAY_SHOW_CUSTOM) != 0) {
            final ViewParent parent = mCustomNavView.getParent();
            if (parent != this) {
                if (parent instanceof ViewGroup) {
                    ((ViewGroup) parent).removeView(mCustomNavView);
                }
                addView(mCustomNavView);
            }
        }
    }

    private void initTitle() {
        if (mTitleLayout == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            
            mTitleLayout = inflater.inflate(mTitleResId/*R.layout.abs__action_bar_title_item*/,
                    this, false);
            try{
            	mTitleView = (TextView) mTitleLayout.findViewById(R.id.abs__action_bar_title);
            	mSubtitleView = (TextView) mTitleLayout.findViewById(R.id.abs__action_bar_subtitle);
            	mGpbztitleView = (TextView) mTitleLayout.findViewById(R.id.abs__action_bar_gpbz_title);
            	mGpbztitleViewImg = (ImageView) mTitleLayout.findViewById(R.id.abs__action_bar_gpbz_title_img);
            }catch(Exception e){
            	
            }
            //mTitleUpView = mTitleLayout.findViewById(R.id.abs__up);

            mTitleLayout.setOnClickListener(mUpClickListener);

            if (mTitleStyleRes != 0 && mTitleView != null) {
                mTitleView.setTextAppearance(mContext, mTitleStyleRes);
            }
            if (mTitle != null && mTitleView != null) {
                mTitleView.setText(mTitle);
            }

            if (mSubtitleStyleRes != 0 && mSubtitleView != null) {
                mSubtitleView.setTextAppearance(mContext, mSubtitleStyleRes);
            }
            if (mSubtitle != null && mSubtitleView != null) {
                mSubtitleView.setText(mSubtitle);
                mSubtitleView.setVisibility(VISIBLE);
            }
            if (mRzrqtitle != null && mGpbztitleView != null) {
                if(Res.getBoolean(R.bool.is_roma_stock_mark_use_img)) {
                    if ("HK".equalsIgnoreCase(mRzrqtitle.toString())) {
                        PicassoHelper.load(mContext, mGpbztitleViewImg, R.drawable.roma_stock_mark_hk);
                    } else if ("SGT".equalsIgnoreCase(mRzrqtitle.toString())) {
                        PicassoHelper.load(mContext, mGpbztitleViewImg, R.drawable.roma_stock_mark_sgt);
                    } else if ("HGT".equalsIgnoreCase(mRzrqtitle.toString())) {
                        PicassoHelper.load(mContext, mGpbztitleViewImg, R.drawable.roma_stock_mark_hgt);
                    } else if ("R".equalsIgnoreCase(mRzrqtitle.toString())) {
                        PicassoHelper.load(mContext, mGpbztitleViewImg, R.drawable.roma_stock_mark_r);
                    }
                    mGpbztitleViewImg.setVisibility(VISIBLE);
                }else {
                    mGpbztitleView.setText(mRzrqtitle);
                    mGpbztitleView.setVisibility(VISIBLE);
                }
			}

            final boolean homeAsUp = (mDisplayOptions & ActionBar.DISPLAY_HOME_AS_UP) != 0;
            final boolean showHome = (mDisplayOptions & ActionBar.DISPLAY_SHOW_HOME) != 0;
            //mTitleUpView.setVisibility(!showHome ? (homeAsUp ? VISIBLE : INVISIBLE) : GONE);
            mTitleLayout.setEnabled(homeAsUp && !showHome);
        }

        addView(mTitleLayout);  
        if (mExpandedActionView != null ||
                (TextUtils.isEmpty(mTitle) && TextUtils.isEmpty(mSubtitle))) {
            // Don't show while in expanded mode or with empty text
            //mTitleLayout.setVisibility(GONE);
        }
    }

    public void setContextView(ActionBarContextView view) {
        mContextView = view;
    }

    public void setCollapsable(boolean collapsable) {
        mIsCollapsable = collapsable;
    }

    public boolean isCollapsed() {
        return mIsCollapsed;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int childCount = getChildCount();
        if (mIsCollapsable) {
            int visibleChildren = 0;
            for (int i = 0; i < childCount; i++) {
                final View child = getChildAt(i);
                if (child.getVisibility() != GONE &&
                        !(child == mMenuView && mMenuView.getChildCount() == 0)) {
                    visibleChildren++;
                }
            }

            if (visibleChildren == 0) {
                // No size for an empty action bar when collapsable.
                setMeasuredDimension(0, 0);
                mIsCollapsed = true;
                return;
            }
        }
        mIsCollapsed = false;

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        if (widthMode != MeasureSpec.EXACTLY) {
            throw new IllegalStateException(getClass().getSimpleName() + " can only be used " +
                    "with android:layout_width=\"match_parent\" (or fill_parent)");
        }

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (heightMode != MeasureSpec.AT_MOST) {
            throw new IllegalStateException(getClass().getSimpleName() + " can only be used " +
                    "with android:layout_height=\"wrap_content\"");
        }

        int contentWidth = MeasureSpec.getSize(widthMeasureSpec);

        int maxHeight = mContentHeight > 0 ?
                mContentHeight : MeasureSpec.getSize(heightMeasureSpec);

        final int verticalPadding = getPaddingTop() + getPaddingBottom();
        final int paddingLeft = getPaddingLeft();
        final int paddingRight = getPaddingRight();
        final int height = maxHeight - verticalPadding;
        final int childSpecHeight = MeasureSpec.makeMeasureSpec(height, MeasureSpec.AT_MOST);

        int availableWidth = contentWidth - paddingLeft - paddingRight;
        int leftOfCenter = availableWidth / 2;
        int rightOfCenter = leftOfCenter;

        HomeView homeLayout = mExpandedActionView != null ? mExpandedHomeLayout : mHomeLayout;

        if (homeLayout.getVisibility() != GONE) {
            final ViewGroup.LayoutParams lp = homeLayout.getLayoutParams();
            int homeWidthSpec;
            if (lp.width < 0) {
                homeWidthSpec = MeasureSpec.makeMeasureSpec(availableWidth, MeasureSpec.AT_MOST);
            } else {
                homeWidthSpec = MeasureSpec.makeMeasureSpec(lp.width, MeasureSpec.EXACTLY);
            }
            homeLayout.measure(homeWidthSpec,
                    MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
            final int homeWidth = homeLayout.getMeasuredWidth() + homeLayout.getLeftOffset();
            availableWidth = Math.max(0, availableWidth - homeWidth);
            leftOfCenter = Math.max(0, availableWidth - homeWidth);
        }

        if (mMenuView != null && mMenuView.getParent() == this) {
            availableWidth = measureChildView(mMenuView, availableWidth,
                    childSpecHeight, 0);
            rightOfCenter = Math.max(0, rightOfCenter - mMenuView.getMeasuredWidth());
        }

        if (mIndeterminateProgressView != null &&
                mIndeterminateProgressView.getVisibility() != GONE) {
            availableWidth = measureChildView(mIndeterminateProgressView, availableWidth,
                    childSpecHeight, 0);
            rightOfCenter = Math.max(0,
                    rightOfCenter - mIndeterminateProgressView.getMeasuredWidth());
        }

        final boolean showTitle = mTitleLayout != null && mTitleLayout.getVisibility() != GONE &&
                (mDisplayOptions & ActionBar.DISPLAY_SHOW_TITLE) != 0;

        if (mExpandedActionView == null) {
            switch (mNavigationMode) {
                case ActionBar.NAVIGATION_MODE_LIST:
                    if (mListNavLayout != null) {
                        final int itemPaddingSize = showTitle ? mItemPadding * 2 : mItemPadding;
                        availableWidth = Math.max(0, availableWidth - itemPaddingSize);
                        leftOfCenter = Math.max(0, leftOfCenter - itemPaddingSize);
                        mListNavLayout.measure(
                                MeasureSpec.makeMeasureSpec(availableWidth, MeasureSpec.AT_MOST),
                                MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
                        final int listNavWidth = mListNavLayout.getMeasuredWidth();
                        availableWidth = Math.max(0, availableWidth - listNavWidth);
                        leftOfCenter = Math.max(0, leftOfCenter - listNavWidth);
                    }
                    break;
                case ActionBar.NAVIGATION_MODE_TABS:
                    if (mTabScrollView != null) {
                        final int itemPaddingSize = showTitle ? mItemPadding * 2 : mItemPadding;
                        availableWidth = Math.max(0, availableWidth - itemPaddingSize);
                        leftOfCenter = Math.max(0, leftOfCenter - itemPaddingSize);
                        mTabScrollView.measure(
                                MeasureSpec.makeMeasureSpec(availableWidth, MeasureSpec.AT_MOST),
                                MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
                        final int tabWidth = mTabScrollView.getMeasuredWidth();
                        availableWidth = Math.max(0, availableWidth - tabWidth);
                        leftOfCenter = Math.max(0, leftOfCenter - tabWidth);
                    }
                    break;
            }
        }

        View customView = null;
        if (mExpandedActionView != null) {
            customView = mExpandedActionView;
        } else if ((mDisplayOptions & ActionBar.DISPLAY_SHOW_CUSTOM) != 0 &&
                mCustomNavView != null) {
            customView = mCustomNavView;
        }

        if (customView != null) {
            final ViewGroup.LayoutParams lp = generateLayoutParams(customView.getLayoutParams());
            final ActionBar.LayoutParams ablp = lp instanceof ActionBar.LayoutParams ?
                    (ActionBar.LayoutParams) lp : null;

            int horizontalMargin = 0;
            int verticalMargin = 0;
            if (ablp != null) {
                horizontalMargin = ablp.leftMargin + ablp.rightMargin;
                verticalMargin = ablp.topMargin + ablp.bottomMargin;
            }

            // If the action bar is wrapping to its content height, don't allow a custom
            // view to MATCH_PARENT.
            int customNavHeightMode;
            if (mContentHeight <= 0) {
                customNavHeightMode = MeasureSpec.AT_MOST;
            } else {
                customNavHeightMode = lp.height != LayoutParams.WRAP_CONTENT ?
                        MeasureSpec.EXACTLY : MeasureSpec.AT_MOST;
            }
            final int customNavHeight = Math.max(0,
                    (lp.height >= 0 ? Math.min(lp.height, height) : height) - verticalMargin);

            final int customNavWidthMode = lp.width != LayoutParams.WRAP_CONTENT ?
                    MeasureSpec.EXACTLY : MeasureSpec.AT_MOST;
            int customNavWidth = Math.max(0,
                    (lp.width >= 0 ? Math.min(lp.width, availableWidth) : availableWidth)
                    - horizontalMargin);
            final int hgrav = (ablp != null ? ablp.gravity : DEFAULT_CUSTOM_GRAVITY) &
                    Gravity.HORIZONTAL_GRAVITY_MASK;

            // Centering a custom view is treated specially; we try to center within the whole
            // action bar rather than in the available space.
            if (hgrav == Gravity.CENTER_HORIZONTAL && lp.width == LayoutParams.MATCH_PARENT) {
                customNavWidth = Math.min(leftOfCenter, rightOfCenter) * 2;
            }

            customView.measure(
                    MeasureSpec.makeMeasureSpec(customNavWidth, customNavWidthMode),
                    MeasureSpec.makeMeasureSpec(customNavHeight, customNavHeightMode));
            availableWidth -= horizontalMargin + customView.getMeasuredWidth();
        }

        if (mExpandedActionView == null && showTitle) {
            availableWidth = measureChildView(mTitleLayout, availableWidth,
                    MeasureSpec.makeMeasureSpec(mContentHeight, MeasureSpec.EXACTLY), 0);
            leftOfCenter = Math.max(0, leftOfCenter - mTitleLayout.getMeasuredWidth());
        }

        if (mContentHeight <= 0) {
            int measuredHeight = 0;
            for (int i = 0; i < childCount; i++) {
                View v = getChildAt(i);
                int paddedViewHeight = v.getMeasuredHeight() + verticalPadding;
                if (paddedViewHeight > measuredHeight) {
                    measuredHeight = paddedViewHeight;
                }
            }
            setMeasuredDimension(contentWidth, measuredHeight);
        } else {
            setMeasuredDimension(contentWidth, maxHeight);
        }

        if (mContextView != null) {
            mContextView.setContentHeight(getMeasuredHeight());
        }

        if (mProgressView != null && mProgressView.getVisibility() != GONE) {
            mProgressView.measure(MeasureSpec.makeMeasureSpec(
                    contentWidth - mProgressBarPadding * 2, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(getMeasuredHeight(), MeasureSpec.AT_MOST));
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int x = getPaddingLeft();
        final int y = getPaddingTop();
        final int contentHeight = b - t - getPaddingTop() - getPaddingBottom();

        if (contentHeight <= 0) {
            // Nothing to do if we can't see anything.
            return;
        }

        HomeView homeLayout = mExpandedActionView != null ? mExpandedHomeLayout : mHomeLayout;
        if (homeLayout.getVisibility() != GONE) {
            final int leftOffset = homeLayout.getLeftOffset();
            x += positionChild(homeLayout, x + leftOffset, y, contentHeight) + leftOffset;
        }

        if (mExpandedActionView == null) {
            final boolean showTitle = mTitleLayout != null && mTitleLayout.getVisibility() != GONE &&
                    (mDisplayOptions & ActionBar.DISPLAY_SHOW_TITLE) != 0;
            if (showTitle) {
            	//modify by wanlaihuan for title center
            	int titleX = (r - l - mTitleLayout.getMeasuredWidth()) / 2;
                x += positionChild(mTitleLayout, titleX, y, contentHeight) + (titleX - x);
            }  
            
            switch (mNavigationMode) {
                case ActionBar.NAVIGATION_MODE_STANDARD:
                    break;
                case ActionBar.NAVIGATION_MODE_LIST:
                    if (mListNavLayout != null) {
                        if (showTitle) x += mItemPadding;
                        x += positionChild(mListNavLayout, x, y, contentHeight) + mItemPadding;
                    }
                    break;
                case ActionBar.NAVIGATION_MODE_TABS:
                    if (mTabScrollView != null) {
                        if (showTitle) x += mItemPadding;
                        x += positionChild(mTabScrollView, x, y, contentHeight) + mItemPadding;
                    }
                    break;
            }
        }

        int menuLeft = r - l - getPaddingRight();
        if (mMenuView != null && mMenuView.getParent() == this) {
            positionChildInverse(mMenuView, menuLeft, y, contentHeight);
            menuLeft -= mMenuView.getMeasuredWidth();
        }

        if (mIndeterminateProgressView != null &&
                mIndeterminateProgressView.getVisibility() != GONE) {
            positionChildInverse(mIndeterminateProgressView, menuLeft, y, contentHeight);
            menuLeft -= mIndeterminateProgressView.getMeasuredWidth();
        }

        View customView = null;
        if (mExpandedActionView != null) {
            customView = mExpandedActionView;
        } else if ((mDisplayOptions & ActionBar.DISPLAY_SHOW_CUSTOM) != 0 &&
                mCustomNavView != null) {
            customView = mCustomNavView;
        }
        if (customView != null) {
            ViewGroup.LayoutParams lp = customView.getLayoutParams();
            final ActionBar.LayoutParams ablp = lp instanceof ActionBar.LayoutParams ?
                    (ActionBar.LayoutParams) lp : null;

            final int gravity = ablp != null ? ablp.gravity : DEFAULT_CUSTOM_GRAVITY;
            final int navWidth = customView.getMeasuredWidth();

            int topMargin = 0;
            int bottomMargin = 0;
            if (ablp != null) {
                x += ablp.leftMargin;
                menuLeft -= ablp.rightMargin;
                topMargin = ablp.topMargin;
                bottomMargin = ablp.bottomMargin;
            }

            int hgravity = gravity & Gravity.HORIZONTAL_GRAVITY_MASK;
            // See if we actually have room to truly center; if not push against left or right.
            if (hgravity == Gravity.CENTER_HORIZONTAL) {
                final int centeredLeft = ((getRight() - getLeft()) - navWidth) / 2;
                if (centeredLeft < x) {
                    hgravity = Gravity.LEFT;
                } else if (centeredLeft + navWidth > menuLeft) {
                    hgravity = Gravity.RIGHT;
                }
            } else if (gravity == -1) {
                hgravity = Gravity.LEFT;
            }

            int xpos = 0;
            switch (hgravity) {
                case Gravity.CENTER_HORIZONTAL:
                    xpos = ((getRight() - getLeft()) - navWidth) / 2;
                    break;
                case Gravity.LEFT:
                    xpos = x;
                    break;
                case Gravity.RIGHT:
                    xpos = menuLeft - navWidth;
                    break;
            }

            int vgravity = gravity & Gravity.VERTICAL_GRAVITY_MASK;

            if (gravity == -1) {
                vgravity = Gravity.CENTER_VERTICAL;
            }

            int ypos = 0;
            switch (vgravity) {
                case Gravity.CENTER_VERTICAL:
                    final int paddedTop = getPaddingTop();
                    final int paddedBottom = getBottom() - getTop() - getPaddingBottom();
                    ypos = ((paddedBottom - paddedTop) - customView.getMeasuredHeight()) / 2;
                    break;
                case Gravity.TOP:
                    ypos = getPaddingTop() + topMargin;
                    break;
                case Gravity.BOTTOM:
                    ypos = getHeight() - getPaddingBottom() - customView.getMeasuredHeight()
                            - bottomMargin;
                    break;
            }
            final int customWidth = customView.getMeasuredWidth();
            customView.layout(xpos, ypos, xpos + customWidth,
                    ypos + customView.getMeasuredHeight());
            x += customWidth;
        }

        if (mProgressView != null) {
            mProgressView.bringToFront();
            final int halfProgressHeight = mProgressView.getMeasuredHeight() / 2;
            mProgressView.layout(mProgressBarPadding, -halfProgressHeight,
                    mProgressBarPadding + mProgressView.getMeasuredWidth(), halfProgressHeight);
        }
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new ActionBar.LayoutParams(getContext(), attrs);
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams lp) {
        if (lp == null) {
            lp = generateDefaultLayoutParams();
        }
        return lp;
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState state = new SavedState(superState);

        if (mExpandedMenuPresenter != null && mExpandedMenuPresenter.mCurrentExpandedItem != null) {
            state.expandedMenuItemId = mExpandedMenuPresenter.mCurrentExpandedItem.getItemId();
        }

        state.isOverflowOpen = isOverflowMenuShowing();

        return state;
    }

    @Override
    public void onRestoreInstanceState(Parcelable p) {
        SavedState state = (SavedState) p;

        super.onRestoreInstanceState(state.getSuperState());

        if (state.expandedMenuItemId != 0 &&
                mExpandedMenuPresenter != null && mOptionsMenu != null) {
            final MenuItem item = mOptionsMenu.findItem(state.expandedMenuItemId);
            if (item != null) {
                item.expandActionView();
            }
        }

        if (state.isOverflowOpen) {
            postShowOverflowMenu();
        }
    }

    static class SavedState extends BaseSavedState {
        int expandedMenuItemId;
        boolean isOverflowOpen;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            expandedMenuItemId = in.readInt();
            isOverflowOpen = in.readInt() != 0;
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(expandedMenuItemId);
            out.writeInt(isOverflowOpen ? 1 : 0);
        }

        public static final Parcelable.Creator<SavedState> CREATOR =
                new Parcelable.Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }

    public static class HomeView extends FrameLayout {
        private View mUpView;
        private TextView mIconView;
        private SVGView mSVGIconView;
        private int mUpWidth;

        private Context mContext;
        
        public HomeView(Context context) {
            this(context, null);
        }

        public HomeView(Context context, AttributeSet attrs) {
            super(context, attrs);
            
            mContext = context;
        }

        public void setUp(boolean isUp) {
            mUpView.setVisibility(isUp ? VISIBLE : GONE);
        }

        /**
         * 设置png格式的接口
         * @param icon
         */
        public void setIcon(Drawable icon) {
        	mIconView.setVisibility(View.VISIBLE);
            mSVGIconView.setVisibility(View.GONE);
            mIconView.setText("");
            mIconView.setBackgroundDrawable(icon);
        }
        /**
         * 设置SVG格式图片的接口
         * @param svgParserRenderer 
         * 可通过 new SVGParserRenderer(mContext, 
            		R.drawable.abs__navigation_back)得到该对象进行传递
         */
        public void setSVGIcon(SVGParserRenderer svgParserRenderer) {
        	mIconView.setVisibility(View.GONE);
            mSVGIconView.setVisibility(View.VISIBLE);
            mIconView.setText("");
            mSVGIconView.setSVGRenderer(svgParserRenderer, null);
            mIconView.setBackgroundDrawable(null);
        }

        public void setText(CharSequence text){
            if(text != null){
            	 mIconView.setVisibility(View.VISIBLE);
            	 mSVGIconView.setVisibility(View.GONE);
                mIconView.setText(text);
            }
        }
        
        public void setTextColor(int color){
            mIconView.setTextColor(color);
        }
        
        public void setTextSize(int size){
            mIconView.setTextSize(size);
        }
        
        public void hideLeftIcon(){
            mIconView.setBackgroundDrawable(null);
        }
        @Override
        public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent event) {
            onPopulateAccessibilityEvent(event);
            return true;
        }

        @Override
        public void onPopulateAccessibilityEvent(AccessibilityEvent event) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                super.onPopulateAccessibilityEvent(event);
            }
            final CharSequence cdesc = getContentDescription();
            if (!TextUtils.isEmpty(cdesc)) {
                event.getText().add(cdesc);
            }
        }

        @Override
        public boolean dispatchHoverEvent(MotionEvent event) {
            // Don't allow children to hover; we want this to be treated as a single component.
            return onHoverEvent(event);
        }

        @Override
        protected void onFinishInflate() {
            mUpView = findViewById(R.id.abs__up);
            mIconView = (TextView) findViewById(R.id.abs__home);
            mIconView.setBackgroundDrawable(null);
            
            mSVGIconView = (SVGView)  findViewById(R.id.abs__home_svgicon);
        }

        public int getLeftOffset() {
            return mUpView.getVisibility() == GONE ? mUpWidth : 0;
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        	int width = 0;
        	int height = 0;
        	if(mUpView.getVisibility() != View.GONE){
	            measureChildWithMargins(mUpView, widthMeasureSpec, 0, heightMeasureSpec, 0);
	            final LayoutParams upLp = (LayoutParams) mUpView.getLayoutParams();
	            mUpWidth = upLp.leftMargin + mUpView.getMeasuredWidth() + upLp.rightMargin;
	            width = mUpView.getVisibility() == GONE ? 0 : mUpWidth;
	            height = upLp.topMargin + mUpView.getMeasuredHeight() + upLp.bottomMargin;
        	}
        	View measureView = null;
        	if(mIconView.getVisibility() != View.GONE)
        			measureView = mIconView;
        	else
        		measureView = mSVGIconView;
        	
            measureChildWithMargins(measureView, widthMeasureSpec, width, heightMeasureSpec, 0);
            final LayoutParams iconLp = (LayoutParams) measureView.getLayoutParams();
            width += iconLp.leftMargin + measureView.getMeasuredWidth() + iconLp.rightMargin;
            height = Math.max(height,
                    iconLp.topMargin + measureView.getMeasuredHeight() + iconLp.bottomMargin);

            final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
            final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
            final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
            final int heightSize = MeasureSpec.getSize(heightMeasureSpec);

            switch (widthMode) {
                case MeasureSpec.AT_MOST:
                    width = Math.min(width, widthSize);
                    break;
                case MeasureSpec.EXACTLY:
                    width = widthSize;
                    break;
                case MeasureSpec.UNSPECIFIED:
                default:
                    break;
            }
            switch (heightMode) {
                case MeasureSpec.AT_MOST:
                    height = Math.min(height, heightSize);
                    break;
                case MeasureSpec.EXACTLY:
                    height = heightSize;
                    break;
                case MeasureSpec.UNSPECIFIED:
                default:
                    break;
            }
            setMeasuredDimension(width, height);
        }

        @Override
        protected void onLayout(boolean changed, int l, int t, int r, int b) {
            final int vCenter = (b - t) / 2;
            //UNUSED int width = r - l;
            int upOffset = 0;
            if (mUpView.getVisibility() != GONE) {
                final LayoutParams upLp = (LayoutParams) mUpView.getLayoutParams();
                final int upHeight = mUpView.getMeasuredHeight();
                final int upWidth = mUpView.getMeasuredWidth();
                final int upTop = vCenter - upHeight / 2;
                mUpView.layout(0, upTop, upWidth, upTop + upHeight);
                upOffset = upLp.leftMargin + upWidth + upLp.rightMargin;
                //UNUSED width -= upOffset;
                l += upOffset;
            }
            View measureView = null;
        	if(mIconView.getVisibility() != View.GONE)
        			measureView = mIconView;
        	else
        		measureView = mSVGIconView;
        	
            if (measureView.getVisibility() != GONE) {
	            final LayoutParams iconLp = (LayoutParams) measureView.getLayoutParams();
	            final int iconHeight = measureView.getMeasuredHeight();
	            final int iconWidth = measureView.getMeasuredWidth();
	            final int hCenter = (r - l) / 2;
	            final int iconLeft = upOffset + Math.max(iconLp.leftMargin, hCenter - iconWidth / 2);
	            final int iconTop = Math.max(iconLp.topMargin, vCenter - iconHeight / 2);
	            measureView.layout(iconLeft, iconTop, iconLeft + iconWidth, iconTop + iconHeight);
            }
        }
    }

    private class ExpandedActionViewMenuPresenter implements MenuPresenter {
        MenuBuilder mMenu;
        MenuItemImpl mCurrentExpandedItem;

        @Override
        public void initForMenu(Context context, MenuBuilder menu) {
            // Clear the expanded action view when menus change.
            if (mMenu != null && mCurrentExpandedItem != null) {
                mMenu.collapseItemActionView(mCurrentExpandedItem);
            }
            mMenu = menu;
        }

        @Override
        public MenuView getMenuView(ViewGroup root) {
            return null;
        }

        @Override
        public void updateMenuView(boolean cleared) {
            // Make sure the expanded item we have is still there.
            if (mCurrentExpandedItem != null) {
                boolean found = false;

                if (mMenu != null) {
                    final int count = mMenu.size();
                    for (int i = 0; i < count; i++) {
                        final MenuItem item = mMenu.getItem(i);
                        if (item == mCurrentExpandedItem) {
                            found = true;
                            break;
                        }
                    }
                }

                if (!found) {
                    // The item we had expanded disappeared. Collapse.
                    collapseItemActionView(mMenu, mCurrentExpandedItem);
                }
            }
        }

        @Override
        public void setCallback(Callback cb) {
        }

        @Override
        public boolean onSubMenuSelected(SubMenuBuilder subMenu) {
            return false;
        }

        @Override
        public void onCloseMenu(MenuBuilder menu, boolean allMenusAreClosing) {
        }

        @Override
        public boolean flagActionItems() {
            return false;
        }

        @Override
        public boolean expandItemActionView(MenuBuilder menu, MenuItemImpl item) {
            mExpandedActionView = item.getActionView();
            mExpandedHomeLayout.setIcon(mIcon.getConstantState().newDrawable(/* TODO getResources() */));
            mCurrentExpandedItem = item;
            if (mExpandedActionView.getParent() != ActionBarView.this) {
                addView(mExpandedActionView);
            }
            if (mExpandedHomeLayout.getParent() != ActionBarView.this) {
                addView(mExpandedHomeLayout);
            }
            mHomeLayout.setVisibility(GONE);
           // if (mTitleLayout != null) mTitleLayout.setVisibility(GONE);
            if (mTabScrollView != null) mTabScrollView.setVisibility(GONE);
            if (mSpinner != null) mSpinner.setVisibility(GONE);
            if (mCustomNavView != null) mCustomNavView.setVisibility(GONE);
            requestLayout();
            item.setActionViewExpanded(true);

            if (mExpandedActionView instanceof CollapsibleActionView) {
                ((CollapsibleActionView) mExpandedActionView).onActionViewExpanded();
            }

            return true;
        }

        @Override
        public boolean collapseItemActionView(MenuBuilder menu, MenuItemImpl item) {
            // Do this before detaching the actionview from the hierarchy, in case
            // it needs to dismiss the soft keyboard, etc.
            if (mExpandedActionView instanceof CollapsibleActionView) {
                ((CollapsibleActionView) mExpandedActionView).onActionViewCollapsed();
            }

            removeView(mExpandedActionView);
            removeView(mExpandedHomeLayout);
            mExpandedActionView = null;
            if ((mDisplayOptions & ActionBar.DISPLAY_SHOW_HOME) != 0) {
                mHomeLayout.setVisibility(VISIBLE);
            }
            if ((mDisplayOptions & ActionBar.DISPLAY_SHOW_TITLE) != 0) {
                if (mTitleLayout == null) {
                    initTitle();
                } else {
                    mTitleLayout.setVisibility(VISIBLE);
                }
            }
            if (mTabScrollView != null && mNavigationMode == ActionBar.NAVIGATION_MODE_TABS) {
                mTabScrollView.setVisibility(VISIBLE);
            }
            if (mSpinner != null && mNavigationMode == ActionBar.NAVIGATION_MODE_LIST) {
                mSpinner.setVisibility(VISIBLE);
            }
            if (mCustomNavView != null && (mDisplayOptions & ActionBar.DISPLAY_SHOW_CUSTOM) != 0) {
                mCustomNavView.setVisibility(VISIBLE);
            }
            mExpandedHomeLayout.setIcon(null);
            mCurrentExpandedItem = null;
            requestLayout();
            item.setActionViewExpanded(false);

            return true;
        }

        @Override
        public int getId() {
            return 0;
        }

        @Override
        public Parcelable onSaveInstanceState() {
            return null;
        }

        @Override
        public void onRestoreInstanceState(Parcelable state) {
        }
    }
}
