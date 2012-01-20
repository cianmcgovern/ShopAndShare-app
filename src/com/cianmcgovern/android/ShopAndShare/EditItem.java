package com.cianmcgovern.android.ShopAndShare;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class EditItem extends Activity implements OnClickListener{
	
	private String product;
	private Button save;
	private Button cancel;
	private CheckBox upload;
	private EditText ed1,ed2;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_item);
		
		// Product to edit is sent through intent
		product = this.getIntent().getStringExtra("Product");
		Log.v("ShopAndShare","Value recieved from intent is: "+product);
		
		// Overriding onClick() for the save button as this Class implements OnClickListener
		save = (Button)findViewById(R.id.saveButton);
		save.setOnClickListener(this);
		
		// Cancel button finishes the activity
		cancel = (Button)findViewById(R.id.cancelEditButton);
		cancel.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		// Setting up the checkbox for user decision on whether to share item or not
		// Defaults to checked ie. share item
		upload=(CheckBox)findViewById(R.id.checkBox1);
		upload.setChecked(Results.getInstance().getProducts().get(product).getUpload());
		
		// Editable text fields containing the current product and price
		// OnKeyListener listens for enter button and hides keyboard when pressed
		ed1 = (EditText)findViewById(R.id.editProduct);
		ed1.setText(product);
		ed1.setOnKeyListener(new OnKeyListener(){

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if((event.getAction())==KeyEvent.ACTION_DOWN && (keyCode == KeyEvent.KEYCODE_ENTER)){
					InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
	                in.hideSoftInputFromWindow(ed1.getApplicationWindowToken(),
	                		InputMethodManager.HIDE_NOT_ALWAYS);
	                return true;
				}
				return false;
			}
			
		});
		
		ed2 = (EditText)findViewById(R.id.editPrice);
		String x;
		if((x = Results.getInstance().getProducts().get(product).getPrice())!=null)
			ed2.setText(x);
		else
			Log.v("ShopAndShare","Product not found in HashResult: "+product);
		
		ed2.setOnKeyListener(new OnKeyListener(){
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if((event.getAction())==KeyEvent.ACTION_DOWN && (keyCode == KeyEvent.KEYCODE_ENTER)){
					InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
	                in.hideSoftInputFromWindow(ed2.getApplicationWindowToken(),
	                		InputMethodManager.HIDE_NOT_ALWAYS);
	                return true;
				}
				return false;
			}
			
		});
		
		Log.v("ShopAndShare","Price value has been set to: "+Results.getInstance().getProducts().get(product));
		
		
	}
	
	@Override
	public void onBackPressed(){
		Intent home = new Intent(this,DisplayResults.class);
		startActivity(home);
	}

	@Override
	public void onClick(View v) {
		
		final String productText = ed1.getText().toString();
		final String priceText = ed2.getText().toString();
		
		// If the product name has not been changed
		if(product.compareTo(productText)==0){
			// Update product with price
			Results.getInstance().getProducts().get(productText).setPrice(priceText);
			// Update upload boolean
			if(!upload.isChecked())
				Results.getInstance().getProducts().get(productText).setUpload(false);
			else
				Results.getInstance().getProducts().get(productText).setUpload(true);
		}
		
		// If the product name has been changed
		else{
			Log.v("ShopAndShare","Changing product name from: "+product+" to: "+productText);
			// Change the product name in the map
			Results.getInstance().changeKey(product, productText);
			
			// Check to see if the product id was changed correctly
			if(!Results.getInstance().getProducts().containsKey(productText))
				Log.e("ShopAndShare", productText+" does not exist in HashResults");
			else{
				Results.getInstance().getProducts().get(productText).setPrice(priceText);
				// Update upload boolean
				if(!upload.isChecked())
					Results.getInstance().getProducts().get(productText).setUpload(false);
				else
					Results.getInstance().getProducts().get(productText).setUpload(true);
			}
		}
		
		// Restart the DisplayResults activity so that the new data is displayed
		Intent home = new Intent(this,DisplayResults.class);
		startActivity(home);
	}

}