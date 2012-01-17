package com.cianmcgovern.android.ShopAndShare;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * Implements the OnClickListener for any button that returns to the main activity
 * 
 * @author Cian Mc Govern
 *
 */
public class CancelButtonListener implements OnClickListener {
	
	private Context context;
	
	/**
	 * Constructor to save context
	 * 
	 * @param context Calling activities context
	 */
	public CancelButtonListener(Context context){
		this.context=context;
	}

	@Override
	public void onClick(View v) {
		Intent parent = new Intent(context,ShopAndShare.class);
		context.startActivity(parent);
	}
}
