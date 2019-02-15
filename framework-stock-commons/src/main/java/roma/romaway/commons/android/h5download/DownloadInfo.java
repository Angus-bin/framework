package roma.romaway.commons.android.h5download;
/** 
 *下载信息的实体类 
 */ 
public class DownloadInfo {
	
	/**
	 * 下载器id 用于多线程下载用的
	 */
	private int threadId;
	/**
	 * 开始点,默认为0
	 */
    private int startPos; 
    /**
     * 结束点，总长度
     */
    private int endPos; 
    /**
     * 完成度,偏移量
     */
    private int compeleteSize;
    /**
     * 下载网络标识  
     */
    private String url;
    /**
     * 当前下载的版本号
     */
    private String version;
    /**
     * 下载状态，0为暂停，1为下载中，2为下载成功。
     */
    private int state;
    /**
     * 存放本地压缩文件路径
     */
    private String upzip_file;
    
    
  
	public DownloadInfo(int threadId, int startPos, int endPos,  
            int compeleteSize,String url,String version,int state,String upzip_file) {  
        this.threadId = threadId;  
        this.startPos = startPos;  
        this.endPos = endPos;  
        this.compeleteSize = compeleteSize;  
        this.url=url;  
        this.version = version;
        this.state = state;
        this.upzip_file = upzip_file;
    }  
    public DownloadInfo() {  
    }  
    public String getUrl() {  
        return url;  
    }  
    public void setUrl(String url) {  
        this.url = url;  
    }  
    public int getThreadId() {  
        return threadId;  
    }  
    public void setThreadId(int threadId) {  
        this.threadId = threadId;  
    }  
    public int getStartPos() {  
        return startPos;  
    }  
    public void setStartPos(int startPos) {  
        this.startPos = startPos;  
    }  
    public int getEndPos() {  
        return endPos;  
    }  
    public void setEndPos(int endPos) {  
        this.endPos = endPos;  
    }  
    public int getCompeleteSize() {  
        return compeleteSize;  
    }  
    public void setCompeleteSize(int compeleteSize) {  
        this.compeleteSize = compeleteSize;  
    }  
  
    public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	
  public String getUpzip_file() {
		return upzip_file;
	}
	public void setUpzip_file(String upzip_file) {
		this.upzip_file = upzip_file;
	}
	
	@Override  
    public String toString() {  
        return "DownloadInfo [threadId=" + threadId  
                + ", startPos=" + startPos + ", endPos=" + endPos  
                + ", compeleteSize=" + compeleteSize +"version="+version+"state = "+state+"]";  
    }  
}  
