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
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * Activity that gets displayed while the native code is executing
 * Uses a separate thread to execute the native code and displays a progress bar in this (UI) thread
 * 
 * @author Cian Mc Govern
 *
 */
public class LoadingPage extends Activity{

	ProgressThread progThread;
	ProgressDialog progDialog;
	private byte[] ImageData;

	@Override
	public void onCreate(Bundle savedInstance){
		
		super.onCreate(savedInstance);
		showDialog(0);
	}

	private native void callnativecode(String dir);

	static{
		System.loadLibrary("lept");
		System.loadLibrary("tess");
		System.loadLibrary("ShopAndShare");
	}

	/**
	 * Calls the native code methods through the JNI
	 * 
	 * @param imageLocation Path to the folder containing the image.jpg ie. the file from SaveBitmap
	 */
	private void AnalyseImage(String imageLocation){
	    
	    // Delete the results file before we call the native code
	    if(new File(Constants.filesDir + "/results").exists())
	        new File(Constants.filesDir).delete();
	        
		Log.d("ShopAndStore","Calling native code");
		callnativecode(imageLocation);
		String[] results = getProducts();
		Results.getInstance().setProducts(results,results.length);
	}
	
	private String[] getProducts(){
		
		ArrayList<String> as = new ArrayList<String>();
		
		try{
			FileInputStream fin = new FileInputStream(Constants.filesDir+"/results");
			DataInputStream din = new DataInputStream(fin);
			BufferedReader br = new BufferedReader(new InputStreamReader(din));

			String line;

			while((line = br.readLine()) != null){
				as.add(line);
			}
			br.close();
			din.close();
			fin.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		String[] collectedResults = new String[as.size()];
		
		for(int i=0;i<as.size();i++){
			collectedResults[i]=as.get(i);
		}
		
		new File(Constants.filesDir+"/results").delete();
		
		return collectedResults;
	}

	// Method to create a spinner dialog
	@Override
	protected Dialog onCreateDialog(int id) {
            // Spinner
			progDialog = new ProgressDialog(this);
			progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progDialog.setCancelable(false);
			progDialog.setMessage("Analysing...");
			progThread = new ProgressThread(handler);
			progThread.start();
			return progDialog;
	}

	// Handler on the main (UI) thread that will receive messages from the 
	// second thread and update the progress.

	final Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			// Get the current value of the variable total from the message data
			// and update the progress bar.
			int total = msg.getData().getInt("total");
			progDialog.setProgress(total);
			if (total <= 0){
				dismissDialog(0);
				progThread.setState(ProgressThread.DONE);
				Intent disp = new Intent(ShopAndShare.sContext,ListCreator.class);
				startActivity(disp);
				finish();
			}
		}
	};

	// Inner class that performs progress calculations on a second thread.  Implement
	// the thread by subclassing Thread and overriding its run() method.  Also provide
	// a setState(state) method to stop the thread gracefully.

	private class ProgressThread extends Thread {	

		// Class constants defining state of the thread
		final static int DONE = 0;
		final static int RUNNING = 1;

		Handler mHandler;
		int mState;
		int total;

		ProgressThread(Handler h) {
			mHandler = h;
		}

		@Override
		public void run() {
			mState = RUNNING;   
			total = 1;
			while (mState == RUNNING) {
				new SaveBitmap(Constants.ImageData,Constants.filesDir);
				// Calls native code and passes path to image excluding the file name which is expected to be image.jpg
				AnalyseImage(Constants.filesDir);
				Message msg = mHandler.obtainMessage();
				Bundle b = new Bundle();
				total=0;
				b.putInt("total", total);
				msg.setData(b);
				mHandler.sendMessage(msg);
				setState(ProgressThread.DONE);
				total=0;    // Count down
				setState(ProgressThread.DONE);
			}
		}

		// Set current state of thread (use state=ProgressThread.DONE to stop thread)
		public void setState(int state) {
			mState = state;
		}
		
	}
}
