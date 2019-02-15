package roma.romaway.commons.android.config;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.romaway.commons.log.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import roma.romaway.commons.android.config.ConfigsDownloader.OnDownloadCompleteListener;

/**
 * 下载文件线程
 * 
 */
public class DownloadConfigFileThread extends Thread
{
    private String downurl;
    private File configFile;
    private OnDownloadCompleteListener mOnDownloadCompleteListener;
    private Context mContext;
    
    public DownloadConfigFileThread(Context context, String downurl, File configFile, 
            OnDownloadCompleteListener onDownloadCompleteListener){
        mContext = context;
        this.downurl = downurl;
        this.configFile = configFile;
        this.mOnDownloadCompleteListener = onDownloadCompleteListener;
    }
    @SuppressLint("NewApi")
    @Override
    public synchronized void run()
    {
        //Logger.d("downloadApkThread", "downloadApkThread downurl:"+downurl);
        try
        {
            //将链接中的中文进行URLEncoder编码，防止图片链接中包含中文会下载失败问题
            String regexStr = "[\\u4E00-\\u9FA5]";
            Matcher matcher = Pattern.compile(regexStr).matcher(downurl);
            while (matcher.find()){
                String temp = matcher.group();
                downurl = downurl.replace(temp, URLEncoder.encode(temp, "UTF-8"));
            }
            URL url = new URL(downurl);
            // 创建连接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            
            //Log.d("tag", "下载模块： 进入run statusCode = "+statusCode);
            conn.connect();
            //Logger.d("downloadApkThread", "downurl:"+downurl);
            // 获取文件大小
            int length = conn.getContentLength();
            //Log.d("tag", "下载模块： 文件大小length = "+length);
            // 创建输入流
            InputStream is = conn.getInputStream();
            
            //处理文件路径不存在的问题
            String dirFilePath = configFile.getPath();
            String childDirPath = "";
            String[] path = dirFilePath.split("/");
            for(int i = 0; i < path.length-1;i++){
                if(path[i].contains("."))
                    continue;
                
                childDirPath += "/" + path[i];
                
                File file = new File(childDirPath);
                if(!file.exists()) {//考虑已经存在同样名字的文件或者目录，
                    Logger.d("tag", "DownloadConfigFileThread mkdir newPath:"+file.getPath());
                    file.mkdir();
                    file.setExecutable(true, false);
                    file.setReadable(true, false);
                    file.setWritable(true, false);
                }
            }
            
            //Logger.d("downloadApkThread", "下载模块： 进入run-3 configFile = "+configFile.getPath());
            FileOutputStream fos = new FileOutputStream(configFile); 
            //Log.d("tag", "下载模块： 进入run-4");
            // 缓存
            byte buf[] = new byte[1024];
            int count = 0;
            //StringBuffer strBuf = new StringBuffer();
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
                
                //filesiz = Math.round((float) count / 1000000*100);
                int progress = (int) (((float) count / length) * 100);
                float tempSpace = count / 1024.0f / 1024.0f;
                String downloadSpace = "";
                try{
                    downloadSpace = String.format("%.3f",tempSpace)+"M";// > 1.0f ? (tempSpace+"M") : (count / 1024.0f+"KB");
                }catch(Exception e){
                    
                }
                
                //Logger.d("downloadApkThread", "下载中... numread = "+numread+ ",downloadSpace = "+downloadSpace);
                
                if (numread <= 0)
                {
                  //下载完成
                    
                    mHandler.removeMessages(0);
                    Message msg = Message.obtain();
                    //msg.obj = strBuf.toString();
                    msg.what = 0;
                    mHandler.sendMessage(msg);
                    break;
                }
                
                //strBuf.append(EncodingUtils.getString(buf, 0, numread, "UTF-8"));
                
               //Logger.d("tag", "EncodingUtils.getString result = "+  
                //       EncodingUtils.getString(buf, 0, numread, "UTF-8")+",length:"+buf.length);
                // 写入文件
                fos.write(buf, 0, numread);
            } while (true);// 点击取消就停止下载.
            fos.close();
            is.close();
            
        }catch(Exception e){
            mHandler.removeMessages(1);
            mHandler.sendEmptyMessage(1);
            //e.printStackTrace();
        }
    }
    
    public void onDownloadComplete(){
        if(mOnDownloadCompleteListener != null){
            mOnDownloadCompleteListener.onDownloadComplete();
        }
    }
    private Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            
            switch(msg.what){
                case 0:
                 // 下载完成
                    if(mOnDownloadCompleteListener != null){
                        mOnDownloadCompleteListener.onDownloadComplete();
                        //Logger.d("downloadApkThread", "下载结束  OnDownloadComplete");
                    }
                    break;
                case 1:
                 // 下载完成
                    if(mOnDownloadCompleteListener != null){
                        mOnDownloadCompleteListener.onDownloadError();
                        //Logger.d("downloadApkThread", "下载错误  onDownloadError");
                    }
                    
                    break;
            }
        }
    };
}
