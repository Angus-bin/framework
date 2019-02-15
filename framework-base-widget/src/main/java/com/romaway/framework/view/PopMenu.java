package com.romaway.framework.view;
import java.util.ArrayList;

import com.android.basephone.widget.R;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PopMenu {
	private ArrayList<String> itemList = new ArrayList<String>();
	private Context context;
	private PopupWindow popupWindow ;
	private ListView listView;
	private PopAdapter mPopAdapter;
	
	private OnItemClickListener mOnItemClickListener = null;
	public void setOnItemClickListener(OnItemClickListener onItemClickListener){
		mOnItemClickListener = onItemClickListener;
	}
	public interface OnItemClickListener{
		public void onTextSelected(CharSequence text);
	}
	
	private OnClearClickListener mOnClearClickListener = null;
	public void setOnClearClickListener(OnClearClickListener onClearClickListener){
		mOnClearClickListener = onClearClickListener;
	}
	public interface OnClearClickListener{
		public void onClearClick();
	}
	
	/**
	 * 锟斤拷锟斤拷popwindow锟斤拷锟斤拷锟斤拷
	 * @param onDismissListener
	 */
	public void setOnDismissListener(OnDismissListener onDismissListener){
		popupWindow.setOnDismissListener(onDismissListener);
	}
	
	public PopMenu(Context context, int width, int listHeight) {
		// TODO Auto-generated constructor stub
		this.context = context;

		View view = LayoutInflater.from(context).inflate(R.layout.popmenu, null);
        listView = (ListView)view.findViewById(R.id.listView);
        
        popupWindow = new PopupWindow(view, width, LayoutParams.WRAP_CONTENT);
        // 锟斤拷锟斤拷锟轿拷说锟斤拷锟斤拷锟斤拷锟斤拷Back锟斤拷也锟斤拷使锟斤拷锟斤拷失
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
       
        mPopAdapter = new PopAdapter();
        listView.setAdapter(mPopAdapter);
	}

	//锟斤拷锟斤拷锟斤拷硬说锟斤拷锟�
	public void addItems(String[] items) {
		for (String s : items)
			itemList.add(s);
	}

	//锟斤拷锟斤拷锟斤拷硬说锟斤拷锟�
	public void addItem(String item) {
		itemList.add(item);
	}

	//锟斤拷锟斤拷式 锟斤拷锟斤拷 pop锟剿碉拷 parent 锟斤拷锟铰斤拷
	public void showAsDropDown(View parent) {
		popupWindow.showAsDropDown(parent, 0, 0);
		// 使锟斤拷奂锟�
        popupWindow.setFocusable(true);
        // 锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷失
        popupWindow.setOutsideTouchable(true);
        //刷锟斤拷状态
        popupWindow.update();
	}
	
	//锟斤拷锟截菜碉拷
	public void dismiss() {
		popupWindow.dismiss();
	}
	
	private void notifyDataSetInvalidated(){
		mPopAdapter.notifyDataSetInvalidated();
	}
	// 锟斤拷锟斤拷锟斤拷
	private final class PopAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return itemList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return itemList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder;
			//if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(R.layout.pomenu_item, null);
				holder = new ViewHolder();
				
				holder.position = position;
				holder.title = (TextView) convertView.findViewById(R.id.textView);
				holder.item = (RelativeLayout) convertView.findViewById(R.id.ListItem); 
				holder.clearButton = (Button) convertView.findViewById(R.id.RemoveButton); 
				holder.title.setText((String)getItem(position));
				convertView.setTag(holder);
				
			//} else {
			//	holder = (ViewHolder) convertView.getTag();
			//}
			
			holder.item.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(mOnItemClickListener != null){
						ViewHolder holder1 = (ViewHolder)((View)v.getParent()).getTag();
						mOnItemClickListener.onTextSelected(holder1.title.getText());
					}
				}
			});
			
			holder.clearButton.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					ViewHolder holder1 = (ViewHolder)((View)v.getParent().getParent()).getTag();
					itemList.remove(holder1.position);
					notifyDataSetInvalidated();
					if(itemList.size() == 0)
						dismiss();
					
					if(mOnClearClickListener != null)
						mOnClearClickListener.onClearClick();
				}
			} );
			return convertView;
		}

		private final class ViewHolder {
			int position;
			RelativeLayout item;
			TextView title;
			Button clearButton;
		}
	}
}
