package com.psyphertxt.gavel.activity;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.os.Handler;
import com.crashlytics.android.Crashlytics;
import com.digits.sdk.android.Digits;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import io.fabric.sdk.android.Fabric;

public class splashScreen extends Activity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "Gdsq8mE638DbzxH71OnMqhDvH";
    private static final String TWITTER_SECRET = "N3G4GE9DnucI8xchwUgzAHzErohAuy8KSwk1pCp4wMo78L9X6W";


    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 1000;

    //TODO: Make sure this splash screen only shows when there's no user currently logged in

    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new TwitterCore(authConfig), new Digits(), new Crashlytics());
        setContentView(R.layout.activity_splash_screen);

        /* New Handler to start the Menu-Activity
         * and close this splash screen after some seconds.
         */
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity */
                Intent signUpIntent = new Intent(splashScreen.this, SignUp.class);
                startActivity(signUpIntent);
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
