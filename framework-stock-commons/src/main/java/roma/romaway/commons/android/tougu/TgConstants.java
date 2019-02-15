package roma.romaway.commons.android.tougu;

import android.os.Bundle;

public class TgConstants {

    /**通知栏点击跳转数据传递的bundle*/
	public static Bundle notificationBundle;
	/**通知栏点击事件的webUrl key*/
	public static final String KEY_WEBURL = "key_webUrl";
	/**通知栏点击事件的通知类型 key 1：代表个人中心通知*/
	public static final String KEY_TYPE = "key_type";
	/**通知栏点击事件的webUrl是否需要原生的title否则就是url自带title key*/
	public static final String KEY_SRCTitleVisibility = "src_TitleVisibility";
	/**通知栏点击事件的数据交互bundle key*/
	public static final String KEY_BUNDLE = "key_bundle";
	
	/**设置极光推送别名的广播action*/
	public static final String ACTION_KDS_JPUSH_SETALIAS = "action.kds.jpush.setalias";
	
	/**通知栏点击事件的广播action*/
	public static final String ACTION_NOTIFICATION_OPENED = "action.notification.opened";

	/**通知栏接收事件的广播action*/
	public static final String ACTION_NOTIFICATION_RECEIVED = "action.notification.received";
	
	/**通知栏接收事件取消红点的广播action*/
	public static final String ACTION_NOTIFICATION_REDPOINT_CANCEL = "action.notification.REDPOINT_CANCEL";
}
