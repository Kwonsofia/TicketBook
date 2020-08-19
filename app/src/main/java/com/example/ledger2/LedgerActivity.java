package com.example.ledger2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class LedgerActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<LedgerItem> arrayList;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    TextView textMonth;
    TextView textSum;
    ImageView calendar_page;
    ImageView my_page;

    String getTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ledger);

        textMonth = findViewById(R.id.textMonth);
        textSum = findViewById(R.id.textSum);
        calendar_page = findViewById(R.id.calendar_page);
        my_page = findViewById(R.id.my_page);


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>(); // Ledger 객체를 담을 arrayList

        database = FirebaseDatabase.getInstance();

        // 현재 날짜 구하기
        Date date = new Date();

        long now = System.currentTimeMillis();
        Date mDate = new Date(now);

        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM");
        getTime = simpleDate.format(mDate);

        textMonth.setText(getTime);


        databaseReference = database.getReference("Ledger"); // DB 테이블 연결
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // firebase database의 data를 받아오는 곳
                arrayList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) { // 반복문으로 List 추출
                    LedgerItem item = snapshot.getValue(LedgerItem.class); //만들어졌던 Ledger 객체에 데이터를 담음
                    String str = item.getDate().substring(0,7);
                    if(getTime.equals(str)){
                        arrayList.add(item);
                    }
                }



                // 총액 계산하기
                getSum(getTime);

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // DB를 가져오던 중 error 발생 시
                Log.d("LedgerActivity", String.valueOf(databaseError.toException()));
            }
        });

        adapter = new RecyclerAdapter(arrayList, this,database);
        recyclerView.setAdapter(adapter);


        // 가계부 추가하기 버튼
        findViewById(R.id.add_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LedgerActivity.this, AddActivity.class);
                startActivityForResult(intent, 0);

            }
        });

        //이전 달로 이동
        findViewById(R.id.month_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String month = textMonth.getText().toString().replace("-", "");
                int M = Integer.parseInt(month);

                if ((M - 1)%100 == 0) {
                    M = M - 89;
                } else {
                    M--;
                }

                String rYear = Integer.toString(M).substring(0, 4);
                String rMonth = Integer.toString(M).substring(4);

                final String rDate = rYear + "-" + rMonth;

                textMonth.setText(rDate);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        // firebase database의 data를 받아오는 곳
                        arrayList.clear();

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) { // 반복문으로 List 추출
                            LedgerItem item = snapshot.getValue(LedgerItem.class); //만들어졌던 Ledger 객체에 데이터를 담음
                            String str = item.getDate().substring(0,7);
                            if(rDate.equals(str)){
                                arrayList.add(item);
                            }
                        }



                        // 총액 계산하기
                        getSum(rDate);

                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // DB를 가져오던 중 error 발생 시
                        Log.d("LedgerActivity", String.valueOf(databaseError.toException()));
                    }
                });


//                recyclerAdapter = new RecyclerAdapter(memoList);
//                recyclerView.setAdapter(recyclerAdapter);




            }
        });

        //다음 달로 이동
        findViewById(R.id.month_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String month = textMonth.getText().toString().replace("-", "");
                int M = Integer.parseInt(month);

                if ((M + 1)%100 == 13) {
                    M = M + 89;
                } else {
                    M++;
                }

                String rYear = Integer.toString(M).substring(0, 4);
                String rMonth = Integer.toString(M).substring(4);

                final String rDate2 = rYear + "-" + rMonth;

                textMonth.setText(rDate2);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        // firebase database의 data를 받아오는 곳
                        arrayList.clear();

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) { // 반복문으로 List 추출
                            LedgerItem item = snapshot.getValue(LedgerItem.class); //만들어졌던 Ledger 객체에 데이터를 담음
                            String str = item.getDate().substring(0,7);
                            if(rDate2.equals(str)){
                                arrayList.add(item);
                            }
                        }



                        // 총액 계산하기
                        getSum(rDate2);

                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // DB를 가져오던 중 error 발생 시
                        Log.d("LedgerActivity", String.valueOf(databaseError.toException()));
                    }
                });

//                recyclerAdapter = new RecyclerAdapter(memoList);
//                recyclerView.setAdapter(recyclerAdapter);
            }
        });

        // 캘린더로 이동
        calendar_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_one = new Intent(LedgerActivity.this, Calendar.class);
                LedgerActivity.this.startActivity(intent_one);
            }
        });


        // 마이페이지로 이동
        my_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_two = new Intent(LedgerActivity.this, MyPage.class);
                LedgerActivity.this.startActivity(intent_two);
            }
        });

    }


    // 총액 계산하기
    public void getSum(String month) {
        int sum = 0;
        for (int i = 0; i < arrayList.size(); i++) {
            String mMonth = arrayList.get(i).date.substring(0, 7);
            if (mMonth.equals(month)) {
                String price = arrayList.get(i).price.replace(",", "");
                sum += Integer.parseInt(price);
            }
        }

        DecimalFormat formatter = new DecimalFormat("###,###");
        textSum.setText(formatter.format(sum));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 0) {
            String date = data.getStringExtra("date");
            String type = data.getStringExtra("type");
            String price = data.getStringExtra("price");
            String title = data.getStringExtra("title");

            String key = date;

            LedgerItem memo = new LedgerItem(date, type, title, price, key);



            database.getReference("Ledger/"+key).setValue(memo);

//            database.getReference("Ledger")
//                    .push()
//                    .setValue(memo);

        }
    }



}
