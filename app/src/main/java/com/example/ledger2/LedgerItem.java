package com.example.ledger2;


public class LedgerItem implements Comparable<LedgerItem> {

    String date;
    String type;
    String title;
    String price;
    String key;

    @Override
    public int compareTo(LedgerItem o) {
        if(this.date.compareTo(o.getDate())>0){
            return -1;
        }else if(this.date.compareTo(o.getDate())<0){
            return 1;
        }
        return 0;
    }

    public LedgerItem(){}

    public LedgerItem(String date, String type, String title, String price, String key) {
        this.date = date;
        this.type = type;
        this.title = title;
        this.price = price;
        this.key = key;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
