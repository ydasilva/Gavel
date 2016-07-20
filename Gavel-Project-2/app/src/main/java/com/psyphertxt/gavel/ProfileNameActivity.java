package com.psyphertxt.gavel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ProfileNameActivity extends Activity {
    private Settings mSettings;

    @InjectView(R.id.name) protected EditText mNameText;
    @InjectView(R.id.btnDone_BasicInfo) protected Button mNameButton;

    String userID ;
    String userNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_name);

        mSettings = new Settings(this);

        userNumber = mSettings.getUserNumber();

        ButterKnife.inject(this);

        //Store Id if it's not available
        if (!mSettings.getUserId().isEmpty()){
            // Success
            userID = mSettings.getUserId();

        }

        mNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String profileName = mNameText.getText().toString();

                if (profileName.trim().equals("")){
                    //The field is empty
                    Toast.makeText(
                            getApplicationContext(),
                            "Please enter your profile name",
                            Toast.LENGTH_LONG).show();
                } else {
                    mSettings.setUserName(profileName);
                    FirebaseDatabase database = FirebaseDatabase.getInstance();

                    //java.lang.NullPointerException: Can't pass null for argument 'pathString' in child()
                    DatabaseReference myRef = database.getReference("profiles").child(userID);

                    //format displayName:Codebender
                    Map<String,Object> value = new HashMap<>();
                    value.put("displayName",profileName);

                    value.put("phoneNumber",userNumber);

                    myRef.setValue(value);

                    if (mSettings.getUserId() != Config.EMPTY_STRING && mSettings.getUserName() != Config.EMPTY_STRING && mSettings.getUserNumber() != Config.EMPTY_STRING){
                        Toast.makeText(getApplicationContext(),
                                "Well Done! ",
                                Toast.LENGTH_LONG).show();

                        Toast.makeText(getApplicationContext(),
                                "Your id is: " + mSettings.getUserId()
                                        + ", your name is: " + mSettings.getUserName()
                                        + " and your number is " + mSettings.getUserNumber(),
                                Toast.LENGTH_LONG).show();

                        startActivity(new Intent(ProfileNameActivity.this, MainFeedActivity.class));
                        finish();
                    }

                }

            }
        });


    }
}
