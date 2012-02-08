/*******************************************************************************
 * Copyright 2012 Cian Mc Govern
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.cianmcgovern.android.ShopAndShare;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Item implements Serializable{

	private static final long serialVersionUID = 1L;
	private String productName;
	private String price;
	private String time;
	private int rating;
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public Item(String product, String price,int rating){
	    df.setTimeZone(TimeZone.getTimeZone("GMT"));
		this.productName=product;
		this.price=price;
		this.rating=rating;
		this.time = df.format(new Date());
	}
	
	public Item(String product, String price){
	    df.setTimeZone(TimeZone.getTimeZone("GMT"));
		this.productName=product;
		this.price=price;
		this.rating=0;
		this.time = df.format(new Date());
	}
	
	public String getProduct(){
		return this.productName;
	}
	
	public String getPrice(){
		return this.price;
	}
	
	public int getRating(){
		return this.rating;
	}
	
	public String getTime() {
	    return this.time;
	}
	
	public void setProduct(String product){
		this.productName=product;
	}
	
	public void setPrice(String price){
		this.price=price;
	}
	
	public void setRating(int rating){
		this.rating=rating;
	}
}
