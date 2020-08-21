package com.example.ledger2;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.recyclerview.widget.RecyclerView;

//((MyPage) getActivity())
public class MyPage_TabOne_Musical extends Fragment {

//    private RecyclerView recyclerView_fragone;
//    private List<Image> imageList = new ArrayList<>();
//    private List<String> uidLists = new ArrayList<>();

    //이메일 비밀번호 로그인 모듈 변수
    private FirebaseAuth mAuth;
    //현재 로그인 된 유저 정보를 담을 변수
    private FirebaseUser currentUser;
    //데이터 베이스 저장
    private static FirebaseDatabase database;


    ArrayList<String> worksnames = new ArrayList<String>();
    ArrayList<String> worksdetails = new ArrayList<String>();
    CustomAdapter ca; //커스텀 어뎁터

    public static MyPage_TabOne_Musical newInstance(){
        return new MyPage_TabOne_Musical();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

        Activity root = getActivity(); //이 클래스가 프레그먼트이기 때문에 액티비티 정보를 얻는다.
        Log.d("myTag1", "onCreateView-1");
        Log.d("myTag2", "onCreateView-2");
        View view = inflater.inflate(R.layout.fragone_content,container,false);

        TextView tabone_title = (TextView) view.findViewById(R.id.fragone_title);
        TextView tabone_details = (TextView) view.findViewById(R.id.fragone_details);
        ListView listView = (ListView)view.findViewById(R.id.fragone_listView);

        ca = new CustomAdapter();
        listView.setAdapter(ca);

        currentUser = mAuth.getCurrentUser();


        display();
        return view;
    }

    //커스텀 뷰
    class CustomAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return worksnames.size();
        }
        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            Activity root = getActivity(); //이 클래스가 프레그먼트이기 때문에 액티비티 정보를 얻는다.
            Toast.makeText(root,"getView" , Toast.LENGTH_SHORT).show();

            //커스텀뷰에 있는 객체들 가져오기
            convertView = getLayoutInflater().inflate(R.layout.mypage_fragone_musical,null);
            TextView tName = (TextView)convertView.findViewById(R.id.fragone_title);
            TextView tDetails = (TextView)convertView.findViewById(R.id.fragone_details);


            tName.setText(worksnames.get(position));
            tDetails.setText(worksdetails.get(position));

            return convertView;
        }
    }//mFirebaseUser.getUid()+"/MyPage/"
    public void display(){
        database.getReference(currentUser.getUid()+"/Calendar")
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        //개수 만큼 돈다

                        //Activity root = getActivity(); //이 클래스가 프레그먼트이기 때문에 액티비티 정보를 얻는다.
                        //Toast.makeText(root,"onChildAdded-2" , Toast.LENGTH_SHORT).show();
                        Log.d("myTag3", "onChildAdded-2");


                        CalendarItem thankDTo = dataSnapshot.getValue(CalendarItem.class);
                        //키값이랑 같이 넣어주기
                        thankDTo.setdetails(dataSnapshot.getKey());

                        //Activity root = getActivity(); //이 클래스가 프레그먼트이기 때문에 액티비티 정보를 얻는다.
                        //Toast.makeText(root, thankDTo.getName()+"/"+ thankDTo.getComment(), Toast.LENGTH_SHORT).show();

                        worksnames.add(thankDTo.getStitle());
                        worksdetails.add(thankDTo.getdetails());


                        int a = worksnames.size();
                        String aa = String.valueOf(a);
                        Log.d("myTagrsult", aa);
                        //이거를 해줘야지 adapter getView가 호출이 된다!!!!중요!!!
                        ca.notifyDataSetChanged();
                        //Toast.makeText(root, aa , Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
}



