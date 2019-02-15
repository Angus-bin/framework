package com.romalibs.utils.cache;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.romalibs.utils.cache.FilesDownloadTask.OnDownloadListener;
import com.romalibs.utils.gson.GsonHelper;
import com.romaway.utilslibs.BuildConfig;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * Created by Wesley Lin on 9/5/15.
 */
public class CacheUtils {

    private static final String ENCODING = "utf8";
    private static final String FILE_SUFFIX = ".txt";
    public static String BASE_CACHE_PATH;

    private static final String TAG = "CACHE_UTILS";

    @SuppressLint("NewApi")
	public static void configureCache(Context context) {
        BASE_CACHE_PATH = context.getApplicationInfo().dataDir + File.separator + "files" + File.separator + "CacheUtils";

        if (new File(BASE_CACHE_PATH).mkdirs()) {
            if (BuildConfig.DEBUG)
                Log.d(TAG, BASE_CACHE_PATH + " created.");
        }
    }

    private static String pathForCacheEntry(String name) {
        return BASE_CACHE_PATH + File.separator + name + FILE_SUFFIX;
    }

    /**
     * @param fileName the name of the file
     * @return the content of the file, null if there is no such file
     */
    public static String readFile(String fileName) {
        try {
            return IOUtils.toString(new FileInputStream(pathForCacheEntry(fileName)), ENCODING);
        } catch (IOException e) {
            if (BuildConfig.DEBUG)
                Log.d(TAG, "read cache file failure" + e.toString());
            return null;
        }
    }

    /**
     * @param fileName the name of the file
     * @param fileContent the content of the file
     */
    public static void writeFile(String fileName, String fileContent) {
        try {
            IOUtils.write(fileContent, new FileOutputStream(pathForCacheEntry(fileName)), ENCODING);
        } catch (IOException e) {
            if (BuildConfig.DEBUG)
                Log.d(TAG, "write cache file failure" + e.toString());
        }
    }

    /**
     * @param fileName the name of the file
     * @param dataMaps the map list you want to store
     */
    public static <T> void writeDataMapsFile(String fileName, List<Map<String, T>> dataMaps) {
        writeFile(fileName, GsonHelper.dataMapstoJson(dataMaps));
    }

    /**
     * @param fileName the name of the file
     * @return the map list you previous stored, an empty {@link List} will be returned if there is no such file
     */
    public static <T> List<Map<String, T>> readDataMapsFile(String fileName) {
        return GsonHelper.dataMapsFromJson(readFile(fileName));
    }

    /**
     * @param fileName the name of the file
     * @param object the object you want to store
     * @param <T> a class extends from {@link Object}
     */
    public static <T> void writeObjectFile(String fileName, T object) {
        writeFile(fileName, GsonHelper.objectToJson(object));
    }

    /**
     * @param fileName the name of the file
     * @param t the type of the object you previous stored
     * @return the {@link T} type object you previous stored
     */
    public static <T> T readObjectFile(String fileName, Type t) {
        return GsonHelper.objectFromJson(readFile(fileName), t);
    }

    /**
     * @param fileName the name of the file
     * @param dataMap the map data you want to store
     */
    public static <T> void writeDataMapFile(String fileName, Map<String, T> dataMap) {
        writeFile(fileName, GsonHelper.dataMaptoJson(dataMap));
    }

    /**
     * @param fileName the name of the file
     * @return the map data you previous stored
     */
    public static <T> Map<String, T> readDataMapFile(String fileName) {
        return GsonHelper.dataMapFromJson(readFile(fileName));
    }

    /**
     * delete the file with fileName
     * @param fileName the name of the file
     */
    public static void deleteFile(String fileName) {
        FileUtils.deleteQuietly(new File(pathForCacheEntry(fileName)));
    }


    /**
     * check if there is a cache file with fileName
     * @param fileName the name of the file
     * @return true if the file exits, false otherwise
     */
    public static boolean hasCache(String fileName) {
        return new File(pathForCacheEntry(fileName)).exists();
    }

    /**
     * 缓存文件，比如图片
     * @param tag
     * @param loadUrl
     * @param savePathFile  缓存的绝对路径及文件
     * @param onDownloadListener 监听下载结果成功或失败
     */
    public static void toCacheFiles(String tag, String loadUrl, File savePathFile,
    		OnDownloadListener onDownloadListener){
    	//启动下载任务,开始下载
		new FilesDownloadTask(tag, savePathFile,onDownloadListener).execute(loadUrl);
    }
}
