package roma.romaway.commons.android.tougu.ImageView;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.romaway.android.phone.R;
import com.romaway.commons.log.Logger;
import com.romawaylibs.picasso.PicassoHelper;
import com.squareup.picasso.Callback;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;


import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import roma.romaway.commons.android.tougu.ImageView.ImageViewTouch.OnImageViewTouchDoubleTapListener;
import roma.romaway.commons.android.tougu.ImageView.ImageViewTouchBase.OnDrawableChangeListener;
import roma.romaway.commons.android.tougu.ImageView.utils.DecodeUtils;

/**
 * Created by hongrb on 2017/11/27.
 */
public class ImageViewActivity extends Activity {

    private static final String LOG_TAG = "ImageViewActivity";
    private String imageUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PushAgent.getInstance(this).onAppStart();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.image_main);
        if (this.getIntent() != null) {
            Bundle bundle = this.getIntent().getExtras();
            imageUrl = bundle.getString("image_url", "");
        }
        Logger.d(LOG_TAG, "imageUrl = " + imageUrl);
    }

    ImageViewTouch mImage;

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        mImage = (ImageViewTouch) findViewById(R.id.image);

        // set the default image display type
        mImage.setDisplayType(ImageViewTouchBase.DisplayType.FIT_TO_SCREEN);

        mImage.setSingleTapListener(
                new ImageViewTouch.OnImageViewTouchSingleTapListener() {

                    @Override
                    public void onSingleTapConfirmed() {
                        Logger.d(LOG_TAG, "onSingleTapConfirmed");
                        finish();
                    }
                }
        );

        mImage.setDoubleTapListener(
                new OnImageViewTouchDoubleTapListener() {

                    @Override
                    public void onDoubleTap() {
                        Logger.d(LOG_TAG, "onDoubleTap");
                    }
                }
        );

        mImage.setOnDrawableChangedListener(
                new OnDrawableChangeListener() {

                    @Override
                    public void onDrawableChanged(Drawable drawable) {
                        Logger.i(LOG_TAG, "onBitmapChanged: " + drawable);
                    }
                }
        );

        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mImage.postDelayed(new Runnable() {
            @Override
            public void run() {
                startThread();
//                selectRandomImage(false);
            }
        }, 300);

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                selectRandomImage(false);
//            }
//        }).start();

//        if (!StringUtils.isEmpty(imageUrl)) {
//            PicassoHelper.load(ImageViewActivity.this, mImage, imageUrl, new Callback() {
//                @Override
//                public void onSuccess() {
//
//                }
//
//                @Override
//                public void onError() {
//
//                }
//            });
//        }

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public void selectRandomImage(boolean small) {
//        final Bitmap[] bitmap = {null};
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                bitmap[0] = RomaShareImageManager.getInstance(ImageViewActivity.this).getUrlBitmap(imageUrl);
//            }
//        }).start();
//
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        Log.d("image", Uri.parse(imageUrl).toString());
//
//        final DisplayMetrics metrics = getResources().getDisplayMetrics();
//        int size = (int) (Math.min(metrics.widthPixels, metrics.heightPixels) / 0.55);
//
//        if (small) {
//            size /= 3;
//        }
//
//        Bitmap bitmap = DecodeUtils.decode(this, Uri.parse(imageUrl), size, size);
//        Bitmap overlay = getOverlayBitmap("circle-black-medium.png");
        if (null != imageBitmap) {
            mImage.setOnDrawableChangedListener(
                    new OnDrawableChangeListener() {
                        @Override
                        public void onDrawableChanged(final Drawable drawable) {
                            Logger.v(LOG_TAG, "image scale: " + mImage.getScale() + "/" + mImage.getMinScale());
                            Logger.v(LOG_TAG, "scale type: " + mImage.getDisplayType() + "/" + mImage.getScaleType());

                        }
                    }
            );
            mImage.setImageBitmap(imageBitmap, null, -1, -1);
//            PicassoHelper.loadBig(ImageViewActivity.this, mImage, imageUrl, new Callback() {
//                @Override
//                public void onSuccess() {
//
//                }
//
//                @Override
//                public void onError() {
//
//                }
//            }, false);
//            mImage.setImageBitmap(imageBitmap, null, -1, -1);
        }
    }

    public void startThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("image", Uri.parse(imageUrl).toString());

                final DisplayMetrics metrics = getResources().getDisplayMetrics();
                int size = (int) (Math.min(metrics.widthPixels, metrics.heightPixels) / 0.55);
                Bitmap bitmap = DecodeUtils.decode(ImageViewActivity.this, Uri.parse(imageUrl), size, size);
                Bitmap bmp = getURLimage(imageUrl);
                Message msg1 = new Message();
                msg1.what = 0;
                msg1.obj = bitmap;
                System.out.println("000");
                handle.sendMessage(msg1);
            }
        }).start();
    }

    //加载图片
    public Bitmap getURLimage(String url) {
        Bitmap bmp = null;
        try {
            Logger.d(LOG_TAG, "Url = " + url);
            URL myurl = new URL(url);
            Logger.d(LOG_TAG, "myUrl = " + myurl);
            // 获得连接
            URLConnection conn = (URLConnection) myurl.openConnection();
            conn.setConnectTimeout(15000);//设置超时
            conn.setDoInput(true);
            conn.setUseCaches(false);//不缓存
            conn.connect();
            InputStream is = conn.getInputStream();//获得图片的数据流
            bmp = BitmapFactory.decodeStream(is);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bmp;
    }

    Bitmap imageBitmap;

    //在消息队列中实现对控件的更改
    private Handler handle = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    System.out.println("111");
                    imageBitmap=(Bitmap)msg.obj;
                    selectRandomImage(false);
                    break;
            }
        };
    };

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("ImageViewActivity");
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("ImageViewActivity");
        MobclickAgent.onPause(this);
    }
}
