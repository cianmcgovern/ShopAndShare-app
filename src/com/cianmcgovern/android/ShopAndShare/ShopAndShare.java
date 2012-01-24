package com.cianmcgovern.android.ShopAndShare;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.cianmcgovern.android.ShopAndShare.R;
import com.cianmcgovern.android.ShopAndShare.Comparison.LoadFile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ShopAndShare extends Activity
{
	private Button callPhoto,callLoad;
	private Context context;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		context=this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		Constants.filesDir=getFilesDir().toString();
		Constants.saveDir=Constants.filesDir.concat("/saves");
		
		Constants.wordList = LoadFile.fileToList(Constants.filesDir.concat("/wordlist"));
		
		try {
			loadTrainingData();
			//loadTestPhoto();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Create the saves directory if it doesn't exist
		if(!new File(Constants.saveDir).isDirectory())
			new File(Constants.saveDir).mkdir();
		
		callPhoto=(Button)findViewById(R.id.callPhotoButton);
		callPhoto.setOnClickListener(new CallPhotoButtonListener(this));
		callLoad=(Button)findViewById(R.id.callLoadButton);
		callLoad.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent x = new Intent(context,LoadResults.class);
				startActivity(x);
			}
			
		});
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
		
		is = getResources().openRawResource(R.raw.wordlist);
		os = new FileOutputStream(new File(Constants.filesDir.concat("/wordlist")));
		bis = new BufferedInputStream(is);
		bos = new BufferedOutputStream(os);
		
		buf = new byte[1024];
		n =0;
        o =0;
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
			System.runFinalizersOnExit(true);
			System.exit(0);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}	
}
