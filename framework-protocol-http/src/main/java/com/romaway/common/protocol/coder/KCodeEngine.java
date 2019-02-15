package com.romaway.common.protocol.coder;

import android.support.annotation.Keep;

import java.util.Random;

import com.romaway.common.protocol.ServerException;

/**
 * <p>
 * Title: 编码解码器
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * 
 * <p>
 * Company:金慧盈通
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
@Keep
public class KCodeEngine
{
	// 下面是终端类型号的定义
	private final static byte TT_KJAVA = 0x01; // KJAVA客户端

	private final static byte TT_S60 = 0x02; // S60客户端
	// 下面是压缩类型定义
	public final static byte CT_NONE = 0x00; // 不压缩
	public final static byte CT_GZIP = 0x01; // GZIP压缩
	// 下面是加密类型定义
	public final static byte ET_NONE = 0x00; // 不加密
	public final static byte ET_3DES = 0x01; // 3DES加密
	// 下面是服务端返回数据类型定义
	@SuppressWarnings("unused")
	private final static byte RDT_GBK_DATA = 0x00; // GBK编码的正确数据
	private final static byte RDT_GBK_ERROR = 0x01; // GBK编码的错误信息
	@SuppressWarnings("unused")
	private final static byte RDT_UNICODE_DATA = 0x10; // UNICODE编码的正确数据
	private final static byte RDT_UNICODE_ERROR = 0x11; // UNICODE编码的错误信息

	// 下面是协议标识、版本及包头、包体长度定义
	private final static int PROTOCOL_TAG = 2008; // 协议头标识
	private final static int PROTOCOL_VERSION = 100; // 协议版本号
	private final static int HEADERLENGTH = 20; // 包头的长度
	private final static int BODYLENGTH = 24; // 包体（不包含数据部分）的长度

	// package header
	private int headTag; // 4B 通讯包标识 (0x07d8)
	private int headVersion; // 4B 协议版本
	private short headTermType; // 2B 终端类型标识(参见enum TremTypes)
	private int headBodyLength; // 4B 通讯包的包体(body)长度
	private byte headCompress; // 1B 包体的压缩方式 (参见enum CompressTypes)
	private byte headEncrypt; // 1B 包体的加密方式 (参见enum EncryptTypes)
	private short headChecksum; // 2B 数据包的校验字 (包体数据部分)
	private byte[] headReserved = new byte[2]; // 2B 保留，暂时不用
	// end
	// body header
	private static byte[] userID = new byte[]{0,0,0,0,0,0,0,0}; // (v2.0) 8B 客户ID
	private static int sessionID; // (v1.0~v2.0)4B 会话ID
	private short mainCommandNO; // 2B 主功能号
	private short subCommandNO; // 2B 子功能号
	private short commandVersion; // (v2.0)命令版本（指业务命令的版本）;(v1.0)wProductVersion;
	                              // // 2B
	// 产品版本
	private short dataType; // 2B 返回数据类型
	private int bodyLength; // 4B 数据内容长度
	// byte[] pData; // 数据内容指针
	// end

	@SuppressWarnings("unused")
	private int encodeID;
	private byte[] currentEncryptKey;

	public static KCodeEngine create()
	{
		return new KCodeEngine();
	}

	private KCodeEngine()
	{
	}

	/**
	 * 编码请求数据
	 * 
	 * @param data
	 *            byte[]
	 * @return byte[]
	 */
	public byte[] encode(int mainCmd, int subCmd, int sessionID,
	        int commandVer, byte compressType, byte encryptType, byte[] userID,
	        byte[] data)
	{
		initSendHeader(compressType, encryptType);
		// 如果需要加密和压缩，先进行加密和压缩
		byte[] bizData = getSendBody(mainCmd, subCmd, sessionID, commandVer,
		        userID, data);

		if (headEncrypt != ET_NONE)
		{ // 判断是否需要加密
			bizData = encryptData(bizData, headEncrypt, true);
		}
		headBodyLength = bizData.length;
		headChecksum = getChecksum(bizData);
		// 把结果输出到缓冲中
		byte[] buffer = new byte[HEADERLENGTH + bizData.length];
		System.arraycopy(getSendHeader(), 0, buffer, 0, HEADERLENGTH);
		System.arraycopy(bizData, 0, buffer, HEADERLENGTH, bizData.length);
		return buffer;
	}

	private void initSendHeader(byte compressType, byte encryptType)
	{
		headTag = PROTOCOL_TAG;
		headVersion = PROTOCOL_VERSION;
		headTermType = getSerialNumber();//TT_KJAVA;
		headBodyLength = 0;
		headCompress = compressType;
		headEncrypt = encryptType;
		headChecksum = 0;
	}
	
	public static short SerialNumber = 100;//2011-01-04 Mark Ye
	/**
	 * 产生流水号
	 * @return
	 */
	public synchronized short getSerialNumber(){
		  SerialNumber++;
		  if(SerialNumber>=Short.MAX_VALUE)
			  SerialNumber = 100;
		  return SerialNumber;
	  }

	private void initSendBody(int manCmd, int subCmd, int sessionID,
	        int cmdVer, byte[] userID)
	{
		this.sessionID = sessionID;
		this.userID = userID;
		mainCommandNO = (short) manCmd;
		subCommandNO = (short) subCmd;
		commandVersion = (short) cmdVer;
		dataType = 0x10;
		bodyLength = 0;
	}

	private byte[] getSendHeader()
	{
		byte[] headerData = new byte[HEADERLENGTH];
		int p = 0;
		CoderUtils.integer2Bytes(headerData, p, headTag);
		p += 4;
		CoderUtils.integer2Bytes(headerData, p, headVersion);
		p += 4;
		CoderUtils.short2Bytes(headerData, p, headTermType);
		p += 2;
		CoderUtils.integer2Bytes(headerData, p, headBodyLength);
		p += 4;
		headerData[p++] = headCompress;
		headerData[p++] = headEncrypt;
		CoderUtils.short2Bytes(headerData, p, headChecksum);
		p += 2;
		if (headReserved != null)
		{
			System.arraycopy(headReserved, 0, headerData, p,
			        headReserved.length);
		}
		return headerData;
	}

	private byte[] getSendBody(int mainCmd, int subCmd, int sessionID,
	        int commandVer, byte[] userID, byte[] reqData)
	{
		initSendBody(mainCmd, subCmd, sessionID, commandVer, userID);
		bodyLength = (reqData == null ? 0 : reqData.length);
		int dataLength = BODYLENGTH + bodyLength;
		byte[] bodyData = new byte[dataLength];
		int p = 0;
		for (int i = 0; i < userID.length; i++)
		{
			bodyData[p++] = userID[i];
		}
		CoderUtils.integer2Bytes(bodyData, p, this.sessionID);
		p += 4;
		CoderUtils.short2Bytes(bodyData, p, mainCommandNO);
		p += 2;
		CoderUtils.short2Bytes(bodyData, p, subCommandNO);
		p += 2;
		CoderUtils.short2Bytes(bodyData, p, commandVersion);
		p += 2;
		CoderUtils.short2Bytes(bodyData, p, dataType);
		p += 2;
		CoderUtils.integer2Bytes(bodyData, p, this.bodyLength);
		p += 4;
		if (reqData != null)
		{
			System.arraycopy(reqData, 0, bodyData, p, reqData.length);
		}
		return bodyData;
	}

	// end -- 编码请求数据

	/**
	 * 
	 * @param data
	 *            byte[]
	 * @return byte[][]<br>
	 *         0：服务器返回的版本号<br>
	 *         1：服务器返回的数据
	 * 
	 */
	public byte[][] decode(byte[] data) throws ServerException
	{
		// 解析包头
		parseHeader(data, 0);
		// 检查包的合法性
		if (!((headTag == PROTOCOL_TAG) && (headVersion == PROTOCOL_VERSION)))
		{// 接收到数据包非法!
			return null;
		}
		if (data.length < (HEADERLENGTH + headBodyLength))
		{
			return null;
		}
		byte[] bodyData = new byte[headBodyLength];
		System.arraycopy(data, HEADERLENGTH, bodyData, 0, bodyData.length);
		// 检查校验和
		if (headChecksum != getChecksum(bodyData))
		{
			return null;
		}
		// 看看是否需要解密和解压
		if (headCompress != CT_NONE)
		{ // 判断是否需要解压缩
			bodyData = compressData(bodyData, headCompress, false);
			// Logger.i("Compress", "Compress");
		}
		if (headEncrypt != ET_NONE)
		{ // 判断是否需要解密
			bodyData = encryptData(bodyData, headEncrypt, false);
		}
		// 解析包体
		// byte[] uiData = parseBody(bodyData, 0, bodyData.length);
		byte[][] result = parseBody(bodyData, 0, bodyData.length);
		return result;
	}

	/**
	 * 解析一个二进制数据串的包头结构
	 * 
	 * @param data
	 *            二进制数据串
	 * @param offset
	 *            开始偏移位置
	 * @throws java.lang.Exception
	 *             解析异常
	 */
	private void parseHeader(byte[] data, int offset)
	{
		if ((data == null) || ((data.length - offset) < HEADERLENGTH))
		{
			return;
		}
		int p = offset;
		headTag = CoderUtils.bytes2Integer(data, p);
		p += 4;
		headVersion = CoderUtils.bytes2Integer(data, p);
		p += 4;
		//headTermType = CoderUtils.bytes2Short(data, p);
		p += 2;
		headBodyLength = CoderUtils.bytes2Integer(data, p);
		p += 4;
		headCompress = data[p++];
		headEncrypt = data[p++];
		headChecksum = CoderUtils.bytes2Short(data, p);
		p += 2;
		System.arraycopy(data, p, headReserved, 0, 2);
		getTimeStamp(headReserved);
	}

	/**
	 * 解析一个二进制数据串的包体结构
	 * 
	 * @param data
	 *            二进制数据串
	 * @param offset
	 *            开始偏移位置
	 * @param len
	 *            数据长度
	 * @return
	 */
	private byte[][] parseBody(byte[] data, int offset, int len)
	        throws ServerException
	{
		if ((data == null)
		        || (((data.length - offset) < len) || (len < BODYLENGTH)))
		{
			return null;
		}
		int p = offset;
		for (int i = 0; i < userID.length; i++)
		{
			userID[i] = data[p++];
		}
		// 旧本版
		// dwConnectionId = CoderUtils.bytes2Integer(data, j);
		// j += 4;
		// dwSessionId = CoderUtils.bytes2Integer(data, j);
		// j += 4;
		// dwFlowNo = CoderUtils.bytes2Integer(data, j);
		// j += 4;
		sessionID = CoderUtils.bytes2Integer(data, p);

		p += 4;
		mainCommandNO = CoderUtils.bytes2Short(data, p);
		p += 2;
		subCommandNO = CoderUtils.bytes2Short(data, p);
		p += 2;
		commandVersion = CoderUtils.bytes2Short(data, p);
		p += 2;
		dataType = CoderUtils.bytes2Short(data, p);
		p += 2;
		bodyLength = CoderUtils.bytes2Integer(data, p);
		p += 4;
		if (bodyLength > (len - BODYLENGTH))
		{
			// KDSNet.netState = 3;
			return null;
		}
		byte[] uiData = new byte[bodyLength];
		System.arraycopy(data, p, uiData, 0, bodyLength);
		if (dataType == RDT_GBK_ERROR || dataType == RDT_UNICODE_ERROR)
		{
			short wErrorNo = CoderUtils.bytes2Short(uiData, 0);
			int infoLen = CoderUtils.bytes2Integer(uiData, 2);
			String errormsg = CoderUtils.bytes2String(uiData, 6, infoLen);
			encodeID = 10000 + Math.abs(wErrorNo);
			throw new ServerException(wErrorNo,errormsg);
			// return null;
		}
		byte result[][] = new byte[2][];
		result[0] = new byte[] { (byte) commandVersion };

		result[1] = uiData;
		return result;
	}

	public static int getSessionID()
	{
		return sessionID;
	}
	
	public static void setSessionID(int v)
	{
		sessionID = v ;
	}
	
	public static byte[] getUserID()
	{
		return userID;
	}
	
	public static void setUserID(long qwUserID)
	{
		
		userID = longToByte(qwUserID);
	}
	/**
     * 获取下发的UserId，转换为byte型，用于ANetMsg()中值。
     */
    public static byte[] longToByte(long longUserID){
    	byte[] b = new byte[8];
    	for (int i = 0; i < b.length; i++) {
    		b[i] = new Long(longUserID & 0xff).byteValue();// 将最低位保存在最低位             
    		longUserID = longUserID >> 8; // 向右移8位         
    	}         
    	return b;
	}
	private byte[] encryptData(byte[] source, byte encodeType, boolean isEncode)
	{
		int len = source.length;
		if (encodeType == ET_NONE || len == 0)
		{
			return source;
		}
		// 保证加解密串长度为8的倍数
		if ((len % 8) != 0)
		{
			len = ((len / 8) + 1) * 8;
		}
		byte[] m_data = new byte[len]; // 加解密源数据
		byte[] m_crypt = new byte[len]; // 加解密结果数据
		System.arraycopy(source, 0, m_data, 0, source.length);
		// 开始加解密处理
		// try {//by linzy
		headReserved = getTimeStamp(null);
		KeyParameter param = new KeyParameter(currentEncryptKey);
		BufferedBlockCipher m_cipher = new BufferedBlockCipher(
		        new DESedeEngine());
		m_cipher.init(isEncode, param);
		int len1 = m_cipher.processBytes(m_data, 0, len, m_crypt, 0);
		m_cipher.doFinal(m_crypt, len1);
		// } catch (Exception e) {}
		// 返回结果数据
		return m_crypt;
	}

	private short getChecksum(byte[] data)
	{
		short wRet = 0;

		int dwLen = data.length;
		// Logger.i("getChecksum", "getChecksum beginning...");
		for (int i = 0; i < dwLen / 2; i++)
		{
			wRet += CoderUtils.bytes2Short(data, i * 2);
			// Logger.i("getChecksum", "run:"+CoderUtils.bytes2Short(data, i * 2));
		}
		if (dwLen % 2 > 0)
		{
			byte[] tmp = new byte[2];
			tmp[0] = 0;
			tmp[1] = data[dwLen - 1];
			wRet += CoderUtils.bytes2Short(tmp, 0);
		}
		// Logger.i("getChecksum", "getChecksum end:"+wRet);

		return wRet;
	}

	/**
	 * 得到动态生成的交易密钥，如果还没有存在与缓存中，就动态产生一份。
	 * 
	 * @return 字节数组形式表示的交易密钥。
	 */
	public static byte[] getEncryptKey()
	{
		byte[] clsEncryptKey = null;
		if (clsEncryptKey == null)
		{
			clsEncryptKey = new byte[16];
			Random random = new Random(System.currentTimeMillis());
			for (int i = 0; i < 16; i++)
			{ // 生产随机密匙
				clsEncryptKey[i] = (byte) (Math.abs(random.nextInt() % 26) + 'A'); // A-Z
			}
		}
		return clsEncryptKey;
	}

	private byte[] getTimeStamp(byte[] buffer)
	{
		byte[] timeStamp = new byte[2];
		if (buffer == null)
		{
			int key = (int) System.currentTimeMillis();
			Random random = new Random(System.currentTimeMillis());
			for (int i = 0; i < 16; i++)
			{
				key = key | (Math.abs(random.nextInt()) % 2) << i;
			}
			CoderUtils.short2Bytes(timeStamp, 0, (short) key);
		} else
		{
			timeStamp = buffer;
		}
		short keyNum = CoderUtils.bytes2Short(timeStamp, 0);
		String sKey = Integer.toBinaryString(keyNum);
		if (sKey.length() < 16)
		{
			int i = 16 - sKey.length();
			sKey = "0000000000000000".substring(0, i) + sKey;
		} else if (sKey.length() > 16)
		{
			sKey = sKey.substring(sKey.length() - 16);
		}
		currentEncryptKey = sKey.getBytes();
		return timeStamp;
	}

	/**
	 * 压缩的实现接口
	 * 
	 * @param isEncode
	 *            是否进行编码
	 * @param encodeType
	 *            编码解码的种类
	 * @return 压缩或解压完成的数据内容
	 */
	private byte[] compressData(byte[] source, byte encodeType, boolean isEncode)
	{
		if (encodeType == CT_NONE || source.length == 0)
		{
			return source;
		}
		byte[] m_comp = null;
		GZIP gzip = new GZIP();
		m_comp = gzip.inflate(source);
		return m_comp;
	}

}
