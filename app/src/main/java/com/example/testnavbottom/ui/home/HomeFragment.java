package com.example.testnavbottom.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HomeFragment extends Fragment {
    public  int today=1;

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

    void listviewLoad(LayoutInflater inflater,ViewGroup container){
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final ListView listView = root.findViewById(R.id.lv1);
        DatabaseHelper mydb = new DatabaseHelper(this.getContext());
        ArrayList<Classview> a  =  mydb.getAllProducts();

        classListAdaper adaper = new classListAdaper(this.getContext(),R.layout.adaper_view_layout,a);

       // listView.removeViewAt(1);

        listView.invalidateViews();

        Toast.makeText(this.getContext(), "reload ListView", Toast.LENGTH_SHORT).show();


    }





    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final ListView listView = root.findViewById(R.id.lv1);

        Calendar calendar = Calendar.getInstance();
        DatabaseHelper mydb = new DatabaseHelper(this.getContext());
        Context context=this.getContext();
      //  listviewLoad(inflater,container);
     //   Classview event= new Classview("--","mon loz","T5","18");


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

                Gson gson = new Gson();
                String rawJson="deo co gi";
                try {
                    rawJson=readText(getContext() ,R.raw.eventsjs);
                } catch (IOException e) {
                    e.printStackTrace();
                }
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


                ArrayList<Classview> a  =  mydb.getAllProducts();

                classListAdaper adaper = new classListAdaper(context,R.layout.adaper_view_layout,a);


                listView.setAdapter(adaper);





            }
        });





      /*
        for (int i=0;i<30;i++) {

            int day = calendar.get(Calendar.DAY_OF_WEEK);
            Date date = calendar.getTime();
            int cday = calendar.get(Calendar.DATE);

            String currentdayweek = "T2";

            switch (day) {
                case Calendar.SUNDAY:
                    currentdayweek = "CN";
                    break;
                case Calendar.MONDAY:
                    currentdayweek = "T2";
                    break;
                case Calendar.TUESDAY:
                    currentdayweek = "T3";
                    break;
                case Calendar.WEDNESDAY:
                    currentdayweek = "T4";
                    break;
                case Calendar.THURSDAY:
                    currentdayweek = "T5";
                    break;
                case Calendar.FRIDAY:
                    currentdayweek = "T6";
                    break;
                case Calendar.SATURDAY:
                    currentdayweek = "T7";
                    break;
                default:
                    currentdayweek = "T2";
                    break;
            }


            Classview class1 = new Classview("--:--", "Bạn chưa có lịch hôm nay, vui lòng thêm sự kiện nhé!~", currentdayweek, String.valueOf(cday));




            mydb.insertProduct(class1);
            classlist.add(class1);





            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }
        */

       /* classlist.add(class2);
        classlist.add(class3);
        classlist.add(class4);
        classlist.add(class5);
        classlist.add(class1);*/
    /*
      classlist.add(mydb.getProductByID(1));
        classlist.add(mydb.getProductByID(2));
        classlist.add(mydb.getProductByID(3));
        classlist.add(mydb.getProductByID(4));

     */

    //  mydb.deleteallevent();







        ArrayList<Classview> a  =  mydb.getAllProducts();

        classListAdaper adaper = new classListAdaper(this.getContext(),R.layout.adaper_view_layout,a);

        today =mydb.getDayIndex();
        listView.setAdapter(adaper);
        listView.setSelection(today);




        return root;
    }
}