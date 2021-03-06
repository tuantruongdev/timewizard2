package com.example.testnavbottom;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Music extends Service {
    MediaPlayer mediaPlayer;
    String newid = "";

    public String load() {
        String ret = "";

        try {
            InputStream inputStream = getApplicationContext().openFileInput("ringtone.txt");

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append("\n").append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;

    }

    private static final int ID_SERVICE = 101;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("on music", "im on music ");
        int id = 0;

        Bundle extras = intent.getExtras();

        String keymedia = extras.getString("extra");
        String getnewID = extras.getString("newid");
        String neededid = extras.getString("neededid");
        String title = extras.getString("title");
        String desc = extras.getString("desc");

        if (getnewID != null) {

            if (!getnewID.equals(" "))
                newid = getnewID;

        }

        if (keymedia.equals("on")) {
            id = 1;
        }
        if (keymedia.equals("off")) {
            id = 0;

        }

        if (id == 1) {

            String channelId = "Your_channel_id";
            NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

            Intent repeating_intent = new Intent(getApplicationContext(), MainActivity.class);
            repeating_intent.putExtra("action", "alarm");

            repeating_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 100, repeating_intent, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(
                    getApplicationContext()
            ).setSmallIcon(R.drawable.ic_baseline_calendar_today_24)
                    .setContentTitle(title)
                    .setAutoCancel(true)
                    .setContentText(desc)

                    .setShowWhen(true)

                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentIntent(pendingIntent);

            NotificationChannel channel = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                channel = new NotificationChannel(
                        channelId,
                        "Channel human readable title",
                        NotificationManager.IMPORTANCE_HIGH);
                notificationManager.createNotificationChannel(channel);
            }

            builder.setChannelId(channelId);

            //   notificationManager.notify(1,builder.build());
            String ringtone = load();
            ringtone = ringtone.replace("\n", "");
            if (ringtone.compareTo("") != 0) {

                if (ringtone.compareTo("0") == 0) {
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.iphone_alarm_morning);
                    mediaPlayer.start();
                }

                if (ringtone.compareTo("1") == 0) {
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.reality);
                    mediaPlayer.start();
                }
                if (ringtone.compareTo("2") == 0) {
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.gac_lai_au_lo);
                    mediaPlayer.start();
                }
                if (ringtone.compareTo("3") == 0) {
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.tiec_tra_sao);
                    mediaPlayer.start();
                }
            } else {
                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.tiec_tra_sao);
                mediaPlayer.start();

            }

            //  builder.build().flags |= Notification.FLAG_AUTO_CANCEL;

            SystemClock.sleep(2000);
            //  Notification notification = new Notification.Builder(getApplicationContext(),createNotificationChannel(notificationManager)).build();

            this.stopForeground(false);

            startForeground(1, builder.build());
            //
            return START_NOT_STICKY;
        }
        if (id == 0) {

            Log.d("trying to stop service", "1");
            Log.d("newid", newid);
            Log.d("needid", neededid);

            if (mediaPlayer != null) {

                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    mediaPlayer.reset();

                }
            }
        }

        //   stopSelf();

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("getting destroyed", "goodbye");

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
    private String createNotificationChannel(NotificationManager notificationManager) {
        String channelId = "my_service_channelid";
        String channelName = "My Foreground Service";
        NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);

        channel.setImportance(NotificationManager.IMPORTANCE_NONE);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        notificationManager.createNotificationChannel(channel);
        return channelId;
    }

}
