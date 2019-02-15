package roma.romaway.abs.android.keyboard;

import java.util.List;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.Keyboard.Key;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.text.Editable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.actionbarsherlock.app.ActionBar;
import com.android.basephone.widget.R;

/**
 * 处理自定义键盘和工具类
 * 
 * @author 万籁唤
 * 
 */
public class RomaWayStockKeyboardUtil {
    private ActionBar mActionBar;
	private RomaWayKeyBoardView keyboardView;
	private Keyboard mCurrentKeyboard;// 当前键盘
	private Keyboard k1;// 字母键盘
	private Keyboard k2;// 数字键盘
	private Keyboard k3;// 纯数字键盘
	private Keyboard k4;// 带小数点键盘
	public boolean isnun = false;// 是否数据键盘
	public boolean isupper = true;// 是否大写
	public static Keyboard kdef;

	private EditText ed = null;// 编辑输入框
	private int keyHeight;// 键盘的高度

	public RomaWayStockKeyboardUtil() {

	}

	public RomaWayStockKeyboardUtil(EditText edit) {
		this.ed = edit;
	}

	/**
	 * 设置初始化键盘
	 * 
	 * @param context
	 * @param defKeyboardXmlId
	 * @return
	 */
	public Keyboard initKeyboard(Context context, int defKeyboardXmlId) {
		k1 = new Keyboard(context, R.xml.roma_stock_keyboard_qwerty);// 字母键盘
		k2 = new Keyboard(context, R.xml.roma_stock_keyboard_to_qwerty);// 数字键盘
		k3 = new Keyboard(context, R.xml.roma_stock_keyboard_symbols);// 纯数字键盘
		k4 = new Keyboard(context, R.xml.roma_stock_keyboard_point_symbols);// 带小数点键盘

		// 当没有设置默认的定制键盘时才默认数字和英文切花键盘
		if (defKeyboardXmlId <= 0) {
			// this.keyboardView.setKeyboard(k1);
			setCurrentKeyboard(k2);
			isnun = true;
		} else {
			kdef = new Keyboard(context, defKeyboardXmlId);
			// this.keyboardView.setKeyboard(kdef);
			setCurrentKeyboard(kdef);
			// [优化] isnum用于记录键盘模式切换时当前是否为数字模式,故数字键盘默认为true;
			isnun = (defKeyboardXmlId == R.xml.roma_stock_keyboard_to_qwerty);
		}

		return mCurrentKeyboard;
	}

	public void loadKeyboard(ActionBar actionBar,OnClickListener hideButtonListener) {
	    mActionBar = actionBar;
	    this.keyboardView = actionBar.getRomaKeyBoardView();
	    keyboardView.loadKeyboard(mCurrentKeyboard, listener);
	    if(hideButtonListener == null)//等于null就采用内部监听器
            keyboardView.setOnClickHideButtonListener(mHideButtonListener);
        else
            keyboardView.setOnClickHideButtonListener(hideButtonListener);
	}

	public void loadKeyboard(ActionBar actionBar,
			OnKeyboardActionListener onKeyboardActionListener,OnClickListener hideButtonListener) {
	    mActionBar = actionBar;
	    this.keyboardView = actionBar.getRomaKeyBoardView();
	    keyboardView.loadKeyboard(mCurrentKeyboard,new RomaOnKeyboardActionListener(
                onKeyboardActionListener));
	    if(hideButtonListener == null)//等于null就采用内部监听器
	        keyboardView.setOnClickHideButtonListener(mHideButtonListener);
	    else
	        keyboardView.setOnClickHideButtonListener(hideButtonListener);
	}

	/**
	 * 设置当前键盘
	 * 
	 * @param keyboard
	 */
	private void setCurrentKeyboard(Keyboard keyboard) {
		mCurrentKeyboard = keyboard;
		keyHeight = keyboard.getHeight();
	}

	/**
	 * 获取键盘的总高度
	 * 
	 * @return
	 */
	public int geKeyboardHeight() {
		return keyHeight;
	}

	public void setOnClickHideButtonListener(OnClickListener l){
	    keyboardView.setOnClickHideButtonListener(l);
	}
	
	private RomaOnClickListener mHideButtonListener = new RomaOnClickListener();
	public class RomaOnClickListener implements OnClickListener{

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            if(v.getId() == R.id.abs__keyboard_hide){
                mActionBar.hideKeyboard();
            }
        }
	}
	private RomaOnKeyboardActionListener listener = new RomaOnKeyboardActionListener(
			null);

	public void setOnKeyboardActionListener(
			OnKeyboardActionListener onKeyboardActionListener) {
		listener = new RomaOnKeyboardActionListener(onKeyboardActionListener);
	}

	public class RomaOnKeyboardActionListener implements
			OnKeyboardActionListener {
		private OnKeyboardActionListener listener = null;

		public RomaOnKeyboardActionListener(OnKeyboardActionListener listener) {
			this.listener = listener;
		}

		@Override
		public void onPress(int primaryCode) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onRelease(int primaryCode) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onKey(int primaryCode, int[] keyCodes) {
			// TODO Auto-generated method stub

			Editable editable = null;
			int start = -1;
			if (ed != null) {
				editable = ed.getText();
				start = ed.getSelectionStart();
			}
			if (primaryCode == Keyboard.KEYCODE_CANCEL) {// 完成
			    mActionBar.hideKeyboard();
			} else if (primaryCode == Keyboard.KEYCODE_DELETE) {// 回退
				if (editable != null && editable.length() > 0) {
					if (start > 0) {
						editable.delete(start - 1, start);
					}
				}
			} else if (primaryCode == Keyboard.KEYCODE_SHIFT) {// 大小写切换
				changeKey();
				setCurrentKeyboard(k1);
				keyboardView.setKeyboard(mCurrentKeyboard);

			} else if (primaryCode == Keyboard.KEYCODE_MODE_CHANGE) {// 数字键盘切换
				if (isnun) {
					isnun = false;
					setCurrentKeyboard(k1);
					keyboardView.setKeyboard(mCurrentKeyboard);
				} else {
					isnun = true;
					setCurrentKeyboard(k2);
					keyboardView.setKeyboard(mCurrentKeyboard);
				}
			} else if(primaryCode == Keyboard.KEYCODE_DONE){//确定按键
			    mActionBar.hideKeyboard();
            }
			else if (primaryCode == 519000) {
			    if (editable != null && start >= 0)
			        editable.insert(start, "000");
			} else if (primaryCode == 519001) {
			    if (editable != null && start >= 0)
			        editable.insert(start, ".");
			} else {
				if (editable != null && start >= 0)
					editable.insert(start,
							Character.toString((char) primaryCode));
			}

			// 将结果传到到
			if (listener != null)
				listener.onKey(primaryCode, keyCodes);
		}

		@Override
		public void onText(CharSequence text) {
			// TODO Auto-generated method stub

		}

		@Override
		public void swipeLeft() {
			// TODO Auto-generated method stub

		}

		@Override
		public void swipeRight() {
			// TODO Auto-generated method stub

		}

		@Override
		public void swipeDown() {
			// TODO Auto-generated method stub

		}

		@Override
		public void swipeUp() {
			// TODO Auto-generated method stub

		}

	}

	/**
	 * 键盘大小写切换
	 */
	private void changeKey() {
		List<Key> keylist = k1.getKeys();
		if (isupper) {// 大写切换小写
			isupper = false;
			for (Key key : keylist) {
				if (key.label != null && isword(key.label.toString())) {
					key.label = key.label.toString().toLowerCase();
					key.codes[0] = key.codes[0] + 32;
				}
			}
		} else {// 小写切换大写
			isupper = true;
			for (Key key : keylist) {
				if (key.label != null && isword(key.label.toString())) {
					key.label = key.label.toString().toUpperCase();
					key.codes[0] = key.codes[0] - 32;
				}
			}
		}
	}

	private boolean isword(String str) {
		String wordstr = "abcdefghijklmnopqrstuvwxyz";
		if (wordstr.indexOf(str.toLowerCase()) > -1) {
			return true;
		}
		return false;
	}

}