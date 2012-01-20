package com.cianmcgovern.android.ShopAndShare;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import android.util.Log;

/**
 * Singleton object that stores the results from image analysing
 * 
 * @author Cian Mc Govern
 *
 */
public class Results {
	
	private static HashResults<String, Item> results;
	
	private static Results _instance;
	
	public static Results getInstance(){
		if(_instance==null){
			_instance = new Results();
			results = new HashResults<String,Item>();
		}
		return _instance;
	}
	
	private Results(){
	}
	
	/**
	 * Takes in an array containing the list of products and the length of the array
	 * It splits each result based on the last decimal point in each slot of the array
	 * 
	 * @param inProducts
	 * @param length
	 * 
	 */
	public void setProducts(String[] inProducts,int length){
		for(int i=0;i<length;i++){
			int decimalLoc = inProducts[i].lastIndexOf(".");
			if(decimalLoc > 0){
				String product = inProducts[i].substring(0, (decimalLoc-2)).trim();
				String price = inProducts[i].substring(decimalLoc-1,inProducts[i].length()).trim();
				Item x = new Item(product,price);
				results.put(product, x);
			}
		}
	}
	
	public void setHashResults(HashResults<String,Item> in){
		results=in;
	}
	
	public HashResults<String,Item> getProducts(){
		return results;
	}
	
	public void changeKey(String old, String snew){
		Item x = results.get(old);
		
		results.remove(old);
		results.put(snew, x);
	}
	
	public void saveCurrentResults() throws IOException{
		
		if(!new File(Constants.saveDir).isDirectory())
			new File(Constants.saveDir).mkdir();
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd_hh:mm:ss");
		df.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		String filename = Constants.saveDir+"/"+df.format(new Date());
		Log.v("ShopAndShare", "Save file is: "+filename);
		
		File f = new File(filename);
		FileOutputStream fos = new FileOutputStream(f);
		ObjectOutputStream ofos = new ObjectOutputStream(fos);
		ofos.writeObject(results);
		ofos.close();
		fos.close();
	}
}


