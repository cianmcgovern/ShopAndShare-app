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

import com.cianmcgovern.android.ShopAndShare.Constants;

public class Comparator {

    /**
     * Takes in a string and compares it to all strings in Constants.wordlist
     * 
     * @param string The string to be compared
     * @return Returns the best match string
     */
    public static String findClosestString(String string){
        
        String bestMatch = string;
        
        // Base score - Score must be higher than 0.7 before a string is suggested
        double previous = 0.8;
        
        // Loop through the wordlist and compare each string with the string to compare
        // If the score is better than the last score, store the best match
        for(int i = 0; i < Constants.wordList.size();i++){
            
            if(CompareStrings.similarity(string, Constants.wordList.get(i)) > previous)
                bestMatch = Constants.wordList.get(i);
        }
        return bestMatch;
    }
}
