/**
 * 
 */
package com.romaway.common.net;

import android.support.annotation.Keep;

/**
 * 消息接收状态，分别有以下几个状态：<br>
 * wait-等待接收<br>
 * received-接收完毕<br>
 * receiveDrop-接收丢弃<br>
 * @author duminghui
 * 
 */
@Keep
public enum EMsgReceiveStatus
{
    /**
     * 等待接收
     */
    wait,
    /**
     * 接收完毕
     */
    received,
    /**
     * 接收丢弃
     */
    receiveDrop;
}
