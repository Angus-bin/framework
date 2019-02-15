package com.romaway.commons.utils;

import android.view.View;

/**
 * 阻止在极短的时间重复执行某个函数中的代码
 * @author wanlh
 *
 */
public class PreRepeatUtil {
	
	private static boolean isRepeatFlag = true;
	private static MyRunnable mMyRunnable = new MyRunnable();
	
	private static class MyRunnable implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			isRepeatFlag = true;
		}
	}
	
	public static boolean isRepeat(View view){
		if(!isRepeatFlag){
			view.removeCallbacks(mMyRunnable);
			view.postDelayed(mMyRunnable, 300);//300毫秒恢复，300毫秒之内不允许重复请求数据
			return false;
		}else{
			isRepeatFlag = false;
			view.removeCallbacks(mMyRunnable);
			view.postDelayed(mMyRunnable, 300);//300毫秒恢复，300毫秒之内不允许重复请求数据
		}
		return true;
	}
}
