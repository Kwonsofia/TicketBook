package com.example.ledger2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FragOne_RecyclerAdapter extends RecyclerView.Adapter<FragOne_RecyclerAdapter.Holder> {

    private List<Schedule> list = new ArrayList<>();

    public FragOne_RecyclerAdapter(List<Schedule> list) {
        this.list = list;
    }

    // ViewHolder 생성
    // row layout을 화면에 뿌려주고 holder에 연결
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_adapter, parent, false);
        Holder holder = new Holder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(Holder holder, int position) {
        // 각 위치에 문자열 세팅
        int itemposition = position;

        holder.title.setText(list.get(itemposition).title);
        holder.time.setText(list.get(itemposition).time+"시 ");
        holder.date.setText("날짜: "+ list.get(itemposition).date);
        holder.detail.setText(list.get(itemposition).date);
    }

    // 몇개의 데이터를 리스트로 뿌려줘야하는지 반드시 정의해줘야한다
    @Override
    public int getItemCount() {
        return list.size(); // RecyclerView의 size return
    }

    // ViewHolder는 하나의 View를 보존하는 역할을 한다
    public class Holder extends RecyclerView.ViewHolder{
        TextView title;
        TextView date;
        TextView time;
        TextView detail;

        public Holder(View view){
            super(view);

            this.title = itemView.findViewById(R.id.adap_title);
            this.date = itemView.findViewById(R.id.adap_date);
            this.detail = itemView.findViewById(R.id.adap_detail);
            this.time = itemView.findViewById(R.id.adap_time);
        }
    }




    /*
    private ArrayList<Schedule> arrayList;

    public FragOne_RecyclerAdapter(ArrayList<Schedule> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_adapter, parent, false);
        RecyclerViewHolder holder = new FragOne_RecyclerAdapter.RecyclerViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {

        String title = arrayList.get(position).getTitle();
        String detail = arrayList.get(position).getDetail();
        String date = arrayList.get(position).getDate();
        int hour = arrayList.get(position).getHour();
        int min = arrayList.get(position).getMin();

        holder.title.setText(title);
        holder.time.setText(hour+"시 "+min+"분 ");
        holder.date.setText("날짜: "+date);
        holder.detail.setText(detail);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView title;
        TextView date;
        TextView time;
        TextView detail;

        public RecyclerViewHolder(@NonNull final View itemView) {
            super(itemView);

            this.title = itemView.findViewById(R.id.adap_title);
            this.date = itemView.findViewById(R.id.adap_date);
            this.detail = itemView.findViewById(R.id.adap_detail);
            this.time = itemView.findViewById(R.id.adap_time);

        }
    }*/
}
