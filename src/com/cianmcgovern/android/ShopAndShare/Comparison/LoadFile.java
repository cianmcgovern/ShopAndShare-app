/*******************************************************************************
 * Copyright (c) 2012 Cian Mc Govern.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     Cian Mc Govern - initial API and implementation
 ******************************************************************************/
package com.cianmcgovern.android.ShopAndShare.Comparison;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import android.util.Log;

public class LoadFile {
	
	public static ArrayList<String> fileToList(String path){
	    
	    String line;
		
	    ArrayList<String> list = new ArrayList<String>();
	    
		if((new File(path)).exists()){
			try{
				BufferedReader br = new BufferedReader(new FileReader(path));

				while((line=br.readLine())!=null){
					list.add(line);
				}
			}
			catch(Exception e){
				Log.e("ShopAndShare","Exception when when reading from file in Comparator.LoadFile");
				e.printStackTrace();
			}
		}
		else
			Log.e("ShopAndShare",path+" doesn't exist!!");
		
		return list;
	}
}
