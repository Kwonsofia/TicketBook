package com.example.ledger2;

public class CalendarItem {
    String id;  //사용자 아이디
    String stitle;
    int years;  //년도
    int month;  //월
    int date;  //일
    int hour;  //시간
    int min;  //분
    String date_set;
    String details;  //상세내용
    String imagePath;


    public String getStitle() {
        return stitle;
    }

    public void setstitle(String stitle) {
        this.stitle = stitle;
    }

    public String getdetails() {
        return details;
    }

    public void setdetails(String details) {
        this.details = details;
    }


}
