package com.cianmcgovern.android.ShopAndShare;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * Implements the OnClickListener and starts the TakePhoto activity when the button is pressed
 * 
 * @author Cian Mc Govern
 *
 */
public class CallPhotoButtonListener implements OnClickListener{
	
	private Context context;
	
	public CallPhotoButtonListener(Context context){
		this.context=context;
	}

	@Override
	public void onClick(View v){
		Intent photo = new Intent(context,TakePhoto.class);
		context.startActivity(photo);
	}
}
