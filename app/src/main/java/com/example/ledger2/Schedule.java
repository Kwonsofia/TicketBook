package com.example.ledger2;

import android.graphics.Bitmap;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Schedule {
    public String id;  //사용자 아이디
    public String title;
    public int year;  //년도
    public int month;  //월
    public int date;  //일
    //    public int expense; //지출
//    public String userid;
//    public String userName;
//    public String location;
    public int hour;
    public int min;
    public String pmam;
    public String detail;
    public Bitmap imgUri;

    Schedule() {
//        String id;
        String title;
        int year;
        int month;
        int date;
        int hour;
        int min;
        String pmam;
        String detail;
        Bitmap imgUri;
    }

    Schedule(String title, int year, int month, int date, int hour, int min, String detail) {
//        this.id = id;
        this.title = title;
        this.year = year;
        this.month = month;
        this.date = date;
        this.hour = hour;
        this.min = min;
        this.detail = detail;
    }

    String getId() {
        return id;
    }

    String getTitle() {
        return title;
    }

    int getYear() {
        return year;
    }

    int getMonth() {
        return month;
    }

    int getDate() {
        return date;
    }

    int getHour() {
        return hour;
    }

    int getMin() {
        return min;
    }

    String getPmam() {
        return pmam;
    }

    ;

    String getDetail() {
        return detail;
    }

    Bitmap getImgUri() {
        return imgUri;
    }

    void setId(String id) {
        this.id = id;
    }

    void setTitle(String title) {
        this.title = title;
    }

    void setYear(int year) {
        this.year = year;
    }

    void setMonth(int month) {
        this.month = month;
    }

    void setDate(int date) {
        this.date = date;
    }

    void setHour(int hour) {
        this.hour = hour;
    }

    void setMin(int min) {
        this.min = min;
    }

    void setPmAm(String pmam) {
        this.pmam = pmam;
    }

    void setDetail(String detail) {
        this.detail = detail;
    }

    void setImgUri(Bitmap imgUri) {
        this.imgUri = imgUri;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
//        result.put("id", id);
        result.put("title", title);
        result.put("year", year);
        result.put("month", month);
        result.put("date", date);
        result.put("hour", hour);
        result.put("min", min);
        result.put("detail", detail);
        return result;
    }
}
