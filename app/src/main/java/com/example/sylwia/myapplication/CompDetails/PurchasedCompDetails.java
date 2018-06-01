package com.example.sylwia.myapplication.CompDetails;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sylwia.myapplication.CompList.MostActiveList;
import com.example.sylwia.myapplication.DBHandler;
import com.example.sylwia.myapplication.MyPurchases.MyPurchases;
import com.example.sylwia.myapplication.MyPurchases.PurchasedComp;
import com.example.sylwia.myapplication.MyPurchases.PurchasedCompOverview;
import com.example.sylwia.myapplication.R;

public class PurchasedCompDetails extends AppCompatActivity {

    DBHandler dbHandler;
    PurchasedCompOverview purchasedCompOverview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_purchased_comp_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        purchasedCompOverview = (PurchasedCompOverview)intent.getSerializableExtra("extraPurchasedCompOverviev");

        dbHandler = new DBHandler(this, null, null, 1);

        TextView tv_symbol = findViewById(R.id.symbol);
        TextView tv_companyName = findViewById(R.id.compName);
        TextView tv_latestPrice = findViewById(R.id.latestPrice);
        TextView tv_purchasedPrice = findViewById(R.id.purchasedPrice);
        TextView tv_amount = findViewById(R.id.amountPurchase);
        TextView tv_totalDifference = findViewById(R.id.totalDifference);

        tv_symbol.setText(purchasedCompOverview.getSymbol());
        tv_companyName.setText(purchasedCompOverview.getCompanyName());
        tv_latestPrice.setText(purchasedCompOverview.getLatestPrice().toString());
        tv_latestPrice.setText(purchasedCompOverview.getLatestPrice().toString());
        tv_purchasedPrice.setText(purchasedCompOverview.getPurchasePrice().toString());

        System.out.println(purchasedCompOverview.getAmount());
        tv_amount.setText(purchasedCompOverview.getAmount().toString());
        tv_totalDifference.setText(purchasedCompOverview.getTotalDifference().toString());



    }

    public void onSell(View view) {
        dbHandler.deletePurchase(purchasedCompOverview.getId());
        onSeePurchased(view);
    }
    public void onSeePurchased(View view) {
        Intent intent = new Intent(this, MyPurchases.class);
        startActivity(intent);
    }

}
