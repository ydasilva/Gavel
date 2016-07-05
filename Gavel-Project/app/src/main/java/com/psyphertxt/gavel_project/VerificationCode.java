package com.psyphertxt.gavel_project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class VerificationCode extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_code);

        Button prev = (Button) findViewById(R.id.btnPrevious_verification);

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUp = new Intent(VerificationCode.this, SignUp.class);
                VerificationCode.this.startActivity(signUp);
            }
        });

        Button next = (Button) findViewById(R.id.btnNext_Verification);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent basicInfo = new Intent(VerificationCode.this, BasicInfo.class);
                VerificationCode.this.startActivity(basicInfo);
            }
        });
    }
}
