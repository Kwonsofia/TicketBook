package com.example.ledger2;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
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
import androidx.loader.content.CursorLoader;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

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

    String id;  //사용자 아이디
    String stitle;
    int years;  //년도
    int month;  //월
    int date;  //일
    int hour;  //시간
    int min;  //분
    String date_set;
    String details;  //상세내용
    String imagePath;
    Task<Uri> downloadUri;
    File f;
    private static int GALLARY_CODE = 10;

    String sort = "id";

    EditText title;
    EditText sdate;
    EditText detail;

    ImageButton image_addButton;

    ArrayAdapter<String> arrayAdapter;
    FirebaseAuth mFirebaseAuth;
    FirebaseUser mFirebaseUser;
    FirebaseStorage storage;

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
        sdate = (EditText) findViewById(R.id.datePicker);
        final TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker);
        detail = (EditText) findViewById(R.id.detail);

        timePicker.setIs24HourView(false);
        timePicker.setOnTimeChangedListener((TimePicker.OnTimeChangedListener) this);

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        ListView listView = (ListView) findViewById(R.id.schedule_list);
        if (listView != null) {
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
        image_addButton = findViewById(R.id.image_add_sche);
        image_addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(Intent.ACTION_PICK);
                intent2.setType(MediaStore.Images.Media.CONTENT_TYPE);

                startActivityForResult(intent2, GALLARY_CODE);

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

                date_set = String.format("%04d-%02d-%02d", years, month + 1, date);
                sdate.setText(date_set);

                //시간
                onTimeChanged(timePicker, hour, min);

                //상세내용
                details = detail.getText().toString();

                scheduleFirebaseDatabase(true);

                title.requestFocus();
                title.setCursorVisible(true);

                Intent intent2 = new Intent(getBaseContext(), Calendar.class);
                startActivity(intent2);
                finish();
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLARY_CODE) {
            imagePath = getPath(data.getData());

            f = new File(imagePath);
            image_addButton.setImageURI(Uri.fromFile(f));
        }
    }

    public String getPath(Uri uri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader cursorLoader = new CursorLoader(this, uri, proj, null, null, null);

        Cursor cursor = cursorLoader.loadInBackground();
        int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        return cursor.getString(index);
    }

    private void upload(String uri) {
        StorageReference storageRef = storage.getReferenceFromUrl("gs://ledger2-3da43.appspot.com/");

        Uri file = Uri.fromFile(new File(uri));
        StorageReference riversRef = storageRef.child("images/" + file.getLastPathSegment());
        UploadTask uploadTask = riversRef.putFile(file);

// Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                downloadUri = taskSnapshot.getStorage().getDownloadUrl();
            }
        });
    }


    public void scheduleFirebaseDatabase(boolean add) {
        scheduleReference = FirebaseDatabase.getInstance().getReference(mFirebaseUser.getUid() + "/Calendar");
        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> scheduleValues = null;
        if (add) {
            Schedule schedule =
                    new Schedule(id, stitle, date_set, hour, min, details, Uri.fromFile(f).toString());
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
                    Log.d("getFirebaseDatabase", "key: " + key);
                    Log.d("getFirebaseDatabase", "info: " + info[0] + info[1] + info[2] + info[3] + info[4]);
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
        Query sortbyAge = FirebaseDatabase.getInstance().getReference().child("id_list").orderByChild(sort);
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

    public void dateCalendar() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int mYear, int month, int dayOfMonth) {
                date_set = String.format("%04d-%02d-%02d", mYear, month + 1, dayOfMonth);
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

