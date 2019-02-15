package com.romaway.android.phone.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.romaway.android.phone.R;

/**
 * 包含列表的对话框
 * @author chenjp
 *
 */
public class RomaListDialog extends Dialog {
	/**
	 * 标题
	 */
	private String titleStr = null;
	private Context mContext;
	private LinearLayout ll_title;
	private LinearLayout ll_content;
	private TextView txt_title;
	/**
	 * 列表项点击事件
	 */
	private OnDialogItemClickListener mListener;
	/**
	 * 列表项
	 */
	private String[] mItems;
	/**
	 * 列表项颜色
	 */
	private int[] mColors;
	private LayoutInflater mInflater;
	private Dialog mDialog;

	public RomaListDialog(Context context) {
		this(context, R.style.Theme_CustomDialog);
	}

	public RomaListDialog(Context context, int theme) {
		super(context, theme);
		mContext = context;
	}
	
	public RomaListDialog(Context context, String[] items, OnDialogItemClickListener listener){
		this(context, R.style.Theme_CustomDialog);
		this.mItems = items;
		this.mListener = listener;
	}
	
	/**
	 * @param context
	 * @param items 列表项
	 * @param colors 列表项颜色
	 * @param listener 列表项点击事件
	 */
	public RomaListDialog(Context context, String[] items, int[] colors, OnDialogItemClickListener listener){
		this(context, R.style.Theme_CustomDialog);
		this.mItems = items;
		this.mColors = colors;
		this.mListener = listener;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		Window window = getWindow();
		window.requestFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.roma_list_dialog_layout);
		mDialog = this;
		
		LinearLayout ll_root = (LinearLayout) this.findViewById(R.id.ll_root);
		ShapeDrawable mSd = new ShapeDrawable(
                new RoundRectShape(new float[] { 12, 12, 12, 12, 12, 12, 12, 12 }, null,
                null));
		mSd.getPaint().setColor(0xffffffff);
		ll_root.setBackgroundDrawable(mSd);
		
		ll_title = (LinearLayout) this.findViewById(R.id.dialog_title);
		txt_title = (TextView) this.findViewById(R.id.title_text);
		
		ll_content = (LinearLayout) this.findViewById(R.id.ll_content);
		mInflater = LayoutInflater.from(mContext);
		
		if (mItems != null && mItems.length > 0 && mItems.length == mColors.length) {
			for (int i = 0; i < mItems.length; i++) {
				final int index = i;
				View view = mInflater.inflate(R.layout.roma_list_dialog_item_layout, null);
				TextView txt_item = (TextView) view.findViewById(R.id.txt_item);
				txt_item.setText(mItems[i]);
				txt_item.setTextColor(mColors[i]);
				txt_item.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						mListener.onItemClick(mDialog, index);
						mDialog.dismiss();
					}
				});
				ll_content.addView(view);
			}
		}
	}
	
	@Override
	public void setTitle(int titleId) {
		titleStr = mContext.getResources().getString(titleId);
	}

	@Override
	public void setTitle(CharSequence title) {
		titleStr = title.toString();
	}
	
	/**
	 * 列表项点击事件
	 * @author chenjp
	 *
	 */
	public interface OnDialogItemClickListener{
		public void onItemClick(DialogInterface dialog, int which);
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		super.show();
		if (titleStr == null) {
			ll_title.setVisibility(View.GONE);
			txt_title.setText("");
		} else {
			ll_title.setVisibility(View.VISIBLE);
			txt_title.setText(titleStr);
		}
	}
}
