package com.example.sylwia.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.sylwia.myapplication.CompList.CompOverview;
import com.example.sylwia.myapplication.CompList.MostActiveList;
import com.example.sylwia.myapplication.MyPurchases.PurchasedComp;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Chart extends AppCompatActivity {
    String url;
    RequestQueue requestQueue;

    DBHandler dbHandler;
    PurchasedComp purchasedComp;
    GraphView graph;
    LineGraphSeries<DataPoint> series;
    List<DataPoint> dataPointArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        dbHandler = new DBHandler(this, null, null, 1);
        requestQueue = Volley.newRequestQueue(this);
        Intent intent = getIntent();
        String symbol = intent.getStringExtra(MostActiveList.compSymbol);
        dataPointArray= new ArrayList<>();
         graph = (GraphView) findViewById(R.id.graph);
        getChart(symbol);
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this));



    }

    private void getChart(final String symbol) {
        this.url = "https://api.iextrading.com/1.0/stock/"+symbol+"/chart/1m";

        JsonArrayRequest arrReq = new JsonArrayRequest(Request.Method.GET, url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObj = response.getJSONObject(i);
                                String date = jsonObj.get("date").toString();
                                String close = jsonObj.get("close").toString();
                                System.out.println(date);
                                System.out.println(close);
                                System.out.println(Date.valueOf(date));
                                DataPoint dataPoint= new DataPoint(Date.valueOf(date), Double.parseDouble(close));
                                dataPointArray.add(dataPoint);

                            } catch (JSONException e) {
                                Log.e("Volley", "Invalid JSON Object.");
                            }
                        }
                        DataPoint[] dataPoints = new DataPoint[dataPointArray.size()];
                        dataPointArray.toArray(dataPoints);
                        series= new LineGraphSeries<>(dataPoints);
                        series.setAnimated(true);
                        series.setTitle(symbol);
                        graph.getLegendRenderer().setVisible(true);
                        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.BOTTOM);
                        graph.addSeries(series);
                        graph.getViewport().setMinX(( dataPoints[0].getX()));
                        graph.getViewport().setMaxX(dataPoints[dataPointArray.size()-1].getX());
                        graph.getViewport().setXAxisBoundsManual(true);
                        graph.getGridLabelRenderer().setNumHorizontalLabels(3);
                        graph.getGridLabelRenderer().setHumanRounding(false);

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
