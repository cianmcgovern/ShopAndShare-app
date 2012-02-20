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
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class EditItem extends Activity implements OnClickListener {

    private String mProduct;
    private Button mSave;
    private Button mDelete;
    private EditText mEd1, mEd2;
    private Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        mContext = this;

        super.onCreate(savedInstanceState);

        setContentView(R.layout.edit_item);

        // Product to edit is sent through intent
        mProduct = this.getIntent().getStringExtra("Product");
        Log.v(Constants.LOG_TAG, "Value recieved from intent is: " + mProduct);

        // Overriding onClick() for the save button as this Class implements
        // OnClickListener
        mSave = (Button) findViewById(R.id.saveButton);
        mSave.setOnClickListener(this);
        mSave.setText(R.string.saveButton);

        // Delete button deletes the item
        mDelete = (Button) findViewById(R.id.deleteButton);
        mDelete.setText(R.string.deleteButton);
        mDelete.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(mContext)
                        .setTitle(R.string.deleteConfirmTitle)
                        .setMessage(R.string.deleteItemConfirm)
                        .setPositiveButton(R.string.yes,
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                            int which) {
                                        Results.getInstance().getProducts()
                                                .remove(mProduct);
                                        Intent in = new Intent(
                                                ShopAndShare.sContext,
                                                ListCreator.class);
                                        startActivity(in);
                                        finish();
                                    }
                                }).setNegativeButton(R.string.no, null).show();
            }
        });

        // Editable text fields containing the current product and price
        // OnKeyListener listens for enter button and hides keyboard when
        // pressed
        mEd1 = (EditText) findViewById(R.id.editProduct);
        mEd1.setText(mProduct);
        mEd1.setOnKeyListener(new OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction()) == KeyEvent.ACTION_DOWN
                        && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(
                            mEd1.getApplicationWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                    return true;
                }
                return false;
            }

        });

        mEd2 = (EditText) findViewById(R.id.editPrice);
        String x;
        if ((x = Results.getInstance().getProducts().get(mProduct).getPrice()) != null)
            mEd2.setText(x);
        else
            Log.v(Constants.LOG_TAG, "Product not found in HashResult: "
                    + mProduct);

        mEd2.setOnKeyListener(new OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction()) == KeyEvent.ACTION_DOWN
                        && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(
                            mEd2.getApplicationWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                    return true;
                }
                return false;
            }

        });

    }

    @Override
    public void onBackPressed() {
        Intent home = new Intent(ShopAndShare.sContext, ListCreator.class);
        startActivity(home);
        finish();
    }

    @Override
    public void onClick(View v) {

        final String productText = mEd1.getText().toString().toUpperCase();
        final String priceText = mEd2.getText().toString();

        // If the product name has not been changed
        if (mProduct.compareTo(productText) == 0) {
            // Update product with price
            Results.getInstance().getProducts().get(productText)
                    .setPrice(priceText);
        }

        // If the product name has been changed
        else {
            Log.v(Constants.LOG_TAG, "Changing product name from: " + mProduct
                    + " to: " + productText);
            // Change the product name in the map
            Results.getInstance().changeKey(mProduct, productText);

            // Check to see if the product id was changed correctly
            if (!Results.getInstance().getProducts().containsKey(productText))
                Log.e(Constants.LOG_TAG, productText
                        + " does not exist in HashResults");
            else {
                Results.getInstance().getProducts().get(productText)
                        .setPrice(priceText);
            }
        }

        // Restart the DisplayResults activity so that the new data is displayed
        Intent home = new Intent(ShopAndShare.sContext, ListCreator.class);
        startActivity(home);
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
        case R.id.help:
            new HelpDialog(this, this.getText(R.string.editItemHelpMessage)
                    .toString()).show();
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

}
