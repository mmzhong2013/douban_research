package com.dcheck;

import android.app.Activity;



public class SystemCommon {
	private static final float maxWidth = 1080;
	
	private static final float maxHeight = 1920;
	
	private static Activity context = null;
	public enum DIR{
		north, south, east, west
	}
	public static void setActivity(Activity context){
		SystemCommon.context = context;
	}
	public static int getScreenWidth(){  
		return context.getWindowManager().getDefaultDisplay().getWidth();
	}
	
	public static int getScreenHeight(){
		return context.getWindowManager().getDefaultDisplay().getHeight();
	}

	
	public static final float getWidthCoefficient(){
		float Coefficient = getScreenWidth()/maxWidth;
		return Coefficient;
	}
	
	public static final float getHeightCoefficient(){
		float Coefficient = getScreenHeight()/maxHeight;
		return Coefficient;
	}
}
