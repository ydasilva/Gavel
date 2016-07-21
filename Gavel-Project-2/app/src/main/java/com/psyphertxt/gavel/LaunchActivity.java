package com.psyphertxt.gavel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LaunchActivity extends AppCompatActivity {

    private Settings mSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        mSettings = new Settings(this);

        if (mSettings.getUserId().isEmpty()){
            //redirect to Get Started
            startActivity(new Intent(LaunchActivity.this, GetStartedActivity.class));
            finish();
        } else {
            //redirect to feed
            startActivity(new Intent(LaunchActivity.this, MainFeedActivity.class));
            finish();
        }
    }
}
