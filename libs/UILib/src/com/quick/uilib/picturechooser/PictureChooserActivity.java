package com.quick.uilib.picturechooser ;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.quick.framework.util.dir.DirUtil;
import com.quick.uilib.R;
import com.quick.uilib.actionsheet.DialogActionSheet;



	
public class PictureChooserActivity extends Activity {
	private static final int REQUEST_CODE_FROM_CAMERA = 0 ;
	private static final int REQUEST_CODE_FROM_LOCAL_GALLERY = 1 ;
	private static final int REQUEST_CODE_CROP = 2 ;
	private static final int REQUEST_CODE_FILTER = 3 ;
	private static final String TAG = PictureChooserActivity.class.getSimpleName();
	
	private File  mCameraFile;
	
	// input params
	private float mAspectRatio;
	private boolean mNeedFilter;
	private boolean mNeedCrop;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTheme(android.R.style.Theme_NoDisplay);
		
		mAspectRatio = getIntent().getFloatExtra(PictureChooserConstant.KEY_PIC_ASPECTRATIO, 1.0f);
//		mNeedFilter = getIntent().getBooleanExtra(PictureChooserConstant.KEY_PIC_NEED_FILTER, false);
		mNeedCrop = getIntent().getBooleanExtra(PictureChooserConstant.KEY_PIC_NEED_CROP, true);
		
		showChooserActionSheet();
	}

	private void selectPicFromGallery(){
		Intent intent;
		// 浠庡浘搴撻�夋嫨
		if (Build.VERSION.SDK_INT < 19) {
			intent = new Intent(Intent.ACTION_GET_CONTENT);
			intent.setType("image/*");

		} else {
			// change to below intent to support 4.4 content
			// resolver change
			// http://developer.android.com/about/versions/android-4.4.html
			intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		}
		startActivityForResult(intent, REQUEST_CODE_FROM_LOCAL_GALLERY);
	}
	private void selectPicFromCamera(){
		
		mCameraFile = new File(DirUtil.getAvailableCacheDir(this),"pic_from_camera.jpg");
		startActivityForResult(
				new Intent(MediaStore.ACTION_IMAGE_CAPTURE).putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mCameraFile)),
				REQUEST_CODE_FROM_CAMERA);
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.i(TAG,"onResume() called.");
		
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.i(TAG,"onDestroy() called.");
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.i(TAG,"onStop() called.");
	}
	
	private void handleNoCrop(Uri picUri){
		Bitmap bitmapSrc = CropImageActivity.decodeBitmapFromUri(getContentResolver(), picUri,PictureChooserConstant.MAX_OUTPUT_IMAGE_WIDTH,PictureChooserConstant.MAX_OUTPUT_IMAGE_HEIGHT);
		if(bitmapSrc != null){
		
			String pictureFullPath = DirUtil.getAvailableCacheDir(PictureChooserActivity.this)+File.separator+"pic_no_cropped_"+System.currentTimeMillis()+".jpg";
			try {
				bitmapSrc.compress(CompressFormat.JPEG, PictureChooserConstant.JPG_QUALITY, new FileOutputStream(pictureFullPath));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				Log.i(TAG,e.toString());
			}
			finally{
				bitmapSrc.recycle();
			}
			
			// return the file path
			setResult(RESULT_OK,new Intent().putExtra(PictureChooserConstant.KEY_RESULT_DATA, pictureFullPath));
		}
		else {
			
			Log.e(TAG,"handleNoCrop()-decode image from uri fail. ");
			setResult(RESULT_CANCELED);
		}
		finish();
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if(resultCode == Activity.RESULT_OK){
			switch(requestCode){
			case REQUEST_CODE_FROM_CAMERA :
				{
					if(mNeedCrop){
						
						Intent intent = new Intent(this, CropImageActivity.class);
						intent.putExtra(PictureChooserConstant.KEY_URI, Uri.fromFile(mCameraFile));
						intent.putExtra(PictureChooserConstant.KEY_PIC_ASPECTRATIO, mAspectRatio);
						startActivityForResult(intent, REQUEST_CODE_CROP);
					}
					else {
						handleNoCrop( Uri.fromFile(mCameraFile));
					}
				}
				
				break;
				
			case REQUEST_CODE_FROM_LOCAL_GALLERY :
			
					
				if (data != null && data.getData() != null) {
					if(mNeedCrop){
						
						Intent intent = new Intent(this, CropImageActivity.class);
						intent.putExtra(PictureChooserConstant.KEY_URI, data.getData());
						intent.putExtra(PictureChooserConstant.KEY_PIC_ASPECTRATIO, mAspectRatio);
						startActivityForResult(intent, REQUEST_CODE_CROP);
					}
					else {
						handleNoCrop(data.getData());
					}
				}
					
				break;
			case REQUEST_CODE_CROP :
				{
					if(false /*mNeedFilter*/){
						
//						String imageFullPath = data.getStringExtra(PictureChooserConstant.KEY_RESULT_DATA);
//						if(imageFullPath != null){
//							ImageFilterActivity.launchForResult(this, imageFullPath, REQUEST_CODE_FILTER);
//						}
					}
					else {
						setResult(RESULT_OK,data);
						finish();
						Log.i(TAG,"finish() called after crop.");
					}
				}
				break;
			case REQUEST_CODE_FILTER :
				setResult(RESULT_OK,data);
				finish();
				Log.i(TAG,"finish() called after filter.");
				break;
			}
		}
		else {
			setResult(RESULT_CANCELED);
			finish();
		}
		
	}
	
	protected void showChooserDialog(){
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("选择图片");
		
		String[] items = new String[]{"拍照",
				                      "从手机相册选择"};
		
		builder.setItems(items, new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				if (which == 0) {
					// 鎷嶇収
					selectPicFromCamera();
				} 
				else {
					selectPicFromGallery();

				}
				
				
			}
		});

		
		AlertDialog dlg = builder.show();
		dlg.setOnCancelListener(new OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface arg0) {
				// TODO Auto-generated method stub
				finish();
				 
			}
		});  
		 
	}
	
	protected void showChooserActionSheet(){
		View contentView = View.inflate(this, R.layout.action_sheet_picture_chooser, null);
		final DialogActionSheet actionSheet = new DialogActionSheet(this, contentView, new OnDismissListener() {
			
			@Override
			public void onDismiss(DialogInterface arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	
		// setup action sheet
		contentView.findViewById(R.id.from_camera).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				actionSheet.setOnDismissListener(null);
				actionSheet.dismiss();
				selectPicFromCamera();
			}
		});
		contentView.findViewById(R.id.from_gallery).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				actionSheet.setOnDismissListener(null);
				actionSheet.dismiss();
				selectPicFromGallery();
			}
		});
		contentView.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
	
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				actionSheet.dismiss();
			}
		});
		
		actionSheet.show();
	}
	
//
//	public static void launch(Context context , int requestCode){
//		Intent intent = new Intent(context,PictureChooserActivity.class);
//		context.startActivity(intent,requestCode);
//	}
	
	public static void startForResult(Fragment fragment, boolean needFilter, boolean needCrop,int requestCode){
		
		Intent intent = new Intent(fragment.getActivity(),PictureChooserActivity.class);
		intent.putExtra(PictureChooserConstant.KEY_PIC_NEED_FILTER, needFilter);
		intent.putExtra(PictureChooserConstant.KEY_PIC_NEED_CROP, needCrop);
		fragment.startActivityForResult(intent, requestCode);
	}
	public static void startForResult(Activity activity, boolean needFilter, boolean needCrop, int requestCode){
		Intent intent = new Intent(activity,PictureChooserActivity.class);
		intent.putExtra(PictureChooserConstant.KEY_PIC_NEED_FILTER, needFilter);
		intent.putExtra(PictureChooserConstant.KEY_PIC_NEED_CROP, needCrop);
		activity.startActivityForResult(intent, requestCode);
	}
}
