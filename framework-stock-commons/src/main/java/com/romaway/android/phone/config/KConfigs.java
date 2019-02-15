package com.romaway.android.phone.config;

import com.romaway.common.android.base.Res;
import com.romaway.android.phone.R;

/**
 * 从kconfigs中读取相应配置
 * 
 * @author qinyn
 * 
 */
public class KConfigs {
	/**
	 * 是否开通行情（分时，K线）缓存
	 */
	public static boolean hasHQCache;

	/**
	 * 是否允许自定义K线均线
	 */
	public static boolean hasCustomKlineMA;

	public static void init() {
//		hasHQCache = Res.getBoolean(R.bool.kconfigs_hasHQCache);
//		hasCustomKlineMA = Res.getBoolean(R.bool.kconfigs_hasCustomKLineMA);
	}

}
