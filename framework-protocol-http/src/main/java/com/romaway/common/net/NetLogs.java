/**
 * 
 */
package com.romaway.common.net;

import android.support.annotation.Keep;

import java.util.Date;

import com.romaway.common.net.conn.AConnection;
import com.romaway.common.net.serverinfo.ServerInfo;
import com.romaway.commons.lang.DateUtils;
import com.romaway.commons.log.Logger;

/**
 * @author duminghui
 * 
 */
@Keep
public class NetLogs
{

	public final static void d_msginfo(ANetMsg msg, String tag, String title,
	        String extraMsg)
	{
		ServerInfo si = msg.getConnInfo().getServerInfo();
		String url = si == null ? "" : si.getUrl();
		String showMsg = String
		        .format("[%s][HC:%s][FG:%s][L:%s][SS:%s][RS:%s][CT:%s][UL:%s][TO:%s][td:%s][%s]",
		                title, Integer.toHexString(msg.hashCode()), msg
		                        .getMsgFlag(), msg.getMsgLevel(), msg
		                        .getSendStatus(), msg.getReceiveStatus(), msg
		                        .getConnInfo().getConnMethod(), url, msg
		                        .getConnInfo().getTimeOut(), Thread
		                        .currentThread().getName(), extraMsg);
		Logger.d(tag, showMsg);
	}

	public final static void d_msginfo_simple(ANetMsg msg, String tag,
	        String title, String extraMsg)
	{
		String showMsg = String.format(
		        "[%s][HC:%s][FG:%s][L:%s][SS:%s][RS:%s][%s]", title,
		        Integer.toHexString(msg.hashCode()), msg.getMsgFlag(),
		        msg.getMsgLevel(), msg.getSendStatus(), msg.getReceiveStatus(),
		        extraMsg);
		Logger.d(tag, showMsg);
	}

	public static final void d_netstep(ANetMsg netMsg, AConnection connection,
	        String tag, String msg, String step)
	{
		ServerInfo serverInfo = netMsg.getConnInfo().getServerInfo();
		Logger.d(tag,
		        String.format(
		                "[HC:%s][url:%s][F:%s][connhcode:%s][sTime:%s][ut:%s][%s][step:%s]",
		                Integer.toHexString(netMsg.hashCode()), serverInfo.getUrl(), netMsg
		                        .getMsgFlag(),
		                connection != null ? connection.hashCode() : "",
		                DateUtils.fromat_YYYY_MM_DD_HH_MM_SS_SSS(new Date(
		                        netMsg.getSendTime())),
		                System.currentTimeMillis() - netMsg.getSendTime(), msg,
		                step));
	}

	public final static void d_time_msginfo(String timerFlag, ANetMsg netMsg,
	        String tag, String title, String extraMsg)
	{
		String showMsg = String.format(
		        "[Timer:%s][%s][HC:%s][FG:%s][L:%s][%s]", timerFlag, title,
		        Integer.toHexString(netMsg.hashCode()), netMsg.getMsgFlag(),
		        netMsg.getMsgLevel(), extraMsg);
		Logger.d(tag, showMsg);
	}

	public final static void d_time(String tag, String timerFlag, String title,
	        String extraMsg)
	{
		String showMsg = String.format("[Timer:%s][%s][%s]", timerFlag, title,
		        extraMsg);
		Logger.d(tag, showMsg);
	}
}
