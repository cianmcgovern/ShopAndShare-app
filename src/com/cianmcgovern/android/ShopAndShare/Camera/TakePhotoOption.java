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

    public TakePhotoOption(String option, ArrayList<String> optionChildren) {
        this.mOption = option;
        this.mOptionChildren = optionChildren;
    }

    public String getOption() {
        return this.mOption;
    }

    public ArrayList<String> getOptionChildren() {
        return this.mOptionChildren;
    }
}
