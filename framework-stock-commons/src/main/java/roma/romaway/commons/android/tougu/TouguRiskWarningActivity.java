package roma.romaway.commons.android.tougu;

import android.os.Bundle;

import com.romaway.activity.basephone.BaseSherlockFragmentActivity;
import com.umeng.message.PushAgent;

import cn.magicwindow.mlink.annotation.MLinkRouter;

/**
 * Created by hongrb on 2016/12/6 11:24
 */
public class TouguRiskWarningActivity extends BaseSherlockFragmentActivity {

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        PushAgent.getInstance(this).onAppStart();
        TouguRiskWarningFragment mTouguRiskWarningFragment = new TouguRiskWarningFragment();
        initFragmentForStack(mTouguRiskWarningFragment);
    }

}
