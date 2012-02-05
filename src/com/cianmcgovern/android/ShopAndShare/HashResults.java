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
package com.cianmcgovern.android.ShopAndShare;

import java.util.HashMap;

public class HashResults<ID,Price> extends HashMap<ID,Price> {

	private static final long serialVersionUID = 3561313463102404517L;
	private HashMap<ID, Price> mHashMap;
	
	public HashResults(){
		mHashMap = new HashMap<ID, Price>();
	}
}
