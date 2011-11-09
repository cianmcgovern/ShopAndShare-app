package com.cianmcgovern.android.ShopAndStore;

import java.io.File;
import java.io.FileOutputStream;
import android.graphics.Bitmap;
import android.util.Log;

public class SaveBitmap{
	
	private final String path;
	public SaveBitmap(Bitmap bmp, String x){
		
		path=x;
		if(bmp!=null){
			Log.d("ShopAndStore","Bitmap input is not empty");
			save(bmp);
		}
		else{
			Log.e("ShopAndStore","SaveBitmap input is empty");
		}
		
	}
	
	private void save(Bitmap bmp){
		try{
			FileOutputStream out = new FileOutputStream(new File(path+"/camera.jpg"));
			bmp.compress(Bitmap.CompressFormat.JPEG, 100, out);
			out.close();
		}
		catch(Exception e){
			Log.e("ShopAndStore","Could not save bitmap");
		}
	}
}
