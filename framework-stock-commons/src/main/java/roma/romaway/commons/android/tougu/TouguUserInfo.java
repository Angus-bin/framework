package roma.romaway.commons.android.tougu;

import com.romaway.common.android.base.data.SharedPreferenceUtils;

public class TouguUserInfo {
	
	private static final String pName = "user_data";
	private static final String NAME = "name";
	private static final String MOBILEID = "mobileId";
	private static final String USERID = "userId";
	private static final String FUNDID = "fundId";
	private static final String LEVEL = "level";
	private static final String AVARAT = "avarar";

	/**
	 * 清空各种用户信息，退出手机登录接口
	 */
	public static void clearUserAccount(){
		// 清空用户信息
		setLevel("");
		setFundId("");
		setUserId("");
		setMobildId("");
		setAvaRar("");
		setName("");
	}

	/**
	 * 设置昵称
	 * @param name
	 */
	public static void setName(String name){
		SharedPreferenceUtils.setPreference(pName, NAME, name);
	}
	
	/**
	 * 获取昵称
	 * @return
	 */
	public static String getName(){
		return SharedPreferenceUtils.getPreference(pName, NAME, "");
	}
	
	/**
	 * 设置手机号码
	 * @param mobile
	 */
	public static void setMobildId(String mobile){
		SharedPreferenceUtils.setPreference(pName, MOBILEID, mobile);
	}
	
	/**
	 * 获取手机号码
	 * @return
	 */
	public static String getMobildId(){
		return SharedPreferenceUtils.getPreference(pName, MOBILEID, "");
	}
	
	/**
	 * 设置用户ID
	 * @param userId
	 */
	public static void setUserId(String userId){
		SharedPreferenceUtils.setPreference(pName, USERID, userId);
	}
	
	/**
	 * 获取用户ID
	 * @return
	 */
	public static String getUserId(){
		return SharedPreferenceUtils.getPreference(pName, USERID, "");
	}
	
	/**
	 * 设置资金账号
	 * @param fundId
	 */
	public static void setFundId(String fundId){
		SharedPreferenceUtils.setPreference(pName, FUNDID, fundId);
	}
	
	/**
	 * 获取资金账号
	 * @return
	 */
	public static String getFundId(){
		return SharedPreferenceUtils.getPreference(pName, FUNDID, "");
	}
	
	/**
	 * 设置是否参加股神大赛标记
	 * @param flag
	 */
	public static void setLevel(String level){
		SharedPreferenceUtils.setPreference(pName, LEVEL, level);
	}
	
	/**
	 * 获取是否参加股神大赛标记
	 * @return
	 */
	public static String getLevel(){
		return SharedPreferenceUtils.getPreference(pName, LEVEL, "");
	}
	
	/**
	 * 设置头像
	 * @param avarar
	 */
	public static void setAvaRar(String avarar){
		SharedPreferenceUtils.setPreference(pName, AVARAT, avarar);
	}
	
	/**
	 * 获取头像路径
	 * @return
	 */
	public static String getAvaRar(){
		return SharedPreferenceUtils.getPreference(pName, AVARAT, "");
	}
}
