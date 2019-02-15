/**
 * 
 */
package com.romaway.common.net.receiver;

import android.support.annotation.Keep;

import com.romaway.common.net.ANetMsg;

/**
 * @author duminghui
 * 
 */
@Keep
public class NetMsgReceiverProxy
{
    private final static NetMsgReceiverProxy instance = new NetMsgReceiverProxy();
    private ANetMsgReceiver receiver;

    private NetMsgReceiverProxy()
    {

    }

    public final static NetMsgReceiverProxy getInstance()
    {
        return instance;
    }

    public final void setReceiver(ANetMsgReceiver receiver)
    {
        this.receiver = receiver;
    }

    public final void receiveMsg(ANetMsg msg)
    {
        receiver.receive(msg);
    }
}
