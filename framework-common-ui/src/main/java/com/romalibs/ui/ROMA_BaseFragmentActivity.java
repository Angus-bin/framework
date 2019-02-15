package com.romalibs.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

/**
 * 用来加载 Fragmnet 作为主界面的 Activity 类
 * 创建时间：2016年3月30日 下午11:00:15 
 * @author wanlh
 * @version 1.0
 */
public class ROMA_BaseFragmentActivity extends FragmentActivity{

	private FragmentManager mFM = null;
	private ROMA_FragmentUtils switchFraUtil;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		switchFraUtil = new ROMA_FragmentUtils(this);
	}
	
	/**
     * 切换 fragment
     * @param fragment Fragment 实例对象
     * @param fragment 实例对象
     */
	public void switchFragment(Fragment fragment) {
		switchFraUtil.switchFragment(fragment);
    }
    
    /**
     * 切换 fragment
     * @param fragmentClassName 包名 + 类名
     */
    public void switchFragment(String fragmentClassName) {
    	switchFraUtil.switchFragment(fragmentClassName);
    }
}
