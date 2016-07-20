package com.psyphertxt.gavel;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class NewAuctionActivity extends AppCompatActivity {
    private Settings mSettings;

    @InjectView(R.id.auction_title_field) protected EditText mTitle;
    @InjectView(R.id.auction_text_field) protected EditText mText;
    @InjectView(R.id.btnCreate_NewAuction) protected Button mCreateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_auction);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSettings = new Settings(this);

        ButterKnife.inject(this);

        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String auctionTitle = mTitle.getText().toString();
                String auctionText = mText.getText().toString();

                if (auctionTitle.trim().equals("") || auctionText.trim().equals("")){
                    //The field is empty
                    Toast.makeText(
                            getApplicationContext(),
                            "Please don't leave any text field empty",
                            Toast.LENGTH_LONG).show();
                } else {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();

                    //java.lang.NullPointerException: Can't pass null for argument 'pathString' in child()
                    DatabaseReference myDataRef = database.getReference("messages").child(mSettings.getUserId());
                    DatabaseReference myFeedRef = myDataRef.push();

                    Map<String,Object> value = new HashMap<>();
                    value.put("text",auctionText);
                    value.put("title",auctionTitle);
                    value.put("type",FeedItem.FEED_AUCTION);
                    value.put("seen",false);

                    myFeedRef.setValue(value);
                    Toast.makeText(getApplicationContext(), "Added a new Auction" , Toast.LENGTH_LONG).show();
                }
            }
        });



        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/
    }

}
