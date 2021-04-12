package com.example.testnavbottom;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.testnavbottom.ui.dashboard.DashboardFragment;
import com.example.testnavbottom.ui.home.HomeFragment;
import com.example.testnavbottom.ui.notifications.NotificationsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    public static Context context = getContext();
    DatabaseHelper mDatabasehelper;

    public static Context getContext() {
        return context;
    }



    public void refresh() {
        ViewGroup vg = (ViewGroup) findViewById(R.id.nav_host_fragment_container);
        vg.removeAllViews();
        vg.refreshDrawableState();
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

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

        super.onCreate(savedInstanceState);
        //   setContentView(R.layout.activity_main);

        //    this.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_main);
        // this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // getSupportActionBar().hide();

        BottomNavigationView navView = findViewById(R.id.nav_view);

        navView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new HomeFragment()).commit();

        Intent intent = getIntent();

        String action = intent.getStringExtra("action");
        if (action != null) {
            if (action.equals("alarm")) {
                navView.setSelectedItemId(R.id.navigation_dashboard);
            }
            if (action.equals("dashboard")) {
                navView.setSelectedItemId(R.id.navigation_notifications);
            }

        } else {
            navView.setSelectedItemId(R.id.navigation_home);

        }

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    selectedFragment = new HomeFragment();
                    break;
                case R.id.navigation_dashboard:
                    selectedFragment = new DashboardFragment();
                    break;
                case R.id.navigation_notifications:
                    selectedFragment = new NotificationsFragment();
                    break;

            }
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, selectedFragment).commit();
            return true;

        }
    };

}