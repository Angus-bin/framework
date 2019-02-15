package com.romaway.android.phone.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Bundle;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.romaway.android.phone.R;

public class RomaHorizontalProgressDialog extends Dialog{
	
	private ProgressBar progressBar;
	private TextView tv_progress_inner;
	private TextView tv_progress_bottom;
	
	public RomaHorizontalProgressDialog(Context context) {
		this(context, R.style.Theme_CustomDialog);
	//	super(context);
		// TODO Auto-generated constructor stub
	}

	public RomaHorizontalProgressDialog(Context context, int theme) {
		super(context, theme);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

		Window window = getWindow();
		window.requestFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.abs__horizotal_dialog_layout);
		
		LinearLayout root = (LinearLayout) this.findViewById(R.id.root);
		ShapeDrawable mSd = new ShapeDrawable(
                new RoundRectShape(new float[] { 5, 5, 5, 5, 5, 5, 5, 5 }, null,
                null));
		mSd.getPaint().setColor(0x00000000);
		root.setBackgroundDrawable(mSd);
		
		progressBar = (ProgressBar) this.findViewById(R.id.pb_progressbar);
		tv_progress_inner = (TextView) this.findViewById(R.id.tv_progress_inner);
		tv_progress_bottom = (TextView) this.findViewById(R.id.tv_progress_bottom);
		
	}
	
	public ProgressBar getProgressBar(){
		return progressBar;
	}
	
	public TextView getCenterTextView(){
		return tv_progress_inner;
	}
	
	public TextView getBottomTextView(){
		return tv_progress_bottom;
	}
}
