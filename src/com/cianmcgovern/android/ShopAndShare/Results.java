package com.cianmcgovern.android.ShopAndShare;

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
	
	
	public HashResults<String,Item> getProducts(){
		return results;
	}
	
	public void changeKey(String old, String snew){
		Item x = results.get(old);
		
		results.remove(old);
		results.put(snew, x);
	}
}


