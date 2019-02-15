package com.romaway.activity.basephone;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import com.romaway.common.android.base.OriginalContext;
import com.romaway.common.android.phone.ISubTabView;

public class ActivityStackMgr {
    
    public static List<ISubTabView> activityHistoryWindows = new ArrayList<ISubTabView>();
    public static void exitActivityHistory(){
        try{
            for(int i = activityHistoryWindows.size()-1; i >= 0; i--)
                ((Activity)activityHistoryWindows.get(i)).finish();
            }catch(Exception e){
                
            }finally{
                activityHistoryWindows.clear();
            }
    }
    
    public static ISubTabView getActivityHistoryTopStack(){
        return activityHistoryWindows.get(activityHistoryWindows.size()-1);
    }
    
	public static List<ISubTabView> jyHistoryWindows = new ArrayList<ISubTabView>();
	
	/**
	 * 检测某ActivityUpdate是否在当前Task的栈顶
	 */
	public static boolean isTopActivy(Activity cmpName) {
		ActivityManager manager = (ActivityManager) OriginalContext.getContext().getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1);
		String cmpNameTemp = null;

		if (null != runningTaskInfos && runningTaskInfos.size() > 0) {
			cmpNameTemp = (runningTaskInfos.get(0).topActivity).toString();
		}

		if (null == cmpNameTemp)
			return false;
		return cmpNameTemp.equalsIgnoreCase(cmpName.getComponentName().toString());
	}
}
