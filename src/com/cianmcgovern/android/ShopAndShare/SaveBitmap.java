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

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;

public class SaveBitmap {

    private final String mPath;
    private byte[] mOrigBmp;

    /**
     * Constructor that calls the save method to save the byte array in the
     * location specified by x
     * 
     * @param bmp
     *            The byte array to be saved as an image
     * @param x
     *            The path where it should be saved ie. getFilesDir()
     */
    public SaveBitmap(byte[] bmp, String x) {
        mPath = x;
        if (bmp != null) {
            Log.d(Constants.LOG_TAG, "Bitmap input is not empty");
            this.mOrigBmp = bmp;
            save();
        }
        else {
            Log.e(Constants.LOG_TAG, "SaveBitmap input is empty");
        }

    }

    /**
     * Saves the byte array as a JPEG file in getFilesDir() Uses a matrix to
     * rotate the image 90 degrees
     */
    private void save() {
        try {
            FileOutputStream out = new FileOutputStream(new File(mPath
                    + "/image.jpg"));
            Bitmap bitmap = decodeSampledBitmapFromResource(mOrigBmp);
            Matrix mat = new Matrix();
            mat.postRotate(90);
            Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                    bitmap.getWidth(), bitmap.getHeight(), mat, true);
            resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            bitmap.recycle();
            resizedBitmap.recycle();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(Constants.LOG_TAG, "Could not save bitmap");
        }
    }

    public int calculateInSampleSize(
            BitmapFactory.Options options) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > (height/2.5) || width > (width/2.5)) {
            if (width > height) {
                inSampleSize = Math.round((float)height / (float)(height/2.5));
            } else {
                inSampleSize = Math.round((float)width / (float)(width/2.5));
            }
        }
        return inSampleSize;
    }
    
    public Bitmap decodeSampledBitmapFromResource(byte[] mOrigBmp) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(mOrigBmp, 0, mOrigBmp.length, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(mOrigBmp, 0, mOrigBmp.length, options);
    }
}
