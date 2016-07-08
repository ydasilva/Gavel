package com.psyphertxt.gavel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.digits.sdk.android.AuthCallback;
import com.digits.sdk.android.Digits;
import com.digits.sdk.android.DigitsAuthButton;
import com.digits.sdk.android.DigitsException;
import com.digits.sdk.android.DigitsSession;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import io.fabric.sdk.android.Fabric;

public class GetStartedActivity extends Activity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "Gdsq8mE638DbzxH71OnMqhDvH";
    private static final String TWITTER_SECRET = "N3G4GE9DnucI8xchwUgzAHzErohAuy8KSwk1pCp4wMo78L9X6W ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new TwitterCore(authConfig), new Digits());
        setContentView(R.layout.activity_get_started);

        DigitsAuthButton digitsButton = (DigitsAuthButton) findViewById(R.id.phone_button);
//        digitsButton.setAuthTheme(R.style.GavelTheme);

        if(digitsButton != null){
            digitsButton.setCallback(new AuthCallback() {
                @Override
                public void success(DigitsSession session, String phoneNumber) {
                    // TODO: associate the session userID with your user model
                    Toast.makeText(getApplicationContext(), "Authentication successful for "
                            + phoneNumber, Toast.LENGTH_LONG).show();
                    GetStartedActivity.this.startActivity(new Intent(GetStartedActivity.this,ProfileNameActivity.class));
                }

                @Override
                public void failure(DigitsException exception) {
                    Log.d("Digits", "Sign in with Digits failure", exception);
                    Toast.makeText(getApplicationContext(), "Authentication FAILED ", Toast.LENGTH_LONG).show();
                }
            });
        }

    }
}
