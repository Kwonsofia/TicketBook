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


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static android.content.ContentValues.TAG;

public class CalRecyclerAdapter extends RecyclerView.Adapter<CalRecyclerAdapter.RecyclerViewHolder> {

    private ArrayList<Schedule> arrayList;
//    private Context context;
//    private FirebaseDatabase database;
//    String selectedKey;

    public CalRecyclerAdapter(ArrayList<Schedule> arrayList, Context context, FirebaseDatabase database) {
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

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull CalRecyclerAdapter.RecyclerViewHolder holder, int position) {
        String title = arrayList.get(position).getTitle();
        String date = arrayList.get(position).getDate();



        holder.title.setText(title);
        holder.date.setText((CharSequence) date);

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView title;
        TextView date;
        //Image poster;
//        FirebaseAuth mFirebaseAuth;
//        FirebaseUser mFirebaseUser;

        public RecyclerViewHolder(@NonNull final View itemView) {
            super(itemView);

            this.title = itemView.findViewById(R.id.schedule_title);
            this.date = itemView.findViewById(R.id.schedule_date);


//            mFirebaseAuth = FirebaseAuth.getInstance();
//            mFirebaseUser = mFirebaseAuth.getCurrentUser();

        }
    }



}
