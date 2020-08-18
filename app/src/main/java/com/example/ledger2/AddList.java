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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class AddList extends Activity {
    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_IMAGE = 2;

    private Uri mImageCaptureUri;
    private ImageView iv_UserPhoto;
    private String absolutePath;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_list);


        //뒤로 가기
        ImageButton backButton=(ImageButton)findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getBaseContext(), Calendar.class);
                startActivity(intent1);
                finish();
            }
        });


        //이미지 가져오기
        ImageButton imageAddButton=(ImageButton)findViewById(R.id.image_add);
        imageAddButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent3=new Intent(getBaseContext(), AddList.class);
                startActivity(intent3);
                finish();

                DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        doTakePhotoAction();
                    }
                };

                DialogInterface.OnClickListener albumListener=new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        doTakeAlbumAction();
                    }
                };

                DialogInterface.OnClickListener cancelListener=new DialogInterface.OnClickListener() {
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


        //저장하기
        Button saveButton=(Button)findViewById(R.id.save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(getBaseContext(), Calendar.class);
                startActivity(intent2);
                finish();
            }
        });
    }

    public void doTakeAlbumAction() { //카메라 촬영 후 이미지 가져오기
        Intent intent4=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        String url="tmp_"+String.valueOf(System.currentTimeMillis())+".jpg";
        mImageCaptureUri= Uri.fromFile(new File(Environment.getExternalStorageDirectory(), url));

        intent4.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
        startActivityForResult(intent4, PICK_FROM_CAMERA);
    }

    public void doTakePhotoAction() {  //앨범에서 이미지 가져오기
        Intent intent5=new Intent(Intent.ACTION_PICK);
        intent5.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent5, PICK_FROM_ALBUM);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode!=RESULT_OK)
            return;
        switch(requestCode){
            case PICK_FROM_ALBUM:
            {
                mImageCaptureUri = data.getData();
                Log.d("SmartWheel", mImageCaptureUri.getPath().toString());
            }
            case PICK_FROM_CAMERA:
            {
                Intent intent5=new Intent("com.android.camera.action.CROP");
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
            case CROP_FROM_IMAGE:
            {
                if(resultCode!=RESULT_OK){
                    return;
                }

                final Bundle extras=data.getExtras();

                String filePath=Environment.getExternalStorageDirectory().getAbsolutePath()+
                        "/SmartWheel/"+System.currentTimeMillis()+".jpg";

                if(extras!=null){
                    Bitmap photo=extras.getParcelable("data");
                    iv_UserPhoto.setImageBitmap(photo);
//                    storeCropImage(photo, filePath);
                    absolutePath=filePath;
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
}

