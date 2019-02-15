package com.romalibs.utils.cache;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import com.romalibs.utils.Logger;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

@SuppressLint("NewApi")
public class FilesDownloadTask extends AsyncTask<String, Void, Object>{

	private String downStatus = "LOADING";
	private File mFileSavePath;
	private String mTag;
	
	public FilesDownloadTask(String tag, File fileSavePath, 
			OnDownloadListener onDownloadListener) {
		
		mTag = tag;
    	mFileSavePath = fileSavePath;
    	mOnDownloadListener = onDownloadListener;
    }
	
	public interface OnDownloadListener {  
		public void onDownloading(int progress);
        public void onDownloadComplete(String tag);
        public void onDownloadError(String tag);
    }
	 
	private OnDownloadListener mOnDownloadListener;
	
    protected Object doInBackground(String... addresses) {

        try
        {
            URL url = new URL(addresses[0]);
            // 创建连接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            
            conn.connect();
            // 获取文件大小
            int length = conn.getContentLength();
            // 创建输入流
            InputStream is = conn.getInputStream();
            
            //处理文件路径不存在的问题
            String dirFilePath = mFileSavePath.getPath();
            String childDirPath = "";
            String[] path = dirFilePath.split("/");
            for(int i = 0; i < path.length-1;i++){
                if(path[i].contains("."))
                    continue;
                
                childDirPath += "/" + path[i];
                
                File file = new File(childDirPath);
                if(!file.exists()) {//考虑已经存在同样名字的文件或者目录，
                    file.mkdir();
                    file.setExecutable(true, false);
                    file.setReadable(true, false);
                    file.setWritable(true, false);
                }
            }
            
            FileOutputStream fos = new FileOutputStream(mFileSavePath); 
            // 缓存
            byte buf[] = new byte[1024];
            int count = 0;
            // 写入到文件中
            do
            {
                //用于取消下载的标志
//                    if(cancelUpdate){
//                        mDowloadHandler.sendEmptyMessage(APP_DOWNLOAD_CANCEL);
//                        break;
//                    }
                
                int numread = is.read(buf);
                count += numread;
                
                // 计算进度条位置
                int progress = (int) (((float) count / length) * 100);
//                float tempSpace = count / 1024.0f / 1024.0f;
//                String downloadSpace = "";
//                try{
//                    downloadSpace = String.format("%.3f",tempSpace)+"M";// > 1.0f ? (tempSpace+"M") : (count / 1024.0f+"KB");
//                }catch(Exception e){
//                    
//                }
                // 监听下载进度
                if(mOnDownloadListener != null)
                	mOnDownloadListener.onDownloading(progress);
                
                if (numread <= 0)
                {
                	downStatus = "SUCCESS";
                    break;
                }
                
                // 写入文件
                fos.write(buf, 0, numread);
            } while (true);// 点击取消就停止下载.
            fos.close();
            is.close();
            
        }catch(Exception e){
        	downStatus = "ERROR";
        }
    
        Logger.d("tag", "0-===== addresses = "+addresses[0]);
        
        return null;
    }

    // Task执行完毕，返回bitmap
    @Override
    protected void onPostExecute(Object result) {
        // Set bitmap image for the result
    	if(mOnDownloadListener != null){
    		if(downStatus.equals("SUCCESS"))
    			mOnDownloadListener.onDownloadComplete(mTag);
    		else
    			mOnDownloadListener.onDownloadError(mTag);
    	}
    }
}
