package com.cianmcgovern.android.ShopAndShare;

import java.util.HashMap;

import android.util.Log;

public class HashResults<ID,Price> extends HashMap<ID,Price> {
	
	private HashMap<ID, Price> mHashMap;
	
	public HashResults(){
		mHashMap = new HashMap<ID, Price>();
	}
}
