package com.any.org.commonlibrary.utils;

import android.content.Context;
import android.content.res.Resources;

/**
 * anyrsan
 */
public class DensityUtil {

	public static int dp2px(Resources res, float paramFloat) {
		return (int) (0.5F + paramFloat * res.getDisplayMetrics().density);
	}

	public static int dip2px(Context paramContext, float paramFloat) {
		return (int) (0.5F + paramFloat
				* paramContext.getResources().getDisplayMetrics().density);
	}

	public static final int getHeightInDp(Context paramContext) {
		return px2dip(paramContext, paramContext.getResources()
				.getDisplayMetrics().heightPixels);
	}

	public static final int getHeightInPx(Context paramContext) {
		return paramContext.getResources().getDisplayMetrics().heightPixels;
	}

	public static final int getWidthInDp(Context paramContext) {
		return px2dip(paramContext, paramContext.getResources()
				.getDisplayMetrics().widthPixels);
	}

	public static final int getWidthInPx(Context paramContext) {
		return paramContext.getResources().getDisplayMetrics().widthPixels;
	}

	public static int px2dip(Context paramContext, float paramFloat) {
		return (int) (0.5F + paramFloat
				/ paramContext.getResources().getDisplayMetrics().density);
	}

	public static int px2sp(Context paramContext, float paramFloat) {
		return (int) (0.5F + paramFloat
				/ paramContext.getResources().getDisplayMetrics().density);
	}

	public static int sp2px(Context paramContext, float paramFloat) {
		return (int) (0.5F + paramFloat
				* paramContext.getResources().getDisplayMetrics().scaledDensity);
	}


	public static float scaledDensity(Context paramsContext){
		return  paramsContext.getResources().getDisplayMetrics().density;
	}
}