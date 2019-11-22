package com.google.sample.cloudvision;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
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

import java.util.StringTokenizer;

public class ListClickedActivity extends AppCompatActivity {
    String a;
    String b;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("User");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_clicked);

        Intent intent = getIntent();

        TextView songInfo = (TextView) findViewById(R.id.tv_songinfo);
        TextView songInfo2 = (TextView) findViewById(R.id.tv_songinfo2);
        ImageView songImage = (ImageView) findViewById(R.id.song_imagee);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressbar);
        TextView delete = (TextView) findViewById(R.id.fvSong_delete);

        String result = intent.getStringExtra("songinfo");
        StringTokenizer st = new StringTokenizer(result, "\n");
        a = st.nextToken();
        b = st.nextToken();
        songInfo.setText(a);    //title
        songInfo2.setText(b);   //performer

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String uE = user.getEmail();
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference().child(a+"_image_"+uE);
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(ListClickedActivity.this)
                        .load(uri).override(1024, 980).listener(new RequestListener<Uri, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, Uri model, Target<GlideDrawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, Uri model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                        .into(songImage);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    public void music_play(View view){
        Intent intent = new Intent(ListClickedActivity.this,LikeMusicPlayActivity.class);
        intent.putExtra("music_info",a+"-"+b);
        startActivity(intent);
    }

    //좋아하는 곡에서 삭제
    public void fav_delete(View view) {
        AlertDialog.Builder alert_confirm = new AlertDialog.Builder(ListClickedActivity.this);
        alert_confirm.setMessage("좋아하는 곡 리스트에서 삭제하시겠습니까?").setCancelable(false).setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        firebaseAuth = FirebaseAuth.getInstance();
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        String userEmail = user.getEmail();

                        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                StringTokenizer st = new StringTokenizer(userEmail, "@");
                                myRef.child(st.nextToken()).child("song").child(a).removeValue();
                                Toast.makeText(ListClickedActivity.this, "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ListClickedActivity.this, LikeListActivity.class);
                                startActivity(intent);
                                LikeListActivity _LikeListActivity = (LikeListActivity) LikeListActivity._LikeListActivity;
                                _LikeListActivity.finish();
                                finish();
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                    }
                }
        );
        alert_confirm.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        alert_confirm.show();
    }
}
