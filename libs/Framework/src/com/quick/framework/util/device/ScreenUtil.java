package com.quick.framework.util.device;

import android.content.Context;
import android.util.DisplayMetrics;

public class ScreenUtil {
	
		public static int getWidth(Context context){
			DisplayMetrics dm = context.getResources().getDisplayMetrics();
			return dm.widthPixels;
		}
		public static int getHeight(Context context){
			DisplayMetrics dm = context.getResources().getDisplayMetrics();
			return dm.heightPixels;
		}
		public static int getDensityDPI(Context context){
			DisplayMetrics dm = context.getResources().getDisplayMetrics();
			return dm.densityDpi;
		}
		public static float getDensity(Context context){
			DisplayMetrics dm = context.getResources().getDisplayMetrics();
			return dm.density;
		}
		
		public static DisplayMetrics getDisplayMetrics(Context context){
			DisplayMetrics dm = context.getResources().getDisplayMetrics();
			return dm;
		}
		public static int dp2Px(Context context,int dp){
			return (int)(dp*getDensity(context));
		}
}
