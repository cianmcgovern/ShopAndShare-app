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

import java.util.ArrayList;

import android.content.pm.PackageManager;

public class Constants {

    public static byte[] IMAGE_DATA = null;
    public static String FILES_DIR = null;
    public static String SAVE_DIR = null;
    public static String UPLOADS = null;
    public static final String FULL_URL = "http://108.60.143.109/submit/upload";
    public static final String URL = "http://108.60.143.109";
    public static ArrayList<String> WORDLIST = null;
    public static final PackageManager PACKAGE_MANAGER = ShopAndShare.sContext
            .getPackageManager();
    public static final String LOG_TAG = "ShopAndShare";
}
