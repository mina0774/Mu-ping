package com.google.sample.cloudvision;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLite extends SQLiteOpenHelper {
    private final String tableName1 = "valence_arousal";
    private final String tableName2 = "music_to_value_final";
    private final String tableName3 = "criteria_adj";

    public SQLite(Context context) {
        super(context, "valence_arousal.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase SampleDB) {

        SampleDB.execSQL("DROP TABLE IF EXISTS " + tableName1);
        SampleDB.execSQL("CREATE TABLE " + tableName1
                + " (word VARCHAR(20), valence REAL,arousal REAL );");
        Log.d("앗사","테이블1");
        SampleDB.execSQL("DROP TABLE IF EXISTS " + tableName2);
        SampleDB.execSQL("CREATE TABLE " + tableName2
                + " (title VARCHAR(30),performer VARCHAR(30) ,word VARCHAR(30),valence REAL, arousal REAL, genre VARCHAR(30));");
        Log.d("앗사","테이블2");
        SampleDB.execSQL("DROP TABLE IF EXISTS " + tableName3);
        SampleDB.execSQL("CREATE TABLE " + tableName3
                + " (word VARCHAR(30),valence REAL, arousal REAL);");
        Log.d("앗사","테이블3");
    }

    @Override
    public void onUpgrade(SQLiteDatabase SampleDB, int oldVersion, int newVersion) {
        SampleDB.execSQL("DROP TABLE IF EXISTS " + tableName1);
        SampleDB.execSQL("DROP TABLE IF EXISTS " + tableName2);
        SampleDB.execSQL("DROP TABLE IF EXISTS " + tableName3);
        onCreate(SampleDB);
    }


}
