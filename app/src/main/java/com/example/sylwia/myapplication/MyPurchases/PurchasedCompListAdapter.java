package com.example.sylwia.myapplication.MyPurchases;

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

public class PurchasedCompListAdapter extends ArrayAdapter<PurchasedCompOverview> {

    public PurchasedCompListAdapter(Context context, List<PurchasedCompOverview> comp) {
        super(context, R.layout.comp_list_item ,comp);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater myCustomInflater = LayoutInflater.from(getContext());
        View customView = myCustomInflater.inflate(R.layout.purchased_comp_list, parent, false);

        String symbol = getItem(position).getSymbol();
        String compName = getItem(position).getCompanyName();
        String purchasePrice = getItem(position).getPurchasePrice().toString();
        String latestPrice = getItem(position).getLatestPrice().toString();
        String difference = getItem(position).getDifference().toString();
        TextView tv_symbol = (TextView) customView.findViewById(R.id.symbol);
        TextView tv_compName = (TextView) customView.findViewById(R.id.compName);
        TextView tv_purchasedPrice = (TextView) customView.findViewById(R.id.purchasedPrice);
        TextView tv_latestPrice = (TextView) customView.findViewById(R.id.latestPrice);
        TextView tv_difference = (TextView) customView.findViewById(R.id.difference);

        tv_symbol.setText(symbol);
        tv_compName.setText(compName);
        tv_purchasedPrice.setText(purchasePrice);
        tv_latestPrice.setText(latestPrice);
        tv_difference.setText(difference);

        return customView;
    }
}