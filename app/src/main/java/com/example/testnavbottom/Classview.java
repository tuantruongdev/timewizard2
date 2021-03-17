package com.example.testnavbottom;

public class Classview {
    private String time;
    private  String info;
    private  String weekday;
    private  String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Classview(String time, String info,String weekday,String date) {
        this.time = time;
        this.info = info;
        this.weekday=weekday;
        this.date= date;

    }
}
