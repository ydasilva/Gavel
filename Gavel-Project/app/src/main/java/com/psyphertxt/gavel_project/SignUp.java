package com.psyphertxt.gavel_project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SignUp extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Button next = (Button) findViewById(R.id.btnNext_CreateProfile);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent verification = new Intent(SignUp.this, VerificationCode.class);
                SignUp.this.startActivity(verification);
            }
        });

    }
}
