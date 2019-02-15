package com.romaway.android.phone.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.romaway.android.phone.utils.DrawUtils;
import com.romaway.common.protocol.hq.HQFSZHProtocol;
import com.romaway.common.protocol.hq.HQQHFSZHProtocol;
import com.romaway.common.protocol.util.KFloat;
import com.romaway.common.protocol.util.KFloatUtils;

/**
 * 下拉盘口明细页
 * 
 * @author qinyn
 * 
 */
public class MingXiView extends View {
	private Paint paint;
	private int Height;
	private int Width;

	private Rect mxRect;
	private Rect fbRect;

	// 公共颜色 黄、红、绿
	private static int clr_fs_yellow = MinuteViewTheme.clr_fs_yellow;
	private static int clr_fs_red = MinuteViewTheme.clr_fs_red;
	private static int clr_fs_green = MinuteViewTheme.clr_fs_green;
	private static int clr_fs_blue = MinuteViewTheme.clr_fs_blue;

	/** 线条颜色 **/
	private static int clr_fs_line = MinuteViewTheme.clr_fs_line;
	/** 线条宽度 **/
	private static int theme_fs_line_width = MinuteViewTheme.theme_fs_line_width;
	/** 字体大小 **/
	private static int theme_fs_bs5_textsize = MinuteViewTheme.theme_fs_bs5_body_textsize;

	/**
	 * 最近成交，现价
	 */
	private KFloat kfZjcj = new KFloat();
	/**
	 * 涨跌幅
	 */
	private int[] nZdf_s;
	/**
	 * 今开
	 */
	private KFloat kfJrkp = new KFloat();
	/**
	 * 昨收
	 */
	private KFloat kfZrsp = new KFloat();
	/**
	 * 成交均价
	 */
	private int[] nCjjj_s;
	/**
	 * 涨跌
	 */
	private KFloat kfZd = new KFloat();
	/**
	 * 总成交数量
	 */
	private KFloat kfCjss = new KFloat();
	/**
	 * 成交量
	 */
	private int[] nCjss_s;
	/**
	 * 振幅
	 */
	private KFloat kfZf = new KFloat();
	/**
	 * 成交金额
	 */
	private int[] nCjje_s;
	/**
	 * 总成交金额
	 */
	private KFloat kfCjje = new KFloat();
	/**
	 * 量比
	 */
	private KFloat kfLb = new KFloat();
	/**
	 * 买盘
	 */
	private KFloat kfBuyp = new KFloat();
	/**
	 * 卖盘
	 */
	private KFloat kfSelp = new KFloat();
	/**
	 * 最高成交
	 */
	private KFloat kfZgcj = new KFloat();
	/**
	 * 最低成交
	 */
	private KFloat kfZdcj = new KFloat();
	/**
	 * 涨停价
	 */
	private KFloat kfLimUp = new KFloat();
	/**
	 * 跌停价
	 */
	private KFloat kfLimDown = new KFloat();
	private int fsDataWCount = 0;

	/**
	 * 交易明细<br>
	 * mxData[0][]:分别时间，mxData[1][]:分笔成交类别,mxData[2][]:分笔成交明细，mxData[3][]:分笔成交价格
	 * **/
	private int[][] mxData;
	/** 触摸x **/
	private float eventX;
	/** 触摸 y **/
	private float eventY;
	/** 分笔事件 **/
	private boolean fb_event;
	/** 切换分笔标记 **/
	private int fb_next = 0;
	private boolean mx_event;
	/** 停牌 **/
	int suspended;
	int version = 1;

	/**
	 * @param context
	 */
	public MingXiView(Context context) {
		super(context);
		init();
	}

	public MingXiView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		paint = new Paint();
		paint.setColor(clr_fs_line);
		paint.setStrokeWidth(theme_fs_line_width);
		paint.setAntiAlias(true);
		paint.setFilterBitmap(true);
		setLongClickable(true);
		// MainTabControl.getInstance().showTJZXText();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		initRect(canvas);
		drawFrame(canvas, mxRect);
		drawFrame(canvas, fbRect);
		drawMXRect(canvas, mxRect);
		drawFBRect(canvas, fbRect);

	}

	/**
	 * 换手率
	 */
	private String kfHsl;
	/**
	 * 总市值
	 */
	private String kfZsz;
	/**
	 * 流通盘
	 */
	private String kfLtp;
	/** 现量 **/
	private String kfXl;

	public void setData(HQFSZHProtocol ptl) {
		if (ptl == null) {
			return;
		}
		version = ptl.getCmdServerVersion();
		suspended = ptl.resp_bSuspended;

		fsDataWCount = ptl.resp_wFSDataCount;
		this.kfZjcj = new KFloat(ptl.resp_nZjcj);
		this.nCjjj_s = ptl.resp_nCjjj_s;
		this.kfZd = new KFloat(ptl.resp_nZd);
		this.nZdf_s = ptl.resp_nZdf_s;
		this.kfJrkp = new KFloat(ptl.resp_nJrkp);
		this.kfZrsp = new KFloat(ptl.resp_nZrsp);

		this.kfCjss = new KFloat(ptl.resp_nCjss);
		this.nCjss_s = ptl.resp_nCjss_s;
		// this.kfHsl = new KFloat(ptl.resp_nh);
		this.kfZf = new KFloat(ptl.resp_nZf);
		this.nCjje_s = ptl.resp_nCjje_s;
		this.kfCjje = new KFloat(ptl.resp_nCjje);
		this.kfLb = new KFloat(ptl.resp_nLb);

		this.kfBuyp = new KFloat(ptl.resp_nBuyp);
		this.kfSelp = new KFloat(ptl.resp_nSelp);

		this.kfZgcj = new KFloat(ptl.resp_nZgcj);
		this.kfZdcj = new KFloat(ptl.resp_nZdcj);
		this.kfLimUp = new KFloat(ptl.resp_nLimUp);
		this.kfLimDown = new KFloat(ptl.resp_nLimDown);
		this.kfHsl = String.valueOf(new KFloat(ptl.resp_iHSL));
		this.kfZsz = String.valueOf(new KFloat(ptl.resp_iZSZ));
		this.kfLtp = String.valueOf(new KFloat(ptl.resp_iLTP));
		this.kfXl = String.valueOf(new KFloat(ptl.resp_iXL));

		// 分笔
		int mxCount = ptl.resp_wFBDataCount;
		mxData = new int[4][mxCount];
		for (int i = 0; i < mxCount; i++) {
			mxData[0][i] = ptl.resp_dwFBTime_s[i];
			mxData[1][i] = ptl.resp_bFBCjlb_s[i];
			mxData[2][i] = ptl.resp_nFBZjcj_s[i];
			mxData[3][i] = ptl.resp_nFBCjss_s[i];
		}
		invalidate();
	}

	private boolean isQiHuo = false;

	public void setQHData(HQQHFSZHProtocol ptl) {
		if (ptl == null) {
			return;
		}
		isQiHuo = true;
		suspended = ptl.resp_bSuspended;
		fsDataWCount = ptl.resp_wFSDataCount;
		this.kfZjcj = new KFloat(ptl.resp_nZjcj);
		this.nCjjj_s = ptl.resp_nCjjj_s;
		this.kfZd = new KFloat(ptl.resp_nZd);
		this.nZdf_s = ptl.resp_nZdf_s;
		this.kfJrkp = new KFloat(ptl.resp_nJrkp);
		this.kfZrsp = new KFloat(ptl.resp_nZrsp);

		this.kfCjss = new KFloat(ptl.resp_nCjss);
		this.nCjss_s = ptl.resp_nCjss_s;
		// this.kfHsl = new KFloat(ptl.resp_nh);
		this.kfZf = new KFloat(ptl.resp_nZf);
		this.nCjje_s = ptl.resp_nCjje_s;
		this.kfLb = new KFloat(ptl.resp_nLb);

		this.kfBuyp = new KFloat(ptl.resp_nBuyp);
		this.kfSelp = new KFloat(ptl.resp_nSelp);

		this.kfZgcj = new KFloat(ptl.resp_nZgcj);
		this.kfZdcj = new KFloat(ptl.resp_nZdcj);
		this.kfLimUp = new KFloat(ptl.resp_nLimUp);
		this.kfLimDown = new KFloat(ptl.resp_nLimDown);

		// 分笔
		int mxCount = ptl.resp_wFBDataCount;
		mxData = new int[4][mxCount];
		for (int i = 0; i < mxCount; i++) {
			mxData[0][i] = ptl.resp_dwFBTime_s[i];
			mxData[1][i] = ptl.resp_bFBCjlb_s[i];
			mxData[2][i] = ptl.resp_nFBZjcj_s[i];
			mxData[3][i] = ptl.resp_nFBCjss_s[i];
		}
		invalidate();

	}

	public void initRect(Canvas canvas) {
		Height = getHeight();
		Width = getWidth() - 1;

		mxRect = new Rect(0, 0, Width, Height / 2);
		fbRect = new Rect(0, Height / 2, Width, Height - 1);
	}

	/** 画rect边框 */
	public void drawFrame(Canvas canvas, Rect rect) {
		canvas.save();
		canvas.drawLine(rect.left, rect.top, rect.right, rect.top, paint);
		canvas.drawLine(rect.left, rect.bottom, rect.right, rect.bottom, paint);
		canvas.drawLine(rect.left, rect.top, rect.left, rect.bottom, paint);
		canvas.drawLine(rect.right, rect.top, rect.right, rect.bottom, paint);
		canvas.restore();
	}

	public void drawMXRect(Canvas canvas, Rect rect) {
		Paint[] p = new Paint[] { new Paint(), new Paint(), new Paint(),
				new Paint(), };
		p[0].setAntiAlias(true);
		p[0].setFilterBitmap(true);
		p[1].setAntiAlias(true);
		p[1].setFilterBitmap(true);
		p[2].setAntiAlias(true);
		p[2].setFilterBitmap(true);
		p[3].setAntiAlias(true);
		p[3].setFilterBitmap(true);

		int h = rect.height() / 10;
		int w = rect.width() / 2;
		int left1 = rect.left + 5;
		int left2 = left1 + w;
		int[] y = new int[] { h + 1, h * 2 + 1, h * 3 + 1, h * 4 + 1,
				h * 5 + 1, h * 6 + 1, h * 7 + 1, h * 8 + 1, h * 9 + 1,
				h * 10 + 1 };
		// 虚线
		float[] line1 = DrawUtils.getDottedLine(rect.left, h * 3 + 4, rect,
				true);
		float[] line2 = DrawUtils.getDottedLine(rect.left, h * 6 + 4, rect,
				true);
		float[] line3 = DrawUtils.getDottedLine(rect.left, h * 8 + 4, rect,
				true);
		canvas.save();
		canvas.drawLines(line1, paint);
		canvas.drawLines(line2, paint);
		canvas.drawLines(line3, paint);
		canvas.restore();

		String[] title1 = new String[] { "现价:", "涨跌:", "今开:", "总手:", "换手:",
				"总额:", "内盘:", "总市值:", "最高:", "涨停:", };
		String[] title2 = new String[] { "均价:", "涨幅:", "昨收:", "现手:", "振幅:",
				"量比:", "外盘:", "流通:", "最低:", "跌停:", };

		p[0].setColor(Color.WHITE);
		p[0].setTextSize(theme_fs_bs5_textsize);
		for (int i = 0; i <= 9; i++) {
			canvas.drawText(title1[i], left1, y[i], p[0]);
			canvas.drawText(title2[i], left2, y[i], p[0]);
		}
		p[1].setTextSize(theme_fs_bs5_textsize);
		p[2].setTextSize(theme_fs_bs5_textsize);
		p[3].setTextSize(theme_fs_bs5_textsize);

		p[0].setTextSize(theme_fs_bs5_textsize);
		p[1].setColor(clr_fs_red);
		p[2].setColor(clr_fs_green);
		int data_left1 = rect.width() / 4 - 20;
		int data_left2 = rect.width() / 4 * 3 - 20;
		String z = "0";
		int zd_index = 0;
		String stop = "---";

		if (suspended == 1) {
			canvas.save();
			Paint pp = new Paint();
			pp.setColor(Color.WHITE);
			for (int i = 0; i <= 9; i++) {
				canvas.drawText(stop.toString(), data_left1, y[i], pp);
				canvas.drawText(stop.toString(), data_left2, y[i], pp);
			}
			canvas.restore();
		} else {
			// 最新
			zd_index = kCompare(kfZjcj, kfZrsp);

			canvas.drawText(kfZjcj.toString(), data_left1, y[0], p[zd_index]);

			// 涨跌
			zd_index = kCompare(kfZjcj, kfZrsp);

			canvas.drawText(kfZd.toString(), data_left1, y[1], p[zd_index]);
			// 今开
			zd_index = kCompare(kfJrkp, kfZrsp);
			canvas.drawText(kfJrkp.toString(), data_left1, y[2], p[zd_index]);
			// 均价
			KFloat s = new KFloat();
			int wCount = fsDataWCount - 1;
			if (nCjjj_s == null) {
				return;
			}
			zd_index = kCompare(s.init(nCjjj_s[wCount]), kfZrsp);
			canvas.drawText(s.init(nCjjj_s[wCount]).toString(), data_left2,
					y[0], p[zd_index]);
			// 涨幅
			// switch (s.init(nZdf_s[wCount]).toString().compareTo(z))
			// {
			// case -1:
			// zd_index = 2;
			// break;
			// case 0:
			// zd_index = 0;
			// break;
			// case 1:
			// zd_index = 1;
			// break;
			// }
			zd_index = kCompare(kfZjcj, kfZrsp);
			canvas.drawText(s.init(nZdf_s[wCount]).toString("%"), data_left2,
					y[1], p[zd_index]);
			// 昨收
			canvas.drawText(kfZrsp.toString(), data_left2, y[2], p[0]);

			// 总手
			p[3].setColor(clr_fs_yellow);
			canvas.drawText(kfCjss.toString(), data_left1, y[3], p[3]);
			p[3].setColor(Color.WHITE);
			// 换手
			if (kfHsl == null || kfHsl.equals("") || kfHsl.equals("---")) {
				canvas.drawText("---", data_left1, y[4], p[3]);
			} else {
				canvas.drawText(kfHsl.toString() + "%", data_left1, y[4], p[3]);
			}
			p[3].setColor(clr_fs_blue);
			// 总额
			// canvas.drawText(s.init(nCjje_s[wCount]).toString(), data_left1,
			// y[5], p[3]);
			canvas.drawText(kfCjje.toString(), data_left1, y[5], p[3]);
			p[3].setColor(clr_fs_yellow);
			// 现手
			// canvas.drawText(s.init(nCjss_s[wCount]).toString(), data_left2,
			// y[3], p[3]);
			canvas.drawText(kfXl.toString(), data_left2, y[3], p[3]);

			// 振幅
			p[3].setColor(Color.WHITE);
			canvas.drawText(kfZf.toString("%"), data_left2, y[4], p[3]);
			// 量比
			if (kfLb.toString().compareTo("1") > 1) {
				p[3].setColor(clr_fs_red);
			} else if (kfLb.toString().compareTo("1") < 1) {
				p[3].setColor(clr_fs_green);
			} else {
				p[3].setColor(Color.WHITE);
			}
			canvas.drawText(kfLb.toString(), data_left2, y[5], p[3]);

			// 内盘=卖出
			canvas.drawText(kfSelp.toString(), data_left1, y[6], p[2]);
			// 总市值
			p[3].setColor(Color.WHITE);
			canvas.drawText(kfZsz.toString(), data_left1, y[7], p[3]);
			// 流通
			canvas.drawText(kfLtp.toString(), data_left2, y[7], p[3]);
			p[3].setColor(clr_fs_yellow);
			// 外盘=买入
			canvas.drawText(kfBuyp.toString(), data_left2, y[6], p[1]);

			// 最高
			zd_index = kCompare(kfZgcj, kfZrsp);
			canvas.drawText(kfZgcj.toString(), data_left1, y[8], p[zd_index]);
			// 涨停
			canvas.drawText(kfLimUp.toString(), data_left1, y[9], p[1]);
			// 最低
			zd_index = kCompare(kfZdcj, kfZrsp);
			canvas.drawText(kfZdcj.toString(), data_left2, y[8], p[zd_index]);
			// 跌停
			canvas.drawText(kfLimDown.toString(), data_left2, y[9], p[2]);
		}
	}

	public int kCompare(KFloat x1, KFloat x2) {
		int index = 0;
		switch (KFloatUtils.compare(x1, x2)) {
		case -1:
			index = 2;
			break;
		case 0:
			index = 0;
			break;
		case 1:
			index = 1;
			break;
		}
		return index;
	}

	public int sCompare(String x1, String x2) {
		int index = 0;
		switch (x1.compareTo(x2)) {
		case -1:
			index = 2;
			break;
		case 0:
			index = 0;
			break;
		case 1:
			index = 1;
			break;
		}
		return index;
	}

	public void drawFBRect(Canvas canvas, Rect rect) {
		Paint[] p = new Paint[] { new Paint(), new Paint(), new Paint(),
				new Paint(), new Paint() };
		for (Paint pp : p) {
			pp.setAntiAlias(true);
			pp.setFilterBitmap(true);
			pp.setTextSize(theme_fs_bs5_textsize);
		}

		// 虚线
		int h = rect.height() / 8;
		float[] line1 = DrawUtils.getDottedLine(rect.left, h + rect.top, rect,
				true);
		canvas.drawLines(line1, paint);
		// 顶部
		int w = rect.width() / 3;
		int time_left = rect.left + 5;
		int price_left = time_left + w;
		int titleH = h + rect.top - 5;
		p[0].setColor(Color.WHITE);
		canvas.drawText("时间", time_left, titleH, p[0]);
		canvas.drawText("价格", price_left, titleH, p[0]);
		p[4].setColor(Color.WHITE);
		p[4].setTextAlign(Paint.Align.RIGHT);
		int right = rect.right - 10;
		canvas.drawText("成交量", right, titleH, p[4]);

		// 时间参数
		if (mxData == null || mxData[0] == null) {
			return;
		}
		int mx_index = mxData[0].length;// 一共得到多少行数据
		int maxH = theme_fs_bs5_textsize + 4;// 每一行数据最大高度
		int rowCount = (rect.height() - h - 2) / maxH;// 最大可容纳多少行
		// 涨跌
		int zd_index = 0;
		// 价格参数
		p[1].setColor(clr_fs_red);
		p[2].setColor(clr_fs_green);
		p[3].setTextAlign(Paint.Align.RIGHT);
		float prictX = price_left; // 价格显示X坐标
		if (mx_index <= rowCount) {
			if (mx_index == 0) {
				return;
			}

			int y1 = rect.top + h + maxH;
			for (int index = 0; index < mx_index; index++) {
				int y = y1 + maxH * index;
				int top_index = mx_index - index - 1;
				// 时间
				canvas.drawText(DrawUtils.formatNTime(mxData[0][top_index]),
						time_left, y, p[0]);

				// 价格
				KFloat kfZjcj = new KFloat(mxData[2][top_index]);
				zd_index = kCompare(kfZjcj, kfZrsp);
				canvas.drawText(kfZjcj.toString(), prictX, y, p[zd_index]);

				// 成交量
				String sCjlb = String.valueOf((char) mxData[1][top_index]);
				KFloat kfCjss = new KFloat(mxData[3][top_index]);
				p[3].setColor(sCjlb.equals("B") ? clr_fs_red : clr_fs_green);
				canvas.drawText(kfCjss.toString(), right, y, p[3]);
			}
		} else {// 下发的数据条数大于显示区域所 容纳的行数
			int y1 = rect.top + h + maxH;
			if (fb_next == 0) {

				int i = mx_index - rowCount;
				for (int index = i; index < mx_index; index++) {
					int c = index - i;
					int y = y1 + maxH * c;
					int top_index = mx_index - c - 1;

					// 时间
					canvas.drawText(
							DrawUtils.formatNTime(mxData[0][top_index]),
							time_left, y, p[0]);

					// 价格
					KFloat price = new KFloat(mxData[2][top_index]);
					zd_index = kCompare(price, kfZrsp);
					canvas.drawText(price.toString(), prictX, y, p[zd_index]);

					// 成交量
					String sCjlb = String.valueOf((char) mxData[1][top_index]);
					KFloat cjl = new KFloat(mxData[3][top_index]);
					p[3].setColor(sCjlb.equals("B") ? clr_fs_red : clr_fs_green);
					canvas.drawText(cjl.toString(), right, y, p[3]);

				}
			} else {
				for (int index = 0; index < rowCount; index++) {
					int y = y1 + maxH * index;
					int top_index = rowCount - index - 1;
					// 时间
					canvas.drawText(
							DrawUtils.formatNTime(mxData[0][top_index]),
							time_left, y, p[0]);

					// 价格
					KFloat price = new KFloat(mxData[2][top_index]);
					zd_index = kCompare(price, kfZrsp);
					canvas.drawText(price.toString(), prictX, y, p[zd_index]);

					// 成交量
					String sCjlb = String.valueOf((char) mxData[1][top_index]);
					KFloat cjl = new KFloat(mxData[3][top_index]);
					p[3].setColor(sCjlb.equals("B") ? clr_fs_red : clr_fs_green);
					canvas.drawText(cjl.toString(), right, y, p[3]);
				}
			}

		}

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		eventX = event.getX();
		eventY = event.getY();

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			fb_event = fbRect.contains((int) eventX, (int) eventY);
			mx_event = mxRect.contains((int) eventX, (int) eventY);
			break;
		case MotionEvent.ACTION_MOVE:
			if (fb_event == true && event.getHistorySize() > 0) {
				float hy = event.getHistoricalY(0);
				if (eventY - hy > 10) {
					fb_next = 1;
					invalidate();
				} else if (hy - eventY > 10) {
					fb_next = 0;
					invalidate();

				}

			}
			if (isQiHuo) {// 期货只能按返回键返回
				mx_event = false;
			}
			// if (mx_event == true && event.getHistorySize() > 0)
			// {
			// float hy = event.getHistoricalY(0);
			// if (eventY - hy > 10)
			// {
			// MxActivity mx = (MxActivity) this.getContext();
			// mx.finish();
			// // Intent intent = new Intent();
			// // intent.setClass(mx, MinuteActivity.class);
			// // mx.startActivity(intent);
			// }
			// }
			break;
		case MotionEvent.ACTION_UP:
			break;
		}

		return true;
	}

	public void onDestroy() {
		paint = null;

		mxRect = null;
		fbRect = null;

		kfZjcj = null;
		nZdf_s = null;
		kfJrkp = null;
		kfZrsp = null;
		nCjjj_s = null;
		kfZd = null;
		kfCjss = null;
		nCjss_s = null;
		kfZf = null;
		nCjje_s = null;
		kfCjje = null;
		kfLb = null;
		kfBuyp = null;
		kfSelp = null;
		kfZgcj = null;
		kfZdcj = null;
		kfLimUp = null;
		kfLimDown = null;

		mxData = null;
	}
}
