package com.cianmcgovern.android.ShopAndShare;

import android.app.AlertDialog;
import android.content.Context;

/**
 * Wrapper for AlertDialog that creates an AlertDialog for help screens
 * 
 * @author Cian Mc Govern <cian@cianmcgovern.com>
 * 
 */
public class HelpDialog {

    AlertDialog mDialog;

    /**
     * Takes in a context for the HelpDialog and the message to be displayed
     * 
     * @param con
     *            The context that the dialog will be displayed in
     * @param message
     *            The message to display
     */
    public HelpDialog(Context con, String message) {

        mDialog = new AlertDialog.Builder(con).create();
        mDialog.setTitle(R.string.help);
        mDialog.setMessage(message);
    }

    /**
     * Displays the HelpDialog in the context passed to the constructor
     */
    public void show() {
        mDialog.show();
    }
}
