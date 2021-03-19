package com.example.testnavbottom;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

public class database {
    private String title = "";
    private int enable = 1;
    private String descr = "";
    private String startat = "";
    private String endat = "";
    private String type = "";
    private String arlamat = "";
    private String note = "";



    database(){};

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getEnable() {
        return enable;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getStartat() {
        return startat;
    }

    public void setStartat(String startat) {
        this.startat = startat;
    }

    public String getEndat() {
        return endat;
    }

    public void setEndat(String endat) {
        this.endat = endat;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getArlamat() {
        return arlamat;
    }

    public void setArlamat(String arlamat) {
        this.arlamat = arlamat;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }



    boolean isTableExist(SQLiteDatabase db, String table) {
        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name=?", new String[]{table});
        boolean tableExist = (cursor.getCount() != 0);
        cursor.close();
        return tableExist;
    }

    public boolean addItemDB() {

        SQLiteDatabase db = null;
        try {
            db = SQLiteDatabase.openOrCreateDatabase("/data/data/com.example.timewizard/databases/mydb.db", null);
        } catch (
                SQLiteException ex) {
            //Lỗi kết nối
            Log.e("err", ex.getMessage());
        }


        //    SQLiteDatabase db = openOrCreateDatabase(MainActivity.DB_NAME, Context.MODE_PRIVATE, null);
        this.title = "Cong Nhe phan mem";
        this.descr = "day la dong desc";
        boolean isupdate = false;
        if (isupdate) {
            //Cập nhật
            db.execSQL("UPDATE Event SET name=?, price = ? where id = ?",
                    new String[]{"", this.title, this.descr, this.startat, this.endat, this.type, this.arlamat, this.note, String.valueOf(this.enable)});
        } else {
            //Tạo
            //Cập nhật
            db.execSQL("INSERT INTO Event (id,title,descr,startat,endat,type,arlamat,note,enable ) VALUES (?,?,?,?,?,?,?,?,?)",
                    new String[]{"", this.title, this.descr, this.startat, this.endat, this.type, this.arlamat, this.note, String.valueOf(this.enable)});
        }
        db.close();

        return true;
    }





    public void defaultDB(){




    }



}
