package com.example.sylwia.myapplication.CompList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.sylwia.myapplication.R;

import java.util.List;

/**
 * Created by Sylwia on 6/1/2018.
 */

public class CompListAdapter extends ArrayAdapter<CompOverview> {

    public CompListAdapter(Context context, List<CompOverview> comp) {
        super(context, R.layout.comp_list_item ,comp);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater myCustomInflater = LayoutInflater.from(getContext());
        View customView = myCustomInflater.inflate(R.layout.comp_list_item, parent, false);

        String symbol = getItem(position).getSymbol();
        String compName = getItem(position).getCompanyName();
        String price = getItem(position).getLatestPrice().toString();
        String change = getItem(position).getChange().toString();
        TextView tv_symbol = (TextView) customView.findViewById(R.id.symbol);
        TextView tv_compName = (TextView) customView.findViewById(R.id.compName);
        TextView tv_price = (TextView) customView.findViewById(R.id.price);
        TextView tv_change = (TextView) customView.findViewById(R.id.change);

        tv_symbol.setText(symbol);
        tv_compName.setText(compName);
        tv_price.setText(price);
        tv_change.setText(change);

        return customView;
    }
}