package com.romaway.framework.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.basephone.widget.R;
import com.romawaylibs.theme.ROMA_SkinManager;

public class RomaWayRadioButton extends FrameLayout implements ROMA_SkinManager.OnSkinChangeListener{

	private  LinearLayout ll_parent;
	private android.widget.RadioButton mRadioButton;
	private TextView mLeftTextView;
	private TextView mRightTextView;
	private int selectId = -1;
	private RelativeLayout mRl_parent;
	private View mDivider_bottom;

	public RomaWayRadioButton(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}

	public RomaWayRadioButton(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		// TODO Auto-generated constructor stub
	}

	public RomaWayRadioButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		LayoutInflater inflater = LayoutInflater.from(context);
		inflater.inflate(R.layout.roma_radio_button, this, true);

		mRadioButton = (android.widget.RadioButton) this.findViewById(R.id.radio_icon_view);
		mLeftTextView = (TextView) this.findViewById(R.id.txt_left_text);
		mRightTextView = (TextView) this.findViewById(R.id.txt_right_text);
		mDivider_bottom = (View) this.findViewById(R.id.v_ser_sel_divider);
		ll_parent = (LinearLayout) this.findViewById(R.id.ll_parent);

		mRadioButton.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
				// TODO Auto-generated method stub
				if(mOnRadioCheckedChangedListener != null && checked == true)
					mOnRadioCheckedChangedListener.onRadioCheckedChanged(selectId, checked);
			}
		});

		mRl_parent = (RelativeLayout) this.findViewById(R.id.rl_radio_parent);
		mRl_parent.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (!mRadioButton.isChecked()) {
					mRadioButton.setChecked(true);
				}
			}
		});
		
		setOnClickListener(new OnClickListener(){

			public void onClick(View view) {
				// TODO Auto-generated method stub
				mRadioButton.setChecked(true);
			}
		});
		ROMA_SkinManager.setOnSkinChangeListener(this);
		onSkinChanged(null);
	}
	
	public void setLeftText(CharSequence text){
		mLeftTextView.setText(text);
	}
	
	public void setRightText(CharSequence text){
		mRightTextView.setText(text);
	}

	OnRadioCheckedChangedListener mOnRadioCheckedChangedListener = null;
	public void setOnCheckedChangeWidgetListener(OnRadioCheckedChangedListener onRadioCheckedListener){
		mOnRadioCheckedChangedListener = onRadioCheckedListener;
	}
	public interface OnRadioCheckedChangedListener{
		void onRadioCheckedChanged(int id, boolean checked);
	}
	
	public boolean isChecked(){
		return mRadioButton.isChecked();
	}
	
	public android.widget.RadioButton getRadioButton(){
		return mRadioButton;
	}
	
	public void setChecked(boolean checked){
		mRadioButton.setChecked(checked);
	}
	
	@Override
	public void setId(int id) {
		// TODO Auto-generated method stub
		super.setId(id);
		selectId = id;
	}

	@Override
	public void onSkinChanged(String skinTypeKey) {
		mRl_parent.setBackgroundColor(ROMA_SkinManager.getColor("kds_zhandian_switch_btnBgColor",0xffffffff));
		mLeftTextView.setTextColor(ROMA_SkinManager.getColor("kds_zhandian_LeftTextViewBgColor",0x666666));
		mRightTextView.setTextColor(ROMA_SkinManager.getColor("kds_zhandian_RightTextViewBgColor",0x333333));
		mDivider_bottom.setBackgroundColor(ROMA_SkinManager.getColor("kds_zhandian_Divider_bottoBgColor",0xf2f4f8));
		ll_parent.setBackgroundColor(ROMA_SkinManager.getColor("kds_zhandian_switch_btnBgColor",0xffffffff));
	}
}
