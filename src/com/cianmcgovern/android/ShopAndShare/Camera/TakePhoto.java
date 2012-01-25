package com.cianmcgovern.android.ShopAndShare.Camera;

import java.io.IOException;

import com.cianmcgovern.android.ShopAndShare.Constants;
import com.cianmcgovern.android.ShopAndShare.LoadingPage;
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

/**
 * Displays the camera output to the screen and allows the user to take a photo
 * 
 * @author Cian Mc Govern <cian@cianmcgovern.com>
 *
 */
public class TakePhoto extends Activity implements SurfaceHolder.Callback,OnClickListener {

    private SurfaceView mSurfaceView;
    private Camera mCamera;
    private boolean mPreviewRunning;
    private Button takePhotoButton;
    private Camera.Parameters mParameters;
    private Context mContext;

    // These options set the default parameters for the Camera
    // They can be changed by the user in TakePhotoOptionsActivity
    public static String sFlashMode = Camera.Parameters.FLASH_MODE_AUTO;
    public static String sFocusMode = Camera.Parameters.FOCUS_MODE_MACRO;
    public static String sWhiteBalance = Camera.Parameters.WHITE_BALANCE_AUTO;
    public static String sSceneMode = Camera.Parameters.SCENE_MODE_STEADYPHOTO;

    public void onCreate(Bundle savedInstance) {
        
        super.onCreate(savedInstance);
        // Save context for passing to LoadingPage
        mContext = this;
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

        Button cancel = (Button)findViewById(R.id.photoOptionsButton);
        cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext,TakePhotoOptionsActivity.class);
                startActivity(i);
            }
        });

    }

    public void onClick(View v) {
        mCamera.autoFocus(cb);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        mCamera = Camera.open();
        mCamera.setDisplayOrientation(90);
    }

    public void surfaceChanged(SurfaceHolder holder,int format, int w, int h) {

        if(mPreviewRunning) {
            mCamera.stopPreview();
        }

        // Set up the camera with the parameters from the method call
        mParameters = mCamera.getParameters();
        mParameters.setPreviewSize(w, h);
        mParameters.setFocusMode(sFocusMode);
        mParameters.setFlashMode(sFlashMode);
        mParameters.setWhiteBalance(sWhiteBalance);
        mParameters.setJpegQuality(100);
        mCamera.setParameters(mParameters);

        try {
            mCamera.setPreviewDisplay(holder);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("ShopAndShare","Camera Preview Display not set");
        }
        mCamera.startPreview();
        mPreviewRunning = true;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mCamera.stopPreview();
        mPreviewRunning = false;
        mCamera.release();
    }

    Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
        public void onPictureTaken(byte[] imageData, Camera c) {

            // If the data is received from the camera, call the subsequent activity LoadingPage
            if (imageData!=null) {
                Log.d("ShopAndShare","Data received from camera");
                Constants.ImageData=imageData;
                Intent displayResults = new Intent(mContext,LoadingPage.class);
                startActivity(displayResults);
                finish();
            }
            else
                Log.e("ShopAndShare","Data not received from camera");
        }

    };

    Camera.AutoFocusCallback cb = new Camera.AutoFocusCallback() {

        @Override
        public void onAutoFocus(boolean success, Camera camera) {
            mCamera.takePicture(null, mPictureCallback, mPictureCallback);
        }
    };

    @Override
    public void onBackPressed() {
        finish();
    }

    // Create options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inf = getMenuInflater();
        inf.inflate(R.layout.default_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
        case R.id.exit:
            finish();
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
}