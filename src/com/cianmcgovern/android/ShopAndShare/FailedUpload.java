package com.cianmcgovern.android.ShopAndShare;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * Displays a message to the user allowing them to retry their upload.
 * This activity should only be called if the upload failed
 * 
 * @author Cian Mc Govern <cian@cianmcgovern.com>
 *
 */
public class FailedUpload extends Activity {
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.failed_upload);
        
        Button tryagain = (Button) findViewById(R.id.tryAgain);
        tryagain.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                finish();
            }
            
        });
        
        Button cancel = (Button) findViewById(R.id.cancelFail);
        cancel.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                finish();
            }
            
        });
    }

}
