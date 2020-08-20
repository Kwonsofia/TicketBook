package com.example.ledger2;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyPage_RecyclerAdapter extends RecyclerView.Adapter<MyPage_RecyclerAdapter.RecyclerViewHolder> {
    private ArrayList<MyPage_WishList_User2> arrayList;
    private Context context;
    private FirebaseDatabase database;
    String selectedKey;

    public MyPage_RecyclerAdapter(ArrayList<MyPage_WishList_User2> arrayList, Context context, FirebaseDatabase database) {
        this.arrayList = arrayList;
        this.context = context;
        this.database = database;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mypage_wishlist, parent, false);
        RecyclerViewHolder holder = new RecyclerViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        String worksname = arrayList.get(position).getWorkname().substring(8);
        String worksmemo = arrayList.get(position).getMemo().substring(0, 7);

    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView title;
        TextView memo;
        Image poster;
        FirebaseAuth mFirebaseAuth;
        FirebaseUser mFirebaseUser;

        public RecyclerViewHolder(@NonNull final View itemView) {
            super(itemView);

            this.title = itemView.findViewById(R.id.wishcontent_title);
            this.memo = itemView.findViewById(R.id.wishcontent_memo);


            mFirebaseAuth = FirebaseAuth.getInstance();
            mFirebaseUser = mFirebaseAuth.getCurrentUser();

        }
    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size(): 0);
    }
}


