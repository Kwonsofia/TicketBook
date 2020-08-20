package com.example.ledger2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MyPage_WishList extends AppCompatActivity {


    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<MyPage_WishList_User2> arrayList;

    FirebaseDatabase database;
    DatabaseReference databaseReference;

    ImageView imageView_wishlist_back;
    ImageView imageView_wishlist_add;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_wishlist);
        /*--------------Hooks---------------*/
        imageView_wishlist_back = findViewById(R.id.wish_back);
        imageView_wishlist_add = findViewById(R.id.wish_add);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>(); //객체를 담을 arrayList


        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("MyPage"); // DB 테이블 연결

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // firebase database의 data를 받아오는 곳
                arrayList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) { // 반복문으로 List 추출
                    MyPage_WishList_User2 item = snapshot.getValue(MyPage_WishList_User2.class); //만들어졌던 Ledger 객체에 데이터를 담음
                    //String str = item.getWorkname().substring(0,7);
                    arrayList.add(item);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // DB를 가져오던 중 error 발생 시
                Log.d("MyPage_WishListActivity", String.valueOf(databaseError.toException()));
            }
        });

        imageView_wishlist_back.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_wish_back = new Intent(getBaseContext(), MyPage.class);
                startActivity(intent_wish_back);
                }
            });


        imageView_wishlist_add.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imageView_wishlist_add = new Intent(getBaseContext(), MyPage_WishList_Add.class);
                startActivity(imageView_wishlist_add);
            }
        });





    } //OnCreate
}
