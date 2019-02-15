package com.romaway.framework.view;

import com.android.basephone.widget.R;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class RomaWayProgressDialog extends Dialog{
	private static com.romaway.framework.view.RomaWayProgressDialog mDialog;
	private LinearLayout mRoot;
	private LinearLayout mChildParent;
	private ProgressBar mProgressBar;
	private TextView mTextView;
	private Context mContext;
	
	/**
	 * @param context
	 * @return
	 */
	/*public static RomaWayProgressDialog getInstanceAndShow(Context context){
		//if(mDialog == null)
			mDialog = new RomaWayProgressDialog(context);
		mDialog.show();
		return mDialog;
	}
	
	public static RomaWayProgressDialog getInstance(Context context){
		//if(mDialog == null)
			mDialog = new RomaWayProgressDialog(context);
			return mDialog;
		}*/
	
	public RomaWayProgressDialog(Context context) {
		this(context, R.style.Theme_progressDialog);
		// TODO Auto-generated constructor stub
		
		mContext = context;
		getCodeLayout(context);
		
	}

	private ViewGroup getCodeLayout(Context context){
		
		mRoot = new LinearLayout(context);
		mRoot.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		mRoot.setOrientation(LinearLayout.VERTICAL);
		mRoot.setGravity(Gravity.CENTER_HORIZONTAL);
		//mRoot.setBackgroundResource(R.drawable.abs__progress_dialog_bg);

		mProgressBar = new ProgressBar(context);
        mProgressBar.setLayoutParams(new LayoutParams(45, 45));
        mProgressBar.setIndeterminateDrawable(context.getResources().getDrawable(R.drawable.abs__progress_small));
        mProgressBar.setIndeterminate(true);
        
        View view = new View(context);
        view.setLayoutParams(new LayoutParams(1, 10));
        
        mTextView = new TextView(context);
        mTextView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        mTextView.setTextSize(14);
        mTextView.setTextColor(Color.BLACK);
        setMessage("");
        
        mChildParent = new LinearLayout(context);
        mChildParent.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mChildParent.setOrientation(LinearLayout.VERTICAL);
        mChildParent.setGravity(Gravity.CENTER_HORIZONTAL);
        mChildParent.setPadding(15, 15, 15, 15);
        mChildParent.addView(mProgressBar);
        mChildParent.addView(view);
        mChildParent.addView(mTextView);
        
        mRoot.addView(mChildParent);
        
        return mRoot;
	}
	
	public RomaWayProgressDialog(Context context, int theme) {
		super(context, theme);
		// TODO Auto-generated constructor stub
		setCanceledOnTouchOutside(true);
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

		Window window = getWindow();
		window.requestFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(mRoot);
		
	}
	
	public void setBackgroundResource(int resid){
		if(resid > 0)
			mRoot.setBackgroundResource(resid);
		
	}
	
	public void setMessage(CharSequence text){
		
		if(text.toString().equals("")){
			mTextView.setVisibility(View.GONE);
			return;
		}
		
		mTextView.setVisibility(View.VISIBLE);
		mTextView.setText(text);
	}
	
}
