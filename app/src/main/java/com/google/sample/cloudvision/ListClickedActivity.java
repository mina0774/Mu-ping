package com.google.sample.cloudvision;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.StringTokenizer;

public class ListClickedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_clicked);

        Intent intent = getIntent();

        TextView songInfo = (TextView) findViewById(R.id.tv_songinfo);
        TextView songInfo2 = (TextView) findViewById(R.id.tv_songinfo2);

        String result = intent.getStringExtra("songinfo");
        StringTokenizer st = new StringTokenizer(result, "\n");
        String a = st.nextToken();
        String b = st.nextToken();

        songInfo.setText(a);
        songInfo2.setText(b);

    }
}
