package com.psyphertxt.gavel_project;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.os.Handler;

public class splashScreen extends Activity {

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 1000;

    //TODO: Make sure this splash screen only shows when there's no user currently logged in

    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
