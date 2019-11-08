package com.google.sample.cloudvision;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class LikeListActivity extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<LikeItem> data = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like_list);

        ListView listView = (ListView) findViewById(R.id.like_list_view);

        ///////////////////////////////////////////////////////////////////////////////////////////////////
        //연동 전 임시로
        data = new ArrayList<>();
        LikeItem item1 = new LikeItem("song1-singer1", "info~~");
        LikeItem item2 = new LikeItem("song2-singer2", "info~~~~~~~");

        data.add(item1);
        data.add(item2);

        /////////////////////////////////////////////////////////////////////////////////////////////////////

        LikeListAdapter adapter = new LikeListAdapter(this, R.layout.like_item, data);
        listView.setAdapter(adapter);

        //아이템 클릭하면
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), ListClickedActivity.class);
                intent.putExtra("songinfo", data.get(i).getSonginfo());
                intent.putExtra("info", data.get(i).getInfo());
                startActivity(intent);
            }
        });


    }

    @Override
    public void onClick(View view) {

    }
}