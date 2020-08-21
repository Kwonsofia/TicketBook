package com.example.ledger2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
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
    private Context contexts;
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
        String time = arrayList.get(position).getTime();
        String content = arrayList.get(position).getDetail();
        String key = arrayList.get(position).getId();

        holder.title01.setText(title);
        holder.date01.setText((CharSequence) date);
        holder.time01.setText(time);
        holder.content01.setText((CharSequence) content);
        holder.key01.setText(key);
//        Glide.with(holder.itemView)
//                .load(arrayList.get(position).getImgUri())
//                .into(holder.imageView01);

//        holder.key01.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final AlertDialog.Builder builder=new AlertDialog.Builder(contexts);
//                builder.setTitle("삭제");
//                builder.setMessage("해당 항목을 삭제하시겠습니까?");
//                builder.setPositiveButton("예",
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                switch (i.getItemId()) {
////                                case R.id.delete:
////                                    database
////                                            .getReference(mFirebaseUser.getUid() + "/Calendar/" + selectedKey)
////                                            .removeValue(new DatabaseReference.CompletionListener() {
////                                                @Override
////                                                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
////                                                    Toast.makeText(contexts, "삭제가 완료되었습니다.", Toast.LENGTH_LONG).show();
////                                                }
////                                            });
////
////                                    break;
////                            }
//                            }
//                        });
//                builder.setNegativeButton("아니오",
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                dialogInterface.cancel();
//                            }
//                        });
//                builder.show();
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }


    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        ImageView imageView01;
        TextView title01;
        TextView date01;
        TextView time01;
        TextView content01;
        TextView key01;
        //Image poster;
        FirebaseAuth mFirebaseAuth;
        FirebaseUser mFirebaseUser;

        public RecyclerViewHolder(@NonNull final View itemView) {
            super(itemView);

            imageView01 = itemView.findViewById(R.id.schedule_image);
            title01 = itemView.findViewById(R.id.schedule_title);
            date01 = itemView.findViewById(R.id.schedule_date);
            time01 = itemView.findViewById(R.id.schedule_time);
            content01 = itemView.findViewById(R.id.schedule_content);
            key01 = itemView.findViewById(R.id.schedule_key);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(final View v) {
//                    selectedKey = key01.getText().toString();
//
//                    // PopupMenu 객체 생성
//                    PopupMenu popups = new PopupMenu(contexts, v);
//                    // 설정한 popup XML을 inflate
//                    popups.getMenuInflater().inflate(R.menu.popup_menu, popups.getMenu());
//
//                    //팝업메뉴 클릭 시 이벤트
//                    popups.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                        @Override
//                        public boolean onMenuItemClick(MenuItem item) {
//                            switch (item.getItemId()) {
//                                case R.id.delete:
//                                    database
//                                            .getReference(mFirebaseUser.getUid() + "/Calendar/" + selectedKey)
//                                            .removeValue(new DatabaseReference.CompletionListener() {
//                                                @Override
//                                                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
//                                                    Toast.makeText(contexts, "삭제가 완료되었습니다.", Toast.LENGTH_LONG).show();
//                                                }
//                                            });
//
//                                    break;
//                            }
//                            return false;
//                        }
//                    });
//                    popups.show();

                }
//            mFirebaseAuth = FirebaseAuth.getInstance();
//            mFirebaseUser = mFirebaseAuth.getCurrentUser();
//            });
//        }
    }


}
