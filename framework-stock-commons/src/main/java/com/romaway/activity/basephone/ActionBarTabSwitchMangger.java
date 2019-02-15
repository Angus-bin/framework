package com.romaway.activity.basephone;


import roma.romaway.commons.android.theme.SkinManager;

import com.actionbarsherlock.app.ActionBar;
import com.romaway.android.phone.widget.RomaToast;
import com.romaway.framework.view.RomaWaySwitchTowView;
import com.romaway.android.phone.R;
import com.romaway.commons.lang.StringUtils;

public class ActionBarTabSwitchMangger implements RomaWaySwitchTowView.OnClickItemSwitchListener{
    private BaseSherlockFragmentActivity mSherlockActivity;
    private ActionBar mActionBar; 
    private int currentItem = 0;
    
    public ActionBarTabSwitchMangger(BaseSherlockFragmentActivity sherlockFragmentActivity){
        mSherlockActivity = sherlockFragmentActivity;
        mActionBar = sherlockFragmentActivity.getSupportActionBar();
    }

    public int getCurrentItem(){
     return currentItem;   
    }
    
    /**
     * 设置当前选项
     * @param itemIndex
     */
    public void setCurrentIntem(int itemIndex){
    	 currentItem = itemIndex;
    }
    
    /**
     * 初始化数据
     * @param actionBarTabConInfoArr
     */
    public void initData(ActionBarTabConInfo[] actionBarTabConInfoArr){
    	if(actionBarTabConInfoArr == null)
    		return;
    	
    	mActionBarTabConInfoArr = actionBarTabConInfoArr;
    }
    
    /**
     * 初始化控件
     * @param itemIndex
     */
    public void initTab(int itemIndex){
    	setCurrentIntem(itemIndex);
        //重新设置为行情Title
        mActionBar.resetTitle(R.layout.abs__title_switch_widget);
        
		if(mActionBarTabConInfoArr == null){
			RomaToast.showMessage(mSherlockActivity, " [错误]请先调用 initData(...)方法进行初始化数据！");
			return;
		}
		
        RomaWaySwitchTowView mSwitchTowView = (RomaWaySwitchTowView) mSherlockActivity.findViewById(R.id.SwitchTowView);
        mSwitchTowView.init(this, new String[]{mActionBarTabConInfoArr[0].funName,
        		mActionBarTabConInfoArr[1].funName}, 
                new int[]{SkinManager.getColor("actionbar_switchTab_selected_bgColor"),
        				  SkinManager.getColor("actionbar_switchTab_unselected_bgColor")}, 
			    new int[]{SkinManager.getColor("actionbar_switchTab_selected_fontColor"),
    				      SkinManager.getColor("actionbar_switchTab_unselected_fontColor")},
                SkinManager.getColor("actionbar_switchTab_borderColor"));
        mSwitchTowView.setSelectedItem(currentItem, false);
    }
    
    /**
     * 通过功能KEY初始化控件
     * @param funKey
     */
    public void initTab(String funKey){
    	if(mActionBarTabConInfoArr == null){
			RomaToast.showMessage(mSherlockActivity, " [错误]请先调用 initData(...)方法进行初始化数据！");
			return;
		}
    	
    	for(int i = 0; i < mActionBarTabConInfoArr.length; i++){
        	if(funKey.contains(mActionBarTabConInfoArr[i].funKey)){
        		if(!StringUtils.isEmpty(mActionBarTabConInfoArr[i].baseSherlockFragmentContent)){
        			initTab(mActionBarTabConInfoArr[i].index);//funKey转化成了索引
        		}
        	}
        }
    }
    
    /**
     * 通过功能key获取Tab索引
     * @param funKey
     * @return
     */
    public int funkey2Index(String funKey){
    	if(mActionBarTabConInfoArr == null){
			RomaToast.showMessage(mSherlockActivity, " [错误]请先调用 initData(...)方法进行初始化数据！");
			return 0;
		}
    	
    	for(int i = 0; i < mActionBarTabConInfoArr.length; i++){
        	if(funKey.contains(mActionBarTabConInfoArr[i].funKey)){
        		if(!StringUtils.isEmpty(mActionBarTabConInfoArr[i].baseSherlockFragmentContent)){
        			return mActionBarTabConInfoArr[i].index;//funKey转化成了索引
        		}
        	}
        }
    	
    	return 0;
    }
    
    public ActionBarTabConInfo[] getActionBarTabConInfoArr(){
    	return mActionBarTabConInfoArr;
    }
    
    public ActionBarTabConInfo getCurrentActionBarTabConInfo(){
    	return mActionBarTabConInfoArr[getCurrentItem()];
    }
    
    private ActionBarTabConInfo[] mActionBarTabConInfoArr = new ActionBarTabConInfo[2];
    @Override
    public void onClickItem(int item) {
        // TODO Auto-generated method stub
        //先全部退栈
        mSherlockActivity.popBackAllStack();
        
        setCurrentIntem(item);
        
        for(int i = 0; i < mActionBarTabConInfoArr.length; i++){
        	if(item == mActionBarTabConInfoArr[i].index){
        		if(!StringUtils.isEmpty(mActionBarTabConInfoArr[i].baseSherlockFragmentContent)){
        			//currentActionBarTabConInfo = mActionBarTabConInfoArr[i];
        			mSherlockActivity.switchFragmentForStack(mActionBarTabConInfoArr[i].baseSherlockFragmentContent);  
        		}
        	}
        }
    }
}
