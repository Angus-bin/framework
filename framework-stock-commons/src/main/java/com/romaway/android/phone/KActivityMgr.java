package com.romaway.android.phone;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment.InstantiationException;
import android.view.View;

import com.actionbarsherlock.app.SherlockFragment;
import com.romaway.activity.basephone.ActivityStackMgr;
import com.romaway.activity.basephone.BaseSherlockFragmentActivity;
import com.romaway.android.phone.utils.CommonUtils;
import com.romaway.android.phone.utils.JYStatusUtil;
import com.romaway.android.phone.widget.RomaToast;
import com.romaway.common.android.phone.ISubTabView;
import com.romaway.commons.lang.StringUtils;
import com.romaway.commons.log.Logger;
import com.romaway.framework.view.SysInfo;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import roma.romaway.commons.android.tougu.TouguShowH5Activity;

/**
 * @author wanlh
 */
public class KActivityMgr {
	
	public enum ActivityStatus{
		ON_RESUME,
		ON_PAUSE,
		ON_DESTROY
	}
	
	/**当前页面是否是在交易模块中*/
	public static boolean isActionJiaoYiMode = false;
	/**
	 * 当前主页是否是顶部页面
	 */
	public static ActivityStatus mainActivityStatus = KActivityMgr.ActivityStatus.ON_DESTROY;
	
	public static KActivityMgr instance = null;

	/**
	 * 行情-自选
	 */
	public static final int HQ_MYSTOCK = 100;
	/**
	 * 行情-行情走势
	 */
	public static final int HQ_HQZS = 106;
	public static final int HQ_LIST2014 = 706;

	/**
	 * 判断是否已初始化登录
	 */
	private static boolean loginStatus = false;

	public static KActivityMgr instance() {
		if (instance == null) {
			instance = new KActivityMgr();
			// String classname = Res.getString(R.string.class_activitymgr);
			// Logger.d("Login.First",
			// String.format("ActivityMgr配置：%s", classname));
			// if (StringUtils.isEmpty(classname)) {
			// instance = new KActivityMgr();
			// return instance;
			// }
			//
			// try {
			// instance = (KActivityMgr) Class.forName(classname)
			// .getConstructor().newInstance();
			// Logger.d("Login.First",
			// String.format("根据ActivityMgr配置：%s，初始化成功", classname));
			// return instance;
			// } catch (Exception e) {
			// StringBuffer sb = new StringBuffer();
			// sb.append("Loading Fail!  \n");
			// sb.append("Class name:[" + classname);
			// sb.append("]\n");
			// sb.append(e.getClass().toString()).append("\n");
			// sb.append(e.getMessage());
			// SysInfo.showMessage(null, sb.toString());
			// Logger.e("Login.First", sb.toString());
			// e.printStackTrace();
			//
			// }
		}
		return instance;
	}

	/**
	 * 获取当前运行的顶部Activity
	 * @param context
	 * @return
	 */
	public static String getTopActivity(Context context)
	{
	     ActivityManager manager = (ActivityManager)context.getSystemService(Activity.ACTIVITY_SERVICE) ;
	     List<RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1) ;
	         
	     if(runningTaskInfos != null)
	       return (runningTaskInfos.get(0).topActivity).toString() ;
	          else
	       return null ;
	}
	
	/**
     *判断当前应用程序处于前台还是后台
     * @param context
     * @return    
     */
    public static boolean isApplicationBroughtToBackground(final Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
			Logger.d("key_h5url123", "topActivity.getPackageName() = " + topActivity.getPackageName());
			Logger.d("key_h5url1234", "context.getPackageName() = " + context.getPackageName());
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;

    }
	 /**
     * 判断应用是否已经启动
     * @param context 一个context
     * @param packageName 要判断应用的包名
     * @return boolean
     */
    @SuppressLint("NewApi")
	public static boolean isAppAlive(Context context, String packageName){
        ActivityManager activityManager =
                (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processInfos
                = activityManager.getRunningAppProcesses();
        for(int i = 0; i < processInfos.size(); i++){
            if(processInfos.get(i).processName.equals(packageName)){
                Logger.i("NotificationLaunch",
						String.format("the %s is running, isAppAlive return true", packageName));
                return true;
            }
        }
        Logger.i("NotificationLaunch",
				String.format("the %s is not running, isAppAlive return false", packageName));
        return false;
    }
    
	/**
	 * 跳转 子项目中有子Activity，
	 * 则需在子项目中继承实现KActivityMgr,并重载实现goTo方法。并在config.xml中配置子KActivityMgr的类名称。
	 * 
	 * @param currentSubTabView
	 *            当前Activity
	 * @param targetID
	 *            目标Activity的modeID
	 * @param bundle
	 * @param requestCode
	 * @param closeCurrentActivity
	 *            是否关闭当前Activity true:是
	 */
	public void goTo(final ISubTabView currentSubTabView, int targetID,
			final Bundle bundle, final int requestCode,
			final boolean closeCurrentActivity) {
	}

	/**
	 * 判断是否已通过初始化登录
	 * 
	 * @return
	 */
	public static boolean isLogined() {
		return loginStatus;
	}

	/**
	 * 跳转至界面<BR>
	 * 如果目标activity已经存在，则重用，不会生成新的activity
	 * 
	 * @param current
	 *            当前
	 * @param targetActivity
	 *            目标Activity
	 * @param bundle
	 * @param requestCode
	 *            请求码，默认值为-1
	 * @param closeCurrentActivity
	 *            是否关闭当前activity
	 */
	public static void switchWindow(ISubTabView current,
			Class<? extends Activity> targetActivity, Bundle bundle,
			int requestCode, boolean closeCurrentActivity) {
		if (current == null) {
			Logger.d("tag:  ", current + "");
			return;
		}
		Logger.d("tag:	", "进来了");
		Activity currentAct = (Activity) current;
		Intent i = new Intent();
		i.setClass(currentAct, targetActivity);
		// i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

		if (bundle != null) {
			i.putExtras(bundle);
		}
		if (requestCode > 0) {
			currentAct.startActivityForResult(i, requestCode);
			Logger.d("tag:	", "跳转了1");
		} else {
			Logger.d("tag:	", "跳转了");
			currentAct.startActivity(i);
		}

		if (closeCurrentActivity) {
			currentAct.finish();
		} else {
			// historyWindows.put(current.getModeID(), current);
			ActivityStackMgr.activityHistoryWindows.add(current);
		}
	}

	/**
	 * 跳转至界面
	 * 
	 * @param activity
	 *            当前activity
	 * @param to
	 *            目标Activity
	 * @param bundle
	 */
	public static void switchWindow(ISubTabView current,
			Class<? extends Activity> targetActivity) {
		switchWindow(current, targetActivity, null, -1, false);
	}

	/**
	 * 常用于启动从一个模块中启动另一个模块中的activity
	 * 
	 * @param current
	 * @param startActivityPCName
	 *            被启动的Activity的包名+类名
	 * @param bundle
	 * @param requestCode
	 * @param closeCurrentActivity
	 */
	public static void switchWindow(ISubTabView current,
			String startActivityPCName, Bundle bundle,
			boolean closeCurrentActivity) {

		switchWindowForResult(current, startActivityPCName, bundle, -1,
				closeCurrentActivity);
	}

	/**
	 * 常用于启动从一个模块中启动另一个模块中的activity
	 * 
	 * @param current
	 * @param startActivityPCName
	 *            被启动的Activity的包名+类名
	 * @param bundle
	 * @param requestCode
	 * @param closeCurrentActivity
	 */
	public static void switchWindowForResult(ISubTabView current,
			String startActivityPCName, Bundle bundle, int requestCode,
			boolean closeCurrentActivity) {
		if (current == null) {
			return;
		}
		Activity currentAct = (Activity) current;
		Intent i = new Intent();
		// i.setClass(currentAct, targetActivity);
		// i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		ComponentName componentName = new ComponentName(
				currentAct.getPackageName(), startActivityPCName);
		i.setComponent(componentName);

		if (bundle != null) {
			i.putExtras(bundle);
		}
		if (requestCode > 0) {
			currentAct.startActivityForResult(i, requestCode);
		} else {
			currentAct.startActivity(i);
		}

		if (closeCurrentActivity) {
			currentAct.finish();
		} else {
			// historyWindows.put(current.getModeID(), current);
			ActivityStackMgr.activityHistoryWindows.add(current);
		}
	}

	/**
	 * 跳转至界面
	 * 
	 * @param activity
	 *            当前activity
	 * @param to
	 *            目标Activity
	 * @param bundle
	 */
	public static void switchWindow(ISubTabView current,
			Class<? extends Activity> targetActivity, boolean closeCurrent) {
		switchWindow(current, targetActivity, null, -1, closeCurrent);
	}

	/**
	 * 跳转至界面
	 * 
	 * @param activity
	 * @param to
	 * @param bundle
	 */
	public static void switchWindow(ISubTabView current,
			Class<? extends Activity> targetActivity, Bundle bundle,
			boolean closeCurrent) {
		switchWindow(current, targetActivity, bundle, -1, closeCurrent);
	}
	
	/** 以Fragment的为界面切换类型，比如，首页快捷按钮向行情模块功能切换 */
	private static final int SWITCH_TYPE_FRAGMENT = 0;
	
	/** 以Activity的为界面切换类型，比如，首页快捷按钮向行情模块功能切换 */
	private static final int SWITCH_TYPE_ACTIVITY = 1;
	
	/** 以跳转到交易界面切换类型，比如，首页快捷按钮向交易模块功能切换 */
	private static final int SWITCH_TYPE_JIAOYI_FUN = 2;

	/**
	 * 根据不同的按钮获取不同的Intent 实例对象
	 * 
	 * @param funKey
	 * @return
	 */
	public static Intent getIntentInstance(String funKey, String tag, String marketId) {
		Intent intent = new Intent();

		return intent;
	}

	/**
	 * 跳转到交易公共Ticket界面, 并在该界面调用H5方法执行特定Key值处理, 由 H5 统一处理:
	 * @param funKey		功能Key
	 *     仅"KDS_TICKET", "KDS_PHONE_TICKET"开头,    走业务办理流程, 最后回调 openThirdPartyWebSuperInterface 方法;
	 *     "KDS_TICKET_URL"开头,  走老添鑫宝流程, 最后回调 openThirdPartyWebInterface 方法;
	 * @param Intent		intent.putExtra("hasFunction", true); 即可
	 */
	public static Bundle switchJiaoyiTicketHasKey(String funKey) {
		return getJiaoYiSwitchBundle(funKey, "");
	}

	@NonNull
	public static Bundle getJiaoYiSwitchBundle(String funKey, String urlStr) {
		Bundle bundle = new Bundle();
		bundle.putInt("SwitchType", SWITCH_TYPE_JIAOYI_FUN);//快捷按钮向交易模块跳转
		bundle.putInt("hasRefresh", 0);// 有刷新按钮
		bundle.putString("InputContentKey", funKey);
		bundle.putString("JYUrl", urlStr);
		return bundle;
	}

	/**
	 * 根据不同的按钮获取不同的Intent 字符串
	 *
	 * @param funKey
	 * @return
	 */
	public static String getIntentString(String funKey, String tag, String marketId) {

		return getIntentInstance(funKey, tag, marketId).toURI();
	}

	/**
	 * 根据不同的按钮获取不同的Intent 字符串
	 *
	 * @param funKey
	 * @return
	 */
	public static String getIntentString(String funKey, String tag) {

		return getIntentInstance(funKey, tag, "").toURI();
	}

	/**
	 * 用于跳转web链接的接口
	 * @param activity
	 * @param class  传递extends TouguShowH5Activity.java类的子类
	 * @param hasTitle
	 *        在此用来标识是否需要原生title部分 "KDS_WebPageNoHead"为不需要，"KDS_WebPageWithHead"为需要
	 * @param loginType 打开链接标记是否需要先登录 0：无需任何注册登录；1：仅仅手机号码注册登录；2：仅仅资金账号登录；3：必须手机号码和资金账号同时都登录；
	 * @param webUrl  链接url
	 */
	public static void webClickSwitch(final Activity activity, final Class<?> classs,
			final String hasTitle, String loginType, final String webUrl){
		if(!StringUtils.isEmpty(webUrl)){

    		if(StringUtils.isEmpty(loginType))
    			loginType = "0";

			if(loginType.equals("1") ||
					loginType.equals("3")){
				// 手机注册登录
				if (StringUtils.isEmpty(RomaUserAccount.getUsername())) { // 如果初始化验证类型下发为0，即不需手机号码注册，登录交易之前需注册手机号码
					KActivityMgr
							.switchWindow(
									(ISubTabView) activity,
									"kds.szkingdom.modeinit.android.activity.login.UserLoginFragmentActivity",
									null, false);
					return;
				}
			}

			if(loginType.equals("2") ||
					loginType.equals("3")){
				JYStatusUtil jyStatusUtil = new JYStatusUtil(activity, true);
				jyStatusUtil.setOnLoginAccountListener(new JYStatusUtil.OnLoginAccountListener(){

					@Override
					public void onLoginAccount(int status, String loginAccount) {
						// TODO Auto-generated method stub
						if(status == JYStatusUtil.JY_LOGIN_STATUS_NONE){
							Bundle bundle = new Bundle();
							bundle.putString(
									"JYUrl",
									"");
							bundle.putInt("hasRefresh", 1);
							bundle.putString("KeyFunType",
									"KDS_EX_SHORTCUT");
							KActivityMgr
									.switchWindow(
											(ISubTabView) activity,
											"kds.szkingdom.jiaoyi.android.phone.activity.jy.JiaoYiLoginSherlockFragmentActivity",
											bundle, false);

						}else if(status == JYStatusUtil.JY_LOGIN_STATUS_OK){
							Intent intent = new Intent();
				    		intent.putExtra("key_h5url", webUrl);

				    		if(!StringUtils.isEmpty(hasTitle) && hasTitle.equals("KDS_WebPageNoHead"))
				    			intent.putExtra("key_titleVisibility", View.GONE);
							else if(!StringUtils.isEmpty(hasTitle) && hasTitle.equals("KDS_WebPageWithHead"))
								intent.putExtra("key_titleVisibility", View.VISIBLE);

				            intent.setClass(activity, classs);
				            activity.startActivity(intent);
						}
					}
				});

				return;
			}

			Intent intent = new Intent();
    		intent.putExtra("key_h5url", webUrl);

    		if(!StringUtils.isEmpty(hasTitle) && hasTitle.equals("KDS_WebPageNoHead"))
    			intent.putExtra("key_titleVisibility", View.GONE);
			else if(!StringUtils.isEmpty(hasTitle) && hasTitle.equals("KDS_WebPageWithHead"))
				intent.putExtra("key_titleVisibility", View.VISIBLE);

            intent.setClass(activity, classs);
            activity.startActivity(intent);

    	}
	}

	/**
	 * 快捷按钮的跳转
	 *
	 * @param intent
	 */
	public static void shortcutClickSwitch(final Activity activity,
			final Intent intent) {

		if (intent == null || intent.toURI() == null
				|| intent.getExtras().getString("InputContentKey") == null
				|| intent.getExtras().getString("InputContentKey").equals("")) {
			SysInfo.showMessage(activity, "该功能接口暂未开通!");
			return;
		}

		final Bundle bundle = intent.getExtras();
		int switchType = bundle.getInt("SwitchType", SWITCH_TYPE_FRAGMENT);

		if (switchType == SWITCH_TYPE_FRAGMENT)// 以Fragment的形式进行跳转
			((BaseSherlockFragmentActivity) activity)
					.switchFragmentForStack(intent);

		else if (switchType == SWITCH_TYPE_ACTIVITY) {// 以Activity的形式进行跳转
			KActivityMgr.switchWindow((ISubTabView) activity,
					intent.getAction(), intent.getExtras(), false);

		}  else if (switchType == SWITCH_TYPE_JIAOYI_FUN) {

			if(!JYStatusUtil.isClearLoginInfo){
				RomaToast.showMessage(activity, "正在加载交易功能...");

				(new View(activity)).postDelayed(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						switchJiaoYiFun(activity, intent, bundle);
					}
				}, 1000);

				return;
			}

			switchJiaoYiFun(activity, intent, bundle);

		}
	}
// kconfigs_isCheck_Trade_isLogin
	public static void switchJiaoYiFun(final Activity activity, final Intent intent, Bundle bundle){

	}
	
	public static void switchWebViewForStack(
			BaseSherlockFragmentActivity activity, Intent intent) {
		SherlockFragment tagFragment = null;
		String fragmentClassName = intent.getAction();
		try {
			tagFragment = (SherlockFragment) Class.forName(fragmentClassName)
					.getConstructor().newInstance();
			tagFragment.setIntent(intent);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (java.lang.InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (tagFragment != null)
			activity.switchWebViewForStack(tagFragment);
	}


	public static void gotoTicketWebActivity(Activity mActivity, String funKey) {
		// 根据功能 funKey 获取对应的中台/默认配置的 portalUrl、targetUrl 地址:
		String[] configUrls = CommonUtils.getLoadCommonConfigUrls(funKey);

		Intent mIntent = new Intent();
		mIntent.putExtra("key_h5url", configUrls[0]);
		mIntent.putExtra("key_titleVisibility", View.VISIBLE);
		mIntent.putExtra("backType", 0);        // 逐级关闭

		Bundle webBundle = new Bundle();
		webBundle.putString("functionCode", funKey);
		webBundle.putString("custid", "");
		webBundle.putString("fundid", "");
		webBundle.putString("userrole", "");
		webBundle.putString("orgid", "");
		webBundle.putString("ticket", "");
		webBundle.putString("targetUrl", configUrls[1]);
		mIntent.putExtras(webBundle);

		mIntent.setClass(mActivity, TouguShowH5Activity.class);
		mActivity.startActivity(mIntent);
	}
}
