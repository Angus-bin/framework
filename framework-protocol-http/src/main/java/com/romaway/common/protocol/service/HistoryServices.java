package com.romaway.common.protocol.service;

import com.romalibs.utils.Res;
import com.romaway.common.android.base.OriginalContext;
import com.romaway.common.net.EMsgLevel;
import com.romaway.common.net.conn.ConnInfo;
import com.romaway.common.net.receiver.INetReceiveListener;
import com.romaway.common.net.sender.NetMsgSenderProxy;
import com.romaway.common.net.serverinfo.ServerInfo;
import com.romaway.common.protocol.ProtocolConstant;
import com.romaway.common.protocol.R;
import com.romaway.common.protocol.StringRandom;
import com.romaway.common.protocol.tougu.AdjustStoreHistoryProtocol;
import com.romaway.common.utils.DES3;
import com.romaway.common.utils.RomaSysConfig;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.lang.StringUtils;

/**
 * Created by hongrb on 2017/6/5.
 */
public class HistoryServices {

    public static final void reqAdjustStoryHistory(String userID, String pageIndex, INetReceiveListener listener, String msgFlag) {
        AdjustStoreHistoryProtocol ptl = new AdjustStoreHistoryProtocol(msgFlag);
        String xy = "";
        try {
            BaseJSONObject jsonObject = new BaseJSONObject();
            jsonObject.put("user_id", userID);
            jsonObject.put("pageindex", pageIndex);
            DES3.setIv(StringRandom.getInstance(OriginalContext.getContext()).getStringRandom(8));
            xy = DES3.encode(jsonObject.toString());
            xy = DES3.encodeToHex(xy);
            xy = xy.toLowerCase();

            String ip = "";
            if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
                ip = RomaSysConfig.getIp();
            } else {
                ip = Res.getString(R.string.NetWork_IP);
            }

            ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
                    ProtocolConstant.SERVER_FW_AUTH, ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
            ServerInfo temp = new ServerInfo("get_home_title", ProtocolConstant.SERVER_FW_AUTH, "get_home_title", ip, false, 9801);
            temp.setSubFunUrl(ptl.subFunUrl + "?xy=" + xy + "&iv=" + DES3.getIv());
            connInfo.setServerInfo(temp);
            NetMsg msg = new NetMsg(msgFlag, EMsgLevel.normal, ptl, connInfo, true, listener);
            msg.sendByGet = true;
            NetMsgSenderProxy.getInstance().send(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
