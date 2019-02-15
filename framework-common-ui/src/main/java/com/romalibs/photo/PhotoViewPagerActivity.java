package com.romalibs.photo;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.annotation.Keep;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.romalibs.ui.ROMA_BaseActivity;
import com.romaway.libs.R;
import com.romawaylibs.picasso.PicassoHelper;
import com.romalibs.common.CommonEvent.ImgPhotoPreviewEvent;
import com.romalibs.utils.DensityUtil;
import com.romalibs.utils.Logger;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

public class PhotoViewPagerActivity extends ROMA_BaseActivity {

	private ArrayList<String> imageUrls = new ArrayList<String>();
	private int currentPositon;
	private int mPosition;
	private float mLocationX;
	private float mLocationY;
	private float mWidth;
	private float mHeight;
	private float yRatio;//图片顶部相对屏幕高度的比例系数
	private float offsetRatio;//图片顶部与WebView视图最顶部高度与屏幕高度的比例系数
	
	private ViewPager mViewPager;
	private DetailAdapter mDetailAdapter;
	private Object imgPhotoPreviewEventObject;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {  
		super.onCreate(savedInstanceState);
		
		overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
		
		this.setTitleVisibility(View.GONE);//不显示title
		
		setContentView(-1, R.layout.roma_libs_space_image_detail);
		
		final TextView tv_pagecount = (TextView) this.findViewById(R.id.tv_pagecount);  
		
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setPageMargin((int) (getResources().getDisplayMetrics().density * 15));
		 
		mPosition = getIntent().getIntExtra("position", 0);
		yRatio = getIntent().getFloatExtra("yRatio", 0.0f);
		offsetRatio = getIntent().getFloatExtra("offsetRatio", 0.0f);
		
		mLocationX = getIntent().getFloatExtra("locationX", 0.0f);
		mLocationY = yRatio * DensityUtil.getHeightInPx(this);//getIntent().getFloatExtra("locationY", 0.0f);
		mWidth = getIntent().getFloatExtra("width", 0.0f);
		mHeight = getIntent().getFloatExtra("height", 0.0f);
		
		//处理相册图片路径事件
		imgPhotoPreviewEventObject = new Object(){
			
			@Keep
			@Subscribe(threadMode = ThreadMode.MainThread, sticky = true)
		    public void onImgPhotoPreviewEventMainThread(ImgPhotoPreviewEvent messageEvent) {
				Logger.d("tag", "1-onImgPhotoPreviewEventMainThread");
				if(messageEvent == null)
					return;
				imageUrls = (ArrayList<String>) messageEvent.imagePathList.clone();  
				//当页面中不仅仅只包含相册类型的时候会导致图片点击显示错乱，这重新调整
				//当扫描到点击的那一项时应该与list中的索引保持一致，
				for(int i = 0; i < imageUrls.size();i++){
					if(i < mPosition && !imageUrls.get(i).contains("toPhoto_")){
						mPosition -= 1;
						imageUrls.remove(i);
					}
				}

				if(mDetailAdapter == null){//第一次是创建。后续只要更新即可
					mDetailAdapter = new DetailAdapter(imageUrls);
					mViewPager.setAdapter(mDetailAdapter);
					mViewPager.setCurrentItem(mPosition);
					if(imageUrls.size() > 0)
						tv_pagecount.setText((mPosition+1) + "/" + imageUrls.size());
					else
						tv_pagecount.setText("0/0");
				}
				
				mViewPager.setOnPageChangeListener(new OnPageChangeListener(){

					@Override
					public void onPageScrollStateChanged(int arg0) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onPageScrolled(int arg0, float arg1, int arg2) {
						// TODO Auto-generated method stub
					}

					@Override
					public void onPageSelected(int arg0) {
						// TODO Auto-generated method stub
						currentPositon = arg0;
						
						if(imageUrls.size() > 0)
							tv_pagecount.setText((arg0+1) + "/" + imageUrls.size());
						else
							tv_pagecount.setText("0/0");
					}
				});
			}
		};
		EventBus.getDefault().register(imgPhotoPreviewEventObject);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		EventBus.getDefault().unregister(imgPhotoPreviewEventObject);
	}
	
	private class DetailAdapter extends PagerAdapter{
		private PhotoView[] photoViews;
		
		public DetailAdapter(ArrayList<String> imageUrls) {
			super();
			if(imageUrls != null && imageUrls.size() > 0)
				photoViews = new PhotoView[imageUrls.size()];
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return imageUrls == null? 0 : imageUrls.size();
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
			if(photoViews[position] == null){  
				photoViews[position] = new PhotoView(PhotoViewPagerActivity.this);//imageLists.get(position);

				photoViews[position].setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						onBackPressed();
					}  
				  });
				
			//让img2从img1的位置变换到他本身的位置
//			photoViews[position].animaFrom(mRectF);
                
//			photoViews[position].setOriginalInfo(mWidth, mHeight, mLocationX, mLocationY);
//			if(mPosition == position){//第一次点击时产生动画，在滑动过程中不会产生
//				photoViews[position].transformIn();
//				mPosition = -1;
//			} 
			}
			photoViews[position].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 
					ViewGroup.LayoutParams.WRAP_CONTENT));
			photoViews[position].setScaleType(ScaleType.FIT_CENTER);
			photoViews[position].enable();
			PicassoHelper.load(PhotoViewPagerActivity.this, photoViews[position], imageUrls.get(position));
//			ImageLoader.getInstance().displayImage(imageUrls.get(position), 
//					photoViews[position]);
			
			container.addView(photoViews[position]); 
			
			return photoViews[position];
		}
	}
		
	@Override
	public void onBackPressed() {
		finish();
		overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
//		overridePendingTransition(0, R.anim.kds_libs_hold);
//		imageViews[currentPositon].setOnTransformListener(new SmoothImageView.TransformListener() {
//			@Override
//			public void onTransformComplete(int mode) {
//				if (mode == 2) {
//					finish();
//				}
//			}
//		});
//		imageViews[currentPositon].transformOut();
	}

	@Override
	protected void onPause() {  
		super.onPause();
//		if (isFinishing()) {
//			overridePendingTransition(0, 0);
//		}
	}

}
