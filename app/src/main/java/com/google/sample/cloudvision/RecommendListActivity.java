package com.google.sample.cloudvision;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class RecommendListActivity extends AppCompatActivity {

    private ArrayList<SongItem> items = null;

    public String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_list);
        ListView listView = (ListView) findViewById(R.id.recommend_list_view);

        ///////////////////////////////////////////////////////////////////////////////////////////////////
        //연동 전 임시로
        items = new ArrayList<>();
        SongItem item1 = new SongItem("song name1", "performer1","genre");
        SongItem item2 = new SongItem("song name2", "performer2","genre");

        items.add(item1);
        items.add(item2);
        /////////////////////////////////////////////////////////////////////////////////////////////////////

        RecommendListAdapter adapter = new RecommendListAdapter(this, R.layout.recommend_item, items);
        listView.setAdapter(adapter);

        //아이템 클릭하면
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), MusicListActivity.class);
                intent.putExtra("title", items.get(i).getTitle());
                intent.putExtra("performer", items.get(i).getPerformer());
                intent.putExtra("genre",items.get(i).getGenre());
                startActivity(intent);
            }
        });
    }
}
