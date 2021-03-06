package com.example.ledger2;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyPage_TabThree_Exhibition extends Fragment {
    private RecyclerView recyclerView_fragone;

    private FragOne_RecyclerAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Schedule> arrayList;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    FirebaseAuth mFirebaseAuth;
    FirebaseUser mFirebaseUser;

    //
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.mypage_fragthree_exhibition, container, false);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        recyclerView_fragone = (RecyclerView) rootView.findViewById(R.id.recyclerView_fragthree);

        arrayList = new ArrayList<>();
        recyclerView_fragone.setHasFixedSize(true);
        adapter = new FragOne_RecyclerAdapter(arrayList);
        recyclerView_fragone.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView_fragone.setAdapter(adapter);
        database = FirebaseDatabase.getInstance();

        databaseReference = database.getReference(mFirebaseUser.getUid() + "/Calendar"); // DB 테이블 연결
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // firebase database의 data를 받아오는 곳
                arrayList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) { // 반복문으로 List 추출
                    Schedule item = snapshot.getValue(Schedule.class);
                    String comp = item.getDetail();

                    if (comp.equals("Exhibition")) {
                        // Toast.makeText(getContext()," "+comp,Toast.LENGTH_LONG).show();
                        arrayList.add(item);
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // DB를 가져오던 중 error 발생 시
                Log.d("MyPage", String.valueOf(databaseError.toException()));
            }
        });

        adapter = new FragOne_RecyclerAdapter(arrayList);
        recyclerView_fragone.setAdapter(adapter);


        Log.e("Frag", "MainFragment");
        return rootView;
    }
}

