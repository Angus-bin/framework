package com.romalibs.card;

import java.util.HashMap;
import java.util.Map;

/**
 * 卡片管理者, 需要向该管理者注册卡片提供者，才可以提供该项卡片的服务
 * @author wanlh
 *
 */
public class CardManager {

	private static Map<String, CardProvider> cardProviders = new HashMap<String, CardProvider>();
	private String providerName;
	
	/**
	 * @param providerName 卡片提供者名称
	 */
	public CardManager (String providerName) {
		this.providerName = providerName;
	}
	
	/**
	 * 注册卡片提供者
	 * @param provider 卡片提供者对象
	 */
	public static void register(CardProvider provider) {
		String providerName = provider.getClass().getSimpleName();
		cardProviders.put(providerName, provider);
	}
	
	/**
	 * 反注册卡片提供者
	 * @param providerName
	 */
	public static void unregister(String providerName) {
		cardProviders.remove(providerName);
	}
	
	/**
	 * 获取卡片 ,向提供者providerName 获取 cardNum号卡片对象
	 * @param cardNum 卡片代码，
	 * @return
	 */
	public Card card(int cardNum) {
		CardProvider provider = cardProviders.get(providerName);
		if(provider == null) {
			throw new RuntimeException("找不到该卡片提供者或者卡片提供者被混淆了，请先检查代码！");
		}else {
			return provider.card(cardNum);
		}
	}
	
}
