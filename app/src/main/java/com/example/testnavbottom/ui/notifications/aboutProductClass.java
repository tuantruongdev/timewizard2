package com.example.testnavbottom.ui.notifications;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.testnavbottom.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class aboutProductClass extends AppCompatActivity {


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
        setContentView(R.layout.aboutproduct_layout);

        FloatingActionButton btnback = findViewById(R.id.btnBackAbout);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}