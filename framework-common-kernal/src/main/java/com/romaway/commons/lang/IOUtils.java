/**
 * 
 */
package com.romaway.commons.lang;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.romaway.commons.log.Logger;

/**
 * IO类的操作
 * 
 * @author duminghui
 * 
 */
public class IOUtils
{
	//private static Logger log = Logger.getLogger();

	/**
	 * 从流中读取字节数据并转为字符串<br>
	 * 并关闭流
	 * 
	 * @param in
	 * @return
	 */
	public static final String readString(InputStream in)
	{
		if (in == null)
		{
			return "";
		}
		BufferedInputStream buffIn = null;
		try
		{
			buffIn = new BufferedInputStream(in);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buffByte = new byte[1024];
			int len = -1;
			while ((len = buffIn.read(buffByte)) != -1)
			{
				baos.write(buffByte, 0, len);
			}
			return baos.toString();
		} catch (IOException ex)
		{
			Logger.e("IOUtils", ex.getMessage(), ex);
		} finally
		{
			if (buffIn != null)
			{
				try
				{
					buffIn.close();
				} catch (IOException e)
				{
					Logger.e("IOUtils", e.getMessage(), e);
				}
			}
		}
		return "";
	}

	/**
	 * 写字符串到流中，并关闭流,以字节的方法
	 * 
	 * @param text
	 * @param out
	 */
	public static final void writeString(String text, OutputStream out)
	{
		BufferedOutputStream buffOut = null;
		try
		{
			buffOut = new BufferedOutputStream(out);
			ByteArrayInputStream bais = new ByteArrayInputStream(text.trim()
			        .getBytes());
			byte[] buffByte = new byte[1024];
			int len = -1;
			while ((len = bais.read(buffByte)) != -1)
			{
				buffOut.write(buffByte, 0, len);
			}
		} catch (IOException e)
		{
			Logger.e("IOUtils", e.getMessage(), e);
		} finally
		{
			if (buffOut != null)
			{
				try
				{
					buffOut.close();
				} catch (IOException e)
				{
					Logger.e("IOUtils", e.getMessage(), e);
				}
			}
		}
	}
}
