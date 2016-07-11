package com.psyphertxt.gavel;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.digits.sdk.android.AuthCallback;
import com.digits.sdk.android.Digits;
import com.digits.sdk.android.DigitsAuthButton;
import com.digits.sdk.android.DigitsException;
import com.digits.sdk.android.DigitsSession;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import io.fabric.sdk.android.Fabric;

public class GetStartedActivity extends Activity {

    private static final String TAG = "GetStartedActivity";

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "Gdsq8mE638DbzxH71OnMqhDvH";
    private static final String TWITTER_SECRET = "N3G4GE9DnucI8xchwUgzAHzErohAuy8KSwk1pCp4wMo78L9X6W ";

    //Firebase Member variables
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private String userPhoneNumber;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new TwitterCore(authConfig), new Digits());
        setContentView(R.layout.activity_get_started);

        // Get the shared instance of the FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    userID = user.getUid();

                    // TODO: Link this to the "Feed" page
                    Toast.makeText(
                            getApplicationContext(),
                            "Feed Page launches!!!",
                            Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(GetStartedActivity.this,ProfileNameActivity.class);
                    intent.putExtra("userID", userID);
                    startActivity(intent);

                    finish();
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        DigitsAuthButton digitsButton = (DigitsAuthButton) findViewById(R.id.phone_button);
//        digitsButton.setAuthTheme(R.style.GavelTheme);

        if(digitsButton != null){
            digitsButton.setCallback(new AuthCallback() {
                @Override
                public void success(DigitsSession session, String phoneNumber) {
                    userPhoneNumber = phoneNumber;
                    // TODO: associate the session userID with your user model
                    Toast.makeText(getApplicationContext(), "Authentication successful for "
                            + phoneNumber, Toast.LENGTH_LONG).show();

                    // Think about the possibility of signing in the user before creating an account
                    signIn(buildEmail(phoneNumber),buildPassword(phoneNumber));

                }

                @Override
                public void failure(DigitsException exception) {
                    Log.d("Digits", "Sign in with Digits failure", exception);
                    Toast.makeText(getApplicationContext(), "Authentication FAILED ", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount: " + email);

        Toast.makeText(getApplicationContext(), "Creating a new User Account", Toast.LENGTH_LONG).show();

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(GetStartedActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
//                            startActivity(new Intent(GetStartedActivity.this,ProfileNameActivity.class));
//                            finish();
                            Log.d(TAG, "createUserWithEmail:onComplete:Authentication Succeeded");
                        }

                    }
                });
        // [END create_user_with_email]
    }

    private void signIn(String email, final String password) {
        Log.d(TAG, "signIn:" + email);

        Toast.makeText(getApplicationContext(), "Signing In", Toast.LENGTH_LONG).show();

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            //Task is not successful - sign in failed
                            Log.w(TAG, "signInWithEmail", task.getException());
                            Toast.makeText(GetStartedActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                            //Create the account
                            createAccount(buildEmail(userPhoneNumber),buildPassword(userPhoneNumber));
                        } else {
//                            startActivity(new Intent(GetStartedActivity.this,ProfileNameActivity.class));
//                            finish();
                            Log.d(TAG, "signInWithEmail:onComplete:Authentication Succeeded");
                        }
                    }
                });
        // [END sign_in_with_email]
    }

    private String buildEmail(String phoneNumber){
        return phoneNumber + "@gavel.com";
    }

    private String buildPassword(String phoneNumber){
        return phoneNumber + "_gavel_";
    }

}
