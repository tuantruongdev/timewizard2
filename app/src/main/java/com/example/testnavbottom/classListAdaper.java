package com.example.testnavbottom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.example.testnavbottom.ui.home.HomeFragment;

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
int currentAddView=0;
    String eventTitle;
    String eventDesc;
    String eventStartAt;

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
*/



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




    public void displayAlertDialog(View convertview) {


        TextView tvTitle= convertview.findViewById(R.id.tv2);
        TextView tvDesc= convertview.findViewById(R.id.tvInfo);
        TextView tvTime= convertview.findViewById(R.id.tv1);
        TextView tvID= convertview.findViewById(R.id.tvidOfevent);


        LayoutInflater inflater =LayoutInflater.from(mContext);
     //   LayoutInflater inflater = getLayoutInflater();


        View alertLayout = inflater.inflate(R.layout.addeventpicker, null);
        TimePicker timePicker =alertLayout.findViewById(R.id.datePicker1);
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        TextView textViewtime=alertLayout.findViewById(R.id.timetext);
        DatePicker realdatepicker= alertLayout.findViewById(R.id.realdatepicker);
        realdatepicker.setVisibility(View.GONE);
        timePicker.setIs24HourView(true);


        timePicker.setHour(Integer.parseInt(getTime(tvTime.getText().toString(),0)));
        timePicker.setMinute(Integer.parseInt(getTime(tvTime.getText().toString(),1)));
        int year=Integer.parseInt(getdate(tvTime.getText().toString(),0));
        int month=Integer.parseInt(getdate(tvTime.getText().toString(),1))-1;
        int date=Integer.parseInt(getdate(tvTime.getText().toString(),2));
        realdatepicker.init(year,month,date,null);


        textViewtime.setText(timePicker.getHour()+":"+timePicker.getMinute());
        Space space=alertLayout.findViewById(R.id.blankspace);



        EditText edtTitle= alertLayout.findViewById(R.id.edteventTitle);
        edtTitle.setText(tvTitle.getText());


        EditText edtDesc= alertLayout.findViewById(R.id.edteventDesc);
        edtDesc.setText(tvDesc.getText());



        edtTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });




        alert.setTitle("Sửa thông tin Sự Kiện");
        alert.setView(alertLayout);
        alert.setCancelable(false);

        AlertDialog dialog = alert.create();
        dialog.show();
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                textViewtime.setText(timePicker.getHour()+":"+timePicker.getMinute());
            }
        });
        RelativeLayout rev1= alertLayout.findViewById(R.id.relativeTimepicker);
        RelativeLayout rev2= alertLayout.findViewById(R.id.relaAddtext);
        Button btnNext= alertLayout.findViewById(R.id.btnNext);
        Button btnBack= alertLayout.findViewById(R.id.btnBack);
        final float scale = getContext().getResources().getDisplayMetrics().density;
        int pixels = (int) (50 * scale + 0.5f);
        Button btnFinish= alertLayout.findViewById(R.id.btnFinish);
        Button btnBack2= alertLayout.findViewById(R.id.btnBacktoTimepicker);
        TextView timeDisplayOnaddtitle=alertLayout.findViewById(R.id.datetimeTV);
        btnBack2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentAddView==2){
                    rev1.setVisibility(View.VISIBLE);
                    rev2.setVisibility(View.GONE);
                    currentAddView=1;

                }



            }
        });


        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                eventTitle = edtTitle.getText().toString();
                eventDesc =edtDesc.getText().toString();
              DatabaseHelper mydb = new DatabaseHelper(getContext());
              mydb.updateProduct(new Classview(eventTitle,1,eventDesc,eventStartAt,eventStartAt,0,eventStartAt,"no note","T2",Integer.parseInt(tvID.getText().toString())));
            //    mydb.insertProduct(new Classview(eventTitle,1,eventDesc,eventStartAt,"0",0,eventStartAt,"no note","T2",0 ));
                dialog.cancel();
                currentAddView=0;
//  public Classview(String title, int enable, String descr, String startat, String endat, int type, String alarmat, String note,String weekday,int Id) {

            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (currentAddView==1){
                    rev1.setVisibility(View.GONE);
                    rev2.setVisibility(View.VISIBLE);
                    currentAddView=2;

                    String date= String.valueOf(realdatepicker.getDayOfMonth());
                    String month=String.valueOf(realdatepicker.getMonth()+1);;
                    if (realdatepicker.getDayOfMonth()<10){
                        date= "0"+String.valueOf(realdatepicker.getDayOfMonth());
                    }

                    if (realdatepicker.getMonth()+1<10){
                        month= "0"+String.valueOf(realdatepicker.getMonth()+1);
                    }


                    timeDisplayOnaddtitle.setText(timePicker.getHour()+":"+timePicker.getMinute()+"  "+date+"/"+ month +"/"+realdatepicker.getYear());

                    //need to add "0" char


                  eventStartAt=realdatepicker.getYear()+"-"+  month +"-"+date+" "+timePicker.getHour()+":"+timePicker.getMinute()+":00";

                }
                if (currentAddView==0){
                    //set buttons under calendar
                    timePicker.setVisibility(View.GONE);
                    realdatepicker.setVisibility(View.VISIBLE);
                    RelativeLayout.LayoutParams lp =
                            new RelativeLayout.LayoutParams
                                    (
                                            RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT
                                    );
                    lp.setMargins(pixels, 0, 0, 0);
                    lp.addRule(RelativeLayout.BELOW,realdatepicker.getId());
                    lp.addRule(RelativeLayout.RIGHT_OF,space.getId());



                    btnNext.setLayoutParams(lp);

                    RelativeLayout.LayoutParams lpBack =
                            new RelativeLayout.LayoutParams
                                    (
                                            RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT
                                    );
                    lpBack.addRule(RelativeLayout.BELOW,realdatepicker.getId());
                    lpBack.addRule(RelativeLayout.LEFT_OF,space.getId());
                    lpBack.setMargins(0, 0, pixels, 0);
                    btnBack.setLayoutParams(lpBack);
                    currentAddView=1;
                }
            }
        });



        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currentAddView==0){
                    dialog.cancel();
                }
                if (currentAddView==1) {
                    timePicker.setVisibility(View.VISIBLE);
                    realdatepicker.setVisibility(View.GONE);
                    RelativeLayout.LayoutParams lp =
                            new RelativeLayout.LayoutParams
                                    (
                                            RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT
                                    );
                    lp.setMargins(pixels, 0, 0, 0);
                    lp.addRule(RelativeLayout.BELOW, textViewtime.getId());
                    lp.addRule(RelativeLayout.RIGHT_OF, space.getId());


                    btnNext.setLayoutParams(lp);

                    RelativeLayout.LayoutParams lpBack =
                            new RelativeLayout.LayoutParams
                                    (
                                            RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT
                                    );
                    lpBack.addRule(RelativeLayout.BELOW, textViewtime.getId());
                    lpBack.addRule(RelativeLayout.LEFT_OF, space.getId());
                    lpBack.setMargins(0, 0, pixels, 0);
                    btnBack.setLayoutParams(lpBack);
                    currentAddView=0;
                }
            }
        });



    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String time =getItem(position).getStartat();
        String title=getItem(position).getTitle();
        String desc=getItem(position).getDescr();
        int id=getItem(position).getId();
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
        TextView Did = convertView.findViewById(R.id.tvidOfevent);
        TextView Ddesc= convertView.findViewById(R.id.tvInfo);
        View finalConvertView = convertView;
        Dinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             displayAlertDialog(finalConvertView);
            }
        });

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
        Did.setText(String.valueOf(id));
        Ddesc.setText(desc);
        return  convertView;
    }
}
