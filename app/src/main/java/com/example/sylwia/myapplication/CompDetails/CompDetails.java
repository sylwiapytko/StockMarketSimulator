package com.example.sylwia.myapplication.CompDetails;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sylwia.myapplication.Chart;
import com.example.sylwia.myapplication.CompList.CompOverview;
import com.example.sylwia.myapplication.CompList.MostActiveList;
import com.example.sylwia.myapplication.DBHandler;
import com.example.sylwia.myapplication.MyPurchases.MyPurchases;
import com.example.sylwia.myapplication.MyPurchases.PurchasedComp;
import com.example.sylwia.myapplication.MyPurchases.PurchasedCompOverview;
import com.example.sylwia.myapplication.Notifications.MyNotifications;
import com.example.sylwia.myapplication.R;

import org.json.JSONException;
import org.json.JSONObject;

public class CompDetails extends AppCompatActivity {
    String url;
    RequestQueue requestQueue;

    DBHandler dbHandler;
    PurchasedComp purchasedComp;

    TextView recordsTextView;
    Double myBalance;
    public static final String compSymbol = "com.example.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comp_details);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        dbHandler = new DBHandler(this, null, null, 1);
        requestQueue = Volley.newRequestQueue(this);
        SQLiteDatabase db = dbHandler.getWritableDatabase();
//        dbHandler.updateShema(db);
        Intent intent = getIntent();
        String symbol = intent.getStringExtra(MostActiveList.compSymbol);
        getCompDetails(symbol);
TextView balance =findViewById(R.id.balance);
myBalance = dbHandler.getMyBalance();
balance.setText(myBalance.toString());

    }
//    public void printDatabase(){
//        String dbString = dbHandler.databaseToString();
//        recordsTextView.setText(dbString);
//
//    }
    private void getCompDetails(final String compSymbol) {
        this.url = "https://api.iextrading.com/1.0/stock/"+compSymbol+"/quote";
        JsonObjectRequest objReq = new JsonObjectRequest(Request.Method.GET, url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                                try {
                                    JSONObject jsonObj = response;
                                    String symbol = jsonObj.get("symbol").toString();
                                    String companyName = jsonObj.get("companyName").toString();
                                    String latestPrice = jsonObj.get("latestPrice").toString();
                                    String change = jsonObj.get("change").toString();

                                    TextView tv_symbol = findViewById(R.id.symbol);
                                    TextView tv_companyName = findViewById(R.id.compName);
                                    TextView tv_latestPrice = findViewById(R.id.price);
                                    TextView tv_change = findViewById(R.id.change);

                                    purchasedComp = new PurchasedComp(symbol,Double.parseDouble(latestPrice), 1);
                                    tv_symbol.setText(symbol);
                                    tv_companyName.setText(companyName);
                                    tv_latestPrice.setText(latestPrice);
                                    tv_change.setText(change);
                                } catch (JSONException e) {
                                    // If there is an error then output this to the logs.
                                    Log.e("Volley", "Invalid JSON Object.");
                                }

                            }


                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // If there a HTTP error then add a note to our repo list.
                        Log.e("Volley", error.toString());
                    }
                }
        );
        // Add the request we just defined to our request queue.
        // The request queue will automatically handle the request as soon as it can.
        requestQueue.add(objReq);
        //  System.out.println("endend");
    }

    public void onBuy(View view) {
        EditText editText = (EditText) findViewById(R.id.amount);
        String amount = editText.getText().toString();
        purchasedComp.setPurchaseAmount(Integer.parseInt(amount));
        Double totalSum= purchasedComp.getPurchaseAmount()*purchasedComp.getPurchasePrice();
        Double newBalance =myBalance-totalSum;
        dbHandler.updateMyBalance(newBalance);
        dbHandler.addPurchase(purchasedComp);


        Intent intent = new Intent(view.getContext(), MyPurchases.class);
        startActivity(intent);
    }
    public void onViewChart(View view) {
        Intent intent = new Intent(view.getContext(), Chart.class);
        String message = purchasedComp.getSymbol();
        intent.putExtra(compSymbol, message);
        startActivity(intent);
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
}
