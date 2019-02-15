package com.romaway.framework.app;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;

public class FragmentSwitchUtil {

    //private static final String TAG = "FragmentSwitchUtil";
    //private static final boolean DEBUG = false;

    private final FragmentManager mFragmentManager;
    private FragmentTransaction mCurTransaction = null;
    public Fragment mCurrentPrimaryItem = null;

    public FragmentSwitchUtil(FragmentManager fm) {
        mFragmentManager = fm;
    }

    public void switchFagment(int contentId, Fragment switchFragment) {
        if (mCurTransaction == null) {
            mCurTransaction = mFragmentManager.beginTransaction();
        }
        
        // Do we already have this fragment?
        String name = makeFragmentName(switchFragment);  
        Fragment fragment = mFragmentManager.findFragmentByTag(name);

        if (fragment != null) {
            //if (DEBUG) Log.v(TAG, "Attaching item #" + position + ": f=" + fragment);
            ((SherlockFragment)fragment).setIntent(null);
            ((SherlockFragment)fragment).setIntent(((SherlockFragment)switchFragment).getIntent());
            mCurTransaction.attach(fragment);
        } else {
            fragment = switchFragment;
            //if (DEBUG) Log.v(TAG, "Adding item #" + position + ": f=" + fragment);
            ((SherlockFragment)fragment).setIntent(((SherlockFragment)switchFragment).getIntent());
            mCurTransaction.add(contentId, fragment,
                    makeFragmentName(fragment));
        }
        if (fragment != mCurrentPrimaryItem) {
            fragment.setMenuVisibility(false);
            fragment.setUserVisibleHint(false);
        }

        setPrimaryItem(fragment);
        
        finishUpdate();
    }

    /**
     * 移除Fragment
     * @param fragment
     */
    public void removeFragment(Fragment fragment){  
        if (mCurTransaction == null) {
            mCurTransaction = mFragmentManager.beginTransaction();
        }
            mCurTransaction.remove(fragment);
    }
    
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (mCurTransaction == null) {
            mCurTransaction = mFragmentManager.beginTransaction();
        }
        mCurTransaction.detach((Fragment)object);
    }

    public void setPrimaryItem(Fragment fragment) {
        if (fragment != mCurrentPrimaryItem) {
            if (mCurrentPrimaryItem != null) {
                mCurrentPrimaryItem.setMenuVisibility(false);
                mCurrentPrimaryItem.setUserVisibleHint(false);
            }
            if (fragment != null) {
                fragment.setMenuVisibility(true);
                fragment.setUserVisibleHint(true);
            }
            mCurrentPrimaryItem = fragment;
        }
    }

    public void finishUpdate() {
        if (mCurTransaction != null) {
            mCurTransaction.commitAllowingStateLoss();
            mCurTransaction = null;
            mFragmentManager.executePendingTransactions();
        }
    }

    public boolean isViewFromObject(View view, Object object) {
        return ((Fragment)object).getView() == view;
    }

    private static String makeFragmentName(Fragment fragment) {
        return "android:switcher:" + fragment.getClass().getName()+":"+((SherlockFragment) fragment).getRomaTag();
    }
}
