package com.example.ledger2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CalRecyclerAdapter extends RecyclerView.Adapter<CalRecyclerAdapter.RecyclerViewHolder> {

    private ArrayList<Schedule> arrayList;
    private Context contexts;
    private FirebaseDatabase database;
    String selectedKey;

    public CalRecyclerAdapter(ArrayList<Schedule> arrayList) {
        this.arrayList = arrayList;
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
        FirebaseAuth mFirebaseAuth;
        FirebaseUser mFirebaseUser;

        public RecyclerViewHolder(@NonNull final View itemView) {
            super(itemView);

            title01 = itemView.findViewById(R.id.schedule_title);
            date01 = itemView.findViewById(R.id.schedule_date);
            time01 = itemView.findViewById(R.id.schedule_time);
            content01 = itemView.findViewById(R.id.schedule_content);
            key01 = itemView.findViewById(R.id.schedule_key);

        }
    }


}
