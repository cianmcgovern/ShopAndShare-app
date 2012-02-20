package com.cianmcgovern.android.ShopAndShare;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class EmptyResult extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.empty_result);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    // Create options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inf = getMenuInflater();
        inf.inflate(R.layout.default_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.exit:
            finish();
            return true;
        case R.id.help:
            new HelpDialog(this,this.getText(R.string.emptyResultHelpMessage).toString()).show();
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
}
