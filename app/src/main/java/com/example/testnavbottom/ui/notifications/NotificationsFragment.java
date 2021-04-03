package com.example.testnavbottom.ui.notifications;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.testnavbottom.R;
import com.example.testnavbottom.internetClass;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;


import androidx.annotation.RequiresApi;

import com.example.testnavbottom.reponseClass;
import com.example.testnavbottom.ui.home.HomeFragment;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.testnavbottom.MainActivity.context;

public class NotificationsFragment extends Fragment {
String  myTasks="";
    AlertDialog dialog;
    TextView tvloading;
    private NotificationsViewModel notificationsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        TextView textView=root.findViewById(R.id.tvrefreshtaskFromSvo);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //  Intent intent = new Intent( getContext(), LoadingActivity.class);
                //startActivity(intent);
            //    internetClass a= new internetClass();
           //     a.checkSmartName("DTC18548010300766","Leminh77",inflater,container);
            displayLoading();

            }
        });




        return root;
    }


    void displayLoading(){
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.loading_layouts, null);
       tvloading= alertLayout.findViewById(R.id.tvstatus);
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());


        alert.setView(alertLayout);
        alert.setCancelable(true);

         dialog = alert.create();
        dialog.show();
        checkSmartName("DTC1854801030076","Leminh77",alertLayout);



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

        return "error";
    }


    public String checkSmartName(String smartName, String password ,View alertLayout) {

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(MediaType.get("application/json"), "{\"smartname\":\""+smartName+"\",\"acc_type\":\"user\"}");
        String url = "https://api.dhdt.vn/account/login/check-smartname";
        Request request = new Request.Builder().addHeader("accept", "application/json, text/plain, */*")
                .addHeader("refresh_token", "undefined")
                .addHeader("sso_token", "undefined")
                .addHeader("if-none-match", "W/\"b5b-3+NZGVqGPC6cHnb+39bL/VlxSY4\"")
                .addHeader("agent", "{\"brower\":\"SVOapp\",\"version\":\"6.1.3\",\"device_name\":\"twzsv\",\"unique_device_id\":\"8DA9BD10 - B0D0 - 4808 - AB34 - 4AF30AA044EC\",\"user_agent\":\"Mozilla / 5.0(iPhone; CPU iPhone OS 13_6_1 like Mac OS X) AppleWebKit / 605.1.15(KHTML, like Gecko) Mobile / 15E148\",\"system_name\":\"iOS\",\"device_model\":\"iPhone 7\",\"system_version\":\"13.6.1\"}")
                .post(body)
                .url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
                myTasks="fail";
                try {


                    ((Activity) getContext()).runOnUiThread(new Runnable() {
                        public void run() {
                            tvloading.setText("Không thể kết nối đến máy chủ!");

                        }

                    });
                }catch (Exception e1){}
                SystemClock.sleep(3000);
                dialog.cancel();

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {



                    final String myRes = response.body().string();

                    String stt=checkAndGet(myRes, 6);
                    // tv1.setText(myRes);
                    if (stt.equals("1")) {

                        Pattern pattern = Pattern.compile("(?<=_id\":\")[\\s\\S]*?(?=\")",Pattern.MULTILINE);
                        Pattern patternName = Pattern.compile("(?<=fullname\":\")[\\s\\S]*?(?=\")",Pattern.MULTILINE);


                        ArrayList<String> tasks = new ArrayList<String>();
                        Matcher matcher = pattern.matcher(myRes);
                        Matcher matcherName = patternName.matcher(myRes);

                        while (matcher.find()){
                            ((Activity)getContext()).runOnUiThread(new Runnable()
                            {
                                public void run()
                                {

                                    while (matcherName.find()){

                                        tvloading.setText("Xin chào! - "+matcherName.group(0));

                                    }
                                }
                            });
                            loginfunc(matcher.group(0),password);
                        }




                    } else {


                        ((Activity)getContext()).runOnUiThread(new Runnable()
                        {
                            public void run()
                            {
                                tvloading.setText("Tên đăng nhập không chính xác!");

                                }

                        });
                        SystemClock.sleep(3000);
                        dialog.cancel();
                    }


                }else {

                    ((Activity)getContext()).runOnUiThread(new Runnable()
                    {
                        public void run()
                        {
                            tvloading.setText("Lỗi máy chủ!");

                        }

                    });
                    SystemClock.sleep(3000);
                    dialog.cancel();
                }
            }
        });


        return "err notyet";

    }


    public Returnobject loginfunc(String username, String password) {

        final String[] sso_token = {"undefined"};
        final String[] refresh_token = {"undefined"};
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(MediaType.get("application/json"), "{\"type\":\"user\",\"_id\":\""+username+"\",\"passwd\":\""+password+"\"}");
        String url = "https://api.dhdt.vn/account/login/passwd";
        Request request = new Request.Builder().addHeader("accept", "application/json, text/plain, */*")
                .addHeader("refresh_token", refresh_token[0])
                .addHeader("sso_token", sso_token[0])
                .addHeader("if-none-match", "W/\"b5b-3+NZGVqGPC6cHnb+39bL/VlxSY4\"")
                .addHeader("agent", "{\"brower\":\"SVOapp\",\"version\":\"6.1.3\",\"device_name\":\"twzsv\",\"unique_device_id\":\"8DA9BD10 - B0D0 - 4808 - AB34 - 4AF30AA044EC\",\"user_agent\":\"Mozilla / 5.0(iPhone; CPU iPhone OS 13_6_1 like Mac OS X) AppleWebKit / 605.1.15(KHTML, like Gecko) Mobile / 15E148\",\"system_name\":\"iOS\",\"device_model\":\"iPhone 7\",\"system_version\":\"13.6.1\"}")
                .post(body)
                .url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
                myTasks="fail";
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {



                    final String myRes = response.body().string();

                    String stt=checkAndGet(myRes, 7);
                    // tv1.setText(myRes);
                    if (stt.equals("1")) {

                        ((Activity)getContext()).runOnUiThread(new Runnable()
                        {
                            public void run()
                            {
                                tvloading.setText("Đăng nhập thành công!");

                            }

                        });

                        sso_token[0] =checkAndGet(myRes,2);
                        refresh_token[0] =checkAndGet(myRes,3);



                       getTaskFromSVO(sso_token[0],refresh_token[0]);





                    } else {

                        ((Activity)getContext()).runOnUiThread(new Runnable()
                        {
                            public void run()
                            {
                                tvloading.setText("Mật khẩu không chính xác!");

                            }

                        });
                        SystemClock.sleep(3000);
                        dialog.cancel();

                    }


                }

            }
        });


        return new Returnobject( sso_token[0], refresh_token[0]);

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
                ((Activity)getContext()).runOnUiThread(new Runnable()
                {
                    public void run()
                    {
                        tvloading.setText("Lỗi kết nối!");

                    }

                });
                myTasks="fail";
            }

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myRes = response.body().string();

                    String stt=checkAndGet(myRes, 5);

                    if (stt.compareTo("1")==0) {

                        HomeFragment a=new HomeFragment();
                        a.getEventsFromsvo(myRes);



                        ((Activity)getContext()).runOnUiThread(new Runnable()
                        {
                            public void run()
                            {
                                tvloading.setText("Lấy lịch học thành công ^.^!");

                            }

                        });
                        SystemClock.sleep(3000);
                        dialog.cancel();




                    } else {


                        ((Activity)getContext()).runOnUiThread(new Runnable()
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
                    ((Activity)getContext()).runOnUiThread(new Runnable()
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






    class Returnobject {


        public   String sso;
        public String refresh;

        Returnobject(String _sso,String _refresh){
            this.sso=_sso;
            this.refresh=_refresh;

        }


    }



}