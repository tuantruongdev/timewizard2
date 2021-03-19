package com.example.testnavbottom;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.Console;
import java.sql.SQLData;
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
      int year= cal1.get(Calendar.MONTH);
        Log.d("xxxxxxxxxx----------------------", String.valueOf(year));
        //   if (cal.after)


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


    public int getDayIndex() {

        ArrayList<Classview> event = new ArrayList<Classview>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id, title, descr,startat,endat,enable,type,alarmat  from events", null);

        //Đến dòng đầu của tập dữ liệu
        cursor.moveToFirst();
        int i=0;
        while (!cursor.isAfterLast()) {

            int eventID = cursor.getInt(0);
            String eventtitle = cursor.getString(1);
            String eventdescr = cursor.getString(2);
            String eventstartat = cursor.getString(3);
            String eventendat = cursor.getString(4);
            int eventenable = cursor.getInt(5);
            String eventalarmat = cursor.getString(7);
            int eventtype= cursor.getInt(6);



            if ( checkscroll(eventstartat)) {
                currentDateIndex =i;
            }
            //   nClass.getTitle();


            // nClass.getTitle();
            cursor.moveToNext();
            i++;
        }

        cursor.close();


        return currentDateIndex;
    }


    //Lấy toàn bộ SP
    public ArrayList<Classview> getAllProducts() {

        ArrayList<Classview> event = new ArrayList<Classview>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id, title, descr,startat,endat,enable,type,alarmat  from events", null);

        //Đến dòng đầu của tập dữ liệu
        cursor.moveToFirst();
        int i=0;
        while (!cursor.isAfterLast()) {

            int eventID = cursor.getInt(0);
            String eventtitle = cursor.getString(1);
            String eventdescr = cursor.getString(2);
            String eventstartat = cursor.getString(3);
            String eventendat = cursor.getString(4);
            int eventenable = cursor.getInt(5);
            String eventalarmat = cursor.getString(7);
            int eventtype= cursor.getInt(6);





             Classview nClass=   new Classview(eventtitle,eventenable,eventdescr,eventstartat,eventendat,eventtype,eventalarmat,eventalarmat,"1",eventID);

       //   nClass.getTitle();

            event.add(nClass);
           // nClass.getTitle();
            cursor.moveToNext();
            i++;
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


