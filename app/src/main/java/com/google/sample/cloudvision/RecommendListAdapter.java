package com.google.sample.cloudvision;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class RecommendListAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<SongItem> items;
    private int layout;

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("User");


    public RecommendListAdapter(Context context, int layout, ArrayList<SongItem> items) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.items = items;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public String getItem(int i) {
        return items.get(i).getTitle();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View v, ViewGroup viewGroup) {
        if (v == null) {
            v = inflater.inflate(layout, viewGroup, false);
        }
        SongItem songItem = items.get(i);

        TextView title = (TextView) v.findViewById(R.id.song_title);
        title.setText(songItem.getTitle());

        TextView performer = (TextView) v.findViewById(R.id.song_performer);
        performer.setText(songItem.getPerformer());

        /*
        CheckBox like_check = (CheckBox) v.findViewById(R.id.like_check);
        like_check.setOnCheckedChangeListener(OnChe);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String userEmail = user.getEmail();

        if (like_check.isChecked()) {
            String Title = title.getText().toString();
            String Performer = performer.getText().toString();

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    SongItem songItem = new SongItem(Title, Performer);
                    StringTokenizer st = new StringTokenizer(userEmail, "@");
                    myRef.child(st.nextToken()).child("song").push().setValue(songItem);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }
        
         */

        return v;
    }
}
