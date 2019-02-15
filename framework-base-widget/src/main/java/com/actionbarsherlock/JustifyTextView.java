package com.actionbarsherlock;

import android.content.Context;
import android.graphics.Canvas;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 两端对齐的TextView, 修复最后一行文字间距过大的问题;
 */
public class JustifyTextView extends TextView {

    private int mLineY;
    private int mViewWidth;

    public JustifyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        TextPaint paint = getPaint();
        paint.setColor(getCurrentTextColor());
        paint.drawableState = getDrawableState();
        mViewWidth = getMeasuredWidth();
        String text = getText().toString();
        mLineY = 0;
        mLineY += getTextSize();
        Layout layout = getLayout();


        // layout.getLayout()在4.4.3出现NullPointerException
        if (layout == null) {
            return;
        }

        int preTextCount = 0;
        boolean isSubstringed = false;
        for (int i = 0; i < layout.getLineCount(); i++) {
            int lineStart = layout.getLineStart(i);
            int lineEnd = layout.getLineEnd(i);
            String line = text.substring(lineStart, lineEnd);
            // ==================== 特殊处理针对折叠的7*24小时控件(Start) ====================
            if (i == 0)
                preTextCount = lineEnd - lineStart;
            if(lineEnd - lineStart > preTextCount && i == (layout.getLineCount() - 1)) {
                lineEnd = lineStart + preTextCount;
                line = text.substring(lineStart, lineEnd);

                while (StaticLayout.getDesiredWidth(text, lineStart, lineEnd, getPaint()) < mViewWidth && lineEnd < text.length()){
                    lineEnd += 1;
                    line = text.substring(lineStart, lineEnd);
                }
                while (StaticLayout.getDesiredWidth(text, lineStart, lineEnd, getPaint()) > mViewWidth){
                    lineEnd -= 1;
                    line = text.substring(lineStart, lineEnd);
                }
                isSubstringed = true;
            }
            // ==================== 特殊处理针对折叠的7*24小时控件(End) ====================

            float width = StaticLayout.getDesiredWidth(text, lineStart, lineEnd, getPaint());
            // 解决了最后一行文字间距过大的问题
            if(i < layout.getLineCount() - 1) {
                if (needScale(line)) {
                    drawScaledText(canvas, lineStart, line, width);
                } else {
                    canvas.drawText(line, 0, mLineY, paint);
                }
            } else {
                // 特殊处理针对折叠的7*24小时控件:
                if (isSubstringed)
                    drawScaledText(canvas, lineStart, line, width);
                else
                    canvas.drawText(line, 0, mLineY, paint);
            }

            mLineY += getLineHeight();
        }
    }

    private void drawScaledText(Canvas canvas, int lineStart, String line, float lineWidth) {
        float x = 0;
        if (isFirstLineOfParagraph(lineStart, line)) {
            String blanks = "  ";
            canvas.drawText(blanks, x, mLineY, getPaint());
            float bw = StaticLayout.getDesiredWidth(blanks, getPaint());
            x += bw;

            line = line.substring(3);
        }

        float d = (mViewWidth - lineWidth) / line.length() - 1;
        for (int i = 0; i < line.length(); i++) {
            String c = String.valueOf(line.charAt(i));
            float cw = StaticLayout.getDesiredWidth(c, getPaint());
            canvas.drawText(c, x, mLineY, getPaint());
            x += cw + d;
        }
    }

    private boolean isFirstLineOfParagraph(int lineStart, String line) {
        return line.length() > 3 && line.charAt(0) == ' '
                && line.charAt(1) == ' ';
    }

    private boolean needScale(String line) {
        if (line == null || line.length() == 0) {
            return false;
        } else {
            return line.charAt(line.length() - 1) != '\n';
        }
    }

}