package com.example.testnavbottom;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class recycleViewAdapter extends RecyclerView.Adapter<recycleViewAdapter.myViewHolder> {
    private ArrayList<Classview> myArraylist;

    public static class myViewHolder extends RecyclerView.ViewHolder {
        TextView Dtime;
        TextView Dinfo;
        TextView Ddate;
        TextView Ddateweek;
        TextView Did;
        TextView Ddesc;

        TextView tvtimeend;
        TextView tvTime;


        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            Dtime = itemView.findViewById(R.id.tv1);
            Dinfo = itemView.findViewById(R.id.tv2);
            Ddate = itemView.findViewById(R.id.date);
            Ddateweek = itemView.findViewById(R.id.dateweek);
            Did = itemView.findViewById(R.id.tvidOfevent);
            Ddesc = itemView.findViewById(R.id.tvInfo);

            tvtimeend = itemView.findViewById(R.id.tvendat);
            tvTime = itemView.findViewById(R.id.tvsstartAt);

        }
    }

    public recycleViewAdapter(ArrayList<Classview> myArraylist) {
        this.myArraylist = myArraylist;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardviewlayout, parent, false);
        myViewHolder myViewHolder = new myViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        Classview currentClassview = myArraylist.get(position);
        String time = currentClassview.getStartat();
        String title = currentClassview.getTitle();
        String desc = currentClassview.getDescr();
        int id = currentClassview.getId();
        String weekday = dayName(getdate(currentClassview.getStartat(), 0), getdate(currentClassview.getStartat(), 1), getdate(currentClassview.getStartat(), 2));

        String date = getdate(currentClassview.getStartat(), 2);

        if (currentClassview.getType() == 0) {
            holder.Dinfo.setBackgroundColor(Color.parseColor("#5a1363"));

            holder.Ddesc.setBackgroundColor(Color.parseColor("#5a1363"));
        }
        String timeend = currentClassview.getEndat();
        holder.tvtimeend.setText(timeend);
        holder.Ddate.setText(date);
        holder.Ddateweek.setText(weekday);
        holder.Dtime.setText(getTime(time, 0) + ":" + getTime(time, 1) + "-" + getTime(timeend, 0) + ":" + getTime(timeend, 1));
        holder.Dinfo.setText(title);
        holder.tvTime.setText(time);
        holder.Did.setText(String.valueOf(id));
        holder.Ddesc.setText(desc);

    }


    public String getdate(String rawdate, int opt) {

        // rawdate = "2020-08-24 09:30:00";
        Pattern pattern = Pattern.compile("(?<=^)[\\s\\S]*?(?= )");
        ArrayList<String> tasks = new ArrayList<String>();
        Matcher matcher = pattern.matcher(rawdate);
        String month = "0";
        while (matcher.find()) {
            month = matcher.group(0);
        }
        String[] arrOfStr = month.split("-", 3);
        if (arrOfStr[opt].length() < 2) {
            return "0" + arrOfStr[opt];
        }
        return arrOfStr[opt];
    }


    String getTime(String src, int opt) {
        // rawdate = "2020-08-24 09:30:00";
        Pattern pattern = Pattern.compile("(?<= )[\\s\\S]*?(?=$)");
        ArrayList<String> tasks = new ArrayList<String>();
        Matcher matcher = pattern.matcher(src);
        String month = "0";
        while (matcher.find()) {
            month = matcher.group(0);
        }
        String[] arrOfStr = month.split(":", 3);
        if (arrOfStr[opt].length() < 2) {
            return "0" + arrOfStr[opt];
        }
        return arrOfStr[opt];

    }

    public String dayName(String year, String month, String day) {
        String time2 = year + "-" + month + "-" + String.valueOf(Integer.parseInt(day));  // Start date
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c2 = Calendar.getInstance();
        try {
            c2.setTime(sdf2.parse(time2));
        } catch (Exception e) {

        }

        SimpleDateFormat sdf3 = new SimpleDateFormat("EEEE");
        String date = sdf3.format(c2.getTime());

        String realdayname = "err";

/*
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
            case "Thứ Sáu":
                realdayname="T6";
                break;
            case "Thứ Bảy":
                realdayname="T7";
                break;
            case "Chủ Nhật":
                realdayname="CN";
                break;

        }
*/

        switch (date) {
            case "Monday":
                realdayname = "T2";
                break;
            case "Tuesday":
                realdayname = "T3";
                break;
            case "Wednesday":
                realdayname = "T4";
                break;
            case "Thursday":
                realdayname = "T5";
                break;
            case "Friday":
                realdayname = "T6";
                break;
            case "Saturday":
                realdayname = "T7";
                break;
            case "Sunday":
                realdayname = "CN";
                break;

        }

        return realdayname;
    }


    @Override
    public int getItemCount() {
        return myArraylist.size();
    }

}
