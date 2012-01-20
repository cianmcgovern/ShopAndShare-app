package com.cianmcgovern.android.ShopAndShare;

import java.util.HashMap;

public class HashResults<ID,Price> extends HashMap<ID,Price> {

	private static final long serialVersionUID = 3561313463102404517L;
	private HashMap<ID, Price> mHashMap;
	
	public HashResults(){
		mHashMap = new HashMap<ID, Price>();
	}
}
