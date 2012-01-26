package com.cianmcgovern.android.ShopAndShare;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.cianmcgovern.android.ShopAndShare.R;
import com.cianmcgovern.android.ShopAndShare.Camera.TakePhoto;
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
    public static Context sContext;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        // Static application wide context
        sContext=this.getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        try {
            prepareFiles();
        } catch (IOException e) {
            Log.e("ShopAndShare","Unable to execute prepareFiles()");
            e.printStackTrace();
        }

        callPhoto=(Button)findViewById(R.id.callPhotoButton);
        callPhoto.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent photo = new Intent(ShopAndShare.sContext,TakePhoto.class);
                startActivity(photo);
            }
            
        });
        callLoad=(Button)findViewById(R.id.callLoadButton);
        callLoad.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent x = new Intent(sContext,LoadResults.class);
                startActivity(x);
            }

        });
    }

    /**
     * Deletes any files that may conflict and creates the necessary files
     * 
     */
    private void prepareFiles() throws IOException {

        Constants.filesDir=getFilesDir().toString();

        Constants.saveDir=Constants.filesDir.concat("/saves");
        File saveDir = new File(Constants.saveDir);
        // Creates the saves directory if it doesn't exist
        if(!saveDir.exists()) {
            saveDir.mkdir();
            Log.w("ShopAndShare","Saves directory doesn't exists, creating!");
        }
        
        InputStream is;
        OutputStream os;
        BufferedInputStream bis;
        BufferedOutputStream bos;
        byte[] buf = new byte[1024];
        int n = 0;
        int o = 0;
        
        // Delete the wordlist if it exists then create and save it
        File wordList = new File(Constants.filesDir.concat("/wordlist"));
        if(wordList.exists()) {
            wordList.delete();
            Log.w("ShopAndShare","Wordlist exists, deleting!");
        }
        is = getResources().openRawResource(R.raw.wordlist);
        os = new FileOutputStream(wordList);
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
        Constants.wordList = LoadFile.fileToList(Constants.filesDir.concat("/wordlist"));

        // Delete the tesseract training data if it exists then create it
        File tessDir =  new File(Constants.filesDir+"/tessdata");
        if(tessDir.exists()) {
            tessDir.delete();
            Log.w("ShopAndShare","Tesseract data directory exists, deleting!");
        }
        tessDir.mkdir();
        File tessData = new File(Constants.filesDir+"/tessdata/eng.traineddata");
        is = getResources().openRawResource(R.raw.eng);
        os = new FileOutputStream(tessData);
        bis = new BufferedInputStream(is);
        bos = new BufferedOutputStream(os);
        buf = new byte[1024];
        n = 0;
        o = 0;
        while((n=bis.read(buf,o,buf.length))>0){
            bos.write(buf,0,n);
        }
        bis.close();
        bos.close();
        
        // Delete any image files that may exist from previous uses
        File image = new File(Constants.filesDir+"/image.jpg");
        if(image.exists()) {
            image.delete();
            Log.w("ShopAndShare","Image file found, deleting!");
        }
        image = new File(Constants.filesDir+"/bgimage.tiff");
        if(image.exists()) {
            image.delete();
            Log.w("ShopAndShare","TIFF image file found, deleting!");
        }
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
