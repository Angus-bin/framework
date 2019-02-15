package com.romaway.android.phone.cache;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

import com.romaway.common.protocol.hq.HQKXProtocol;
import com.romaway.common.protocol.hq.HQKXZHProtocol;

/**
 * 异步存增量缓存数据 (目前没用，想改进效率同学可以研究，小心竞争问题。)
 * 
 * @author qinyn
 * 
 */
public class KlineCacheHandlerMgr
{
	private static KlineCacheHandlerMgr instance = new KlineCacheHandlerMgr();
	private SaveHandler handler;
	private KlineRun klineRun;
	private static final int toSaveKlineData = 1;

	public static KlineCacheHandlerMgr getInstance()
	{
		return instance;
	}

	public void toSaveKlineData(HQKXProtocol ptl, String stockCode,
	        String stockName, int marketId, int type, short kltype)
	{
		if (handler == null)
		{
			HandlerThread thread = new HandlerThread("kline_run");
			thread.start();
			handler = new SaveHandler(thread.getLooper());
		}
		klineRun = new KlineRun(ptl, stockCode, stockName, marketId, type,
		        kltype);
		handler.sendEmptyMessage(toSaveKlineData);
	}

	private class KlineRun implements Runnable
	{
		private HQKXProtocol kxzh;
		private String stockCode;
		private String stockName;
		private int marketId;
		private int stockType;
		private short kltype;

		public KlineRun(HQKXProtocol ptl, String stockCode, String stockName,
		        int marketId, int type, short kltype)
		{
			this.kxzh = ptl;
			this.stockCode = stockCode;
			this.stockName = stockName;
			this.marketId = marketId;
			this.stockType = type;
			this.kltype = kltype;
		}

		@Override
		public void run()
		{
			KlineCacheUtil.getInstance().delStockData(stockCode, marketId,
			        kltype);
			KlineCacheUtil.getInstance().add(kxzh, stockCode, stockName,
			        marketId, stockType, kltype);
			handler.removeCallbacks(klineRun);
		}
	}

	private class SaveHandler extends Handler
	{
		public SaveHandler(Looper looper)
		{
			super(looper);
		}

		@Override
		public void handleMessage(Message msg)
		{
			int what = msg.what;
			switch (what)
			{
				case toSaveKlineData:
					handler.post(klineRun);
					break;
				default:
					break;
			}
		}
	}
}
