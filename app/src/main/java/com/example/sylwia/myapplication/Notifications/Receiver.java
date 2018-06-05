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

import com.example.sylwia.myapplication.CompList.MostActiveList;
import com.example.sylwia.myapplication.MainActivity;
import com.example.sylwia.myapplication.MyPurchases.MyPurchases;
import com.example.sylwia.myapplication.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Sylwia on 6/4/2018.
 */

public class Receiver  extends BroadcastReceiver {

    public Receiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        System.out.println("alarm running");

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, MyPurchases.class), PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context )
                .setSmallIcon(R.drawable.icon_wallet)
                .setContentTitle("My notification")
                .setContentText("Hello World!")
       .setDefaults(Notification.DEFAULT_ALL)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setPriority(Notification.PRIORITY_MAX)
                .setAutoCancel(true);
        notificationManager.notify(100, mBuilder.build());
    }
}