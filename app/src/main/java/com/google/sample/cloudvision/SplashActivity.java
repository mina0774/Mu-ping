package com.google.sample.cloudvision;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        {
            super.onCreate(savedInstanceState);

            try{
                Thread.sleep(600);
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
            startActivity(new Intent(this, SQLiteActivity.class));
            finish();
        }
    }
}
