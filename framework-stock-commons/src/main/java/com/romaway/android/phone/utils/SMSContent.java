package com.romaway.android.phone.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.widget.EditText;

import com.romaway.android.phone.config.RomaSysConfig;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SMSContent extends ContentObserver{
	
	public static final String SMS_URI_INBOX = "content://sms/inbox";
	private String smsContent = "";
	private Activity mActivity;
	private EditText mEditText;
	private int mCodeSize;
	private Handler mHandler;

	/**
	 * 获取验证码
	 * @param activity
	 * @param handler
	 * @param editText  验证码输入框
	 * @param codeSize  验证码长度
	 */
	public SMSContent(Activity activity, Handler handler, EditText editText, int codeSize) {
		super(handler);
		mHandler = handler;
		mActivity = activity;
		mEditText = editText;
		mCodeSize = codeSize;
	}
	
	@SuppressLint("NewApi")
	@Override
	public void onChange(boolean selfChange) {
		super.onChange(selfChange);
		Cursor cursor = null;
		cursor = mActivity.managedQuery(Uri.parse(SMS_URI_INBOX), new String[]{"_id", "address", "body", "read"}, "address = ? and read = ?", new String[]{RomaSysConfig.serviceHotline, "0"}, "date desc");
//		cursor = mActivity.managedQuery(Uri.parse(SMS_URI_INBOX), new String[]{"_id", "address", "body", "read"}, "address = ? and read = ?", new String[]{"95368", "0"}, "date desc");
		if(cursor != null){
			cursor.moveToFirst();
			if(cursor.moveToFirst()){
				String smsBody = cursor.getString(cursor.getColumnIndex("body"));
				String regEx = "(?<![0-9])([0-9]{" + mCodeSize + "})(?![0-9])";
				Pattern p = Pattern.compile(regEx);
				Matcher m = p.matcher(smsBody.toString());
				while (m.find()) {
					smsContent = m.group();
					mEditText.setText(smsContent);
					mHandler.sendEmptyMessage(0);
				}
				
			}
		}
		if(Build.VERSION.SDK_INT < 14){
			cursor.close();
		}
	}
}
