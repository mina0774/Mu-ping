package com.google.sample.cloudvision;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.InputStream;

//시작 화면 선택
public class StartActivity extends AppCompatActivity {

    private Button gotoLogin;
    private Button gotoMain;
    SQLiteDatabase SampleDB;
    private String create = "";
    InputStream fin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        gotoLogin = (Button) findViewById(R.id.gotoLogin);
        gotoMain = (Button) findViewById(R.id.gotoMain);
        Log.d("start","startactivity");

        gotoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this, LoginActivity.class);
                startActivity(intent);
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
}
