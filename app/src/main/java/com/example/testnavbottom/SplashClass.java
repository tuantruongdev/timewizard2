package com.example.testnavbottom;



import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.SystemClock;
import android.view.Window;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SplashClass extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int nightModeFlags =
                this.getBaseContext().getResources().getConfiguration().uiMode &
                        Configuration.UI_MODE_NIGHT_MASK;
        switch (nightModeFlags) {
            case Configuration.UI_MODE_NIGHT_YES:
                this.requestWindowFeature(Window.FEATURE_NO_TITLE);
                getSupportActionBar().hide();
                break;

            case Configuration.UI_MODE_NIGHT_NO:
                this.requestWindowFeature(Window.FEATURE_NO_TITLE);

                break;

            case Configuration.UI_MODE_NIGHT_UNDEFINED:

                break;
        }

        setContentView(R.layout.spashscreen);

        android.os.Handler handler = new Handler();




        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent= new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }


        }, 3000);






    }
}
