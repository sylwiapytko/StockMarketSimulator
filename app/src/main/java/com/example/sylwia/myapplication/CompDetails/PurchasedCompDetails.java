package com.example.sylwia.myapplication.CompDetails;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sylwia.myapplication.Chart;
import com.example.sylwia.myapplication.CompList.MostActiveList;
import com.example.sylwia.myapplication.DBHandler;
import com.example.sylwia.myapplication.MyPurchases.MyPurchases;
import com.example.sylwia.myapplication.MyPurchases.PurchasedComp;
import com.example.sylwia.myapplication.MyPurchases.PurchasedCompOverview;
import com.example.sylwia.myapplication.Notifications.MyNotifications;
import com.example.sylwia.myapplication.R;

public class PurchasedCompDetails extends AppCompatActivity {

    DBHandler dbHandler;
    PurchasedCompOverview purchasedCompOverview;
    Double myBalance;

    public static final String compSymbol = "com.example.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_purchased_comp_details);

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


        TextView balance =findViewById(R.id.balance);
        myBalance = dbHandler.getMyBalance();
        balance.setText(myBalance.toString());
    }

    public void onSell(View view) {

        Double totalSum= purchasedCompOverview.getAmount()*purchasedCompOverview.getLatestPrice();
        Double newBalance =myBalance+totalSum;
        dbHandler.updateMyBalance(newBalance);
        dbHandler.deletePurchase(purchasedCompOverview.getId());
        onSeePurchased(view);
    }
    public void onSeePurchased(View view) {
        Intent intent = new Intent(this, MyPurchases.class);
        startActivity(intent);
    }
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater= getMenuInflater();
        menuInflater.inflate(R.menu.app_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_most_active:{
                if (item.isChecked())
                    item.setChecked(false);
                else
                    item.setChecked(true);

                startActivity(new Intent(this, MostActiveList.class));
                return true;
            }

            case R.id.menu_my_purchases:{
                if (item.isChecked())
                    item.setChecked(false);
                else
                    item.setChecked(true);
                startActivity(new Intent(this, MyPurchases.class));
                return true;
            }
            case R.id.menu_my_notifications:{
                if (item.isChecked())
                    item.setChecked(false);
                else
                    item.setChecked(true);
                startActivity(new Intent(this, MyNotifications.class));
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void onViewChart(View view) {
        Intent intent = new Intent(view.getContext(), Chart.class);
        String message = purchasedCompOverview.getSymbol();
        intent.putExtra(compSymbol, message);
        startActivity(intent);
    }

}
