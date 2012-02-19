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
package com.cianmcgovern.android.ShopAndShare.Camera;

import java.io.IOException;

import com.cianmcgovern.android.ShopAndShare.CheckFeatures;
import com.cianmcgovern.android.ShopAndShare.Constants;
import com.cianmcgovern.android.ShopAndShare.LoadingPage;
import com.cianmcgovern.android.ShopAndShare.R;
import com.cianmcgovern.android.ShopAndShare.ShopAndShare;

import android.app.Activity;
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
public class TakePhoto extends Activity implements SurfaceHolder.Callback,
        OnClickListener {

    private SurfaceView mSurfaceView;
    private Camera mCamera;
    private boolean mPreviewRunning;
    private Button mTakePhotoButton;
    private Camera.Parameters mParameters;

    // These options set the default parameters for the Camera
    // They can be changed by the user in TakePhotoOptionsActivity
    public static String sFlashMode = Camera.Parameters.FLASH_MODE_ON;
    public static String sFocusMode = Camera.Parameters.FOCUS_MODE_MACRO;
    public static String sWhiteBalance = Camera.Parameters.WHITE_BALANCE_AUTO;
    public static String sSceneMode = Camera.Parameters.SCENE_MODE_STEADYPHOTO;

    @Override
    public void onCreate(Bundle savedInstance) {

        super.onCreate(savedInstance);
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
        this.mTakePhotoButton = (Button) this.findViewById(R.id.photoButton);
        this.mTakePhotoButton.setOnClickListener(this);

        Button cancel = (Button) findViewById(R.id.photoOptionsButton);
        cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ShopAndShare.sContext,
                        TakePhotoOptionsActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    @Override
    public void onClick(View v) {
        if (sFocusMode.equalsIgnoreCase(Camera.Parameters.FOCUS_MODE_FIXED)) {
            mCamera.takePicture(null, mPictureCallback, mPictureCallback);
            this.mTakePhotoButton.setEnabled(false);
        }
        else {
            this.mTakePhotoButton.setEnabled(false);
            mCamera.autoFocus(cb);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mCamera = Camera.open();
        mCamera.setDisplayOrientation(90);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {

        if (mPreviewRunning) {
            mCamera.stopPreview();
        }

        // Set up the camera with the parameters from the method call
        mParameters = mCamera.getParameters();
        mParameters.setPreviewSize(w, h);
        if (CheckFeatures.haveAutoFocus())
            mParameters.setFocusMode(sFocusMode);
        if (CheckFeatures.haveFlash()) mParameters.setFlashMode(sFlashMode);
        mParameters.setWhiteBalance(sWhiteBalance);
        mParameters.setJpegQuality(100);
        mCamera.setParameters(mParameters);

        try {
            mCamera.setPreviewDisplay(holder);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(Constants.LOG_TAG, "Camera Preview Display not set");
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
        @Override
        public void onPictureTaken(byte[] imageData, Camera c) {

            // If the data is received from the camera, call the subsequent
            // activity LoadingPage
            if (imageData != null) {
                Log.d(Constants.LOG_TAG, "Data received from camera");
                Constants.IMAGE_DATA = imageData;
                Intent displayResults = new Intent(ShopAndShare.sContext,
                        LoadingPage.class);
                startActivity(displayResults);
                finish();
            }
            else
                Log.e(Constants.LOG_TAG, "Data not received from camera");
        }

    };

    Camera.AutoFocusCallback cb = new Camera.AutoFocusCallback() {

        @Override
        public void onAutoFocus(boolean success, Camera camera) {
            if (success)
                mCamera.takePicture(null, mPictureCallback, mPictureCallback);
            else
                mTakePhotoButton.setEnabled(true);
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
        switch (item.getItemId()) {
        case R.id.exit:
            finish();
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
}
