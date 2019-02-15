package com.romaway.framework.app;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment.InstantiationException;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.android.basephone.widget.R;
import com.romalibs.utils.Res;
import com.romaway.framework.view.SysInfo;

/**
 * 切换Fragment、Fragment栈管理
 * @author wanlh
 *
 */
public class RomaWayFragmentActivity extends SherlockFragmentActivity{
	
	    /**Fragment 栈，保存被启动的Fragment*/
		private List<SherlockFragment> fragmentStack = new ArrayList<SherlockFragment>();
		
		private FragmentManager mFragmentManager;
		public FragmentSwitchUtil mFragmentSwitchUtil;
		
	 @Override
		protected void onCreate(Bundle arg0) {
			// TODO Auto-generated method stub
			super.onCreate(arg0);
			mFragmentManager = getSupportFragmentManager();
			mFragmentSwitchUtil = new FragmentSwitchUtil(mFragmentManager);
		}

	 /**
	  * fragment入栈处理
	  * @param fragment
	  */

	 Fragment myFragment;
		public void switchFragmentForStack(Fragment fragment){
			myFragment = fragment;
	    	switchFragment(fragment, false);
	    }
	    
		/**
		  * fragment不入栈处理
		  * @param fragment
		  */
	    public void switchFragmentForStackNull(Fragment fragment){
	    	switchFragment(fragment, true);
	    }
	    
	    public void initFragmentForStack(Fragment fragment){
	    	switchFragment(fragment, false);
	    }
	    
	    /**activity 加载第一个Fragment时一定要先不入栈操作*/
	    public void initFragmentForStackNull(Fragment fragment){  
	    	switchFragment(fragment, true);
	    }
	    private void switchFragment(Fragment fragment, boolean init){
	    	
	    	//发现栈中已经存在该Fragment，就不再入栈，而是直接非入栈启动
	    	int stackIndex = getFragmentStackIndex(fragment);
	    	if(stackIndex >= 0){
	    		fragmentStack = setItem2Bottom(fragmentStack, stackIndex);
	    		init = true;
	    	}
	    	

	    	this.savePreFragment();
	    	if(mPreFragment != null) {
				if (mPreFragment != fragment) {
					mPreFragment.onPause();//在同一个activity中切换时，上一个Fragment不会回调暂停方法，固在此手动调用
				}
			}
	    	
	    	
    		if(!init)
	        	fragmentStack.add((SherlockFragment)fragment);
    		
//    		for(int  i = 0; i < fragmentStack.size();i++){
//                Logger.d("tag", "fragmentStack Fragment name = "+
//                        fragmentStack.get(i).getClass().getName()+"");  
//            }
    		
    		mFragmentSwitchUtil.switchFagment(android.R.id.content, fragment);
    		
//	        FragmentTransaction ft = mFragmentManager.beginTransaction();
//	        ft.replace(android.R.id.content, fragment);
//	        //if(!init)
//	          //ft.addToBackStack(null);//入栈，用于finish时仅仅杀掉fragment
//	            
//	        ft.commit();
	        
	      //该log非常重要，不可删除它，用于在log信息中查看启动当前的Fragment所在的包名和类名，对调试程序有很大帮助
	    	Log.i("FragmentManager","Displayed switchFragment "+this.getPackageName() + "/"+fragment.getClass().getName());
	    }
	    
	    /**
	     * 切换其它模块中的Fragment 不入栈
	     * @param fragmentClassName  目标Fragment类名（包括 包名）
	     */
	    public void switchFragmentForStackNull(String fragmentClassName){
	    	Fragment tagFragment = null;
			try {
				tagFragment = (Fragment) 
						Class.forName(fragmentClassName).getConstructor().newInstance();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (java.lang.InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(tagFragment != null)
				switchFragmentForStackNull(tagFragment);
			else
				Toast.makeText(this, "请检查类包名+类名是否正确！", Toast.LENGTH_LONG).show();
	    }
	    
	    /**
	     * 切换其它模块中的Fragment 入栈
	     * @param fragmentClassName  目标Fragment类名（包过包名）
	     */
	    public void switchFragmentForStack(String fragmentClassName){
	    	Fragment tagFragment = null;
			try {
				tagFragment = (Fragment) 
						Class.forName(fragmentClassName).getConstructor().newInstance();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (java.lang.InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(tagFragment != null)
				switchFragmentForStack(tagFragment);
			else
				Toast.makeText(this, "请检查类包名+类名是否正确！", Toast.LENGTH_LONG).show();
	    }
	    
	    /**
         * 切换其它模块中的Fragment 不入栈 
         * 可通过Intnet传递参数,其中设置action时必须是报名+类名的Fragment的字符串
         * @param fragmentClassName  目标Fragment类名（包括 包名）
         */
        public void switchFragmentForStackNull(Intent intent){
            SherlockFragment tagFragment = null;
            String fragmentClassName = intent.getAction();
            try {
                tagFragment = (SherlockFragment) 
                        Class.forName(fragmentClassName).getConstructor().newInstance();
                tagFragment.setIntent(intent);
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InstantiationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }catch (java.lang.InstantiationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
            if(tagFragment != null){  
			    tagFragment.setIntent(intent);
                switchFragmentForStackNull(tagFragment);
            }else
                Toast.makeText(this, this.getResources().getString(
                		R.string.abs__function_enable_toast), Toast.LENGTH_LONG).show();
        }
        
        /**
         * 切换其它模块中的Fragment 入栈
         * 可通过Intnet传递参数,其中设置action时必须是报名+类名的Fragment的字符串
         * @param fragmentClassName  目标Fragment类名（包过包名）
         */
        public void switchFragmentForStack(Intent intent){
            SherlockFragment tagFragment = null;
            
            String fragmentClassName = intent.getAction();
            try {
                tagFragment = (SherlockFragment) 
                        Class.forName(fragmentClassName).getConstructor().newInstance();
                tagFragment.setIntent(intent);
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InstantiationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }catch (java.lang.InstantiationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
            if(tagFragment != null){
            	tagFragment.setIntent(intent);
                switchFragmentForStack(tagFragment);
            }else
                SysInfo.showMessage(this, Res.getString(R.string.abs__home_function_enable_toast));
        }
        
		@Override
		public void finish() {
			// TODO Auto-generated method stub
			int count = getBackStackEntryCount();
			if(count >= 2){
				mFragmentSwitchUtil.removeFragment(fragmentStack.get(count - 1));
				fragmentStack.remove(count - 1);
				
				switchFragmentForStackNull(fragmentStack.get(count - 2));
	    		//mFragmentManager.popBackStack();
			}else
	    		super.finish();
		}
		
		public void finishActivity() {
	    	super.finish();
		}
	    
		public void removePreviousFragment(){
		    //int count = getBackStackEntryCount();
            //if(count >= 2)
            //    mFragmentSwitchUtil.removeFragment(fragmentStack.get(count - 1));
		}
		
		/**获取栈中Fragment 的个数*/
		public int getBackStackEntryCount(){
			
			return fragmentStack.size();//mFragmentManager.getBackStackEntryCount();
		}
		
		/**获取栈中所有的Fragment,注：并不是栈中的顺序，而是栈中所包含有的Fragment*/
		public List<SherlockFragment> getFragmentList(){
			
			return fragmentStack;//mFragmentManager.getFragments();
		}
		
		/**
		 * 栈中是否包含currentFragment 
		 * @param currentFragment
		 * @return  -1 不包含，否则返回栈中的索引
		 */
		public int getFragmentStackIndex(Fragment currentFragment){
			//List<Fragment> fragmentList = getFragmentList();
			if(fragmentStack != null){
				for(int i = 0; i < fragmentStack.size(); i++){
				    SherlockFragment fragment = fragmentStack.get(i);
					if(fragment != null){
						if(fragment.getClass().getName().equals(currentFragment.getClass().getName()) 
						        && fragment.getRomaTag().equals(((SherlockFragment)currentFragment).getRomaTag()))
							return i;
					}
				}
			}
			return -1;
		}
		/**上一个的Fragment实例对象*/
		private Fragment mPreFragment = null;
		/**结束栈中所有的Fragment,但是第一个被启动的Fragment仍然保存在栈中*/
		public void popBackAllStack(){
			//先保存上一个Fragment实例
			savePreFragment();
		   // if(count > 0){
    		//	Fragment fragment0 = fragmentStack.get(0);
    			fragmentStack.clear();
    		//	switchFragmentForStack(fragment0);
		  //  }
    		/*int count = fragmentStack.size();
			Logger.d("tag", "6-wanlaihuanindex[个数]="+count);
			for(int i = 0; i < count;i++){
	    		Logger.d("tag", "7-wanlaihuanindex["+i+"]="+fragmentStack.get(i).getClass().getSimpleName());
	    	}*/
		}
		
		private void savePreFragment(){
			if(fragmentStack.size() > 0){
	    		Fragment oldFragment = fragmentStack.get(fragmentStack.size() - 1);
	    		if(oldFragment != null){
	    			mPreFragment = oldFragment;
	    		}
	    	}
		}
		
		/**
         * 获取当前的Fragment类名
         * @return
         */
        public SherlockFragment getCurrentFragmentClassName(){
            if(fragmentStack != null && fragmentStack.size() > 0){
                SherlockFragment currentStackFragment = fragmentStack.get(fragmentStack.size() - 1);
                Log.i("FragmentManager","FragmentManagerFragmentManager onResumeInit getName:"+
                        currentStackFragment.getClass().getName()+","+getClass().getName());
                return currentStackFragment;//.getClass().getName();
            }
            return null;
        }
        
		@Override
		public boolean dispatchKeyEvent(KeyEvent event) {
			if (event.getAction() == KeyEvent.ACTION_DOWN
			        && event.getKeyCode() == KeyEvent.KEYCODE_BACK)
			{
			    
			    if(getSupportActionBar() != null && 
			            getSupportActionBar().getKeyboardVisibility() != View.GONE){
	                getSupportActionBar().hideKeyboard();
	                return true;
	            }else
	                backKeyCallBack();
	                
				return true;
			}  
			return super.dispatchKeyEvent(event);
		}
		
		/**
		 * 监听返回键
		 */
		public void backKeyCallBack(){
		    finish();
		}
		/**
		 * 置底功能
		 * @param <T>
		 * @param itemList
		 * @param from 其实项索引
		 * @return
		 */
		private <T> List<T> setItem2Bottom(final List<T> itemList, int from){
			
			int count = itemList.size();
			if(from >= count-1)
				return itemList;
			
			List<T> newItemList = itemList;
			T tempData = itemList.get(from);
			
			for(int i = from+1; i < count; i++)
				newItemList.set(i-1, itemList.get(i));
			
			newItemList.set(count-1, tempData);
			
			return newItemList;
		}
		
}
