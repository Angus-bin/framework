package roma.romaway.commons.android.h5download;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;

import com.romaway.android.phone.config.Configs;
import com.romaway.android.phone.config.RomaSysConfig;
import com.romaway.common.android.base.data.SharedPreferenceUtils;
import com.romaway.commons.lang.StringUtils;
import com.romaway.commons.log.Logger;

/**
 * 
 * @author lichuan
 *  H5模块的下载类
 *
 */
public class H5downloader {

	 public static final int INIT = 1;//初始化状态
	 public static final int START_DOWNLOAD_FOR_WIFI = 2;  //开始wifi下载
	 public static final int START_DOWNLOA_FOR_NORWIFI = 3;  //开始移动网下载
	 public static final int DOWNLOADING = 4;//下载进行中
	 public static final int PAUSE = 4;  //暂停状态
	 public static final int DOWNLOAD_COMPELETE = 5;//下载完成
	 public static final int DOWNLOAD_UPZIPFILE_COMPELETE = 6;//解压缩完成
	 
	 private String urlstr;// 下载的地址  
     private String ziplocalfile;// zip保存路径  
     private String  rootFileName = "kds519";//解压根目录的文件名
     private int threadcount;// 线程数  
     private Handler mHandler;// 消息处理器   
     private int fileSize;// 所要下载的文件的大小  
     private Context context;   
     private List<DownloadInfo> infos;// 存放下载信息类的集合  
     private int state = INIT;  
     private H5Dao dao = null;
     private String h5_versionNum;
     private MyThread downThread;
     
     /**
      * 
      * @param context
      * @param localfile 本地Zip文件路径
      * @param mHandler
      */
     public H5downloader(Context context, String localfile,Handler mHandler) {  
         this.ziplocalfile = localfile;  
         this.mHandler = mHandler;  
         this.context = context;  
         dao = H5Dao.getInstance(context);
     } 
     
     /**
      * 
      * @param urlstr 下载链接地址
      * @param localfile 下载zip文件的路径
      * @param threadcount
      * @param context
      * @param mHandler
      */
     public H5downloader(String urlstr, String localfile, int threadcount,  
             Context context, Handler mHandler) {  
         this.urlstr = urlstr;  
         this.ziplocalfile = localfile;  
         this.threadcount = threadcount;  
         this.mHandler = mHandler;  
         this.context = context;  
         dao = H5Dao.getInstance(context);
     }  
     
     /** 
      *判断是否正在下载  
      */  
     public boolean isdownloading() {  
         return state == DOWNLOADING;  
     }  
     
     /** 
      * 得到downloader里的信息 
      * 首先进行判断是否是第一次下载，如果是第一次就要进行初始化，并将下载器的信息保存到数据库中 
      * 如果不是第一次下载，那就要从数据库中读出之前下载的信息（起始位置，结束为止，文件大小等），并将下载信息返回给下载器 
      * 注意这里返回null,表示不会触发下载
      */  
     public LoadInfo getDownloaderInfors_release() {  
    	 
    	 fileSize = RomaSysConfig.h5_fileSize_release;
    	 int range = fileSize / threadcount; 
    	 infos = new ArrayList<DownloadInfo>();
    	 h5_versionNum = RomaSysConfig.h5_versionNum_release;
    	 
    	//比较版本号高低
    	 if( !compareVersion(h5_versionNum) )
    	 {
    		 Logger.v("H5版本号比较", "已经是最新，无需下载！");
    		 return null;
    	 }
    	 
         if (isFirst(h5_versionNum)) 
         {  
             Logger.v("H5downloader", "首次下载当前这版本");  
             init();  
             for (int i = 0; i < threadcount - 1; i++) 
             {  
                 DownloadInfo info = new DownloadInfo(i, i * range, (i + 1)* range - 1, 0, urlstr,
                		 RomaSysConfig.h5_versionNum_release,1,"kds519");
                 infos.add(info);  
             }  
             DownloadInfo info = new DownloadInfo(threadcount - 1,(threadcount - 1) * range, 
            		 fileSize - 1, 0, urlstr,h5_versionNum,1,"kds519");  
             infos.add(info);  
             //保存infos中的数据到数据库  
             H5Dao.getInstance(context).saveInfos(infos); 
             
             if(isWifiConnected(context)){
            	 state = START_DOWNLOAD_FOR_WIFI;
            	 mHandler.sendEmptyMessage(START_DOWNLOAD_FOR_WIFI);
             }else{
            	 state = START_DOWNLOA_FOR_NORWIFI;
            	 mHandler.sendEmptyMessage(START_DOWNLOA_FOR_NORWIFI);
             }
             
             //创建一个LoadInfo对象记载下载器的具体信息  
             LoadInfo loadInfo = new LoadInfo(fileSize, 0, urlstr);  
             return loadInfo;  
         } 
         else 
         {  
        	 List<DownloadInfo> datas = dao.getInfos(h5_versionNum);
        	 
        	 if(datas.size() == 0) 
        		 return null;
        	 
        	 int state = datas.get(0).getState();//Environment.getExternalStorageDirectory().toString()
        	 String upziplocalfile= context.getFilesDir()+"/"+datas.get(0).getUpzip_file();
        	 if(state == START_DOWNLOAD_FOR_WIFI )
        	 {
        		  //表存在，但原文件给删除了
            	 File localFile = new File(upziplocalfile);
            	 if(!localFile.exists())
            	 {
            		//删除表记录
            		 deleteToDB(h5_versionNum);
            		 DownloadInfo info = new DownloadInfo(threadcount - 1,(threadcount - 1) * range, fileSize - 1, 0, 
            				 urlstr,h5_versionNum,1,"kds519");  
                     infos.add(info);  
                     //保存infos中的数据到数据库  
                     dao.saveInfos(infos);
                     
                     if(isWifiConnected(context)){
                    	 state = START_DOWNLOAD_FOR_WIFI;
                    	 mHandler.sendEmptyMessage(START_DOWNLOAD_FOR_WIFI);
                     }else{
                    	 state = START_DOWNLOA_FOR_NORWIFI;
                    	 mHandler.sendEmptyMessage(START_DOWNLOA_FOR_NORWIFI);
                     }
                     
        			//重新创建一个LoadInfo对象记载下载器的具体信息  
                     LoadInfo loadInfo = new LoadInfo(fileSize, 0, urlstr);  
                     return loadInfo;  
        		 }
            	 //表存在，原文件也存在，就不用下载了
            	 infos = null;
            	 SharedPreferenceUtils.setPreference(SharedPreferenceUtils.DATA_CONFIG,
                         "JIAO_YI_DEBUG_ONLINE", Configs.URL_TYPE_SDCARD_UPDATE_H5);
            	 SharedPreferenceUtils.setPreference(SharedPreferenceUtils.DATA_CONFIG,
                         "JIAO_YI_UPDATE_VERSION", h5_versionNum);
            	 
            	 return null;
        	 }
        	 else{
	              //断点续传,由于中台不支持，暂时屏蔽
	             int size = 0;  
	             int compeleteSize = 0;  
		             /* //断点续传,由于中台不支持，暂时屏蔽
		              * for (DownloadInfo info : datas) {  
		                 compeleteSize += info.getCompeleteSize();  
		                 size += info.getEndPos() - info.getStartPos() + 1;  
//		                 DownloadInfo info = new DownloadInfo(infos,(threadcount - 1) * range, fileSize - 1, 0, urlstr,RomaSysConfig.h5_versionNum,1,upziplocalfile);
	                     infos.add(info); 
		             }*/  
	           //删除表记录
        		 deleteToDB(h5_versionNum);
        		 DownloadInfo info = new DownloadInfo(threadcount - 1,(threadcount - 1) * range, fileSize - 1, 0, 
        				 urlstr,h5_versionNum,1,"kds519");  
                 infos.add(info);  
                 //保存infos中的数据到数据库  
                 dao.saveInfos(infos);
                 
                 if(isWifiConnected(context)){
                	 state = START_DOWNLOAD_FOR_WIFI;
                	 mHandler.sendEmptyMessage(START_DOWNLOAD_FOR_WIFI);
                 }else{
                	 state = START_DOWNLOA_FOR_NORWIFI;
                	 mHandler.sendEmptyMessage(START_DOWNLOA_FOR_NORWIFI);
                 }
	             
                return new LoadInfo(size, compeleteSize, urlstr); 
             }
         }
     }  
     
     public LoadInfo getDownloaderInfors_beta() {  
    	 
    	 fileSize = RomaSysConfig.h5_fileSize_beta;
    	 int range = fileSize / threadcount; 
    	 infos = new ArrayList<DownloadInfo>();
    	 h5_versionNum = RomaSysConfig.h5_versionNum_beta;
    	 
    	//比较版本号高低
    	 if( !compareVersion(h5_versionNum) )
    	 {
    		 Logger.v("H5版本号比较", "已经是最新，无需下载！");
    		 return null;
    	 }
    	 
         if (isFirst(h5_versionNum)) 
         {  
             Logger.v("H5downloader", "首次下载当前这版本");  
             init();  
             for (int i = 0; i < threadcount - 1; i++) 
             {  
                 DownloadInfo info = new DownloadInfo(i, i * range, (i + 1)* range - 1, 0, urlstr,
                		 RomaSysConfig.h5_versionNum_beta,1,"kds519");
                 infos.add(info);  
             }  
             DownloadInfo info = new DownloadInfo(threadcount - 1,(threadcount - 1) * range, 
            		 fileSize - 1, 0, urlstr,h5_versionNum,1,"kds519");  
             infos.add(info);  
             //保存infos中的数据到数据库  
             H5Dao.getInstance(context).saveInfos(infos); 
             
             if(isWifiConnected(context)){
            	 state = START_DOWNLOAD_FOR_WIFI;
            	 mHandler.sendEmptyMessage(START_DOWNLOAD_FOR_WIFI);
             }else{
            	 state = START_DOWNLOA_FOR_NORWIFI;
            	 mHandler.sendEmptyMessage(START_DOWNLOA_FOR_NORWIFI);
             }
             
             //创建一个LoadInfo对象记载下载器的具体信息  
             LoadInfo loadInfo = new LoadInfo(fileSize, 0, urlstr);  
             return loadInfo;  
         } 
         else 
         {  
        	 List<DownloadInfo> datas = dao.getInfos(h5_versionNum);
        	 
        	 if(datas.size() == 0) 
        		 return null;
        	 
        	 int state = datas.get(0).getState();//Environment.getExternalStorageDirectory().toString()
        	 String upziplocalfile= context.getFilesDir()+"/"+datas.get(0).getUpzip_file();
        	 if(state == 2 )
        	 {
        		  //表存在，但原文件给删除了
            	 File localFile = new File(upziplocalfile);
            	 if(!localFile.exists())
            	 {
            		//删除表记录
            		 deleteToDB(h5_versionNum);
            		 DownloadInfo info = new DownloadInfo(threadcount - 1,(threadcount - 1) * range, fileSize - 1, 0, 
            				 urlstr,h5_versionNum,1,"kds519");  
                     infos.add(info);  
                     //保存infos中的数据到数据库  
                     dao.saveInfos(infos);
                     
                     if(isWifiConnected(context)){
                    	 state = START_DOWNLOAD_FOR_WIFI;
                    	 mHandler.sendEmptyMessage(START_DOWNLOAD_FOR_WIFI);
                     }else{
                    	 state = START_DOWNLOA_FOR_NORWIFI;
                    	 mHandler.sendEmptyMessage(START_DOWNLOA_FOR_NORWIFI);
                     }
                     
        			//重新创建一个LoadInfo对象记载下载器的具体信息  
                     LoadInfo loadInfo = new LoadInfo(fileSize, 0, urlstr);  
                     return loadInfo;  
        		 }
            	 //表存在，原文件也存在，就不用下载了
            	 infos = null;
            	 SharedPreferenceUtils.setPreference(SharedPreferenceUtils.DATA_CONFIG,
                         "JIAO_YI_DEBUG_ONLINE", Configs.URL_TYPE_SDCARD_UPDATE_H5);
            	 SharedPreferenceUtils.setPreference(SharedPreferenceUtils.DATA_CONFIG,
                         "JIAO_YI_UPDATE_VERSION", h5_versionNum);
            	 
            	 return null;
        	 }
        	 else{
	              //断点续传,由于中台不支持，暂时屏蔽
	             int size = 0;  
	             int compeleteSize = 0;  
		             /* //断点续传,由于中台不支持，暂时屏蔽
		              * for (DownloadInfo info : datas) {  
		                 compeleteSize += info.getCompeleteSize();  
		                 size += info.getEndPos() - info.getStartPos() + 1;  
//		                 DownloadInfo info = new DownloadInfo(infos,(threadcount - 1) * range, fileSize - 1, 0, urlstr,RomaSysConfig.h5_versionNum,1,upziplocalfile);
	                     infos.add(info); 
		             }*/  
	           //删除表记录
        		 deleteToDB(h5_versionNum);
        		 DownloadInfo info = new DownloadInfo(threadcount - 1,(threadcount - 1) * range, fileSize - 1, 0, 
        				 urlstr,h5_versionNum,1,"kds519");  
                 infos.add(info);  
                 //保存infos中的数据到数据库  
                 dao.saveInfos(infos);
                 
                 if(isWifiConnected(context)){
                	 state = START_DOWNLOAD_FOR_WIFI;
                	 mHandler.sendEmptyMessage(START_DOWNLOAD_FOR_WIFI);
                 }else{
                	 state = START_DOWNLOA_FOR_NORWIFI;
                	 mHandler.sendEmptyMessage(START_DOWNLOA_FOR_NORWIFI);
                 }
	             
                return new LoadInfo(size, compeleteSize, urlstr); 
             }
         }
     }
     
     /** 
      * 初始化 
      */  
     private void init() {  
         try {  
//             URL url = new URL(urlstr);  
//             HttpURLConnection connection = (HttpURLConnection) url.openConnection();  
//             connection.setConnectTimeout(5000);  
//             connection.setRequestMethod("GET");  
           //  fileSize = connection.getContentLength();  //取到文件大小，这个值要是服务器端没配置好的，是取不到的
   
            File file = new File(ziplocalfile);  
             if (!file.exists()) {  
                 file.createNewFile();  
             } 
             // 本地访问文件  
             RandomAccessFile accessFile = new RandomAccessFile(file, "rwd");  
             accessFile.setLength(fileSize);  
             accessFile.close();  
           //  connection.disconnect();  
         } catch (Exception e) {  
             e.printStackTrace();  
         }  
     }    
     /** 
      * 判断是否是第一次 下载 
      */  
     private boolean isFirst(String version) {  
         return H5Dao.getInstance(context).isHasInfors(version);  
     }  
   
     /** 
      * 利用线程开始下载数据 
      * 
      */  
     public void download() {
    	 
         if (infos != null && infos.size()>0) 
         {  
             for (DownloadInfo info : infos) {  
            	 downThread = new MyThread();
            	 downThread.init(info.getThreadId(), info.getStartPos(),  
                         info.getEndPos(), info.getCompeleteSize(),  
                         info.getUrl());
            	 downThread.start();  
             }  
         }  
     }  
   
     /** 
      * 解压缩
      */  
     public void upZip() {
    	 
         if (infos != null && infos.size()>0) 
         {  
            if(downThread != null){
             	 downThread.upZipH5();
            }
         }  
     }  
     
     
     public class MyThread extends Thread {  
         private int threadId;  
         private int startPos;  
         private int endPos;  
         private int compeleteSize;  
         private String urlstr;  
         private DecimalFormat decimalFormat;
         private int percent = 0;
         
         public MyThread(){
        	 
         }
         
         public void init(int threadId, int startPos, int endPos,  
                 int compeleteSize, String urlstr){
        	 this.threadId = threadId;  
             this.startPos = startPos;  
             this.endPos = endPos;  
             this.compeleteSize = compeleteSize;  
             this.urlstr = urlstr;  
             this.decimalFormat = new java.text.DecimalFormat("#.##");
         }
         
		@Override  
         public void run() {  
        	 
             HttpURLConnection connection = null;  
             RandomAccessFile randomAccessFile = null;  
             InputStream inputStream = null;  
             try {  
            	 state = DOWNLOADING;
                 URL url = new URL(urlstr);  
                 Logger.v("H5下载地址", urlstr);
                 connection = (HttpURLConnection) url.openConnection(); 
                 connection.setConnectTimeout(10*1000);  
                 connection.setReadTimeout(10*1000);
                 connection.setRequestMethod("GET");  
                 // 设置范围，格式为Range：bytes x-y;  
                 connection.setRequestProperty("Range", "bytes="+(startPos + compeleteSize) + "-" + endPos);  
   
                 randomAccessFile = new RandomAccessFile(ziplocalfile, "rwd");  
                 randomAccessFile.seek(startPos + compeleteSize);  
                 // 将要下载的文件写到保存在保存路径下的文件中  
                 connection.connect();//必须在url.getContent()前面
                //inputStream = connection.getInputStream();  在Android 4.0+ return null
                 inputStream = (InputStream) url.getContent();
                 byte[] buffer = new byte[1024*4];  
                 int length = -1;  
                 while ((length = inputStream.read(buffer)) != -1) {  
                     randomAccessFile.write(buffer, 0, length);  
                     compeleteSize += length;  
                     // 更新数据库中的下载信息  
                     H5Dao.getInstance(context).updataInfos(compeleteSize, 1,h5_versionNum,rootFileName); 
                     //Logger.v("H5下载进度", compeleteSize+",总大小 = "+fileSize);
                     int currentPercent = (int)(compeleteSize / (float)fileSize * 100.0f);
                     if(currentPercent > percent){
                    	 percent = currentPercent;
	                     float completeS = compeleteSize / 1024.0f;
	                     String completeUnit = "KB";
	                     if(completeS >= 1024){
	                    	 completeS /= completeS;
	                    	 completeUnit = "M";
	                     }
	                     float allSize = fileSize/1024.0f;
	                     String allSizeUnit = "KB";
	                     if(allSize >= 1024){
	                    	 allSize /= 1024;
	                    	 allSizeUnit = "M";
	                     }
	                     
	                     if(percent == 100)
	                    	 completeS = allSize;
	                     
	                     String details = "("+decimalFormat.format(completeS)+completeUnit+"/"+
	                    		 decimalFormat.format(allSize)+allSizeUnit+")";
	                     Logger.v("H5下载进度", ""+state+","+percent+details);
	                     
	                     Message msg = Message.obtain();
	                     msg.arg1 = percent;
	                     msg.obj = details;
	                     msg.what = DOWNLOADING;
	                     mHandler.sendMessage(msg);
                     }
                 } 
                 inputStream.close();
                 
                 Logger.v("H5下载进度", "H5下载完成！");
                 
                 state = DOWNLOAD_COMPELETE;
                 mHandler.sendEmptyMessage(DOWNLOAD_COMPELETE);
                 
                 //先保存一下压缩文件的存放路径，可能在下次启动app解压时需要用到
                 SharedPreferenceUtils.setPreference(SharedPreferenceUtils.DATA_CONFIG,
    	                 "JIAO_YI_H5_ZIP_PATH", ziplocalfile);
                 SharedPreferenceUtils.setPreference(SharedPreferenceUtils.DATA_CONFIG,
    	                 "JIAO_YI_H5_VERSION_NUM ", h5_versionNum);
                 
             } catch (Exception e) {  
                 e.printStackTrace(); 
                 Logger.v("H5下载进度异常", e.getMessage());
             }    
         }  
		
		/**
		 * 解压H5下载包
		 */
		public void upZipH5(){
			Logger.v("H5下载进度", "H5开始解压!");
			File zipFile = new File(ziplocalfile);
	    	//下载完成过，这步通知解压
	         try {
	        	Logger.v("H5下载进度", "解压路径="+context.getFilesDir().toString());
				upZipFile(zipFile, context.getFilesDir().toString());
				//删除压缩包
		         //更新数据库表的下载状态，这步才真正加入解压文件的路径
		         H5Dao.getInstance(context).updataInfos(compeleteSize,DOWNLOAD_UPZIPFILE_COMPELETE, 
		        		 h5_versionNum,rootFileName);
		         
		         //更改h5连接地址和版本号保存
		         SharedPreferenceUtils.setPreference(
		                 SharedPreferenceUtils.DATA_CONFIG, 
		                 "JIAO_YI_DEBUG_ONLINE", Configs.URL_TYPE_SDCARD_UPDATE_H5);
		         SharedPreferenceUtils.setPreference(SharedPreferenceUtils.DATA_CONFIG,
		                 "JIAO_YI_UPDATE_VERSION", h5_versionNum);
		         
		         state = DOWNLOAD_UPZIPFILE_COMPELETE;
		         mHandler.sendEmptyMessage(DOWNLOAD_UPZIPFILE_COMPELETE);
		         
		         Logger.v("H5下载进度", "H5解压完成!版本号= "+h5_versionNum);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				//解压异常时删除残余文件夹
      			delete(new File(context.getFilesDir()+"/"+rootFileName));
      		    //解压异常就清除一下保存的压缩文件路径
   	             SharedPreferenceUtils.setPreference(SharedPreferenceUtils.DATA_CONFIG,
   	                 "JIAO_YI_H5_ZIP_PATH", "");
			}finally{
            	//删除压缩包
            	if(zipFile.exists()){ 
            		zipFile.delete();
            		//解压完了就清除一下保存的压缩文件路径
       	            SharedPreferenceUtils.setPreference(SharedPreferenceUtils.DATA_CONFIG,
       	                 "JIAO_YI_H5_ZIP_PATH", "");
            	}
            }
	     }
     }  
     
     /**
		 * 解压H5下载包
		 */
		public void resetUpZipH5(){
			Logger.v("H5下载进度", "H5开始解压!");
			File zipFile = new File(ziplocalfile);
	    	//下载完成过，这步通知解压
	         try {
				upZipFile(zipFile, context.getFilesDir().toString());
				//删除压缩包
		         //更新数据库表的下载状态，这步才真正加入解压文件的路径
		        // H5Dao.getInstance(context).updataInfos(compeleteSize,DOWNLOAD_UPZIPFILE_COMPELETE, 
		        //		 h5_versionNum,rootFileName);
		         
		         //更改h5连接地址和版本号保存
		         SharedPreferenceUtils.setPreference(
		                 SharedPreferenceUtils.DATA_CONFIG, 
		                 "JIAO_YI_DEBUG_ONLINE", Configs.URL_TYPE_SDCARD_UPDATE_H5);
		         SharedPreferenceUtils.setPreference(SharedPreferenceUtils.DATA_CONFIG,
		                 "JIAO_YI_UPDATE_VERSION", 
		                 SharedPreferenceUtils.getPreference(SharedPreferenceUtils.DATA_CONFIG,
		    	                 "JIAO_YI_H5_VERSION_NUM ", ""));
		         
		         state = DOWNLOAD_UPZIPFILE_COMPELETE;
		         mHandler.sendEmptyMessage(DOWNLOAD_UPZIPFILE_COMPELETE);
		         
		         Logger.v("H5下载进度", "H5解压完成!");
		         
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				//解压异常时删除残余文件夹
   			    delete(new File(context.getFilesDir()+"/"+rootFileName));
   		        //解压异常就清除一下保存的压缩文件路径
                SharedPreferenceUtils.setPreference(SharedPreferenceUtils.DATA_CONFIG,
                   "JIAO_YI_H5_ZIP_PATH", "");
                SharedPreferenceUtils.setPreference(SharedPreferenceUtils.DATA_CONFIG,
   	                 "JIAO_YI_H5_VERSION_NUM ", "");
			}finally{
         	//删除压缩包
         	if(zipFile.exists()){ 
         		zipFile.delete();
         		//解压完了就清除一下保存的压缩文件路径
	            SharedPreferenceUtils.setPreference(SharedPreferenceUtils.DATA_CONFIG,
	                 "JIAO_YI_H5_ZIP_PATH", "");
	            SharedPreferenceUtils.setPreference(SharedPreferenceUtils.DATA_CONFIG,
	   	                 "JIAO_YI_H5_VERSION_NUM ", "");
         	}
         }
	         
	     }
     
     
     /**
      * 删除数据库中版本号对应的下载器信息  
      * @param urlstr
      */
     private void deleteToDB(String version) {  
    	 dao.delete(version);  
     }  
     
     //设置暂停  
     public void pause() {  
         state = PAUSE;  
     }  
     
     //重置下载状态  
     public void reset() {  
         state = INIT;  
     }  
     
     
     /**
      * 解压缩功能.
      * 将zipFile文件解压到folderPath目录下.
      * @throws Exception
  */
      private int upZipFile(File zipFile, String folderPath) throws Exception{
         //删除旧的解压文件
    	 File file = new File(context.getFilesDir()+"//"+"kds519");////　context.getFilesDir()
    	  if(file.exists()){
    		   delete(file);
    	  }
    	 File outFile = null;
    	 boolean isRoot = true;
    	  ////context.getf
          ZipFile zfile=new ZipFile(zipFile);
          Enumeration zList=zfile.entries();
          ZipEntry ze=null;
          byte[] buf=new byte[4*1024];
          while(zList.hasMoreElements()){
              ze=(ZipEntry)zList.nextElement();    
              if(ze.isDirectory()){
            	 if(isRoot)
            	  {
            		 rootFileName = ze.getName().replace("/", "");//暂时写死，后面注释开来
            		 isRoot = false;
            	  }
                 // Logger.d("H5", "压缩 ze.getName() = "+ze.getName());
                  String dirstr = folderPath + ze.getName().replace("../","");
                  //dirstr.trim();
                  dirstr = new String(dirstr.getBytes("8859_1"), "GB2312");
               //   Logger.d("upZipFile", "str = "+dirstr);
                  File f=new File(dirstr);
                  f.mkdir();
                  continue;
              }
             // Logger.d("H5", "压缩  ze.getName() = "+ze.getName());
              outFile = getRealFileName(folderPath, ze.getName().replace("../",""));
              OutputStream os=new BufferedOutputStream(new FileOutputStream(outFile));
              InputStream is=new BufferedInputStream(zfile.getInputStream(ze));
              int readLen=0;
              //制造异常调试结果
             // Integer.parseInt("test");
              while ((readLen=is.read(buf, 0, 1024))!=-1) {
                  os.write(buf, 0, readLen);
               }
              is.close();
              os.close();    
            }
            
            zfile.close();
    	 
    	
          return 0;
      }
  
      /**
      * 给定根目录，返回一个相对路径所对应的实际文件名.
      * @param baseDir 指定根目录
      * @param absFileName 相对路径名，来自于ZipEntry中的name
      * @return java.io.File 实际的文件
      */
      private  File getRealFileName(String baseDir, String absFileName){
          String[] dirs=absFileName.split("/");
          File ret=new File(baseDir);
          String substr = null;
          if(dirs.length>1){
              for (int i = 0; i < dirs.length-1;i++) {
                  substr = dirs[i];
                  try {
                      //substr.trim();
                      substr = new String(substr.getBytes("8859_1"), "GB2312");
                      
                  } catch (UnsupportedEncodingException e) {
                      e.printStackTrace();
                  }
                  ret=new File(ret, substr);
                  
              }
              Logger.d("H5", "upZipFile  1ret = "+ret);
              if(!ret.exists())
                  ret.mkdirs();
              substr = dirs[dirs.length-1];
              try {
                  //substr.trim();
                  substr = new String(substr.getBytes("8859_1"), "GB2312");
                  Logger.d("H5", "upZipFile substr = "+substr);
              } 
              catch (UnsupportedEncodingException e) {
                  e.printStackTrace();
              }
              
              ret=new File(ret, substr);
              Logger.d("H5", "upZipFile 2ret = "+ret);
              return ret;
          }
          return ret;
      }
     
     
    /**
     * 递归删除文件及文件夹
     * @param file
     */
  	private  void delete(File file) {
  		if (file.isFile()) {
  			file.delete();
  			return;
  		}

  		if(file.isDirectory()){
  			File[] childFiles = file.listFiles();
  			if (childFiles == null || childFiles.length == 0) {
  				file.delete();
  				return;
  			}
  	
  			for (int i = 0; i < childFiles.length; i++) {
  				delete(childFiles[i]);
  			}
  			file.delete();
  		}
  	}
  	
  	
  /**
   * 是否连接WIFI
   * @param context
   * @return
   */
    private  boolean isWifiConnected(Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if(wifiNetworkInfo.isConnected())
        {
            return true ;
        }
     
        return false ;
    }
  	
    /**
     * 
     * @date   2015年8月13日    下午3:49:50 
     * @Description:但下发H5版本号同时大于内置版本号，和已经下载更新后的版本号才会去下载新的更新包
     * @author lichuan
     * @param @return    
     * @return boolean  返回true表示下发的版本号为最高，否则为false。
     */
    private boolean compareVersion(String version){
    	
   	 if(stringCompareTo(version, 
   			 H5Info.getAssetsCurrVersion(context)) > 0 &&//后台版本与assets版本的比较
   			stringCompareTo(version, 
   					SharedPreferenceUtils.getPreference(
   							SharedPreferenceUtils.DATA_CONFIG,"JIAO_YI_UPDATE_VERSION", "")) > 0)//后台版本与本地保存的版本比较
   	 {
   		 return true;
   	 }
    	
    	return false;
    }
    
    /**
     * 比较两个字符串的大小
     * @param firstStr
     * @param secStr
     * @return
     */
    @SuppressLint("ShowToast")
	private int stringCompareTo(String firstStr, String secStr){
    	
    	Logger.v("H5版本号比较", "服务端版本："+firstStr+",当前版本："+secStr);
    	
    	//if(Logger.getDebugMode() && StringUtils.isEmpty(firstStr))
    	//	Toast.makeText(context, "[警告]后台没有配置最新版本的H5交易模块！", Toast.LENGTH_LONG).show();
    	
    	if(StringUtils.isEmpty(firstStr) && StringUtils.isEmpty(secStr)){
    		return 0;
    	}
    	if(StringUtils.isEmpty(firstStr))
    		return -1;
    	if(StringUtils.isEmpty(secStr))
    		return 1;
//    	if(firstStr.length() > secStr.length())
//    		return 1;
//    	else if(firstStr.length() < secStr.length())
//    		return -1;
    	
    	return firstStr.compareTo(secStr);
    }
 }  
