package com.google.sample.cloudvision;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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

        SQLite mSQLiteHelper = new SQLite(this);
        SampleDB = mSQLiteHelper.getWritableDatabase();
        String fileName = "valence_arousal.csv";
        FileReader file;
        BufferedReader buffer;
        String tableName = "valence_arousal";
        String str2 = ");";
        String line = "";
        String columns = "word, valence, arousal";
        String str1 = "INSERT INTO " + tableName + " (" + columns + ") values(";

    if(TextUtils.isEmpty(PreferenceUtil.getInstance(getApplicationContext()).getStringExtra("create")))
    {
        try {
          PreferenceUtil.getInstance(getApplicationContext()).putStringExtra("create", "wooram");
          Log.d("ddd","한번");
            InputStreamReader in = new InputStreamReader(getResources().openRawResource(R.raw.valence_arousal));
            BufferedReader br = new BufferedReader(in);

            line = br.readLine();

            while (line != null) {
                StringBuilder sb = new StringBuilder(str1);
                String[] str = line.split(",");
                sb.append("'" + str[0] + "'" + ",");
                sb.append(Double.parseDouble(str[1]) + ",");
                sb.append(Double.parseDouble(str[2]));
                sb.append(str2);
                SampleDB.execSQL(sb.toString());
                line = br.readLine();

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
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
