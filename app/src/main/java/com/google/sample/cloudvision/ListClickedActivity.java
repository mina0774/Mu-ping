package com.google.sample.cloudvision;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.StringTokenizer;



public class ListClickedActivity extends AppCompatActivity {
    String a;
    String b;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_clicked);

        Intent intent = getIntent();

        TextView songInfo = (TextView) findViewById(R.id.tv_songinfo);
        TextView songInfo2 = (TextView) findViewById(R.id.tv_songinfo2);
        ImageView songImage = (ImageView) findViewById(R.id.song_imagee);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressbar);

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
}
