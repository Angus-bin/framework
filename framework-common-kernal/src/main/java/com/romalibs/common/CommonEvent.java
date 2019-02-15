package com.romalibs.common;

import java.util.ArrayList;

public class CommonEvent {
	/**图片浏览事件*/
	public static class ImgPhotoPreviewEvent{
		public ArrayList<String> imagePathList = new ArrayList<String>();
	}
	
	/**视频播放事件，比如资讯模块*/
	public static class VideoPlayEvent{
		public String attribute;
	}
	
	/**资讯已读标记事件*/
	public static class NewsReadFlagEvent {
		/**资讯ID*/
		public String newsId;
		/**是否已读标记*/
		public String readFlag;
	}
}
