package roma.romaway.abs.android.keyboard;

import java.util.List;

import roma.romaway.abs.graphics.shapes.BorderShapeDrawable;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.Align;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.Keyboard.Key;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.android.basephone.widget.R;

public class RomaWayKeyBoardInputView extends KeyboardView {

	private Drawable keyBackground1;
	private Drawable keyBackground2;
	private Keyboard mKeyboard;
	private int mLabelTextSize = 14;
	private int mKeyTextSize = 14;
	private Paint mPaint;
	private Context mContext;

	public RomaWayKeyBoardInputView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		keyBackground1 = context.getResources().getDrawable(
				R.drawable.roma_keyboard_key_background);
		keyBackground2 = context.getResources().getDrawable(
				R.drawable.roma_keyboard_key_bg_gray);

		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setTextAlign(Align.CENTER);
		mPaint.setAlpha(255);
		mPaint.setColor(mContext.getResources().getColor(
				R.color.abs__keyboard_font_color));
	}

	private int getFontSize(Context context, int size) {
		DisplayMetrics dm = new DisplayMetrics();
		WindowManager windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		windowManager.getDefaultDisplay().getMetrics(dm);
		int screenHeight = dm.heightPixels;
		int screenWidth = dm.widthPixels;
		screenWidth = screenWidth > screenHeight ? screenWidth : screenHeight;
		int rate = (int) (size * (float) screenWidth / 320);
		return rate;
	}

	/**
	 * 设置Lable字体大小
	 * 
	 * @param size
	 */
	public void setLableTextSize(int size) {
		mLabelTextSize = getFontSize(mContext, size);
	}

	/**
	 * 设置Key的字体大小
	 * 
	 * @param size
	 */
	public void setKeyTextSize(int size) {
		mKeyTextSize = getFontSize(mContext, size);
	}

	@Override
	public void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		// super.onDraw(canvas);
		canvas.drawColor(mContext.getResources().getColor(
				R.color.abs__keyboard_divider_color));
		final Rect padding = new Rect(0, 0, 0, 0);
		List<Key> keys = this.getKeyboard().getKeys();
		final int keyCount = keys.size();
		ShapeDrawable mShapeDrawable = new BorderShapeDrawable(
				new RoundRectShape(new float[] { 0, 0, 0, 0, 0, 0, 0, 0 },
						null, null), 0xffffff, 0);
		for (int i = 0; i < keyCount; i++) {
			final Key key = keys.get(i);
			Drawable currentBackgound = keyBackground1;

			if (keyCount == 12) {
				this.setPadding(0, 0, 0, 0);
				if (i == 9 || i == 11) {
					currentBackgound = keyBackground2;
				} else {
					currentBackgound = keyBackground1;
				}
			} else {
				this.setPadding(5, 10, 5, 5);
				if (i == 19 || i == 27 || i ==28 || i == 30){
					mShapeDrawable.getPaint().setColor(0xffbbbdc1);
					currentBackgound = keyBackground2;
				}else{
					mShapeDrawable.getPaint().setColor(Color.WHITE);
					currentBackgound = keyBackground1;
				}
			}

			int[] drawableState = key.getCurrentDrawableState();
			currentBackgound.setState(drawableState);
			currentBackgound.setBounds(0, 0, key.width, key.height);

			final Rect bounds = currentBackgound.getBounds();
			if (key.width != bounds.right || key.height != bounds.bottom) {
				currentBackgound.setBounds(0, 0, key.width, key.height);
			}
			canvas.translate(key.x + this.getPaddingLeft(),
					key.y + this.getPaddingTop());
			currentBackgound.draw(canvas);

			// Switch the character to uppercase if shift is pressed
			String label = key.label == null ? null : adjustCase(key.label)
					.toString();
			if (label != null) {
				// For characters, use large font. For labels like "Done", use
				// small font.
				if (label.length() > 1 && key.codes.length < 2) {
					mPaint.setTextSize(getFontSize(mContext, mLabelTextSize));
					//mPaint.setTypeface(Typeface.DEFAULT);
				} else {
					mPaint.setTextSize(getFontSize(mContext, mKeyTextSize));
					//mPaint.setTypeface(Typeface.DEFAULT);
				}
				// Draw a drop shadow for the text
				// mPaint.setShadowLayer(mShadowRadius, 0, 0, mShadowColor);
				// Draw the text
				canvas.drawText(label,
						(key.width - padding.left - padding.right) / 2
								+ padding.left,
						(key.height - padding.top - padding.bottom) / 2
								+ (mPaint.getTextSize() - mPaint.descent()) / 2
								+ padding.top, mPaint);
				// Turn off drop shadow
				// mPaint.setShadowLayer(0, 0, 0, 0);
			} else if (key.icon != null) {
				final int drawableX = (key.width - padding.left - padding.right - key.icon
						.getIntrinsicWidth()) / 2 + padding.left;
				final int drawableY = (key.height - padding.top
						- padding.bottom - key.icon.getIntrinsicHeight())
						/ 2 + padding.top;
				canvas.translate(drawableX, drawableY);
				key.icon.setBounds(0, 0, key.icon.getIntrinsicWidth(),
						key.icon.getIntrinsicHeight());
				key.icon.draw(canvas);
				canvas.translate(-drawableX, -drawableY);
			}

			canvas.translate(-key.x - this.getPaddingLeft(),
					-key.y - this.getPaddingTop());
		}
		// 在键盘最上方画一条线
		Paint paint = new Paint();
		paint.setColor(this.getResources().getColor(
				R.color.abs__keyboard_divider_color));
		paint.setStrokeWidth(1);
		canvas.drawLine(0, 0, this.getWidth(), 0, paint);
	}

	@Override
	public void setKeyboard(Keyboard keyboard) {
		// TODO Auto-generated method stub
		super.setKeyboard(keyboard);
		mKeyboard = keyboard;
	}

	private CharSequence adjustCase(CharSequence label) {
		if (mKeyboard.isShifted() && label != null && label.length() < 3
				&& Character.isLowerCase(label.charAt(0))) {
			label = label.toString().toUpperCase();
		}
		return label;
	}
}
