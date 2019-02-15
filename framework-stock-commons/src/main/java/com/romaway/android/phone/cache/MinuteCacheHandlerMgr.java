package com.romaway.android.phone.cache;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

import com.romaway.common.protocol.hq.HQFSZHProtocol;
import com.romaway.common.protocol.hq.HQKXZHProtocol;

/**
 * 异步存增量缓存数据 (目前没用，想改进效率同学可以研究，小心竞争问题。)
 * 
 * @author qinyn
 * 
 */
public class MinuteCacheHandlerMgr
{
	private static MinuteCacheHandlerMgr instance = new MinuteCacheHandlerMgr();

	public static MinuteCacheHandlerMgr getInstance()
	{
		return instance;
	}

	private static final int toSaveMinData = 0;
	private static final int toSelDwTime = 1;
	private SaveHandler handler;
	private MinRun minRun;

	public void toSaveMinData(HQFSZHProtocol ptl, String stockCode,
	        String stockName, int marketId, int type)
	{
		if (handler == null)
		{
			HandlerThread thread = new HandlerThread("min");
			thread.start();
			handler = new SaveHandler(thread.getLooper());
		}
		minRun = new MinRun(ptl, stockCode, stockName, marketId, type);
		handler.sendEmptyMessage(toSaveMinData);
	}

	public void toSelDwTime(String stockCode, int marketId)
	{
		HandlerThread thread = new HandlerThread("min");
		thread.start();
		SelRun run = new SelRun(stockCode, marketId);
		handler = new SaveHandler(thread.getLooper());
		handler.sendEmptyMessage(toSelDwTime);
	}

	private class MinRun implements Runnable
	{
		private HQFSZHProtocol hqfszh;
		private String stockCode;
		private String stockName;
		private int marketId;
		private int stockType;

		public MinRun(HQFSZHProtocol ptl, String stockCode, String stockName,
		        int marketId, int type)
		{
			this.hqfszh = ptl;
			this.stockCode = stockCode;
			this.stockName = stockName;
			this.marketId = marketId;
			this.stockType = type;
		}

		@Override
		public void run()
		{
			MinuteCacheUtil.getInstance().delStockData(stockCode, marketId);
			MinuteCacheUtil.getInstance().add(hqfszh, stockCode, stockName,
			        marketId, stockType);
			handler.removeCallbacks(minRun);
		}
	}

	private class SelRun implements Runnable
	{
		private String stockCode;
		private int marketId;

		public SelRun(String stockCode, int marketId)
		{
			this.stockCode = stockCode;
			this.marketId = marketId;
		}

		@Override
		public void run()
		{
			// dwtime = MinuteCacheUtil.getInstance().selLastDwtime(stockCode,
			// marketId);
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
				case toSaveMinData:
					handler.post(minRun);
					break;
				case toSelDwTime:
					// handler
				default:
					break;
			}

		}
	}

}
