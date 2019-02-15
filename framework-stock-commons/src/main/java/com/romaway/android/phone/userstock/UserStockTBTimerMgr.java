package com.romaway.android.phone.userstock;

import android.app.Activity;

import com.romaway.android.phone.R;
import com.romaway.android.phone.RomaUserAccount;
import com.romaway.android.phone.keyboardelf.UserStockSQLMgr;
import com.romaway.common.android.base.OriginalContext;
import com.romaway.common.android.base.Res;
import com.romaway.common.android.base.data.SharedPreferenceUtils;
import com.romaway.android.phone.config.RomaSysConfig;
import com.romaway.android.phone.netreq.UserStockReq;
import com.romaway.common.protocol.StringRandom;
import com.romaway.common.utils.DES3;
import com.romaway.common.utils.HttpUtils;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.lang.StringUtils;
import com.romaway.commons.log.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by edward on 16/6/2.
 * 自选股定时同步上传管理器
 */
public class UserStockTBTimerMgr {

    private static final String TAG = "UserStockTBTimerMgr";

    /**  同步线程，间隔时间 根据服务器下发 */
    private int delayTime = getTimeInterval() * 20;
    private Activity mActivity;

    /**  单任务线程池(定时线程池) */
    private ScheduledExecutorService mExecutor;
    private static UserStockTBTimerMgr mUserStockTBTimerMgr;

    private UserStockTBTimerMgr(Activity activity) {
        this.mActivity = activity;
    }

    public static UserStockTBTimerMgr getInstance(Activity activity) {
        if (mUserStockTBTimerMgr == null)
            mUserStockTBTimerMgr = new UserStockTBTimerMgr(activity);
        return mUserStockTBTimerMgr;
    }

    private int getTimeInterval() {
        return Integer.parseInt(RomaSysConfig.getParamsValue("ziXuanUploadTimeInterval", "1"));
    }

    /**
     * 启动自选股全量同步上传(是否定时循环)
     */
    public void startSync(boolean isLoopSync, UserStockTBServer.OnTBUpdateListener mOnTBUpdateListener) {
        try {
            // 单任务线程池 定时器
            if (mExecutor == null)
                mExecutor = Executors.newScheduledThreadPool(1);

            UpdateRunnable mRunnable = new UpdateRunnable(mOnTBUpdateListener);
            if (isLoopSync) {
             /*
             * 第一个参数是Runnable对象,
             * 第二个参数是首先过多少时间（秒）后运行一次,
             * 第三个参数是之后每次都过多少时间（秒）运行一次,
             * 第四个参数是指时间单位，这里设置的是秒.
             */
                Logger.d("UserStockTBTimerMgr", "定时同步上传时间 delayTime = " + delayTime);
                mExecutor.scheduleAtFixedRate(mRunnable, delayTime, delayTime, TimeUnit.SECONDS);
            } else {
                mExecutor.execute(mRunnable);
            }
        }catch (Exception e){
            Logger.e(TAG, "startSync: " + e.getMessage());
        }
    }

    /**
     * 停止同步操作
     */
    public void stopSync() {
        if (mExecutor != null && !mExecutor.isShutdown()) {
            mExecutor.shutdown();
            mExecutor = null;
        }
    }

    public class UpdateRunnable implements Runnable {

        private String favors;
        private UserStockTBServer mUserStockTBServer;
        private UserStockTBServer.OnTBUpdateListener mOnTBUpdateListener;
        public UpdateRunnable(UserStockTBServer.OnTBUpdateListener onTBUpdateListener) {
            this.mOnTBUpdateListener = onTBUpdateListener;
            this.mUserStockTBServer = new UserStockTBServer(mActivity);

            mUserStockTBServer.setOnStartUpdateUserStockListener(mOnTBUpdateListener);
        }

        /**
         * Starts executing the active part of the class' code. This method is
         * called when a thread is started that has been created with a class which
         * implements {@code Runnable}.
         */
        @Override
        public void run() {
            Logger.i(TAG, "自选股[定时同步上传]start UpdateRunnable");
            if (SharedPreferenceUtils.getPreference(SharedPreferenceUtils.DATA_USER, "isUserStockChange", false)) {
                Logger.i(TAG, "自选股[定时同步上传]:服务程序已启动...");
                if (RomaUserAccount.isGuest()) { //浏览用户的同步处理
                    Logger.i(TAG, "自选股[定时同步上传]:浏览用户，不可同步全量上传...");
                    return;
                }
                String[][] result = UserStockSQLMgr.queryAll(mActivity);
                String stockCodes = "";
                String marketIds = "";
                favors = "";
                for (int i = 0; i < UserStockSQLMgr.getUserStockCount(mActivity); i++) {
                    stockCodes = result[i][2];
                    marketIds = result[i][3];
//                    if (StringUtils.isEmpty(favors)) {
//                        favors = marketIds + ":" + stockCodes;
//                    } else {
//                        favors += "," + marketIds + ":" + stockCodes;
//                    }
                    if ("1".equals(marketIds)) {
                        stockCodes = "sz" + stockCodes;
                    } else if ("2".equals(marketIds)) {
                        stockCodes = "sh" + stockCodes;
                    }

//                        strBuf.append(marketIds);
//                        strBuf.append(":");
                    if (StringUtils.isEmpty(favors)) {
                        favors = stockCodes;
                    } else {
                        favors += "," + stockCodes;
                    }
                }
                Logger.i(TAG, "自选股[定时同步上传]: "+ favors);
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        UserStockReq.reqZXGTBUpload/* reqZXGTBUpload */(favors, "自选",
//                                mUserStockTBServer.new UserStockHQListener(mActivity, "同步上传"), "zxgtbUpdata",
//                                true);
                        reqZXGTBUpload(favors);
                    }
                });
            } else {
                // 同步完成进行界面跳转
                if (mOnTBUpdateListener != null)
                    mOnTBUpdateListener.onTBComplete();
            }
        }
    }

    private void reqZXGTBUpload(String codes) {
        Logger.d("UserStockTBServer", "codes = " + codes);
        BaseJSONObject jsonObject = new BaseJSONObject();

        jsonObject.put("user_id", RomaUserAccount.getUserID());
        jsonObject.put("code", codes);
        String xy = "";
        try {
            DES3.setIv(StringRandom.getInstance(OriginalContext.getContext()).getStringRandom(8));
            xy = DES3.encode(jsonObject.toString());
            xy = DES3.encodeToHex(xy);
            xy = xy.toLowerCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //参数
        final Map<String,String> params = new HashMap<String,String>();
        params.put("xy", xy);
        params.put("iv", DES3.getIv());
        new Thread(new Runnable() {
            @Override
            public void run() {
                String ip = "";
                if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
                    ip = RomaSysConfig.getIp();
                } else {
                    ip = Res.getString(R.string.NetWork_IP);
                }
                String result = HttpUtils.submitPostData(ip+ "SelfStock/add", params, "utf-8");
                Logger.d("UserStockTBServer", "result = " + result);
                if (!StringUtils.isEmpty(result)) {
                    try {
                        BaseJSONObject jsonObject1 = new BaseJSONObject(result);
                        String errorCode = "";
                        if (jsonObject1.has("error")) {
                            errorCode = jsonObject1.getString("error");
                        }
                        if ("0".equals(errorCode)) {
                            SharedPreferenceUtils.setPreference(SharedPreferenceUtils.DATA_USER, "isUserStockChange", false);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

}
