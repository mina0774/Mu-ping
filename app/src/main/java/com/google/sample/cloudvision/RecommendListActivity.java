package com.google.sample.cloudvision;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.StringTokenizer;

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
        SongItem item1 = new SongItem("song name1", "performer1");
        SongItem item2 = new SongItem("song name2", "performer2");

        items.add(item1);
        items.add(item2);
        /////////////////////////////////////////////////////////////////////////////////////////////////////

        RecommendListAdapter adapter = new RecommendListAdapter(this, R.layout.recommend_item, items);
        listView.setAdapter(adapter);

        //아이템 클릭하면
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), RecommendClickedActivity.class);
                intent.putExtra("title", items.get(i).getTitle());
                intent.putExtra("performer", items.get(i).getPerformer());
                startActivity(intent);
            }
        });
    }
}
