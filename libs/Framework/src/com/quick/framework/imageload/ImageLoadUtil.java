package com.quick.framework.imageload;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class ImageLoadUtil {

	
	public static void setImageDrawable(String url, final ImageView imgView, int defaultImageResid){
		
		if(defaultImageResid != 0){
			DisplayImageOptions.Builder builder = getdefaultDIOBuilder().showImageOnFail(defaultImageResid).showImageForEmptyUri(defaultImageResid);
			ImageLoader.getInstance().displayImage(url, imgView, builder.build());
		}
		ImageLoader.getInstance().displayImage(url, imgView);
	}
	
	
	public static void init(Context context ){
		
		DisplayImageOptions displayOptions = getdefaultDIOBuilder().build();
		
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
		.defaultDisplayImageOptions(displayOptions)
		.threadPoolSize(3)
		.memoryCacheSizePercentage(13)
		.build();

		ImageLoader.getInstance().init(config);
		
		
	}
	
	public static DisplayImageOptions.Builder getdefaultDIOBuilder(){
	
		DisplayImageOptions.Builder dfaultDIOBuilder = new DisplayImageOptions.Builder()
		.cacheInMemory(true)
		.cacheOnDisk(true)
		.resetViewBeforeLoading(true)
		.bitmapConfig(Bitmap.Config.RGB_565);
		
		return dfaultDIOBuilder;
	}
}
