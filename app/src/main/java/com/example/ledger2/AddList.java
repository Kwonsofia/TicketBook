package com.example.ledger2;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddList extends AppCompatActivity implements TimePicker.OnTimeChangedListener {
    private DatabaseReference scheduleReference;
    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_IMAGE = 2;

    private Uri mImageCaptureUri;
    private ImageView iv_UserPhoto;
    private String absolutePath;

    //    String id;  //사용자 아이디
    String stitle;
    int years;  //년도
    int month;  //월
    int date;  //일
    int hour;  //시간
    int min;  //분
    String date_set;
    String details;  //상세내용
    Bitmap imgUri;  //이미지

    String sort="id";

    EditText title;
    EditText sdate;
    EditText detail;

    ArrayAdapter<String> arrayAdapter;
    FirebaseAuth mFirebaseAuth;
    FirebaseUser mFirebaseUser;

    static ArrayList<String> arrayIndex = new ArrayList<String>();
    static ArrayList<String> arraySchedule = new ArrayList<String>();


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_list);

        java.util.Calendar c = java.util.Calendar.getInstance();
        years = c.get(java.util.Calendar.YEAR);
        month = c.get(java.util.Calendar.MONTH);
        date = c.get(java.util.Calendar.DAY_OF_MONTH);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        title = (EditText) findViewById(R.id.title);
        sdate=(EditText)findViewById(R.id.datePicker);
        final TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker);
        detail = (EditText) findViewById(R.id.detail);

        timePicker.setIs24HourView(false);
        timePicker.setOnTimeChangedListener((TimePicker.OnTimeChangedListener) this);

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        ListView listView = (ListView) findViewById(R.id.schedule_list);
        if(listView != null){
            listView.setAdapter(arrayAdapter);
        }

        //취소버튼
        findViewById(R.id.cancel_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddList.this, Calendar.class);
                startActivity(intent);

            }
        });

        //이미지 가져오기
        ImageButton imageAddButton = findViewById(R.id.image_add_sche);
        imageAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent3 = new Intent(getBaseContext(), AddList.class);
                startActivity(intent3);
                finish();*/

                DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        doTakePhotoAction();
                    }
                };

                DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        doTakeAlbumAction();
                    }
                };

                DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                };

                new AlertDialog.Builder(AddList.this)
                        .setTitle("업로드 할 이미지를 선택하세요")
                        .setPositiveButton("사진촬영", cameraListener)
                        .setNeutralButton("앨범선택", albumListener)
                        .setNegativeButton("취소", cancelListener)
                        .show();
            }
        });

        //달력 버튼
        findViewById(R.id.date_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateCalendar();
            }
        });


        //저장하기(제목, 날짜, 시간, 상세내용)
        Button saveButton = (Button) findViewById(R.id.save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //아이디

                //제목
                stitle = title.getText().toString();
                //날짜 데이터

                sdate = findViewById(R.id.datePicker);

                date_set = String.format("%04d-%02d-%02d", years, month+1, date);
                sdate.setText(date_set);

                //시간
                onTimeChanged(timePicker, hour, min);

                //상세내용
                details = detail.getText().toString();

                scheduleFirebaseDatabase(true);
                getFirebaseDatabase();

                title.requestFocus();
                title.setCursorVisible(true);

                Intent intent2 = new Intent(getBaseContext(), Calendar.class);
                startActivity(intent2);
                finish();
            }
        });
    }

    public void doTakePhotoAction() { //카메라 촬영 후 이미지 가져오기
        Intent intent4 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        String url = "tmp_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
        mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), url));

        intent4.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
        startActivityForResult(intent4, PICK_FROM_CAMERA);
    }

    public void doTakeAlbumAction() {  //앨범에서 이미지 가져오기
        Intent intent5 = new Intent(Intent.ACTION_PICK);
        intent5.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent5, PICK_FROM_ALBUM);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK)
            return;
        switch (requestCode) {
            case PICK_FROM_ALBUM: {
                mImageCaptureUri = data.getData();
                Log.d("SmartWheel", mImageCaptureUri.getPath().toString());
            }
            case PICK_FROM_CAMERA: {
                Intent intent5 = new Intent("com.android.camera.action.CROP");
                intent5.setDataAndType(mImageCaptureUri, "image/*");

                intent5.putExtra("outputX", 200);
                intent5.putExtra("outputY", 200);
                intent5.putExtra("aspectX", 1);
                intent5.putExtra("aspectY", 1);
                intent5.putExtra("scale", true);
                intent5.putExtra("return-data", true);
                startActivityForResult(intent5, CROP_FROM_IMAGE);
                break;
            }
            case CROP_FROM_IMAGE: {
                if (resultCode != RESULT_OK) {
                    return;
                }

                final Bundle extras = data.getExtras();

                String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() +
                        "/SmartWheel/" + System.currentTimeMillis() + ".jpg";

                if (extras != null) {
                    Bitmap photo = extras.getParcelable("data");
                    iv_UserPhoto.setImageBitmap(photo);
//                    storeCropImage(photo, filePath);
                    absolutePath = filePath;
                    break;
                }
            }
        }
    }


//    private void storeCropImage(Bitmap photo, String filePath) {
//        String dirPath=Environment.getExternalStorageDirectory().getAbsolutePath()+"/SmartWheel";
//        File directory_SmartWheel=new File(dirPath);
//
//        if(!directory_SmartWheel.exists())
//            directory_SmartWheel.mkdir();
//
//        File copyFile=new File(filePath);
//        BufferedOutputStream out=null;
//
//        try{
//            copyFile.createNewFile();
//            out=new BufferedOutputStream(new FileOutputStream(copyFile));
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
//        }
//    }


    public void scheduleFirebaseDatabase(boolean add) {
        scheduleReference = FirebaseDatabase.getInstance().getReference(mFirebaseUser.getUid()+"/Calendar");
        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> scheduleValues = null;
        if (add) {
            Schedule schedule =
                    new Schedule(stitle, date_set, hour, min, details);
            scheduleValues = schedule.toMap();
        }
        childUpdates.put(stitle, scheduleValues);
        scheduleReference.updateChildren(childUpdates);
    }

    public void getFirebaseDatabase() {
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e("getFirebaseDatabase", "key: " + dataSnapshot.getChildrenCount());
                arraySchedule.clear();
                arrayIndex.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String key = postSnapshot.getKey();
                    Schedule get = postSnapshot.getValue(Schedule.class);
                    String[] info = {get.title, get.date, String.valueOf(get.hour), String.valueOf(get.min), get.detail};
                    String Result = setTextLength(info[0], 10) + setTextLength(info[1], 10) + setTextLength(info[2], 10) +
                            setTextLength(info[3], 10) + setTextLength(info[4], 10);
                    arraySchedule.add(Result);
                    arrayIndex.add(key);
                    Log.d("getFirebaseDatabase", "key: "+key);
                    Log.d("getFirebaseDatabase", "info: "+info[0]+ info[1]+info[2]+info[3]+info[4]);
                }
                arrayAdapter.clear();
                arrayAdapter.addAll(arraySchedule);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("getFirebaseDatabase", "loadPost:onCancelled", databaseError.toException());
            }
        };
        Query sortbyAge=FirebaseDatabase.getInstance().getReference().child("id_list").orderByChild(sort);
        sortbyAge.addListenerForSingleValueEvent(postListener);
    }

    public String setTextLength(String text, int length) {
        if (text.length() < length) {
            int gap = length - text.length();
            for (int i = 0; i < gap; i++) {
                text = text + " ";
            }
        }
        return text;
    }

    public void dateCalendar(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int mYear, int month, int dayOfMonth) {
                date_set = String.format("%04d-%02d-%02d",mYear,month+1,dayOfMonth);
                sdate.setText(date_set);
            }
        }, years, month, date);
        datePickerDialog.show();
    }

    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        this.hour = hourOfDay;
        this.min = minute;
    }



}

