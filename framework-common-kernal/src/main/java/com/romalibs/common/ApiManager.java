package com.romalibs.common;

import java.util.HashMap;
import java.util.Map;

public class ApiManager {
	private static Map<String, ApiProvider> apiProviders = new HashMap<String, ApiProvider>();
	private String providerName;
	
	/**
	 * @param providerName 卡片提供者名称
	 */
	public ApiManager (String providerName) {
		this.providerName = providerName;
	}
	
	/**
	* 资讯模块注册KdsApi接口，
	 * H5：用于H5的数据交互，可直接设置为调试桥之用，如：webView.addJavascriptInterface();
	 * 用于资讯原生与股票系统交互
	 * @param provider API提供者对象
	 */
	public static void register(ApiProvider provider) {
		String providerName = provider.getClass().getSimpleName();
		apiProviders.put(providerName, provider);
	}
	
	/**
	 * 反注册提供者
	 * @param providerName
	 */
	public static void unregister(String providerName) {
		apiProviders.remove(providerName);
	}
	
	/**
	 * 获得API提供者
	 * @return
	 */
	public ApiProvider getApiProvider() {
		ApiProvider provider = apiProviders.get(providerName);
		if(provider == null) 
			throw new RuntimeException("找不到该API提供者或者卡片提供者被混淆了，请先检查代码！");
		
		return provider;
	}
}
