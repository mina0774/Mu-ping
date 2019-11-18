package com.google.sample.cloudvision;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.io.ByteArrayOutputStream;
import java.util.StringTokenizer;

public class RecommendClickedActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("User");
    private FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    StorageReference storageReference = firebaseStorage.getReference();

    LikeButton likeCheck;
    private TextView title;
    private TextView performer;
    private ImageView song_image;
    private String uE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_clicked);

        likeCheck = findViewById(R.id.like_check);
        likeCheck.setCircleStartColorRes(R.color.colorAnimation1);
        likeCheck.setExplodingDotColorsRes(R.color.colorAnimation1, R.color.colorAnimation2);
        likeCheck.setCircleEndColorRes(R.color.colorAnimation1);

        Intent intent = getIntent();

        title = (TextView) findViewById(R.id.tv_title);
        performer = (TextView) findViewById(R.id.tv_performer);
        song_image = (ImageView) findViewById(R.id.song_image);

        title.setText(intent.getStringExtra("title"));
        performer.setText(intent.getStringExtra("performer"));

        byte[] arr = getIntent().getByteArrayExtra("image");
        Bitmap image = BitmapFactory.decodeByteArray(arr, 0, arr.length);
        song_image.setImageBitmap(image);
        song_image.setVisibility(View.GONE);

        String Title = title.getText().toString();
        String Performer = performer.getText().toString();

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user != null) {
            uE = user.getEmail();
        } else {
            likeCheck.setVisibility(View.GONE);
        }
        StorageReference songRef = storageReference.child(Title+"_image_"+uE);

        likeCheck.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                firebaseAuth = FirebaseAuth.getInstance();
                FirebaseUser user = firebaseAuth.getCurrentUser();
                String userEmail = user.getEmail();

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                Bitmap sendBitmap = ((BitmapDrawable)song_image.getDrawable()).getBitmap();
                sendBitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
                byte[] data = stream.toByteArray();
                UploadTask uploadTask = songRef.putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                            @Override
                            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                if (!task.isSuccessful()) {
                                    throw task.getException();
                                }
                                // Continue with the task to get the download URL
                                return songRef.getDownloadUrl();
                            }
                        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                if (task.isSuccessful()) {
                                    Uri downloadUri = task.getResult();
                                } else {
                                }
                            }
                        });

                    }
                });


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

    }
    public void music_play(View view){
        Intent intent = new Intent(RecommendClickedActivity.this, MusicListActivity.class);
        intent.putExtra("music_info",title.getText().toString()+"-"+performer.getText().toString());
        startActivity(intent);
    }
}
