package roma.romaway.commons.android.tougu;

import android.content.Context;
import android.content.res.Resources;

import com.romaway.android.phone.R;

/**
 * Created by hongrb on 2017/12/27.
 */
public class ADFilterTool {

    public static boolean hasAd(Context context, String url){
        Resources res= context.getResources();
        String[]adUrls =res.getStringArray(R.array.adBlockUrl);
        for(String adUrl : adUrls){
            if(url.contains(adUrl)){
                return true;
            }
        }
        return false;
    }

}
