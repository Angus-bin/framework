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

package com.actionbarsherlock;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.InputType;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SpinnerAdapter;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.internal.nineoldandroids.animation.Animator;
import com.actionbarsherlock.internal.nineoldandroids.animation.Animator.AnimatorListener;
import com.actionbarsherlock.internal.nineoldandroids.animation.AnimatorListenerAdapter;
import com.actionbarsherlock.internal.nineoldandroids.animation.AnimatorSet;
import com.actionbarsherlock.internal.nineoldandroids.animation.ObjectAnimator;
import com.actionbarsherlock.internal.nineoldandroids.widget.NineFrameLayout;
import com.actionbarsherlock.internal.view.menu.MenuBuilder;
import com.actionbarsherlock.internal.view.menu.MenuPopupHelper;
import com.actionbarsherlock.internal.view.menu.SubMenuBuilder;
import com.actionbarsherlock.internal.widget.ActionBarBottomView;
import com.actionbarsherlock.internal.widget.ActionBarContainer;
import com.actionbarsherlock.internal.widget.ActionBarContextView;
import com.actionbarsherlock.internal.widget.ActionBarView;
import com.actionbarsherlock.internal.widget.ActionBarView.OnCompleteMenuLayoutListener;
import com.actionbarsherlock.internal.widget.ActionBarView.OnLoadMenuActionListener;
import com.actionbarsherlock.internal.widget.ScrollingTabContainerView;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.android.basephone.widget.R;
import com.trevorpage.tpsvg.SVGParserRenderer;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import roma.romaway.abs.android.keyboard.RomaWayKeyBoardView;
import roma.romaway.abs.android.keyboard.RomaWayStockKeyboardUtil;

/**
 * ActionBarImpl is the ActionBar implementation used
 * by devices of all screen sizes. If it detects a compatible decor,
 * it will split contextual modes across both the ActionBarView at
 * the top of the screen and a horizontal LinearLayout at the bottom
 * which is normally hidden.
 */
public class ActionBarImpl extends ActionBar{ 
    //UNUSED private static final String TAG = "ActionBarImpl";

	private final int ANIMATION_DURATION = 300;
	
    private Context mContext;
    private Context mThemedContext;
    private Activity mActivity;
    //UNUSED private Dialog mDialog;

    private ActionBarContainer mContainerView;
    private ActionBarView mActionView;
    /**
     * 最底部状态条
     */
    private ActionBarBottomView mActionBarBottomView;
    
    /**自定义键盘*/
    private LinearLayout mPopView;
    private RomaWayKeyBoardView mRomaWayKeyboardView;
    
    private ActionBarContextView mContextView;
    private ActionBarContainer mSplitView;
    private NineFrameLayout mContentView;
    private ScrollingTabContainerView mTabScrollView;

    private ArrayList<TabImpl> mTabs = new ArrayList<TabImpl>();

    private TabImpl mSelectedTab;
    private int mSavedTabPosition = INVALID_POSITION;

    ActionModeImpl mActionMode;
    ActionMode mDeferredDestroyActionMode;
    ActionMode.Callback mDeferredModeDestroyCallback;

    private boolean mLastMenuVisibility;
    private ArrayList<OnMenuVisibilityListener> mMenuVisibilityListeners =
            new ArrayList<OnMenuVisibilityListener>();

    private static final int CONTEXT_DISPLAY_NORMAL = 0;
    private static final int CONTEXT_DISPLAY_SPLIT = 1;

    private static final int INVALID_POSITION = -1;

    private int mContextDisplayMode;
    private boolean mHasEmbeddedTabs;

    final Handler mHandler = new Handler();
    Runnable mTabSelector;

    private Animator mCurrentShowAnim;
    private Animator mCurrentModeAnim;
    private boolean mShowHideAnimationEnabled;
    boolean mWasHiddenBeforeMode;

    final AnimatorListener mHideListener = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
            if (mContentView != null) {
                mContentView.setTranslationY(0);
                mContainerView.setTranslationY(0);
            }
            if (mSplitView != null && mContextDisplayMode == CONTEXT_DISPLAY_SPLIT) {
                mSplitView.setVisibility(View.GONE);
            }
            mContainerView.setVisibility(View.GONE);
            mContainerView.setTransitioning(false);
            mCurrentShowAnim = null;
            completeDeferredDestroyActionMode();
        }
    };

    final AnimatorListener mShowListener = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
            mCurrentShowAnim = null;
            mContainerView.requestLayout();
        }
    };

    public ActionBarImpl(Activity activity, int features) {
        mActivity = activity;
        Window window = activity.getWindow();
        View decor = window.getDecorView();
        init(decor);

        //window.hasFeature() workaround for pre-3.0
        if ((features & (1 << Window.FEATURE_ACTION_BAR_OVERLAY)) == 0) {
            mContentView = (NineFrameLayout)decor.findViewById(android.R.id.content);
        }
		setHomeButtonEnabled(true);//default enabled
    }

    public ActionBarImpl(Dialog dialog) {
        //UNUSED mDialog = dialog;
        init(dialog.getWindow().getDecorView());
    }

    private LinearLayout abs__root;
    private LinearLayout abs__root_content;
    private int tran_distance;
    private void init(View decor) {
        mContext = decor.getContext();
        mActionView = (ActionBarView) decor.findViewById(R.id.abs__action_bar);
        mActionBarBottomView = (ActionBarBottomView)decor.findViewById(R.id.abs__bottom_action_bar);
        mRomaWayKeyboardView = (RomaWayKeyBoardView) decor.findViewById(R.id.abs__normal_romakeyboard_view);
        mPopView = (LinearLayout)decor.findViewById(R.id.abs__pop_view);
        
        abs__root = (LinearLayout)decor.findViewById(R.id.abs__root);
        abs__root_content = (LinearLayout)decor.findViewById(R.id.abs__root_content);
        
        mContextView = (ActionBarContextView) decor.findViewById(
                R.id.abs__action_context_bar);
        mContainerView = (ActionBarContainer) decor.findViewById(
                R.id.abs__action_bar_container);
        mSplitView = (ActionBarContainer) decor.findViewById(
                R.id.abs__split_action_bar);

        if (mActionView == null || mContextView == null || mContainerView == null) {
            throw new IllegalStateException(getClass().getSimpleName() + " can only be used " +
                    "with a compatible window decor layout");
        }

        mActionView.setContextView(mContextView);
        mContextDisplayMode = mActionView.isSplitActionBar() ?
                CONTEXT_DISPLAY_SPLIT : CONTEXT_DISPLAY_NORMAL;

        // Older apps get the home button interaction enabled by default.
        // Newer apps need to enable it explicitly.
        boolean homeButtonEnabled = mContext.getApplicationInfo().targetSdkVersion < Build.VERSION_CODES.ICE_CREAM_SANDWICH;

        // If the homeAsUp display option is set, always enable the home button.
        homeButtonEnabled |= (mActionView.getDisplayOptions() & ActionBar.DISPLAY_HOME_AS_UP) != 0;

        setHomeButtonEnabled(homeButtonEnabled);

        setHasEmbeddedTabs(ResourcesCompat.getResources_getBoolean(mContext,
                R.bool.abs__action_bar_embed_tabs));
    }

    public void onConfigurationChanged(Configuration newConfig) {
        setHasEmbeddedTabs(ResourcesCompat.getResources_getBoolean(mContext,
                R.bool.abs__action_bar_embed_tabs));

        //Manually dispatch a configuration change to the action bar view on pre-2.2
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.FROYO) {
            mActionView.onConfigurationChanged(newConfig);
            if (mContextView != null) {
                mContextView.onConfigurationChanged(newConfig);
            }
        }
    }

    private void setHasEmbeddedTabs(boolean hasEmbeddedTabs) {
        mHasEmbeddedTabs = hasEmbeddedTabs;
        // Switch tab layout configuration if needed
        if (!mHasEmbeddedTabs) {
            mActionView.setEmbeddedTabView(null);
            mContainerView.setTabContainer(mTabScrollView);
        } else {
            mContainerView.setTabContainer(null);
            mActionView.setEmbeddedTabView(mTabScrollView);
        }
        final boolean isInTabMode = getNavigationMode() == NAVIGATION_MODE_TABS;
        if (mTabScrollView != null) {
            mTabScrollView.setVisibility(isInTabMode ? View.VISIBLE : View.GONE);
        }
        mActionView.setCollapsable(!mHasEmbeddedTabs && isInTabMode);
    }

    private void ensureTabsExist() {
        if (mTabScrollView != null) {
            return;
        }

        ScrollingTabContainerView tabScroller = new ScrollingTabContainerView(mContext);

        if (mHasEmbeddedTabs) {
            tabScroller.setVisibility(View.VISIBLE);
            mActionView.setEmbeddedTabView(tabScroller);
        } else {
            tabScroller.setVisibility(getNavigationMode() == NAVIGATION_MODE_TABS ?
                    View.VISIBLE : View.GONE);
            mContainerView.setTabContainer(tabScroller);
        }
        mTabScrollView = tabScroller;
    }

    void completeDeferredDestroyActionMode() {
        if (mDeferredModeDestroyCallback != null) {
            mDeferredModeDestroyCallback.onDestroyActionMode(mDeferredDestroyActionMode);
            mDeferredDestroyActionMode = null;
            mDeferredModeDestroyCallback = null;
        }
    }

    /**
     * Enables or disables animation between show/hide states.
     * If animation is disabled using this method, animations in progress
     * will be finished.
     *
     * @param enabled true to animate, false to not animate.
     */
    public void setShowHideAnimationEnabled(boolean enabled) {
        mShowHideAnimationEnabled = enabled;
        if (!enabled && mCurrentShowAnim != null) {
            mCurrentShowAnim.end();
        }
    }

    public void addOnMenuVisibilityListener(OnMenuVisibilityListener listener) {
        mMenuVisibilityListeners.add(listener);
    }

    public void removeOnMenuVisibilityListener(OnMenuVisibilityListener listener) {
        mMenuVisibilityListeners.remove(listener);
    }

    public void dispatchMenuVisibilityChanged(boolean isVisible) {
        if (isVisible == mLastMenuVisibility) {
            return;
        }
        mLastMenuVisibility = isVisible;

        final int count = mMenuVisibilityListeners.size();
        for (int i = 0; i < count; i++) {
            mMenuVisibilityListeners.get(i).onMenuVisibilityChanged(isVisible);
        }
    }

    @Override
    public void setCustomView(int resId) {
        setCustomView(LayoutInflater.from(getThemedContext()).inflate(resId, mActionView, false));
    }

    @Override
    public void setDisplayUseLogoEnabled(boolean useLogo) {
        setDisplayOptions(useLogo ? DISPLAY_USE_LOGO : 0, DISPLAY_USE_LOGO);
    }

    @Override
    public void setDisplayShowHomeEnabled(boolean showHome) {
        setDisplayOptions(showHome ? DISPLAY_SHOW_HOME : 0, DISPLAY_SHOW_HOME);
    }

    @Override
    public void setDisplayHomeAsUpEnabled(boolean showHomeAsUp) {
        setDisplayOptions(showHomeAsUp ? DISPLAY_HOME_AS_UP : 0, DISPLAY_HOME_AS_UP);
    }

    @Override
    public void setDisplayShowTitleEnabled(boolean showTitle) {
        setDisplayOptions(showTitle ? DISPLAY_SHOW_TITLE : 0, DISPLAY_SHOW_TITLE);
    }

    @Override
    public void setDisplayShowCustomEnabled(boolean showCustom) {
        setDisplayOptions(showCustom ? DISPLAY_SHOW_CUSTOM : 0, DISPLAY_SHOW_CUSTOM);
    }

    @Override
    public void setHomeButtonEnabled(boolean enable) {
        mActionView.setHomeButtonEnabled(enable);
    }

    @Override
    public void setTitle(int resId) {
        setTitle(mContext.getString(resId));
    }

    @Override
    public void setSubtitle(int resId) {
        setSubtitle(mContext.getString(resId));
    }

    public void setSelectedNavigationItem(int position) {
        switch (mActionView.getNavigationMode()) {
        case NAVIGATION_MODE_TABS:
            selectTab(mTabs.get(position));
            break;
        case NAVIGATION_MODE_LIST:
            mActionView.setDropdownSelectedPosition(position);
            break;
        default:
            throw new IllegalStateException(
                    "setSelectedNavigationItem not valid for current navigation mode");
        }
    }

    public void removeAllTabs() {
        cleanupTabs();
    }

    private void cleanupTabs() {
        if (mSelectedTab != null) {
            selectTab(null);
        }
        mTabs.clear();
        if (mTabScrollView != null) {
            mTabScrollView.removeAllTabs();
        }
        mSavedTabPosition = INVALID_POSITION;
    }

    public void setTitle(CharSequence title) {
        mActionView.setTitle(title);
    }

    public void setSubtitle(CharSequence subtitle) {
        mActionView.setSubtitle(subtitle);
    }
    
	public void setSubTitleLeft(CharSequence SubTitleLeft) {
		// TODO Auto-generated method stub
		mActionView.setSubTitleLeft(SubTitleLeft);
	}
	
	public void setSubTitleLeftBGDrawable(int color) {
		// TODO Auto-generated method stub
		mActionView.setSubTitleLeftBGDrawable(color);
	}

    public void setDisplayOptions(int options) {
        mActionView.setDisplayOptions(options);
    }

    public void setDisplayOptions(int options, int mask) {
        final int current = mActionView.getDisplayOptions();
        mActionView.setDisplayOptions((options & mask) | (current & ~mask));
    }

    public void setBackgroundDrawable(Drawable d) {
        mContainerView.setPrimaryBackground(d);
    }

    public void setStackedBackgroundDrawable(Drawable d) {
        mContainerView.setStackedBackground(d);
    }

    public void setSplitBackgroundDrawable(Drawable d) {
        if (mSplitView != null) {
            mSplitView.setSplitBackground(d);
        }
    }

    public View getCustomView() {
        return mActionView.getCustomNavigationView();
    }

    public CharSequence getTitle() {
        return mActionView.getTitle();
    }

    public CharSequence getSubtitle() {
        return mActionView.getSubtitle();
    }
    public CharSequence getRzrqtitle() {
    	return mActionView.getRzrqtitle();
    }

    public int getNavigationMode() {
        return mActionView.getNavigationMode();
    }

    public int getDisplayOptions() {
        return mActionView.getDisplayOptions();
    }

    public ActionMode startActionMode(ActionMode.Callback callback) {
        boolean wasHidden = false;
        if (mActionMode != null) {
            wasHidden = mWasHiddenBeforeMode;
            mActionMode.finish();
        }

        mContextView.killMode();
        ActionModeImpl mode = new ActionModeImpl(callback);
        if (mode.dispatchOnCreate()) {
            mWasHiddenBeforeMode = !isShowing() || wasHidden;
            mode.invalidate();
            mContextView.initForMode(mode);
            animateToMode(true);
            if (mSplitView != null && mContextDisplayMode == CONTEXT_DISPLAY_SPLIT) {
                // TODO animate this
                mSplitView.setVisibility(View.VISIBLE);
            }
            mContextView.sendAccessibilityEvent(AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED);
            mActionMode = mode;
            return mode;
        }
        return null;
    }

    private void configureTab(Tab tab, int position) {
        final TabImpl tabi = (TabImpl) tab;
        final ActionBar.TabListener callback = tabi.getCallback();

        if (callback == null) {
            throw new IllegalStateException("Action Bar Tab must have a Callback");
        }

        tabi.setPosition(position);
        mTabs.add(position, tabi);

        final int count = mTabs.size();
        for (int i = position + 1; i < count; i++) {
            mTabs.get(i).setPosition(i);
        }
    }

    @Override
    public void addTab(Tab tab) {
        addTab(tab, mTabs.isEmpty());
    }

    @Override
    public void addTab(Tab tab, int position) {
        addTab(tab, position, mTabs.isEmpty());
    }

    @Override
    public void addTab(Tab tab, boolean setSelected) {
        ensureTabsExist();
        mTabScrollView.addTab(tab, setSelected);
        configureTab(tab, mTabs.size());
        if (setSelected) {
            selectTab(tab);
        }
    }

    @Override
    public void addTab(Tab tab, int position, boolean setSelected) {
        ensureTabsExist();
        mTabScrollView.addTab(tab, position, setSelected);
        configureTab(tab, position);
        if (setSelected) {
            selectTab(tab);
        }
    }

    @Override
    public Tab newTab() {
        return new TabImpl();
    }

    @Override
    public void removeTab(Tab tab) {
        removeTabAt(tab.getPosition());
    }

    @Override
    public void removeTabAt(int position) {
        if (mTabScrollView == null) {
            // No tabs around to remove
            return;
        }

        int selectedTabPosition = mSelectedTab != null
                ? mSelectedTab.getPosition() : mSavedTabPosition;
        mTabScrollView.removeTabAt(position);
        TabImpl removedTab = mTabs.remove(position);
        if (removedTab != null) {
            removedTab.setPosition(-1);
        }

        final int newTabCount = mTabs.size();
        for (int i = position; i < newTabCount; i++) {
            mTabs.get(i).setPosition(i);
        }

        if (selectedTabPosition == position) {
            selectTab(mTabs.isEmpty() ? null : mTabs.get(Math.max(0, position - 1)));
        }
    }

    @Override
    public void selectTab(Tab tab) {
        if (getNavigationMode() != NAVIGATION_MODE_TABS) {
            mSavedTabPosition = tab != null ? tab.getPosition() : INVALID_POSITION;
            return;
        }

        FragmentTransaction trans = null;
        if (mActivity instanceof FragmentActivity) {
            trans = ((FragmentActivity)mActivity).getSupportFragmentManager().beginTransaction()
                    .disallowAddToBackStack();
        }

        if (mSelectedTab == tab) {
            if (mSelectedTab != null) {
                mSelectedTab.getCallback().onTabReselected(mSelectedTab, trans);
                mTabScrollView.animateToTab(tab.getPosition());
            }
        } else {
            mTabScrollView.setTabSelected(tab != null ? tab.getPosition() : Tab.INVALID_POSITION);
            if (mSelectedTab != null) {
                mSelectedTab.getCallback().onTabUnselected(mSelectedTab, trans);
            }
            mSelectedTab = (TabImpl) tab;
            if (mSelectedTab != null) {
                mSelectedTab.getCallback().onTabSelected(mSelectedTab, trans);
            }
        }

        if (trans != null && !trans.isEmpty()) {
            trans.commit();
        }
    }

    @Override
    public Tab getSelectedTab() {
        return mSelectedTab;
    }

    @Override
    public int getHeight() {
        return mContainerView.getHeight();
    }

    @Override
    public void show() {
        show(true);
    }

    void show(boolean markHiddenBeforeMode) {
        if (mCurrentShowAnim != null) {
            mCurrentShowAnim.end();
        }
        if (mContainerView.getVisibility() == View.VISIBLE) {
            if (markHiddenBeforeMode) mWasHiddenBeforeMode = false;
            return;
        }
        mContainerView.setVisibility(View.VISIBLE);

        if (mShowHideAnimationEnabled) {
            mContainerView.setAlpha(0);
            AnimatorSet anim = new AnimatorSet();
            AnimatorSet.Builder b = anim.play(ObjectAnimator.ofFloat(mContainerView, "alpha", 1));
            if (mContentView != null) {
                b.with(ObjectAnimator.ofFloat(mContentView, "translationY",
                        -mContainerView.getHeight(), 0));
                mContainerView.setTranslationY(-mContainerView.getHeight());
                b.with(ObjectAnimator.ofFloat(mContainerView, "translationY", 0));
            }
            if (mSplitView != null && mContextDisplayMode == CONTEXT_DISPLAY_SPLIT) {
                mSplitView.setAlpha(0);
                mSplitView.setVisibility(View.VISIBLE);
                b.with(ObjectAnimator.ofFloat(mSplitView, "alpha", 1));
            }
            anim.addListener(mShowListener);
            mCurrentShowAnim = anim;
            anim.start();
        } else {
            mContainerView.setAlpha(1);
            mContainerView.setTranslationY(0);
            mShowListener.onAnimationEnd(null);
        }
    }

    @Override
    public void hide() {
        if (mCurrentShowAnim != null) {
            mCurrentShowAnim.end();
        }
        if (mContainerView.getVisibility() == View.GONE) {
            return;
        }

        if (mShowHideAnimationEnabled) {
            mContainerView.setAlpha(1);
            mContainerView.setTransitioning(true);
            AnimatorSet anim = new AnimatorSet();
            AnimatorSet.Builder b = anim.play(ObjectAnimator.ofFloat(mContainerView, "alpha", 0));
            if (mContentView != null) {
                b.with(ObjectAnimator.ofFloat(mContentView, "translationY",
                        0, -mContainerView.getHeight()));
                b.with(ObjectAnimator.ofFloat(mContainerView, "translationY",
                        -mContainerView.getHeight()));
            }
            if (mSplitView != null && mSplitView.getVisibility() == View.VISIBLE) {
                mSplitView.setAlpha(1);
                b.with(ObjectAnimator.ofFloat(mSplitView, "alpha", 0));
            }
            anim.addListener(mHideListener);
            mCurrentShowAnim = anim;
            anim.start();
        } else {
            mHideListener.onAnimationEnd(null);
        }
    }

    public boolean isShowing() {
        return mContainerView.getVisibility() == View.VISIBLE;
    }

    void animateToMode(boolean toActionMode) {
        if (toActionMode) {
            show(false);
        }
        if (mCurrentModeAnim != null) {
            mCurrentModeAnim.end();
        }

        mActionView.animateToVisibility(toActionMode ? View.GONE : View.VISIBLE);
        mContextView.animateToVisibility(toActionMode ? View.VISIBLE : View.GONE);
        if (mTabScrollView != null && !mActionView.hasEmbeddedTabs() && mActionView.isCollapsed()) {
            mTabScrollView.animateToVisibility(toActionMode ? View.GONE : View.VISIBLE);
        }
    }

    public Context getThemedContext() {
        if (mThemedContext == null) {
            TypedValue outValue = new TypedValue();
            Resources.Theme currentTheme = mContext.getTheme();
            currentTheme.resolveAttribute(R.attr.ROMAactionBarWidgetTheme,
                    outValue, true);
            final int targetThemeRes = outValue.resourceId;

            if (targetThemeRes != 0) { //XXX && mContext.getThemeResId() != targetThemeRes) {
                mThemedContext = new ContextThemeWrapper(mContext, targetThemeRes);
            } else {
                mThemedContext = mContext;
            }
        }
        return mThemedContext;
    }

    /**
     * @hide
     */
    public class ActionModeImpl extends ActionMode implements MenuBuilder.Callback {
        private ActionMode.Callback mCallback;
        private MenuBuilder mMenu;
        private WeakReference<View> mCustomView;

        public ActionModeImpl(ActionMode.Callback callback) {
            mCallback = callback;
            mMenu = new MenuBuilder(getThemedContext())
                    .setDefaultShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
            mMenu.setCallback(this);
        }

        @Override
        public MenuInflater getMenuInflater() {
            return new MenuInflater(getThemedContext());
        }

        @Override
        public Menu getMenu() {
            return mMenu;
        }

        @Override
        public void finish() {
            if (mActionMode != this) {
                // Not the active action mode - no-op
                return;
            }

            // If we were hidden before the mode was shown, defer the onDestroy
            // callback until the animation is finished and associated relayout
            // is about to happen. This lets apps better anticipate visibility
            // and layout behavior.
            if (mWasHiddenBeforeMode) {
                mDeferredDestroyActionMode = this;
                mDeferredModeDestroyCallback = mCallback;
            } else {
                mCallback.onDestroyActionMode(this);
            }
            mCallback = null;
            animateToMode(false);

            // Clear out the context mode views after the animation finishes
            mContextView.closeMode();
            mActionView.sendAccessibilityEvent(AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED);

            mActionMode = null;

            if (mWasHiddenBeforeMode) {
                hide();
            }
        }

        @Override
        public void invalidate() {
            mMenu.stopDispatchingItemsChanged();
            try {
                mCallback.onPrepareActionMode(this, mMenu);
            } finally {
                mMenu.startDispatchingItemsChanged();
            }
        }

        public boolean dispatchOnCreate() {
            mMenu.stopDispatchingItemsChanged();
            try {
                return mCallback.onCreateActionMode(this, mMenu);
            } finally {
                mMenu.startDispatchingItemsChanged();
            }
        }

        @Override
        public void setCustomView(View view) {
            mContextView.setCustomView(view);
            mCustomView = new WeakReference<View>(view);
        }

        @Override
        public void setSubtitle(CharSequence subtitle) {
            mContextView.setSubtitle(subtitle);
        }

        @Override
        public void setTitle(CharSequence title) {
            mContextView.setTitle(title);
        }

        @Override
        public void setTitle(int resId) {
            setTitle(mContext.getResources().getString(resId));
        }

        @Override
        public void setSubtitle(int resId) {
            setSubtitle(mContext.getResources().getString(resId));
        }

        @Override
        public CharSequence getTitle() {
            return mContextView.getTitle();
        }

        @Override
        public CharSequence getSubtitle() {
            return mContextView.getSubtitle();
        }

        @Override
        public View getCustomView() {
            return mCustomView != null ? mCustomView.get() : null;
        }

        public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
            if (mCallback != null) {
                return mCallback.onActionItemClicked(this, item);
            } else {
                return false;
            }
        }

        public void onCloseMenu(MenuBuilder menu, boolean allMenusAreClosing) {
        }

        public boolean onSubMenuSelected(SubMenuBuilder subMenu) {
            if (mCallback == null) {
                return false;
            }

            if (!subMenu.hasVisibleItems()) {
                return true;
            }

            new MenuPopupHelper(getThemedContext(), subMenu).show();
            return true;
        }

        public void onCloseSubMenu(SubMenuBuilder menu) {
        }

        public void onMenuModeChange(MenuBuilder menu) {
            if (mCallback == null) {
                return;
            }
            invalidate();
            mContextView.showOverflowMenu();
        }
    }

    /**
     * @hide
     */
    public class TabImpl extends ActionBar.Tab {
        private ActionBar.TabListener mCallback;
        private Object mTag;
        private Drawable mIcon;
        private CharSequence mText;
        private CharSequence mContentDesc;
        private int mPosition = -1;
        private View mCustomView;

        @Override
        public Object getTag() {
            return mTag;
        }

        @Override
        public Tab setTag(Object tag) {
            mTag = tag;
            return this;
        }

        public ActionBar.TabListener getCallback() {
            return mCallback;
        }

        @Override
        public Tab setTabListener(ActionBar.TabListener callback) {
            mCallback = callback;
            return this;
        }

        @Override
        public View getCustomView() {
            return mCustomView;
        }

        @Override
        public Tab setCustomView(View view) {
            mCustomView = view;
            if (mPosition >= 0) {
                mTabScrollView.updateTab(mPosition);
            }
            return this;
        }

        @Override
        public Tab setCustomView(int layoutResId) {
            return setCustomView(LayoutInflater.from(getThemedContext())
                    .inflate(layoutResId, null));
        }

        @Override
        public Drawable getIcon() {
            return mIcon;
        }

        @Override
        public int getPosition() {
            return mPosition;
        }

        public void setPosition(int position) {
            mPosition = position;
        }

        @Override
        public CharSequence getText() {
            return mText;
        }

        @Override
        public Tab setIcon(Drawable icon) {
            mIcon = icon;
            if (mPosition >= 0) {
                mTabScrollView.updateTab(mPosition);
            }
            return this;
        }

        @Override
        public Tab setIcon(int resId) {
            return setIcon(mContext.getResources().getDrawable(resId));
        }

        @Override
        public Tab setText(CharSequence text) {
            mText = text;
            if (mPosition >= 0) {
                mTabScrollView.updateTab(mPosition);
            }
            return this;
        }

        @Override
        public Tab setText(int resId) {
            return setText(mContext.getResources().getText(resId));
        }

        @Override
        public void select() {
            selectTab(this);
        }

        @Override
        public Tab setContentDescription(int resId) {
            return setContentDescription(mContext.getResources().getText(resId));
        }

        @Override
        public Tab setContentDescription(CharSequence contentDesc) {
            mContentDesc = contentDesc;
            if (mPosition >= 0) {
                mTabScrollView.updateTab(mPosition);
            }
            return this;
        }

        @Override
        public CharSequence getContentDescription() {
            return mContentDesc;
        }
    }

    @Override
    public void setCustomView(View view) {
        mActionView.setCustomNavigationView(view);
    }

    @Override
    public void setCustomView(View view, LayoutParams layoutParams) {
        view.setLayoutParams(layoutParams);
        mActionView.setCustomNavigationView(view);
    }

    @Override
    public void setListNavigationCallbacks(SpinnerAdapter adapter, OnNavigationListener callback) {
        mActionView.setDropdownAdapter(adapter);
        mActionView.setCallback(callback);
    }

    @Override
    public int getSelectedNavigationIndex() {
        switch (mActionView.getNavigationMode()) {
            case NAVIGATION_MODE_TABS:
                return mSelectedTab != null ? mSelectedTab.getPosition() : -1;
            case NAVIGATION_MODE_LIST:
                return mActionView.getDropdownSelectedPosition();
            default:
                return -1;
        }
    }

    @Override
    public int getNavigationItemCount() {
        switch (mActionView.getNavigationMode()) {
            case NAVIGATION_MODE_TABS:
                return mTabs.size();
            case NAVIGATION_MODE_LIST:
                SpinnerAdapter adapter = mActionView.getDropdownAdapter();
                return adapter != null ? adapter.getCount() : 0;
            default:
                return 0;
        }
    }

    @Override
    public int getTabCount() {
        return mTabs.size();
    }

    @Override
    public void setNavigationMode(int mode) {
        final int oldMode = mActionView.getNavigationMode();
        switch (oldMode) {
            case NAVIGATION_MODE_TABS:
                mSavedTabPosition = getSelectedNavigationIndex();
                selectTab(null);
                mTabScrollView.setVisibility(View.GONE);
                break;
        }
        mActionView.setNavigationMode(mode);
        switch (mode) {
            case NAVIGATION_MODE_TABS:
                ensureTabsExist();
                mTabScrollView.setVisibility(View.VISIBLE);
                if (mSavedTabPosition != INVALID_POSITION) {
                    setSelectedNavigationItem(mSavedTabPosition);
                    mSavedTabPosition = INVALID_POSITION;
                }
                break;
        }
        mActionView.setCollapsable(mode == NAVIGATION_MODE_TABS && !mHasEmbeddedTabs);
    }

    @Override
    public Tab getTabAt(int index) {
        return mTabs.get(index);
    }


    @Override
    public void setIcon(int resId) {
        mActionView.setIcon(resId);
    }

    @Override
    public void setIcon(Drawable icon) {
        mActionView.setIcon(icon);
    }

    @Override
    public void setLogo(int resId) {
        mActionView.setLogo(resId);
    }

    @Override
    public void setLogo(Drawable logo) {
        mActionView.setLogo(logo);
    }

	@Override
	public View findViewById(int resId) {
		// TODO Auto-generated method stub
		return mActionView.findViewByIdFromTitle(resId);
	}

	@Override
	public void showTitle() {
		// TODO Auto-generated method stub
		mActionView.showTitle();
	}

	@Override
	public void hideTitle() {
		// TODO Auto-generated method stub
		mActionView.hideTitle();
	}

	@Override
	public void setTitleCompoundDrawables(Drawable left, Drawable top,
			Drawable right, Drawable bottom) {
		// TODO Auto-generated method stub
		mActionView.setTitleCompoundDrawables(left, top, right, bottom);
	}

	@Override
	public void hideBottomBar() {
		// TODO Auto-generated method stub
		mActionBarBottomView.setVisibility(View.GONE);
	}

	@Override
	public void showBottomBar() {
		// TODO Auto-generated method stub
		mActionBarBottomView.setVisibility(View.VISIBLE);
	}
	@Override
    public void setBottomBarBackgroundColor(int color) {
        // TODO Auto-generated method stub
	    mActionBarBottomView.setBackgroundColor(color);
    }
	
	@Override
	public void resetTitle(int layoutResId){
		 mActionView.resetTitle(layoutResId);
	}

	@Override
	public void resetTitleToDefault() {
		// TODO Auto-generated method stub
		mActionView.resetTitleToDefault();
	}

	@Override
	public void hideIcon() {
		// TODO Auto-generated method stub
	    mActionView.hideLeftIcon();
	    mActionView.setLeftText("");
		//mActionView.hideIcon();
	}

	@Override
	public void showIcon() {
		// TODO Auto-generated method stub
		mActionView.showIcon();
	}

    @Override
    public void hideRightText() {
        mActionView.hideRightText();
    }

    @Override
    public void showRightText() {
        mActionView.showRightText();
    }

    @Override
    public void showRightBitmp() {
        mActionView.showRightBitmp();
    }

    @Override
    public void hideRightBitmp() {
        mActionView.hideRightBitmp();
    }

    @Override
    public void setLeftText(CharSequence text) {
        // TODO Auto-generated method stub
        mActionView.setLeftText(text);
    }
	
	@Override
    public void setLeftTextColor(int color) {
        // TODO Auto-generated method stub
	    mActionView.setLeftTextColor(color);
    }

    @Override
    public void setLeftTextSize(int size) {
        // TODO Auto-generated method stub
        mActionView.setLeftTextSize(size);
    }
    
    @Override
    public void setBackgroundColor(int color) {
        // TODO Auto-generated method stub
        mActionView.setBackgroundColor(color);
    }

    @Override
    public void showKeyboard() {
        // TODO Auto-generated method stub
    	
    	AnimationSet animationSet = new AnimationSet(true);
    	
    	/*if(mRomaWayKeyboardView.getVisibility() != View.VISIBLE){
	
	        //参数1～2：x轴的开始位置
	
	        //参数3～4：y轴的开始位置
	
	        //参数5～6：x轴的结束位置
	
	        //参数7～8：x轴的结束位置
	
	        TranslateAnimation translateAnimation =
	
	           new TranslateAnimation(
	
	               Animation.RELATIVE_TO_SELF,0f,
	
	               Animation.RELATIVE_TO_SELF,0f,
	
	               Animation.RELATIVE_TO_SELF,1f,
	
	               Animation.RELATIVE_TO_SELF,0f);
	
	        translateAnimation.setDuration(ANIMATION_DURATION);
	        //animationSet.setFillAfter(true);
	        animationSet.addAnimation(translateAnimation);
    	
	        AlphaAnimation alphaAnimation = new AlphaAnimation(0.5f, 1);
	
	        //设置动画执行的时间
	
	        alphaAnimation.setDuration((int)(ANIMATION_DURATION * 1.7f));
	
	        //将alphaAnimation对象添加到AnimationSet当中
	
	        animationSet.addAnimation(alphaAnimation);
	        mRomaWayKeyboardView.startAnimation(animationSet);
    	}else{//当已经是显示的时候切换时产生的动画
    		
    		 AlphaAnimation alphaAnimation = new AlphaAnimation(0.8f, 1);
    			
 	        //设置动画执行的时间
 	
 	        alphaAnimation.setDuration((int)(ANIMATION_DURATION * 1.3f));
 	
 	        //将alphaAnimation对象添加到AnimationSet当中
 	
 	        animationSet.addAnimation(alphaAnimation);
 	        mRomaWayKeyboardView.startAnimation(animationSet);
    	}*/
    	
        mRomaWayKeyboardView.setVisibility(View.VISIBLE);
        
        
    }

    @Override
    public void hideKeyboard() {
        // TODO Auto-generated method stub
    	if(mRomaWayKeyboardView.getVisibility() == View.VISIBLE){
	    	AnimationSet animationSet = new AnimationSet(true);
	
	        //参数1～2：x轴的开始位置
	
	        //参数3～4：y轴的开始位置
	
	        //参数5～6：x轴的结束位置
	
	        //参数7～8：x轴的结束位置
	
	        TranslateAnimation translateAnimation =
	
	           new TranslateAnimation(
	
	               Animation.RELATIVE_TO_SELF,0f,
	
	               Animation.RELATIVE_TO_SELF,0f,
	
	               Animation.RELATIVE_TO_SELF,0f,
	
	               Animation.RELATIVE_TO_SELF,1f);
	
	        translateAnimation.setDuration((int)(ANIMATION_DURATION * 1.5f));
	       // animationSet.setFillAfter(true);
	        animationSet.addAnimation(translateAnimation);
	        AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0.3f);
	
	        //设置动画执行的时间
	
	        alphaAnimation.setDuration((int)(ANIMATION_DURATION * 0.4f));
	
	        //将alphaAnimation对象添加到AnimationSet当中
	
	        animationSet.addAnimation(alphaAnimation);
	        mRomaWayKeyboardView.startAnimation(animationSet);
    	}
        
        mRomaWayKeyboardView.setVisibility(View.GONE);
        mPopView.setVisibility(View.GONE);
        //abs__root.scrollTo(0, 0);
        abs__root_content.scrollTo(0, 0);
    }
    
    @Override
    public int getKeyboardVisibility() {
        // TODO Auto-generated method stub
        return mRomaWayKeyboardView.getVisibility();
    }
    
    private RomaStockKeyboardListener eotListener;
    @Override
    public void loadShowKeyboard(int defKeyboardXmlId, int editBottomY, 
            OnKeyboardActionListener listener,OnClickListener hideButtonListener){
     // TODO Auto-generated method stub
        
        eotListener = new RomaStockKeyboardListener(defKeyboardXmlId, hideButtonListener);
        eotListener.loadKeyboard(editBottomY, listener,hideButtonListener);
    }
    
    @Override
    public void bundingRomaKeyboardAndLoad(EditText editText, int defKeyboardXmlId,
            OnClickListener hideButtonListener) {
        // TODO Auto-generated method stub
        
        hideSoftInputMethod(editText);
        eotListener = new RomaStockKeyboardListener(editText, defKeyboardXmlId, hideButtonListener);
        eotListener.loadKeyboard(hideButtonListener);
        editText.setOnTouchListener(eotListener);
    }
    
    @Override
    public void setRomaKeyboardHideButtonVisibility(int visibility){
        mRomaWayKeyboardView.setHideButtonVisibility(visibility);
    }
    
    @Override
    public void setOnClickHideButtonListener(OnClickListener l){
        //mRomaWayKeyboardView.setOnClickHideButtonListener(l);
        if(eotListener != null)
            eotListener.setOnClickHideButtonListener(l);
    }
    
    @Override
    public void bundingRomaKeyboard(EditText editText, int defKeyboardXmlId,
            OnClickListener hideButtonListener) {
        // TODO Auto-generated method stub
        hideSoftInputMethod(editText);
        eotListener = new RomaStockKeyboardListener(editText, defKeyboardXmlId, hideButtonListener);
        editText.setOnTouchListener(eotListener);
    }

    /**
     *  隐藏系统键盘,需要经过该方法隐藏，不然编辑框会没有光标的
     * @param ed
     */
    private void hideSoftInputMethod(EditText ed) {
        mActivity.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        int currentVersion = android.os.Build.VERSION.SDK_INT;
        String methodName = null;
        if (currentVersion >= 16) {
            // 4.2
            methodName = "setShowSoftInputOnFocus";
        } else if (currentVersion >= 14) {
            // 4.0
            methodName = "setSoftInputShownOnFocus";
        }

        if (methodName == null) {
            ed.setInputType(InputType.TYPE_NULL);
        } else {
            Class<EditText> cls = EditText.class;
            Method setShowSoftInputOnFocus;
            try {
                setShowSoftInputOnFocus = cls.getMethod(methodName,
                        boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                setShowSoftInputOnFocus.invoke(ed, false);
            } catch (NoSuchMethodException e) {
                ed.setInputType(InputType.TYPE_NULL);
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    
    /**
     * 用于监听触发键盘的调用加载
     * @author wanlaihuan
     *
     */
    private class RomaStockKeyboardListener implements View.OnTouchListener{
        
        private int mKeyboardHeight;
        private RomaWayStockKeyboardUtil mRomaWayStockKeyboardUtil;
        private EditText mEditText;
        private OnClickListener hideButtonListener;
        
        public RomaStockKeyboardListener(int defKeyboardXmlId, OnClickListener hideButtonListener){
          mRomaWayStockKeyboardUtil = new RomaWayStockKeyboardUtil();
          //初始化键盘
          mRomaWayStockKeyboardUtil.initKeyboard(mContext, defKeyboardXmlId);
          //获取键盘高度
          mKeyboardHeight = mRomaWayStockKeyboardUtil.geKeyboardHeight();
          
          this.hideButtonListener = hideButtonListener;
      }
            
        public RomaStockKeyboardListener(EditText editText, int defKeyboardXmlId, OnClickListener hideButtonListener){
            mEditText = editText;
            mRomaWayStockKeyboardUtil = new RomaWayStockKeyboardUtil(editText);
            //初始化键盘
            mRomaWayStockKeyboardUtil.initKeyboard(mContext, defKeyboardXmlId);
            //获取键盘高度
            mKeyboardHeight = mRomaWayStockKeyboardUtil.geKeyboardHeight();
            this.hideButtonListener = hideButtonListener;
        }
        
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            // TODO Auto-generated method stub
            
            if(event.getAction() == MotionEvent.ACTION_UP)
                loadKeyboard(hideButtonListener);
            
            return false;
        }
        
        public void loadKeyboard(OnClickListener hideButtonListener){
            
            int[] location = new int[2];
            mEditText.getLocationOnScreen(location);
            int x = location[0];
            int y = location[1];
            System.out.println("x:"+x+"y:"+y);
            
            int editBottomY = y + mEditText.getHeight();
            
            initTypeKeyboard(editBottomY, mKeyboardHeight);
            
            mRomaWayStockKeyboardUtil.loadKeyboard(ActionBarImpl.this,hideButtonListener);
            
        }
        
        public void loadKeyboard(int editBottomY, OnKeyboardActionListener listener,OnClickListener hideButtonListener){
            
            initTypeKeyboard(editBottomY, mKeyboardHeight);
            
            mRomaWayStockKeyboardUtil.loadKeyboard(ActionBarImpl.this, listener,hideButtonListener);
        }
        
        public void setOnClickHideButtonListener(OnClickListener l){
            mRomaWayStockKeyboardUtil.setOnClickHideButtonListener(l);
        }
    }

    @Override
    public RomaWayKeyBoardView getRomaKeyBoardView(){
        return mRomaWayKeyboardView;
    }
    
    /**
     * 加载不同类型的键盘，遮挡型和上推型
     * @param editBottomY
     * @param keyboardHeight
     */
    private void initTypeKeyboard(int editBottomY, int keyboardHeight){
        int screenHeight = mContext.getResources().getDisplayMetrics().heightPixels;
        int keyboardTop = screenHeight - keyboardHeight;
        
        showKeyboard();
       final int translate = editBottomY - keyboardTop + 3;
       tran_distance=translate;
 
        if(translate <= 0){
            mPopView.setVisibility(View.GONE);   
            
        }else{
            //将页面向上推
//            mPopView.setVisibility(View.VISIBLE);
//            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
//                    LinearLayout.LayoutParams.MATCH_PARENT,
//                    translate);
//            mPopView.setLayoutParams(lp);  
            
            //abs__root.scrollTo(0, translate);
        	  abs__root_content.scrollTo(0, translate);
            
//            final TranslateAnimation animation = new TranslateAnimation(0, 0,0, -translate); 
//            
//            animation.setAnimationListener(new AnimationListener(){
//
//                public void onAnimationEnd(Animation animation) {
//                    //ll_root.clearAnimation();
//                    TranslateAnimation anim = new TranslateAnimation(0,0,0,0);
//                    ll_root.setAnimation(anim);
//                    ll_root.layout(ll_root.getLeft(), ll_root.getTop() - translate, 
//                          ll_root.getRight(), ll_root.getBottom());
//                    ll_root.scrollTo(0, translate);
//                }
//
//                public void onAnimationRepeat(Animation animation) { }
//                public void onAnimationStart(Animation animation) { }
//
//                });
//            
//            animation.setDuration(400);//设置动画持续时间 
//            //animation.setRepeatMode(Animation.REVERSE);//设置反方向执行 
//            ll_root.setAnimation(animation); 
//            /** 开始动画 */ 
//            animation.startNow(); 
        }
        
    }
    
    @Override
    public ActionBarView getActionBarView(){
        return mActionView;
    }
    
    @Override
    public void setMenuLayout(int layoutResId, OnCompleteMenuLayoutListener onCompleteMenuLayoutListener) {
        // TODO Auto-generated method stub
        mActionView.setMenuLayout(layoutResId, onCompleteMenuLayoutListener);
    }

    @Override
    public View getMenuView() {
        // TODO Auto-generated method stub
        return mActionView.getMenuView();
    }
    
    /**用于监听自定义菜单设置成功，可以在回调方法中来处理了*/
    @Override
    public void setOnLoadMenuActionListener(OnLoadMenuActionListener onLoadMenuActionListener){
        mActionView.setOnLoadMenuActionListener(onLoadMenuActionListener);
    }

    @Override
    public void showNetReqProgress() {
        // TODO Auto-generated method stub
        mActionView.showNetReqProgress();
    }

    @Override
    public void hideNetReqProgress() {
        // TODO Auto-generated method stub
        mActionView.hideNetReqProgress();
    }

    @Override
    public void hideRefreshButton() {
        // TODO Auto-generated method stub
        mActionView.hideRefreshButton();
    }

    @Override
    public void hideSearchButton() {
        // TODO Auto-generated method stub
        mActionView.hideSearchButton();
    }
    @Override
    public void showRefreshButton() {
        // TODO Auto-generated method stub
        mActionView.showRefreshButton();
    }

    @Override
    public void showSearchButton() {
        // TODO Auto-generated method stub
        mActionView.showSearchButton();
    }
	@Override
	public void setTitleColor(int color) {
		// TODO Auto-generated method stub
	    mActionView.setTitleColor(color);
	}

	@Override
	public void setSubtitleColor(int color) {
		// TODO Auto-generated method stub
	    mActionView.setSubtitleColor(color);
	}
	
	@Override
	public void setSubTitleLeftColor(int color){
		mActionView.setSubTitleLeftColor(color);
	}
	
	@Override
	public void setLeftSvgIcon(SVGParserRenderer svgParserRenderer) {
		// TODO Auto-generated method stub
		mActionView.setLeftSvgIcon(svgParserRenderer);
	}

    @Override
    public void setRightText(CharSequence text) {
        mActionView.setRightText(text);
    }

    @Override
    public void setRightTextColor(int Color) {
        mActionView.setRightTextColor(Color);
    }

    @Override
    public void setRightTextSize(int size) {
        mActionView.setRightTextSize(size);
    }

    @Override
    public void setRightBitmp(Bitmap mBitmap) {
        mActionView.setRightBitmp(mBitmap);
    }

    @Override
	public void setTitleTextSize(float size) {
		// TODO Auto-generated method stub
		mActionView.setTitleTextSize(size);
	}

	@Override
	public void setSubTitleTextSize(float size) {
		// TODO Auto-generated method stub
		mActionView.setSubTitleTextSize(size);
	}

	@Override
	public LinearLayout get_abs__root_content() {
		return abs__root_content;
	}

	@Override
	public int get_tran_distance() {
		return tran_distance;
	}

}
