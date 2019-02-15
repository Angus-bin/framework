package roma.romaway.commons.android.webkit.keyboard;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.support.annotation.Keep;
import android.text.Editable;
import android.view.View;

import com.romaway.android.phone.R;
import com.szkingdom.stocksearch.keyboard.KDS_KeyboardManager;
import com.szkingdom.stocksearch.keyboard.Kds_KeyBoard;
import com.szkingdom.stocksearch.keyboard.Kds_KeyBoardView;
import com.szkingdom.stocksearch.keyboard.KeyCodes;
import com.szkingdom.stocksearch.keyboard.OnClickKeyboardListener;

/**
 * Created by edward on 16/10/12.
 */
@Keep
public class ZXJT_KeyboardManager extends KDS_KeyboardManager {

    public ZXJT_KeyboardManager(Activity activity, View contentParent, Kds_KeyBoardView keyboardView) {
        super(activity, contentParent, keyboardView);
    }

    /**
     * 设置当前键盘
     *
     * @param keyboard
     */
    @SuppressLint("NewApi")
    @Override
    protected void setCurrentKeyboard(Kds_KeyBoard keyboard) {
        super.setCurrentKeyboard(keyboard);
        if (mCurrentKeyboard.keyXmlId == R.xml.kds_stock_keyboard_symbols_for_switch_num_abc
                || mCurrentKeyboard.keyXmlId == R.xml.kds_stock_keyboard_abc_for_switch_num_symbols) {
            setKdsKeyboardHeadVisibility(View.VISIBLE);
        } else {
            setKdsKeyboardHeadVisibility(View.GONE);
        }
    }

    /**
     * 响应处理 KeyCode 事件
     * @param primaryCode
     * @param editable
     * @param start
     */
    protected void handleKeyCodeEvent(int primaryCode, Editable editable, int start) {
        if (primaryCode == KeyCodes.KEYCODE_SWITCH_NUMBER) {// 切换到数字键盘（带字母、中文切换功能按钮）
            setCurrentKeyboard(new Kds_KeyBoard(activity,
                    R.xml.kds_stock_keyboard_number_for_switch_abc_symbols));
            keyboardView.setKeyboard(mCurrentKeyboard);
        }else if(primaryCode == KeyCodes.KEYCODE_SWITCH_ABC) { // 切换到字母键盘（带数字、中文切换功能按钮）
            setCurrentKeyboard(new Kds_KeyBoard(activity,
                    R.xml.kds_stock_keyboard_abc_for_switch_num_symbols));
            keyboardView.setKeyboard(mCurrentKeyboard);
        }else {
            super.handleKeyCodeEvent(primaryCode, editable, start);
        }
    }

    //H5的键盘点击事件监听
    @SuppressLint("NewApi")
    @Keep
    public abstract class KdsOnWebKeyboardActionListener implements OnClickKeyboardListener {

        @TargetApi(Build.VERSION_CODES.CUPCAKE)
        @SuppressLint("NewApi")
        public KdsOnWebKeyboardActionListener() {

        }
        @Override
        public void onKey(int primaryCode) {
            keyboardView.setRoundKeyEnable(false);// 默认是直角

            handleKeyCodeEvent(primaryCode,this);

            if(mOnClickKeyboardListener != null)
                mOnClickKeyboardListener.onKey(primaryCode);
        }

        public abstract void setInputContent(String inputContent);
    }

    //处理H5的键盘点击事件
    protected void handleKeyCodeEvent(int primaryCode, KdsOnWebKeyboardActionListener listener) {
        if (primaryCode == KeyCodes.KEYCODE_SWITCH_SYMBOLS) {// 符号切换按钮
            setCurrentKeyboard(new Kds_KeyBoard(activity,
                    R.xml.kds_stock_keyboard_symbols_for_switch_num_abc));
            keyboardView.setKeyboard(mCurrentKeyboard);
        } else if (primaryCode == KeyCodes.KEYCODE_SWITCH_NUMBER) {// 切换到数字键盘（带字母、中文切换功能按钮）
            setCurrentKeyboard(new Kds_KeyBoard(activity,
                    R.xml.kds_stock_keyboard_number_for_switch_abc_symbols));
            // R.xml.kds_stock_keyboard_number_for_switch_abc_system));
            keyboardView.setKeyboard(mCurrentKeyboard);
        } else if (primaryCode == KeyCodes.KEYCODE_SWITCH_ABC) { // 切换到字母键盘（带数字、中文切换功能按钮）
            setCurrentKeyboard(new Kds_KeyBoard(activity,
                    R.xml.kds_stock_keyboard_abc_for_switch_num_symbols));
            // R.xml.kds_stock_keyboard_abc_for_switch_num_systen));
            keyboardView.setKeyboard(mCurrentKeyboard);
        } else if (primaryCode == KeyCodes.KEYCODE_CANCEL) {// 完成
            hideKeyboard();
        } else if (primaryCode == KeyCodes.KEYCODE_DELETE) {// 回退
            listener.setInputContent("del");
        } else if (primaryCode == KeyCodes.KEYCODE_SHIFT) {// 大小写切换
            changeKey();
        } else if (primaryCode == KeyCodes.KEYCODE_DONE) {//确定按键
            hideKeyboard();
        } else if (primaryCode == KeyCodes.KEYCODE_LABEL_00) {
            listener.setInputContent("00");
        } else if (primaryCode == KeyCodes.KEYCODE_LABEL_000) {
            listener.setInputContent("000");
        } else if (primaryCode == KeyCodes.KEYCODE_LABEL_600) {
            listener.setInputContent("600");
        } else if (primaryCode == KeyCodes.KEYCODE_LABEL_601) {
            listener.setInputContent("601");
        } else if (primaryCode == KeyCodes.KEYCODE_LABEL_002) {
            listener.setInputContent("002");
        } else if (primaryCode == KeyCodes.KEYCODE_LABEL_300) {
            listener.setInputContent("300");
        } else if (primaryCode == KeyCodes.KEYCODE_SWITCH_TO_SYSKEY) {//  显示系统键盘隐藏自定义键盘
            isShowSystemKeyboard = showSystemKeyboard();
            hideKeyboard();
        } else if (primaryCode == KeyCodes.KEYCODE_TREAD_ALL_CANG ||
                primaryCode == KeyCodes.KEYCODE_TREAD_1_2_CANG ||
                primaryCode == KeyCodes.KEYCODE_TREAD_1_3_CANG ||
                primaryCode == KeyCodes.KEYCODE_TREAD_1_4_CANG) {
            switch (primaryCode) {
                case KeyCodes.KEYCODE_TREAD_ALL_CANG:
                    listener.setInputContent("全仓");
                    break;
                case KeyCodes.KEYCODE_TREAD_1_2_CANG:
                    listener.setInputContent("1/2仓");
                    break;
                case KeyCodes.KEYCODE_TREAD_1_3_CANG:
                    listener.setInputContent("1/3仓");
                    break;
                case KeyCodes.KEYCODE_TREAD_1_4_CANG:
                    listener.setInputContent("1/4仓");
                    break;
            }
        } else {
            if (primaryCode != KeyCodes.KEYCODE_LABEL_NULL)// 空的按键
                listener.setInputContent(Character.toString((char) primaryCode));
        }
    }
}
