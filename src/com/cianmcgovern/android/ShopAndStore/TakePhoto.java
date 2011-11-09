package com.cianmcgovern.android.ShopAndStore;

import java.io.IOException;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
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
	
	public void onCreate(Bundle savedInstance){
		super.onCreate(savedInstance);
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
	}
	
	public void onClick(View v){
		mCamera.takePicture(null, mPictureCallback, mPictureCallback);
	}

	public void surfaceCreated(SurfaceHolder holder){
		mCamera = Camera.open();
	}

	public void surfaceChanged(SurfaceHolder holder,int format, int w, int h){

		if(mPreviewRunning){
			mCamera.stopPreview();
		}

		Camera.Parameters p = mCamera.getParameters();
		p.setPreviewSize(w, h);
		p.setRotation(90);
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

	public void surfaceDestroyed(SurfaceHolder holder){
		mCamera.stopPreview();

		mPreviewRunning=false;

		mCamera.release();
	}

	Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
		public void onPictureTaken(byte[] imageData, Camera c) {
			if (imageData!=null){
				Log.d("ShopAndStore","Data received from camera");
				Bitmap bmp = BitmapFactory.decodeByteArray(imageData,0,imageData.length);
				new SaveBitmap(bmp,getFilesDir().toString());
				finish();
			}
			else{
				Log.e("ShopAndStore","Data not received from camera");
			}
		}

	};
}