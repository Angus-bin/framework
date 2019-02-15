package com.romaway.android.phone.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.romaway.android.phone.utils.DrawableUtils;
import com.romaway.android.phone.R;
import com.romaway.android.phone.utils.ColorUtils;
import com.romaway.android.phone.widget.DialogFactory;
import com.romaway.common.android.base.Res;
import com.trevorpage.tpsvg.SVGView;

import roma.romaway.commons.android.theme.svg.SVGManager;

/**
 * Created by edward on 16/11/4.
 */
public class RomaImageDialog extends Dialog implements View.OnClickListener{

    public Context mContext;
    protected Button mLeftButton;
    protected Button mRightButton;
    private View mTitleView, mCenterView = null;

    // 接收的参数:
    private CharSequence mTitle, mContentMsg;       // 支持String, SpannableStringBuilder
    private String leftBtnText, rightBtnText;
    private OnClickDialogBtnListener leftBtnListener, rightBtnListener;
    private TextView msgTextView;
    private int dialogType;

    public RomaImageDialog(Context context, int theme) {
        super(context, theme);
        mContext = context;
    }

    public RomaImageDialog(Context context) {
        this(context, R.style.Theme_CustomDialog);
    }

    public RomaImageDialog(Context context, CharSequence title, CharSequence message,
                           String leftBtnText, OnClickDialogBtnListener leftBtnListener,
                           String rightBtnText, OnClickDialogBtnListener rightBtnListener) {
        this(context, -1, -1, title, message, null, leftBtnText, leftBtnListener, rightBtnText, rightBtnListener);
    }

    public RomaImageDialog(Context context, CharSequence title, CharSequence message, Bitmap imageBitmap,
                           String leftBtnText, OnClickDialogBtnListener leftBtnListener,
                           String rightBtnText, OnClickDialogBtnListener rightBtnListener) {
        this(context, -1, -1, title, message, imageBitmap, leftBtnText, leftBtnListener, rightBtnText, rightBtnListener);
    }

    public RomaImageDialog(Context context, int titleLayoutId, int msglayoutId,
                           CharSequence title, CharSequence message, Bitmap imageBitmap,
                           OnClickDialogBtnListener leftBtnListener, OnClickDialogBtnListener rightBtnListener) {
        this(context, titleLayoutId, msglayoutId, title, message, imageBitmap, null, leftBtnListener, null, rightBtnListener);
    }

    public RomaImageDialog(Context context, int titleLayoutId, int msglayoutId,
                           CharSequence title, CharSequence message, int imageResId,
                           String leftBtnText, OnClickDialogBtnListener leftBtnListener,
                           String rightBtnText, OnClickDialogBtnListener rightBtnListener) {
        this(context, titleLayoutId, msglayoutId, title, message,
                null, leftBtnText, leftBtnListener, rightBtnText, rightBtnListener);
        setTopImageBitmap(imageResId);
    }

    public RomaImageDialog(Context context, int titleLayoutId, int msglayoutId,
                           CharSequence title, CharSequence message, int dialogType, int imageResId,
                           String leftBtnText, OnClickDialogBtnListener leftBtnListener,
                           String rightBtnText, OnClickDialogBtnListener rightBtnListener) {
        this(context, titleLayoutId, msglayoutId, title, message,
                null, leftBtnText, leftBtnListener, rightBtnText, rightBtnListener);
        this.dialogType = dialogType;
        if(dialogType == DialogFactory.DIALOG_TYPE_APP_UPDATE){
            dialogType = R.layout.yt_list_text_view;
            mCenterView = LayoutInflater.from(mContext).inflate(dialogType, null);
        }
        setTopImageBitmap(imageResId);
    }

    public RomaImageDialog(Context context, int titleLayoutId, int msglayoutId,
                           CharSequence title, CharSequence message, Bitmap imageBitmap,
                           String leftBtnText, OnClickDialogBtnListener leftBtnListener,
                           String rightBtnText, OnClickDialogBtnListener rightBtnListener) {
        this(context, R.style.Theme_CustomDialog);
        this.mTitle = title;
        this.mContentMsg = message;
        this.mImageBitmap = imageBitmap;
        this.leftBtnText = leftBtnText;
        this.rightBtnText = rightBtnText;
        this.leftBtnListener = leftBtnListener;
        this.rightBtnListener = rightBtnListener;

        if(titleLayoutId <= 0)
            titleLayoutId = R.layout.yt_dialog_single_title_layout;
        mTitleView = LayoutInflater.from(mContext).inflate(titleLayoutId, null);

        if(msglayoutId <= 0)
            msglayoutId = R.layout.yt_scroll_text_view;
        mCenterView = LayoutInflater.from(mContext).inflate(msglayoutId, null);
    }
    private void initCenterView(View view){
        String centerMsg = null;
        String[] split = null;
        if(mContentMsg!=null) {
            centerMsg = mContentMsg.toString();
            split = centerMsg.split("\\|");
        }

        ListView lv_msg = (ListView) view.findViewById(R.id.lv_msg);
        CenterViewMsgListAdapter centerAdapter = new CenterViewMsgListAdapter(split);
        View gradient_bar = findViewById(R.id.iv_gradient_bar_image);
        if(centerAdapter.getCount()>3){
            gradient_bar.setVisibility(View.VISIBLE);
        }else {
            gradient_bar.setVisibility(View.GONE);
        }
        lv_msg.setAdapter(centerAdapter);
    }

    class CenterViewMsgListAdapter extends BaseAdapter{
        String[] msg;
        CenterViewMsgListAdapter(String [] msg){
            this.msg = msg;
        }
        @Override
        public int getCount() {
            if (msg == null)
                return 0;
            else{
                return msg.length;
            }
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            CenterViewMsgHolder holder = null;
            if(convertView==null){
                convertView = parent.inflate(mContext,R.layout.yt_listview_items_view,null);
                holder = new CenterViewMsgHolder(convertView);
                convertView.setTag(holder);
            }else{
                holder = (CenterViewMsgHolder) convertView.getTag();
            }
            holder.items_index.setText(""+(position+1));
            holder.items_msg.setText(msg[position]);
            return convertView;
        }

        class CenterViewMsgHolder{
            TextView items_index;
            TextView items_msg;
            CenterViewMsgHolder(View root){
                items_index = (TextView) root.findViewById(R.id.center_items_index);
                items_msg = (TextView) root.findViewById(R.id.center_items_msg);
            }
        }
    }
    private View rootView;
    private ImageView mDialogImage;
    private FrameLayout mDialogTitle, mDialogCenter;
    private View divider_top, divider_bottom, divider_line,v_space;
    private Bitmap mImageBitmap;

    protected View closeView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.requestFeature(Window.FEATURE_NO_TITLE);
        if(dialogType == DialogFactory.DIALOG_TYPE_CONFIRM){
            setContentView(R.layout.yt_dialog_layout_new);
        } else if(dialogType == DialogFactory.DIALOG_TYPE_APP_UPDATE) {
            setContentView(R.layout.yt_dialog_layout2);
        } else {
            setContentView(R.layout.yt_dialog_layout);
        }

        rootView = this.findViewById(R.id.root);
        // 对话框圆角背景:
        rootView.setBackgroundDrawable(DrawableUtils.getShapeDrawable(0xffffffff, Res.getInteger(R.integer.roma_dialog_layout_bg_corner)));

        mDialogImage = (ImageView) this.findViewById(R.id.iv_dialog_topImage);
        mDialogTitle = (FrameLayout) this.findViewById(R.id.dialog_title);
        mDialogCenter = (FrameLayout)this.findViewById(R.id.dialog_center_info);

        mLeftButton = (Button) this.findViewById(R.id.CancleButton);
        mRightButton = (Button) this.findViewById(R.id.SureButton);

        divider_top = this.findViewById(R.id.divider_top);
        divider_bottom = this.findViewById(R.id.divider_bottom);
        divider_line = this.findViewById(R.id.divider_line);
        v_space = this.findViewById(R.id.v_space);

        addTitleView(mTitleView);
        addCenterView(mCenterView);

        if (mTitle != null)
            ((TextView)mDialogTitle.findViewById(R.id.title_text)).setText(mTitle);

        if (mContentMsg != null&&dialogType != DialogFactory.DIALOG_TYPE_APP_UPDATE) {
            msgTextView = ((TextView) mCenterView.findViewById(R.id.message_view));
            msgTextView.setText(Html.fromHtml(mContentMsg.toString()));
            msgTextView.post(new Runnable() {
                @Override
                public void run() {
                    if (msgTextView.getLineCount() > 1)
                        msgTextView.setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL);
                    else
                        msgTextView.setGravity(Gravity.CENTER);

                    View gradient_bar = findViewById(R.id.iv_gradient_bar_image);
                    if (gradient_bar != null) {
                        if (msgTextView.getLineCount() > 5)
                            gradient_bar.setVisibility(View.VISIBLE);
                        else
                            gradient_bar.setVisibility(View.GONE);
                    }
                }
            });
        }

        if(mContentMsg != null&&dialogType == DialogFactory.DIALOG_TYPE_APP_UPDATE){
            initCenterView(mCenterView);
        }

        if(dialogType == DialogFactory.DIALOG_TYPE_APP_UPDATE){
            v_space.setVisibility(View.VISIBLE);
        }

        if(dialogType == DialogFactory.DIALOG_TYPE_CONFIRM) {
            ((SVGView) this.findViewById(R.id.svg_dialog_close)).setSVGRenderer(SVGManager.getSVGParserRenderer(mContext, "roma_svg_close", R.drawable.roma_svg_close), null);
            closeView = findViewById(R.id.fl_dialog_close);
            closeView.setOnClickListener(this);
        }

        setLeftButtonText(leftBtnText);
        setRightButtonText(rightBtnText);

        setOnClickLeftButtonListener(leftBtnListener);
        setOnClickRightButtonListener(rightBtnListener);
    }

    /** 获取 Dialog 根布局对象, 个性化设置title或centerView控件: */
    public View getDialogRootView(){
        return rootView;
    }

    /** 获取 Dialog 对象布局中指定id控件: */
    public View findViewByIdOnDialog(int id){
        if(rootView != null)
            return rootView.findViewById(id);
        return null;
    }

    // 设置 Dialog 分割线颜色:
    public void setDialogDivider(int topDividerColor, int bottomDividerColor, int btnDividerColor){
        divider_top.setBackgroundColor(topDividerColor);
        divider_bottom.setBackgroundColor(bottomDividerColor);
        divider_line.setBackgroundColor(btnDividerColor);
    }

    public void setLeftButtonText(String text){
        if(mLeftButton != null && !TextUtils.isEmpty(text))
            mLeftButton.setText(text);
    }

    public void setRightButtonText(String text){
        if(mRightButton != null && !TextUtils.isEmpty(text))
            mRightButton.setText(text);
    }

    public void setOnClickLeftButtonListener(OnClickDialogBtnListener l){
        leftBtnListener = l;
        if(l != null){
            mLeftButton.setVisibility(View.VISIBLE);
            divider_line.setVisibility(View.VISIBLE);
            mLeftButton.setOnClickListener(this);
        }else{
            mLeftButton.setVisibility(View.GONE);
            divider_line.setVisibility(View.GONE);
        }
    }

    public void setOnClickRightButtonListener(OnClickDialogBtnListener l){
        rightBtnListener = l;
        if(l != null){
            mRightButton.setVisibility(View.VISIBLE);
            mRightButton.setOnClickListener(this);
        }else{
            mRightButton.setVisibility(View.GONE);
        }
    }

    public void setOnClickLeftButtonListener(String text, OnClickDialogBtnListener l){
        setLeftButtonText(text);
        setOnClickLeftButtonListener(l);
    }

    public void setOnClickRightButtonListener(String text, OnClickDialogBtnListener l){
        setRightButtonText(text);
        setOnClickRightButtonListener(l);
    }

    @Override
    public void setTitle(int titleId) {
        mTitle = mContext.getResources().getString(titleId);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title.toString();
    }

    @Override
    public void show() {
        super.show();
        addTitleView(mTitleView);
        addCenterView(mCenterView);

        if(mTitle == null){
            mDialogTitle.setVisibility(View.GONE);
            divider_top.setVisibility(View.GONE);
        }else{
            mDialogTitle.setVisibility(View.VISIBLE);
            divider_top.setVisibility(View.VISIBLE);
        }

        if(mImageBitmap != null) {
            mDialogImage.setImageBitmap(mImageBitmap);
            mDialogImage.setVisibility(View.VISIBLE);
        }else
            mDialogImage.setVisibility(View.INVISIBLE);
    }

    public void setTopImageBitmap(Bitmap imageBitmap){
        this.mImageBitmap = imageBitmap;
    }

    public void setTopImageBitmap(int drawableId){
        if (drawableId > 0) {
            setTopImageBitmap(ColorUtils.convertDrawableToBitmap(
                    mContext.getResources().getDrawable(drawableId)));
        }
    }

    // --- 个性化设置 Title 内容区域 ----
    private void setTitleView(int layoutId){
        if(mDialogTitle.getChildCount() > 0)
            mDialogTitle.removeAllViews();

        View view = LayoutInflater.from(mContext).inflate(layoutId, null);
        setTitleView(view);
    }
    public void setTitleView(View view){
        mTitleView = view;
    }
    private void addTitleView(View child){
        if(mDialogTitle.getChildCount() > 0)
            mDialogTitle.removeAllViews();

        if(child != null)
            mDialogTitle.addView(child);
    }

    // --- 个性化设置 Center文本 内容区域 ----
    public void setCenterView(int layoutId){
        View view = LayoutInflater.from(mContext).inflate(layoutId, null);
        setCenterView(view);
    }
    public void setCenterView(View view){
        mCenterView = view;
    }
    private void addCenterView(View child){
        if(mDialogCenter.getChildCount() > 0)
            mDialogCenter.removeAllViews();

        if(child != null)
            mDialogCenter.addView(child);
    }

    public boolean isCancelable = true;
    /**
     * Sets whether this dialog is cancelable with the
     * {@link KeyEvent#KEYCODE_BACK BACK} key.
     */
    public void setCancelable(boolean flag) {
        super.setCancelable(flag);
        isCancelable = flag;
    }

    private boolean isDismissDialog = true;
    public void setOnClickDismissDialog(boolean flag){
        isDismissDialog = flag;
    }

    @Override
    public void onClick(View view) {
        isDismissDialog = true;

        if (view.getId() == R.id.CancleButton) {
            if (leftBtnListener != null)
                leftBtnListener.onClickButton(view);

        } else if (view.getId() == R.id.SureButton) {
            if (rightBtnListener != null)
                rightBtnListener.onClickButton(view);
        }

        if (isDismissDialog)
            this.dismiss();
    }
}
