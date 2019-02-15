package com.romaway.android.phone.utils;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;

import com.romaway.common.android.base.OriginalContext;
import com.romaway.commons.android.fileutil.FileSystem;
import com.romaway.commons.log.Logger;
/**
 * 异步图片加载工具类
 * @author chenjp
 *
 */
public class AsyncImageLoader {
	//软引用，使用内存做临时缓存
	private HashMap<String, SoftReference<Drawable>> imageCache;
	
	public AsyncImageLoader(){
		imageCache = new HashMap<String, SoftReference<Drawable>>();
	}
	
	public interface ImageCallBack{
		public void imageLoaded(Drawable drawable, String imgUrl);
	}
	
	public Drawable loadDrawable(final String imgUrl, final ImageCallBack imageCallBack){
		//如果缓存中存在图片，则先使用缓存
		if (imageCache.containsKey(imgUrl)) {
			SoftReference<Drawable> softReference = imageCache.get(imgUrl);
			Drawable drawable = softReference.get();
			if (drawable != null) {
				imageCallBack.imageLoaded(drawable, imgUrl);//执行回调
				//Logger.d("AsyncImageLoader", "load image from cache and url = " + imgUrl);
				return drawable;
			}
		}
		
		//在主线程执行回调，更新视图
		final Handler mHandler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				imageCallBack.imageLoaded((Drawable)msg.obj, imgUrl);
			}
		};
		
		//创建子线程访问网络并加载图片，把结果交给handler处理
		new Thread(){
			@Override
			public void run() {
				Drawable drawable = loadImageFromUrl(imgUrl);
				//下载完图片放到缓存里
				imageCache.put(imgUrl, new SoftReference<Drawable>(drawable));
				Message message = mHandler.obtainMessage(0, drawable);
				mHandler.sendMessage(message);
			}
		}.start();
		
		return null;
	}
	
	/**
	 * 通过网络下载图片
	 * @param url
	 * @return
	 */
	public Drawable loadImageFromUrl(String url){
		try {
			HttpClient httpClient = new DefaultHttpClient();
			httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
			HttpGet get = new HttpGet(url);
			HttpResponse response = httpClient.execute(get);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = response.getEntity();
				Logger.d("AsyncImageLoader", "load image from web and url = " + url);
				
				Drawable drawable = Drawable.createFromStream(entity.getContent(), "src");
				return drawable;
			} else {
				return null;
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public void clearCache(){
		if (imageCache.size() > 0 ) {
			imageCache.clear();
		}
	}
	
	public Drawable loadDrawableAsFileCache(final String imgUrl, final ImageCallBack imageCallBack) {
		// 如果缓存中存在图片，则先使用缓存
		if (imageCache.containsKey(imgUrl)) {
			SoftReference<Drawable> softReference = imageCache.get(imgUrl);
			Drawable drawable = softReference.get();
			if (drawable != null) {
				imageCallBack.imageLoaded(drawable, imgUrl);// 执行回调
				// Logger.d("AsyncImageLoader", "load image from cache and url = " + imgUrl);
				return drawable;
			}
		}

		// 在主线程执行回调，更新视图
		final Handler mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				imageCallBack.imageLoaded((Drawable) msg.obj, imgUrl);
			}
		};

		// 创建子线程访问网络并加载图片，把结果交给handler处理
		new Thread() {
			@Override
			public void run() {
				Drawable drawable = getDrawableAsFile(OriginalContext.getContext(), imgUrl);
				// 下载完图片放到缓存里
				imageCache.put(imgUrl, new SoftReference<Drawable>(drawable));
				Message message = mHandler.obtainMessage(0, drawable);
				mHandler.sendMessage(message);
			}
		}.start();
		return null;
	}
	
	
	/*
    * 从网络上获取图片，如果图片在本地存在的话就直接拿，如果不存在再去服务器上下载图片
    * 这里的path是图片的地址
    */
    public Drawable getDrawableAsFile(Context context, String url) {
    	try {
        	String name = MD5.makeMd5Sum(url.getBytes());
	        File cache = FileSystem.getCacheRootDir(context, "image");
	        File file = new File(cache, name);
	        // 如果图片存在本地缓存目录，则不去服务器下载 
	        if (!file.exists()) {
	        	loadImageFromUrl(url, file);
	        }
	        return Drawable.createFromStream(new FileInputStream(file), "src");
        } catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        return null;
    }
    
    /**
	 * 通过网络下载图片, 缓存到本地存储
	 * @param url
	 * @return
	 */
	public void loadImageFromUrl(String url, File file){
		InputStream is = null;
		FileOutputStream fos = null;
		try {
			HttpClient httpClient = new DefaultHttpClient();
			httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
			HttpGet get = new HttpGet(url);
			HttpResponse response = httpClient.execute(get);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = response.getEntity();
				Logger.d("AsyncImageLoader", "load image from web and url = " + url);
				is = entity.getContent();
                fos = new FileOutputStream(file);
                byte[] buffer = new byte[1024*2];
                int len = 0;
                while ((len = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                }
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				if (is != null) is.close();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (fos != null) fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
