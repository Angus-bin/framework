package com.romaway.android.phone.share;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Build;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ScreenShot {  
   
    @TargetApi(Build.VERSION_CODES.CUPCAKE)
	public static Bitmap takeScreenShot(Activity activity) { 
    	
    	Bitmap b;
    	 // View是你需要截图的View  
        View view = activity.getWindow().getDecorView();  
        view.setDrawingCacheEnabled(true);  
        view.buildDrawingCache();  
        Bitmap b1 = view.getDrawingCache();  
    	try{
	        // 获取状态栏高度  
	        Rect frame = new Rect();  
	        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);  
	        int statusBarHeight = frame.top;  
	   
	        // 获取屏幕长和高  
	        int width = activity.getWindowManager().getDefaultDisplay().getWidth();  
	        int height = activity.getWindowManager().getDefaultDisplay()  
	                .getHeight();  
	        // 去掉标题栏  
	        b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height  
	                - statusBarHeight);  
	        view.destroyDrawingCache();  
    	}catch(Exception e){
    		return b1;
    	}
        return b;  
    }  
   
    private static void savePic(Bitmap b, File filePath) {  
        FileOutputStream fos = null;  
        try {  
            fos = new FileOutputStream(filePath);  
            if (null != fos) {  
                b.compress(Bitmap.CompressFormat.PNG, 100, fos);  
                fos.flush();  
                fos.close();  
            }  
        } catch (FileNotFoundException e) {  
            // e.printStackTrace();  
        	Log.d("tag", "ScreenShotScreenShot_2");
        } catch (IOException e) {  
            // e.printStackTrace();  
        	Log.d("tag", "ScreenShotScreenShot_3");
        }  
    }

    public static void shoot(Activity a, File filePath) {
        Log.d("tag", "ScreenShotScreenShot filePath = "+filePath);
        if (filePath == null) {
            return;
        }
        try {
            if (!filePath.exists()) {
                filePath.createNewFile();
            }
            ScreenShot.savePic(ScreenShot.takeScreenShot(a), filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
