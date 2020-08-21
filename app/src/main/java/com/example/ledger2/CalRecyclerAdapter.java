package com.example.ledger2;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.text.BreakIterator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static android.content.ContentValues.TAG;

public class CalRecyclerAdapter extends RecyclerView.Adapter<CalRecyclerAdapter.RecyclerViewHolder> {

    private ArrayList<Schedule> arrayList;
    private Context context;
    private FirebaseDatabase database;
    String selectedKey;

    public CalRecyclerAdapter(ArrayList<Schedule> arrayList) {
        this.arrayList = arrayList;
//        this.context = context;
//        this.database = database;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_schedule_list, parent, false);
        RecyclerViewHolder holder = new RecyclerViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        String title = arrayList.get(position).getTitle();
        String date = arrayList.get(position).getDate();

        holder.titles.setText(title);
        holder.date.setText((CharSequence) date);
    }

    @Override
    public int getItemCount() {
        return 0;
    }


    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView titles;
        TextView date;
        TextView key;
        //Image poster;
        FirebaseAuth mFirebaseAuth;
        FirebaseUser mFirebaseUser;

        public RecyclerViewHolder(@NonNull final View itemView) {
            super(itemView);

            this.titles = itemView.findViewById(R.id.schedule_title);
            this.date = itemView.findViewById(R.id.schedule_date);
            key=itemView.findViewById(R.id.schedule_key);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    selectedKey = key.getText().toString();

                    // PopupMenu 객체 생성
                    PopupMenu popup = new PopupMenu(context, v);
                    // 설정한 popup XML을 inflate
                    popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

                    //팝업메뉴 클릭 시 이벤트
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.delete:
                                    database
                                            .getReference(mFirebaseUser.getUid() + "/Ledger/" + selectedKey)
                                            .removeValue(new DatabaseReference.CompletionListener() {
                                                @Override
                                                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                                    Toast.makeText(context, "삭제가 완료되었습니다.", Toast.LENGTH_LONG).show();
                                                }
                                            });

                                    break;
                            }
                            return false;
                        }
                    });
                    popup.show();

                }
//            mFirebaseAuth = FirebaseAuth.getInstance();
//            mFirebaseUser = mFirebaseAuth.getCurrentUser();
            });
        }
    }



}
