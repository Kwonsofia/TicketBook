package com.example.ledger2;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MyPage_WishList extends AppCompatActivity {

    private RecyclerView recyclerView_mypage;
    private MyPage_RecyclerAdapter adapter_mypage;
    private RecyclerView.LayoutManager layoutManager_mypage;
    private ArrayList<MyPage_WishList_User2> arrayList_mypage;

    private FirebaseDatabase database_mypage;
    private DatabaseReference databaseReference_mypage;


    ImageView imageView_wishlist_back;
    ImageView imageView_wishlist_add;

    FirebaseAuth mFirebaseAuth;
    FirebaseUser mFirebaseUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_wishlist);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();


        /*--------------Hooks---------------*/
        imageView_wishlist_back = findViewById(R.id.wish_back);
        imageView_wishlist_add = findViewById(R.id.wish_add);

        recyclerView_mypage = findViewById(R.id.recyclerView_mypage);
        recyclerView_mypage.setHasFixedSize(true);
        layoutManager_mypage = new LinearLayoutManager(this);
        recyclerView_mypage.setLayoutManager(layoutManager_mypage);
        arrayList_mypage = new ArrayList<>(); //객체를 담을 arrayList

        database_mypage = FirebaseDatabase.getInstance();


        databaseReference_mypage = database_mypage.getReference(mFirebaseUser.getUid() + "/MyPage"); // DB 테이블 연결
        databaseReference_mypage.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // firebase database의 data를 받아오는 곳
                arrayList_mypage.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) { // 반복문으로 List 추출
                    MyPage_WishList_User2 works = snapshot.getValue(MyPage_WishList_User2.class); //만들어졌던  객체에 데이터를 담음
                    arrayList_mypage.add(works);
                }

                adapter_mypage.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // DB를 가져오던 중 error 발생 시
                Log.d("MyPage_WishListActivity", String.valueOf(databaseError.toException()));
            }
        });

        adapter_mypage = new MyPage_RecyclerAdapter(arrayList_mypage);
        recyclerView_mypage.setAdapter(adapter_mypage);

        //뒤로가기
        imageView_wishlist_back.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_wish_back = new Intent(getBaseContext(), MyPage.class);
                startActivity(intent_wish_back);
            }
        });


        //추가하기
        imageView_wishlist_add.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imageView_wishlist_add = new Intent(getBaseContext(), MyPage_WishList_Add.class);
                startActivityForResult(imageView_wishlist_add, 1);
            }
        });


    } //OnCreate

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 1) {
            String workname = data.getStringExtra("title");
            String memo = data.getStringExtra("memo");
            String image = data.getStringExtra("poster");
            //Image poster = data.

            String key = workname;//

            MyPage_WishList_User2 works = new MyPage_WishList_User2(workname, memo, key, image);

            database_mypage.getReference(mFirebaseUser.getUid() + "/MyPage/" + key).setValue(works);

        }
    }
}
