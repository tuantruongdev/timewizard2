package com.example.testnavbottom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class classListAdaper extends ArrayAdapter<Classview> {
    private  Context mContext;
    int mRes;
    public classListAdaper(@NonNull Context context, int resource, @NonNull ArrayList<Classview> objects) {
        super(context, resource, objects);
        mContext=context;
        mRes=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String time =getItem(position).getTime();
        String info=getItem(position).getInfo();
        String weekday=getItem(position).getWeekday();
        String date =getItem(position).getDate();
        Classview mClass= new Classview(time,info,weekday,date);
        LayoutInflater inflater =LayoutInflater.from(mContext);
        convertView = inflater.inflate(mRes,parent,false);
        TextView Dtime = convertView.findViewById(R.id.tv1);
        TextView Dinfo = convertView.findViewById(R.id.tv2);
        TextView Ddate = convertView.findViewById(R.id.date);
        TextView Ddateweek = convertView.findViewById(R.id.dateweek);

        Ddate.setText(date);
        Ddateweek.setText(weekday);
        Dtime.setText(time);
        Dinfo.setText(info);
        return  convertView;
    }
}
