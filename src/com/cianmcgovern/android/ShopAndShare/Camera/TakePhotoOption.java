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
package com.cianmcgovern.android.ShopAndShare.Camera;

import java.util.ArrayList;

/**
 * Stores an option name and it's children
 * 
 * @author Cian Mc Govern <cian@cianmcgovern.com>
 *
 */
public class TakePhotoOption {
    
    private String mOption;
    private ArrayList<String> mOptionChildren;
    
    public TakePhotoOption(String option, ArrayList<String> optionChildren){
        this.mOption = option;
        this.mOptionChildren = optionChildren;
    }
    
    public String getOption(){
        return this.mOption;
    }
    
    public ArrayList<String> getOptionChildren(){
        return this.mOptionChildren;
    }
}
