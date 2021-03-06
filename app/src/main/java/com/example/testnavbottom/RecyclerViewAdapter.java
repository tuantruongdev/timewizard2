package com.example.testnavbottom;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.example.testnavbottom.ui.home.notiReceiver;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.ALARM_SERVICE;
import static com.example.testnavbottom.MainActivity.context;
import static com.example.testnavbottom.MainActivity.getContext;

public class RecyclerViewAdapter extends RecyclerSwipeAdapter<RecyclerViewAdapter.SimpleViewHolder> {
    int currentAddView = 0;
    String eventTitle;
    String eventDesc;
    String eventStartAt;

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        SwipeLayout swipeLayout;
        TextView textViewPos;
        TextView textViewData;
        ImageView buttonDelete;
        ImageView buttonedit;
        TextView Dtime;
        TextView Dinfo;
        TextView Ddate;
        TextView Ddateweek;
        TextView Did;
        TextView Ddesc;
        TextView daycolor;
        TextView tvtimeend;
        TextView tvTime;
        RelativeLayout myLayout;

        public SimpleViewHolder(View itemView) {
            super(itemView);
            Dtime = itemView.findViewById(R.id.tv1);
            Dinfo = itemView.findViewById(R.id.tv2);
            Ddate = itemView.findViewById(R.id.date);
            Ddateweek = itemView.findViewById(R.id.dateweek);
            Did = itemView.findViewById(R.id.tvidOfevent);
            Ddesc = itemView.findViewById(R.id.tvInfo);
            daycolor = itemView.findViewById(R.id.colormatch);
            tvtimeend = itemView.findViewById(R.id.tvendat);
            tvTime = itemView.findViewById(R.id.tvsstartAt);
            myLayout = itemView.findViewById(R.id.idrelalayout);

            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            textViewPos = (TextView) itemView.findViewById(R.id.position);
            // textViewData = (TextView) itemView.findViewById(R.id.text_data);
            buttonDelete = (ImageView) itemView.findViewById(R.id.trash);
            buttonedit = (ImageView) itemView.findViewById(R.id.edit);

        }
    }

    private Context mContext;
    private ArrayList<Classview> mDataset;

    //protected SwipeItemRecyclerMangerImpl mItemManger = new SwipeItemRecyclerMangerImpl(this);

    public RecyclerViewAdapter(Context context, ArrayList<Classview> objects) {
        this.mContext = context;
        this.mDataset = objects;
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        return new SimpleViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Boolean checkscroll2(String mydate) {

        Calendar cal1 = GregorianCalendar.getInstance();

        String mTempday4 = getdate(mydate, 2);

        String mTempmonth4 = getdate(mydate, 1);

        String mTempyear4 = getdate(mydate, 0);

        int mTempday5 = cal1.get(Calendar.DAY_OF_MONTH);

        int mTempmonth5 = cal1.get(Calendar.MONTH) + 1;
        int mTempyear5 = cal1.get(Calendar.YEAR);

        String mTempday6 = String.valueOf(mTempday5);
        String mTempmonth6 = String.valueOf(mTempmonth5);

        if (mTempday5 < 10) {
            mTempday6 = "0" + mTempday6;
        }
        if (mTempmonth5 < 10) {
            mTempmonth6 = "0" + mTempmonth6;
        }

        LocalDate dt1 = LocalDate.parse(mTempyear4 + "-" + mTempmonth4 + "-" + mTempday4);
        //  cal.add(Calendar.DATE,-1);
        LocalDate dt2 = LocalDate.parse(mTempyear5 + "-" + mTempmonth6 + "-" + mTempday6);
        //      dt2=   dt2.minusDays(1);
        //     dt2=   dt2.minusDays(1);

//Integer.parseInt(mTempday4)<Integer.parseInt(mTempday5)-1

        // Log.d("cac",cal.getTime().toString());
        //   dt1.isAfter(dt2)
        if (dt2.isAfter(dt1)) {

            return true;
        } else {
            return false;
        }
    }

    public Boolean checkscroll(String mydate) {

        Calendar cal1 = GregorianCalendar.getInstance();

        if (Integer.parseInt(getdate(mydate, 0)) == cal1.get(Calendar.YEAR) && Integer.parseInt(getdate(mydate, 1)) == cal1.get(Calendar.MONTH) + 1 && Integer.parseInt(getdate(mydate, 2)) == cal1.get(Calendar.DAY_OF_MONTH)) {
            return true;
        }

        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(final SimpleViewHolder viewHolder, final int position) {
        Classview item = mDataset.get(position);

        String time = item.getStartat();
        String title = item.getTitle();
        String desc = item.getDescr();
        int id = item.getId();
        String weekday = dayName(getdate(item.getStartat(), 0), getdate(item.getStartat(), 1), getdate(item.getStartat(), 2));

        String month = getdate(item.getStartat(), 1);
        String date = getdate(item.getStartat(), 2);

        if (item.getType() == 0) {
            // viewHolder.Dinfo.setBackgroundColor(Color.parseColor("#5a1363"));

            //viewHolder.Ddesc.setBackgroundColor(Color.parseColor("#5a1363"));
        }

        if (checkscroll(time)) {

            //  viewHolder.Ddate.setOutlineAmbientShadowColor(Color.parseColor("#cf1d1d"));
            viewHolder.Ddate.setShadowLayer(15, 0, 0, Color.parseColor("#cf1d1d"));
            viewHolder.Ddate.setTextColor(Color.parseColor("#CFFFCC00"));
            //   viewHolder.Dtime.setShadowLayer(15,0,0,Color.parseColor("#cf1d1d") );
            viewHolder.Dtime.setTextColor(Color.parseColor("#CFFFCC00"));

            // viewHolder.Ddateweek.setShadowLayer(15,0,0,Color.parseColor("#cf1d1d") );
            viewHolder.Ddateweek.setTextColor(Color.parseColor("#CFFFCC00"));
/*
            android:shadowColor="#cf1d1d"
            android:shadowDx="0.0"
            android:shadowDy="0.0"
            android:shadowRadius="8"
*/
            switch (weekday) {
                case "T2":
                    viewHolder.daycolor.setBackgroundResource(R.drawable.roundedleft_mon);
                    break;
                case "T3":
                    viewHolder.daycolor.setBackgroundResource(R.drawable.roundedleft_tue);
                    break;
                case "T4":
                    viewHolder.daycolor.setBackgroundResource(R.drawable.roundedleft_we);
                    break;

                case "T5":
                    viewHolder.daycolor.setBackgroundResource(R.drawable.roundedleft_th);
                    break;
                case "T6":
                    viewHolder.daycolor.setBackgroundResource(R.drawable.roundedleft_fri);
                    break;
                case "T7":

                    break;
                case "CN":
                    viewHolder.daycolor.setBackgroundResource(R.drawable.roundedleft_sun);
                    break;
            }
        } else {

            viewHolder.Ddate.setShadowLayer(15, 0, 0, Color.parseColor("#000000"));
            viewHolder.Ddate.setTextColor(Color.parseColor("#DFFFFFFF"));
            viewHolder.Dtime.setTextColor(Color.parseColor("#DFFFFFFF"));
            viewHolder.Ddateweek.setTextColor(Color.parseColor("#DFFFFFFF"));
        }

        if (checkscroll2(time)) {
            viewHolder.Ddate.setTextColor(Color.parseColor("#4FFFFFFF"));
            viewHolder.Dtime.setTextColor(Color.parseColor("#4FFFFFFF"));
            viewHolder.Ddateweek.setTextColor(Color.parseColor("#4FFFFFFF"));
            viewHolder.Ddesc.setTextColor(Color.parseColor("#4FFFFFFF"));
            viewHolder.Dinfo.setTextColor(Color.parseColor("#4FFFFFFF"));
            viewHolder.daycolor.setBackgroundResource(R.drawable.roundedleft_exp);
        } else {
            switch (weekday) {
                case "T2":
                    viewHolder.daycolor.setBackgroundResource(R.drawable.roundedleft_mon);
                    break;
                case "T3":
                    viewHolder.daycolor.setBackgroundResource(R.drawable.roundedleft_tue);
                    break;
                case "T4":
                    viewHolder.daycolor.setBackgroundResource(R.drawable.roundedleft_we);
                    break;

                case "T5":
                    viewHolder.daycolor.setBackgroundResource(R.drawable.roundedleft_th);
                    break;
                case "T6":
                    viewHolder.daycolor.setBackgroundResource(R.drawable.roundedleft_fri);
                    break;
                case "T7":

                    break;
                case "CN":
                    viewHolder.daycolor.setBackgroundResource(R.drawable.roundedleft_sun);
                    break;
            }

            viewHolder.Ddate.setTextColor(Color.parseColor("#AFFFFFFF"));
            viewHolder.Dtime.setTextColor(Color.parseColor("#AFFFFFFF"));
            viewHolder.Ddateweek.setTextColor(Color.parseColor("#AFFFFFFF"));
            viewHolder.Ddesc.setTextColor(Color.parseColor("#AFFFFFFF"));
            viewHolder.Dinfo.setTextColor(Color.parseColor("#AFFFFFFF"));

        }

        if (checkscroll(time)) {

            //  viewHolder.Ddate.setOutlineAmbientShadowColor(Color.parseColor("#cf1d1d"));
            viewHolder.Ddate.setShadowLayer(15, 0, 0, Color.parseColor("#cf1d1d"));
            viewHolder.Ddate.setTextColor(Color.parseColor("#CFFFCC00"));
            viewHolder.Dtime.setTextColor(Color.parseColor("#CFFFCC00"));
            viewHolder.Ddateweek.setTextColor(Color.parseColor("#CFFFCC00"));
        }

        String timeend = item.getEndat();
        viewHolder.tvtimeend.setText(timeend);
        viewHolder.Ddate.setText(date + "/" + month);
        viewHolder.Ddateweek.setText(weekday);
        viewHolder.Dtime.setText(getTime(time, 0) + ":" + getTime(time, 1) + "-" + getTime(timeend, 0) + ":" + getTime(timeend, 1));
        viewHolder.Dinfo.setText(title);
        viewHolder.tvTime.setText(time);
        viewHolder.Did.setText(String.valueOf(id));
        viewHolder.Ddesc.setText(desc);



        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
        viewHolder.swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
                //    Toast.makeText(mContext, "delete", Toast.LENGTH_SHORT).show();
            }
        });
        viewHolder.swipeLayout.setOnDoubleClickListener(new SwipeLayout.DoubleClickListener() {
            @Override
            public void onDoubleClick(SwipeLayout layout, boolean surface) {
                //    Toast.makeText(mContext, "DoubleClick", Toast.LENGTH_SHORT).show();
            }
        });

        viewHolder.buttonedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                displayAlertDialog(time, desc, title, id, position);

                mItemManger.closeAllItems();
                notifyItemRangeChanged(position, mDataset.size());

            }
        });

        viewHolder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String time = item.getStartat();

                Intent intent2 = new Intent(mContext, notiReceiver.class);
                intent2.setAction(time);

                AlarmManager alarmManager2;
                PendingIntent pendingIntent2;
                alarmManager2 = (AlarmManager) mContext.getSystemService(ALARM_SERVICE);
                pendingIntent2 = PendingIntent.getBroadcast(mContext, 99, intent2, PendingIntent.FLAG_UPDATE_CURRENT);

                alarmManager2.cancel(pendingIntent2);
                Log.d("canceled action", time);

                DatabaseHelper mydb = new DatabaseHelper(mContext);
                mydb.deleteProductByID(item.getId());
                mydb.close();
                mItemManger.removeShownLayouts(viewHolder.swipeLayout);
                mDataset.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, mDataset.size());
                mItemManger.closeAllItems();

                //  Toast.makeText(view.getContext(), "Deleted  !", Toast.LENGTH_SHORT).show();
            }
        });

        //  viewHolder.textViewPos.setText((position + 1) + ".");
        //   viewHolder.textViewData.setText(item.getTitle());
        mItemManger.bindView(viewHolder.itemView, position);

    }

    void dosomething(Classview a) {

    }

    public Bundle displayAlertDialog(String time, String eventDesc, String eventTitle, int id, int position) {

        Bundle extras = new Bundle();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        //   LayoutInflater inflater = getLayoutInflater();

        View alertLayout = inflater.inflate(R.layout.addeventpicker, null);
        TimePicker timePicker = alertLayout.findViewById(R.id.datePicker1);
        AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
        TextView textViewtime = alertLayout.findViewById(R.id.timetext);
        DatePicker realdatepicker = alertLayout.findViewById(R.id.realdatepicker);
        realdatepicker.setVisibility(View.GONE);
        timePicker.setIs24HourView(true);

        timePicker.setHour(Integer.parseInt(getTime(time, 0)));
        timePicker.setMinute(Integer.parseInt(getTime(time, 1)));
        int year = Integer.parseInt(getdate(time, 0));
        int month = Integer.parseInt(getdate(time, 1)) - 1;
        int date = Integer.parseInt(getdate(time, 2));
        realdatepicker.init(year, month, date, null);

        textViewtime.setText(timePicker.getHour() + ":" + timePicker.getMinute());
        Space space = alertLayout.findViewById(R.id.blankspace);

        EditText edtTitle = alertLayout.findViewById(R.id.edteventTitle);
        edtTitle.setText(eventTitle);

        EditText edtDesc = alertLayout.findViewById(R.id.edteventDesc);
        edtDesc.setText(eventDesc);

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

        alert.setTitle("S???a th??ng tin S??? Ki???n");
        alert.setView(alertLayout);
        alert.setCancelable(false);

        AlertDialog dialog = alert.create();
        dialog.show();
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                textViewtime.setText(timePicker.getHour() + ":" + timePicker.getMinute());
            }
        });
        RelativeLayout rev1 = alertLayout.findViewById(R.id.relativeTimepicker);
        RelativeLayout rev2 = alertLayout.findViewById(R.id.relaAddtext);
        Button btnNext = alertLayout.findViewById(R.id.btnNext);
        Button btnBack = alertLayout.findViewById(R.id.btnBack);
        final float scale = mContext.getResources().getDisplayMetrics().density;
        int pixels = (int) (50 * scale + 0.5f);
        Button btnFinish = alertLayout.findViewById(R.id.btnFinish);
        Button btnBack2 = alertLayout.findViewById(R.id.btnBacktoTimepicker);
        TextView timeDisplayOnaddtitle = alertLayout.findViewById(R.id.datetimeTV);
        btnBack2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentAddView == 2) {
                    rev1.setVisibility(View.VISIBLE);
                    rev2.setVisibility(View.GONE);
                    currentAddView = 1;

                }

            }
        });

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                String neventTitle = edtTitle.getText().toString();
                String neventDesc = edtDesc.getText().toString();
                DatabaseHelper mydb = new DatabaseHelper(getContext());
                mydb.updateProduct(new Classview(neventTitle, 1, neventDesc, eventStartAt, eventStartAt, 0, eventStartAt, "no note", "T2", id));
                //    mydb.insertProduct(new Classview(eventTitle,1,eventDesc,eventStartAt,"0",0,eventStartAt,"no note","T2",0 ));
                dialog.cancel();
                currentAddView = 0;
                //  notifyItemRemoved(position);
                //  notifyItemRangeChanged(position, mDataset.size());

                mDataset.remove(position);
                mDataset.add(position, new Classview(neventTitle, 1, neventDesc, eventStartAt, eventStartAt, 0, eventStartAt, "no note", "T2", id));
                notifyDataSetChanged();
                notifyItemRangeChanged(position, mDataset.size());

                //  View root = inflater.inflate(R.layout.fragment_dashboard,);

/*
                final ListView listView = convertview.findViewById(R.id.listviewAlarm);
                ArrayList<Classview> a  =  mydb.getAllProducts();
                a=mydb.ArraylistCompare(a);



                classListAdaper adaper = new classListAdaper(getContext(),R.layout.adaper_view_layout,a);

                today =mydb.getDayIndex(a);
                a=mydb.ArraylistCompare(a);
                listView.setAdapter(adaper);
                adaper.notifyDataSetChanged();
                today =mydb.getDayIndex(a);
                listView.setSelection(today);
*/

//  public Classview(String title, int enable, String descr, String startat, String endat, int type, String alarmat, String note,String weekday,int Id) {

            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currentAddView == 1) {
                    rev1.setVisibility(View.GONE);
                    rev2.setVisibility(View.VISIBLE);
                    currentAddView = 2;

                    String date = String.valueOf(realdatepicker.getDayOfMonth());
                    String month = String.valueOf(realdatepicker.getMonth() + 1);
                    ;
                    if (realdatepicker.getDayOfMonth() < 10) {
                        date = "0" + String.valueOf(realdatepicker.getDayOfMonth());
                    }

                    if (realdatepicker.getMonth() + 1 < 10) {
                        month = "0" + String.valueOf(realdatepicker.getMonth() + 1);
                    }

                    timeDisplayOnaddtitle.setText(timePicker.getHour() + ":" + timePicker.getMinute() + "  " + date + "/" + month + "/" + realdatepicker.getYear());

                    //need to add "0" char

                    eventStartAt = realdatepicker.getYear() + "-" + month + "-" + date + " " + timePicker.getHour() + ":" + timePicker.getMinute() + ":00";

                }
                if (currentAddView == 0) {
                    //set buttons under calendar
                    timePicker.setVisibility(View.GONE);
                    realdatepicker.setVisibility(View.VISIBLE);
                    RelativeLayout.LayoutParams lp =
                            new RelativeLayout.LayoutParams
                                    (
                                            RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT
                                    );
                    lp.setMargins(pixels, 0, 0, 0);
                    lp.addRule(RelativeLayout.BELOW, realdatepicker.getId());
                    lp.addRule(RelativeLayout.RIGHT_OF, space.getId());

                    btnNext.setLayoutParams(lp);

                    RelativeLayout.LayoutParams lpBack =
                            new RelativeLayout.LayoutParams
                                    (
                                            RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT
                                    );
                    lpBack.addRule(RelativeLayout.BELOW, realdatepicker.getId());
                    lpBack.addRule(RelativeLayout.LEFT_OF, space.getId());
                    lpBack.setMargins(0, 0, pixels, 0);
                    btnBack.setLayoutParams(lpBack);
                    currentAddView = 1;
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currentAddView == 0) {
                    dialog.cancel();
                }
                if (currentAddView == 1) {
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
                    currentAddView = 0;
                }
            }
        });

        return extras;
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

        if (Locale.getDefault().getLanguage().compareTo("en") != 0) {
            switch (date) {
                case "Th??? Hai":
                    realdayname = "T2";
                    break;
                case "Th??? Ba":
                    realdayname = "T3";
                    break;
                case "Th??? T??":
                    realdayname = "T4";
                    break;
                case "Th??? N??m":
                    realdayname = "T5";
                    break;
                case "Th??? S??u":
                    realdayname = "T6";
                    break;
                case "Th??? B???y":
                    realdayname = "T7";
                    break;
                case "Ch??? Nh???t":
                    realdayname = "CN";
                    break;

            }
        } else {

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
        }

        return realdayname;
    }



    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }
}
