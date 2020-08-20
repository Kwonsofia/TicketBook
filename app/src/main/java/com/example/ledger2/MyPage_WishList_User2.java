package com.example.ledger2;

import android.media.Image;

import com.google.firebase.database.IgnoreExtraProperties;

public class MyPage_WishList_User2 implements Comparable<com.example.ledger2.MyPage_WishList_User2> {

        public String workname;
        public String memo;
        //public Image poster;

        public MyPage_WishList_User2() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        public MyPage_WishList_User2(String workname, String memo) {

            this.workname = workname;
            this.memo = memo;
            /////this.poster = poster;
        }

        /*---------------get--------------*/
        public String getWorkname(){
            return workname;
        }
        public String getMemo(){
            return memo;
        }
        /*---------------set--------------*/
        public void setWorkname(String workname) {
            this.workname = workname;
        }
        public void setMemo(String memo) {
            this.memo = memo;
        }

        @Override
        public int compareTo(com.example.ledger2.MyPage_WishList_User2 myPage_wishList_user2) {
            return 0;
        }





}
