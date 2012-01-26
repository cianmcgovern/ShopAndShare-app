package com.cianmcgovern.android.ShopAndShare;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class LoadResultsEditAdapter<String> extends ArrayAdapter<String> {

    private Context mContext;
    private ArrayList<String> mList;
    
    public LoadResultsEditAdapter(Context context, int textViewResourceId,
            List<String> objects) {
        super(context, textViewResourceId, objects);
        this.mContext = context;
        this.mList = (ArrayList<String>) objects;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        
        View v = convertView;
        if(v == null) {
            LayoutInflater vi = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.load_results_edit_row, null);
        }
        
        String text = mList.get(position);
        
        if(text != null) {
            TextView tv = (TextView)v.findViewById(R.id.loadResultsEditText);
            if(tv != null)
                tv.setText(text.toString());
        }
        
        return v;
    }
}
