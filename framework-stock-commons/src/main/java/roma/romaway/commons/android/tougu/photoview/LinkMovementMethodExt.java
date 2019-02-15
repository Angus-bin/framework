package roma.romaway.commons.android.tougu.photoview;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Created by hongrb on 2017/6/7.
 * 重写LinkMovementMethod类，获取图片的点击事件
 */

public class LinkMovementMethodExt extends LinkMovementMethod {

    private static LinkMovementMethod sInstance;
    private Handler handler = null;
    private  Class spanClass = null;
    private Activity mActivity;

    public static MovementMethod getInstance(Handler _handler, Class _spanClass, Activity activity) {
        if (sInstance == null) {
            sInstance = new LinkMovementMethodExt();
            ((LinkMovementMethodExt)sInstance).handler = _handler;
            ((LinkMovementMethodExt)sInstance).spanClass = _spanClass;
            ((LinkMovementMethodExt)sInstance).mActivity = activity;
        }

        return sInstance;
    }

    int x1;
    int x2;
    int y1;
    int y2;

    @Override
    public boolean onTouchEvent(TextView widget, Spannable buffer,
                                MotionEvent event) {
        int action = event.getAction();

        if (event.getAction() == MotionEvent.ACTION_DOWN){
            x1 = (int) event.getX();
            y1 = (int) event.getY();
        }

        if (event.getAction() == MotionEvent.ACTION_UP) {
            x2 = (int) event.getX();
            y2 = (int) event.getY();

            if (Math.abs(x1 - x2) < 10 && Math.abs(y1 - y2) < 10) {

                x2 -= widget.getTotalPaddingLeft();
                y2 -= widget.getTotalPaddingTop();

                x2 += widget.getScrollX();
                y2 += widget.getScrollY();

                Layout layout = widget.getLayout();
                int line = layout.getLineForVertical(y2);
                int off = layout.getOffsetForHorizontal(line, x2);
                /**
                 * get you interest span
                 */
                Object[] spans = buffer.getSpans(off, off, spanClass);
                if (spans.length != 0) {

                    Selection.setSelection(buffer,
                            buffer.getSpanStart(spans[0]),
                            buffer.getSpanEnd(spans[0]));
                    MessageSpan obj = new MessageSpan();
                    obj.setObj(spans);
                    obj.setView(widget);
                    Message message = handler.obtainMessage();
                    message.obj = obj;
                    message.what = 200;
                    message.sendToTarget();
                    return false;
                }
            }
        }
        return false;
    }

    public boolean canSelectArbitrarily() {
        return true;
    }

    public boolean onKeyUp(TextView widget, Spannable buffer, int keyCode,
                           KeyEvent event) {
        return false;
    }

}
