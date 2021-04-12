package com.example.testnavbottom;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class notiReceiver extends BroadcastReceiver {

    void displayNoti() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("im in", "in noti receiver");

        Bundle extras = intent.getExtras();

    }

}
