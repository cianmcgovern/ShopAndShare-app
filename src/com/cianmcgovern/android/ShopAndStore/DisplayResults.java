package com.cianmcgovern.android.ShopAndStore;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

/**
 * Activity to display the results from the native code execution
 * Displays two arrays with corresponding elements in two ListViews as specified in display_results.xml
 * 
 * @author 
 *
 */
public class DisplayResults extends Activity{
	
	private String[] products;
	@Override
	public void onCreate(Bundle savedInstance){
		
		super.onCreate(savedInstance);
		
		products = Results.getInstance().getProducts();
		//double[] oldPrices = Results.getInstance().getPrices();
		//String[] prices = new String[oldPrices.length];
//		for(int i=0;i<oldPrices.length;i++){
//			prices[i]=Double.toString(oldPrices[i]);
//		}
		

		setContentView(R.layout.display_results);
		ListView lv;
		lv = (ListView)findViewById(R.id.ListViewProducts);
		lv.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,products));
		
		Button cancel = (Button)findViewById(R.id.cancelResultsButton);
		cancel.setOnClickListener(new CancelButtonListener(this));
		
//		ListView lv2;
//		lv2 = (ListView)findViewById(R.id.ListViewPrices);
//		lv2.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,prices));
		
		new DeleteImages(Constants.filesDir);
		
	}
	
	@Override
	public void onBackPressed(){
		Intent home = new Intent(this,ShopAndStore.class);
		startActivity(home);
	}
}
