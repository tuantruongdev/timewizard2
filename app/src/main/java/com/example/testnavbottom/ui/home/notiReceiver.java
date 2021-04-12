package com.example.testnavbottom.ui.home;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.testnavbottom.DatabaseHelper;
import com.example.testnavbottom.MainActivity;
import com.example.testnavbottom.R;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static androidx.core.app.NotificationCompat.VISIBILITY_PUBLIC;

public class notiReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("im in", "in receiver");
        Intent clickIntent = new Intent(context, MainActivity.class);
        PendingIntent clickpendingIntent = PendingIntent.getActivity(context, 0, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        String title = intent.getStringExtra("title");
        String desc = intent.getStringExtra("desc");
        String id = intent.getStringExtra("id");
        String time = intent.getStringExtra("time");
        String ret = "";

        try {
            InputStream inputStream = context.openFileInput("settings.txt");

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

        } catch (IOException e) {

        }
        if (ret.compareTo("\n0") == 0 || ret.compareTo("") == 0) {
            title = "[" + time + "]" + title;

        } else {
            title = "[" + time + "/" + ret + " phút nữa] - " + title;

        }

        String channelId = "Channel_ID_6969";
        String message = "this is a message";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                context

        ).setSmallIcon(R.drawable.ic_baseline_calendar_today_24)
                .setContentTitle(title)
                .setAutoCancel(true)
                .setContentText(desc)
                .setVisibility(VISIBILITY_PUBLIC)
                .setShowWhen(true);
        Intent intentnew = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("message", message);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intentnew, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(
                Context.NOTIFICATION_SERVICE

        );

        NotificationChannel channel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            channel = new NotificationChannel(
                    channelId,
                    "Timewizard notifitcation channel",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        builder.setChannelId(channelId);

        notificationManager.notify(0, builder.build());
        DatabaseHelper mydb = new DatabaseHelper(context);
        mydb.updatenoitced(Integer.valueOf(id));

    }

}
