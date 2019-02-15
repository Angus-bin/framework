package com.romaway.android.phone.keyboardelf;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;

import com.romaway.android.phone.netreq.HQReq;
import com.romaway.activity.basephone.ActivityStackMgr;
import com.romaway.android.phone.BundleKeyValue;
import com.romaway.android.phone.RomaUserAccount;
import com.romaway.android.phone.R;
import com.romaway.android.phone.SuperUserAdminActivity;
import com.romaway.android.phone.ViewParams;
import com.romaway.android.phone.net.UINetReceiveListener;
import com.romaway.android.phone.netreq.UserStockReq;
import com.romaway.android.phone.utils.StockCacheInfo;
import com.romaway.common.android.base.Res;
import com.romaway.common.android.base.data.SharedPreferenceUtils;
import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.hq.HQNewStockProtocol;
import com.romaway.common.protocol.hq.HQZXGTBAddProtocol;
import com.romaway.common.protocol.service.NetMsg;
import com.romaway.commons.lang.NumberUtils;
import com.romaway.commons.lang.StringUtils;
import com.romaway.commons.log.Logger;
import com.romaway.framework.view.SysInfo;
import com.szkingdom.stocksearch.StockHistory;
import com.szkingdom.stocksearch.bean.StockBean;
import com.szkingdom.stocksearch.ui.StockSearchFragment;

/**
 * Created by wanlh on 2016/8/29.
 */
public class Roma_SearchFragment extends StockSearchFragment{
    private boolean isSearchTrade = false;
    private boolean isSearchYJ = false;
    private StockHistory mStockHistory;

    private String matchStr;

    private Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);

            switch(msg.what){
                case 0:// 更新加减按钮
                    // 更新加减号，一定要写在 super.onSearchSuccess(matchStr, stockBeans);之前，否则不刷新
                    for (int i = 0; i < searchStockBeans.length; i++) {
                        boolean isMyStock = UserStockSQLMgr.isExistStock(getActivity(),
                                searchStockBeans[i].stockCode, searchStockBeans[i].marketId);
                        if(isMyStock){
                            searchStockBeans[i].isShowDelBtn = true;
                        }else
                            searchStockBeans[i].isShowDelBtn = false;
                    }

                    notifyDataSetChanged(searchStockBeans);
                    break;
                case 1:
                    try{
//                        if (Res.getBoolean(R.bool.kconfigs_is_goto_DataInfo)) {
//                            goToStockInfo(reqStockBeans, 0);
//                        }
                    }catch(Exception e){

                    }
                    break;
            }
        }
    };

    public boolean isSearchTrade(){
        return isSearchTrade;
    }

    public void setSearchTradeType(boolean isTrade){
        isSearchTrade = isTrade;
    }

    public boolean isSearchYJ() {
        return isSearchYJ;
    }

    public void setIsSearchYJ(boolean isSearchYJ) {
        this.isSearchYJ = isSearchYJ;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mStockHistory = new StockHistory(this.getActivity());
    }

    @Override
    public void onItemClick(View view, int position, StockBean[] stockBeans) {
        super.onItemClick(view, position, stockBeans);

            if(isSearchTrade){// 交易下单的选项点击事件
                if (stockBeans != null && position < stockBeans.length &&
                        stockBeans[position] != null &&
                        !TextUtils.isEmpty(stockBeans[position].stockCode)) {

                    String stockCode = stockBeans[position].stockCode;
                    String marketId = stockBeans[position].marketId;
                    String codeType = stockBeans[position].codeType;
                    marketId = tradeMarketIdCorrect(stockCode,marketId,codeType);

                    Intent intent = new Intent("KDS_STOCK_LIST_ITEM");
                    Bundle bundle = new Bundle();
                    bundle.putString(BundleKeyValue.HQ_STOCKCODE, stockCode);
                    bundle.putString(BundleKeyValue.HQ_MARKETID, marketId);
                    intent.putExtras(bundle);
                    getActivity().sendBroadcast(intent);
                }
                getActivity().finish();

            }else {
                try {
                    goToStockInfo(stockBeans, position);
                } catch (Exception e) {

                }
            }
    }

    /**
     * 纠正市场代码: 行情市场代码 --> 交易市场代码
     * 暂仅支持沪深AB股
     * @param marketId	行情市场代码
     * @return			交易市场代码
     */
    private String tradeMarketIdCorrect(String stockCode, String marketId, String codeType){
        String tradeMarketId = "";
        if (!StringUtils.isEmpty(marketId)) {
            if ("1".equals(marketId)) {  // 深证
                if ("2".equals(codeType)) {  // B股
                    tradeMarketId = "2";
                } else {  // A股
                    tradeMarketId = "0";
                }
            } else if ("2".equals(marketId)) {  // 上证
                if ("2".equals(codeType)) {  // B股
                    tradeMarketId = "3";
                } else {  // A股
                    tradeMarketId = "1";
                }
            } else if ("4".equals(marketId)) {  // 股转
                String code = stockCode.substring(0, 3);
                if ("420".equals(code)) {  // B股
                    tradeMarketId = "5";
                } else {  // A股
                    tradeMarketId = "4";
                }
            } else if ("33".equals(marketId)) {  // 沪港通
                tradeMarketId = "6";
            } else if ("32".equals(marketId)) {  // 深港通
                tradeMarketId = "9";
            }  else if ("5".equals(marketId)) {  // 港股
                tradeMarketId = "100001";   // 由于交易内市场代码为5是股转B股，这里设置一个交易内不会使用到的市场代码
            } else {   // 其他保持不变
                tradeMarketId = marketId;
            }
        }
        return tradeMarketId;
    }

    @Override
    public void onItemClickClearHistory() {
        super.onItemClickClearHistory();
    }

    @Override
    public void onClickAddDelBtn(View view, int position, StockBean[] stockBeans) {
        super.onClickAddDelBtn(view, position, stockBeans);

        addUserStock(stockBeans[position].stockCode, stockBeans[position].stockName,
                stockBeans[position].marketId, stockBeans[position].stockMark);
        stockBeans[position].isShowDelBtn = true;
        notifyDataSetChanged(stockBeans);
        //4.自选股同步：增加自选股请求
        /**
         * 1.用户级别判断
         * 2.增加自选股
         * 3.响应失败，就弹出提示：添加/删除自选股失败，并且回退数据库的修改和界面的修改
         */
        String stockCode_marketId = stockBeans[position].marketId + ":" + stockBeans[position].stockCode;
        Logger.i("UserStockTBServer", "自选股[新增]：stockCode_marketId："+stockCode_marketId);
    }

    protected void addUserStock(final String stockCode,
                                final String stockName, final String marketId, String stockMark) {
        UserStockSQLMgr.insertData(getActivity(), stockName, stockCode, marketId, "", "", "0", stockMark);
    }

    /** 自选行情网络监听者*/
    private class UserStockHQListener extends UINetReceiveListener {
        private int position;
        private StockBean[] stockBeens;
        public UserStockHQListener(Activity activity, int position, StockBean[] stockBeens) {
            super(activity);
            this.position = position;
            this.stockBeens = stockBeens;
        }

        @Override
        protected void onSuccess(NetMsg msg, AProtocol ptl) {
            //自选股同步添加
            HQZXGTBAddProtocol hqzxgtb = (HQZXGTBAddProtocol) ptl;
            if(hqzxgtb.serverErrCode!=0){
                String stockCode = hqzxgtb.req_favors.split(":")[1];
                String marketId = hqzxgtb.req_favors.split(":")[0];
                    if(StringUtils.isEmpty(hqzxgtb.serverMsg)){
                        SysInfo.showMessage(getActivity(), "添加自选股" + stockCode + "失败");
                    }else{
                        SysInfo.showMessage(getActivity(), hqzxgtb.serverMsg);
                    }
                //回退本地自选股
                rollBack(stockCode, marketId, position,stockBeens);
            }
            Logger.i("UserStockTBServer", "自选股[新增]：成功");
        }

        @Override
        protected void onShowStatus(int status, NetMsg msg) {
            super.onShowStatus(status, msg);
            if (status != SUCCESS) {
                String stockCode = ((HQZXGTBAddProtocol)msg.getProtocol()).req_favors.split(":")[1];
                String marketId = ((HQZXGTBAddProtocol)msg.getProtocol()).req_favors.split(":")[0];
                if(getActivity() != null)
                    SysInfo.showMessage(getActivity(), "添加自选股" + stockCode + "失败");
                //回退本地自选股
                rollBack(stockCode, marketId, position,stockBeens);
            }
        }
    }

    /**
     * 回退本地数据库
     */
    public void rollBack(String stockCode, String marketId, int position, StockBean[] stockBeens){
        UserStockSQLMgr.deleteByStockCode(getActivity(), stockCode, marketId);
        stockBeens[position].isShowDelBtn = false;
        notifyDataSetChanged(stockBeens);
    }

    private StockBean[] searchStockBeans;
    @Override
    public void onSearchSuccess(String matchStr, StockBean[] stockBeans) {
        super.onSearchSuccess(matchStr, stockBeans);

        // 为了更新加减按钮的显示
        if(stockBeans != null && stockBeans.length > 0) {
            searchStockBeans = stockBeans;
            mHandler.removeMessages(0);
            Message msg = Message.obtain();
            msg.what = 0;
            mHandler.sendMessageDelayed(msg, 10);
        }


        this.matchStr = matchStr;

        if (matchStr.equalsIgnoreCase("kds888")) {
            Intent i = new Intent();
            i.setClass(this.getActivity(), SuperUserAdminActivity.class);
            startActivity(i);
            return;
        }

        // 没搜索到任何数据时
        if (stockBeans == null || stockBeans.length == 0) {
            // 作数据请求
            if ((matchStr.length() == 5 ||
                    matchStr.length() == 6 ||
                    matchStr.length() == 8) && !matchStr.contains("kds88")) {
                    HQReq.reqNewStockData(matchStr, "new_stock", new NewStockHQListener(getActivity()));
            }
        }
    }

    private StockBean[] reqStockBeans;
    private class NewStockHQListener extends UINetReceiveListener {

        public NewStockHQListener(Activity activity) {
            super(activity);
        }

        @Override
        protected void onSuccess(NetMsg msg, AProtocol ptl) {
            super.onSuccess(msg, ptl);
            HQNewStockProtocol newStockData = (HQNewStockProtocol) ptl;
            if(newStockData.req_wCount == 0 &&
                    isSearchTrade &&
                    NumberUtils.isDigits(matchStr)){//交易搜索

                reqStockBeans = new StockBean[1];
                StockBean stockBean = new StockBean();
                stockBean.stockCode = matchStr;
                stockBean.stockName = newStockData.stock_name;
                stockBean.py = newStockData.stock_pinyin;
                stockBean.codeType = newStockData.stock_type;
                stockBean.marketId = newStockData.stock_market;
                stockBean.stockMark = newStockData.stock_mark;
                reqStockBeans[0] = stockBean;

            }else{
                reqStockBeans = new StockBean[newStockData.req_wCount];
                for (int i = 0; i < newStockData.req_wCount; i++) {
                    StockBean stockBean = new StockBean();
                    stockBean.stockCode = newStockData.stock_code;
                    stockBean.stockName = newStockData.stock_name;
                    stockBean.py = newStockData.stock_pinyin;
                    stockBean.codeType = newStockData.stock_type;
                    stockBean.marketId = newStockData.stock_market;
                    stockBean.stockMark = newStockData.stock_mark;
                    reqStockBeans[i] = stockBean;
                }
                notifyDataSetChanged(reqStockBeans);
            }

            mHandler.sendEmptyMessage(0);
            mHandler.removeMessages(1);
            if( reqStockBeans != null &&
                    reqStockBeans.length == 1 &&
                    !isSearchTrade) {
                mHandler.sendEmptyMessageDelayed(1, 300);
            }
        }

        @Override
        protected void onConnError(NetMsg msg) {
        }

        @Override
        protected void onShowStatus(int status, NetMsg msg) {
            // TODO Auto-generated method stub
            super.onShowStatus(status, msg);
        }

    }

    /**
     * 跳转到个股详情界面
     * @param position
     */
    private void goToStockInfo(StockBean[] stockBeans, final int position){

        //保存历史记录到数据库
        mStockHistory.saveHistory(stockBeans[position]);

        Bundle bundle = new Bundle();

        // 跳转到分时K线界面
        bundle.putString(BundleKeyValue.HQ_STOCKNAME,
                stockBeans[position].stockName);
        bundle.putString(BundleKeyValue.HQ_STOCKCODE,
                stockBeans[position].stockCode);
        bundle.putShort(BundleKeyValue.HQ_MARKETID,
                (short) NumberUtils.toInt(stockBeans[position].marketId));
        bundle.putShort(BundleKeyValue.HQ_STOCKTYPE,
                (short) NumberUtils.toInt(stockBeans[position].codeType));
        bundle.putInt("HQ_POSITION", position);
        StockCacheInfo.clearCacheList();
        saveListToCache(stockBeans);
//        StockCacheInfo.saveListToCache(datas, new int[] { 1, 0, 4, 3});

        //先将上一个个股详情finish掉,
        Activity activity = (Activity) ActivityStackMgr.getActivityHistoryTopStack();
        String className = activity.getLocalClassName();
        if(className != null && className.
                equals("kds.szkingdom.android.phone.activity.hq.HQStockDataInfoFragmentActivity"))
            activity.finish();

        Intent i = new Intent();
        ComponentName componentName = new ComponentName(
                this.getActivity().getPackageName(),
                "kds.szkingdom.android.phone.activity.hq.HQStockDataInfoFragmentActivity");
        i.setComponent(componentName);
        if (bundle != null) {
            i.putExtras(bundle);
        }
        startActivity(i);
    }

    private void goToStockWarningSet(StockBean[] stockBeans, final int position){
        //保存历史记录到数据库
        mStockHistory.saveHistory(stockBeans[position]);

        //跳转到预警设置界面 ViewParams.
        ViewParams.bundle.putBoolean("isModified", false);
        ViewParams.bundle.putString(BundleKeyValue.HQ_STOCKTYPE, stockBeans[position].codeType);
        ViewParams.bundle.putString(BundleKeyValue.HQ_STOCKCODE,stockBeans[position].stockCode);
        ViewParams.bundle.putInt(BundleKeyValue.HQ_MARKETID, NumberUtils.toInt(stockBeans[position].marketId));
        ViewParams.bundle.putInt("warningFlag",1);
        Intent i = new Intent();
        ComponentName componentName = new ComponentName(
                this.getActivity().getPackageName(),
                "kds.szkingdom.android.phone.activity.hq.GPYJActivity");
        i.setComponent(componentName);
        startActivity(i);
    }

    /**
     * 保存数据到缓存
     * @param stockBeans
     */
    private void saveListToCache(StockBean[] stockBeans){
        for (int i = 0; i < stockBeans.length; i++) {
            StockCacheInfo si = new StockCacheInfo();
            si.stockName = stockBeans[i].stockName;
            si.stockCode = stockBeans[i].stockCode;
            si.marketId = (short) NumberUtils.toInt(stockBeans[i].marketId);
            si.stockType = (short) NumberUtils.toInt(stockBeans[i].codeType);
            StockCacheInfo.addToCacheList(si);
        }
    }
}
