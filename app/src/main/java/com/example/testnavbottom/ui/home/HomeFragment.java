package com.example.testnavbottom.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.testnavbottom.Classview;
import com.example.testnavbottom.DatabaseHelper;
import com.example.testnavbottom.R;
import com.example.testnavbottom.classListAdaper;
import com.example.testnavbottom.recycleViewAdapter;
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




        alert.setTitle("Chọn thời gian cho Sự Kiện");
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

              //  listView.setSelection(1);







            }
        });







        ArrayList<Classview>  arrayList=new ArrayList<Classview>();



        /*
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




        ArrayList a = new ArrayList<Classview>();
        a  =  mydb.getAllProducts();
        a=mydb.ArraylistCompare(a);

      classListAdaper adaper = new classListAdaper(this.getContext(),R.layout.adaper_view_layout,a);



        today =mydb.getDayIndex(a);
        listView.setAdapter(adaper);
       listView.setSelection(today);


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
}