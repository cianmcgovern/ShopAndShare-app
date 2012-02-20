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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OptionalDataException;
import java.util.ArrayList;

import com.cianmcgovern.android.ShopAndShare.DisplayResults.ListCreator;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * LoadResults displays all of the saved results currently on the device The
 * user can load a save by clicking on it which redirects them to the Display
 * results activity
 * 
 * @author Cian Mc Govern <cian@cianmcgovern.com>
 * 
 */
public class LoadResults extends ListActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // Fetch the list of files from the saves directory and display them in
        // the ListView
        ArrayList<String> saves = new ArrayList<String>();

        File[] saveFiles = new File(Constants.SAVE_DIR).listFiles();

        for (int i = 0; i < saveFiles.length; i++)
            saves.add(saveFiles[i].getName());

        ArrayAdapter<String> ad = new LoadResultsAdapter<String>(this,
                android.R.layout.simple_list_item_1, saves);

        this.getListView().setBackgroundResource(R.drawable.default_background);

        setListAdapter(ad);

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        // Load the object from the file selected by the user
        String x = (String) getListAdapter().getItem(position);
        try {
            loadObject(Constants.SAVE_DIR + "/" + x);
        } catch (OptionalDataException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Loads a serialized file containing a HashResults object and updates
     * Results with that object and then calls DisplayResults
     * 
     * @param path
     * @throws OptionalDataException
     * @throws ClassNotFoundException
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    private void loadObject(String path) throws OptionalDataException,
            ClassNotFoundException, IOException {
        File file = new File(path);
        FileInputStream f;
        ObjectInputStream fo;
        f = new FileInputStream(file);
        fo = new ObjectInputStream(f);
        Results.getInstance().setHashResults(
                (HashResults<String, Item>) fo.readObject());
        Intent i = new Intent(ShopAndShare.sContext, ListCreator.class);
        startActivity(i);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inf = getMenuInflater();
        inf.inflate(R.layout.load_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem it) {
        switch (it.getItemId()) {
        // Edit menu redirects to LoadResultsEdit which allows user to delete
        // saves
        case R.id.edit:
            Intent i = new Intent(ShopAndShare.sContext, LoadResultsEdit.class);
            startActivity(i);
            finish();
            return true;
        case R.id.exit:
            finish();
            return true;
        case R.id.help:
            new HelpDialog(this,this.getText(R.string.loadResultsHelpMessage).toString()).show();
            return true;
        default:
            return super.onOptionsItemSelected(it);
        }
    }
}
