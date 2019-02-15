package roma.romaway.abs.android.keyboard;

import com.android.basephone.widget.R;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.FrameLayout;

public class RomaWayKeyBoardView extends FrameLayout{
    private RomaWayKeyBoardInputView keyboardInputView;
    private Button mHideButton;
    
    public RomaWayKeyBoardView(Context context) {
        this(context, null);
        // TODO Auto-generated constructor stub
    }

    public RomaWayKeyBoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        
        LayoutInflater.from(context).inflate(
                R.layout.abs__keyboard_view_layout, this, true);
     
        keyboardInputView = (RomaWayKeyBoardInputView) findViewById(R.id.abs__keyboard_input_view);
        
        mHideButton = (Button)this.findViewById(R.id.abs__keyboard_hide);
    }

    /**
     * 设置键盘
     * @param keyboard
     */
    public void setKeyboard(Keyboard keyboard){
        keyboardInputView.setKeyboard(keyboard);
    }
    
   /**
    * 加载自定义键盘
    * @param keyboard
    * @param listener
    */
    public void loadKeyboard(Keyboard keyboard, OnKeyboardActionListener listener) {

        this.keyboardInputView.setEnabled(true);
        this.keyboardInputView.setPreviewEnabled(true);
        this.keyboardInputView.setOnKeyboardActionListener(listener);
        this.keyboardInputView.setKeyboard(keyboard);
    }
    /**
     * 设置隐藏按钮的监听
     * @param l
     */
    public void setOnClickHideButtonListener(OnClickListener l){
        mHideButton.setOnClickListener(l);
    }
    
    /**
     * 设置隐藏按钮的使能，标志是否显示
     * @param flag
     */
    public void setHideButtonVisibility(int visibility){
        mHideButton.setVisibility(visibility);
        
    }
}
