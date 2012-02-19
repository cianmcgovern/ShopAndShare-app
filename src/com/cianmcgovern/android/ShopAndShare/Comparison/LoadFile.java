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
package com.cianmcgovern.android.ShopAndShare.Comparison;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import com.cianmcgovern.android.ShopAndShare.Constants;

import android.util.Log;

public class LoadFile {

    public static ArrayList<String> fileToList(String path) {

        String line;

        ArrayList<String> list = new ArrayList<String>();

        if ((new File(path)).exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(path));

                while ((line = br.readLine()) != null) {
                    list.add(line);
                }
            } catch (Exception e) {
                Log.e(Constants.LOG_TAG,
                        "Exception when when reading from file in Comparator.LoadFile");
                e.printStackTrace();
            }
        }
        else
            Log.e(Constants.LOG_TAG, path + " doesn't exist!!");

        return list;
    }
}
