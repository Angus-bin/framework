package com.romaway.android.phone.keyboardelf;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.romaway.android.phone.BundleKeyValue;
import com.romaway.common.android.base.OriginalContext;
import com.romaway.commons.lang.StringUtils;

/**
 * 无添加按钮的键盘精灵搜索
 * 
 * @author chenjp
 *
 */
public class RomaSearchFragmentNew extends RomaSearchFragment {
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		adapter.setAddVisible(false);
		
		addTextChangedListener(1);//交易下单股票搜索流程
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if (datas != null && position < datas.length && datas[position] != null && !StringUtils.isEmpty(datas[position][0])) {
			String stockCode = datas[position][0];
			String marketId = datas[position][4];

			String codeType = datas[position][3];
			marketId = Roma_MarketIdManager.getInstance(OriginalContext.getContext()).tradeMarketIdCorrect(stockCode,marketId,codeType);
			
			Intent intent = new Intent("KDS_STOCK_LIST_ITEM");
			Bundle bundle = new Bundle();
			bundle.putString(BundleKeyValue.HQ_STOCKCODE, stockCode);
			bundle.putString(BundleKeyValue.HQ_MARKETID, marketId);
			intent.putExtras(bundle);
			mActivity.sendBroadcast(intent);
			mActivity.finish();
		} else {
			mActivity.finish();
		}
	}
	
	@Override
	public void setSerachGg(boolean isSerachGg) {
		// TODO Auto-generated method stub
		super.setSerachGg(isSerachGg);
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
	
}
