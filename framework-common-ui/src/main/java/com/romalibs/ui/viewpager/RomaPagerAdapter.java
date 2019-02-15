package com.romalibs.ui.viewpager;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

public class RomaPagerAdapter extends PagerAdapter  {

	public RomaPagerAdapter(Context context){
		super();
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		// TODO Auto-generated method stub
		return super.getPageTitle(position);
	}
	
	@Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }
	
	@Override
	public RomaBaseBaseView instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		return (RomaBaseBaseView) super.instantiateItem(container, position);
	}
	
	@Override
	public void resumeItem(ViewGroup container, int position, Object object) {
		RomaBaseBaseView onObject = (RomaBaseBaseView)object;
		onObject.onResume();
	}
	
	@Override
	public void pauseItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		RomaBaseBaseView onObject = (RomaBaseBaseView)object;
		onObject.onPause();
	}
	
	@Override
    public void destroyItem(ViewGroup container, int position, Object object) {
		
		RomaBaseBaseView onObject = (RomaBaseBaseView) object;
		onObject.onDestroy();
        container.removeView(onObject);
    }


}
