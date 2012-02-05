/*******************************************************************************
 * Copyright (c) 2012 Cian Mc Govern.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     Cian Mc Govern - initial API and implementation
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
