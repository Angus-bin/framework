package com.romawaylibs.videoplayer;

import com.romaway.libs.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

/**
 * 视频播放管理类
 * @author wanlh
 *
 */
public class VideoPlayMgr{
    private JCVideoPlayer mSuperVideoPlayer;
//    private Activity activity;
    private LayoutInflater inflater;
	private RelativeLayout parentView;
	
    public VideoPlayMgr(Activity activity){
//    	this.activity = activity;
    	inflater = LayoutInflater.from(activity);
    }
    

    /**
     * 获取是播放器视图
     * @return
     */
    @SuppressLint("InlinedApi")
	public View getView(){
    	
		if(parentView == null){  
			parentView = (RelativeLayout)inflater.inflate(R.layout.roma_libs_video_activity_main, null);
			parentView.setLayoutParams(new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.WRAP_CONTENT, 
					RelativeLayout.LayoutParams.WRAP_CONTENT));
	      //添加视频播放控件并启动服务
	        mSuperVideoPlayer = (JCVideoPlayer) parentView.findViewById(R.id.videoplayer);
//	        setPlayerHeight(480);
		}
        
		return parentView;
	}
    
    public void setPlayerHeight(int height) {
    	RelativeLayout.LayoutParams lp = (LayoutParams) mSuperVideoPlayer.getLayoutParams();
        lp.width = LayoutParams.MATCH_PARENT;
        lp.height = height;
        mSuperVideoPlayer.setLayoutParams(lp);
    }
    
    /**
     * 设置视频播放组件参数
     * @param url 视频源的 url 地址
     * @param thumb 视频初始化显示的图片 url 地址
     * @param title 视频的 title 文本内容
     * @param ifShowTitle 是否需要显示title
     */
    public void setVideoUp(String url, String thumb, String title, boolean ifShowTitle) {
    	mSuperVideoPlayer.setUp(url, thumb, title, ifShowTitle);
    }
    
    /**
     * 设置视频播放组件参数
     * @param url 视频源的 url 地址
     * @param thumb 视频初始化显示的图片 url 地址
     * @param title 视频的 title 文本内容
     */
    public void setVideoUp(String url, String thumb, String title) {
    	mSuperVideoPlayer.setUp(url, thumb, title);
    }
    
    /**
     * 开始视频播放
     */
    public void startPlay(){
    	mSuperVideoPlayer.play();
    }
    
    /**
     * 页面暂停时回调的接口
     */
    public void releaseAllVideos() {
    	JCVideoPlayer.releaseAllVideos();
    }
}

