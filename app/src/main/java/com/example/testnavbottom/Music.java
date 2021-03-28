package com.example.testnavbottom;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaParser;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import java.security.Provider;

import static androidx.core.app.NotificationCompat.PRIORITY_MIN;

public class Music extends Service {
    private static final int ID_SERVICE = 101;
@Nullable
    @Override
    public IBinder onBind(Intent intent){
    return null;
}
    @Override
    public int onStartCommand(Intent intent,int flags,int startId){
        Log.d("on music", "im on music ");

        MediaPlayer mediaPlayer;
        mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.iphone_alarm_morning);
        mediaPlayer.start();


        return START_NOT_STICKY;


    }


    public void onCreate() {
        super.onCreate();



/*
        // do stuff like register for BroadcastReceiver, etc.

        // Create the Foreground Service
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String channelId = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ? createNotificationChannel(notificationManager) : "";

        Intent repeating_intent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent pendingIntent =PendingIntent.getActivity(getApplicationContext(),100,repeating_intent,PendingIntent.FLAG_UPDATE_CURRENT);



        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId);


        Notification notification = notificationBuilder.setOngoing(false)
                .setContentTitle("this is a content title")
                .setAutoCancel(true)
                .setContentText("this is a text of content")
                .addAction(R.drawable.ic_baseline_edit_24,"small title",null)
                .setShowWhen(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)

                .setContentIntent(pendingIntent)

                .build();

        startForeground(ID_SERVICE, notification);
        */
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private String createNotificationChannel(NotificationManager notificationManager){
        String channelId = "my_service_channelid";
        String channelName = "My Foreground Service";
        NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);

        channel.setImportance(NotificationManager.IMPORTANCE_NONE);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        notificationManager.createNotificationChannel(channel);
        return channelId;
    }

}
