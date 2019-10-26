package com.google.sample.cloudvision;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SQLiteActivity extends Activity {
    SQLiteDatabase SampleDB;
    private String create = "";
    InputStream fin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SQLite mSQLiteHelper = new SQLite(this);
        SampleDB = mSQLiteHelper.getWritableDatabase();
        String tableName1 = "valence_arousal";
        String tableName2 = "music_to_value_final";
        String str2 = ");";
        String line = "";
        String columns1 = "word, valence, arousal";
        String columns2= "title, performer, word, valence, arousal";
        String stra = "INSERT INTO " + tableName1 + " (" + columns1 + ") values(";
        String strb = "INSERT INTO " + tableName2 + " (" + columns2 + ") values(";

        {
            super.onCreate(savedInstanceState);

            if (TextUtils.isEmpty(PreferenceUtil.getInstance(getApplicationContext()).getStringExtra("create"))) {
                PreferenceUtil.getInstance(getApplicationContext()).putStringExtra("create", "wooram");
                try {
                    Log.d("ddd", "한번");
                    InputStreamReader in = new InputStreamReader(getResources().openRawResource(R.raw.valence_arousal));
                    BufferedReader br = new BufferedReader(in);

                    line = br.readLine();

                    while (line != null) {
                        StringBuilder sb = new StringBuilder(stra);
                        String[] str = line.split(",");
                        sb.append("'" + str[0] + "'" + ",");
                        sb.append(Double.parseDouble(str[1]) + ",");
                        sb.append(Double.parseDouble(str[2]));
                        sb.append(str2);
                        SampleDB.execSQL(sb.toString());
                        line = br.readLine();
                    }

                    Log.d("ddd", "두번");
                    InputStreamReader in1 = new InputStreamReader(getResources().openRawResource(R.raw.music_to_value_final));
                    BufferedReader br2 = new BufferedReader(in1);
                    Log.d("ddd", "두번1");

                    line = br2.readLine();

                    while (line != null) {
                        StringBuilder sb = new StringBuilder(strb);
                        String[] str = line.split(",");
                        sb.append("'" + str[0] + "'" + ",");
                        sb.append("'"+str[1]+"'"+ ",");
                        sb.append("'"+str[2]+"'"+ ",");
                        sb.append(Double.parseDouble(str[3]) +",");
                        sb.append(Double.parseDouble(str[4]));
                        sb.append(str2);
                        SampleDB.execSQL(sb.toString());
                        line = br2.readLine();
                        Log.d("ddd", "들어갓");
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            startActivity(new Intent(this,StartActivity.class));
            finish();
        }
    }




}
