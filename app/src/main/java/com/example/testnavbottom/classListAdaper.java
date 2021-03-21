package com.example.testnavbottom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.testnavbottom.R.id.date;
import static com.example.testnavbottom.R.id.fade;
import static com.example.testnavbottom.R.id.swEnableEvent;






public class classListAdaper extends ArrayAdapter<Classview> {
    private  Context mContext;
    int mRes;



//THIS FUNCTION IS HUGEEEEEEEEEEEEEE ERROR!!!
    public  String dayName(String year,String month,String day){
        String time2 = year+"-"+month+"-"+String.valueOf(Integer.parseInt(day)) ;  // Start date
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c2 = Calendar.getInstance();
        try {
            c2.setTime(sdf2.parse(time2));
        }catch (Exception e){

        }

        SimpleDateFormat sdf3 = new SimpleDateFormat("EEEE");
        String date= sdf3.format(c2.getTime());









                String realdayname= "err";


        switch (date){
            case "Thứ Hai":
                realdayname="T2";
                break;
            case "Thứ Ba":
                realdayname="T3";
                break;
            case "Thứ Tư":
                realdayname="T4";
                break;
            case "Thứ Năm":
                realdayname="T5";
                break;
            case "Thứ 6":
                realdayname="T6";
                break;
            case "Thứ 7":
                realdayname="T7";
                break;
            case "Chủ Nhật":
                realdayname="CN";
                break;

        }



        /*
        switch (date){
            case "Monday":
                realdayname="T2";
                break;
            case "Tuesday":
                realdayname="T3";
                break;
            case "Wednesday":
                realdayname="T4";
                break;
            case "Thursday":
                realdayname="T5";
                break;
            case "Friday":
                realdayname="T6";
                break;
            case "Saturday":
                realdayname="T7";
                break;
            case "Sunday":
                realdayname="CN";
                break;

        }
*/


        return realdayname;
    }
    public classListAdaper(@NonNull Context context, int resource, @NonNull ArrayList<Classview> objects) {
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
        return arrOfStr[opt];
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String time =getItem(position).getStartat();
        String title=getItem(position).getTitle();

        String weekday= dayName(getdate(getItem(position).getStartat(),0),getdate(getItem(position).getStartat(),1),getdate(getItem(position).getStartat(),2));

      String date =getdate(getItem(position).getStartat(),2);
        //String date =getItem(position).getStartat();
      //  Classview mClass= new Classview(time,info,weekday,date);
        LayoutInflater inflater =LayoutInflater.from(mContext);
        convertView = inflater.inflate(mRes,parent,false);
        TextView Dtime = convertView.findViewById(R.id.tv1);
        TextView Dinfo = convertView.findViewById(R.id.tv2);
        TextView Ddate = convertView.findViewById(R.id.date);
        TextView Ddateweek = convertView.findViewById(R.id.dateweek);

        Switch sw = convertView.findViewById(swEnableEvent);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Dinfo.setBackgroundColor(Color.parseColor("#FF018786"));
                }else {
                    Dinfo.setBackgroundColor(Color.parseColor("#7e827f"));
                }

            }
        });


        //Dinfo.setText( sw.toString());


        Ddate.setText(date);
        Ddateweek.setText(weekday);
        Dtime.setText(time);
        Dinfo.setText(title);
        return  convertView;
    }
}
