package com.psyphertxt.gavel;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class NewAuctionActivity extends AppCompatActivity {
    private Settings mSettings;

    @InjectView(R.id.auction_title_field) protected EditText mTitle;
    @InjectView(R.id.auction_text_field) protected EditText mText;
    @InjectView(R.id.auction_price_field) protected EditText mPrice;
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
                String auctionStartingPrice = mPrice.getText().toString();

                if (auctionTitle.trim().equals("") || auctionText.trim().equals("") || auctionStartingPrice.trim().equals("")){
                    //The field is empty
                    Toast.makeText(
                            getApplicationContext(),
                            "Please don't leave any text field empty",
                            Toast.LENGTH_LONG).show();
                } else {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();

                    //creating the feedItem
                    DatabaseReference myMessageDataRef = database.getReference(FeedItem.DATABASE_REFERENCE_NAME);
                    DatabaseReference myFeedRef = myMessageDataRef.push();

                    Map<String,Object> value = new HashMap<>();
                    value.put("text",auctionText);
                    value.put("title",auctionTitle);
                    value.put("type",FeedItem.FEED_AUCTION);
                    value.put("seen",false);
                    value.put("key",myFeedRef.getKey());
                    value.put("authorId",mSettings.getUserId());

                    myFeedRef.setValue(value);
                    Toast.makeText(getApplicationContext(), "Added a new Auction" , Toast.LENGTH_LONG).show();


                    //creating an auction
                    DatabaseReference myDataRef = database.getReference();
                    DatabaseReference auctionRef = myDataRef.child(Auction.DATABASE_REFERENCE_NAME).child(myFeedRef.getKey());

                    Toast.makeText(v.getContext(), "Feed Push Key: " + myFeedRef.getKey(), Toast.LENGTH_LONG).show();

                    ArrayList<String> participants = new ArrayList<>();
                    participants.add(participants.size(),mSettings.getUserId());

                    Map<String,Object> newAuction = new HashMap<>();
                    value.put("auctionDescription",auctionText);
                    value.put("auctionTitle",auctionTitle);
                    value.put("auctionAuthorId",mSettings.getUserId());
                    value.put("auctionStartPrice",auctionStartingPrice);
                    value.put("auctionEndDate","01-08-2016");
                    value.put("auctionParticipantsId",participants);

                    auctionRef.setValue(newAuction);
                    Toast.makeText(getApplicationContext(), "Added a new Auction" , Toast.LENGTH_LONG).show();

                    startActivity(new Intent(NewAuctionActivity.this, MainFeedActivity.class));
                    finish();
                }
            }
        });


    }

}
