package com.cianmcgovern.android.ShopAndStore;

import android.util.Log;

/**
 * Singleton object that stores the two arrays containing the items and products
 * 
 * @author Cian Mc Govern
 *
 */
public class Results {
	
	private String[] products;
	private double[] prices;
	
	private static Results _instance;
	
	public static Results getInstance(){
		if(_instance==null)
			_instance = new Results();
		return _instance;
	}
	
	private Results(){
	}
	
	public void setProducts(String[] inProducts){
		this.products=inProducts;
		Log.v("ShopAndStore", "Products array has been set to: ");
		for(int i=0;i<this.products.length;i++){
			Log.v("ShopAndStore","Product: "+this.products[i]);
		}
	}
	
	public void setPrices(String[] inPrices){
		int length = inPrices.length;
		double[] tmpPrices=new double[length];
		Log.v("ShopAndStore", "Prices array has been set to: ");
		for(int i=0;i<inPrices.length;i++){
			tmpPrices[i]=(Double.parseDouble(inPrices[i]))/100;
		}
		this.prices=tmpPrices;
		for(int i=0;i<this.prices.length;i++){
			Log.v("ShopAndStore","Price: "+this.prices[i]);
		}
	}
	
	public double[] getPrices(){
		double[] tmpprices = this.prices.clone();
		this.prices=null;
		return tmpprices;
	}
	
	public String[] getProducts(){
		String[] tmpproducts=this.products.clone();
		this.products=null;
		return tmpproducts;
	}
}


