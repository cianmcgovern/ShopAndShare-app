package com.cianmcgovern.android.ShopAndShare;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import com.cianmcgovern.android.ShopAndShare.R;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Activity to display the results from the native code execution
 * Displays two arrays with corresponding elements in two ListViews as specified in display_results.xml
 * 
 * @author Cian Mc Govern
 *
 */
public class DisplayResults extends ListActivity{

	@SuppressWarnings("rawtypes")
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
		finish();
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id){
		String product = (String) getListAdapter().getItem(position);
		Intent in = new Intent(this,EditItem.class);
		in.putExtra("Product", product);
		startActivity(in);
		finish();
	}

	// Create options menu
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater inf = getMenuInflater();
		inf.inflate(R.layout.results_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
		case R.id.exit:
			finish();
			return true;
		case R.id.save:
			try {
				Results.getInstance().saveCurrentResults();
				finish();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
