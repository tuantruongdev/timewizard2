package com.example.testnavbottom.ui.notifications;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.testnavbottom.MainActivity;
import com.example.testnavbottom.R;
import com.example.testnavbottom.reponseClass;
import com.example.testnavbottom.ui.home.HomeFragment;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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




public class loginClass extends AppCompatActivity {
    String myTasks = "";
    AlertDialog dialog;
    TextView tvloading;
    TextView alertLogin;
    private static final String FILE_NAME = "info.txt";
    ImageView profile;
    EditText tvuser;
    EditText tvpassword;
    Button btnlogin;
    final String[] sso_token = {"undefined"};
    final String[] refresh_token = {"undefined"};
    final String[] ids = {"undefined"};
    final String[] img250 = {"undefined"};
    final String[] fullname = {"undefined"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int nightModeFlags =
                this.getBaseContext().getResources().getConfiguration().uiMode &
                        Configuration.UI_MODE_NIGHT_MASK;
        switch (nightModeFlags) {
            case Configuration.UI_MODE_NIGHT_YES:
                this.requestWindowFeature(Window.FEATURE_NO_TITLE);
                getSupportActionBar().hide();
                break;

            case Configuration.UI_MODE_NIGHT_NO:
                this.requestWindowFeature(Window.FEATURE_NO_TITLE);

                break;

            case Configuration.UI_MODE_NIGHT_UNDEFINED:

                break;
        }

        setContentView(R.layout.loginlayout);

        tvuser = findViewById(R.id.usernamelg);

        tvpassword = findViewById(R.id.passwordlg);
        btnlogin = findViewById(R.id.btnlogin);
        alertLogin = findViewById(R.id.alertLogin);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayLoading(tvuser.getText().toString(), tvpassword.getText().toString());
            }
        });
      /*
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayLoading(tvuser.getText().toString(),tvpassword.getText().toString());



            }
        });*/

    }








    void displayLoading(String username, String password) {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.loading_layouts, null);
        tvloading = alertLayout.findViewById(R.id.tvstatus);

        //eerrr here
        AlertDialog.Builder alert = new AlertDialog.Builder(loginClass.this);

        alert.setView(alertLayout);
        alert.setCancelable(true);

        dialog = alert.create();
        dialog.show();
        checkSmartName(username, password, alertLayout);
        //  downloadImage("https://halustorage-hn.ss-hn-1.vccloud.vn/60011b3b8003bf099275ca6d.jpg");

    }


    String checkAndGet(String myRes, int mode) {
        Gson gson1 = new Gson();
        reponseClass mtask = gson1.fromJson(myRes, reponseClass.class);
        if (mode == 1) {

            return "1";
        }
        if (mode == 2) {
            return mtask.sso_token;
        }
        if (mode == 3) {
            return mtask.refresh_token;
        }

        if (mode == 5 && mtask._id.compareTo("1") == 0) {
            return "1";
        }

        if (mode == 6 && myRes.compareTo("{\"list_acc\":[]}") != 0) {
            return "1";
        }
        if (mode == 7 && mtask.stt.compareTo("success") == 0) {
            return "1";
        }
        if (mode == 8 && myRes.compareTo("{\"list_acc\":[]}") != 0) {
            return mtask.img250;

        }
        if (mode == 9 && myRes.compareTo("{\"list_acc\":[]}") != 0) {
            return mtask.fullname;

        }
        if (mode == 10 && myRes.compareTo("{\"list_acc\":[]}") != 0) {
            return mtask.ids;

        }

        return "error";
    }


    public String checkSmartName(String smartName, String password, View alertLayout) {

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(MediaType.get("application/json"), "{\"smartname\":\"" + smartName + "\",\"acc_type\":\"user\"}");
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
                myTasks = "fail";
                try {

                    (loginClass.this).runOnUiThread(new Runnable() {
                        public void run() {
                            tvloading.setText("Không thể kết nối đến máy chủ!");

                        }

                    });
                } catch (Exception e1) {
                }
                SystemClock.sleep(3000);
                dialog.cancel();

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {

                    final String myRes = response.body().string();

                    String stt = checkAndGet(myRes, 6);
                    // tv1.setText(myRes);
                    if (stt.equals("1")) {

                        Pattern pattern = Pattern.compile("(?<=_id\":\")[\\s\\S]*?(?=\")", Pattern.MULTILINE);
                        Pattern patternName = Pattern.compile("(?<=fullname\":\")[\\s\\S]*?(?=\")", Pattern.MULTILINE);
                        Pattern userinfopattern = Pattern.compile("(?<=\\[)[\\s\\S]*?(?=\\])", Pattern.MULTILINE);

                        ArrayList<String> tasks = new ArrayList<String>();
                        Matcher matcher = pattern.matcher(myRes);
                        Matcher matcherName = patternName.matcher(myRes);
                        Matcher userInfoMatcher = userinfopattern.matcher(myRes);

                        while (matcher.find()) {
                            (loginClass.this).runOnUiThread(new Runnable() {
                                public void run() {

                                    while (userInfoMatcher.find()) {
                                        ids[0] = checkAndGet(userInfoMatcher.group(0), 10);
                                        fullname[0] = checkAndGet(userInfoMatcher.group(0), 9);
                                        img250[0] = checkAndGet(userInfoMatcher.group(0), 8);
                                    }

                                    while (matcherName.find()) {

                                        tvloading.setText("Xin chào! - " + matcherName.group(0));

                                    }
                                }
                            });
                            loginfunc(matcher.group(0), password);
                        }

                    } else {

                        (loginClass.this).runOnUiThread(new Runnable() {
                            public void run() {
                                tvloading.setText("Tên đăng nhập không chính xác!");
                                alertLogin.setText("Tên đăng nhập không chính xác!");
                            }

                        });
                        SystemClock.sleep(3000);
                        dialog.cancel();
                    }

                } else {

                    (loginClass.this).runOnUiThread(new Runnable() {
                        public void run() {
                            tvloading.setText("Lỗi máy chủ!");
                            alertLogin.setText("Lỗi máy chủ!");
                        }

                    });
                    SystemClock.sleep(3000);
                    dialog.cancel();
                }
            }
        });

        return "err notyet";

    }


    public void loginfunc(String username, String password) {

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(MediaType.get("application/json"), "{\"type\":\"user\",\"_id\":\"" + username + "\",\"passwd\":\"" + password + "\"}");
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
                myTasks = "fail";
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {

                    final String myRes = response.body().string();

                    String stt = checkAndGet(myRes, 7);
                    // tv1.setText(myRes);
                    if (stt.equals("1")) {

                        (loginClass.this).runOnUiThread(new Runnable() {
                            public void run() {
                                tvloading.setText("Đăng nhập thành công!");

                            }

                        });

                        File fdelete = new File(FILE_NAME);
                        if (fdelete.exists()) {
                            if (fdelete.delete()) {
                                System.out.println("file Deleted :");
                            } else {
                                System.out.println("file not Deleted :");
                            }
                        }
                        File fdelete2 = new File("profile.png");
                        if (fdelete.exists()) {
                            if (fdelete.delete()) {
                                System.out.println("file Deleted :");
                            } else {
                                System.out.println("file not Deleted :");
                            }
                        }
                        SystemClock.sleep(2000);

                        downloadImage(img250[0]);
                        sso_token[0] = checkAndGet(myRes, 2);
                        refresh_token[0] = checkAndGet(myRes, 3);

                        save(sso_token[0] + "|" + refresh_token[0] + "|" + ids[0] + "|" + fullname[0] + "|" + img250[0]);
                        SystemClock.sleep(3000);
                        dialog.cancel();
                        finish();

                        Intent intent = new Intent(loginClass.this, MainActivity.class);
                        intent.putExtra("action", "dashboard");
                        startActivity(intent);
                        overridePendingTransition(0, 0);

                        //     getTaskFromSVO(sso_token[0],refresh_token[0]);

                    } else {

                        (loginClass.this).runOnUiThread(new Runnable() {
                            public void run() {
                                tvloading.setText("Mật khẩu không chính xác!");
                                alertLogin.setText("Mật khẩu không chính xác!");
                            }

                        });
                        SystemClock.sleep(3000);
                        dialog.cancel();

                    }

                }

            }
        });

    }


    public void save(String text) {

        FileOutputStream fos = null;
        try {
            fos = loginClass.this.openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write(text.getBytes());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String load() {
        FileInputStream fis = null;
        String text = " ";
        try {

            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();

            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }
        } catch (FileNotFoundException e) {
            text = "nofile";
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return text;
    }








    public void downloadImage(String image250) {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(MediaType.get("image/jpeg"), "");
        String url = image250;
        final Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
                myTasks = "fail";
                try {
                    Log.d("err", "network failed!");

                } catch (Exception e1) {
                }

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {

                    (loginClass.this).runOnUiThread(new Runnable() {
                        public void run() {

                            final Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());
                            try (FileOutputStream fos = loginClass.this.openFileOutput("profile.png", MODE_PRIVATE)) {
                                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos); // bmp is your Bitmap instance
                                // PNG is a lossless format, the compression factor (100) is ignored
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }

                    });

                    Log.d("image", "saved");
                } else {

                    Log.d("err", "reponse failed!");

                }

            }
        });

    }





    public String getTaskFromSVO(String sso, String refresh_token) {

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
                (loginClass.this).runOnUiThread(new Runnable() {
                    public void run() {
                        tvloading.setText("Lỗi kết nối!");
                        alertLogin.setText("Lỗi kết nối!");
                    }

                });
                myTasks = "fail";
            }

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myRes = response.body().string();

                    String stt = checkAndGet(myRes, 5);

                    if (stt.compareTo("1") == 0) {

                        HomeFragment a = new HomeFragment();
                        a.getEventsFromsvo(myRes);

                        (loginClass.this).runOnUiThread(new Runnable() {
                            public void run() {
                                tvloading.setText("Lấy lịch học thành công ^.^!");
                                SystemClock.sleep(3000);
                                dialog.cancel();
                                finish();

                                Intent intent = new Intent(loginClass.this, MainActivity.class);
                                intent.putExtra("action", "dashboard");
                                startActivity(intent);
                                overridePendingTransition(0, 0);
                            }

                        });

                    } else {

                        (loginClass.this).runOnUiThread(new Runnable() {
                            public void run() {
                                tvloading.setText("Lấy lịch học thất bại!");

                            }

                        });
                        SystemClock.sleep(3000);
                        dialog.cancel();

                    }

                } else {
                    (loginClass.this).runOnUiThread(new Runnable() {
                        public void run() {
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


        public String sso;
        public String refresh;

        Returnobject(String _sso, String _refresh) {
            this.sso = _sso;
            this.refresh = _refresh;

        }

    }

}
