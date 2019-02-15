package roma.romaway.commons.android.FloatWindow;

import android.content.Context;
import android.view.View;
import android.view.WindowManager;

import com.romaway.common.android.base.ActivityUtils;
import com.romaway.common.android.base.OriginalContext;

/**
 * 悬浮窗管理器
 * 
 * @author hongrb
 * 
 */
public class FloatWindowManager {

	// 小悬浮窗对象
	private FloatWindowSmallView smallWindow;
	// 用于控制在屏幕上添加或移除悬浮窗
	private WindowManager mWindowManager;
	// FloatWindowManager的单例
	private static FloatWindowManager floatWindowManager;
	// 上下文对象
	private Context context;

	private FloatWindowManager(Context context) {
		this.context = context;
	}

	public static FloatWindowManager getInstance(Context context) {
		if (floatWindowManager == null) {
			floatWindowManager = new FloatWindowManager(context);
		}
		return floatWindowManager;
	}

	/**
	 * 创建小悬浮窗
	 * @param context 	必须为应用程序的Context.
	 */
	public void createSmallWindow(Context context, int layoutResId,
			int rootLayoutId) {
		if (smallWindow == null) {
			smallWindow = new FloatWindowSmallView(context, layoutResId,
					rootLayoutId);
			getWindowManager().addView(smallWindow, smallWindow.smallWindowParams);
		}
	}

	public FloatWindowSmallView getSmallWindow() {
		return smallWindow;
	}

	public synchronized void showFloatWindow(Context context, int layoutResId, int rootLayoutId){
		if (smallWindow == null)
			createSmallWindow(context, layoutResId, rootLayoutId);

		/*
		 * [Bug]添加当前App是否运行在前台判断, 修复因其他券商版本存在关联代码启动时
		 *      触发BaseSherlockFragmentActivity类中showFloatWindow方法, 导致优问图标显示在其他应用:
		 */
		if (ActivityUtils.isTopActivity(OriginalContext.getContext()))
			smallWindow.setVisibility(View.VISIBLE);
		else
			smallWindow.setVisibility(View.GONE);
	}

	public synchronized void hideFloatWindow(){
		if(smallWindow == null)
			return;

		smallWindow.setVisibility(View.GONE);
	}

	/**
	 * 将小悬浮窗从屏幕上移除
	 * 
	 * @param context
	 */
	public void removeSmallWindow() {
		if (smallWindow != null) {
			getWindowManager().removeView(smallWindow);
			smallWindow = null;
		}
	}

	public void setOnClickListener(FloatWindowSmallView.OnClickListener listener) {
		if (smallWindow != null) {
			smallWindow.setOnClickListener(listener);
		}
	}

	/**
	 * 如果WindowManager还未创建，则创建新的WindowManager返回。否则返回当前已创建的WindowManager
	 * @return
	 */
	private WindowManager getWindowManager() {
		if (mWindowManager == null) {
			mWindowManager = (WindowManager) context
					.getSystemService(Context.WINDOW_SERVICE);
		}
		return mWindowManager;
	}
}
