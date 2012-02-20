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

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.cianmcgovern.android.ShopAndShare.DisplayResults.ListCreator;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * Activity that gets displayed while the native code is executing Uses a
 * separate thread to execute the native code and displays a progress bar in
 * this (UI) thread
 * 
 * @author Cian Mc Govern
 * 
 */
public class LoadingPage extends Activity {

    private ProgressThread mProgThread;
    private ProgressDialog mProgDialog;
    private Context mCon;
    private byte[] mImageData;

    @Override
    public void onCreate(Bundle savedInstance) {

        super.onCreate(savedInstance);

        mCon = this;

        showDialog(0);
    }

    private native void callnativecode(String dir);

    static {
        System.loadLibrary("lept");
        System.loadLibrary("tess");
        System.loadLibrary("ShopAndShare");
    }

    /**
     * Calls the native code methods through the JNI
     * 
     * @param imageLocation
     *            Path to the folder containing the image.jpg ie. the file from
     *            SaveBitmap
     */
    private void AnalyseImage(String imageLocation) {

        // Delete the results file before we call the native code
        if (new File(Constants.FILES_DIR + "/results").exists())
            new File(Constants.FILES_DIR).delete();

        Log.d(Constants.LOG_TAG, "Calling native code");
        callnativecode(imageLocation);
        String[] results = getProducts();
        Results.getInstance().setProducts(results, results.length);
    }

    private String[] getProducts() {

        ArrayList<String> as = new ArrayList<String>();

        try {
            FileInputStream fin = new FileInputStream(Constants.FILES_DIR
                    + "/results");
            DataInputStream din = new DataInputStream(fin);
            BufferedReader br = new BufferedReader(new InputStreamReader(din));

            String line;

            while ((line = br.readLine()) != null) {
                as.add(line);
            }
            br.close();
            din.close();
            fin.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] collectedResults = new String[as.size()];

        for (int i = 0; i < as.size(); i++) {
            collectedResults[i] = as.get(i);
        }

        new File(Constants.FILES_DIR + "/results").delete();

        return collectedResults;
    }

    // Method to create a spinner dialog
    @Override
    protected Dialog onCreateDialog(int id) {
        // Spinner
        mProgDialog = new ProgressDialog(this);
        mProgDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgDialog.setCancelable(false);
        mProgDialog.setMessage(mCon.getText(R.string.loadingMessage));
        mProgThread = new ProgressThread(handler);
        mProgThread.start();
        return mProgDialog;
    }

    // Handler on the main (UI) thread that will receive messages from the
    // second thread and update the progress.

    final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // Get the current value of the variable total from the message data
            // and update the progress bar.
            int total = msg.getData().getInt("total");
            mProgDialog.setProgress(total);
            if (total <= 0) {
                dismissDialog(0);
                mProgThread.setState(ProgressThread.mDone);
                Intent disp = new Intent(ShopAndShare.sContext,
                        ListCreator.class);
                disp.putExtra("Take Photo", true);
                startActivity(disp);
                finish();
            }
        }
    };

    // Inner class that performs progress calculations on a second thread.
    // Implement
    // the thread by subclassing Thread and overriding its run() method. Also
    // provide
    // a setState(state) method to stop the thread gracefully.

    private class ProgressThread extends Thread {

        // Class constants defining state of the thread
        private final static int mDone = 0;
        private final static int mRunning = 1;

        Handler mHandler;
        int mState;
        int mTotal;

        ProgressThread(Handler h) {
            mHandler = h;
        }

        @Override
        public void run() {
            mState = mRunning;
            mTotal = 1;
            while (mState == mRunning) {
                new SaveBitmap(Constants.IMAGE_DATA, Constants.FILES_DIR);
                // Calls native code and passes path to image excluding the file
                // name which is expected to be image.jpg
                AnalyseImage(Constants.FILES_DIR);
                Message msg = mHandler.obtainMessage();
                Bundle b = new Bundle();
                mTotal = 0;
                b.putInt("total", mTotal);
                msg.setData(b);
                mHandler.sendMessage(msg);
                setState(ProgressThread.mDone);
                mTotal = 0; // Count down
                setState(ProgressThread.mDone);
            }
        }

        // Set current state of thread (use state=ProgressThread.DONE to stop
        // thread)
        public void setState(int state) {
            mState = state;
        }

    }
}
