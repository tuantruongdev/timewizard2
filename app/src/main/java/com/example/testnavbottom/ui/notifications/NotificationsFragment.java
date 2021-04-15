package com.example.testnavbottom.ui.notifications;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.testnavbottom.R;
import com.example.testnavbottom.reponseClass;
import com.example.testnavbottom.ui.home.HomeFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

import static android.content.Context.MODE_PRIVATE;

public class NotificationsFragment extends Fragment {
    String myTasks = "";
    AlertDialog dialog;
    TextView tvloading;
    private static final String FILE_NAME = "info.txt";
    ImageView profile;
    TextView tvMsv;
    TextView tvName;

    private NotificationsViewModel notificationsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        FloatingActionButton addbtn = root.findViewById(R.id.floatingBtnaddQR);
        FloatingActionButton loginout = root.findViewById(R.id.floatingBtnaddlogout);
        FloatingActionButton btnsetting = root.findViewById(R.id.floatingBtnSetting);
        profile = root.findViewById(R.id.imageview1);
        tvMsv = root.findViewById(R.id.msv);
        tvName = root.findViewById(R.id.tvname);
        FileInputStream in = null;
        try {
            in = getContext().openFileInput("profile.png");
            profile.setImageBitmap(BitmapFactory.decodeStream(in));

            try {
                String info = load();
                if (info != null && info.compareTo("nofile") != 0) {
                    String[] arrInfo = info.split("\\|", 5);
                    if (arrInfo[3] != null && arrInfo[2] != null) {
                        tvName.setText(arrInfo[3]);
                        tvMsv.setText(arrInfo[2]);
                    }
                }
            } catch (Exception e) {

            }

        } catch (FileNotFoundException e) {

        }

        btnsetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), settingClass.class);
                startActivity(intent);

            }
        });

        loginout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                File file = new File("data/data/com.example.timewizard/files/info.txt");
                file.delete();
                file = new File("data/data/com.example.timewizard/files/profile.png");
                file.delete();
///data/data/com.example.timewizard/files



                Intent intent = new Intent(getContext(), loginClass.class);
                startActivity(intent);

            }
        });

        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayQR(tvMsv.getText().toString());

            }
        });

        TextView textView = root.findViewById(R.id.tvrefreshtaskFromSvo);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), aboutProductClass.class);
                startActivity(intent);

                //  Intent intent = new Intent( getContext(), LoadingActivity.class);
                //startActivity(intent);
                //    internetClass a= new internetClass();
                //     a.checkSmartName("DTC18548010300766","Leminh77",inflater,container);
                //   displayLoading();

            }
        });

        return root;
    }


    void displayQR(String content) {

        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.qrcode_layout, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setView(alertLayout);
        alert.setCancelable(true);
        if (content.compareTo(" ") != 0) {
            dialog = alert.create();
            dialog.show();
            //   dialog.getWindow().setLayout(1200, 1200);
            QRCodeWriter writer = new QRCodeWriter();
            try {
                BitMatrix bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, 512, 512);
                int width = bitMatrix.getWidth();
                int height = bitMatrix.getHeight();
                Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
                for (int x = 0; x < width; x++) {
                    for (int y = 0; y < height; y++) {
                        bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                    }
                }
                ((ImageView) alertLayout.findViewById(R.id.QRimageview)).setImageBitmap(bmp);

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            ((Activity) getContext()).runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getContext(), "Bạn chưa đăng nhập!", Toast.LENGTH_SHORT).show();

                }

            });

        }
    }







    void displayLoading() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.loading_layouts, null);
        tvloading = alertLayout.findViewById(R.id.tvstatus);
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());

        alert.setView(alertLayout);
        alert.setCancelable(true);

        dialog = alert.create();
        dialog.show();
        checkSmartName("DTC1854801030076", "Leminh77", alertLayout);
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

                    ((Activity) getContext()).runOnUiThread(new Runnable() {
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
                    String img250;
                    if (stt.equals("1")) {
                        img250 = checkAndGet(myRes, 8);
                        //  downloadImage(img250);

                                            /*
                        ids[0]=checkAndGet(myRes,10);
                        fullname[0]=checkAndGet(myRes,9);

                        sso_token[0] =checkAndGet(myRes,2);
                        refresh_token[0] =checkAndGet(myRes,3);
                        save(sso_token[0]+"|"+refresh_token+"|"+ids[0]+"|"+fullname[0]+"|"+img250[0]);
*/
                        //sso error

                        Pattern pattern = Pattern.compile("(?<=_id\":\")[\\s\\S]*?(?=\")", Pattern.MULTILINE);
                        Pattern patternName = Pattern.compile("(?<=fullname\":\")[\\s\\S]*?(?=\")", Pattern.MULTILINE);

                        ArrayList<String> tasks = new ArrayList<String>();
                        Matcher matcher = pattern.matcher(myRes);
                        Matcher matcherName = patternName.matcher(myRes);

                        while (matcher.find()) {
                            ((Activity) getContext()).runOnUiThread(new Runnable() {
                                public void run() {

                                    while (matcherName.find()) {

                                        tvloading.setText("Xin chào! - " + matcherName.group(0));

                                    }
                                }
                            });
                            loginfunc(matcher.group(0), password);
                        }

                    } else {

                        ((Activity) getContext()).runOnUiThread(new Runnable() {
                            public void run() {
                                tvloading.setText("Tên đăng nhập không chính xác!");

                            }

                        });
                        SystemClock.sleep(3000);
                        dialog.cancel();
                    }

                } else {

                    ((Activity) getContext()).runOnUiThread(new Runnable() {
                        public void run() {
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

                        ((Activity) getContext()).runOnUiThread(new Runnable() {
                            public void run() {
                                tvloading.setText("Đăng nhập thành công!");

                            }

                        });

                        //          getTaskFromSVO(sso_token[0],refresh_token[0]);

                    } else {

                        ((Activity) getContext()).runOnUiThread(new Runnable() {
                            public void run() {
                                tvloading.setText("Mật khẩu không chính xác!");

                            }

                        });
                        SystemClock.sleep(3000);
                        dialog.cancel();

                    }

                }

            }
        });

        return new Returnobject(sso_token[0], refresh_token[0]);

    }


    public void save(String text) {

        FileOutputStream fos = null;
        try {
            fos = getContext().openFileOutput(FILE_NAME, MODE_PRIVATE);
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
        String ret = "";

        try {
            InputStream inputStream = getContext().openFileInput(FILE_NAME);

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append("\n").append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
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

                    ((Activity) getContext()).runOnUiThread(new Runnable() {
                        public void run() {

                            final Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());
                            try (FileOutputStream fos = getContext().openFileOutput("profile.png", MODE_PRIVATE)) {
                                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos); // bmp is your Bitmap instance
                                // PNG is a lossless format, the compression factor (100) is ignored
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            FileInputStream in = null;
                            try {
                                in = getContext().openFileInput("profile.png");
                                profile.setImageBitmap(BitmapFactory.decodeStream(in));

                            } catch (FileNotFoundException e) {
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
                ((Activity) getContext()).runOnUiThread(new Runnable() {
                    public void run() {
                        tvloading.setText("Lỗi kết nối!");

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

                        ((Activity) getContext()).runOnUiThread(new Runnable() {
                            public void run() {
                                tvloading.setText("Lấy lịch học thành công ^.^!");

                            }

                        });
                        SystemClock.sleep(3000);
                        dialog.cancel();

                    } else {

                        ((Activity) getContext()).runOnUiThread(new Runnable() {
                            public void run() {
                                tvloading.setText("Lấy lịch học thất bại!");

                            }

                        });
                        SystemClock.sleep(3000);
                        dialog.cancel();

                    }

                } else {
                    ((Activity) getContext()).runOnUiThread(new Runnable() {
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