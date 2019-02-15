package com.romaway.android.phone.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.romaway.android.phone.utils.DrawableUtils;
import com.romaway.common.android.base.Res;
import com.romaway.android.phone.R;
import com.romaway.android.phone.widget.RomaListDialog.OnDialogItemClickListener;
import com.romaway.commons.lang.StringUtils;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * 包含列表的对话框
 * @author chenjp
 *
 */
public class RomaListImageDialog extends Dialog implements View.OnClickListener {
	/**
	 * 标题
	 */
	private String titleStr = null;
	private String subTitleStr = null;
	private Context mContext;
	private LinearLayout ll_title;
	private LinearLayout ll_content;
	private TextView txt_title;
	private TextView sub_txt_title;

	private LinearLayout mBottomRootroot;
	/**
	 * 列表项点击事件
	 */
	private OnDialogItemClickListener mListener;

	private OnClickDialogBtnListener leftListenter = null;
	private OnClickDialogBtnListener rightListenter = null;
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

	private ImageView mImageView;
	private int ImageId;

	private View divider_top;
	private View divider_bottom;
	protected Button mLeftButton;
	protected Button mRightButton;
	private String leftButtonText = null;
	private String rightButtonText = null;

	public RomaListImageDialog(Context context) {
		this(context, R.style.Theme_CustomDialog);
	}

	public RomaListImageDialog(Context context, int theme) {
		super(context, theme);
		mContext = context;
	}

	public RomaListImageDialog(Context context, String[] items, OnDialogItemClickListener listener){
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
	public RomaListImageDialog(Context context, String[] items, int[] colors, OnDialogItemClickListener listener){
		this(context, R.style.Theme_CustomDialog);
		this.mItems = items;
		this.mColors = colors;
		this.mListener = listener;
	}

	/**
	 *
	 * @param context
	 * @param title
	 * @param items 列表项
	 * @param colors 列表项颜色
	 * @param listener 列表项点击事件
	 */
	public RomaListImageDialog(Context context, String title, String subTitle, String[] items, int[] colors, OnDialogItemClickListener listener, String leftButtonText, OnClickDialogBtnListener leftButton, String rightButtonText, OnClickDialogBtnListener rightButton, int ImageId){
		this(context, R.style.Theme_CustomDialog);
		this.titleStr = title;
		this.subTitleStr = subTitle;
		this.mItems = items;
		this.mColors = colors;
		this.mListener = listener;
		this.ImageId = ImageId;
		leftListenter = leftButton;
		rightListenter = rightButton;
		this.leftButtonText = leftButtonText;
		this.rightButtonText = rightButtonText;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		Window window = getWindow();
		window.requestFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.roma_list_dialog_title_icon_layout);
		mDialog = this;
		
		RelativeLayout ll_root = (RelativeLayout) this.findViewById(R.id.root);
//		ShapeDrawable mSd = new ShapeDrawable(
//                new RoundRectShape(new float[] { 12, 12, 12, 12, 12, 12, 12, 12 }, null,
//                null));
//		mSd.getPaint().setColor(0xffffffff);
//		ll_root.setBackgroundDrawable(mSd);
		// 对话框圆角背景:
		ll_root.setBackgroundDrawable(DrawableUtils.getShapeDrawable(0xffffffff, Res.getInteger(R.integer.roma_dialog_layout_bg_corner)));
		
		ll_title = (LinearLayout) this.findViewById(R.id.dialog_title);
		txt_title = (TextView) this.findViewById(R.id.title_text);
		sub_txt_title = (TextView) this.findViewById(R.id.title_text2);
		mLeftButton = (Button) this.findViewById(R.id.CancleButton);
		mRightButton = (Button) this.findViewById(R.id.SureButton);

		divider_top = this.findViewById(R.id.divider_top);
		divider_bottom = this.findViewById(R.id.divider_bottom);

		mBottomRootroot = (LinearLayout)this.findViewById(R.id.bottom_rootroot);

		mImageView = (ImageView) this.findViewById(R.id.icon_dialog);

		if (ImageId > 0) {
			mImageView.setImageResource(ImageId);
		}

		switch (ImageId) {
			case -1: // -1 为确认取消弹出框
				mImageView.setVisibility(View.GONE);
				divider_top.setVisibility(View.GONE);
				if (!StringUtils.isEmpty(rightButtonText)) {
					mRightButton.setText(rightButtonText);
				}
				if (!StringUtils.isEmpty(leftButtonText)) {
					mLeftButton.setText(leftButtonText);
				}
				break;
			default:
				break;
		}

		ll_content = (LinearLayout) this.findViewById(R.id.ll_content);
		mInflater = LayoutInflater.from(mContext);
		
		if (mItems != null && mItems.length > 0 && mItems.length == mColors.length) {
			for (int i = 0; i < mItems.length; i++) {
				final int index = i;
				View view = mInflater.inflate(R.layout.roma_list_dialog_item_layout, null);
				TextView txt_item = (TextView) view.findViewById(R.id.txt_item);
				TextView txt_item_left = (TextView) view.findViewById(R.id.txt_item_left);
				TextView txt_item_right = (TextView) view.findViewById(R.id.txt_item_right);
				View divider = view.findViewById(R.id.divider_top);
				if (i == 0) {
					txt_item_left.setText("★ ");
					txt_item_left.setTextColor(mColors[i]);
					txt_item_right.setText(" ★");
					txt_item_right.setTextColor(mColors[i]);
				}
				if (i == mItems.length - 1){
					divider.setVisibility(View.INVISIBLE);
				}
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
			View view = new View(getContext());
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, AutoUtils.getPercentWidthSize(25));
			ll_content.addView(view, params);
		}

		//此两行需放在最后
		setOnClickLeftButtonListener(leftListenter);
		setOnClickRightButtonListener(rightListenter);

		if (leftListenter == null && rightListenter == null) {
			mBottomRootroot.setVisibility(View.GONE);
			divider_bottom.setVisibility(View.GONE);
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
	
//	/**
//	 * 列表项点击事件
//	 * @author chenjp
//	 *
//	 */
//	public interface OnDialogItemClickListener{
//		public void onItemClick(DialogInterface dialog, int which);
//	}

	OnClickDialogBtnListener mOnClickLeftButtonListener;
	OnClickDialogBtnListener mOnClickRightButtonListener;

	public void setLeftButtonText(String text){
		if(mLeftButton != null)
			mLeftButton.setText(text);
	}

	public void setRightButtonText(String text){
		if(mRightButton != null)
			mRightButton.setText(text);
	}

	public void setOnClickLeftButtonListener(OnClickDialogBtnListener l){
		mOnClickLeftButtonListener = l;
		if(l != null){
			mLeftButton.setVisibility(View.VISIBLE);
			mLeftButton.setOnClickListener(this);
		}else{
			mLeftButton.setVisibility(View.GONE);
		}
	}
	public void setOnClickRightButtonListener(OnClickDialogBtnListener l){
		mOnClickRightButtonListener = l;
		if(l != null){
			mRightButton.setVisibility(View.VISIBLE);
			mRightButton.setOnClickListener(this);
		}else{
			mRightButton.setVisibility(View.GONE);
		}
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
		if (StringUtils.isEmpty(subTitleStr)) {
			sub_txt_title.setText("");
			sub_txt_title.setVisibility(View.GONE);
		} else {
			sub_txt_title.setText(subTitleStr);
		}
	}

	private boolean isDismissDialog = true;
	public void setOnClickDismissDialog(boolean flag){
		isDismissDialog = flag;
	}

	@Override
	public void onClick(View view) {
		isDismissDialog = true;

		if(view.getId() == R.id.CancleButton){
			if(mOnClickLeftButtonListener != null)
				mOnClickLeftButtonListener.onClickButton(view);

		}else if(view.getId() == R.id.SureButton){
			if(mOnClickRightButtonListener != null)
				mOnClickRightButtonListener.onClickButton(view);
		}

		if(isDismissDialog)
			this.dismiss();
	}
}
