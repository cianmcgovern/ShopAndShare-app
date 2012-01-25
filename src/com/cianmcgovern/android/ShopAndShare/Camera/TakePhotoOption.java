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
