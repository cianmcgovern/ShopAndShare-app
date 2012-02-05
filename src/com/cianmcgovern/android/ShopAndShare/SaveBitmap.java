/*******************************************************************************
 * Copyright 2012 Cian Mc Govern
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.cianmcgovern.android.ShopAndShare;

import java.io.File;
import java.io.FileOutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;

public class SaveBitmap{

	private final String path;
	private byte[] origBmp;

	/**
	 * Constructor that calls the save method to save the byte array in the location specified by x
	 * 
	 * @param bmp The byte array to be saved as an image
	 * @param x The path where it should be saved ie. getFilesDir()
	 */
	public SaveBitmap(byte[] bmp, String x){
		path=x;
		if(bmp!=null){
			Log.d("ShopAndStore","Bitmap input is not empty");
			this.origBmp=bmp;
			save();
		}
		else{
			Log.e("ShopAndStore","SaveBitmap input is empty");
		}

	}
	
	/**
	 * Saves the byte array as a JPEG file in getFilesDir()
	 * Uses a matrix to rotate the image 90 degrees
	 */
	private void save(){
		try{
			FileOutputStream out = new FileOutputStream(new File(path+"/image.jpg"));
			Bitmap bitmap = BitmapFactory.decodeByteArray(origBmp, 0, origBmp.length);
			Matrix mat = new Matrix();
			mat.postRotate(90);
			Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), mat, true);
			resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
			out.close();
		}
		catch(Exception e){
			e.printStackTrace();
			Log.e("ShopAndStore","Could not save bitmap");
		}
	}

}
