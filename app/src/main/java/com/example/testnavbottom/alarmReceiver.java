package com.example.testnavbottom;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.SystemClock;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.testnavbottom.ui.dashboard.DashboardFragment;

public class alarmReceiver extends BroadcastReceiver {

    void displayNoti(){




    }

    @Override
    public void onReceive(Context context , Intent intent){
        Log.d("im in","in receiver");

        String extra=intent.getExtras().getString("extra");

            Intent myIntent= new Intent(context,Music.class);
            myIntent.putExtra("extra",extra);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(myIntent);
        } else {
            context.startService(myIntent);
        }



/*

        String channelId = "Your_channel_id";
        NotificationManager notificationManager= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent repeating_intent = new Intent(context, MainActivity.class);
        repeating_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent =PendingIntent.getActivity(context,100,repeating_intent,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder= new NotificationCompat.Builder(
                context
        ).setSmallIcon(R.drawable.ic_baseline_calendar_today_24)
                .setContentTitle("this is a content title")
                .setAutoCancel(true)
                .setContentText("this is a text of content")
                .addAction(R.drawable.ic_baseline_edit_24,"small title",null)
                .setShowWhen(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                ;




        NotificationChannel channel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            channel = new NotificationChannel(
                    channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }




        builder.setChannelId(channelId);



        notificationManager.notify(1,builder.build());



        
        MediaPlayer mediaPlayer;
        mediaPlayer = MediaPlayer.create(context,R.raw.iphone_alarm_morning);
        mediaPlayer.start();



        SystemClock.sleep(17000);
*/

    }

}
