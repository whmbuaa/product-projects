package com.quick.uilib.picturechooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

import com.quick.framework.util.dir.DirUtil;
import com.quick.uilib.R;
import com.quick.uilib.imageview.ZoomImageView;

public class CropImageActivity extends Activity {
	private static final int MAX_INPUT_IMAGE_WIDTH = 1280;
	private static final int MAX_INPUT_IMAGE_HEIGHT = 1280;
	
	
	private static final String TAG = CropImageActivity.class.getSimpleName();
	
	private Bitmap  mBitmapSrc;
	private ZoomImageView mZoomImageView;
	private TransparentFrameGridView    mCropWindow;
	
	private ImageButton  mBtnConfirm;
	private ImageButton  mBtnCancel;
	private ImageButton mBtnRotate;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.crop_image);
		
		initViews();
		
		
		Uri uri = getIntent().getParcelableExtra(PictureChooserConstant.KEY_URI);
		mBitmapSrc = decodeBitmapFromUri(getContentResolver(),uri,MAX_INPUT_IMAGE_WIDTH, MAX_INPUT_IMAGE_HEIGHT);
		
		mZoomImageView = (ZoomImageView)findViewById(R.id.image);
		mZoomImageView.setImageBitmap(mBitmapSrc);
		
		float aspectRatio = getIntent().getFloatExtra(PictureChooserConstant.KEY_PIC_ASPECTRATIO, 1.0f);
		mCropWindow.setAspectRation(aspectRatio);

	}
	
	
	
	@Override
	public void onAttachedToWindow() {
		// TODO Auto-generated method stub
		super.onAttachedToWindow();
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);

		if(hasFocus){
			mZoomImageView.setBarrier(new Rect(mCropWindow.getLeft(),mCropWindow.getTop(),mCropWindow.getRight(),mCropWindow.getBottom()));
		}
	}
	private void initViews(){
		mBtnConfirm  = (ImageButton)findViewById(R.id.confirm);
		mBtnCancel =  (ImageButton)findViewById(R.id.cancel);
		mCropWindow = (TransparentFrameGridView)findViewById(R.id.crop_window);
		mBtnRotate = (ImageButton)findViewById(R.id.rotate);
		
		mBtnConfirm.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				RectF rectFrame = new RectF(mCropWindow.getLeft(),mCropWindow.getTop(),mCropWindow.getRight(),mCropWindow.getBottom());
				Matrix matrix = mZoomImageView.getCurrentMatrix();
				Matrix inverMatrix = new Matrix();
				if(!matrix.invert(inverMatrix)){
					Log.i(TAG,"matrix can not invert.");
					Toast.makeText(CropImageActivity.this, "剪裁图片失败：matrix can not invert.", Toast.LENGTH_SHORT);
					setResult(RESULT_CANCELED);
					
				}
				else{ 
					inverMatrix.mapRect(rectFrame);
					
					// limit picutre size 
					float scale = Math.max(rectFrame.width()/PictureChooserConstant.MAX_OUTPUT_IMAGE_WIDTH,rectFrame.height()/PictureChooserConstant.MAX_OUTPUT_IMAGE_HEIGHT);
					scale = Math.max(scale, 1);
			
					// compress to file
					Bitmap resultBitmap = CropImage(mZoomImageView.getBitpmap(), (int)rectFrame.left, (int)rectFrame.top, (int)rectFrame.right, (int)rectFrame.bottom, (int)(rectFrame.width()/scale), (int)(rectFrame.height()/scale));
					String pictureFullPath = DirUtil.getAvailableFilesDir(CropImageActivity.this,File.separator+"pic_cropped_"+System.currentTimeMillis()+".jpg").getAbsolutePath();
					try {
						resultBitmap.compress(CompressFormat.JPEG, PictureChooserConstant.JPG_QUALITY, new FileOutputStream(pictureFullPath));
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						Log.i(TAG,e.toString());
					}
					finally{
						resultBitmap.recycle();
						mBitmapSrc.recycle();
					}
					
					// return the file path
					setResult(RESULT_OK,new Intent().putExtra(PictureChooserConstant.KEY_RESULT_DATA, pictureFullPath));
				}
				finish();
			}});
		
		mBtnCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				setResult(RESULT_CANCELED);
				finish();
			}
		});
		mBtnRotate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				mBitmapSrc = rotateBitmap(mBitmapSrc, 90);
				mZoomImageView.setImageBitmap(mBitmapSrc);
			}
		});
	}
	
	private Bitmap CropImage(Bitmap bitmapSrc,int left, int top, int right, int bottom,int destWidth, int destHeight){
		
		
		// adjust src rect
		RectF adjustedSrcRectF = adjustPictureSrcRectF(new RectF(left,top,right,bottom), bitmapSrc);
		RectF destRectF = new RectF(adjustedSrcRectF);
		
		// get destination rect
		RectF containerRecF = new RectF(0,0,destWidth, destHeight);
		Matrix matrix =  new Matrix();
		matrix.setRectToRect(adjustedSrcRectF, containerRecF, Matrix.ScaleToFit.CENTER);
		matrix.mapRect(destRectF);
		
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setDither(true);
		
		Bitmap result = Bitmap.createBitmap(destWidth,destHeight,Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(result);
		canvas.drawBitmap(bitmapSrc, fromRectF(adjustedSrcRectF), fromRectF(destRectF), paint);
		return result;
	}
	public static Bitmap decodeBitmapFromUri(ContentResolver contentResolver,Uri uri,int maxWidth, int maxHeight)
	{
		
		try {
			// calculate scale
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(contentResolver.openInputStream(uri), null, options);
			int scale = Math.max(options.outWidth/maxWidth,options.outHeight/maxWidth);
			scale = Math.max(scale, 1);
			Log.d(TAG, "decodeBitmapFromUri scale is : "+scale);
			
			// decode the picture
			options.inSampleSize = scale;
			options.inJustDecodeBounds = false;
			options.inPreferredConfig = Bitmap.Config.RGB_565;
			return BitmapFactory.decodeStream(contentResolver.openInputStream(uri), null, options);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			Log.i(TAG,e.toString());
		}

		return null;
	}
	
	public static Bitmap decodeBitmapFromStream(InputStream inputStream ,int maxImageWidth, int maxImageHeight)
	{
		
			// calculate scale
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(inputStream, null, options);
			int scale = Math.max(options.outWidth/maxImageWidth,options.outHeight/maxImageHeight);
			scale = Math.max(scale, 1);
			Log.d(TAG, "decodeBitmapFromStream scale is : "+scale);
			
			// decode the picture
			options.inSampleSize = scale;
			options.inJustDecodeBounds = false;
			options.inPreferredConfig = Bitmap.Config.RGB_565;
			return BitmapFactory.decodeStream(inputStream, null, options);
	}
	private RectF adjustPictureSrcRectF(RectF rect,Bitmap bitmap){
		
		rect.left = Math.max(rect.left, 0);
		rect.top = Math.max(rect.top, 0);
		rect.right=Math.min(rect.right, bitmap.getWidth());
		rect.bottom = Math.min(rect.bottom, bitmap.getHeight());
		
		return rect;
	}
	
	private Rect fromRectF(RectF rectF){
		Rect rect = new Rect();
		rect.left = (int)rectF.left;
		rect.top = (int)rectF.top;
		rect.right = (int)rectF.right;
		rect.bottom = (int)rectF.bottom;
		
		return rect;
	}
	
	
	private Bitmap rotateBitmap(Bitmap bitmapSrc, int degree) {

		Matrix matrix = new Matrix();
		matrix.setRotate(degree, (float) bitmapSrc.getWidth() / 2,
				(float) bitmapSrc.getHeight() / 2);

		try {

			Bitmap result = Bitmap.createBitmap(bitmapSrc, 0, 0,
					bitmapSrc.getWidth(), bitmapSrc.getHeight(), matrix, true);

			return result;
		} 
		catch (OutOfMemoryError ex) {
		}

		return null;
	}
}
