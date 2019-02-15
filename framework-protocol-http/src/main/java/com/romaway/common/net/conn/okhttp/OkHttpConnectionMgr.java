package com.romaway.common.net.conn.okhttp;

import com.romaway.common.net.conn.AConnection;
import com.romaway.common.net.conn.ConnInfo;
import com.romaway.common.net.conn.IConnectionManager;

/**
 * @author duminghui
 */
public class OkHttpConnectionMgr implements IConnectionManager {


    public OkHttpConnectionMgr() {
    }

    @Override
    public AConnection crate(ConnInfo connInfo) {
        return new OkHttpConnection();
    }

    @Override
    public void init() {

    }

    @Override
    public void destroy() {

    }
}
