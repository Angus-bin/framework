package com.romawaylibs.picasso;

import android.content.Context;
import android.graphics.Bitmap.Config;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.romaway.libs.R;

public class PicassoHelper {

	/**
	 * 加载毕加索框架接口
	 * @param context
	 * @param imageView
	 */
	public static void load(Context context
			,ImageView imageView
			,Object urlOrResId){
		try{
			if(urlOrResId instanceof String) {
				Picasso.with(context).load((String)urlOrResId)
				.config(Config.RGB_565)
				.noPlaceholder()
//				.memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
				.into(imageView);
				
			}else if(urlOrResId instanceof Integer) {
				Picasso.with(context).load(((Integer)urlOrResId).intValue())
				.config(Config.RGB_565)
				.noPlaceholder()
//				.memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
				.into(imageView);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 加载毕加索框架接口
	 * @param context
	 * @param imageView
	 * @param uri 网络url 或者本地路径，如："file:///android_asset/night_empty.png"
	 */
	public static void load(Context context
			,ImageView imageView
			,String uri){
		try{
			Picasso.with(context).load(uri)
		    .placeholder(R.drawable.roma_news_small_loadpic_empty_listpage)
			.error(R.drawable.roma_news_small_loadpic_empty_listpage)
			.config(Config.RGB_565)
//			.memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
			.into(imageView);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 加载毕加索框架接口
	 * @param context
	 * @param imageView
	 * @param drawableResId 图片资源ID
	 */
	public static void load(Context context
			,ImageView imageView
			,int drawableResId){
		try{
		Picasso.with(context).load(drawableResId)
		    .placeholder(R.drawable.roma_news_small_loadpic_empty_listpage)
			.error(R.drawable.roma_news_small_loadpic_empty_listpage)
			.config(Config.RGB_565)
//			.memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
			.into(imageView);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param context
	 * @param imageView
	 * @param uri
	 * @param errorIcon
	 */
	public static void load(Context context
			,ImageView imageView
			,String uri
			,int errorIcon){
		try{
		Picasso.with(context).load(uri)
		    .placeholder(errorIcon)
			.error(errorIcon)
//			.memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
			.into(imageView);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 *
	 * @param context
	 * @param imageView
	 * @param uri
	 * @param errorIcon
	 */
	public static void load(Context context
			,ImageView imageView
			,String uri
			,int errorIcon, boolean isCache){
		try{
			if (isCache) {
				Picasso.with(context).load(uri)
						.placeholder(errorIcon)
						.error(errorIcon)
						.memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
						.into(imageView);
			} else {
				Picasso.with(context).load(uri)
						.placeholder(errorIcon)
						.error(errorIcon)
//						.memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
						.into(imageView);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 *
	 * @param context
	 * @param imageView
	 * @param uri
	 * @param errorIcon
	 */
	public static void load(Context context
			,ImageView imageView
			,String uri
			,int errorIcon, Callback callback){
		try{
			Picasso.with(context).load(uri)
					.placeholder(errorIcon)
					.error(errorIcon)
//					.memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
					.into(imageView, callback);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 加载毕加索框架接口
	 * @param context
	 * @param imageView
	 * @param uri 网络url 或者本地路径，如："file:///android_asset/night_empty.png"
	 */
	public static void load(Context context
			,ImageView imageView
			,String uri, Callback callback){
		try{
			Picasso.with(context).load(uri)
					.placeholder(R.drawable.roma_news_small_loadpic_empty_listpage)
					.error(R.drawable.roma_news_small_loadpic_empty_listpage)
					.config(Config.RGB_565)
			.memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
					.into(imageView, callback);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 加载毕加索框架接口
	 * @param context
	 * @param imageView
	 * @param uri 网络url 或者本地路径，如："file:///android_asset/night_empty.png"
	 */
	public static void loadSpread(Context context
			,ImageView imageView
			,String uri, Callback callback){
		try{
			Picasso.with(context).load(uri)
					.placeholder(R.drawable.place_icon)
					.error(R.drawable.error_icon)
					.config(Config.RGB_565)
					.memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
					.into(imageView, callback);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 加载毕加索框架接口
	 * @param context
	 * @param imageView
	 * @param uri 网络url 或者本地路径，如："file:///android_asset/night_empty.png"
	 * 启动广告
	 */
	public static void loadAdv(Context context
			,ImageView imageView
			,String uri, Callback callback){
		try{
			Picasso.with(context).load(uri)
					.placeholder(R.drawable.place_icon)
					.error(R.drawable.error_icon)
					.config(Config.RGB_565)
					.memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
					.into(imageView, callback);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 加载毕加索框架接口
	 * @param context
	 * @param imageView
	 * @param uri 网络url 或者本地路径，如："file:///android_asset/night_empty.png"
	 */
	public static void load(Context context
			,ImageView imageView
			,String uri, Callback callback, boolean isCache){
		try{
			if (isCache) {
				Picasso.with(context).load(uri)
						.placeholder(R.drawable.roma_news_small_loadpic_empty_listpage)
						.error(R.drawable.roma_news_small_loadpic_empty_listpage)
						.config(Config.RGB_565)
//						.memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
						.into(imageView, callback);
			} else {
				Picasso.with(context).load(uri)
						.placeholder(R.drawable.roma_news_small_loadpic_empty_listpage)
						.error(R.drawable.roma_news_small_loadpic_empty_listpage)
						.config(Config.RGB_565)
						.memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
						.into(imageView, callback);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 加载毕加索框架接口
	 * @param context
	 * @param imageView
	 * @param uri 网络url 或者本地路径，如："file:///android_asset/night_empty.png"
	 */
	public static void load(Context context
			,ImageView imageView
			,String uri, Callback callback, int errorIcon, boolean isCache){
		try{
			if (isCache) {
				Picasso.with(context).load(uri)
						.placeholder(R.drawable.roma_news_small_loadpic_empty_listpage)
						.error(errorIcon)
						.config(Config.RGB_565)
//						.memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
						.into(imageView, callback);
			} else {
				Picasso.with(context).load(uri)
						.placeholder(R.drawable.roma_news_small_loadpic_empty_listpage)
						.error(errorIcon)
						.config(Config.RGB_565)
						.memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
						.into(imageView, callback);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 加载毕加索框架接口
	 * @param context
	 * @param imageView
	 * @param uri 网络url 或者本地路径，如："file:///android_asset/night_empty.png"
	 */
	public static void loadIcon(Context context
			,ImageView imageView
			,String uri, Callback callback, int errorIcon, boolean isCache){
		try{
			if (isCache) {
				Picasso.with(context).load(uri)
						.placeholder(R.drawable.roma_news_small_loadpic_empty_listpage)
						.error(errorIcon)
						.config(Config.RGB_565)
//						.memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
						.fit()
						.into(imageView, callback);
			} else {
				Picasso.with(context).load(uri)
						.placeholder(R.drawable.roma_news_small_loadpic_empty_listpage)
						.error(errorIcon)
						.config(Config.RGB_565)
						.memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
						.fit()
						.into(imageView, callback);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 加载毕加索框架接口
	 * @param context
	 * @param imageView
	 * @param uri 网络url 或者本地路径，如："file:///android_asset/night_empty.png"
	 */
	public static void loadBig(Context context
			,ImageView imageView
			,String uri, Callback callback, boolean isCache){
		try{
			if (isCache) {
				Picasso.with(context).load(uri)
						.placeholder(R.drawable.roma_news_small_loadpic_empty_listpage)
						.error(R.drawable.roma_news_small_loadpic_empty_listpage)
						.config(Config.ARGB_8888)
//						.memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
						.into(imageView, callback);
			} else {
				Picasso.with(context).load(uri)
						.placeholder(R.drawable.roma_news_small_loadpic_empty_listpage)
						.error(R.drawable.roma_news_small_loadpic_empty_listpage)
						.config(Config.ARGB_8888)
						.memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
						.into(imageView, callback);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
