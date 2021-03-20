package com.example.testnavbottom;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.io.Console;
import java.sql.SQLData;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "ProductDbHelper";
    private static final String DATABASE_NAME = "myproduct.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_PRODUCT = "events";
    private String title = "";
    private int enable = 1;
    private String descr = "";
    private String startat = "";
    private String endat = "";
    private String type = "";
    private String alarmat = "";
    private String note = "";
    private  int currentDateIndex=0;

    public int getCurrentDateIndex() {
        return currentDateIndex;
    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Phương thức này tự động gọi nếu storage chưa có DATABASE_NAME
    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.i(TAG, "Create table");
        String queryCreateTable = "CREATE TABLE " + TABLE_PRODUCT + " ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title VARCHAR (255) NOT NULL, " +
                "descr VARCHAR (255) NOT NULL, " +
                "startat VARCHAR (255) NOT NULL, " +
                "endat VARCHAR (255) NOT NULL, " +
                "type VARCHAR (255) NOT NULL, " +
                "note VARCHAR (255) NOT NULL, " +
                "alarmat VARCHAR (255) NOT NULL, " +
                "enable INT NOT NULL " +

                ")";

        db.execSQL(queryCreateTable);
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
        return arrOfStr[opt];
    }


    public Boolean checkscroll(String mydate){

        Calendar cal1 = GregorianCalendar.getInstance();
      //  int date=  cal1.get(Calendar.YEAR);
        if (Integer.parseInt(getdate(mydate,0))==cal1.get(Calendar.YEAR)&&Integer.parseInt(getdate(mydate,1))==cal1.get(Calendar.MONTH)+1&&Integer.parseInt(getdate(mydate,2))==cal1.get(Calendar.DAY_OF_MONTH)){
            return  true;
        }



        return false;
    }


    //Phương thức này tự động gọi khi đã có DB trên Storage, nhưng phiên bản khác
    //với DATABASE_VERSION
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Xoá bảng cũ
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
        //Tiến hành tạo bảng mới
        onCreate(db);
    }


    public int getDayIndex(ArrayList<Classview> events) {

      //  ArrayList<Classview> event = new ArrayList<Classview>();


        //Đến dòng đầu của tập dữ liệu




        int i=0;


        while (i < events.size()) {
            if (checkscroll(events.get(i).getStartat())) {
                currentDateIndex = i;

            }
            i++;
        }







        return currentDateIndex;
    }


    //Lấy toàn bộ SP
    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<Classview> getAllProducts() {

        ArrayList<Classview> event = new ArrayList<Classview>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id, title, descr,startat,endat,enable,type,alarmat  from events", null);

        //Đến dòng đầu của tập dữ liệu
        cursor.moveToFirst();
        int i=0;

        //dddddddddddajwhdlkahjdlawd
        Classview nClass=   new Classview("Bạn không có lịch hôm nay, hãy thêm lịch nhé~!",1,"", "1970-01-01 00:00:00"," ",2,"","","1",-1);
        Cursor oldCusor =cursor;
        int ftime=1;
        String olddate="";
        while (!cursor.isAfterLast()) {




            int eventID = cursor.getInt(0);
            String eventtitle = cursor.getString(1);
            String eventdescr = cursor.getString(2);
            String eventstartat = cursor.getString(3);
            String eventendat = cursor.getString(4);
            int eventenable = cursor.getInt(5);
            String eventalarmat = cursor.getString(7);
            int eventtype= cursor.getInt(6);


// request add vao database, truong hop su kien trong, insert cot vao db



        if(ftime==1){
            nClass=   new Classview(eventtitle,eventenable,eventdescr,eventstartat,eventendat,eventtype,eventalarmat,eventalarmat,"1",eventID);

            //   nClass.getTitle();

            event.add(nClass);
            olddate = cursor.getString(3);
            // nClass.getTitle();
            cursor.moveToNext();
            i++;
            ftime=0;
            continue;
        }else {




            String tempDay = olddate;

            String mTempday4 = getdate(tempDay, 2);
            String mTempmonth4 = getdate(tempDay, 1);
            String mTempyear4 = getdate(tempDay, 0);

            String mTempday5 = getdate(eventstartat, 2);
            String mTempmonth5 = getdate(eventstartat, 1);
            String mTempyear5 = getdate(eventstartat, 0);

            String mTempyear9 = getdate(eventstartat, 0);
        //check neu 2 task gan nhau thi bo qua
//dawhjkkkkkkkkkkkkkkk
            Calendar cal = GregorianCalendar.getInstance();
            Calendar oldcal = GregorianCalendar.getInstance();
            int flag=0;

        cal.set(Integer.parseInt(mTempyear4) ,Integer.parseInt(mTempmonth4 ),Integer.parseInt( mTempday4));
                    LocalDate dt1 = LocalDate.parse(mTempyear4+"-"+mTempmonth4+"-"+mTempday4);
                  //  cal.add(Calendar.DATE,-1);
                    LocalDate dt2 = LocalDate.parse(mTempyear5+"-"+mTempmonth5+"-"+mTempday5);
                 dt2=   dt2.minusDays(1);
//Integer.parseInt(mTempday4)<Integer.parseInt(mTempday5)-1

            Log.d("cac",cal.getTime().toString());
         //   dt1.isAfter(dt2)
            if (dt2.isAfter(dt1)){

                String time1 = mTempyear4+"-"+mTempmonth4+"-"+String.valueOf(Integer.parseInt(mTempday4)) ;  // Start date
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c1 = Calendar.getInstance();
                try {
                    c1.setTime(sdf.parse(time1));
                }catch (Exception e){

                }


                c1.add(Calendar.DATE,1);

                String timeC1 =sdf.format(c1.getTime())+" 01:01:01";



                String time2 = mTempyear5+"-"+mTempmonth5+"-"+String.valueOf(Integer.parseInt(mTempday5)) ;  // Start date
                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c2 = Calendar.getInstance();
                try {
                    c2.setTime(sdf2.parse(time1));
                }catch (Exception e){

                }

                //  cal.set(Integer.valueOf(getdate(startat,0)), Integer.valueOf(getdate(startat,1)), Integer.valueOf(getdate(startat,2)));



                String timeC2 =sdf.format(c2.getTime())+" 01:01:01";




                if (timeC1!=timeC2 ){



                        nClass = new Classview("Bạn không có lịch hôm nay, hãy thêm lịch nhé~!", 1, "", timeC1, " ", 2, "", "", "1", -1);
                        event.add(nClass);
                        i++;

                        //set next day here
               // Calendar cal = GregorianCalendar.getInstance();
                //   cal.set(Integer.valueOf(getdate(startat,0)), Integer.valueOf(getdate(startat,1)), Integer.valueOf(getdate(startat,2)));
                String dt3 = mTempyear4+"-"+mTempmonth4+"-"+String.valueOf(Integer.parseInt(mTempday4)) ;
                SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c3 = Calendar.getInstance();
               try {
                   c3.setTime(sdf3.parse(dt3));
               }catch (Exception e){}

                //  cal.set(Integer.valueOf(getdate(startat,0)), Integer.valueOf(getdate(startat,1)), Integer.valueOf(getdate(startat,2)));

                c3.add(Calendar.DATE,1);

                olddate =sdf.format(c3.getTime())+" 01:01:01";
                     ///   olddate = mTempyear5+"-"+mTempmonth5+"-"+String.valueOf(Integer.parseInt(mTempday4)+1)+" 00:00:00";
                        continue;

                     }


            }


            nClass = new Classview(eventtitle, eventenable, eventdescr, eventstartat, eventendat, eventtype, eventalarmat, eventalarmat, "1", eventID);

            //   nClass.getTitle();

            event.add(nClass);

            olddate = cursor.getString(3);
            // nClass.getTitle();
            cursor.moveToNext();
            i++;
            continue;
        }


          //  Calendar cal = GregorianCalendar.getInstance();
         //   cal.set(Integer.valueOf(getdate(startat,0)), Integer.valueOf(getdate(startat,1)), Integer.valueOf(getdate(startat,2)));




        }

        cursor.close();
       // Classview a= event.get(5);

        return event;
    }


    //Lấy một SP biết ID
    public Classview getProductByID(int ID) {
        Classview classEvent = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id, title, descr,startat,endat,enable,type,alarmat from events where id = ?",
                new String[]{ID + ""});

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            int eventID = cursor.getInt(0);
            String eventtitle = cursor.getString(1);
            String eventdescr = cursor.getString(2);
            String eventstartat = cursor.getString(3);
            String eventendat = cursor.getString(4);
            int eventenable = cursor.getInt(5);
            String eventalarmat = cursor.getString(7);
            int eventtype= cursor.getInt(6);







            classEvent = new Classview(eventtitle,eventenable,eventdescr,eventstartat,eventendat,eventtype,eventalarmat,"","1",0);
        }
        cursor.close();
        return classEvent;
    }

    //Cập nhật
    void updateProduct(Classview classEvent) {
        SQLiteDatabase db = getWritableDatabase();
      //  db.execSQL("UPDATE events SET title=?, startat = ? where id = ?",
         //      new String[]{classEvent.getInfo(), classEvent.getTime() +  "1"});
    }

    //Chèn mới
  public   void insertProduct(Classview classEvent) {
        SQLiteDatabase db = getWritableDatabase();
    // String[] a= new String[]{classEvent.getTitle(), classEvent.getDescr(),classEvent.getStartat(),classEvent.getEndat(),classEvent.getType(),classEvent.getNote(),classEvent.getNote()};
        db.execSQL("INSERT INTO events (title,descr,startat,endat,type,note,alarmat,enable) VALUES (?,?,?,?,?,?,?,?)",
                new String[]{classEvent.getTitle(), classEvent.getDescr(),classEvent.getStartat(),classEvent.getEndat(),String.valueOf(classEvent.getType()),classEvent.getNote(),classEvent.getNote(),enable+ ""});
    }

    //Xoá sản phẩm khỏi DB
  public   void deleteProductByID(int eventID) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM events where id = ?", new String[]{String.valueOf(eventID)});
    }
    public   void deleteallevent() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM events where id NOT NULL");
    }
}


