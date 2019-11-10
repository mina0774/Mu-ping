package com.google.sample.cloudvision;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class PlayMusicActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener{

        private YouTubePlayerView ytpv;
        private YouTubePlayer ytp;
        final String serverKey = "AIzaSyA0B72xByDrqzb68bZ0fQylnasgHDdGbMw";

        @Override
        protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);
        ytpv = (YouTubePlayerView) findViewById(R.id.youtubeView);
        ytpv.initialize(serverKey, this);
    }


        @Override
        public void onInitializationFailure (YouTubePlayer.Provider arg0, YouTubeInitializationResult arg1){
        Toast.makeText(this, "Initialization Fail", Toast.LENGTH_LONG).show();
    }

        @Override
        public void onInitializationSuccess (YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasrestored){
        ytp = player;
        Intent gt = getIntent();
        ytp.loadVideo(gt.getStringExtra("id"));
    }

}