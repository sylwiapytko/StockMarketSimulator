package com.example.sylwia.myapplication;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class CompDetails extends AppCompatActivity {
    String url;
    RequestQueue requestQueue;

    DBHandler dbHandler;
    PurchasedComp purchasedComp;

    TextView recordsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comp_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbHandler = new DBHandler(this, null, null, 1);
        requestQueue = Volley.newRequestQueue(this);
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        dbHandler.updateShema(db);
        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String symbol = intent.getStringExtra(MostActiveList.compSymbol);
        recordsTextView = (TextView) findViewById(R.id.showdb);
        // Capture the layout's TextView and set the string as its text
        printDatabase();
        getCompDetails(symbol);


    }
    public void printDatabase(){
        String dbString = dbHandler.databaseToString();
        recordsTextView.setText(dbString);

    }
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
        dbHandler.addPurchase(purchasedComp);
        printDatabase();
    }
}
