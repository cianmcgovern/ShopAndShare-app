package com.cianmcgovern.android.ShopAndShare.DisplayResults;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cianmcgovern.android.ShopAndShare.Item;
import com.cianmcgovern.android.ShopAndShare.R;

public class ItemAdapter extends ArrayAdapter<Item>{

    private ArrayList<Item> items;
    private Context con;

    public ItemAdapter(Context context, int textViewResourceId, ArrayList<Item> items) {
        
        super(context, textViewResourceId, items);
        this.con = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater)con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.display_results_row, null);
        }

        Item o = items.get(position);

        if (o != null) {
            TextView tt = (TextView) v.findViewById(R.id.toptext);
            TextView bt = (TextView) v.findViewById(R.id.bottomtext);
            if (tt != null) {
                tt.setText("Product: "+o.getProduct());                            }
            if(bt != null){
                bt.setText("Price: "+ o.getPrice());
            }
        }
        return v;
    }
}
