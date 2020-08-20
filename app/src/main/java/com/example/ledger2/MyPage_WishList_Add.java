package com.example.ledger2;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

public class MyPage_WishList_Add extends AppCompatActivity {

    // Write a message to the database
    FirebaseDatabase database_wish = FirebaseDatabase.getInstance();

    Button button_save;
    EditText edit_wish_title;
    EditText edit_wish_memo;
    ImageButton image_wish_poster;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_wishlist_add);

        button_save = findViewById(R.id.wish_add_save);
        edit_wish_title = findViewById(R.id.wishlist_edit_title);
        edit_wish_memo = findViewById(R.id.wishlist_edit_memo);
        image_wish_poster = findViewById(R.id.image_add);

//        database_wishlist = FirebaseDatabase.getInstance().getReference();
//        //데이터베이스에서 데이터를 읽거나 쓰려면 DatabaseReference의 인스턴스가 필요합니다.

        findViewById(R.id.wish_add_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("title", edit_wish_title.getText().toString());
                intent.putExtra("memo", edit_wish_memo.getText().toString());
                intent.putExtra("poster", image_wish_poster.getImageTintMode());
                setResult(0, intent);

                Intent intent2 = new Intent(getBaseContext(), MyPage_WishList.class);
                startActivity(intent2);
                finish();
            }
        });

        findViewById(R.id.wish_add_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MyPage_WishList.class);
                startActivity(intent);
            }
        });
    }

} //Class  -   MyPage_WishList_Add

@IgnoreExtraProperties
class MyPage_WishList_User2 {

    public int workId=0;
    public String workname;
    public String memo;
    public Image poster;


    public MyPage_WishList_User2() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public MyPage_WishList_User2(int workID, String workname, String memo, Image poster) {
        this.workId = workID;
        this.workname = workname;
        this.memo = memo;
        this.poster = poster;
    }



    /*---------------get--------------*/
    public int getWorkId(){
        return workId;
    }
    public String getWorkname(){
        return workname;
    }
    public String getMemo(){
        return memo;
    }
    public Image getPoster(){
        return poster;
    }
    /*---------------set--------------*/
    public void setWorkId(int workId) {
        this.workId = workId;
    }
    public void setWorkname(String workname) {
        this.workname = workname;
    }
    public void setMemo(String memo) {
        this.memo = memo;
    }
    public void setPoster(Image poster) {
        this.poster = poster;
    }
} //Class  -   MyPage_WishList_User2



