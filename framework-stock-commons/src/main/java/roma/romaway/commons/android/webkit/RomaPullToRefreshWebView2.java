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

import android.content.Context;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.webkit.WebView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase2;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RomaPullToRefreshWebView2 extends PullToRefreshBase2<WebView> {
	/**
	 * 构造方法
	 *
	 * @param context context
	 */
	public RomaPullToRefreshWebView2(Context context) {
		this(context, null);
	}

	/**
	 * 构造方法
	 *
	 * @param context context
	 * @param attrs attrs
	 */
	public RomaPullToRefreshWebView2(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	/**
	 * 构造方法
	 *
	 * @param context context
	 * @param attrs attrs
	 * @param defStyle defStyle
	 */
	public RomaPullToRefreshWebView2(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public Orientation getPullToRefreshScrollDirection() {
		return Orientation.VERTICAL;
	}

	/**
	 * @see com.handmark.pulltorefresh.library.PullToRefreshBase2#createRefreshableView(android.content.Context, android.util.AttributeSet)
	 */
	@Override
	protected WebView createRefreshableView(Context context, AttributeSet attrs) {
		WebView webView = new WebView(context);
		return webView;
	}

	/**
	 * @see com.handmark.pulltorefresh.library.PullToRefreshBase2#isReadyForPullDown()
	 */
	@Override
	protected boolean isReadyForPullDown() {
		return mRefreshableView.getScrollY() == 0;
	}

	/**
	 * @see com.handmark.pulltorefresh.library.PullToRefreshBase2#isReadyForPullUp()
	 */
	@Override
	protected boolean isReadyForPullUp() {
		float exactContentHeight = FloatMath.floor(mRefreshableView.getContentHeight() * mRefreshableView.getScale());
		return mRefreshableView.getScrollY() >= (exactContentHeight - mRefreshableView.getHeight());
	}

	public void setLastUpdateTime() {
		String text = formatDateTime(System.currentTimeMillis());
		setLastUpdatedLabel(text);
	}

	private String formatDateTime(long time) {
		if (0 == time) {
			return "";
		}

		return mDateFormat.format(new Date(time));
	}

}
