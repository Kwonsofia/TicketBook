package com.example.ledger2;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;


public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {


    Button google_sign; //구글 로그인 버튼
    private FirebaseAuth mAuth; //파이어 베이스 인증 객체
    private GoogleApiClient googleApiClient; //구글 API 클라이언트 객체
    private static final int GOOGLE_LOGIN_CODE = 100; //구글 로그인 결과 코드

    //다시
    EditText email_edittext;
    EditText password_edittext;
    Button email_login_button;
    Button email_join_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //new
        email_edittext = findViewById(R.id.email_edittext);
        password_edittext = findViewById(R.id.password_edittext);
        email_login_button = findViewById(R.id.email_login_button);
        email_join_button = findViewById(R.id.email_join_button);

        //이메일 회원가입
        email_join_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = email_edittext.getText().toString().trim();
                String password = password_edittext.getText().toString().trim();
                //비었는지 체크
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(LoginActivity.this, "Email을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "Password를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                //new user 만듬
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            finish();
                            Toast.makeText(LoginActivity.this, "가입되었습니다. 로그인해주세요.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        } else {
                            Toast.makeText(LoginActivity.this, "이미 등록된 이메일입니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        //이메일 로그인
        email_login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = email_edittext.getText().toString().trim();
                String password = password_edittext.getText().toString().trim();
                //비었는지 체크
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(LoginActivity.this, "Email을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "Password를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                //new user 만듬
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            finish();
                            Toast.makeText(LoginActivity.this, "로그인되었습니다.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), Calendar.class));
                        } else {
                            Toast.makeText(LoginActivity.this, "없는 정보 입니다. 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        //구글 로그인 연동
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();
        mAuth = FirebaseAuth.getInstance(); //파이어베이스 인증 객체 초기화

        google_sign = findViewById(R.id.google_sign);
        google_sign.setOnClickListener(new View.OnClickListener() { //구글 로그인 버튼 클릭시 이곳 수행
            @Override
            public void onClick(View view) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, GOOGLE_LOGIN_CODE);
            }
        });
    }

    //구글 로그인 인증을 욫요청 했을 때 결과 값을 되돌려 받는 곳
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GOOGLE_LOGIN_CODE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) { //인증 결과가 성공적이면
                GoogleSignInAccount account = result.getSignInAccount(); //account에 정보가 담겨있음
                resultLogin(account); //로그인 결과 값 출력 수행하라는 메소드
            }
        }
    }

    private void resultLogin(final GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) { //로그인이 성공했으면
                            Toast.makeText(LoginActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), Calendar.class));
                        } else { //로그인이 실패했으면
                            Toast.makeText(LoginActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


}