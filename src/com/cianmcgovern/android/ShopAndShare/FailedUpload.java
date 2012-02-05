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
