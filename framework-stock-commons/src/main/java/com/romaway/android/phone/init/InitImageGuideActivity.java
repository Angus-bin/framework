package com.romaway.android.phone.init;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.LruCache;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.romaway.activity.basephone.BaseSherlockFragmentActivity;
import com.romaway.android.phone.utils.ExitConfirm;
import com.romaway.android.phone.R;
import com.romaway.android.phone.config.SysConfigs;
import com.romaway.common.android.base.Res;
import com.romaway.commons.lang.StringUtils;
import com.romaway.commons.log.Logger;
import com.romaway.framework.view.pagerindicator.CirclePageIndicator;
import com.umeng.message.PushAgent;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.magicwindow.mlink.annotation.MLinkRouter;
import roma.romaway.commons.android.config.JsonConfigsParser;
import roma.romaway.commons.android.config.OtherPageConfigsManager;

public class InitImageGuideActivity extends BaseSherlockFragmentActivity {

	private ViewPager mPager;
	//private CirclePageFragmentAdapter mAdapter;
	private GuidePagerAdapter mAdapter;
	private int stateCount;
	private Bitmap[] bitmaps;
	private LruCache<String, Bitmap> mMemoryCache;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		PushAgent.getInstance(this).onAppStart();
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		hideNavigationBar();
		getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
			@Override
			public void onSystemUiVisibilityChange(int visibility) {
				if(visibility == View.VISIBLE)
					hideNavigationBar();
			}
		});
		setContentView(R.layout.roma_init_circles_page);
		getSupportActionBar().hide();

		// 获取到可用内存的最大值，使用内存超出这个值会引起OutOfMemory异常。
		// LruCache通过构造函数传入缓存值，以KB为单位。
		int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
		// 使用最大可用内存值的1/8作为缓存的大小。
		int cacheSize = maxMemory / 8;
		mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
			@Override
			protected int sizeOf(String key, Bitmap bitmap) {
				// 重写此方法来衡量每张图片的大小，默认返回图片数量。
				return bitmap.getByteCount() / 1024;
			}
		};

		String config = OtherPageConfigsManager.getInstance().getConfig();
		if (!StringUtils.isEmpty(config)) {
			List<Map<String, String>> ROMA_Oper_Guide = JsonConfigsParser
					.getJsonConfigInfo(config, "ROMA_Oper_Guide", new String[] {
							"iconUrlNor", "iconUrlSel" });
			bitmaps = new Bitmap[ROMA_Oper_Guide.size()];
			int i = 0;
			for (Map<String, String> itemMap : ROMA_Oper_Guide) {
				Bitmap bm = OtherPageConfigsManager.getInstance().getBitmap(this, itemMap.get("iconUrlNor"));
				if(bm != null){
					bitmaps[i] = bm;
					i++;
				}
			}
		}
		
		//mAdapter = new CirclePageFragmentAdapter(getSupportFragmentManager());
		mAdapter = new GuidePagerAdapter(initViews());
		mPager = (ViewPager) findViewById(R.id.pager);
		mPager.setAdapter(mAdapter);
		final CirclePageIndicator indicator = (CirclePageIndicator) findViewById(R.id.indicator);
//		indicator.setVisibility(View.GONE);
		indicator.setFillColor(0xFFe32f37);
		indicator.setStrokeColor(0xFFe0e0e0);
		indicator.setViewPager(mPager);
		indicator.setCentered(true);
		// We set this on the indicator, NOT the pager
		mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				// Toast.makeText(SampleCirclesWithListener.this,
				// "Changed to page " + position, Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				Logger.d("tag",
						"setOnPageChangeListener onPageScrolled position = "
								+ position + ",positionOffset = "
								+ positionOffset + ",positionOffsetPixels = "
								+ positionOffsetPixels);
			}

			@Override
			public void onPageScrollStateChanged(int state) {
				Logger.d("tag",
						"setOnPageChangeListener onPageScrollStateChanged state = "
								+ state + ",position = "
								+ mPager.getCurrentItem());
				int currentItem = mPager.getCurrentItem();
				indicator.setCurrentItem(currentItem);
				if (state == 1)// 刚开始滑动的时候 stateCount 置 1
					stateCount = 1;
				else
					stateCount++;// 如果次数为
									// 2次说明没有滑动过程，3次说明有滑动过程，也即是从上一个页面滑动过来的，否则是在本身页中滑动。

				if (currentItem == mAdapter.getCount() - 1) {// 说明是在滑动最后一项
//					SysConfigs.setDisplayHelpOnFirst(false, true);
//					InitImageGuideActivity.this.setResult(Activity.RESULT_OK);
//					InitImageGuideActivity.this.finish();
					if (state == 0 && stateCount == 2) {
						SysConfigs.setDisplayHelpOnFirst(false, true);
						InitImageGuideActivity.this.setResult(Activity.RESULT_OK);
						InitImageGuideActivity.this.finish();
						if (Res.getBoolean(R.bool.kconfigs_hasClickGlobalToIntent)) {
						}
					}
				}
			}
		});
	}

	public static final int SYSTEM_UI_FLAG_IMMERSIVE_STICKY = 0x00001000;
	/**
	 * 隐藏底部NavigationBar, 保证全屏完整显示图片比例:
	 * Detects and toggles immersive mode (also known as "hidey bar" mode).
	 */
	public void hideNavigationBar() {
		// The UI options currently enabled are represented by a bitfield.
		// getSystemUiVisibility() gives us that bitfield.
		int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
		int newUiOptions = uiOptions;
		boolean isImmersiveModeEnabled =
				((uiOptions | SYSTEM_UI_FLAG_IMMERSIVE_STICKY) == uiOptions);
		if (isImmersiveModeEnabled) {
			Logger.i("TAG", "Turning immersive mode mode off. ");
		} else {
			Logger.i("TAG", "Turning immersive mode mode on.");
		}

		// Navigation bar hiding:  Backwards compatible to ICS.
		if (Build.VERSION.SDK_INT >= 14) {
			newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
		}

		// Status bar hiding: Backwards compatible to Jellybean
		if (Build.VERSION.SDK_INT >= 16) {
			newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
		}

		// Immersive mode: Backward compatible to KitKat.
		// Note that this flag doesn't do anything by itself, it only augments the behavior
		// of HIDE_NAVIGATION and FLAG_FULLSCREEN.  For the purposes of this sample
		// all three flags are being toggled together.
		// Note that there are two immersive mode UI flags, one of which is referred to as "sticky".
		// Sticky immersive mode differs in that it makes the navigation and status bars
		// semi-transparent, and the UI flag does not get cleared when the user interacts with
		// the screen.
		if (Build.VERSION.SDK_INT >= 18) {
			newUiOptions ^= SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
		}

		getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
	}

	// 找到与像素匹配的图片路径
	public int reSvgPath(String mpx) {
		if (mpx.equals("1.5")) {
			return 0;
		} else if (mpx.equals("1.67")) {
			return 0;
		} else if (mpx.equals("1.78")) {
			return 0;
		} else if (mpx.equals("1.99")) {
			return 1;
		} else if (mpx.equals("2.06")) {
			return 1;
		} else {

		}
		return 1;
	}

	private List<View> initViews(){
		List<View> views = new ArrayList<View>();
		String mPX;
		WindowManager wm = InitImageGuideActivity.this.getWindowManager();
		int width = wm.getDefaultDisplay().getWidth();
		int height = wm.getDefaultDisplay().getHeight();
		// mPX = 10/3;// 拼接分辨率
		float num = (float) height / width;
		DecimalFormat df = new DecimalFormat("0.00");// 格式化小数
		mPX = df.format(num); // 跟服务器下发的保持一致
		int type = reSvgPath(mPX);
		int[] imgId;
//		if (type == 0) {
//			imgId = new int[]{R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4};
//		} else if (type == 1) {
//			imgId = new int[]{R.drawable.image5, R.drawable.image6, R.drawable.image7, R.drawable.image8};
//		} else {
//		}
		imgId = new int[]{R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4};
		if(bitmaps !=null && bitmaps.length >0 && bitmaps[0] != null){
			for (Bitmap bm : bitmaps) {
				ImageView img = new ImageView(this);
				img.setBackgroundDrawable(new BitmapDrawable(bm));
				ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(  
		                ViewGroup.LayoutParams.MATCH_PARENT,  
		                ViewGroup.LayoutParams.MATCH_PARENT);  
				img.setLayoutParams(params);  
				img.setScaleType(ScaleType.FIT_XY); 
				views.add(img);
			}
		}else{
			for (int i : imgId) {
				ImageView img = new ImageView(this);
				img.setImageResource(i);
				ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(  
		                ViewGroup.LayoutParams.MATCH_PARENT,  
		                ViewGroup.LayoutParams.MATCH_PARENT);  
				img.setLayoutParams(params);  
				img.setScaleType(ScaleType.FIT_XY); 
				views.add(img);
			}
		}
		
//		views.get(views.size()-1).setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
////				Logger.d("tag_hrb    ", "v.getHeight()/4:   " + v.getHeight()/4);
////				SysConfigs.setDisplayHelpOnFirst(false, true);
////				InitImageGuideActivity.this.setResult(Activity.RESULT_OK);
////				InitImageGuideActivity.this.finish();
//			}
//		});
//		views.get(views.size()-1).setOnTouchListener(new View.OnTouchListener() {
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
////				if (event.getAction() == MotionEvent.ACTION_DOWN){
////					Logger.d("tag_hrb    ", "v.getHeight()/4:   " + v.getHeight() * 4/5);
////					Logger.d("tag_hrb    ", "event.getY():   " + event.getY());
////				}
//				if (event.getAction() == MotionEvent.ACTION_UP){
//					if (!Res.getBoolean(R.bool.kconfigs_hasClickGlobalToIntent)) {
//						if (event.getY() > v.getHeight() * 4 / 5) {
//							SysConfigs.setDisplayHelpOnFirst(false, true);
//							InitImageGuideActivity.this.setResult(Activity.RESULT_OK);
//							InitImageGuideActivity.this.finish();
//						}
//					} else {
//						SysConfigs.setDisplayHelpOnFirst(false, true);
//						InitImageGuideActivity.this.setResult(Activity.RESULT_OK);
//						InitImageGuideActivity.this.finish();
//					}
//				}
//				return false;
//			}
//		});
		return views;
	}

	private class CirclePageFragmentAdapter extends FragmentPagerAdapter {

		private int[] imageIconResId = new int[] { R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4};

		public CirclePageFragmentAdapter(FragmentManager fm) {
			super(fm);

		}

		@Override
		public Fragment getItem(int position) {
			if(bitmaps !=null && bitmaps.length >0 && bitmaps[position] != null){
				return new GuideFragment(bitmaps[position]);
			}else{
				return new GuideFragment(imageIconResId[position]);
			}
		}

		@Override
		public int getCount() {

			return imageIconResId.length;
		}

	}

	@SuppressLint("ValidFragment")
	private class GuideFragment extends Fragment {

		private int imageResId;
		private Bitmap bitmap;

		@SuppressLint("ValidFragment")
		public GuideFragment(int imageResId) {
			super();
			this.imageResId = imageResId;
		}

		public GuideFragment(Bitmap bitmap) {
			super();
			this.bitmap = bitmap;
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			super.onCreateView(inflater, container, savedInstanceState);
			// View v = inflater.inflate(R.layout.kds_hqstockdatainfo_new_new,
			// container, false);

			ImageView imageView = new ImageView(InitImageGuideActivity.this);
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeResource(getResources(), imageResId, options);
			if(bitmap!=null){
				imageView.setBackgroundDrawable(new BitmapDrawable(bitmap));
			}else{
				imageView.setBackgroundResource(imageResId);
			}
			return imageView;
		}
		
		@Override
		public void onDestroy() {
			// TODO Auto-generated method stub
			super.onDestroy();
			if(bitmap != null){
				bitmap.recycle();
			}
		}
	}

	@Override
	public void backKeyCallBack() {
		// finish();
		Bundle bundle = this.getIntent().getExtras();
		if(bundle!=null&&(!StringUtils.isEmpty(bundle.getString("from")))){
			String from = bundle.getString("from");
			if(from.equals("systemSetting")){
				finish();
				return;
			}
		}
		ExitConfirm.confirmExit(this);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(bitmaps != null && bitmaps.length >0){
			for (Bitmap bitmap : bitmaps) {
				if(bitmap != null)
					bitmap.recycle();
			}
		}
	}
	
	private class GuidePagerAdapter extends PagerAdapter{
		
		private List<View> views;

		public GuidePagerAdapter(List<View> views) {
			super();
			this.views = views;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return views.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}
		
		@Override
		public void destroyItem(View container, int position, Object object) {
			 ((ViewPager) container).removeView(views.get(position));  
		}
		
		@Override
		public Object instantiateItem(View container, int position) {
			((ViewPager) container).addView(views.get(position),0);
			views.get(position).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
//				Logger.d("tag_hrb    ", "v.getHeight()/4:   " + v.getHeight()/4);
//				SysConfigs.setDisplayHelpOnFirst(false, true);
//				InitImageGuideActivity.this.setResult(Activity.RESULT_OK);
//				InitImageGuideActivity.this.finish();
				}
			});
			views.get(position).setOnTouchListener(new View.OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN){
					Logger.d("tag_hrb    ", "v.getHeight()/4:   " + v.getHeight() * 4/5);
					Logger.d("tag_hrb    ", "event.getY():   " + event.getY());
				}
					if (event.getAction() == MotionEvent.ACTION_UP){
						if (!Res.getBoolean(R.bool.kconfigs_hasClickGlobalToIntent)) {
							if (event.getY() > v.getHeight() * 4 / 5) {
								SysConfigs.setDisplayHelpOnFirst(false, true);
								InitImageGuideActivity.this.setResult(Activity.RESULT_OK);
								InitImageGuideActivity.this.finish();
							}
						} else {
							SysConfigs.setDisplayHelpOnFirst(false, true);
							InitImageGuideActivity.this.setResult(Activity.RESULT_OK);
							InitImageGuideActivity.this.finish();
						}
					}
					return false;
				}
			});
			return views.get(position);
		}
		
	}
}
