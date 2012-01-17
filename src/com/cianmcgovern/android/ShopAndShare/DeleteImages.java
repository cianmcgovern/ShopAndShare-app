package com.cianmcgovern.android.ShopAndShare;

import java.io.File;

import android.util.Log;

/**
 * Deletes the image taken by the camera and the image that had it's background eliminated in native code
 * 
 * @author cian
 *
 */
public class DeleteImages{
	
	public DeleteImages(String path){
		
		File orig = new File(path+"/image.jpg");
		File bgelim = new File(path+"/bgimage.tiff");
		
		if(!orig.exists() && !bgelim.exists()){
			Log.e("ShopAndStore","DeleteImages: Files do not exists for deletion");
		}
		
		else if(!orig.canWrite() && !bgelim.canWrite()){
			Log.e("ShopAndStore","DeleteImages: Files are not writable");
		}
		
		else{
			if(!orig.delete())
				Log.e("ShopAndStore","DeleteImages: Original file was not successfully deleted");
			
			else if(!bgelim.delete()){
				Log.e("ShopAndStore","DeleteImages: bgElim file was not successfully deleted");
			}
			
			else
				Log.d("ShopAndStore","DeleteImages: Files successfully deleted");
		}
	}
}
