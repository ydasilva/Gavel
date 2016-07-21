package com.psyphertxt.gavel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class FeedItemActivity extends AppCompatActivity {
    private static final String TAG = "FeedItemActivity";
    private String key;

    private DatabaseReference mDatabase;
    private Settings mSettings;
    FeedItem item;
    Auction auction;

    @InjectView(R.id.btnLive_FeedItem) protected Button mLiveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_item);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mSettings = new Settings(this);

        ButterKnife.inject(this);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        key = getIntent().getStringExtra("key");
        final String userId = mSettings.getUserId();

        mDatabase.child(FeedItem.DATABASE_REFERENCE_NAME).child(key).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
//                        User user = dataSnapshot.getValue(User.class);
                         item = dataSnapshot.getValue(FeedItem.class);

                        //check if seen if false - set it to true if it is false

                        toolbar.setTitle(item.getTitle());
                        Map<String, Object> childUpdates = new HashMap<>();
                        childUpdates.put("/" + FeedItem.DATABASE_REFERENCE_NAME + "/" + key + "/seen","true");
                        mDatabase.updateChildren(childUpdates);
//                        childUpdates.put("/posts/" + key, postValues);
//                        childUpdates.put("/user-posts/" + userId + "/" + key, postValues);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                    }
                });

        mDatabase.child(Auction.DATABASE_REFERENCE_NAME).child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                auction = dataSnapshot.getValue(Auction.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mLiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Starting Living Broadcast", Toast.LENGTH_LONG).show();
            }
        });

    }

}
