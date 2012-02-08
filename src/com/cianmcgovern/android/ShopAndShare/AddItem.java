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

import com.cianmcgovern.android.ShopAndShare.DisplayResults.ListCreator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddItem extends Activity {


    private EditText mEd1,mEd2;
    private Button mAdd,mCancel;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_item);

        mEd1 = (EditText)findViewById(R.id.editProduct);
        mEd1.setOnKeyListener(new OnKeyListener(){

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if((event.getAction())==KeyEvent.ACTION_DOWN && (keyCode == KeyEvent.KEYCODE_ENTER)){
                    InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(mEd1.getApplicationWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                    return true;
                }
                return false;
            }

        });

        mEd2 = (EditText)findViewById(R.id.editPrice);
        mEd2.setOnKeyListener(new OnKeyListener(){

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if((event.getAction())==KeyEvent.ACTION_DOWN && (keyCode == KeyEvent.KEYCODE_ENTER)){
                    InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(mEd2.getApplicationWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                    return true;
                }
                return false;
            }

        });

        mAdd = (Button)findViewById(R.id.saveButton);
        mAdd.setText(R.string.addButton);

        mAdd.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View arg0) {
                String product = mEd1.getText().toString();
                String price = mEd2.getText().toString();
                if(product.length() > 0 && price.length() > 0)
                    if(!price.contains("."))
                        Toast.makeText(ShopAndShare.sContext, "The price must be valid eg. 1.99", Toast.LENGTH_LONG).show();
                    else {
                        Results.getInstance().addItem(product.toUpperCase(), new Item(product.toUpperCase(),price));
                        Intent in = new Intent(ShopAndShare.sContext,ListCreator.class);
                        startActivity(in);
                        finish();
                    }
                else
                    Toast.makeText(ShopAndShare.sContext, "Product or price cannot be empty!", Toast.LENGTH_LONG).show();
            }

        });

        mCancel = (Button)findViewById(R.id.deleteButton);
        mCancel.setText(R.string.cancelButton);
        mCancel.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent in = new Intent(ShopAndShare.sContext,ListCreator.class);
                startActivity(in);
                finish();
            }

        });
    }

    @Override
    public void onBackPressed(){
        Intent in = new Intent(ShopAndShare.sContext,ListCreator.class);
        startActivity(in);
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
