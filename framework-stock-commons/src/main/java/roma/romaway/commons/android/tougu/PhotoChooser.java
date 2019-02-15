package roma.romaway.commons.android.tougu;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import com.romaway.android.phone.R;
import com.romaway.commons.android.fileutil.FileSystem;
import com.romaway.commons.lang.DateUtils;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * 相册、拍照图片选择器
 * @author wanlh
 *
 */
public class PhotoChooser implements View.OnClickListener {

	public static String fileName = "roma_stock.jpg";
	public static final int REQUESTCODE_CAMERA = 100;
	public static final int REQUESTCODE_PHOTO = 101;
	public static final int REQUESTCODE_CUTTING = 102;
	
	public static File file_dir;
	public static Uri tempFileUri;
	
	private Activity mActivity;
	public PhotoChooser(Activity activity){
		mActivity = activity;
		
		initPopupWindow();
		file_dir = FileSystem.getCacheRootDir(mActivity, "images_tg");
	}
	
	private TextView txt_choice, txt_take, txt_cancle;
	private LinearLayout ll_parent;
	private PopupWindow popupWindow;
	@SuppressLint({ "InlinedApi", "NewApi" })
	private void initPopupWindow(){
		LayoutInflater inflater = LayoutInflater.from(mActivity);
		View popView = inflater.inflate(R.layout.roma_photo_chooser_popup_window, null);
		txt_choice = (TextView) popView.findViewById(R.id.abs_txt_choice_from_photo);
		txt_take = (TextView) popView.findViewById(R.id.abs_txt_take_a_photo);
		txt_cancle = (TextView) popView.findViewById(R.id.abs_txt_cancle);
		ll_parent = (LinearLayout) popView.findViewById(R.id.abs_ll_parent);
		txt_choice.setOnClickListener(this);
		txt_take.setOnClickListener(this);
		txt_cancle.setOnClickListener(this);
		ll_parent.setOnClickListener(this);
		
		popupWindow = new PopupWindow(popView, 
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		popupWindow.update();
	}
	
	/**
	 * 显示图片选择器
	 * @param parentView
	 */
	public void showPhotoChooser(View parentView){
		popupWindow.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
	}
	
	/**
	 * 获取照片的存储位置
	 * @return
	 */
	public File getPhotoSaveFolder(){
		return file_dir;
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		if(v.getId() == R.id.abs_txt_choice_from_photo){
//			Intent pickIntent = new Intent(Intent.ACTION_GET_CONTENT);
//			pickIntent.setType("image/*");
//			mActivity.startActivityForResult(pickIntent, REQUESTCODE_PHOTO);
			//当sdk版本低于19时使用此方法
			if (Build.VERSION.SDK_INT < 19) {
				Intent pickIntent = new Intent(Intent.ACTION_GET_CONTENT);
				pickIntent.setType("image/*");
				mActivity.startActivityForResult(pickIntent, REQUESTCODE_PHOTO);
			} else {
				Intent pickIntent = new Intent(Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				mActivity.startActivityForResult(pickIntent, REQUESTCODE_PHOTO);
			}
			popupWindow.dismiss();
			
		}else if(v.getId() == R.id.abs_txt_take_a_photo){
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			fileName = DateUtils.format_YYYYMMDDHHMMSS(new Date()) + ".jpg";
			//指定照片存储位置
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(file_dir, fileName)));
			//设置照片质量，0-低质量 1-高质量
//			intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
			mActivity.startActivityForResult(intent, REQUESTCODE_CAMERA);
			popupWindow.dismiss();
			
		}else if(v.getId() == R.id.abs_txt_cancle){
			popupWindow.dismiss();
			
		}else if(v.getId() == R.id.abs_ll_parent){
			popupWindow.dismiss();
		}
	}
}
