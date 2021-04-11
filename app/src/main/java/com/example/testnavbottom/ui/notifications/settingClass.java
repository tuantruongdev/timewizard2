package com.example.testnavbottom.ui.notifications;

import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.SystemClock;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.testnavbottom.MainActivity;
import com.example.testnavbottom.R;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static com.example.testnavbottom.MainActivity.getContext;

public class settingClass extends AppCompatActivity {
    TextView tvTime;
    SeekBar seekBar;
    Spinner spinner;
    @Override
    protected void onDestroy() {
        super.onDestroy();
        save(String.valueOf(seekBar.getProgress()*5));
        save2(String.valueOf(spinner.getSelectedItemId()));

    }
    public String load() {
        String ret = "";

        try {
            InputStream inputStream = getApplicationContext().openFileInput("ringtone.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append("\n").append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {

        } catch (IOException e) {

        }

        return ret;

    }
    public String load2() {
        String ret = "";

        try {
            InputStream inputStream = getApplicationContext().openFileInput("settings.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append("\n").append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {

        } catch (IOException e) {

        }

        return ret;

    }
    public void save(String text) {


        FileOutputStream fos = null;
        try {
            fos = getApplicationContext().openFileOutput("settings.txt", MODE_PRIVATE);
            fos.write(text.getBytes());



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void save2(String text) {


        FileOutputStream fos = null;
        try {
            fos = getApplicationContext().openFileOutput("ringtone.txt", MODE_PRIVATE);
            fos.write(text.getBytes());



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
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
        setContentView(R.layout.setting_layout);




       spinner = findViewById(R.id.spinerRingtone);

        List<String> categories = new ArrayList<String>();
        categories.add("Reality");
        categories.add("ios Ringtone");
        categories.add("Gac lai au lo - Dalab");
        categories.add("Tiec tra sao - 星茶会");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getApplicationContext(),R.layout.spinner_layout, categories);

    //    dataAdapter.setDropDownViewResource(R.layout.spinner_layout);

       spinner.setAdapter(dataAdapter);
        String ringtone=load();
        ringtone=ringtone.replace("\n","");
        if(ringtone.compareTo("")!=0) {

            if (ringtone.compareTo("0") == 0) {
                spinner.setSelection(0);

            }
            if (ringtone.compareTo("1") == 0) {
                spinner.setSelection(1);
            }
            if (ringtone.compareTo("2") == 0) {
                spinner.setSelection(2);
            }
            if (ringtone.compareTo("3") == 0) {
                spinner.setSelection(3);
            }
        }       else {

            spinner.setSelection(3);
        }





       tvTime=findViewById(R.id.tvtime);
        seekBar = findViewById(R.id.seekbar1);
        seekBar.setMax(24);
        String progess =load2();
        progess =progess.replace("\n","");
        if (progess.compareTo("")!=0){
            seekBar.setProgress(Integer.valueOf(progess)/5);
            tvTime.setText(   Integer.valueOf(progess)  +" Phút");
        }
        else {
            seekBar.setProgress(3);

        }


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                tvTime.setText(   seekBar.getProgress()*5 +" Phút");


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });









    }
}
