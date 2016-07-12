package com.psyphertxt.gavel;

import android.app.Activity;
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
    private FirebaseAuth.AuthStateListener mAuthListener;

    @InjectView(R.id.textView) protected TextView mNameLabel;
    @InjectView(R.id.name) protected EditText mNameText;
    @InjectView(R.id.btnDone_BasicInfo) protected Button mNameButton;

    String userID ;
    String userNumber ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_name);
        ButterKnife.inject(this);

        Bundle bundle = getIntent().getExtras();
        if(bundle.getString("userID") != null && bundle.getString("userPhoneNumber") != null) {
            userID = bundle.getString("userID");
            userNumber = bundle.getString("userPhoneNumber");
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
                    FirebaseDatabase database = FirebaseDatabase.getInstance();

                    //java.lang.NullPointerException: Can't pass null for argument 'pathString' in child()
                    DatabaseReference myRef = database.getReference("profiles").child(userID);

                    //format displayName:Codebender
                    Map<String,Object> value = new HashMap<>();
                    value.put("displayName",mNameText.getText().toString());

                    value.put("phoneNumber",userNumber);

                    myRef.setValue(value);
//                    myRef.setValue(valueId);

                    Toast.makeText(
                            getApplicationContext(),
                            "Profile name:" + profileName + ", and id:" + userID,
                            Toast.LENGTH_LONG).show();

                    Toast.makeText(
                            getApplicationContext(),
                            "Feed page coming up next!!",
                            Toast.LENGTH_LONG).show();
                }

            }
        });


    }
}
