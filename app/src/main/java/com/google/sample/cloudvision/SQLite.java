package com.google.sample.cloudvision;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.BufferedReader;
import java.io.FileReader;

public class SQLite extends SQLiteOpenHelper {
    private final String tableName = "valence_arousal";
   // private String fileName="valence_arousal.csv";
    FileReader file;
    BufferedReader buffer;
    String line = "";
    String columns = "word, valence, arousal";
    String str1 = "INSERT INTO " + tableName + " (" + columns + ") values(";
    String str2 = ");";


    public SQLite(Context context) {
        super(context, "valence_arousal.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase SampleDB) {
        SampleDB.execSQL("DROP TABLE IF EXISTS " + tableName);
        SampleDB.execSQL("CREATE TABLE " + tableName
                + " (word VARCHAR(20), valence REAL,arousal REAL );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase SampleDB, int oldVersion, int newVersion) {
        SampleDB.execSQL("DROP TABLE IF EXISTS " + tableName);
        onCreate(SampleDB);
    }


}
