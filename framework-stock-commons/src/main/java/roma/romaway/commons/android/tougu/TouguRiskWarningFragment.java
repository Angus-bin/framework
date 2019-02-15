package roma.romaway.commons.android.tougu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.actionbarsherlock.view.Menu;
import com.romaway.activity.basephone.BaseSherlockFragment;
import com.romaway.android.phone.R;
import com.romaway.android.phone.utils.ColorUtils;
import com.romaway.common.android.base.Res;
import com.romawaylibs.theme.ROMA_SkinManager;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * Created by hongrb on 2016/12/6 13:44
 */
public class TouguRiskWarningFragment extends BaseSherlockFragment implements ROMA_SkinManager.OnSkinChangeListener, View.OnClickListener {

    private TextView tv_title, tv_subTitle, tv_detail;
    private LinearLayout ll_zuhe_risk_bottom;

    private ImageView img_left, img_right;

    private TextView txt_left, txt_title;

    private RelativeLayout rl_img_right;

    private LinearLayout ll_img_left;

    @Override
    public void onResumeInit() {
        super.onResumeInit();
//        mActionBar.resetTitleToDefault();
        mActionBar.hide();
        mActionBar.hideBottomBar();
        onSkinChanged(null);
    }

    public void setTitle(CharSequence text) {
        if (txt_title != null) {
            txt_title.setText(text);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.roma_risk_warning_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        img_left = (ImageView) view.findViewById(R.id.img_left);
        img_right = (ImageView) view.findViewById(R.id.img_right);
        txt_left = (TextView) view.findViewById(R.id.txt_left);
        txt_title = (TextView) view.findViewById(R.id.txt_title);
        txt_title.setTextColor(0xffffffff);
        rl_img_right = (RelativeLayout) view.findViewById(R.id.rl_img_right);
        ll_img_left = (LinearLayout) view.findViewById(R.id.ll_img_left);
        ll_img_left.setOnClickListener(this);

        tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_subTitle = (TextView) view.findViewById(R.id.tv_sub_title);
        tv_detail = (TextView) view.findViewById(R.id.tv_risk_detail);
        ll_zuhe_risk_bottom = (LinearLayout) view.findViewById(R.id.ll_zuhe_risk_bottom);
        setLineSpacing(new TextView[]{tv_detail});
        tv_detail.setText(Res.getString(R.string.roma_zuhe_risk_warning_context));
        ll_zuhe_risk_bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backHomeCallBack();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mActionBar.hide();
        mActionBar.hideBottomBar();
        setTitle("信息购买服务说明");
        if (txt_left != null) {
            txt_left.setVisibility(View.GONE);
        }
        if (img_left != null) {
            img_left.setImageBitmap(ColorUtils.getAlphaBitmap(ColorUtils.convertDrawableToBitmap(Res.getDrawable(R.drawable.title_back)), 0xffffffff));
            img_left.setVisibility(View.VISIBLE);
        }
        if (img_right != null) {
            img_right.setVisibility(View.GONE);
        }
    }

    /** 设置TextView 字体行间距 */
    private void setLineSpacing(TextView[] textViews){
        if(textViews != null && textViews.length > 0){
            for (TextView textView : textViews){
                textView.setLineSpacing(AutoUtils.getPercentHeightSize(10), 1.0f);
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getSherlockActivity().getSupportActionBar().setMenuLayout(-1, null);
//        mActionBar.hideSearchButton();
//        mActionBar.hideRefreshButton();
//        mActionBar.hideRightText();
    }

    @Override
    public void onSkinChanged(String skinTypeKey) {
        setWindowBackgroundColor(ROMA_SkinManager.getColor("contentBackgroundColor", 0xffe5e6e6));
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.ll_img_left) {
            backHomeCallBack();
        }
    }
}
