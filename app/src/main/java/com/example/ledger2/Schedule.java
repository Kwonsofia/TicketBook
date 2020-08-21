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
    public String date;  //년, 월, 일
    //    public int expense; //지출
//    public String userid;
//    public String userName;
//    public String location;
    public int hour;
    public int min;
    public String detail;
    public String imgUri;
//    public Bitmap imgUri;

    Schedule() {
//
    }

    Schedule(String id, String title, String dates, int hour, int min, String detail, String imgUri) {
        this.id = id;
        this.title = title;
        this.date=dates;
        this.hour = hour;
        this.min = min;
        this.detail = detail;
        this.imgUri=imgUri;
    }

    String getId() {
        return id;
    }

    String getTitle() {
        return title;
    }

    String getDate() {
        return date;
    }

    int getHour() {
        return hour;
    }

    int getMin() {
        return min;
    }

    String getDetail() {
        return detail;
    }

//    Bitmap getImgUri() {
//        return imgUri;
//    }

    void setId(String id) {
        this.id = id;
    }

    void setTitle(String title) {
        this.title = title;
    }

    void setDate(String date) {
        this.date = date;
    }

    void setHour(int hour) {
        this.hour = hour;
    }

    void setMin(int min) {
        this.min = min;
    }

    public String getImgUri() {
        return imgUri;
    }

    public void setImgUri(String imgUri) {
        this.imgUri = imgUri;
    }

    //    void setPmAm(String pmam) {
//        this.pmam = pmam;
//    }

    void setDetail(String detail) {
        this.detail = detail;
    }

//    void setImgUri(Bitmap imgUri) {
//        this.imgUri = imgUri;
//    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
//        result.put("id", id);
        result.put("title", title);
        result.put("date", date);
        result.put("hour", hour);
        result.put("min", min);
        result.put("detail", detail);
        return result;
    }
}
