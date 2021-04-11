package com.example.testnavbottom.ui.home;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.daimajia.swipe.util.Attributes;
import com.example.testnavbottom.Classview;
import com.example.testnavbottom.DatabaseHelper;
import com.example.testnavbottom.MainActivity;
import com.example.testnavbottom.R;
import com.example.testnavbottom.RecyclerViewAdapter;
import com.example.testnavbottom.alarmReceiver;
import com.example.testnavbottom.classListAdaper;
import com.example.testnavbottom.recycleViewAdapter;
import com.example.testnavbottom.reponseClass;
import com.example.testnavbottom.taskCL;
import com.example.testnavbottom.internetClass;
import com.example.testnavbottom.ui.notifications.loginClass;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.Inflater;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.Context.ALARM_SERVICE;

public class HomeFragment extends Fragment {
    String  myTasks="";
    AlertDialog dialog;
    TextView tvloading;





    public  int today=1;
    public int currentAddView=0;
    Context contextnew=this.getContext();
    private static Context context;
    private RecyclerView mRecycleview;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    String eventStartAt="Không có tiêu đề";

    String eventTitle="Không có tiêu đề";
    String eventDesc="Không có tiêu đề";

    private HomeViewModel homeViewModel;
    private  String readText(Context context, int resId) throws IOException {
        InputStream is = context.getResources().openRawResource(resId);
        BufferedReader br= new BufferedReader(new InputStreamReader(is));
        StringBuilder sb= new StringBuilder();
        String s= null;
        while((  s = br.readLine())!=null) {
            sb.append(s);
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }


    void displayLoading(){
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.loading_layouts, null);
        tvloading= alertLayout.findViewById(R.id.tvstatus);

        //eerrr here
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());


        alert.setView(alertLayout);
        alert.setCancelable(true);

        dialog = alert.create();
        dialog.show();

        try {
            String info=load("info.txt");
            if (info!=null&&info.compareTo("nofile")!=0) {
                String[] arrInfo = info.split("\\|", 5);
                if (arrInfo[0] != null && arrInfo[1]!=null){
                String agument1=arrInfo[0].replace("\n","");
                    String agument2=arrInfo[1];
                    getTaskFromSVO(agument1,agument2);
                }else {
                    Toast.makeText(getContext(),"Bạn chưa đăng nhập!",Toast.LENGTH_SHORT).show();
                    tvloading.setText("Bạn chưa đăng nhập!");

                    dialog.cancel();

                }
            }
        }catch (Exception e){
            Toast.makeText(getContext(),"Bạn chưa đăng nhập!",Toast.LENGTH_SHORT).show();
            tvloading.setText("Bạn chưa đăng nhập!");

            dialog.cancel();
        }

        //  downloadImage("https://halustorage-hn.ss-hn-1.vccloud.vn/60011b3b8003bf099275ca6d.jpg");


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
   public void getEventsFromsvo(String rawJson){




        DatabaseHelper mydb = new DatabaseHelper(this.getContext());
        Gson gson1=new Gson();

        int count=0;
        Pattern pattern = Pattern.compile("(?<=title)[\\s\\S]*?(?=\\})",Pattern.MULTILINE);


        ArrayList<String> tasks = new ArrayList<String>();
        Matcher matcher = pattern.matcher(rawJson);

        while (matcher.find()){
            tasks.add("{\"title"+matcher.group(0)+"}");
            count++;
        }

        for (int i=0;i<count;i++) {
            taskCL mtask= gson1.fromJson(tasks.get(i),taskCL.class);
            Classview myDbtask= new Classview(mtask.title,1,mtask.desc,mtask.startAt,mtask.finishAt,1,"1","this is note","T2",1);


            mydb.insertProduct(myDbtask);
            //editText.setText(editText.getText()+mtask.title+"\n");
        }


    //    ArrayList<Classview> a  =  mydb.getAllProducts();
     //   a=mydb.ArraylistCompare(a);



/*
            classListAdaper adaper = new classListAdaper(contextnew, R.layout.adaper_view_layout, a);
            today =mydb.getDayIndex(a);
            listView.setAdapter(adaper);
            listView.setSelection(today);

*/








    }









    @RequiresApi(api = Build.VERSION_CODES.O)
    void listviewLoad(LayoutInflater inflater, ViewGroup container){
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final SwipeMenuListView listView = root.findViewById(R.id.lv1);
        DatabaseHelper mydb = new DatabaseHelper(this.getContext());
        ArrayList<Classview> a  =  mydb.getAllProducts();

        classListAdaper adaper = new classListAdaper(this.getContext(),R.layout.adaper_view_layout,a);

       // listView.removeViewAt(1);

        listView.invalidateViews();

        Toast.makeText(this.getContext(), "reload ListView", Toast.LENGTH_SHORT).show();


    }



    public void displayAlertDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.addeventpicker, null);
        TimePicker timePicker =alertLayout.findViewById(R.id.datePicker1);
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        TextView textViewtime=alertLayout.findViewById(R.id.timetext);
        DatePicker realdatepicker= alertLayout.findViewById(R.id.realdatepicker);
        realdatepicker.setVisibility(View.GONE);
        timePicker.setIs24HourView(true);
        textViewtime.setText(timePicker.getHour()+":"+timePicker.getMinute());
        Space space=alertLayout.findViewById(R.id.blankspace);



        EditText edtTitle= alertLayout.findViewById(R.id.edteventTitle);
        EditText edtDesc= alertLayout.findViewById(R.id.edteventDesc);
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




        //alert.setTitle("Chọn thời gian cho Sự Kiện");
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
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                eventTitle = edtTitle.getText().toString();
                if (eventTitle.compareTo("")==0){
                    eventTitle="Sự kiện không có tiêu đề.";

                }
                eventDesc=edtDesc.getText().toString();
                DatabaseHelper mydb = new DatabaseHelper(getContext());
                mydb.insertProduct(new Classview(eventTitle,1,eventDesc,eventStartAt,eventStartAt,0,eventStartAt,"no note","T2",0 ));
                dialog.cancel();
                currentAddView=0;

                setUserVisibleHint(true);





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


    public String load(String filename) {
        String ret = "";

        try {
            InputStream inputStream = getContext().openFileInput(filename);

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
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;

    }

    String checkAndGet(String myRes, int mode) {
        Gson gson1 = new Gson();
        reponseClass mtask = gson1.fromJson(myRes, reponseClass.class);
        if (mode == 1) {

            return   "1";
        }
        if(mode==2){
            return  mtask.sso_token;
        }
        if(mode==3){
            return  mtask.refresh_token;
        }


        if (mode==5 && mtask._id.compareTo("1")==0){
            return "1";
        }

        if (mode==6 && myRes.compareTo("{\"list_acc\":[]}")!=0){
            return "1";
        }
        if (mode==7 && mtask.stt.compareTo("success")==0){
            return "1";
        }
        if (mode==8 && myRes.compareTo("{\"list_acc\":[]}")!=0){
            return mtask.img250;


        }
        if (mode==9 && myRes.compareTo("{\"list_acc\":[]}")!=0){
            return mtask.fullname;


        }
        if (mode==10 && myRes.compareTo("{\"list_acc\":[]}")!=0){
            return mtask.ids;


        }

        return "error";
    }

    public String getTaskFromSVO(String sso,String refresh_token ){


        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(MediaType.get("application/json"), "{\"force_update\":true}");
        String url = "https://api.dhdt.vn/calendar/task";
        Request request = new Request.Builder().addHeader("accept", "application/json, text/plain, */*")
                .addHeader("refresh_token", refresh_token)
                .addHeader("sso_token", sso)
                .addHeader("if-none-match", "W/\"b5b-3+NZGVqGPC6cHnb+39bL/VlxSY4\"")
                .addHeader("agent", "{\"brower\":\"SVOapp\",\"version\":\"6.1.3\",\"device_name\":\"twzsv\",\"unique_device_id\":\"8DA9BD10 - B0D0 - 4808 - AB34 - 4AF30AA044EC\",\"user_agent\":\"Mozilla / 5.0(iPhone; CPU iPhone OS 13_6_1 like Mac OS X) AppleWebKit / 605.1.15(KHTML, like Gecko) Mobile / 15E148\",\"system_name\":\"iOS\",\"device_model\":\"iPhone 7\",\"system_version\":\"13.6.1\"}")
                .post(body)
                .url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
                (getActivity()).runOnUiThread(new Runnable()
                {
                    public void run()
                    {
                        tvloading.setText("Lỗi kết nối!");

                    }

                });
                SystemClock.sleep(3000);
                dialog.cancel();
                myTasks="fail";
            }

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myRes = response.body().string();

                    String stt=checkAndGet(myRes, 5);

                    if (stt.compareTo("1")==0) {


                        getEventsFromsvo(myRes);



                        (getActivity()).runOnUiThread(new Runnable()
                        {
                            public void run()
                            {
                                tvloading.setText("Lấy lịch học thành công ^.^!");
                                setUserVisibleHint(true);



                            }

                        });
                        SystemClock.sleep(4000);
                        dialog.cancel();




                    } else {


                        (getActivity()).runOnUiThread(new Runnable()
                        {
                            public void run()
                            {
                                tvloading.setText("Lấy lịch học thất bại!");

                            }

                        });
                        SystemClock.sleep(3000);
                        dialog.cancel();

                    }


                }
                else {
                    (getActivity()).runOnUiThread(new Runnable()
                    {
                        public void run()
                        {
                            tvloading.setText("Lấy lịch học thất bại");

                        }

                    });
                    SystemClock.sleep(3000);
                    dialog.cancel();
                }

            }
        });


        return "";
    }




    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);




                Calendar calendar = Calendar.getInstance();
        DatabaseHelper mydb = new DatabaseHelper(this.getContext());
        Context context=this.getContext();

        FloatingActionButton demobtn = root.findViewById(R.id.floatingBtndemo);
        FloatingActionButton demobtn2 = root.findViewById(R.id.floatingBtndemo2);

        demobtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        demobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            displayLoading();

            }
        });
        demobtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String rawJson="deo co gi";
                try {
                    rawJson=readText(getContext() ,R.raw.eventsjs);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                getEventsFromsvo(rawJson);
                setUserVisibleHint(true);
                return true;
            }
        });


        FloatingActionButton addbtn = root.findViewById(R.id.floatingBtn);



        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            //    addbtn.setColorFilter(ContextCompat.getColor(context,R.color.teal_700));
               displayAlertDialog();

            }

        });

/*

//
           //     ArrayList<Classview> a  =  mydb.getAllProducts();

             //   classListAdaper adaper = new classListAdaper(context,R.layout.adaper_view_layout,a);

             //  adaper.remove();

              // adaper.notifyDataSetChanged();





*/

        FloatingActionButton btn2=root.findViewById(R.id.floatingBtnrefresh);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              mydb.deleteallevent();

               setUserVisibleHint(true);



            }
        });





/*

        ArrayList<Classview>  arrayList=new ArrayList<Classview>();




    //    arrayList.add(new Classview("0",1,"0","2021-04-03 01:00:00","2021-04-03 01:00:00",0,"2021-04-03 01:00:00","0","T2",9 ));
        mRecycleview =root.findViewById(R.id.recycleview);
      //  mRecycleview.setHasFixedSize(true);
        mLayoutManager= new LinearLayoutManager(getContext());

        arrayList=mydb.getAllProducts();
        arrayList=mydb.ArraylistCompare(arrayList);
        mAdapter= new recycleViewAdapter(arrayList);

        mRecycleview.setLayoutManager(mLayoutManager);
        mRecycleview.setAdapter(mAdapter);
        today =mydb.getDayIndex(arrayList);

*/

        // Layout Managers:
        mLayoutManager= new LinearLayoutManager(getContext());

        // Item Decorator:
      //  mRecycleview.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(R.drawable.divider)));
        // recyclerView.setItemAnimator(new FadeInLeftAnimator());

        // Adapter:
        mRecycleview =root.findViewById(R.id.recycleview);
        ArrayList<Classview>  arrayList=new ArrayList<Classview>();








    arrayList=mydb.getAllProducts();





    if (arrayList.isEmpty()){
        ImageView emptyimg= root.findViewById(R.id.imgviewEmpty);
        emptyimg.setVisibility(View.VISIBLE);
        Toast.makeText(getContext(),"Bạn chưa có sự kiện nào, thêm ngay nhé!",Toast.LENGTH_SHORT).show();
       //     mRecycleview.setBackgroundResource(R.drawable.ss1faa3cda455c865f037d63a223577ab5);
    }
    else {

        ArrayList<Classview> finalArrayList = arrayList;
      //  new Handler().postDelayed(new Runnable() {
       //     public void run() {
                final int[] i = {0};
                finalArrayList.forEach((arrayListitem)->{

                    if( checkscroll2(arrayListitem.getStartat())&& i[0] <3&&arrayListitem.getNote().compareTo("noitced")!=0){
                        AlarmManager alarmManager;
                        PendingIntent pendingIntent;
                         Calendar calendar2;
                        calendar2 = Calendar.getInstance();
                        calendar2.set(Integer.parseInt(getdate(arrayListitem.getStartat(), 0)),Integer.parseInt(getdate(arrayListitem.getStartat(), 1))-1,Integer.parseInt(getdate(arrayListitem.getStartat(), 2)),Integer.parseInt(getTime(arrayListitem.getStartat(), 0)),Integer.parseInt(getTime(arrayListitem.getStartat(), 1)),0 );

                   //     calendar2 = Calendar.getInstance();
                    //    calendar2.set(Calendar.HOUR_OF_DAY, Integer.parseInt(getTime(arrayListitem.getStartat(), 0)));
                   //     calendar2.set(Calendar.MINUTE, Integer.parseInt(getTime(arrayListitem.getStartat(), 1)));
                   //     calendar2.set(Calendar.SECOND, 0);


                        Intent intent= new Intent(getContext(),notiReceiver.class);


                        intent.setAction(arrayListitem.getStartat());
                        intent.putExtra("id",String.valueOf(arrayListitem.getId()));
                        intent.putExtra("title",arrayListitem.getTitle());
                        intent.putExtra("desc",arrayListitem.getDescr());
                        intent.putExtra("time",getTime(arrayListitem.getStartat(),0)+":"+getTime(arrayListitem.getStartat(),1));
                        alarmManager=(AlarmManager) getContext().getSystemService(ALARM_SERVICE);

                        pendingIntent =PendingIntent.getBroadcast(getContext(),99,intent,PendingIntent.FLAG_UPDATE_CURRENT);



                        Calendar nowcalendar=Calendar.getInstance();
                        if (nowcalendar.getTimeInMillis()<calendar2.getTimeInMillis()){

                            String delay= load("settings.txt");
                            delay =delay.replace("\n","");
                            if (delay.compareTo("nofile")!=0&&delay.compareTo("")!=0){
                                calendar2.add(Calendar.MINUTE, Integer.parseInt(delay)*-1);
                            }

                            Log.d("time",String.valueOf(calendar2.getTimeInMillis()));
                            alarmManager.set(AlarmManager.RTC_WAKEUP,calendar2.getTimeInMillis(),pendingIntent);
                            i[0]++;
                       //     alarmManager.cancel(pendingIntent);















                            //  Toast.makeText(getContext(),"added alarm "+calendar2.getTimeInMillis(),Toast.LENGTH_SHORT).show();
                            Log.d("added alarm", String.valueOf(calendar2.getTimeInMillis()));
                        }else {
                           // Toast.makeText(getContext(),"time expried",Toast.LENGTH_SHORT).show();
                        }



                    }
                    else {

                   //     Toast.makeText(getContext(),"time expried "+arrayListitem.getStartat(),Toast.LENGTH_SHORT).show();

                    }


                        }






                    );


     //       }
    //    }, 100);





    }

    //    arrayList.add(new Classview("0",1,"0","2021-04-03 01:00:00","2021-04-03 01:00:00",0,"2021-04-03 01:00:00","0","T2",9 ));
        arrayList=mydb.ArraylistCompare(arrayList);
        int mypos=mydb.getDayIndex(arrayList);
        mAdapter = new RecyclerViewAdapter(getContext(), arrayList);
        ((RecyclerViewAdapter) mAdapter).setMode(Attributes.Mode.Single);
        mRecycleview.setLayoutManager(mLayoutManager);
        mRecycleview.setAdapter(mAdapter);
        mRecycleview.scrollToPosition(mypos);






/*
        ArrayList a = new ArrayList<Classview>();
        a  =  mydb.getAllProducts();
        a=mydb.ArraylistCompare(a);

      classListAdaper adaper = new classListAdaper(this.getContext(),R.layout.adaper_view_layout,a);



        today =mydb.getDayIndex(a);
        listView.setAdapter(adaper);
       listView.setSelection(today);
*/

        /*

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                // set item width
                openItem.setWidth(180);
                // set item title
               // openItem.setTitle("Open");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color

                openItem.setTitleColor(Color.GREEN);
                openItem.setBackground(R.color.translucent_green);

                // add to menu
                openItem.setIcon(R.drawable.ic_baseline_edit_24);
                menu.addMenuItem(openItem);



                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getContext());
                // set item background
                deleteItem.setBackground(R.color.translucent_red);
                // set item width
                deleteItem.setWidth(180);
                // set a icon
                deleteItem.setIcon(R.drawable.ic_baseline_delete_sweep_24);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };




  //     listView.setMenuCreator(creator);

    // listView.setCloseInterpolator(new BounceInterpolator());







/*





        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch(index){
                    case 1:
                        final int selected_item = position;

                        new AlertDialog.Builder(getContext()).
                                setIcon(android.R.drawable.ic_delete)
                                .setTitle("Xóa sự kiện này?")
                                .setMessage("Bạn có thực sự muốn xóa vĩnh viễn sự kiện này..?")
                                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which)
                                    {

                                        mydb.deleteProductByID(finalA.get(selected_item).getId());

                                        setUserVisibleHint(true);
                                    }
                                })
                                .setNegativeButton("Không" , null).show();
                        break;
                    case 0:

                       String startAt=finalA.get(position).getStartat();
                        String desc=finalA.get(position).getDescr();
                        String title=finalA.get(position).getTitle();
                        int id=finalA.get(position).getId();

                        DatabaseHelper mydb= new DatabaseHelper(getContext());
                        mydb.updateProduct(new Classview("0",1,"0",startAt,startAt,0,startAt,"0","T2",id ));

                    setUserVisibleHint(true);



                        break;



                }



                        return true;
            }
        });

*/





        return root;
    }
//this is  a cancel pending intent func
    void dosomething(Classview a){

        String time =a.getStartat();
        Intent intent = new Intent(getContext(), alarmReceiver.class);
        Calendar calendar;
        AlarmManager alarmManager;
        PendingIntent pendingIntent;
        alarmManager = (AlarmManager) getContext().getSystemService(ALARM_SERVICE);
        pendingIntent = PendingIntent.getBroadcast(getContext(), 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(getTime(time, 0)));
        calendar.set(Calendar.MINUTE, Integer.parseInt(getTime(time, 1)));
        calendar.set(Calendar.SECOND, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        Calendar currnethour=Calendar.getInstance();
        alarmManager.cancel(pendingIntent);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Boolean checkscroll2(String mydate) {
      //  calendar2.set(Integer.parseInt(getdate(arrayListitem.getStartat(), 0)),Integer.parseInt(getdate(arrayListitem.getStartat(), 1))-1,Integer.parseInt(getdate(arrayListitem.getStartat(), 2)),Integer.parseInt(getTime(arrayListitem.getStartat(), 0)),Integer.parseInt(getTime(arrayListitem.getStartat(), 1)) );
        Calendar calendar2=Calendar.getInstance();

        Calendar cal1 = GregorianCalendar.getInstance();


        String mTempday4 = getdate(mydate, 2);


        String mTempmonth4 = getdate(mydate, 1);


        String mTempyear4 = getdate(mydate, 0);



        //trash

        int mTempday5 = cal1.get(Calendar.DAY_OF_MONTH);

        int mTempmonth5 = cal1.get(Calendar.MONTH)+1;
        int mTempyear5 =  cal1.get(Calendar.YEAR);

        String mTempday6=String.valueOf(mTempday5);
        String mTempmonth6=String.valueOf(mTempmonth5);

        if (mTempday5<10){
            mTempday6="0"+mTempday6;
        }
        if (mTempmonth5<10){
            mTempmonth6="0"+mTempmonth6;
        }



        LocalDate dt1 = LocalDate.now();

        //  cal.add(Calendar.DATE,-1);
        LocalDate dt2 = LocalDate.parse(mTempyear5+"-"+mTempmonth6+"-"+mTempday6);
        //      dt2=   dt2.minusDays(1);
        //     dt2=   dt2.minusDays(1);


        dt2 = LocalDateTime.ofInstant(calendar2.toInstant(), calendar2.getTimeZone().toZoneId()).toLocalDate();
//Integer.parseInt(mTempday4)<Integer.parseInt(mTempday5)-1

        // Log.d("cac",cal.getTime().toString());
        //   dt1.isAfter(dt2)
        Calendar calendar3=Calendar.getInstance();
        LocalTime lt1= LocalTime.of(calendar3.get(Calendar.HOUR_OF_DAY),calendar3.get(Calendar.MINUTE),0);
        LocalTime lt2=  LocalTime.of(Integer.parseInt(getTime(mydate, 0)),Integer.parseInt(getTime(mydate, 1)),0);

        if (dt1.isAfter(dt2)){


            return true;
        } else {
            if(lt2.isAfter(lt1)){
                return  true;

            }


            return false;
        }
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

}