package com.romalibs.share;

public interface LoginListener {
	
	void onLoginSuccess(String account);
	
	void onLoginFailed();
}
