package roma.romaway.commons.android.tougu;

import com.romaway.android.phone.RomaUserAccount;
import com.romaway.android.phone.utils.JYStatusUtil;
import com.romaway.android.phone.utils.JYStatusUtil.OnLoginAccountListener;
import com.romaway.commons.db.PersistentCookieStore;

import android.content.Context;
import android.text.TextUtils;

public class UrlParamsManager {

	public static final String KDS_USERID = "{KDS_USERID}";//：用户注册后生成的唯一ID，userid
	
	public static final String KDS_TOKEN = "{KDS_TOKEN}";//：接入认证系统生成的TOKEN
	
	public static final String DGZQ_SC_TOKEN = "{DGZQ_SC_TOKEN}";//：东证商城的TOKEN
	
	public static final String DGZQ_WX_TOKEN = "{DGZQ_WX_TOKEN}";//：东证微信的TOKEN
	
	public static final String KDS_TIME = "{KDS_TIME}";//：App根据本地时间生成getTime()
	
	public static final String KDS_MOBILEPHONE = "{KDS_MOBILEPHONE}";//：用户注册的手机号码
	
	public static final String KDS_ZJZH = "{KDS_ZJZH}";//：当前客户端交易登录的资金账号，若未登录则为空
	
	
	private String[] paramsArray = {KDS_USERID, KDS_TOKEN, DGZQ_SC_TOKEN, DGZQ_WX_TOKEN,
			KDS_TIME, KDS_MOBILEPHONE, KDS_ZJZH};
	
	private static UrlParamsManager mUrlParamsManager;
	private Context context;
	public UrlParamsManager(Context context){
		this.context = context;
	}
	
	public static UrlParamsManager getInstance(Context context){
		if(mUrlParamsManager == null)
			mUrlParamsManager = new UrlParamsManager(context);
		
		return mUrlParamsManager;
	}
	
	//资金账号
	private String acccount = "";
	
	/**
	 * 通过参数key查询得到值
	 * @param key
	 * @return
	 */
	public String getParamsValue(String key){
		if(KDS_USERID.equals(key)){//userId
			PersistentCookieStore cookieStore = new PersistentCookieStore(context);
			String userId = cookieStore.getValue("user_id");
			return userId;
					
		}else if(KDS_MOBILEPHONE.equals(key)){//手机号码
			return RomaUserAccount.getUsername();
			
		}else if(KDS_ZJZH.equals(key)){//资金账号
			JYStatusUtil mJYStatusUtil = new JYStatusUtil(context, true);
			mJYStatusUtil.setOnLoginAccountListener(new OnLoginAccountListener(){

				@Override
				public void onLoginAccount(int status, String loginAccount) {
					acccount = loginAccount;
				}
			});
			
			return acccount;
		}
		
		return null;
	}
	
	/**
	 * 将一个带参数的url转化成实际的url
	 * @param url
	 * @return
	 */
	public String toNewUrl(String url){
		if(!TextUtils.isEmpty(url)) {
			for (int i = 0; i < paramsArray.length; i++) {
				if (url.contains(paramsArray[i]))
					url = url.replace(paramsArray[i], getParamsValue(paramsArray[i]));//进行替换
			}
		}
		return url;
	}
	
}
