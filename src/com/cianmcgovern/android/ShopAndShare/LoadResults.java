package com.cianmcgovern.android.ShopAndShare;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OptionalDataException;
import java.util.ArrayList;

import com.cianmcgovern.android.ShopAndShare.DisplayResults.ListCreator;

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
 * LoadResults displays all of the saved results currently on the device
 * The user can load a save by clicking on it which redirects them to the Display results activity
 * 
 * @author Cian Mc Govern <cian@cianmcgovern.com>
 *
 */
public class LoadResults extends ListActivity{
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		
		super.onCreate(savedInstanceState);
	
		// Fetch the list of files from the saves directory and display them in the ListView
		ArrayList<String> saves = new ArrayList<String>();
		
		File[] saveFiles = new File(Constants.saveDir).listFiles();
		
		for(int i=0; i<saveFiles.length;i++)
			saves.add(saveFiles[i].getName());
		
		ArrayAdapter<String> ad = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,saves);
		
		setListAdapter(ad);
		
	}
	
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id){
		
		// Load the object from the file selected by the user
		String x = (String)getListAdapter().getItem(position);
		try {
			loadObject(Constants.saveDir+"/"+x);
		} catch (OptionalDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Loads a serialized file containing a HashResults object and updates Results with that object and then calls DisplayResults
	 * 
	 * @param path
	 * @throws OptionalDataException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	private void loadObject(String path) throws OptionalDataException, ClassNotFoundException, IOException{
		File file = new File(path);
		FileInputStream f;
		ObjectInputStream fo;
		f = new FileInputStream(file);
		fo = new ObjectInputStream(f);
		Results.getInstance().setHashResults((HashResults<String,Item>)fo.readObject());
		Intent i = new Intent(this,ListCreator.class);
		startActivity(i);
		finish();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater inf = getMenuInflater();
		inf.inflate(R.layout.load_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem it){
		switch(it.getItemId()){
		// Edit menu redirects to LoadResultsEdit which allows user to delete saves
		case R.id.edit:
			Intent i = new Intent(this,LoadResultsEdit.class);
			startActivity(i);
			finish();
			return true;
		case R.id.exit:
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(it);
		}
	}
}