package com.romaway.android.phone.utils;

import java.io.File;

import android.content.Context;
//import android.webkit.CacheManager;
/**
 * 
 * @author xueyan
 *清除浏览器缓存数据
 */
public class ClearCache
{
	
	public static void clearCache(Context context){
		clearCacheFolder(context.getCacheDir(), System.currentTimeMillis());
		/*File file = CacheManager.getCacheFileBaseDir();
		if (file != null && file.exists() && file.isDirectory())
		{
			for (File item : file.listFiles())
			{
				item.delete();
			}
			file.delete();
		}*/
		context.deleteDatabase("webview.db");
		context.deleteDatabase("webviewCache.db");
	}
	// clear the cache before time numDays
		public static int clearCacheFolder(File dir, long numDays)
		{
			int deletedFiles = 0;
			if (dir != null && dir.isDirectory())
			{
				try
				{
					for (File child : dir.listFiles())
					{
						if (child.isDirectory())
						{
							deletedFiles += clearCacheFolder(child, numDays);
						}

						if (child.lastModified() < numDays)
						{
							if (child.delete())
							{
								deletedFiles++;
							}
						}
					}
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			return deletedFiles;
		}
}
