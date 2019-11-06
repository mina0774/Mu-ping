package com.google.sample.cloudvision;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.InputStream;

//시작 화면 선택
public class StartActivity extends AppCompatActivity {

    private Button gotoLogin;
    private Button gotoMain;
    SQLiteDatabase SampleDB;
    private String create = "";
    InputStream fin;

    private FirebaseAuth firebaseAuth;

    //백버튼 두번 종료
    private BackPressCloseHandler backPressCloseHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        gotoLogin = (Button) findViewById(R.id.gotoLogin);
        gotoMain = (Button) findViewById(R.id.gotoMain);
        Log.d("start","startactivity");

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        backPressCloseHandler = new BackPressCloseHandler(this);

        gotoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //현재 로그인된 사용자 있는지
                if (user != null) {
                    //로그인 되어 있으면 바로 메인으로
                    Toast.makeText(StartActivity.this, "로그인되어 있습니다.", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(StartActivity.this, MainActivity.class));
                    finish();

                } else {
                    //안되어 있으면
                    Intent intent = new Intent(StartActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        });

        gotoMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        backPressCloseHandler.onBackPressed();
    }

}
