package com.romaway.news.sql;

public class SqlContent {

	/**
	 * 资讯列表数据缓存的表名
	 */
	public static final String CACHE_LIST_DB_NAME = "news2_0";
	
	/**
	 * 资讯专题缓存表名
	 */
	public static final String CACHE_TOPIC_DB_NAME = "news_2_0_topic";
	
	/**
	 * 资讯收藏缓存表名头部，收藏功能还需要涉及到用户userId，会连接到后面
	 */
	public static final String CACHE_FAVORITE_DB_NAME = "news2_0_favorite_";
	
}
