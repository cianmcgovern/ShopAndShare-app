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

public class Item implements Serializable {

    private static final long serialVersionUID = 1L;
    private String mProductName;
    private String mPrice;
    private String mTime;
    private int mRating;

    public Item(String product, String price, int rating, String time) {
        this.mProductName = product;
        this.mPrice = price;
        this.mRating = rating;
        this.mTime = time;
    }

    public Item(String product, String price, String time) {
        this.mProductName = product;
        this.mPrice = price;
        this.mRating = 0;
        this.mTime = time;
    }

    public String getProduct() {
        return this.mProductName;
    }

    public String getPrice() {
        return this.mPrice;
    }

    public int getRating() {
        return this.mRating;
    }

    public String getTime() {
        return this.mTime;
    }

    public void setProduct(String product) {
        this.mProductName = product;
    }

    public void setPrice(String price) {
        this.mPrice = price;
    }

    public void setRating(int rating) {
        this.mRating = rating;
    }
}
