package com.example.ledger2;

import android.app.Activity;
import android.app.AlertDialog;
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
    private DatabaseReference mPostReference;
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
    String pmam;  //오전오후
    String details;  //상세내용
    Bitmap imgUri;  //이미지

    String sort="id";

    EditText title;
    EditText detail;

    ArrayAdapter<String> arrayAdapter;

    static ArrayList<String> arrayIndex = new ArrayList<String>();
    static ArrayList<String> arrayData = new ArrayList<String>();


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_list);

        title = (EditText) findViewById(R.id.title);
        final DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);
        final TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker);
        detail = (EditText) findViewById(R.id.detail);

        timePicker.setIs24HourView(false);
        timePicker.setOnTimeChangedListener((TimePicker.OnTimeChangedListener) this);

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        ListView listView = (ListView) findViewById(R.id.schedule_list);
        if(listView != null){
            listView.setAdapter(arrayAdapter);
        }

        //뒤로 가기
        ImageButton backButton = (ImageButton) findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getBaseContext(), Calendar.class);
                startActivity(intent1);
                finish();
            }
        });


        //이미지 가져오기
        ImageButton imageAddButton = (ImageButton) findViewById(R.id.image_add);
        imageAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(getBaseContext(), AddList.class);
                startActivity(intent3);
                finish();

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

                new AlertDialog.Builder(getBaseContext())
                        .setTitle("업로드 할 이미지를 선택하세요")
                        .setPositiveButton("사진촬영", cameraListener)
                        .setNeutralButton("앨범선택", albumListener)
                        .setNegativeButton("취소", cancelListener)
                        .show();
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
                //날짜
                datePicker.init(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(),
                        new DatePicker.OnDateChangedListener() {
                            @Override
                            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                years = year;
                                month = monthOfYear;
                                date = dayOfMonth;
                            }
                        });
                //시간
                onTimeChanged(timePicker, hour, min);

                //상세내용
                details = detail.getText().toString();

//                AdapterView.OnItemClickListener onClickListener=new AdapterView.OnItemClickListener() {
////                    @Override
////                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////                        Log.e("On Click", "position="+position);
////                        Log.e("On Click", "Data: "+arrayData.get(position));
////                        String[] tempData=arrayData.get(position).split("\\s+");
////                        Log.e("On Click", "Split Result= "+tempData);
////                        title.setText(tempData[0].trim());
////                        years.
////                    }
////                }

                postFirebaseDatabase(true);
                getFirebaseDatabase();
                setInsertMode();

                title.requestFocus();
                title.setCursorVisible(true);

                Intent intent2 = new Intent(getBaseContext(), Calendar.class);
                startActivity(intent2);
                finish();
            }
        });
    }

    public void setInsertMode() {
        title.setText("");
    }

    public void doTakeAlbumAction() { //카메라 촬영 후 이미지 가져오기
        Intent intent4 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        String url = "tmp_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
        mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), url));

        intent4.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
        startActivityForResult(intent4, PICK_FROM_CAMERA);
    }

    public void doTakePhotoAction() {  //앨범에서 이미지 가져오기
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


    public void postFirebaseDatabase(boolean add) {
        mPostReference = FirebaseDatabase.getInstance().getReference();
        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> scheduleValues = null;
        if (add) {
            Schedule schedule =
                    new Schedule(stitle, years, month, date, hour, min, details);
            scheduleValues = schedule.toMap();
        }
        childUpdates.put(stitle, scheduleValues);
        mPostReference.updateChildren(childUpdates);
    }

    public void getFirebaseDatabase() {
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e("getFirebaseDatabase", "key: " + dataSnapshot.getChildrenCount());
                arrayData.clear();
                arrayIndex.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String key = postSnapshot.getKey();
                    Schedule get = postSnapshot.getValue(Schedule.class);
                    String[] info = {get.title, String.valueOf(get.year), String.valueOf(get.month),
                            String.valueOf(get.date), String.valueOf(get.hour), String.valueOf(get.min), get.detail};
                    String Result = setTextLength(info[0], 10) + setTextLength(info[1], 10) + setTextLength(info[2], 10) +
                            setTextLength(info[3], 10) + setTextLength(info[4], 10) + setTextLength(info[5], 10) + setTextLength(info[6], 10);
                    arrayData.add(Result);
                    arrayIndex.add(key);
                    Log.d("getFirebaseDatabase", "key: "+key);
                    Log.d("getFirebaseDatabase", "info: "+info[0]+ info[1]+info[2]+info[3]+info[4]+info[5]+info[6]);
                }
                arrayAdapter.clear();
                arrayAdapter.addAll(arrayData);
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

    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        this.hour = hourOfDay;
        this.min = minute;
    }

}

