package com.psyphertxt.gavel.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.psyphertxt.gavel.R;

public class BasicInfo extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_info);

//        Button done = (Button) findViewById(R.id.btnDone_BasicInfo);
        Button skip = (Button) findViewById(R.id.btnSkip_BasicInfo);

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent verification = new Intent(BasicInfo.this, VerificationCode.class);
                BasicInfo.this.startActivity(verification);
            }
        });

    }
}
