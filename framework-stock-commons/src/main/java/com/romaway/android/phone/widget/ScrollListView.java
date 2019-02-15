/**
 * 
 */
package com.romaway.android.phone.widget;

import java.util.ArrayList;
import java.util.List;

import roma.romaway.commons.android.theme.SkinManager;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.romaway.android.phone.R;

/**
 * @author duminghui
 * 
 */
public class ScrollListView extends RelativeLayout {
	public static Activity mActivity;
	private Context context;

	private LinearLayout ll;
	private ListView lv;
	private int iFixViewWidth;
	private int iMoveableViewWidth;
	private View viewMoveableHeader;
	private List<View> viewMoveableListViews;
	private int moveSlop = 5;

	private int iLastMotionY;
	private int iLastMotionX;
	private int iTransferX;
	private int iStartDownX;
	
	private View listTitleRect;
	private ScrollListViewAdapter mAdapter;

	private View top_line_view;
	private View bottom_line_view;
	public ScrollListView(Context context) {
		super(context);
		this.context = context;
		SysInfo.closePopupWindow();
		initMainLayout();

	}

	public ScrollListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		setBaseAttributes(this.context, attrs);
		SysInfo.closePopupWindow();
		initMainLayout();
	}

	private void setBaseAttributes(Context c, AttributeSet attrs) {
//		TypedArray a = c.obtainStyledAttributes(attrs,
//				R.styleable.ScrollListView);
//		moveSlop = a.getDimensionPixelSize(R.styleable.ScrollListView_moveSlop,
//				5);
//		a.recycle();
		
		//kevin 2012.7.16， 对于多项目，若包名不同，则通过xml文件来定义属性会导致编译失败，
		//故采用以下这种方式来定义属性。
		int  resouceId  =   - 1 ;	 
        resouceId  =  attrs.getAttributeResourceValue( null ,  "moveSlop" ,  0);
          if  (resouceId  >   0 ) {
        	  try{
        	  moveSlop  =  Integer.parseInt(context.getResources().getText(resouceId).toString());
        	  }catch(NumberFormatException e)
        	  {
        		  //System.out.println("读取moveSlop属性失败");
        		  moveSlop = 5;
        	  }
         }  else  {
        	 moveSlop  =   5 ;
         }

	}

	private void initMainLayout() {
		ll = new LinearLayout(context);
		ll.setOrientation(LinearLayout.VERTICAL);
		addView(ll, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	}

	public void setAdapter(ScrollListViewAdapter<? extends ViewHolder> adapter) {
		mAdapter = adapter;
		SysInfo.closePopupWindow();
		initView(adapter);
		setListAdapter(adapter);
	}

	public void initView(ScrollListViewAdapter<? extends ViewHolder> adapter) {
	    //标题行区域
		ViewGroup vg = adapter.createHeaderLayout();
		View fixView = adapter.createHeaderFixView(); //标题行固定列
		View moveableView = adapter.createHeaderMoveView(); //标题行可移动列
		iFixViewWidth = getViewMeasuredWidth(fixView);
		iMoveableViewWidth = getViewMeasuredWidth(moveableView);
		vg.addView(fixView);
		viewMoveableHeader = moveableView;
		vg.addView(moveableView);
		top_line_view = new View(mActivity);
		bottom_line_view = new View(mActivity);
		ll.addView(top_line_view,LayoutParams.MATCH_PARENT,dip2px(mActivity,1.0f));
		ll.addView(vg);
		ll.addView(bottom_line_view,LayoutParams.MATCH_PARENT,dip2px(mActivity,1.0f));
		//数据行区域
		lv = adapter.createListView();
		lv.setDividerHeight(1);
		
		ll.addView(lv);
		
		listTitleRect = vg; 
		getListArrowPosition();
	}
	  /** 
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素) 
     */  
    public static int dip2px(Context context, float dpValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (dpValue * scale + 0.5f);  
    }  
	public void updateTitle(ScrollListViewAdapter<? extends ViewHolder> adapter){
		top_line_view.setBackgroundColor(SkinManager.getColor("hqMode_divider_color"));
		bottom_line_view.setBackgroundColor(SkinManager.getColor("hqMode_divider_color"));
		ll.removeViewAt(1);
		//标题行区域
		ViewGroup vg = adapter.createHeaderLayout();
		View fixView = adapter.createHeaderFixView(); //标题行固定列
		iFixViewWidth = getViewMeasuredWidth(fixView);
		View moveableView = adapter.createHeaderMoveView(); //标题行可移动列
		iMoveableViewWidth = getViewMeasuredWidth(moveableView);
		vg.addView(fixView);
		viewMoveableHeader = moveableView;
		vg.addView(moveableView);
		ll.addView(vg, 1);
		
	}

	/**
	 * 设置分割线的颜色
	 * @param color
	 */
	public void setDividerColor(int color){
		lv.setCacheColorHint(0x00000000);
		lv.setDivider(new ColorDrawable(color));// abs__action_bar_background_color
		lv.setDividerHeight(1);
	}
	
	/**
	 * 隐藏分割线
	 */
	public void hideDivider(){
		lv.setDividerHeight(0);
		lv.setDivider(null);
	}
	
	/**
	 * 设置滚动监听器
	 * @param l
	 */
	public void setOnScrollListener(OnScrollListener l){
	    lv.setOnScrollListener(l);
	}
	
	private void setListAdapter(
			ScrollListViewAdapter<? extends ViewHolder> adapter) {
		lv.setAdapter(adapter);
		viewMoveableListViews = adapter.getMoveViews();
		List<View> fixViews = adapter.getFixViews();
		for (View view : fixViews) {
			iFixViewWidth = Math.max(iFixViewWidth, getViewMeasuredWidth(view));
		}
		for (View view : viewMoveableListViews) {
			iMoveableViewWidth = Math.max(iMoveableViewWidth,
					getViewMeasuredWidth(view));
		}
	}

	private int getViewMeasuredWidth(View view) {
		view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
				MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		return view.getMeasuredWidth();
	}
 
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		int action = ev.getAction();
		// Logger.getLogger().e("ScrollListView",
		// String.format("onInterceptTouchEvent::::action:%s", action));
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			iLastMotionX = (int) ev.getX();
			iLastMotionY = (int) ev.getY();
			iStartDownX = iLastMotionX;
			break;
		case MotionEvent.ACTION_MOVE:
			int x = (int) ev.getX();
			int y = (int) ev.getY();
			int offsetX = iLastMotionX - x;
			int offsetY = iLastMotionY - y;
			int offsetMoveX = iStartDownX - x;
			if (Math.abs(offsetX) > Math.abs(offsetY)) {
				if (Math.abs(offsetMoveX) > moveSlop) {
					if (iTransferX <= 0
							|| iTransferX >= getHorizontalScrollRange()) {
						iTransferX += (offsetX / 2);
					} else {
						iTransferX += offsetX;
					}
					srcoll(iTransferX);
					iLastMotionX = x;
					return true;
				}
			}
			
			getListArrowPosition();
			break;
		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP:
			// endHorizontalScroll();
			break;
		}
		return super.onInterceptTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		// Logger.getLogger().e("ScrollListView",
		// String.format("onTouchEvent::::action:%s", action));
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			iLastMotionX = (int) event.getX();
			break;
		case MotionEvent.ACTION_MOVE:
			int x = (int) event.getX();
			int offsetX = iLastMotionX - x;
			if (iTransferX <= 0) {
//				iTransferX += (offsetX / 2);
				iTransferX = 0;
			} else if(iTransferX >= getHorizontalScrollRange()){
				iTransferX = getHorizontalScrollRange();
			} else {
				iTransferX += offsetX;
			}
			srcoll(iTransferX);
			iLastMotionX = x;
			
			getListArrowPosition();
			break;
		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP:
			endHorizontalScroll();
			break;
		}
		return true;
	}

	public void srcoll(int iTransferX) {
		if(iTransferX > 0){
			mAdapter.setRightArrowVisible(false);
		}else{
			mAdapter.setRightArrowVisible(true);
		}
		
		viewMoveableHeader.scrollTo(iTransferX, 0);
		for (View view : viewMoveableListViews) {
			view.scrollTo(iTransferX, 0);
		}
	}

	private void endHorizontalScroll() {
		int scrollRange = getHorizontalScrollRange();
		if (iTransferX < 0) {
			iTransferX = 0;
			srcoll(iTransferX);
		} else if (iTransferX > scrollRange) {
			iTransferX = scrollRange;
			srcoll(iTransferX);
		}
//		显示标题栏的左、右移动箭头出错故注释
//		showHeaderMoveArrow();
	}

	public int getHorizontalScrollRange() {
		int scrollRange = 0;
		scrollRange = Math
				.max(0,
						iMoveableViewWidth
								- (getWidth() - getPaddingLeft()
										- getPaddingRight() - iFixViewWidth));
		return scrollRange;
	}

	public static abstract class ScrollListViewAdapter<T extends ViewHolder>
			extends BaseAdapter {
		private List<View> lstMoveView = new ArrayList<View>();
		private List<View> lstFixView = new ArrayList<View>();

		public void setActivity(Activity activity) {
			mActivity = activity;
		}

		public List<View> getMoveViews() {
			return lstMoveView;
		}
		
		public List<View> getFixViews() {
			return lstFixView;
		}

		@Override
		public void notifyDataSetChanged() {
			// TODO Auto-generated method stub
			super.notifyDataSetChanged();
		}
		
		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			T viewHolder = null;
			if (convertView == null) {
				viewHolder = getViewHolder(position);
				ViewGroup vg = createListItemLayout();
				View viewFix = createListItemFixView(position, viewHolder);
				lstFixView.add(viewFix);
				vg.addView(viewFix);
				View viewMove = createListItemMoveView(position, viewHolder);
				lstMoveView.add(viewMove);
				vg.addView(viewMove);
				
				convertView = vg;
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (T) convertView.getTag();
				setListViewData(position, viewHolder);
			}
			return convertView;
		}

		/**
		 * 设置已经在ViewHolder存在的列表数据
		 * 
		 * @param position
		 * @param viewHolder
		 */
		protected abstract void setListViewData(int position, T viewHolder);
		
		/**
		 * 创建列天
		 * 
		 * @return
		 */
		protected abstract ViewGroup createListItemLayout();

		/**
		 * 创建列表项中固定的列
		 * 
		 * @param position
		 * @param viewHolder
		 * @param parent
		 * @return
		 */
		protected abstract View createListItemFixView(int position, T viewHolder);

		/**
		 * 获取列表项中可移动的列
		 * 
		 * @param position
		 * @param viewHolder
		 * @param parent
		 * @return
		 */
		protected abstract View createListItemMoveView(int position,
				T viewHolder);

		/**
		 * 创建标题行布局
		 * 
		 * @param context
		 * @return
		 */
		protected abstract ViewGroup createHeaderLayout();

		/**
		 * 创建列头固定视图
		 * 
		 * @param context
		 * @return
		 */
		protected abstract View createHeaderFixView();

		/**
		 * 创建列头可移动视图
		 * 
		 * @param context
		 * @return
		 */
		protected abstract View createHeaderMoveView();

		/**
		 * 创建数据行ListView
		 * 
		 * @return
		 */
		protected abstract ListView createListView();

		/**
		 * 获取ViewHolder
		 * 
		 * @param position
		 * @return
		 */
		protected abstract T getViewHolder(int position);
		
		protected abstract void setRightArrowVisible(boolean visible);

	}

	public static abstract class ViewHolder {

	}

	/** 显示标题栏的左、右移动箭头 */
	public void showHeaderMoveArrow() {
		int scrollRange = getHorizontalScrollRange();
		SysInfo.closePopupWindow();
		getListArrowPosition();
		if (iTransferX <= 0) {
			// 水平滚动至最左端，显示标题栏的右移动箭头
			showPWHeaderMoveArrowRight(); 
		} else if (iTransferX > 0 && iTransferX < scrollRange) {
			// 水平滚动至最右端，显示标题栏的左、右移动箭头
			showPWHeaderMoveArrowLeft();
			showPWHeaderMoveArrowRight();
		} else if (iTransferX >= scrollRange) {
			// 水平滚动至最右端，显示标题栏的左移动箭头
			showPWHeaderMoveArrowLeft(); 
		}

	}

	private View contentView;

	/**
	 * 显示标题栏的左移动箭头窗体<br>
	 */
	@SuppressLint("NewApi")
	private void showPWHeaderMoveArrowLeft() {
		if (!getCreatePWMethod(SysInfo.mPWMoveArrowLeft, true)) {
			return;
		}
		int pos = iFixViewWidth - 10;
		if(pos < 30){
			pos = 73;
		}
		SysInfo.mPWMoveArrowLeft.showAtLocation(ll, Gravity.NO_GRAVITY, pos,
				SysInfo.mListToWindowMarginTopHeight);// width= 73, height=119
		SysInfo.mPWMoveArrowLeft.update();
		
	}

	/**
	 * 显示标题栏的右移动箭头窗体<br>
	 */
	@SuppressLint("NewApi")
	private void showPWHeaderMoveArrowRight() {
		if (!getCreatePWMethod(SysInfo.mPWMoveArrowRight, false)) {
			return;
		}
		@SuppressWarnings("static-access")
        WindowManager windowManager = this.mActivity.getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		SysInfo.mPWMoveArrowRight.showAtLocation(ll, Gravity.NO_GRAVITY,
				display.getWidth(), SysInfo.mListToWindowMarginTopHeight);// height=119
		SysInfo.mPWMoveArrowRight.update();
	}

	
	@SuppressLint("NewApi")
	@SuppressWarnings("static-access")
    private boolean getCreatePWMethod(PopupWindow mPopupWindow, boolean isLeft) {

		if (this.mActivity == null) {
			return false;
		}

		int layoutID = R.layout.hq_slv_header_left_move_arrow;
		int imgID = R.id.hq_slv_header_left_move_arrow_img;
		int imgBGId = R.drawable.hq_header_navigation_arrows_left;
		if (!isLeft) {
			layoutID = R.layout.hq_slv_header_right_move_arrow;
			imgID = R.id.hq_slv_header_right_move_arrow_img;
			imgBGId = R.drawable.hq_header_navigation_arrows_right;
		}

		Rect rect = new Rect();
		contentView = this.mActivity.getWindow().findViewById(
				Window.ID_ANDROID_CONTENT);
		contentView.getWindowVisibleDisplayFrame(rect);
		LayoutInflater inflater = (LayoutInflater) this.mActivity
				.getLayoutInflater();
		View view = inflater.inflate(layoutID, null, false);

		mPopupWindow = new PopupWindow(view, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT, false);
		mPopupWindow.setBackgroundDrawable(this.mActivity.getResources()
				.getDrawable(R.drawable.widget_bgnull));
		ImageView mImageView = (ImageView) view.findViewById(imgID);
		mImageView.setBackgroundDrawable(this.mActivity.getResources()
				.getDrawable(imgBGId));
		mImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (arg0.getId() == R.id.hq_slv_header_left_move_arrow_img) {
					iTransferX = iTransferX - 80;
				} else {
					iTransferX = iTransferX + 80;
				}
				int scrollRange = getHorizontalScrollRange();
				if (iTransferX < 0) {
					iTransferX = 0;
				} else if (iTransferX > scrollRange) {
					iTransferX = scrollRange;
				}
				srcoll(iTransferX);

//				显示标题栏的左、右移动箭头出错故注释
//				showHeaderMoveArrow();
			}
		});

		if (isLeft) {
			SysInfo.mPWMoveArrowLeft = mPopupWindow;
		} else {
			SysInfo.mPWMoveArrowRight = mPopupWindow;
		}
		return true;
	}
	/**设置列表移动箭头显示位置*/
	private void getListArrowPosition(){
		if (ll != null) {
			int[] location = new int[2];
			// ll.getLocationOnScreen(location);
			ll.getLocationInWindow(location);
			if(listTitleRect == null){
				SysInfo.mListToWindowMarginTopHeight = location[1] + 13;
			} else{
				SysInfo.mListToWindowMarginTopHeight = location[1] + listTitleRect.getMeasuredHeight()/3;
			}

//			Logger.getLogger().e("ABCD", String.format("SysInfo.mListToWindowMarginTopHeight:%s, listTitleRect == null[%s]",
//					SysInfo.mListToWindowMarginTopHeight, listTitleRect == null));
		}
	}

	public void setFirstSelection() {
		lv.setSelection(0);
	}

	public int getTransferX() {
		return iTransferX;
	}

	/**
	 * 重置表单数据,及各坐标位置，常用于清屏
	 */
	public void reSet() {
		iTransferX = iLastMotionX = iStartDownX = 0;
		srcoll(0);
		SysInfo.closePopupWindow();
		lv.setSelection(0);
	}
}
