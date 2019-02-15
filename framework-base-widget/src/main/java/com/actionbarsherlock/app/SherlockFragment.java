package com.actionbarsherlock.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.internal.view.menu.MenuItemWrapper;
import com.actionbarsherlock.internal.view.menu.MenuWrapper;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.android.basephone.widget.R;
import com.romaway.common.android.base.OriginalContext;
import com.romaway.framework.view.CustomProgressDialog;
import com.trevorpage.tpsvg.SVGParserRenderer;

import static android.support.v4.app.Watson.OnCreateOptionsMenuListener;
import static android.support.v4.app.Watson.OnOptionsItemSelectedListener;
import static android.support.v4.app.Watson.OnPrepareOptionsMenuListener;

public class SherlockFragment extends Fragment implements OnCreateOptionsMenuListener, OnPrepareOptionsMenuListener, OnOptionsItemSelectedListener {
    public SherlockFragmentActivity mActivity;
    public ActionBar mActionBar = null;
    
    public SherlockFragmentActivity getSherlockActivity() {
        return mActivity;
    }

    private Intent intent = null;
    public void setIntent(Intent intent){
        this.intent = intent;
    }
    
    public Intent getIntent(){
        return intent;
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onViewCreated(view, savedInstanceState);
        
    }

    @Override
    public void onAttach(Activity activity) {
        if (!(activity instanceof SherlockFragmentActivity)) {
            throw new IllegalStateException(getClass().getSimpleName() + " must be attached to a SherlockFragmentActivity.");
        }
        mActivity = (SherlockFragmentActivity)activity;
        mActionBar = mActivity.getSupportActionBar();
        
      //该log非常重要，不可删除它，用于在log信息中查看启动当前的Fragment所在的包名和类名，对调试程序有很大帮助
    	Log.i("FragmentManager","Displayed switchFragment "+mActivity.getPackageName() + "/"+getClass().getName());
        super.onAttach(activity);
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (this.getView() != null){
            this.getView().setVisibility(menuVisible ? View.VISIBLE : View.GONE);
            if(menuVisible){
                if(menuVisible){ 
                    onResume();
                }else
                    onPause();
            }
        }
    }
    
    /**
     * 用于切换时进行初始化
     */
    public void onResumeInit(){
        
        //Log.d("tag", "wanlaihuan................onResumeInit "+this.getClass().getName());
        if(getView() != null && getView().getVisibility() == View.VISIBLE)
            getView().setBackgroundColor(backgroundColor);
        //设置ActionBar背景
        //mActionBar.setBackgroundColor(mActivity.getResources().getColor(R.color.abs__action_bar_background_color));
        if (mActivity != null && !mActivity.isFinishing()) {
            //为Activity重新设置Fragment并通知重新加载
            mActivity.setSherlockFragment(this);
            //当切换Fragment时，通知Fragment重新加载菜单项
            mActivity.supportInvalidateOptionsMenu();
            //向Activity传递被启动的Fragment类名
            mActivity.onFragmentResumeListener(this);
        }
        
       //默认进入时时显示的
        mActionBar.show();
        //mActionBar.showIcon();
        //mActionBar.setIcon(R.drawable.abs__back_btn);
        mActionBar.setLeftSvgIcon(new SVGParserRenderer(OriginalContext.getContext(), R.drawable.abs__navigation_back));
    }
    
    @Override
    public void onDetach() {
        mActivity = null;
        super.onDetach();
    }

    private int backgroundColor = 0xffffffff;
    /**设置fragment主题背景颜色*/
    public void setWindowBackgroundColor(int color){
        backgroundColor = color;
        if (this.getView() != null){
            getView().setBackgroundColor(color);
        }
    }
    
    /**获取fragment主题背景颜色*/
    public int getWinowVackgroundColor(){
        return backgroundColor;
    }
    
/*+ add by wanlaihuan 2014-06-21 通过Fragment onCreateOptionsMenu创建菜单*/
    @Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
//		root.startAnimation(animation);
		if(getView() != null && getView().getVisibility() == View.VISIBLE)
		    onResumeInit();
	}
    
    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        
    }

    /**创建右上角菜单*/ 
    public void onCreateOptionsMenu(Menu menu) {
        
        mActivity.getSupportActionBar().getActionBarView().
            setMenuDefaultLayout(R.layout.roma_common_actionbar_right_layout);
        
    }
    
/*- add by wanlaihuan 2014-06-21 通过Fragment onCreateOptionsMenu创建菜单*/
	
    /**不入栈*/
    public void switchFragmentForStackNull(Fragment fragment){
    	((com.romaway.framework.app.RomaWayFragmentActivity)mActivity).switchFragmentForStackNull(fragment);
    }
    
    public void switchFragmentForStack(Fragment fragment){
    	((com.romaway.framework.app.RomaWayFragmentActivity)mActivity).switchFragmentForStack(fragment);
    }
    
    /**
     * 切换其它模块中的Fragment 不入栈
     * @param fragmentClassName  目标Fragment类名（包过包名）
     */
    public void switchFragmentForStackNull(String fragmentClassName){
    	((com.romaway.framework.app.RomaWayFragmentActivity)mActivity).switchFragmentForStackNull(fragmentClassName);
    }
    
    /**
     * 切换其它模块中的Fragment 入栈
     * @param fragmentClassName  目标Fragment类名（包过包名）
     */
    public void switchFragmentForStack(String fragmentClassName){
    	((com.romaway.framework.app.RomaWayFragmentActivity)mActivity).switchFragmentForStack(fragmentClassName);
    }
    
    /**
     * 切换其它模块中的Fragment 不入栈 
     * 可通过Intnet传递参数,其中设置action时必须是报名+类名的Fragment的字符串
     * @param fragmentClassName  目标Fragment类名（包括 包名）
     */
    public void switchFragmentForStackNull(Intent intent){
        ((com.romaway.framework.app.RomaWayFragmentActivity)mActivity).switchFragmentForStackNull(intent);
    }
    
    /**
     * 切换其它模块中的Fragment 不入栈 
     * 可通过Intnet传递参数,其中设置action时必须是报名+类名的Fragment的字符串
     * @param fragmentClassName  目标Fragment类名（包括 包名）
     */
    public void switchFragmentForStack(Intent intent){
        ((com.romaway.framework.app.RomaWayFragmentActivity)mActivity).switchFragmentForStack(intent);
    }
    
    private String romaTag = "";
    public void setRomaTag(String tag){
        romaTag = tag;
    }
    
    public String getRomaTag(){
        return romaTag;
    }
    
    @Override
    public final void onCreateOptionsMenu(android.view.Menu menu, android.view.MenuInflater inflater) {
        onCreateOptionsMenu(new MenuWrapper(menu), mActivity.getSupportMenuInflater());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //Nothing to see here.
    }

    @Override
    public final void onPrepareOptionsMenu(android.view.Menu menu) {
        onPrepareOptionsMenu(new MenuWrapper(menu));
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        //Nothing to see here.
    }

    @Override
    public final boolean onOptionsItemSelected(android.view.MenuItem item) {
        return onOptionsItemSelected(new MenuItemWrapper(item));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Nothing to see here.
    	int id = item.getItemId();
    	if(id == android.R.id.home){
			if(mActivity.getSupportActionBar().getKeyboardVisibility() != View.GONE){
			    mActivity.getSupportActionBar().hideKeyboard();
			    return false;
			}else
			    backHomeCallBack();  
		}
        return false;
    }
    
    /**
     * 点击左上角返回按钮时会回调该方法
     */
    public void backHomeCallBack(){
        mActivity.finish();
    }
    protected CustomProgressDialog pd;

	public void showNetReqDialog(Context context)
	{
		this.showNetReqDialog("", context);
	}

	/**
	 * 显示带文本文字进度条
	 * @param context
	 * @param message
	 */
	public void showNetReqDialog(Context context, String message)
	{
		this.showNetReqDialog(message, context);
	}
	
	public void showNetReqDialog(String msg, Context context)
	{
		
		if (pd == null)
		{
			if(context != null){
				pd = CustomProgressDialog.createDialog(context);//new RomaWayProgressDialog(context);
				pd.setMessage(msg);
				pd.show();
			}

		} else {
			pd.setMessage(msg);
			
			if (!pd.isShowing())
			{
				pd.show();
			}
		}
	}

    public void hideNetReqDialog() {
        try {
            if (pd != null) {
                if (pd.isShowing()) {
                    pd.dismiss();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pd = null;
        }
    }

    /**
	 * 设置监听请求对话框结束
	 * @param listener
	 */
	public void setOnNetReqDialogDismissListener(OnDismissListener listener){
		if(pd != null){
			pd.setOnDismissListener(listener);
		}
	}
	
	/**
	 * 显示对话框
	 * 
	 * @param title
	 *            对话框标题
	 * @param message
	 *            内容
	 * @param okButton
	 *            确定按钮文字,若为null,则表示不显示此按钮
	 * @param cancelButton
	 *            取消按钮文字，若为null,则表示不显示此按钮
	 * @param okOnClickListener
	 *            确定按钮响应事件，若为null,则默认为关闭Diolog
	 * @param cancelOnClickListener
	 *            取消按钮响应时间，若未null,则默认为关闭Diolog
	 */
	public void showDialog(String title, String message, String okButton,
	        String cancelButton,
	        DialogInterface.OnClickListener okOnClickListener,
	        DialogInterface.OnClickListener cancelOnClickListener)
	{

		Builder b = new AlertDialog.Builder(mActivity);
		b.setTitle(title);
		b.setMessage(message);

		if (!TextUtils.isEmpty(okButton))
		{
			if (okOnClickListener == null)
			{
				b.setPositiveButton(okButton,
				        new DialogInterface.OnClickListener()
				        {

					        @Override
					        public void onClick(DialogInterface dialog,
					                int which)
					        {
						        dialog.dismiss();
					        }
				        });
			} else
			{
				b.setPositiveButton(okButton, okOnClickListener);
			}

		}

		if (!TextUtils.isEmpty(cancelButton))
		{
			if (cancelOnClickListener == null)
			{
				b.setNegativeButton(cancelButton,
				        new DialogInterface.OnClickListener()
				        {

					        @Override
					        public void onClick(DialogInterface dialog,
					                int which)
					        {
						        dialog.dismiss();
					        }
				        });
			} else
			{
				b.setNegativeButton(cancelButton, cancelOnClickListener);
			}
		}

		b.show();
	}

}
