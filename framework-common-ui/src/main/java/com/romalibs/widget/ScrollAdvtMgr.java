package com.romalibs.widget;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.romaway.libs.R;
import com.romawaylibs.picasso.PicassoHelper;

public class ScrollAdvtMgr {

	public String[] imageUrls; 
	public String[] urls;
	public String[] titles;
	public String[] subTitles; 
	
	private ViewPager mViewPager;
	private RomaCirclePageIndicator mIndicator;
	private List<ImageView> imageLists = new ArrayList<ImageView>();
	private AdvAdapter mAdvtAdapter;
	private Runnable mRunnable;
	private Context context;
	private LayoutInflater inflater;
	private View parentView;
	
	public ScrollAdvtMgr(Context context){
		this.context = context;
		inflater = LayoutInflater.from(context);
	}
	
	public View getView(){
		if(parentView == null){
			parentView = inflater.inflate(R.layout.roma_libs_scrollads, null);
			mViewPager = (ViewPager) parentView.findViewById(R.id.pager);
			mIndicator = 
					(RomaCirclePageIndicator) parentView.findViewById(R.id.indicator);
		}
		
		return parentView;
	}
	
	/**
	 * 初始化展示的控件视图
	 */
	public void notifyDataSetChanged(){
		
		if((imageUrls.length > 0 && imageLists.size() == 0) || 
				imageLists.size() < imageUrls.length){//内存中的ImageView不够时才创建相应的控件
			
			int listCout = imageLists.size();
			int imageCount = imageUrls.length;
			for(int i = 0; i < imageCount - listCout; i++){ 
				ImageView imageView = new ImageView(context);
				imageView.setScaleType(ScaleType.FIT_XY);
				imageView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						
					}
				});
				imageLists.add(imageView);
			}
		}
		
		if(imageUrls.length > 0 && mViewPager != null){
			if(mAdvtAdapter == null){//第一次是创建。后续只要更新即可
				mAdvtAdapter = new AdvAdapter();
				mViewPager.setAdapter(mAdvtAdapter);
				mIndicator.setViewPager(mViewPager);
			}else{
				mAdvtAdapter.notifyDataSetChanged();
			}
		}
		
		startScroll();
	}
	
	/**
	 * 启动广告位进行轮播
	 */
	private void startScroll(){
		
		//自动轮播线程
		if(imageLists.size() > 1 && mRunnable == null){
			mRunnable = new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					 {
						int position = mViewPager.getCurrentItem();
						if (position == (imageLists.size() - 1)) {
							position = 0;
							mViewPager.setCurrentItem(position);
							mIndicator.setCurrentItem(position);
						} else {
							int curPosition = ++ position;
							mViewPager.setCurrentItem(curPosition);
							mIndicator.setCurrentItem(curPosition);
						}
						mViewPager.postDelayed(mRunnable, 3000);
					}
				}
			};
			mViewPager.postDelayed(mRunnable, 3000);
		}
	}
	
   private class AdvAdapter extends PagerAdapter{
		
		public AdvAdapter() {
			super();
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return imageUrls.length;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public Object instantiateItem(final ViewGroup container, final int position) {
			ImageView imageView = imageLists.get(position);
			PicassoHelper.load(context, imageView, imageUrls[position]);
//			ImageLoader.getInstance().displayImage(imageUrls[position], 
//					imageView, mOptions);
			return imageView;
		}
	}
}
