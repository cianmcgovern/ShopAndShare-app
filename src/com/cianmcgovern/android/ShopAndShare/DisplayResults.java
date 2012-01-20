package com.cianmcgovern.android.ShopAndShare;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.cianmcgovern.android.ShopAndShare.R;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Activity to display the results from the native code execution
 * Displays two arrays with corresponding elements in two ListViews as specified in display_results.xml
 * 
 * @author Cian Mc Govern
 *
 */
public class DisplayResults extends ListActivity{
	
	private Context context;
	
	public void onCreate(Bundle savedInstance){
		
		super.onCreate(savedInstance);
			
		ArrayList<String> products = new ArrayList<String>();
		
		Iterator i = Results.getInstance().getProducts().entrySet().iterator();
		
		while(i.hasNext()){
			Map.Entry x = (Map.Entry)i.next();
			products.add((String) x.getKey());
		}
		
		ArrayAdapter<String> ad = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,products);
		
		setListAdapter(ad);
		
        // Temporary
		//new DeleteImages(Constants.filesDir);
		
	}
	
	@Override
	public void onBackPressed(){
		Intent home = new Intent(this,ShopAndShare.class);
		startActivity(home);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id){
		String product = (String) getListAdapter().getItem(position);
		Intent in = new Intent(this,EditItem.class);
		in.putExtra("Product", product);
		startActivity(in);
	}
}
