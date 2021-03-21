package com.example.testnavbottom;

import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

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

public class internetClass {
    String myTasks="not yet";


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


        return "error";
    }

    class Returnobject {


        public   String sso;
        public String refresh;

        Returnobject(String _sso,String _refresh){
            this.sso=_sso;
            this.refresh=_refresh;

        }


    }
//5b7c2bbaad2d080d6c44dd36
    //Leminh77

    public String getTaskFromSVO(String sso,String refresh_token,LayoutInflater inflater, ViewGroup container ){


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
                myTasks="fail";
            }

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myRes = response.body().string();

                            String stt=checkAndGet(myRes, 1);

                            if (stt.equals("1")) {

                                HomeFragment a=new HomeFragment();
                                a.getEventsFromsvo(myRes,inflater,container);

                                Log.d("abc","congrats");




                            } else {

                            }


                }

            }
        });


        return "";
    }





    public String checkSmartName(String smartName, String password ,LayoutInflater inflater, ViewGroup container) {

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
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myRes = response.body().string();

                            String stt=checkAndGet(myRes, 1);
                            // tv1.setText(myRes);
                            if (stt.equals("1")) {

                                Pattern pattern = Pattern.compile("(?<=_id\":\")[\\s\\S]*?(?=\")",Pattern.MULTILINE);


                                ArrayList<String> tasks = new ArrayList<String>();
                                Matcher matcher = pattern.matcher(myRes);

                                while (matcher.find()){
                                    loginfunc(matcher.group(0),password,inflater,container);
                                }




                            } else {

                            }


                }

            }
        });


        return "err notyet";

    }







    public Returnobject loginfunc(String username, String password,LayoutInflater inflater, ViewGroup container) {

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

                            String stt=checkAndGet(myRes, 1);
                            // tv1.setText(myRes);
                            if (stt.equals("1")) {

                                sso_token[0] =checkAndGet(myRes,2);
                                refresh_token[0] =checkAndGet(myRes,3);
                                getTaskFromSVO(sso_token[0],refresh_token[0],inflater,container);





                            } else {

                            }


                }

            }
        });


        return new Returnobject( sso_token[0], refresh_token[0]);

    }





}
