package com.example.sylwia.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MostActiveList extends AppCompatActivity {
    RequestQueue requestQueue;  // This is our requests queue to process our HTTP requests.
    List<CompOverview> compOverviewList;
    String url;  // This will hold the full URL which will include the username entered in the etGitHubUser.
    public static final String compSymbol = "com.example.myfirstapp.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_most_active_list);
        requestQueue = Volley.newRequestQueue(this);  // This setups up a new request queue which we will need to make HTTP requests.

        compOverviewList = new ArrayList<>();
        getMostActiveList();
    }

private void setMostActiveList(){
    ListAdapter compListAdapter = new CompListAdapter(this, compOverviewList);
    ListView compListView = (ListView) findViewById(R.id.compList);
    compListView.setAdapter(compListAdapter);

    compListView.setOnItemClickListener(
            new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    CompOverview compOverview = (CompOverview) parent.getItemAtPosition(position);
                    Intent intent = new Intent(view.getContext(), CompDetails.class);
        String message = compOverview.getSymbol();
        intent.putExtra(compSymbol, message);
                    startActivity(intent);
                }
            }
    );
}
    private void getMostActiveList() {
         this.url = "https://api.iextrading.com/1.0/stock/market/list/mostactive";

        JsonArrayRequest arrReq = new JsonArrayRequest(Request.Method.GET, url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject jsonObj = response.getJSONObject(i);
                                    String symbol = jsonObj.get("symbol").toString();
                                    String companyName = jsonObj.get("companyName").toString();
                                    String latestPrice = jsonObj.get("latestPrice").toString();
                                    String change = jsonObj.get("change").toString();
                                    CompOverview compOverview = new CompOverview(symbol,companyName, Double.parseDouble(latestPrice), Double.parseDouble(change));
                                    compOverviewList.add(compOverview);
                                } catch (JSONException e) {
                                    Log.e("Volley", "Invalid JSON Object.");
                                }
                            }
                        setMostActiveList();
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", error.toString());
                    }
                }
        );
        requestQueue.add(arrReq);
    }
}
