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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cianmcgovern.android.ShopAndShare.Comparison.Comparator;
import com.cianmcgovern.android.ShopAndShare.Comparison.CompareStrings;

import android.util.Log;

/**
 * Singleton object that stores the results from image analysing
 * 
 * @author Cian Mc Govern
 * 
 */
public class Results {

    private HashResults<String, Item> mResults;

    private static Results sInstance;
    private final SimpleDateFormat mDf = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");

    public static Results getInstance() {
        if (sInstance == null) {
            sInstance = new Results();
            sInstance.mResults = new HashResults<String, Item>();
        }
        return sInstance;
    }

    private Results() {
    }

    public void addItem(String productName, Item product) {
        mResults.put(productName, product);
    }

    /**
     * Takes in an array containing the list of products and the length of the
     * array
     * 
     * @param inProducts
     * @param length
     * 
     */
    public void setProducts(String[] inProducts, int length) {
        clearResults();
        boolean reducedPriceDone = false;
        String reducedPriceKey = null;
        for (int i = 0; i < length; i++) {
            String line = inProducts[i].trim();
            String nextLine = null;
            if (i + 1 < length) nextLine = inProducts[i + 1].trim();

            if (reducedPriceDone) {
                mResults.remove(reducedPriceKey);
                reducedPriceDone = false;
            }
            else if (line.contains(".") && line.length() > 4) {
                String product = parseProduct(line);
                String price = parsePrice(line);
                if (product != null && product.length() < 15) {
                    if (!mResults.containsKey(product.trim())) {
                        mDf.setTimeZone(TimeZone.getTimeZone("GMT"));
                        ;
                        Item x = new Item(product.trim(), price.trim(), mDf
                                .format(new Date()).toString());
                        Log.v(Constants.LOG_TAG, "Added parsed product: "
                                + product + " with price: " + price);
                        mResults.put(product.trim(), x);
                    }
                    else
                        Log.w(Constants.LOG_TAG, "Map already contains "
                                + product + "! Not adding...");
                }
            }
            // If there was no price in the current result, check if the next
            // product is similar to "Reduced Price" then store this price as
            // the products price
            else if (nextLine != null && nextLine.contains(".")
                    && nextLine.length() > 4) {
                String price = parsePrice(nextLine);
                String reducedPrice = parseProduct(nextLine);
                String product = parseProduct(line);
                if (reducedPrice != null
                        && CompareStrings.similarity(reducedPrice.trim(),
                                "REDUCED PRICE") > 0.8) {
                    Log.v(Constants.LOG_TAG, "Added parsed product: " + product
                            + " with reduced price: " + price);
                    mResults.put(product,
                            new Item(product, price, mDf.format(new Date())
                                    .toString()));
                    reducedPriceDone = true;
                }

            }
            else
                Log.w(Constants.LOG_TAG,
                        "Couldn't find decimal point in string");
        }
    }

    /**
     * Clears the current results
     */
    public void clearResults() {
        this.mResults.clear();
    }

    /**
     * Parses the input string for a decimal number and returns it as a string
     * 
     * @param x
     * @return
     */
    public String parsePrice(String input) {

        String re1 = ".*?"; // Non-greedy match on filler
        String re2 = "([+-]?\\d*\\.\\d+)(?![-+0-9\\.])"; // Float 1

        Pattern p = Pattern.compile(re1 + re2, Pattern.CASE_INSENSITIVE
                | Pattern.DOTALL);
        Matcher m = p.matcher(input);

        if (m.find()) {
            String float1 = m.group(1);
            return float1.toString();
        }
        else
            return "0.00";
    }

    /**
     * Parses the input string and returns all words in the string as a single
     * string
     * 
     * @param input
     * @return
     */
    public String parseProduct(String input) {

        String re1 = "((?:[a-z][a-z]+))";

        Pattern p = Pattern.compile(re1, Pattern.CASE_INSENSITIVE
                | Pattern.DOTALL);
        Matcher m = p.matcher(input);

        String result = null;

        while (m.find()) {
            if (result == null)
                result = m.group();
            else
                result = result + " " + m.group();
        }

        // Optimise result using Comparator
        if (result != null) {
            result = Comparator.findClosestString(result);
            return result.trim();
        }

        else
            return result;
    }

    public void setHashResults(HashResults<String, Item> in) {
        mResults = in;
    }

    public HashResults<String, Item> getProducts() {
        return mResults;
    }

    public void changeKey(String old, String snew) {
        Item x = mResults.get(old);
        x.setProduct(snew);
        mResults.remove(old);
        mResults.put(snew, x);
    }

    public void saveCurrentResults() throws IOException {

        if (!new File(Constants.SAVE_DIR).isDirectory())
            new File(Constants.SAVE_DIR).mkdir();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd_hh:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("GMT"));

        String filename = Constants.SAVE_DIR + "/" + df.format(new Date());
        Log.v(Constants.LOG_TAG, "Save file is: " + filename);

        File f = new File(filename);
        FileOutputStream fos = new FileOutputStream(f);
        ObjectOutputStream ofos = new ObjectOutputStream(fos);
        ofos.writeObject(mResults);
        ofos.close();
        fos.close();
        // Empty the results so they don't get appended to a new result
        this.clearResults();
    }

    /**
     * Saves the current results to a text file for uploading
     * 
     * @return filename Filename of save file
     * @throws IOException
     */
    public String toFile() throws IOException {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        String filename = Constants.UPLOADS + "/" + df.format(new Date());
        File f = new File(filename);
        BufferedWriter fout = new BufferedWriter(new FileWriter(f));
        Iterator it = this.mResults.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry) it.next();
            Item item = (Item) pairs.getValue();
            String line = item.getProduct() + "/" + item.getPrice() + "/"
                    + item.getTime();
            fout.write(line + "\r\n");
        }
        fout.close();
        return filename;
    }
}
