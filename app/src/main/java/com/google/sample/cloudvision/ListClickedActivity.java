package com.google.sample.cloudvision;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ListClickedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_clicked);

        Intent intent = getIntent();

        TextView songInfo = (TextView) findViewById(R.id.tv_songinfo);
        TextView Info = (TextView) findViewById(R.id.tv_info);

        songInfo.setText(intent.getStringExtra("songinfo"));
        Info.setText(intent.getStringExtra("info"));

    }
}
