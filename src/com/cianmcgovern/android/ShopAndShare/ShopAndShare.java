package com.cianmcgovern.android.ShopAndShare;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.cianmcgovern.android.ShopAndShare.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

public class ShopAndShare extends Activity
{
	private Button callPhoto;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Constants.filesDir=getFilesDir().toString();
		try {
			loadTrainingData();
			loadTestPhoto();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.callPhoto=(Button)findViewById(R.id.callPhotoButton);
		callPhoto.setOnClickListener(new CallPhotoButtonListener(this));
	}

	private void loadTrainingData() throws IOException{
		InputStream is = getResources().openRawResource(R.raw.eng);
		new File(Constants.filesDir+"/tessdata").mkdirs();
		OutputStream os = new FileOutputStream(new File(Constants.filesDir+"/tessdata/eng.traineddata"));
		BufferedInputStream bis = new BufferedInputStream(is);
		BufferedOutputStream bos = new BufferedOutputStream(os);
		byte[] buf = new byte[1024];

		int n =0;
		int o =0;
		while((n=bis.read(buf,o,buf.length))>0){
			bos.write(buf,0,n);
		}
		bis.close();
		bos.close();
	}
	
	private void loadTestPhoto() throws IOException{
		InputStream is = getResources().openRawResource(R.raw.test);
		OutputStream out = new FileOutputStream(new File(Constants.filesDir+"/image.jpg"));
		BufferedInputStream bis = new BufferedInputStream(is);
		BufferedOutputStream bos = new BufferedOutputStream(out);
		byte[] buf = new byte[1024];
		
		int n =0;
		int o =0;
		while((n=bis.read(buf,o,buf.length))>0){
			bos.write(buf,0,n);
		}
		bis.close();
		bos.close();		
	}
	@Override
	public void onResume(){
		super.onResume();
		Log.d("ShopAndStore", "Resumed");
	}
	
}
