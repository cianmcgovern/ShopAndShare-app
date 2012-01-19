package com.cianmcgovern.android.ShopAndShare;

import com.cianmcgovern.android.ShopAndShare.R;

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
 * Activity that gets displayed while the native code is executing
 * Uses a separate thread to execute the native code and displays a progress bar in this (UI) thread
 * 
 * @author Cian Mc Govern
 *
 */
public class LoadingPage extends Activity{

	ProgressThread progThread;
	ProgressDialog progDialog;
	private Context context;
	private byte[] ImageData;

	@Override
	public void onCreate(Bundle savedInstance){
		
		context = this;
		setContentView(R.layout.loading);
		super.onCreate(savedInstance);
		showDialog(0);
	}

	private native void callnativecode(String dir);
	private native String[] getProducts();

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
		Log.d("ShopAndStore","Calling native code");
		callnativecode(imageLocation);
		Results.getInstance().deleteProducts();
		Results.getInstance().setProducts(getProducts());
	}

	// Method to create a spinner dialog
	@Override
	protected Dialog onCreateDialog(int id) {
            // Spinner
			progDialog = new ProgressDialog(this);
			progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progDialog.setMessage("Loading...");
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
				Intent disp = new Intent(context,DisplayResults.class);
				startActivity(disp);
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
				//new SaveBitmap(Constants.ImageData,Constants.filesDir);
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
