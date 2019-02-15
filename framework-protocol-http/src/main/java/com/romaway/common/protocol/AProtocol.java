/**
 * 
 */
package com.romaway.common.protocol;

import android.support.annotation.Keep;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;

import org.json.JSONException;

import com.romaway.common.protocol.coder.KCodeEngine;
import com.romaway.commons.log.Logger;

/**
 * 业务协议
 * 
 * @author duminghui 修改日志： 2013.12.24 祝丰华 增加服务器错误码及读取方法getServerErrCode()
 */
@Keep
public abstract class AProtocol {
	private EProtocolStatus status = EProtocolStatus.wait;

	/**
	 * 标记
	 */
	private String flag;

	/**
	 * 服务端错误码
	 */
	public int serverErrCode = 0;
	/**
	 * 服务端错误码
	 */
	public String serverErrCodeStr;

	/**
	 * 系统返回的MSG
	 */
	public String serverMsg;

	/**
	 * 发送至服务器的数据
	 */
	private byte[] sendData;
	/**
	 * 服务器返回的数据
	 */
	private byte[] serverReceiveData;

	/**
	 * 用于解析消息包体的数据
	 */
	private byte[] receiveData = new byte[] {};

	/**
	 * 消息主命令号
	 */
	private short nMFuncNo;

	/**
	 * 消息子命令号
	 */
	private short nSFuncNo;
	/**
	 * 消息版本
	 */
	private int cmdVersion = 0;
	/**
	 * 服务器返回的消息版本号
	 */
	private int cmdServerVersion = -1;
	/**
	 * 压缩标志
	 */
	private byte bCompress;
	/**
	 * 加密标志
	 */
	private byte bEncrypt;
	/**
	 * userID
	 */

	// private byte[] userId = new byte[8];
	public static byte[] userId = new byte[8];

	/**
	 * 服务器返回消息时是否解析，默认为true
	 */
	private boolean isDecodeOnReceive = true;

	/**
	 * 编码部件
	 */
	private KCodeEngine codeEngine = null;
	@SuppressWarnings("rawtypes")
	private AProtocolCoder coder = null;

	/**
	 * 是否是自动刷新请求
	 */
	private boolean autoRefreshStatus = false;

	/**
	 * 子功能url地址
	 */
	public String subFunUrl;
	/**
	 * 是否是json请求
	 */
	public boolean isJson;
	/**
	 * 是否是Buffer请求
	 */
	public boolean isBuffer;
	
	/**
	 * 是否是通过网络加载的数据
	 */
	public boolean isNetLoad = true;
	
	/**
	 * 是否每次都取缓存数据
	 */
	public boolean isLoadCache = true;
	
	/**
	 * 有缓存数据时是否进行网络数据请求标记，默认会请求
	 */
	public boolean isHasCacheNetLoadEnable = true;
	
	/**
	 * 是否内存中有数据
	 */
	public boolean isHasMemory = false;
	/**
	 * 是否加载全部数据
	 */
	public boolean isDataLoadFull = false;
	
	
	/**
	 * json请求cookie
	 */
	private HashMap<String, String> sendCookie;
	/**
	 * json请求头
	 */
	private HashMap<String, String> sendHeader;
	/**
	 * json响应头键
	 */
	private String[] responseHeader;
	/**
	 * json响应Header内容
	 */
	private HashMap<String, String> respHeader;
	
	/**
	 * 
	 * @param flag
	 * @param autoRefreshStatus 是否自动刷新
	 */
	public AProtocol(String flag, boolean autoRefreshStatus){
		this.flag = flag;
		this.autoRefreshStatus = autoRefreshStatus;
	}

	/**
	 * 
	 * @param flag
	 * @param nMFuncNo
	 * @param nSFuncNo
	 * @param cmdVersion
	 * @param isCompress
	 *            是否压缩
	 * @param isEncrypt
	 *            是否加密
	 */
	public AProtocol(String flag, short nMFuncNo, short nSFuncNo,
			int cmdVersion, boolean isCompress, boolean isEncrypt) {
		this(flag, nMFuncNo, nSFuncNo, cmdVersion, isCompress, isEncrypt, false);
	}

	public AProtocol(String flag, short nMFuncNo, short nSFuncNo,
			int cmdVersion, boolean isCompress, boolean isEncrypt,
			boolean autoRefreshStatus) {
		this.flag = flag;
		this.nMFuncNo = nMFuncNo;
		this.nSFuncNo = nSFuncNo;
		this.cmdVersion = cmdVersion;
		if (isCompress) {
			bCompress = KCodeEngine.CT_GZIP;
		} else {
			bCompress = KCodeEngine.CT_NONE;
		}
		if (isEncrypt) {
			bEncrypt = KCodeEngine.ET_3DES;
		} else {
			bEncrypt = KCodeEngine.ET_NONE;
		}
		this.autoRefreshStatus = autoRefreshStatus;
	}

	public String getFlag() {
		return flag;
	}

	public short getnMFuncNo() {
		return nMFuncNo;
	}

	public void setnMFuncNo(short nMFuncNo) {
		this.nMFuncNo = nMFuncNo;
	}

	public short getnSFuncNo() {
		return nSFuncNo;
	}

	public EProtocolStatus getStatus() {
		return status;
	}

	public void setStatus(EProtocolStatus status){
		this.status = status;
	}
	/**
	 * 服务器返回的信息
	 * 
	 * @return
	 */
	public String getServerMsg() {
		return serverMsg;
	}

	public int getServerErrorCode() {
		return serverErrCode;
	}

	/**
	 * 网络发送数据<br>
	 * 在获取前需要encode()
	 * 
	 * @return
	 */
	public byte[] getSendData() {
		return sendData;
	}

	/**
	 * 解析后的服务器返回消息
	 * 
	 * @return
	 */
	public byte[] getReceiveData() {
		return receiveData;
	}

	/**
	 * 获取请求时的协议版本号
	 * 
	 * @return
	 */
	public int getCmdVersion() {
		return cmdVersion;
	}

	/**
	 * 服务器返回的命令版本号
	 * 
	 * @return
	 */
	public int getCmdServerVersion() {
		if (this.cmdServerVersion == -1) {
			return this.cmdVersion;
		}
		return cmdServerVersion;
	}

	/**
	 * 设置服务器返回的数据
	 * 
	 * @param serverReceiveData
	 */
	public void setServerReceiveData(byte[] serverReceiveData) {
		this.serverReceiveData = serverReceiveData;
	}

	/**
	 * 本次请求协议是否来自自动刷新，若是,则返回true,否则，返回false
	 * 
	 * @return
	 */
	public boolean isAutoRefresh() {
		return this.autoRefreshStatus;
	}

	/**
	 * 设置本次协议请求自动刷新状态，即本次请求是否是来自自动刷新，
	 * 
	 * @param status
	 *            true:来自自动刷新，false:来自非自动刷新
	 */
	public void setAutoRefresh(boolean status) {
		this.autoRefreshStatus = status;
	}

	@SuppressWarnings("unchecked")
	public final void encode() {
		if (coder == null) {
			coder = ProtocolCoderMgr.getInstance().getCoder(this.getClass());
		}
		if (isJson||isBuffer) {
			sendData = coder.encode(this);
		} else {
			if (codeEngine == null) {
				codeEngine = KCodeEngine.create();
			}
			sendData = codeEngine.encode(nMFuncNo, nSFuncNo,
					KCodeEngine.getSessionID(), cmdVersion, bCompress,
					bEncrypt,
					/* userId */KCodeEngine.getUserID(), coder.encode(this));
		}

	}

	public AProtocol readCache(String key){
		try {
			return coder.readCache(key, this);
		} catch (ProtocolParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public void saveCache(String key, AProtocol protocol){
		try {
			//System.out.println("3----- protocolFlag::"+protocol.getFlag());
			coder.saveCache(key, protocol);
			//System.out.println("end----- protocol");
		} catch (ProtocolParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * decode前需要设置serverReceiveData
	 */
	public final void decode() {

		if (isJson||isBuffer) {
			receiveData = serverReceiveData;
		} else {
			try {
				byte[][] datas = codeEngine.decode(serverReceiveData);
				cmdServerVersion = datas[0][0];
				receiveData = datas[1];
				serverErrCode = 0;

			} catch (ServerException ex) {
				status = EProtocolStatus.serverError;
				Logger.d("NetParser",
						String.format("ServerException:%s", ex.toString()));
				serverMsg = ex.getMessage();// ex.toString();
				serverErrCode = ex.getErrorCode();
				return;
			} catch (Exception e) {
				// 有时候会空指针，不知道为什么，暂时捕获起来 by qinyn

				status = EProtocolStatus.serverError;
				Logger.d("NetParser",
						String.format("ServerException:%s", e.toString()));
				serverMsg = e.getMessage();

				return;
			}
		}

		try {
			coder.decode(this);
			status = EProtocolStatus.success;
		} catch (ProtocolParserException e) {
			status = EProtocolStatus.parseError;
			
		}
	}

	/**处理解析json异常的信息*/
	public String getJSONExceptionInfo(JSONException e){
		StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		System.out.println(this.getClass().getName()+":"+sw.toString().toUpperCase());
		return this.getClass().getName()+":"+sw.toString().toUpperCase();
	}
	
	public HashMap<String, String> getSendHeader() {
		return sendHeader;
	}

	public void setSendHeader(HashMap<String, String> sendHeader) {
		this.sendHeader = sendHeader;
	}

	public String[] getResponseHeader() {
		return responseHeader;
	}

	public void setResponseHeader(String[] responseHeader) {
		this.responseHeader = responseHeader;
	}

	public HashMap<String, String> getRespHeaderValue() {
		return respHeader;
	}

	public void setRespHeaderValue(HashMap<String, String> respHeader) {
		this.respHeader = respHeader;
	}
	
}
