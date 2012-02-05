/*******************************************************************************
 * Copyright (c) 2012 Cian Mc Govern.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     Cian Mc Govern - initial API and implementation
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
