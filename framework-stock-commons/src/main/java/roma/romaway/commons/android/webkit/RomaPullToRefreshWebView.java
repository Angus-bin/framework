/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package roma.romaway.commons.android.webkit;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.webkit.WebView;

import com.handmark.pulltorefresh.library.OverscrollHelper;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.romaway.android.phone.R;

public class RomaPullToRefreshWebView extends PullToRefreshBase<WebView> {

//	private static final OnRefreshListener<WebView> defaultOnRefreshListener = new OnRefreshListener<WebView>() {
//
//		@Override
//		public void onRefresh(PullToRefreshBase<WebView> refreshView) {
//			refreshView.getRefreshableView().reload();
//		}
//
//	};

//	private final WebChromeClient defaultWebChromeClient = new WebChromeClient() {
//
//		@Override
//		public void onProgressChanged(WebView view, int newProgress) {
//			if (newProgress == 100) {
//				onRefreshComplete();
//			}
//		}
//
//	};

	public RomaPullToRefreshWebView(Context context) {
		super(context);

		/**
		 * Added so that by default, Pull-to-Refresh refreshes the page
		 */
		//setOnRefreshListener(defaultOnRefreshListener);
		//mRefreshableView.setWebChromeClient(defaultWebChromeClient);
	}

	public RomaPullToRefreshWebView(Context context, AttributeSet attrs) {
		super(context, attrs);

		/**
		 * Added so that by default, Pull-to-Refresh refreshes the page
		 */
		//setOnRefreshListener(defaultOnRefreshListener);
		//mRefreshableView.setWebChromeClient(defaultWebChromeClient);
	}

	public RomaPullToRefreshWebView(Context context, Mode mode) {
		super(context, mode);

		/**
		 * Added so that by default, Pull-to-Refresh refreshes the page
		 */
		//setOnRefreshListener(defaultOnRefreshListener);
		//mRefreshableView.setWebChromeClient(defaultWebChromeClient);
	}

	public RomaPullToRefreshWebView(Context context, Mode mode, AnimationStyle style) {
		super(context, mode, style);

		/**
		 * Added so that by default, Pull-to-Refresh refreshes the page
		 */
		//setOnRefreshListener(defaultOnRefreshListener);
		//mRefreshableView.setWebChromeClient(defaultWebChromeClient);
	}

	@Override
	public final Orientation getPullToRefreshScrollDirection() {
		return Orientation.VERTICAL;
	}

	@TargetApi(VERSION_CODES.DONUT)
    @SuppressLint("NewApi")
    @Override
	protected RomaWebView createRefreshableView(Context context, AttributeSet attrs) {
		RomaWebView webView;
		if (VERSION.SDK_INT >= VERSION_CODES.GINGERBREAD) {
			webView = new InternalWebViewSDK9(context, attrs);
		} else {
			webView = new RomaWebView(context, attrs);
		}

		webView.setId(R.id.webview);
		return webView;
	}

	@Override
	protected boolean isReadyForPullStart() {
		return mRefreshableView.getScrollY() == 0;
	}

	@Override
	protected boolean isReadyForPullEnd() {
		float exactContentHeight = FloatMath.floor(mRefreshableView.getContentHeight() * mRefreshableView.getScale());
		return mRefreshableView.getScrollY() >= (exactContentHeight - mRefreshableView.getHeight());
	}

	@Override
	protected void onPtrRestoreInstanceState(Bundle savedInstanceState) {
		super.onPtrRestoreInstanceState(savedInstanceState);
		mRefreshableView.restoreState(savedInstanceState);
	}

	@Override
	protected void onPtrSaveInstanceState(Bundle saveState) {
		super.onPtrSaveInstanceState(saveState);
		mRefreshableView.saveState(saveState);
	}

	@TargetApi(9)
	final class InternalWebViewSDK9 extends RomaWebView {

		// WebView doesn't always scroll back to it's edge so we add some
		// fuzziness
		static final int OVERSCROLL_FUZZY_THRESHOLD = 2;

		// WebView seems quite reluctant to overscroll so we use the scale
		// factor to scale it's value
		static final float OVERSCROLL_SCALE_FACTOR = 1.5f;

		public InternalWebViewSDK9(Context context, AttributeSet attrs) {
			super(context, attrs);
		}

		@Override
		protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX,
				int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {

			final boolean returnValue = super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX,
					scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);

			// Does all of the hard work...
			OverscrollHelper.overScrollBy(RomaPullToRefreshWebView.this, deltaX, scrollX, deltaY, scrollY,
					getScrollRange(), OVERSCROLL_FUZZY_THRESHOLD, OVERSCROLL_SCALE_FACTOR, isTouchEvent);

			return returnValue;
		}

		private int getScrollRange() {
			return (int) Math.max(0, FloatMath.floor(mRefreshableView.getContentHeight() * mRefreshableView.getScale())
					- (getHeight() - getPaddingBottom() - getPaddingTop()));
		}
	}
}
