package com.romaway.news.protocol.util;

import android.content.Context;

import com.romaway.commons.android.fileutil.FileSystem;

import java.io.File;

public class NewsContent {
	/**
	 * Asset目录头
	 */
	public static final String ASSET_PATH_HEADER = "file:///android_asset/";
	
	/**
	 * 资讯2.0 Config Asset目录下的文件系统目录
	 */
	public static final String ASSET_CONFIG_PATH = "QuanShang/zixun2_0/config/";
	
	/**
	 * 资讯2.0详情 Asset目录下的文件系统目录
	 */
	public static final String ASSET_DETAILS_PATH = "QuanShang/zixun2_0/details/";
	
	private static String files_absolute_path = null;
	
	/**
	 * 获取全路径： file:///data/data/../files/zixun2_0/ 目录路径
	 * @param context
	 * @return
	 */
	public static String getFilesFullDir(Context context) {  
		String path = files_absolute_path == null? 
			       getFilesAbsoluteDir(context) : 
			       files_absolute_path;
		return "file://" + path;
	}
	
	/**
	 * 获取绝对路径：/data/data/../files/zixun2_0/ 
	 * @param context
	 * @return
	 */
	public static String getFilesAbsoluteDir(Context context) {
		if(files_absolute_path == null)
			files_absolute_path = FileSystem.getDataCacheRootDir(context,
					"zixun2_0").getAbsolutePath() + File.separator;	
		
		return files_absolute_path;
	}
}
