package com.cianmcgovern.android.ShopAndShare;

import java.io.File;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

/**
 * LoadResultsEdit activity extends LoadResults and overrides the onListItemClick to allow a user to delete a save
 * 
 * @author Cian Mc Govern <cian@cianmcgovern.com>
 *
 */
public class LoadResultsEdit extends LoadResults{
	
	private String filename;
	private Context con;
	
	public void onListItemClick(ListView l, View v, int position, long id){
		
		con = this;
		
		filename = (String)getListAdapter().getItem(position);
		
		// AlertDialog to confirm deletion of save by user
		new AlertDialog.Builder(this)
		.setTitle(R.string.deleteConfirmTitle)
		.setMessage(R.string.deleteConfirm)
		.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				File f = new File(Constants.saveDir+"/"+filename);
				if(!f.delete())
					Log.e("ShopAndShare",filename+" save file was not deleted");
				else
					Log.v("ShopAndShare",filename+" save file was deleted");
				Intent i = new Intent(con,LoadResultsEdit.class);
				startActivity(i);
				finish();
			}
			
		})
		.setNegativeButton(R.string.no, null)
		.show();
	}
}
