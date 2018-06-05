package com.example.sylwia.myapplication.CompList;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.sylwia.myapplication.CompDetails.CompDetails;
import com.example.sylwia.myapplication.MyPurchases.MyPurchases;
import com.example.sylwia.myapplication.Notifications.Receiver;
import com.example.sylwia.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
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

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        System.out.println("set alarm1 " +prefs.getBoolean("firstTime", false)+prefs.getBoolean("firstTime", true));
        if (prefs.getBoolean("firstTime", false)) {
            Intent alarmIntent = new Intent(this, Receiver.class);
            System.out.println("set alarm1");


            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, 1);
            calendar.set(Calendar.MINUTE,1);
            calendar.set(Calendar.SECOND, 1);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
            AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

            manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, pendingIntent);

            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", true);
            editor.apply();
        }
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

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
