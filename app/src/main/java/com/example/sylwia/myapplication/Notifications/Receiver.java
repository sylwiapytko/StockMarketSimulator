package com.example.sylwia.myapplication.Notifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sylwia.myapplication.CompList.MostActiveList;
import com.example.sylwia.myapplication.DBHandler;
import com.example.sylwia.myapplication.MainActivity;
import com.example.sylwia.myapplication.MyPurchases.MyPurchases;
import com.example.sylwia.myapplication.MyPurchases.PurchasedCompOverview;
import com.example.sylwia.myapplication.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Sylwia on 6/4/2018.
 */

public class Receiver  extends BroadcastReceiver {

    RequestQueue requestQueue;
    List<PurchasedCompOverview> purchasedCompOverviewsList;
    String url;
    DBHandler dbHandler;
    Double balanceDifference;
    NotificationManager notificationManager;
    NotificationCompat.Builder mBuilder;
    public Receiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        System.out.println("alarm running");
        balanceDifference=0.0;
        requestQueue = Volley.newRequestQueue(context);
        dbHandler = new DBHandler(context, null, null, 1);
        purchasedCompOverviewsList = dbHandler.selectAllPurchasedCompOverview();

         notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
         mBuilder = new NotificationCompat.Builder(context );
        for(PurchasedCompOverview purchasedCompOverview: purchasedCompOverviewsList){
            getCompDetailsLatestPrice(purchasedCompOverview, context, intent);
        }



    }



    private void getCompDetailsLatestPrice(final PurchasedCompOverview purchasedCompOverview,final Context context,final Intent intent) {
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
                            getBalanceDifference();
                            setNotification(context, intent);
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

    public void getBalanceDifference(){
        Double totalDifference=0.0;
        for(PurchasedCompOverview purchasedCompOverview: purchasedCompOverviewsList){
            totalDifference= purchasedCompOverview.getLatestPrice()-purchasedCompOverview.getPurchasePrice();
            totalDifference = totalDifference*purchasedCompOverview.getAmount();
            balanceDifference+=totalDifference;
        }
    }

    public void setNotification(Context context, Intent intent) {
         notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, MyPurchases.class), PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder.setSmallIcon(R.drawable.icon_wallet)
                .setContentTitle("StockMarketApp")
                .setContentText("Balance can be changed by "+ balanceDifference)
                .setDefaults(Notification.DEFAULT_ALL)
                .setContentIntent(pendingIntent)
                .setPriority(Notification.PRIORITY_MAX)
                .setAutoCancel(true);
        notificationManager.notify(100, mBuilder.build());
    }

}