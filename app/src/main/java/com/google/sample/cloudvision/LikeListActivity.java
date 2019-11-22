package com.google.sample.cloudvision;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

public class LikeListActivity extends AppCompatActivity {

    //private ArrayList<LikeItem> data = null;
    ListView listView;
    FirebaseDatabase database;
    DatabaseReference ref;
    FirebaseAuth firebaseAuth;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    LikeItem likeItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like_list);
        getWindow().setStatusBarColor(Color.BLACK);

        likeItem = new LikeItem();
        listView = (ListView) findViewById(R.id.like_list_view);
        database = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String userEmail = user.getEmail();
        StringTokenizer st = new StringTokenizer(userEmail, "@");

        ref = database.getReference("User").child(st.nextToken()).child("song");
        list = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, R.layout.like_item, R.id.songinfo, list);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    likeItem = ds.getValue(LikeItem.class);
                    list.add(likeItem.getTitle().toString() + "\n"+likeItem.getPerformer().toString());
                }
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        /*
        LikeListAdapter adapter = new LikeListAdapter(this, R.layout.like_item, data);
        listView.setAdapter(adapter);
        */

        //아이템 클릭하면
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), ListClickedActivity.class);
                intent.putExtra("songinfo", list.get(i).toString());
                startActivity(intent);
            }
        });

    }


}