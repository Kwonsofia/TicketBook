package com.example.ledger2;

import android.graphics.Bitmap;

public class Schedule {
    public String id;  //사용자 아이디
    public String pw;  //사용자 비밀번호
    public String title;
    public int year;  //년도
    public int month;  //월
    public int date;  //일
//    public int expense; //지출
//    public String userid;
//    public String userName;
//    public String location;
    public String detail;
    public Bitmap imgUri;

    Schedule(){
        String title;
        int year;
        int month;
        int date;
        String detail;
        Bitmap imgUri;
    }

    Schedule(String title, int year, int month, int date, String detail, Bitmap imgUri){
        this.title=title;
        this.year=year;
        this.month=month;
        this.date=date;
        this.detail=detail;
        this.imgUri=imgUri;
    }

    String getTitle(){
        return title;
    }

    int getYear(){
        return year;
    }

    int getMonth(){
        return month;
    }

    int getDate(){
        return date;
    }

    String getDetail(){
        return detail;
    }

    Bitmap getImgUri(){
        return imgUri;
    }

    void setTitle(String title){
        this.title=title;
    }

    void setYear(int year){
        this.year=year;
    }

    void setMonth(int month){
        this.month=month;
    }

    void setDate(int date){
        this.date=date;
    }

    void setDetail(String detail){
        this.detail=detail;
    }

    void setImgUri(Bitmap imgUri){
        this.imgUri=imgUri;
    }

}
