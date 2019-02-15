package com.romaway.android.phone;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import roma.romaway.commons.android.theme.svg.SVGManager;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.internal.widget.ActionBarView.OnCompleteMenuLayoutListener;
import com.romaway.activity.basephone.BaseSherlockFragmentActivity;
import com.romaway.android.phone.utils.DensityUtil;
import com.trevorpage.tpsvg.SVGView;

public class ActionBarEventMgr {
	
	/** 键盘精灵 */ 	public final static String SEARCH_EVENT = "search";
	/** 刷新 */ 		public final static String REFRESH_EVENT = "refresh";
	
	protected static Map<String, MenuEventInfo> mEventMap = new HashMap<String, MenuEventInfo>();
	
	protected ActionBarEventMgr() {}
	private static ActionBarEventMgr mActionBarEventMgr = new ActionBarEventMgr();
	public final static void init(ActionBarEventMgr actionBarEventMgr){
		mActionBarEventMgr = actionBarEventMgr;
	}
	public static ActionBarEventMgr getInitialize(){
		return mActionBarEventMgr;
	}
	
	protected void initEventMap(){
		mEventMap.clear();
		mEventMap.put(SEARCH_EVENT, new MenuEventInfo(SEARCH_EVENT,
				"abs__nav_right_search", R.drawable.abs__nav_right_search, R.id.sb_search));
		mEventMap.put(REFRESH_EVENT, new MenuEventInfo(REFRESH_EVENT,
				"abs__nav_right_refresh", R.drawable.abs__nav_right_refresh, R.id.sb_refresh));
	}
	
	/** 获取菜单View */
	public View addMenuView(Context context, LinearLayout menuLayout, int menuType){
		initEventMap();
		List<String> menuMap = getMenuMap(menuType);
		SVGView childView = null;
		LayoutParams layoutParams = new LayoutParams(DensityUtil.dip2px(context, 26), DensityUtil.dip2px(context, 26));
		layoutParams.setMargins(DensityUtil.dip2px(context, 8), 0, 0, 0);
		for (String menuTag : menuMap) {
			childView = new SVGView(context);
			childView.setLayoutParams(layoutParams);
			
			MenuEventInfo menuEventInfo = mEventMap.get(menuTag);
			childView.setTag(menuEventInfo.eventName);
			childView.setId(menuEventInfo.viewId);
			childView.setSVGRenderer(SVGManager.getSVGParserRenderer(context, menuEventInfo.svgFileName, menuEventInfo.svgResId), null);
			menuLayout.addView(childView);
		}
		return menuLayout;
	}
	
	protected List<String> getMenuMap(int menuType) {
		List<String> menuList = new ArrayList<String>();
		switch (menuType) {
		case 0:			// 空
			break;
		case 1:			// 仅键盘精灵
			menuList.add(SEARCH_EVENT);
			break;
		case 2:			// 添加键盘精灵与刷新按钮(PS: 待完善, 不可用中)
			menuList.add(SEARCH_EVENT);
			menuList.add(REFRESH_EVENT);
			break;
		}
		return menuList;
	}
	
	/** 给当前Activity ActionBar添加子MenuView */
	public void setOptionsMenu(final SherlockFragmentActivity activity, final int menuType) {
		if (menuType == -1)
			return;
		activity.getSupportActionBar().setMenuLayout(
				R.layout.roma_actionbar_right_layout,
				new OnCompleteMenuLayoutListener() {
					@Override
					public void onCompleted(View view) {
						if (view instanceof LinearLayout) {
							LinearLayout menuLayout = (LinearLayout) view;
							// 根据Menu类型添加子View:
							addMenuView(activity, menuLayout, menuType);

							int childCount = menuLayout.getChildCount();
							for (int i = 0; i < childCount; i++) {
								addOnEventClick(activity, menuLayout.getChildAt(i), (String) menuLayout.getChildAt(i).getTag());
							}
						}
					}
				});
	}
	
	/** 添加子View点击事件, 注意ActionBarEventMgr子类 */
	protected void addOnEventClick(final SherlockFragmentActivity activity, View childView, String tag){
		if(ActionBarEventMgr.SEARCH_EVENT.equalsIgnoreCase(tag)){
			childView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if(activity instanceof BaseSherlockFragmentActivity)
						((BaseSherlockFragmentActivity)activity).onMenuItemAction(v);
				}
			});
		}else if(ActionBarEventMgr.REFRESH_EVENT.equalsIgnoreCase(tag)){
			childView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if(activity instanceof BaseSherlockFragmentActivity)
						((BaseSherlockFragmentActivity)activity).onMenuItemAction(v);
				}
			});
		}
	}
	
	public interface onEventClickListener{
		public void onHandleEvent();
	}
	
	public class MenuEventInfo{
		public String eventName;
		public String svgFileName;
		public int svgResId;
		public OnClickListener onEvent;
		public int viewId;
		public MenuEventInfo(String eventName, String svgFileName, int svgResId, int viewId) {
			super();
			this.eventName = eventName;
			this.svgFileName = svgFileName;
			this.svgResId = svgResId;
			this.viewId = viewId;
		}
	}
}
