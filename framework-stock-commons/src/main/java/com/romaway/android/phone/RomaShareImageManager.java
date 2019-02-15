package com.romaway.android.phone;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.romaway.android.phone.widget.RomaToast;
import com.romaway.common.android.base.Res;
import com.romaway.commons.lang.StringUtils;
import com.romaway.commons.log.Logger;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

/**
 * Created by hongrb on 2017/11/16.
 */
public class RomaShareImageManager {

    private static RomaShareImageManager romaShareImageManager;

    // 上下文对象
    private Context context;

    private RomaShareImageManager(Context context) {
        this.context = context;
    }

    public static RomaShareImageManager getInstance(Context context) {

        if (romaShareImageManager == null) {
            romaShareImageManager = new RomaShareImageManager(context);
        }
        return romaShareImageManager;
    }


    public static Bitmap shotScrollView(ScrollView scrollView) {
        int h = 0;
        Bitmap bitmap = null;
        Logger.d("getUrlBitmap", "scrollView = " + scrollView.getChildCount());
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();
            scrollView.getChildAt(i).setBackgroundColor(Res.getColor(R.color.gxs_while));
        }
        bitmap = Bitmap.createBitmap(scrollView.getWidth(), h, Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        scrollView.draw(canvas);
        return bitmap;
    }

    public static Bitmap shotScrollView(ScrollView scrollView, View v) {
        int h = 0;
        Bitmap bitmap = null;
        Logger.d("getUrlBitmap", "scrollView = " + scrollView.getChildCount());
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();
            scrollView.getChildAt(i).setBackgroundColor(Res.getColor(R.color.gxs_while));
        }
        h = h - v.getMeasuredHeight();
        bitmap = Bitmap.createBitmap(scrollView.getWidth(), h, Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        scrollView.draw(canvas);
        return bitmap;
    }

    public static Bitmap getWebViewBp(WebView v) {
        if (null == v) {
            return null;
        }
//        //获取Picture对象
//        Picture picture = v.capturePicture();
//        //得到图片的宽和高（没有reflect图片内容）
//        int width = picture.getWidth();
//        int height = picture.getHeight();
//        if (width > 0 && height > 0) {
//            //创建位图
//            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
//            Canvas canvas = new Canvas(bitmap);
//            //绘制(会调用native方法，完成图形绘制)
//            picture.draw(canvas);
//            return bitmap;
//        }
//        return null;
        //获取webview缩放率
        float scale = v.getScale();
        //得到缩放后webview内容的高度
        int webViewHeight = (int) (v.getContentHeight()*scale);
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(),webViewHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        //绘制
        v.draw(canvas);
        return bitmap;

    }

    public static Bitmap getViewBp(View v) {
        if (null == v) {
            return null;
        }
        v.setDrawingCacheEnabled(true);
        v.buildDrawingCache();
        if (Build.VERSION.SDK_INT >= 11) {
            v.measure(View.MeasureSpec.makeMeasureSpec(v.getWidth(),
                    View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(
                    v.getHeight(), View.MeasureSpec.EXACTLY));
            v.layout((int) v.getX(), (int) v.getY(),
                    (int) v.getX() + v.getMeasuredWidth(),
                    (int) v.getY() + v.getMeasuredHeight());
        } else {
            v.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
        }
        Logger.d("getUrlBitmap", "getDrawingCache = " + v.getDrawingCache());
        Bitmap b = Bitmap.createBitmap(v.getDrawingCache(), 0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());

        v.setDrawingCacheEnabled(false);
        v.destroyDrawingCache();
        return b;
    }

    public Bitmap captureScreenforRecord(WebView v){

        v.measure(View.MeasureSpec.makeMeasureSpec(
                View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        v.layout(0, 0, v.getMeasuredWidth(),
                v.getMeasuredHeight());
        v.setDrawingCacheEnabled(true);
        v.buildDrawingCache();

        Bitmap bm = Bitmap.createBitmap(v.getMeasuredWidth(),
                v.getMeasuredHeight(), Bitmap.Config.RGB_565);

        Canvas bigcanvas = new Canvas(bm);
        Paint paint = new Paint();
        int iHeight = bm.getHeight();
        bigcanvas.drawBitmap(bm, 0, iHeight, paint);
        v.draw(bigcanvas);
        return  bm;
    }

    public Bitmap captureScreenforRecord(ScrollView v){

        v.measure(View.MeasureSpec.makeMeasureSpec(
                View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        v.layout(0, 0, v.getMeasuredWidth(),
                v.getMeasuredHeight());
        v.setDrawingCacheEnabled(true);
        v.buildDrawingCache();

        Bitmap bm = Bitmap.createBitmap(v.getMeasuredWidth(),
                v.getMeasuredHeight(), Bitmap.Config.RGB_565);

        Canvas bigcanvas = new Canvas(bm);
        Paint paint = new Paint();
        int iHeight = bm.getHeight();
        bigcanvas.drawBitmap(bm, 0, iHeight, paint);
        v.draw(bigcanvas);
        return  bm;
    }

    public Bitmap shotActivity(Activity ctx) {

        View view = ctx.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();

        Bitmap bp = Bitmap.createBitmap(view.getDrawingCache(), 0, 0, view.getMeasuredWidth(),
                view.getMeasuredHeight());

        view.setDrawingCacheEnabled(false);
        view.destroyDrawingCache();

        return bp;
    }

    /**
     * 纵向拼接
     * <功能详细描述>
     * @param first
     * @param second
     * @return
     */
    public Bitmap addBitmap(Bitmap first, Bitmap second) {
        int width = Math.max(first.getWidth(),second.getWidth());
        int height = first.getHeight() + second.getHeight();
        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        canvas.drawColor(Res.getColor(R.color.gxs_while));
        canvas.drawBitmap(first, 0, 0, null);
        canvas.drawBitmap(second, 0, first.getHeight(), null);
        return result;
    }

    Long time1, time2;

    public Bitmap getUrlBitmap(Bitmap first, String url) {
        time1 = System.currentTimeMillis();
        Logger.d("getUrlBitmap", "first = " + first);
        Logger.d("getUrlBitmap", "url = " + url);
        if (StringUtils.isEmpty(url)) {
            return null;
        }
        int width = 0;
        if (first != null) {
            width = first.getWidth();
        }
        Bitmap bm = null;
        try {
            URL iconUrl = new URL(url);
            URLConnection conn = iconUrl.openConnection();
//            conn.setUseCaches(false);
            HttpURLConnection http = (HttpURLConnection) conn;
            http.setUseCaches(false);
            int length = http.getContentLength();

            conn.connect();
            // 获得图像的字符流
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is, length);
            Bitmap b = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();// 关闭流
            if (width == 0) {
                width = b.getWidth();
            }
//            bm = Bitmap.createBitmap(width, b.getHeight(), Bitmap.Config.ARGB_8888);
            bm = b;
            Canvas canvas = new Canvas(bm);
            canvas.drawColor(Res.getColor(R.color.gxs_while));
            float x = 0;
            if (b.getWidth() < width) {
                x = (width - b.getWidth()) / 2;
            }
            canvas.drawBitmap(b, x, 0, null);
            time2 = System.currentTimeMillis();
            Logger.d("getUrlBitmap", "time2 - time1 = " + (time2 - time1));
            Logger.d("getUrlBitmap", "bm = " + bm);
        }
        catch (Exception e) {
            e.printStackTrace();
            Logger.d("getUrlBitmap", "Exception = " + e.getMessage());
        }
        return bm;
    }

    public Bitmap getUrlBitmap(ScrollView scrollView, final String url) {
        Bitmap first = shotScrollView(scrollView);
        Logger.d("getUrlBitmap", "first = " + first);
        Logger.d("getUrlBitmap", "url = " + url);
        if (StringUtils.isEmpty(url)) {
            return null;
        }
        final int[] width = {0};
        if (first != null) {
            width[0] = first.getWidth();
        }
        final Bitmap[] bm = {null};
        scrollView.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    URL iconUrl = new URL(url);
                    URLConnection conn = iconUrl.openConnection();
                    HttpURLConnection http = (HttpURLConnection) conn;

                    int length = http.getContentLength();

                    conn.connect();
                    // 获得图像的字符流
                    InputStream is = conn.getInputStream();
                    BufferedInputStream bis = new BufferedInputStream(is, length);
                    Bitmap b = BitmapFactory.decodeStream(bis);
                    bis.close();
                    is.close();// 关闭流
                    if (width[0] == 0) {
                        width[0] = b.getWidth();
                    }
                    bm[0] = Bitmap.createBitmap(width[0], b.getHeight(), Bitmap.Config.ARGB_8888);
                    Canvas canvas = new Canvas(bm[0]);
                    canvas.drawColor(Res.getColor(R.color.gxs_while));
                    float x = 0;
                    if (b.getWidth() < width[0]) {
                        x = (width[0] - b.getWidth()) / 2;
                    }
                    canvas.drawBitmap(b, x, 0, null);
                    Logger.d("getUrlBitmap", "bm = " + bm[0]);
                }
                catch (Exception e) {
                    e.printStackTrace();
                    Logger.d("getUrlBitmap", "Exception = " + e.getMessage());
                }
            }
        }, 0);
        return bm[0];
    }

    public Bitmap getUrlBitmap(String url) {
        if (StringUtils.isEmpty(url)) {
            return null;
        }
        Bitmap bm = null;
        try {
            URL iconUrl = new URL(url);
            URLConnection conn = iconUrl.openConnection();
            HttpURLConnection http = (HttpURLConnection) conn;

            int length = http.getContentLength();

            conn.connect();
            // 获得图像的字符流
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is, length);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();// 关闭流
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return bm;
    }

    public Bitmap getUrlBitmap2(Bitmap first, final String url) {
        Logger.d("getUrlBitmap", "first = " + first);
        Logger.d("getUrlBitmap", "url = " + url);
        if (StringUtils.isEmpty(url)) {
            return null;
        }
        final int[] width = {0};
        if (first != null) {
            width[0] = first.getWidth();
        }
        final Bitmap[] bm = {null};
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL iconUrl = new URL(url);
                    URLConnection conn = iconUrl.openConnection();
                    HttpURLConnection http = (HttpURLConnection) conn;

                    int length = http.getContentLength();

                    conn.connect();
                    // 获得图像的字符流
                    InputStream is = conn.getInputStream();
                    BufferedInputStream bis = new BufferedInputStream(is, length);
                    Bitmap b = BitmapFactory.decodeStream(bis);
                    bis.close();
                    is.close();// 关闭流
                    if (width[0] == 0) {
                        width[0] = b.getWidth();
                    }
                    bm[0] = Bitmap.createBitmap(width[0], b.getHeight(), Bitmap.Config.ARGB_8888);
                    Canvas canvas = new Canvas(bm[0]);
                    canvas.drawColor(Res.getColor(R.color.gxs_while));
                    float x = 0;
                    if (b.getWidth() < width[0]) {
                        x = (width[0] - b.getWidth()) / 2;
                    }
                    canvas.drawBitmap(b, x, 0, null);
                    Logger.d("getUrlBitmap", "bm = " + bm[0]);
                }
                catch (Exception e) {
                    e.printStackTrace();
                    Logger.d("getUrlBitmap", "Exception = " + e.getMessage());
                }
//                Message msg1 = new Message();
//                msg1.what = 0;
//                msg1.obj = bm[0];
//                System.out.println("000");
//                handle.sendMessage(msg1);
            }
        }).start();
        Logger.d("getUrlBitmap", "bm = " + bm[0]);
        return bm[0];
    }

    public class ImageUtils extends AsyncTask<String, Void, Bitmap> {
        Activity mActivity;

        public ImageUtils(Activity activity) {
            this.mActivity = activity;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap  result) {

        }
    }

    Bitmap imageBitmap;

    //在消息队列中实现对控件的更改

//    private Handler handle = new Handler() {
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case 0:
//                    System.out.println("111");
//                    imageBitmap=(Bitmap)msg.obj;
//                    break;
//            }
//        };
//    };

}
