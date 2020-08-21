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
    public String time;
    public String detail;
    public String imgUri;
//    public Bitmap imgUri;

    Schedule() {
//
    }

    Schedule(String id, String title, String dates, String time, String detail, String imgUri) {
        this.id = id;
        this.title = title;
        this.date = dates;
        this.time = time;
        this.detail = detail;
        this.imgUri = imgUri;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDetail() {
        return detail;
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
        result.put("time", time);
        result.put("detail", detail);
        result.put("imgUri", imgUri);
        return result;
    }
}
