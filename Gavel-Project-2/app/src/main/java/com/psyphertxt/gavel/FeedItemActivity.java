package com.psyphertxt.gavel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.api.model.StringList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class FeedItemActivity extends AppCompatActivity {
    private static final String TAG = "FeedItemActivity";
    private String key;

    private DatabaseReference mDatabase;
    private Settings mSettings;
    private String joinMsg = "Still Checking!";
    FeedItem item;
    Auction auction;
    Favorites myFavorites;

    @InjectView(R.id.btnJoin_feedItem) protected Button mJoinButton;
    @InjectView(R.id.textView_feedItem) protected TextView mTitleText;
    @InjectView(R.id.textView2_feedItem) protected TextView mDescriptionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_item);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Auction Summary");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mSettings = new Settings(this);

        ButterKnife.inject(this);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        key = getIntent().getStringExtra("key");

//        mDatabase.child(Auction.DATABASE_REFERENCE_NAME).child(key).child("auctionParticipantsId").updateChildren();

            /*    .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    if (dataSnapshot.getValue() instanceof HashMap){
                        auction = dataSnapshot.getValue(Auction.class);
                        if (auction != null){
                            if (auction.getAuctionParticipantsId().contains(mSettings.getUserId())){
                                joinMsg = "You've already joined this auction";
                            } else {
                                joinMsg = "Not joined";
                            }
                        }
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

        mDatabase.child(Favorites.DATABASE_REFERENCE_NAME).child(mSettings.getUserId())
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                myFavorites = dataSnapshot.getValue(Favorites.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatabase.child(FeedItem.DATABASE_REFERENCE_NAME).child(key).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
//                        User user = dataSnapshot.getValue(User.class);
                         item = dataSnapshot.getValue(FeedItem.class);

                        mTitleText.setText(item.getTitle());
                        mDescriptionText.setText(item.getText());

                        //check if seen if false - set it to true if it is false
                        if(myFavorites != null){
                            if(!myFavorites.getSeen().contains(mSettings.getUserId())){
                                int size = myFavorites.getSeen().size();
                                ArrayList<String> newFavs = myFavorites.getSeen();

                                newFavs.add(size,key);

                                myFavorites.setSeen(newFavs);
                            }

                        }

                        Map<String, Object> childUpdates = new HashMap<>();

                        childUpdates.put("/" + Favorites.DATABASE_REFERENCE_NAME + "/" + mSettings.getUserId() + "/" + key,myFavorites);
                        mDatabase.updateChildren(childUpdates);
//                        childUpdates.put("/posts/" + key, postValues);
//                        childUpdates.put("/user-posts/" + userId + "/" + key, postValues);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                    }
                });

        mJoinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "handle joining auction", Toast.LENGTH_LONG).show();
                //add this auction's push id to the list of joined auctions

                //add this user's id to the list of participants
                if(auction != null){
                    auction.getAuctionParticipantsId().add(auction.getAuctionParticipantsId().size(),mSettings.getUserId());
                }

                Map<String, Object> auctionUpdates = new HashMap<>();
                auctionUpdates.put("/" + Auction.DATABASE_REFERENCE_NAME + "/" + key,auction);
                mDatabase.updateChildren(auctionUpdates);

                startActivity(new Intent(FeedItemActivity.this,AuctionActivity.class));
            }
        });

        mJoinButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(v.getContext(), joinMsg, Toast.LENGTH_LONG).show();
                return true;
            }
        });

    }

}
