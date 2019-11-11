package com.google.sample.cloudvision;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class PlayMusicActivity extends YouTubeBaseActivity{

        private YouTubePlayerView youtubeView;
        YouTubePlayer.OnInitializedListener listener;
        final String serverKey = "AIzaSyA0B72xByDrqzb68bZ0fQylnasgHDdGbMw";

        @Override
        protected void onCreate (Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_play_music);
                youtubeView = (YouTubePlayerView) findViewById(R.id.youtubeView);
                listener = new YouTubePlayer.OnInitializedListener() {
                        @Override
                        public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                                Intent ID = getIntent();
                                Log.d("gt", ID.getStringExtra("id"));
                                youTubePlayer.loadVideo(ID.getStringExtra("id"));
                        }

                        @Override
                        public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

                                Log.d("result",""+youTubeInitializationResult);
                        }
                };
                youtubeView.initialize(serverKey,listener);

        }
}