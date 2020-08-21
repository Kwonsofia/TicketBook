package com.example.ledger2;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.loader.content.CursorLoader;

import java.io.File;

public class MyPage_WishList_Add extends AppCompatActivity {

    // Write a message to the database
    FirebaseDatabase database_wish = FirebaseDatabase.getInstance();

    Button button_save;
    EditText edit_wish_title;
    EditText edit_wish_memo;
    ImageButton image_wish_poster;

    private static int GALLARY_CODE = 10;
    FirebaseStorage storage;
    String imagePath;
    Task<Uri> downloadUri;
    File f;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_wishlist_add);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        }
        storage = FirebaseStorage.getInstance();

        button_save = findViewById(R.id.wish_add_save);
        edit_wish_title = findViewById(R.id.wishlist_edit_title);
        edit_wish_memo = findViewById(R.id.wishlist_edit_memo);
        image_wish_poster = findViewById(R.id.image_add);

//        database_wishlist = FirebaseDatabase.getInstance().getReference();
//        //데이터베이스에서 데이터를 읽거나 쓰려면 DatabaseReference의 인스턴스가 필요합니다.

        image_wish_poster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(Intent.ACTION_PICK);
                intent2.setType(MediaStore.Images.Media.CONTENT_TYPE);

                startActivityForResult(intent2, GALLARY_CODE);
            }
        });

        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                upload(imagePath);
//                String path = downloadUri==null?"":downloadUri.toString();
                Intent intent = new Intent();
                intent.putExtra("title", edit_wish_title.getText().toString());
                intent.putExtra("memo", edit_wish_memo.getText().toString());
                //intent.putExtra("poster", path);
                //intent.putExtra("poster", image_wish_poster.getImageTintMode());
                intent.putExtra("poster", Uri.fromFile(f).toString());
                setResult(1, intent);

//                Intent intent2 = new Intent(getBaseContext(), MyPage_WishList.class);
//                startActivity(intent2);
                finish();

            }
        });

        findViewById(R.id.wish_add_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MyPage_WishList.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLARY_CODE){
            imagePath = getPath(data.getData());

            f = new File(imagePath);
            image_wish_poster.setImageURI(Uri.fromFile(f));
        }
    }

    public String getPath(Uri uri){
        String [] proj = {MediaStore.Images.Media.DATA};
        CursorLoader cursorLoader = new CursorLoader(this, uri, proj,null,null,null);

        Cursor cursor = cursorLoader.loadInBackground();
        int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        return cursor.getString(index);
    }

    private void upload(String uri){
        StorageReference storageRef = storage.getReferenceFromUrl("gs://ledger2-3da43.appspot.com/");

        Uri file = Uri.fromFile(new File(uri));
        StorageReference riversRef = storageRef.child("images/"+file.getLastPathSegment());
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


} //Class  -   MyPage_WishList_Add




