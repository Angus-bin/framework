package com.romalibs.card;

import android.support.annotation.Keep;

@Keep
public interface CardProvider {
	/**
	 * 提供卡片
	 * @param cardNum 卡片编号
	 * @return 返回卡片视图对象
	 */
	public Card card(int cardNum);
	
}
