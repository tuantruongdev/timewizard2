package com.example.testnavbottom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.testnavbottom.R.id.swEnableEvent;

public class classlistAlarm_adapter extends ArrayAdapter<Classview> {
    private  Context mContext;
    int mRes;

    public classlistAlarm_adapter(@NonNull Context context, int resource, @NonNull ArrayList<Classview> objects) {
        super(context, resource, objects);
        mContext=context;
        mRes=resource;
    }



    public String getdate(String rawdate,int opt){

        // rawdate = "2020-08-24 09:30:00";
        Pattern pattern = Pattern.compile("(?<=^)[\\s\\S]*?(?= )");
        ArrayList<String> tasks = new ArrayList<String>();
        Matcher matcher = pattern.matcher(rawdate);
        String month="0";
        while (matcher.find()) {
            month= matcher.group(0);
        }
        String[] arrOfStr = month.split("-", 3);
        if (arrOfStr[opt].length()<2){
            return  "0"+arrOfStr[opt];
        }
        return  arrOfStr[opt];
    }


    String getTime(String src,int opt){
        // rawdate = "2020-08-24 09:30:00";
        Pattern pattern = Pattern.compile("(?<= )[\\s\\S]*?(?=$)");
        ArrayList<String> tasks = new ArrayList<String>();
        Matcher matcher = pattern.matcher(src);
        String month="0";
        while (matcher.find()) {
            month= matcher.group(0);
        }
        String[] arrOfStr = month.split(":", 3);
        if (arrOfStr[opt].length()<2){
            return  "0"+arrOfStr[opt];
        }
        return  arrOfStr[opt];




    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String time =getItem(position).getStartat();
        String hour = getTime(time,0)+":"+getTime(time,1);
        String title=getItem(position).getTitle();
        String desc=getItem(position).getDescr();
        String chuky=getItem(position).getNote();
        int id=getItem(position).getId();





        //String date =getItem(position).getStartat();
        //  Classview mClass= new Classview(time,info,weekday,date);
        LayoutInflater inflater =LayoutInflater.from(mContext);
        convertView = inflater.inflate(mRes,parent,false);
        TextView wakeuptime= convertView.findViewById(R.id.Timeview);
        TextView sleeptime= convertView.findViewById(R.id.startSleepTime);
        TextView descview= convertView.findViewById(R.id.descview);
        Switch swalamr= convertView.findViewById(R.id.swalarm);
        TextView chukyTv=convertView.findViewById(R.id.chuky);


        wakeuptime.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //    displaydeleteopts(finalConvertView);
                return false;
            }
        });


        wakeuptime.setText(hour);
        descview.setText(desc);

        DateFormat dateFormat=new SimpleDateFormat("hh:mm:ss");
        Date date1 = null;
        try {
            date1 = dateFormat.parse("06:40:10");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //time start at 07:00
        Date date2 = null;
        try {
            date2 = dateFormat.parse(hour+":00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date date3 = null;
        try {
            date3 = dateFormat.parse("00:00:01");
        } catch (ParseException e) {
            e.printStackTrace();
        }


        long diff =date2.getTime()-( 5400000* (Integer.parseInt(chuky)+2) +840000);

        long sodu=     86400000+diff;

        if (sodu<0){


            sodu =86400000+sodu;

        }



        date3.setTime(sodu);

        chukyTv.setText("("+ String.valueOf(Integer.parseInt(chuky)+2) +" chu kỳ)");
        sleeptime.setText(String.valueOf(date3.getHours()+":"+date3.getMinutes()));




        return  convertView;
    }
}



