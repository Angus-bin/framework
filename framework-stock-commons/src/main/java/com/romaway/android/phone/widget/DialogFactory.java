package com.romaway.android.phone.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.ReflectionUtils;
import android.text.SpannableStringBuilder;
import android.view.View;

import com.romaway.android.phone.widget.dialog.RomaImageDialog;
import com.romaway.android.phone.KActivityMgr;
import com.romaway.android.phone.RomaUserAccount;
import com.romaway.android.phone.R;
//import RomaImageDialog;
import com.romaway.android.phone.widget.dialog.RomaListImageDialog;
import com.romaway.android.phone.widget.dialog.OnClickDialogBtnListener;
import com.romaway.common.android.base.Res;
import com.romaway.common.android.phone.ISubTabView;
import com.romaway.commons.log.Logger;

/**
 * Created by edward on 16/4/8.
 */
public class DialogFactory {

    /** Dialog单文本 */
    public static final String LAYOUT_TEXT_VIEW = "LAYOUT_TEXT_VIEW";
    /** Dialog支持滚动的文本 */
    public static final String LAYOUT_SCROLL_TEXT_VIEW = "LAYOUT_SCROLL_TEXT_VIEW";

    /** 确认类型(确认是否修改) */
    public static final int DIALOG_TYPE_CONFIRM = 100;
    /** 异常类型(网络错误) */
    public static final int DIALOG_TYPE_ERROR = 101;
    /** 提示类型(最新版本) */
    public static final int DIALOG_TYPE_EXCLAMATORY = 102;
    /** 疑问类型(确认是否升级或退出) */
    public static final int DIALOG_TYPE_QUESTION = 103;
    /** 公告类型 */
    public static final int DIALOG_TYPE_NOTICE = 104;
    public static final int DIALOG_TYPE_NEWS = 105;
    /** 版本升级类型 */
    public static final int DIALOG_TYPE_APP_UPDATE = 106;

    /** 无图标类型 */
    public static final int DIALOG_TYPE_NO_ICON = 110;
    /** 无图标且含副标题类型 */
    public static final int DIALOG_TYPE_NO_ICON_SUB = 111;

    /**
     * @param context           上下文对象
     * @param titleLayoutId     title布局
     * @param msgLayoutId       msg布局
     * @param title             title文本
     * @param message           msg文本
     * @param dialogType        dialog类型, 控制dialog顶部图标显示
     * @param leftBtnText       左按钮文本(为null时默认显示取消)
     * @param leftBtnListener   左按钮事件(为null时不显示左按钮)
     * @param rightBtnText      右按钮文本(为null时默认显示确定)
     * @param rightBtnListener  右按钮事件
     * @return
     */
    public static Dialog getIconDialog(Context context, int titleLayoutId, int msgLayoutId,
                                       CharSequence title, CharSequence message, int dialogType,
                                       String leftBtnText, OnClickDialogBtnListener leftBtnListener,
                                       String rightBtnText, OnClickDialogBtnListener rightBtnListener){
        RomaImageDialog romaImageDialog = new RomaImageDialog(context, titleLayoutId, msgLayoutId, title, message,
                getDialogIcon(dialogType), leftBtnText, leftBtnListener, rightBtnText, rightBtnListener);
        return romaImageDialog;
    }

    public static Dialog getIconDialog(Context context, CharSequence title, CharSequence message, int dialogType,
                                       String leftBtnText, OnClickDialogBtnListener leftBtnListener,
                                       String rightBtnText, OnClickDialogBtnListener rightBtnListener){
        // 根据dialogType类型获取特殊titleLayout/msgLayout布局
        int[] layoutIds = getLayoutId(dialogType);

        RomaImageDialog romaImageDialog = new RomaImageDialog(context, layoutIds[0], layoutIds[1], title, message,dialogType,
                                getDialogIcon(dialogType), leftBtnText, leftBtnListener, rightBtnText, rightBtnListener);
        if(dialogType == DIALOG_TYPE_APP_UPDATE){
            romaImageDialog = new RomaImageDialog(context, layoutIds[0], layoutIds[1], title, message,dialogType,
                    getDialogIcon(dialogType), leftBtnText, leftBtnListener, rightBtnText, rightBtnListener);
        }
        return romaImageDialog;
    }

    /**
     * level2 被踢对话框
     * @param context
     * @return
     */
    public static Dialog getLevel2OfflineDialog(final Context context){
        Dialog dialog = DialogFactory.getIconDialog(context,
                "您的level2授权手机号已在其他设备登录，请重新登录授权手机号查看level2行情",
                DialogFactory.DIALOG_TYPE_EXCLAMATORY,
                "知道了", new OnClickDialogBtnListener() {
                    @Override
                    public void onClickButton(View view) {

                        // 退出手机登录，清空所有数据
                        RomaUserAccount.clearUserAccountAllData((Activity)context);
                    }
                }, "立即登录", new OnClickDialogBtnListener() {
                    @Override
                    public void onClickButton(View view) {

                        // 退出手机登录，清空所有数据
                        RomaUserAccount.clearUserAccountAllData((Activity)context);

                        KActivityMgr
                                .switchWindow(
                                        (ISubTabView) context,
                                        "kds.szkingdom.modeinit.android.activity.login.UserLoginFragmentActivity",
                                        null, false);
                    }
                });
        dialog.setCancelable(false);
        return dialog;
    }

    public static Dialog getIconDialog(Context context, CharSequence message, int dialogType,
                                       String leftBtnText, OnClickDialogBtnListener leftBtnListener,
                                       String rightBtnText, OnClickDialogBtnListener rightBtnListener){
        // 根据dialogType类型获取特殊titleLayout/msgLayout布局:
        int[] layoutIds = getLayoutId(dialogType);

        RomaImageDialog romaImageDialog = new RomaImageDialog(context, layoutIds[0], layoutIds[1], getDialogTitle(dialogType), message,
                getDialogIcon(dialogType), leftBtnText, leftBtnListener, rightBtnText, rightBtnListener);
        return romaImageDialog;
    }

    public static Dialog getIconDialog(Context context, CharSequence title, CharSequence message, Bitmap imageBitmap,
                                   String leftBtnText, OnClickDialogBtnListener leftBtnListener,
                                   String rightBtnText, OnClickDialogBtnListener rightBtnListener){
        RomaImageDialog romaImageDialog = new RomaImageDialog(context, title, message,
                imageBitmap, leftBtnText, leftBtnListener, rightBtnText, rightBtnListener);
        return romaImageDialog;
    }

    public static Dialog getIconDialog(Context context, int titleLayoutId, CharSequence title, CharSequence message, Bitmap imageBitmap,
                                       String leftBtnText, OnClickDialogBtnListener leftBtnListener,
                                       String rightBtnText, OnClickDialogBtnListener rightBtnListener){
        RomaImageDialog romaImageDialog = new RomaImageDialog(context, titleLayoutId, -1, title, message,
                imageBitmap, leftBtnText, leftBtnListener, rightBtnText, rightBtnListener);
        return romaImageDialog;
    }

    public static Dialog getIconDialog(Context context, int titleLayoutId, int msgLayoutId, CharSequence title, CharSequence message, Bitmap imageBitmap,
                                       String leftBtnText, OnClickDialogBtnListener leftBtnListener,
                                       String rightBtnText, OnClickDialogBtnListener rightBtnListener){
        RomaImageDialog romaImageDialog = new RomaImageDialog(context, titleLayoutId, msgLayoutId, title, message,
                imageBitmap, leftBtnText, leftBtnListener, rightBtnText, rightBtnListener);
        return romaImageDialog;
    }

    /** 根据dialogType类型获取特殊titleLayout/msgLayout布局 */
    private static int[] getLayoutId(int dialogType){
        int[] layoutIds = new int[]{-1, -1};
        switch (dialogType){
            case DIALOG_TYPE_NO_ICON:
                layoutIds[0] = R.layout.yt_dialog_no_icon_title_layout;
                // layoutIds[1] = R.layout.yt_dialog_no_icon_text_view;
                break;
            case DIALOG_TYPE_APP_UPDATE:
                layoutIds[0] = R.layout.yt_dialog_subtitle_layout;
                break;
        }
        return layoutIds;
    }

    private static CharSequence getDialogTitle(int dialogType) {
        String title;
        switch (dialogType) {
            case DIALOG_TYPE_ERROR:
                title = "错误提示";
                break;
            case DIALOG_TYPE_NOTICE:
                title = "通知提醒";
                break;
            case DIALOG_TYPE_NEWS:
                title = "今日要闻推送";
                break;
            case DIALOG_TYPE_QUESTION:
                title = "提问";
                break;
            case DIALOG_TYPE_CONFIRM:
                title = "确认";
                break;
            case DIALOG_TYPE_EXCLAMATORY:
            default:
                title = "提示";
                break;
        }
        return title;
    }

    private static int getDialogIcon(int dialogType){
        int imageResId = -1;
        switch (dialogType){
            case DIALOG_TYPE_CONFIRM:
                imageResId = R.drawable.dialog_confirm_icon;
                break;
            case DIALOG_TYPE_ERROR:
                break;
            case DIALOG_TYPE_EXCLAMATORY:
                break;
            case DIALOG_TYPE_QUESTION:
                break;
            case DIALOG_TYPE_NOTICE:
                break;
            case DIALOG_TYPE_NEWS:
                break;
            case DIALOG_TYPE_APP_UPDATE:
                imageResId = R.drawable.roma_dialog_app_update_icon;
                break;
        }
        return imageResId;
    }

//    public static RomaDialog getKdsDialog(Context context, String title, String message, RomaDialog.OnClickButtonListener leftButton, RomaDialog.OnClickButtonListener rightButton){
//        RomaDialog mDialog = null;
//        try {
//            if(Res.getBoolean(R.bool.kconfigs_dialog_isUseCommonDialogView)){
//                mDialog = ReflectionUtils.newInstance(Res.getString(R.string.Package_Class_KdsDialog),
//                        new Class[]{Context.class, String.class, String.class, String.class, RomaDialog.OnClickButtonListener.class, RomaDialog.OnClickButtonListener.class},
//                        new Object[]{context, RomaDialog.LAYOUT_TYPE_DEFAULT, title, message, leftButton, rightButton});
//            }else{
//                mDialog = new RomaDialog(context, title, message,leftButton, rightButton);
//            }
//        } catch (Exception e) {
//            Logger.e("DialogFactory", e.getMessage());
//        }
//        return mDialog;
//    }
//
//    public static RomaDialog getKdsDialog(Context context, String title, String message, String subMessage, RomaDialog.OnClickButtonListener leftButton, RomaDialog.OnClickButtonListener rightButton){
//        RomaDialog mDialog = null;
//        try {
//            Logger.d("RomaDialog", "KdsDialog1  " + subMessage);
//            if(Res.getBoolean(R.bool.kconfigs_dialog_isUseCommonDialogView)){
//                mDialog = ReflectionUtils.newInstance(Res.getString(R.string.Package_Class_KdsDialog),
//                        new Class[]{Context.class, String.class, String.class, String.class, String.class, RomaDialog.OnClickButtonListener.class, RomaDialog.OnClickButtonListener.class},
//                        new Object[]{context, RomaDialog.LAYOUT_TYPE_DEFAULT, title, message, subMessage, leftButton, rightButton});
//            }else{
//                mDialog = new RomaDialog(context, title, message, subMessage, leftButton, rightButton);
//            }
//        } catch (Exception e) {
//            Logger.e("DialogFactory", e.getMessage());
//        }
//        return mDialog;
//    }

    public static Dialog getKdsDialog(Context context, String title, SpannableStringBuilder message, RomaDialog.OnClickButtonListener leftButton, RomaDialog.OnClickButtonListener rightButton, int gravity){
        Dialog mDialog = null;
        try {
            if(Res.getBoolean(R.bool.kconfigs_dialog_isUseCommonDialogView)){
                mDialog = ReflectionUtils.newInstance("com.szkingdom.android.view.dialog.TitleIconDialog",
                        new Class[]{Context.class, String.class, String.class, SpannableStringBuilder.class, RomaDialog.OnClickButtonListener.class, RomaDialog.OnClickButtonListener.class, int.class},
                        new Object[]{context, RomaDialog.LAYOUT_TYPE_DEFAULT, title, message, leftButton, rightButton, gravity});

//                mDialog = ReflectionUtils.newInstance(Res.getString(R.string.Package_Class_KdsDialog),
//                        new Class[]{Context.class, String.class, String.class, SpannableStringBuilder.class, RomaDialog.OnClickButtonListener.class, RomaDialog.OnClickButtonListener.class, int.class},
//                        new Object[]{context, RomaDialog.LAYOUT_TYPE_DEFAULT, title, message, leftButton, rightButton, gravity});
            }else{
                mDialog = new RomaDialog(context, RomaDialog.LAYOUT_TYPE_DEFAULT, title, message,leftButton, rightButton, gravity);
            }
        } catch (Exception e) {
            Logger.e("DialogFactory", e.getMessage());
        }
        return mDialog;
    }

    public static Dialog getKdsListDialog(Context context, String title, String subTitle, String[] items, int[] colors, RomaListDialog.OnDialogItemClickListener listener, String leftButtonText,
                                          OnClickDialogBtnListener leftButton, String rightButtonText, OnClickDialogBtnListener rightButton, int ImageId) {
        Dialog mDialog = null;
        try {
            mDialog = new RomaListImageDialog(context, title, subTitle, items, colors,
                    listener, leftButtonText, leftButton, rightButtonText, rightButton, ImageId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mDialog;
    }
}
