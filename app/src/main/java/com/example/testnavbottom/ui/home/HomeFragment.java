package com.example.testnavbottom.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.example.testnavbottom.Classview;
import com.example.testnavbottom.DatabaseHelper;
import com.example.testnavbottom.R;
import com.example.testnavbottom.classListAdaper;
import com.example.testnavbottom.taskCL;
import com.example.testnavbottom.internetClass;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.Inflater;

public class HomeFragment extends Fragment {
    public  int today=1;
    Context contextnew=this.getContext();
    private static Context context;
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



    @RequiresApi(api = Build.VERSION_CODES.O)
   public void getEventsFromsvo(String rawJson, LayoutInflater inflater,ViewGroup container){


      View root = inflater.inflate(R.layout.fragment_home, container, false);
     final ListView listView = root.findViewById(R.id.lv1);
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
        final ListView listView = root.findViewById(R.id.lv1);
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
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                textViewtime.setText(timePicker.getHour()+":"+timePicker.getMinute());
            }
        });
        Button btnNext= alertLayout.findViewById(R.id.btnNext);
        Button btnBack= alertLayout.findViewById(R.id.btnBack);
        final float scale = getContext().getResources().getDisplayMetrics().density;
        int pixels = (int) (50 * scale + 0.5f);



        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




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
            }
        });



        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                timePicker.setVisibility(View.VISIBLE);
                realdatepicker.setVisibility(View.GONE);
                RelativeLayout.LayoutParams lp =
                        new RelativeLayout.LayoutParams
                                (
                                        RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT
                                );
                lp.setMargins(pixels, 0, 0, 0);
                lp.addRule(RelativeLayout.BELOW,textViewtime.getId());
                lp.addRule(RelativeLayout.RIGHT_OF,space.getId());






                btnNext.setLayoutParams(lp);

                RelativeLayout.LayoutParams lpBack =
                        new RelativeLayout.LayoutParams
                                (
                                        RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT
                                );
                lpBack.addRule(RelativeLayout.BELOW,textViewtime.getId());
                lpBack.addRule(RelativeLayout.LEFT_OF,space.getId());
                lpBack.setMargins(0, 0, pixels, 0);
                btnBack.setLayoutParams(lpBack);

            }
        });


        alert.setTitle("Chọn thời gian cho Sự Kiện");
        alert.setView(alertLayout);
        alert.setCancelable(true);

        AlertDialog dialog = alert.create();
        dialog.show();
    }




    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final ListView listView = root.findViewById(R.id.lv1);

        Calendar calendar = Calendar.getInstance();
        DatabaseHelper mydb = new DatabaseHelper(this.getContext());
        Context context=this.getContext();
      //  listviewLoad(inflater,container);
     //   Classview event= new Classview("--","mon loz","T5","18");

        FloatingActionButton addbtn = root.findViewById(R.id.floatingBtn);
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayAlertDialog();


            }
        });
        ArrayList<Classview> classlist = new ArrayList<>();

        Button btn1=root.findViewById(R.id.btn1);
        Button btn2=root.findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              mydb.deleteallevent();


                ArrayList<Classview> a  =  mydb.getAllProducts();

                classListAdaper adaper = new classListAdaper(context,R.layout.adaper_view_layout,a);

              //  listView.setSelection(1);
                listView.setAdapter(adaper);

            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {






            }
        });




        ArrayList<Classview> a  =  mydb.getAllProducts();
        a=mydb.ArraylistCompare(a);
        classListAdaper adaper = new classListAdaper(this.getContext(),R.layout.adaper_view_layout,a);

        today =mydb.getDayIndex(a);
        listView.setAdapter(adaper);
        listView.setSelection(today);



        return root;
    }
}