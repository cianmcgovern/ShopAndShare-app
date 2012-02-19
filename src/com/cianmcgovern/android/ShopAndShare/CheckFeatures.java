package com.cianmcgovern.android.ShopAndShare;

import android.content.pm.PackageManager;

/**
 * Contains a number of static methods for checking the features of the device
 * 
 * @author Cian Mc Govern <cian@cianmcgovern.com>
 * 
 */
public class CheckFeatures {

    /**
     * Checks whether the device has a flash
     * 
     * @return boolean True if the device has a flash
     */
    public static boolean haveFlash() {
        return Constants.PACKAGE_MANAGER
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

    /**
     * Checks whether the device has auto focus
     * 
     * @return boolean True if the device has auto focus
     */
    public static boolean haveAutoFocus() {
        return Constants.PACKAGE_MANAGER
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_AUTOFOCUS);
    }

    /**
     * Checks whether the device has a camera
     * 
     * @return boolean True if the device has a camera
     */
    public static boolean haveCamera() {
        return Constants.PACKAGE_MANAGER
                .hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    /**
     * Checks whether the device has GPS
     * 
     * @return boolean True if the device has GPS
     */
    public static boolean haveGPS() {
        return Constants.PACKAGE_MANAGER
                .hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS);
    }

}
