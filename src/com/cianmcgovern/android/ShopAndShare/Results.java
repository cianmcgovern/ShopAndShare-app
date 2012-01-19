package com.cianmcgovern.android.ShopAndShare;

import android.util.Log;

/**
 * Singleton object that stores the results from image analysing
 * 
 * @author Cian Mc Govern
 *
 */
public class Results {
	
	private String[] products;
	
	private static Results _instance;
	
	public static Results getInstance(){
		if(_instance==null)
			_instance = new Results();
		return _instance;
	}
	
	private Results(){
	}
	
	public void deleteProducts(){
		this.products=null;
	}
	
	public void setProducts(String[] inProducts){
		this.products=inProducts;
		Log.v("ShopAndStore", "Products array has been set to: ");
		for(int i=0;i<this.products.length;i++){
			Log.v("ShopAndStore","Product: "+this.products[i]);
		}
	}
	
	
	public String[] getProducts(){
		String[] tmpproducts=this.products.clone();
		this.products=null;
		return tmpproducts;
	}
}


