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
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyPage_RecyclerAdapter extends RecyclerView.Adapter<MyPage_RecyclerAdapter.RecyclerViewHolder> {
    private ArrayList<MyPage_WishList_User2> arrayList;
    //private Context context;
//    private FirebaseDatabase database;
    //String selectedKey;

    public MyPage_RecyclerAdapter(ArrayList<MyPage_WishList_User2> arrayList) {
        this.arrayList = arrayList;
        //this.context = context;
        //this.database = database;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wishlist_content, parent, false);
        RecyclerViewHolder holder = new RecyclerViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        String worksname = arrayList.get(position).getWorkname();
        String worksmemo = arrayList.get(position).getMemo();
        String workskey = arrayList.get(position).getKey();


        holder.title.setText(worksname);
        holder.memo.setText((CharSequence) worksmemo);
        holder.key.setText(workskey);

    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView title;
        TextView memo;
        TextView key;
        //Image poster;
//        FirebaseAuth mFirebaseAuth;
//        FirebaseUser mFirebaseUser;

        public RecyclerViewHolder(@NonNull final View itemView) {
            super(itemView);

            this.title = itemView.findViewById(R.id.wishcontent_title);
            this.memo = itemView.findViewById(R.id.wishcontent_memo);
            this.key = itemView.findViewById(R.id.wish_hidden_key);


//            mFirebaseAuth = FirebaseAuth.getInstance();
//            mFirebaseUser = mFirebaseAuth.getCurrentUser();

        }
    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size(): 0);
    }
}

