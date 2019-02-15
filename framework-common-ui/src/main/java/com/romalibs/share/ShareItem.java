package com.romalibs.share;

public class ShareItem {
	public String tag;
	public String title;
	public int logo;
	public String activityName;
	public String packageName;

	public ShareItem(String tag, String title, int logo, String activityName, String packageName) {
		this.tag = tag;
		this.title = title;
		this.logo = logo;
		this.activityName = activityName;
		this.packageName = packageName;
	}
}
