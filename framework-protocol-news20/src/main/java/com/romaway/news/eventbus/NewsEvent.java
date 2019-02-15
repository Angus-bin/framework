package com.romaway.news.eventbus;

import android.support.annotation.Keep;

/**
 * 
 * @author hongrb 2017-05-25
 * 资讯eventBus框架事件定义类
 */
@Keep //保持不被混淆
public class NewsEvent {
	
	/**资讯详情H5图片显示的处理事件*/
	public static class DetailImgShowEvent{
		/**事件类型*/
		public String eventType; 
		/**视频图片下载url*/
		public String videoImageUrl;
		public String videoImageSavePath;
		public String videoUrl;
		public String videoWidth;
		public String videoHeight;
		
		/**图片索引标记*/
		public String[] replaceimages;
		/**图片的网络下载地址*/
		public String[] webImageUrls;
		/**图片的下载本地保存地址*/
		public String[] imageSavePaths;
	}
}
