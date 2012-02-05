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

public class Item implements Serializable{

	private static final long serialVersionUID = 1L;
	private String productName;
	private String price;
	private int rating;
	
	public Item(String product, String price,int rating){
		this.productName=product;
		this.price=price;
		this.rating=rating;
	}
	
	public Item(String product, String price){
		this.productName=product;
		this.price=price;
		this.rating=0;
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
