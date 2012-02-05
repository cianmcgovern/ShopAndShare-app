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

public class CompareStrings {

    // Used the code from here:
    // https://code.google.com/p/duke/source/browse/src/main/java/no/priv/garshol/duke/JaroWinkler.java?r=e32a5712dbd51f1d4c81e84cfa438468e217a65d
    // to implement the Jaro Winkler string comparison algorithm
    public static double similarity(String a, String b) {

        if (a.equalsIgnoreCase(b))
            return 1.0;

        int common = 0, transpositions = 0, prevpos = -1;

        // Set string a to be the shortest string
        if (a.length() > b.length()) {
            String tmp = b;
            b = a.toLowerCase();
            a = tmp.toLowerCase();
        }

        // The maximum distance between matching characters must be half of the
        // length of the longest string minus 1.
        int max = (b.length() / 2);

        // Loop through all the characters in a and see if the character is in b
        for (int i = 0; i < a.length(); i++) {

            char x = a.charAt(i);

            for (int j = Math.max(0, i - max); j < Math
                    .min(b.length(), i + max); j++) {
                // If we get a common character, increment the common counter
                if (x == b.charAt(j)) {
                    common++;
                    // Increment the transposition counter
                    if (prevpos != -1 && j < prevpos)
                        transpositions++;
                    prevpos = j;
                    break;
                }
            }
        }

        // If no common characters are found, return 0.0
        if (common == 0)
            return 0.0;

        // Calculate the score ie. the Jaro distance
        double score = ((common / (double) a.length())
                + (common / (double) b.length()) + ((common - transpositions) / (double) common)) / 3;

        // Common prefix adjustment
        int pr = 0;
        int last = Math.min(4, a.length());
        // Find the number of matching starting characters in both strings
        while (pr < last && a.charAt(pr) == b.charAt(pr)) {
            pr++;
        }

        score = score + ((pr * (1 - score)) / 10);

        return score;
    }
}
