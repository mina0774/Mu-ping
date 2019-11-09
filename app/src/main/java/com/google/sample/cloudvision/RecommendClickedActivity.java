package com.google.sample.cloudvision;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.StringTokenizer;

public class RecommendClickedActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("User");
    LikeButton likeCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_clicked);

        likeCheck = findViewById(R.id.like_check);

        Intent intent = getIntent();

        TextView title = (TextView) findViewById(R.id.tv_title);
        TextView performer = (TextView) findViewById(R.id.tv_performer);

        title.setText(intent.getStringExtra("title"));
        performer.setText(intent.getStringExtra("performer"));

        String Title = title.getText().toString();
        String Performer = performer.getText().toString();

        likeCheck.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                firebaseAuth = FirebaseAuth.getInstance();
                FirebaseUser user = firebaseAuth.getCurrentUser();
                String userEmail = user.getEmail();

                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        SongItem songItem = new SongItem(Title, Performer);
                        StringTokenizer st = new StringTokenizer(userEmail, "@");
                        myRef.child(st.nextToken()).child("song").child(Title).setValue(songItem);

                        Toast.makeText(RecommendClickedActivity.this, "내가 좋아한곡 리스트에 추가되었습니다.", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                firebaseAuth = FirebaseAuth.getInstance();
                FirebaseUser user = firebaseAuth.getCurrentUser();
                String userEmail = user.getEmail();

                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        StringTokenizer st = new StringTokenizer(userEmail, "@");
                        myRef.child(st.nextToken()).child("song").child(Title).removeValue();

                        Toast.makeText(RecommendClickedActivity.this, "취소되었습니다.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

        /*
        likeCheck.setOnClickListener(view -> {
            firebaseAuth = FirebaseAuth.getInstance();
            FirebaseUser user = firebaseAuth.getCurrentUser();
            String userEmail = user.getEmail();

            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    SongItem songItem = new SongItem(Title, Performer);
                    StringTokenizer st = new StringTokenizer(userEmail, "@");
                    myRef.child(st.nextToken()).child("song").child(Title).setValue(songItem);

                    Toast.makeText(RecommendClickedActivity.this, "내가 좋아한곡 리스트에 추가되었습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        });

         */
    }

}
