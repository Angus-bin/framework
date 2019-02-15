package roma.romaway.commons.android.webkit;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.actionbarsherlock.view.Menu;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.romaway.activity.basephone.BaseSherlockFragment;
import com.romaway.activity.basephone.BaseSherlockFragmentActivity;
import com.romaway.android.phone.ActionBarEventMgr;
import com.romaway.android.phone.KActivityMgr;
import com.romaway.android.phone.R;
import com.romaway.android.phone.net.UINetReceiveListener;
import com.romaway.android.phone.utils.RomaCache;
import com.romaway.android.phone.utils.ZXDataUtils;
import com.romaway.common.android.base.Res;
import com.romaway.common.android.phone.ISubTabView;
import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.service.NetMsg;
import com.romaway.common.protocol.zx.ZXListProtocol;
import com.romaway.commons.lang.StringUtils;
import com.romaway.commons.log.Logger;
import com.romawaylibs.theme.ROMA_SkinManager;

import roma.romaway.commons.android.theme.SkinManager;

/**
 * 资讯列表基类
 * @author chenjp
 * @version 2015年7月2日 上午9:47:37
 */
public class RomaZXListBaseSherlockFragment extends BaseSherlockFragment implements ROMA_SkinManager.OnSkinChangeListener{

	public PullToRefreshListView mPullRefreshListView;
	public ZXListAdapter adapter;
	//存储所有的数据
	public String[][] allDatas = new String[0][0];
	//当次请求的数据
	public String[][] datas = new String[0][0];
	//临时数据
	public String[][] tempDatas = new String[0][0];
	//请求的记录起始位置
	public int startIndex = 0;
	//请求的记录结束为止
	public int endIndex = 19;
	private RomaCache romaCache;
	/*
	 * 缓存使用的键
	 */
	public String cacheKey = "";
	//保存点击过的列表ID
	public ArrayList<String> clickList = new ArrayList<String>();
	private boolean isCache = false;
	private boolean isRefresh = false;
	private RelativeLayout rl_zixun;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.roma_zx_base_layout, null);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		ROMA_SkinManager.setOnSkinChangeListener(this);
		mPullRefreshListView = (PullToRefreshListView) view
				.findViewById(R.id.pull_refresh_list);
		rl_zixun = (RelativeLayout) view.findViewById(R.id.rl_zixun);

		// Set a listener to be invoked when the list should be refreshed.
		mPullRefreshListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				tempDatas = null;
				startIndex = 0;
				endIndex = 19;
				isRefresh = false;
				req();
				Logger.d("OnRefreshListener", "onPullDownToRefresh and stratIndex = " + startIndex + ", endIndex = " + endIndex);
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				startIndex = endIndex + 1;
				endIndex += 20;
				isRefresh = false;
				req();
				Logger.d("OnRefreshListener", "onPullUpToRefresh and startIndex = " + startIndex + ", endInex:" + endIndex);
			}
		});
		adapter = new ZXListAdapter(mActivity);
		mPullRefreshListView.setAdapter(adapter);
		romaCache = RomaCache.get(mActivity);
		mPullRefreshListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (allDatas == null || allDatas.length == 0) {
					return;
				}
				//保存点击过的ID；
				clickList.add(allDatas[position - 1][2]);
				saveClickListToCache(clickList);
				TextView txt_title = (TextView) view.findViewById(R.id.txt_zx_title);
				TextView txt_time = (TextView) view.findViewById(R.id.txt_zx_time);
//				txt_title.setTextColor(Res.getColor(R.color.zx_onclick_color));
//				txt_time.setTextColor(Res.getColor(R.color.zx_onclick_color));
				txt_title.setTextColor(Res.getColor(R.color.listmaintitleColor_read));
				txt_time.setTextColor(Res.getColor(R.color.timeColor_read));
				Bundle bundle = new Bundle();
				//从1开始，所以position-1
				Logger.d("RomaZXListBaseSherlockFragment", "KDS_ZX_TITLE_ID = " + allDatas[position - 1][2]);
				bundle.putString("KDS_ZX_TITLE_ID", allDatas[position - 1][2]);
				KActivityMgr.switchWindow((ISubTabView)mActivity, "kds.szkingdom.zx.android.phone.ZXDetailSherlockFragmentActivity", bundle, false);
			}
		});
		onSkinChanged(null);
	}
	
	protected void saveClickListToCache(ArrayList<String> list) {
		isCache = true;
		romaCache.remove(cacheKey + "_click");
		StringBuilder temp = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			temp.append(list.get(i));
			if (i != list.size() - 1 ) {
				temp.append(",");
			}
		}
		romaCache.put(cacheKey + "_click", temp.toString());
	}

	
	@Override
	public void onResumeInit() {
		// TODO Auto-generated method stub
		super.onResumeInit();
		mActionBar.resetTitleToDefault();
		mActionBar.setTitle("资讯中心");
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		tempDatas = null;
		initCacheData();
		refresh();
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		// getSherlockActivity().getSupportActionBar().setMenuLayout(-1, null);
		// 添加ActionBar右上角布局View:
		ActionBarEventMgr.getInitialize().setOptionsMenu(getSherlockActivity(), Res.getInteger(R.integer.roma_zx_sherlock_right_bar));
	}
	
	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		super.refresh();
		isRefresh = true;
		req();
	}
	
	/**
	 * 初始化缓存数据，显示到界面
	 */
	private void initCacheData(){
		String click = romaCache.getAsString(cacheKey + "_click");
		if (!StringUtils.isEmpty(click) && !isCache) {
			String[] array = click.split(",");
			clickList.removeAll(clickList);
			for (int i = 0; i < array.length; i++) {
				clickList.add(array[i]);
			}
		}
		JSONObject json = romaCache.getAsJSONObject(cacheKey);
		if (json != null && json.has("news")) {
			try {
				JSONArray newsList = json.getJSONArray("news");
				int len = newsList.length();
				String[][] temp = new String[len][3];
				for (int i = 0; i < len; i++) {
					JSONObject mNew = newsList.getJSONObject(i);
					temp[i][0] = mNew.getString("time");
					temp[i][1] = mNew.getString("title");
					temp[i][2] = mNew.getString("titleID");
				}
				allDatas = temp;
				adapter.setData(temp);
				adapter.notifyDataSetChanged();
				Logger.d("RomaZXListBaseSherlockFragment", "--- read data from cache ---");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void req(){
	}

	public class ZXListener extends UINetReceiveListener{

		public ZXListener(Activity activity) {
			super(activity);
		}
		
		@Override
		protected void onSuccess(NetMsg msg, AProtocol ptl) {
			// TODO Auto-generated method stub
			super.onSuccess(msg, ptl);
			ZXListProtocol zxList = (ZXListProtocol) ptl;
			int count = zxList.resp_count;
			if (count == 0) {
				if(mPullRefreshListView != null)
					mPullRefreshListView.onRefreshComplete();
				return;
			}
			datas = new String[count][3];
			for (int i = 0; i < count; i++) {
//				datas[i][0] = zxList.resp_time[i].substring(5);
//				datas[i][1] = zxList.resp_title[i];
				datas[i][0] = ZXDataUtils.handleDateFormat(zxList.resp_time[i], Res.getInteger(R.integer.config_zixun_time_format_type));
				datas[i][1] = ToDBC(zxList.resp_title[i]);
				
				datas[i][2] = zxList.resp_titleId[i];
			}
			if (tempDatas != null && tempDatas.length > 0) {
				allDatas = new String[tempDatas.length + datas.length][3];
				for (int i = 0; i < allDatas.length; i++) {
						if (i < tempDatas.length) {
							allDatas[i] = tempDatas[i];
						} else {
							allDatas[i] = datas[i - tempDatas.length];
						}
				}
			} else if(isRefresh){
				int len = allDatas.length;
				if (len >= 20) {
					for (int i = 0; i < count; i++) {
						allDatas[len - 20 + i] = datas[i];
					}
				} else {
					allDatas = datas;
				}
			}  else {
				allDatas = datas;
			}
			tempDatas = allDatas;
			adapter.setData(allDatas);
			adapter.notifyDataSetChanged();
			if(mPullRefreshListView != null)
				mPullRefreshListView.onRefreshComplete();
			saveToCache(allDatas);
		}
		
		@Override
		protected void onShowStatus(int status, NetMsg msg) {
			// TODO Auto-generated method stub
			super.onShowStatus(status, msg);
			if(mPullRefreshListView != null)
				mPullRefreshListView.onRefreshComplete();
			if (mActivity != null ) {
				((BaseSherlockFragmentActivity)mActivity).hideNetReqProgress();
			}
		}
		
	}

	public static String ToDBC(String input) {  
		   char[] c = input.toCharArray();  
		   for (int i = 0; i< c.length; i++) {  
		       if (c[i] == 12288) {  
		         c[i] = (char) 32;  
		         continue;  
		       }if (c[i]> 65280&& c[i]< 65375)  
		          c[i] = (char) (c[i] - 65248);  
		       }  
		   return new String(c);  
		}  
	private void saveToCache(String[][] data){
		if (data != null && data.length > 0) {
			romaCache.remove(cacheKey);
			StringBuilder sb = new StringBuilder();
			sb.append("{\"news\":[");//头
			for (int i = 0; i < data.length; i++) {
				sb.append("{");
				sb.append("\"time\":\"");
				sb.append(data[i][0]);
				sb.append("\",");
				
				sb.append("\"title\":\"");
				sb.append(data[i][1]);
				sb.append("\",");
				
				sb.append("\"titleID\":\"");
				sb.append(data[i][2]);
				sb.append("\"");
				sb.append("}");
				if (i != data.length - 1) {
					sb.append(",");
				}
			}
			sb.append("]}");//尾
			try {
				romaCache.put(cacheKey, new JSONObject(sb.toString()));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private class ZXListAdapter extends BaseAdapter{
		
		private Context mContext;
		private LayoutInflater mInflater;
		private String[][] data;
		
		public ZXListAdapter(Context context){
			mContext = context;
			mInflater = LayoutInflater.from(context);
		}
		
		public void setData(String[][] data){
			this.data = data;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return data == null ? 0 : data.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder = null;
			if (convertView == null) {
				viewHolder = new ViewHolder();
				convertView = mInflater.inflate(R.layout.roma_zx_base_item_layout, null);
				viewHolder.txt_title = (TextView) convertView.findViewById(R.id.txt_zx_title);
				viewHolder.txt_time = (TextView) convertView.findViewById(R.id.txt_zx_time);
				viewHolder.zx_divider = convertView.findViewById(R.id.roma_zx_line);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
//			viewHolder.txt_title.setTextColor(Res.getColor(R.color.zx_title_color));
//			viewHolder.txt_time.setTextColor(Res.getColor(R.color.zx_time_color));
//			viewHolder.txt_title.setTextColor(Res.getColor(R.color.listmaintitleColor_unread));
//			viewHolder.txt_time.setTextColor(Res.getColor(R.color.timeColor_unread));
			viewHolder.txt_title.setTextColor(ROMA_SkinManager.getColor("gegudetail_zx_title_textColor"));
			viewHolder.txt_time.setTextColor(ROMA_SkinManager.getColor("gegudetail_zx_time_textColorNew",Res.getColor(R.color.timeColor_unread)));
			viewHolder.zx_divider.setBackgroundColor(ROMA_SkinManager.getColor("ZXlineColor"));
			for (int i = 0; i < clickList.size(); i++) {
				if (data[position][2].equals(clickList.get(i))) {
//					viewHolder.txt_title.setTextColor(Res.getColor(R.color.zx_onclick_color));
//					viewHolder.txt_time.setTextColor(Res.getColor(R.color.zx_onclick_color));
					viewHolder.txt_title.setTextColor(Res.getColor(R.color.listmaintitleColor_read));
					viewHolder.txt_time.setTextColor(Res.getColor(R.color.timeColor_read));
				}
			}
//			viewHolder.txt_time.setText(data[position][0]);
//			viewHolder.txt_title.setText(data[position][1]);
			viewHolder.txt_time.setText(data[position][0]);
			viewHolder.txt_title.setText(ToDBC(data[position][1]));
			if(Res.getBoolean(R.bool.kconfigs_ZXListItem_isNoAsXmlReference)) {
				viewHolder.txt_title.setTextSize(Res.getDimen(R.dimen.zixun_title_textsize2));
				viewHolder.txt_title.setLineSpacing(Res.getDimen(R.dimen.zixun_title_textsize2), (float) 0.75);
			}

			if(position == datas.length - 1)
				viewHolder.zx_divider.setVisibility(View.GONE);
			else
				viewHolder.zx_divider.setVisibility(View.VISIBLE);

			return convertView;
		}
		public int getFontHeight(float fontSize)  
		{  
		     Paint paint = new Paint();  
		     paint.setTextSize(fontSize);  
		     FontMetrics fm = paint.getFontMetrics();  
		    return (int) Math.ceil(fm.descent - fm.ascent) + 2;  
		}  
		private class ViewHolder{
			TextView txt_title;
			TextView txt_time;
			View zx_divider;
		}
		
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		ROMA_SkinManager.removeOnSkinChangeListener(this);
	}

	@Override
	public void onSkinChanged(String skinTypeKey) {
		rl_zixun.setBackgroundColor(SkinManager.getColor("contentBackgroundColor"));
		mPullRefreshListView.setBackgroundColor(SkinManager.getColor("contentBackgroundColor"));
	}
}
