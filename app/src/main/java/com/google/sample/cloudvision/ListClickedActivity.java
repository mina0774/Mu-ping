package com.google.sample.cloudvision;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.StringTokenizer;

public class ListClickedActivity extends AppCompatActivity {

    String a;
    String b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_clicked);

        Intent intent = getIntent();

        TextView songInfo = (TextView) findViewById(R.id.tv_songinfo);
        TextView songInfo2 = (TextView) findViewById(R.id.tv_songinfo2);
        String result = intent.getStringExtra("songinfo");
        StringTokenizer st = new StringTokenizer(result, "\n");
        a = st.nextToken();
        b = st.nextToken();

        songInfo.setText(a);
        songInfo2.setText(b);
    }

}
