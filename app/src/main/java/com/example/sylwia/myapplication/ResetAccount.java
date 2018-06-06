package com.example.sylwia.myapplication;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.sylwia.myapplication.CompList.MostActiveList;
import com.example.sylwia.myapplication.MyPurchases.MyPurchases;
import com.example.sylwia.myapplication.Notifications.MyNotifications;

public class ResetAccount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_account);

    }

    public void onResetAccount(View view) {
        DBHandler dbHandler= new DBHandler(this, null, null, 1);
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        dbHandler.updateShema(db);
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
            case R.id.menu_reset_account:{
                if (item.isChecked())
                    item.setChecked(false);
                else
                    item.setChecked(true);
                startActivity(new Intent(this, ResetAccount.class));
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
