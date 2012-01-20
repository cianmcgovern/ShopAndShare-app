package com.cianmcgovern.android.ShopAndShare;

import java.io.IOException;

import com.cianmcgovern.android.ShopAndShare.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class TakePhoto extends Activity implements SurfaceHolder.Callback,OnClickListener {

	private SurfaceView mSurfaceView;
	private Camera mCamera;
	private boolean mPreviewRunning;
	private Button takePhotoButton;
	private int counter=0;
	Camera.Parameters p;
	private Context con;
	
	public void onCreate(Bundle savedInstance){
		super.onCreate(savedInstance);
		// Save context for passing to LoadingPage
		con=this;
		// Setup window
		getWindow().setFormat(PixelFormat.TRANSLUCENT);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.camera_surface);

		mSurfaceView = (SurfaceView) findViewById(R.id.surface_camera);
		SurfaceHolder mSurfaceHolder = mSurfaceView.getHolder();
		mSurfaceHolder.addCallback(this);
		mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		this.takePhotoButton = (Button)this.findViewById(R.id.photoButton);
		this.takePhotoButton.setOnClickListener(this);
		Button cancel = (Button)findViewById(R.id.cancelPhotoButton);
		cancel.setOnClickListener(new CancelButtonListener(this));
	}
	
	public void onClick(View v){
		mCamera.autoFocus(cb);
	}

	public void surfaceCreated(SurfaceHolder holder){
		mCamera = Camera.open();
		mCamera.setDisplayOrientation(90);
	}

	public void surfaceChanged(SurfaceHolder holder,int format, int w, int h){

		if(mPreviewRunning){
			mCamera.stopPreview();
		}
		
		p = mCamera.getParameters();
		p.setPreviewSize(w, h);
		p.setFocusMode(Camera.Parameters.FOCUS_MODE_MACRO);
		p.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
		p.setWhiteBalance(Camera.Parameters.WHITE_BALANCE_AUTO);
		p.setJpegQuality(100);
		mCamera.setParameters(p);

		try {
			mCamera.setPreviewDisplay(holder);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e("ShopAndStore","Camera Preview Display not set");
		}
		mCamera.startPreview();
		mPreviewRunning = true;
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder){
		mCamera.stopPreview();
		mPreviewRunning=false;
		mCamera.release();
	}

	Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
		public void onPictureTaken(byte[] imageData, Camera c) {
			
			// If the data is received from the camera, call the subsequent activity LoadingPage
			if (imageData!=null){
				Log.d("ShopAndStore","Data received from camera");
				Constants.ImageData=imageData;
				Intent displayResults = new Intent(con,LoadingPage.class);
				startActivity(displayResults);
				finish();
			}
			else{
				Log.e("ShopAndStore","Data not received from camera");
			}
		}

	};
	
	Camera.AutoFocusCallback cb = new Camera.AutoFocusCallback() {
		
		@Override
		public void onAutoFocus(boolean success, Camera camera) {
			// TODO Auto-generated method stub
			if(success || counter > 0)
				mCamera.takePicture(null, mPictureCallback, mPictureCallback);
			else{
				Log.e("ShopAndStore","autoFocus did not complete successfully");
				counter++;
			}
		}
	};
	
	@Override
	public void onBackPressed(){
		Intent home = new Intent(this,ShopAndShare.class);
		startActivity(home);
		finish();
	}
	
	// Create options menu
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater inf = getMenuInflater();
		inf.inflate(R.layout.default_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
		case R.id.exit:
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}