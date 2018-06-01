package com.example.sylwia.myapplication.MyPurchases;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sylwia.myapplication.CompDetails.CompDetails;
import com.example.sylwia.myapplication.CompDetails.PurchasedCompDetails;
import com.example.sylwia.myapplication.DBHandler;
import com.example.sylwia.myapplication.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.List;

public class MyPurchases extends AppCompatActivity {

    RequestQueue requestQueue;
    List<PurchasedCompOverview> purchasedCompOverviewsList;
    String url;
    DBHandler dbHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_purchases);
        requestQueue = Volley.newRequestQueue(this);

        dbHandler = new DBHandler(this, null, null, 1);

        purchasedCompOverviewsList = dbHandler.selectAllPurchasedCompOverview();
        getLatestPrice();

    }

    private void getLatestPrice() {
        for(PurchasedCompOverview purchasedCompOverview: purchasedCompOverviewsList){
           getCompDetailsLatestPrice(purchasedCompOverview);
        }
    }


    private void getCompDetailsLatestPrice(final PurchasedCompOverview purchasedCompOverview) {
        this.url = "https://api.iextrading.com/1.0/stock/"+purchasedCompOverview.getSymbol()+"/quote";
        JsonObjectRequest objReq = new JsonObjectRequest(Request.Method.GET, url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonObj = response;
                            String companyName = jsonObj.get("companyName").toString();
                            Double latestPrice = Double.parseDouble(jsonObj.get("latestPrice").toString());
                            purchasedCompOverview.setLatestPrice(latestPrice);
                            purchasedCompOverview.setCompanyName(companyName);
                            Double difference = (purchasedCompOverview.getLatestPrice()-purchasedCompOverview.getPurchasePrice());
                            DecimalFormat df= new DecimalFormat("#.##");
                            purchasedCompOverview.setDifference(Double.parseDouble(df.format(difference)));
                            setMyPurchasesList();
                        } catch (JSONException e) {
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
        requestQueue.add(objReq);
    }

    private void setMyPurchasesList(){
        ListAdapter purchasedCompOverviewsListAdapter = new PurchasedCompListAdapter(this, purchasedCompOverviewsList);
        ListView purchasedCompListView = (ListView) findViewById(R.id.purchasedCompList);
        purchasedCompListView.setAdapter(purchasedCompOverviewsListAdapter);

        purchasedCompListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        PurchasedCompOverview purchasedCompOverview = (PurchasedCompOverview) parent.getItemAtPosition(position);
                        Intent intent = new Intent(view.getContext(), PurchasedCompDetails.class);

                        intent.putExtra("extraPurchasedCompOverviev", purchasedCompOverview);
                        startActivity(intent);
                    }
                }
        );
    }
}
