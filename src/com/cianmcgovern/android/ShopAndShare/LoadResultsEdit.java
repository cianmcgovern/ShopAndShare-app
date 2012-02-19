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

import java.io.File;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * LoadResultsEdit activity extends LoadResults and overrides the
 * onListItemClick to allow a user to delete a save
 * 
 * @author Cian Mc Govern <cian@cianmcgovern.com>
 * 
 */
public class LoadResultsEdit extends LoadResults {

    private String mFilename;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // Fetch the list of files from the saves directory and display them in
        // the ListView
        ArrayList<String> saves = new ArrayList<String>();

        File[] saveFiles = new File(Constants.SAVE_DIR).listFiles();

        for (int i = 0; i < saveFiles.length; i++)
            saves.add(saveFiles[i].getName());

        ArrayAdapter<String> ad = new LoadResultsEditAdapter<String>(this,
                android.R.layout.simple_list_item_1, saves);

        this.getListView().setBackgroundResource(R.drawable.default_background);

        setListAdapter(ad);

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        mFilename = (String) getListAdapter().getItem(position);

        // AlertDialog to confirm deletion of save by user
        new AlertDialog.Builder(this)
                .setTitle(R.string.deleteConfirmTitle)
                .setMessage(R.string.deleteConfirm)
                .setPositiveButton(R.string.yes,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                    int which) {
                                // TODO Auto-generated method stub
                                File f = new File(Constants.SAVE_DIR + "/"
                                        + mFilename);
                                if (!f.delete())
                                    Log.e("ShopAndShare", mFilename
                                            + " save file was not deleted");
                                else
                                    Log.v("ShopAndShare", mFilename
                                            + " save file was deleted");
                                Intent i = new Intent(ShopAndShare.sContext,
                                        LoadResultsEdit.class);
                                startActivity(i);
                                finish();
                            }

                        }).setNegativeButton(R.string.no, null).show();
    }
}
