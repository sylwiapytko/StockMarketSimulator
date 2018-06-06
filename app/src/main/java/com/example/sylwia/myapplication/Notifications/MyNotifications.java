package com.example.sylwia.myapplication.Notifications;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.sylwia.myapplication.CompList.MostActiveList;
import com.example.sylwia.myapplication.MyPurchases.MyPurchases;
import com.example.sylwia.myapplication.R;

import java.util.Calendar;

public class MyNotifications extends AppCompatActivity {
    TimePicker timePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_my_notifications);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        timePicker=(TimePicker)findViewById(R.id.timePicker);

        timePicker.setCurrentHour(12);
        timePicker.setCurrentMinute(30);

    }

    public void onSetNotifications(View view) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (prefs.getBoolean("firstTime", false)) {
            Intent alarmIntent = new Intent(this, Receiver.class);
            System.out.println("set alarm");


            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
            calendar.set(Calendar.MINUTE,timePicker.getCurrentMinute());
            calendar.set(Calendar.SECOND, 0);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
            AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

            manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, pendingIntent);

            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", true);
            editor.apply();

            Toast.makeText(this, "Notifications set"+timePicker.getCurrentHour()+":"+ timePicker.getCurrentMinute(), Toast.LENGTH_SHORT).show();
        }
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


