package com.example.ledger2;

import android.content.Context;
import android.view.LayoutInflater;
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
import java.util.Date;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {

    private ArrayList<LedgerItem> arrayList;
    private Context context;
    private FirebaseDatabase database;
    String selectedKey;

    public RecyclerAdapter(ArrayList<LedgerItem> arrayList, Context context, FirebaseDatabase database) {
        this.arrayList = arrayList;
        this.context = context;
        this.database = database;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public RecyclerAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // create a new view
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_ledger_list, viewGroup, false);
        RecyclerViewHolder holder = new RecyclerViewHolder(view);

        return holder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder itemViewHolder, int i) {

        String date_day = arrayList.get(i).getDate().substring(8);
        String date = arrayList.get(i).getDate().substring(0, 7);
        String date_dayy = "";
        try {
            date_dayy = getDateDay(arrayList.get(i).getDate(), "yyyy-MM-dd");
        } catch (Exception e) {
            e.printStackTrace();
        }

        itemViewHolder.date.setText(date_day);
        itemViewHolder.date2.setText(date);
        itemViewHolder.date3.setText(date_dayy);

        itemViewHolder.type.setText(arrayList.get(i).getType());
        itemViewHolder.title.setText(arrayList.get(i).getTitle());
        itemViewHolder.price.setText(arrayList.get(i).getPrice());
        itemViewHolder.key.setText(arrayList.get(i).getKey());

    }


    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView date;
        TextView date2;
        TextView date3;
        TextView type;
        TextView title;
        TextView price;
        TextView key;
        FirebaseAuth mFirebaseAuth;
        FirebaseUser mFirebaseUser;

        public RecyclerViewHolder(@NonNull final View itemView) {
            super(itemView);

            this.date = itemView.findViewById(R.id.textView_date_day);
            this.date2 = itemView.findViewById(R.id.textView_date);
            this.date3 = itemView.findViewById(R.id.textView_date_dayy);
            this.type = itemView.findViewById(R.id.textView_type);
            this.title = itemView.findViewById(R.id.textView_title);
            this.price = itemView.findViewById(R.id.textView_price);
            key = itemView.findViewById(R.id.hidden_key);
            mFirebaseAuth = FirebaseAuth.getInstance();
            mFirebaseUser = mFirebaseAuth.getCurrentUser();

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
            });


        }
    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }


    //특정 날짜에 대하여 요일을 구함(일 ~ 토)
    public String getDateDay(String date, String dateType) throws Exception {
        String day = "";

        SimpleDateFormat dateFormat = new SimpleDateFormat(dateType);
        Date nDate = dateFormat.parse(date);

        Calendar cal = Calendar.getInstance();
        cal.setTime(nDate);

        int dayNum = cal.get(Calendar.DAY_OF_WEEK);

        switch (dayNum) {
            case 1:
                day = "일요일";
                break;
            case 2:
                day = "월요일";
                break;
            case 3:
                day = "화요일";
                break;
            case 4:
                day = "수요일";
                break;
            case 5:
                day = "목요일";
                break;
            case 6:
                day = "금요일";
                break;
            case 7:
                day = "토요일";
                break;
        }
        return day;
    }


}
