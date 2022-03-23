package com.example.studentapplication.UI;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.studentapplication.R;

public class MyReceiver extends BroadcastReceiver {
    String channel_id="test";
    static int notificationID;

    @Override
    public void onReceive(Context context, Intent intent) {
        createNotificationChannel(context, channel_id);
        Notification n = new NotificationCompat.Builder(context,channel_id)
            .setSmallIcon(R.drawable.ic_baseline_school_24)
                .setContentText(intent.getStringExtra("key"))
                .setContentTitle("Notification Test").build();
        NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(notificationID++, n);
    }
    private void createNotificationChannel(Context context, String CHANNEL_ID){
        CharSequence name = context.getResources().getString(R.string.channel_name);
        String description = context.getString(R.string.channel_description);
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID,name,importance);
        channel.setDescription(description);
        //register the channel with system
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }




}