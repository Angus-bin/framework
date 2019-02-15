package roma.romaway.commons.android.webkit;

import android.view.View;

/**
 * 阻止在极短的时间重复执行某个函数中的代码
 * @author wanlh
 *
 */
public class ReEventsController {
	
	private boolean isRepeatFlag = false;
	private MyRunnable mMyRunnable;
	
	private class MyRunnable implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			isRepeatFlag = false;
		}
	}
	
	/**
	 * 在time时间之内是否重复，阻止代码往下执行
	 * @param view
	 * @param time
	 * @return
	 */
	public boolean isRepeat(View view, int time){
		if(mMyRunnable == null)
			mMyRunnable = new MyRunnable();
		if(isRepeatFlag){
			//view.removeCallbacks(mMyRunnable);
			//view.postDelayed(mMyRunnable, time);//2500毫秒恢复，300毫秒之内不允许重复请求数据
			return true;
		}else{
			isRepeatFlag = true;
			view.removeCallbacks(mMyRunnable);
			view.postDelayed(mMyRunnable, time);//2500毫秒恢复，300毫秒之内不允许重复请求数据
		}
		return false;
	}
}
