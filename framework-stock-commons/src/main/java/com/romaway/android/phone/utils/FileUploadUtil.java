package com.romaway.android.phone.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.UUID;

import org.apache.http.HttpStatus;

public class FileUploadUtil {

	private static FileUploadUtil fileUploadUtil;
    private static final int TIME_OUT = 10 * 1000; // 超时时间  
    private static final String CHARSET = "utf-8"; // 设置编码  
    public static final String SUCCESS = "1";
    public static final String FAILURE = "0";
    private String address;
    private Map<String, String> params;
    private Map<String, File> files;
    private FileNetListener listener;
    private UploadThread thread;

	public static FileUploadUtil getInstance(){
		if (fileUploadUtil == null) {
			fileUploadUtil = new FileUploadUtil();
		}
		return fileUploadUtil;
	}
	
	public void upload(String address, Map<String, String> params, Map<String, File> files, FileNetListener listener){
		this.address = address;
		this.params = params;
		this.files = files;
		this.listener = listener;
			thread = new UploadThread();
		if (!thread.isAlive()) {
			thread.start();
		}
	}
	
	private class UploadThread extends Thread{
		@Override
		public void run() {
			String BOUNDARY = UUID.randomUUID().toString(); // 边界标识 随机生成  
	        String PREFIX = "--", LINE_END = "\r\n"; 
	        String CONTENT_TYPE = "multipart/form-data"; // 内容类型 
	        try {
				URL url = new URL(address);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
	            conn.setReadTimeout(TIME_OUT);
	            conn.setConnectTimeout(TIME_OUT);
	            conn.setDoInput(true); // 允许输入流 
	            conn.setDoOutput(true); // 允许输出流 
	            conn.setUseCaches(false); // 不允许使用缓存 
	            conn.setRequestMethod("POST"); // 请求方式 
	            conn.setRequestProperty("Charset", CHARSET); // 设置编码 
	            conn.setRequestProperty("connection", "keep-alive");
	            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);
	            //拼装文本类型的参数
	            StringBuilder txtSB= new StringBuilder();
	            for (Map.Entry<String, String> entry: params.entrySet()) {
	            	txtSB.append(PREFIX);
	            	txtSB.append(BOUNDARY);
	            	txtSB.append(LINE_END);
	            	txtSB.append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"" + LINE_END);
	            	txtSB.append("Content-Type: text/plain; charset=" + CHARSET + LINE_END);
	            	txtSB.append(LINE_END);
	            	txtSB.append(entry.getValue());
	            	txtSB.append(LINE_END);
				}
	            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
	            dos.write(txtSB.toString().getBytes());
	            if (files != null) {
					for (Map.Entry<String, File> file : files.entrySet()) {
						StringBuffer sb = new StringBuffer();
						sb.append(PREFIX);
						sb.append(BOUNDARY);
						sb.append(LINE_END);
						/** 
		                 * 这里重点注意： name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件
		                 * filename是文件的名字，包含后缀名的 比如:abc.png
		                 */  
		                sb.append("Content-Disposition: form-data; name=\"" + file.getKey() + "\"; filename=\"" + file.getValue().getName() + "\"" + LINE_END);
		                sb.append("Content-Type: multipart/form-data; charset=" + CHARSET + LINE_END);
		                sb.append(LINE_END);
		                dos.write(sb.toString().getBytes());
		                InputStream is = new FileInputStream(file.getValue());
		                byte[] bytes = new byte[1024];
		                int len = 0;
		                while ((len = is.read(bytes)) != -1) {
		                    dos.write(bytes, 0, len);
		                }
		                is.close();
		                dos.write(LINE_END.getBytes());
					}
					byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END).getBytes();
	                dos.write(end_data);
	                dos.flush();
	                int respCode = conn.getResponseCode();
	                //200、401、403
	                if (respCode == HttpStatus.SC_OK || respCode == HttpStatus.SC_UNAUTHORIZED || respCode == HttpStatus.SC_FORBIDDEN) {
	                	InputStream input = conn.getInputStream();
	                	InputStreamReader isReader = new InputStreamReader(input);
	                	BufferedReader bufferedReader = new BufferedReader(isReader);
	                	String line = null;
	                	String data = "";
	                	while (( line =bufferedReader.readLine()) != null) {
							data += line;
						}
	                	dos.close();
	                	conn.disconnect();
	                	listener.onSuccess(data);
					}else {
						listener.onFailure();
					}
				}
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public interface FileNetListener{
		void onSuccess(String result);
		void onFailure();
	}
}
