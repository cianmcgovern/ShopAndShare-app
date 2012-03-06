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

import com.cianmcgovern.android.ShopAndShare.Constants;

public class Comparator {

    /**
     * Takes in a string and compares it to all strings in Constants.wordlist
     * 
     * @param string
     *            The string to be compared
     * @return Returns the best match string
     */
    public static String findClosestString(String string) {

        String bestMatch = string;

        // Base score - Score must be higher than 0.9 before a string is
        // suggested
        double previous = 0.9;

        // Loop through the wordlist and compare each string with the string to
        // compare
        // If the score is better than the last score, store the best match
        for (int i = 0; i < Constants.WORDLIST.size(); i++) {

            if (CompareStrings.similarity(string, Constants.WORDLIST.get(i)) > previous)
                bestMatch = Constants.WORDLIST.get(i);
        }
        return bestMatch;
    }
}
