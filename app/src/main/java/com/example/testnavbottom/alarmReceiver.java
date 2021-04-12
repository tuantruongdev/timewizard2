package com.example.testnavbottom;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

public class alarmReceiver extends BroadcastReceiver {

    void displayNoti() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("im in", "in receiver");

        Bundle extras = intent.getExtras();
        Intent myIntent = new Intent(context, Music.class);

        myIntent.putExtras(extras);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(myIntent);
        } else {
            context.startService(myIntent);
        }

    }

}
