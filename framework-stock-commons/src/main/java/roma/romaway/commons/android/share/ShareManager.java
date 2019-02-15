package roma.romaway.commons.android.share;

import android.app.Activity;
import android.content.Context;

import com.romaway.commons.lang.StringUtils;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018-12-21.
 */
public class ShareManager {

    private static ShareManager shareManager;

    // 上下文对象
    private Context context;

    private ShareManager(Context context) {
        this.context = context;
    }

    public static ShareManager getInstance(Context context) {

        if (shareManager == null) {
            shareManager = new ShareManager(context);
        }
        return shareManager;
    }

    public static SHARE_MEDIA[] Install(Activity mActivity, Context context, SHARE_MEDIA[] share_media) {
        List<SHARE_MEDIA> display = new ArrayList<>();
        SHARE_MEDIA[] displaylist = null;
        for (int i = 0; i < share_media.length; i++) {
            boolean isInstall = UMShareAPI.get(context).isInstall(mActivity, share_media[i]);
            if (isInstall) {
                display.add(share_media[i]);
            }
        }
        displaylist = new SHARE_MEDIA[display.size()];
        for (int i = 0; i < display.size(); i++) {
            displaylist[i] = display.get(i);
        }
        return displaylist;
    }

}
