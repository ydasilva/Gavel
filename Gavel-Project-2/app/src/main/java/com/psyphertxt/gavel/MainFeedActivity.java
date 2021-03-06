package com.psyphertxt.gavel;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainFeedActivity extends AppCompatActivity {

    public static class MessageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView messageTextView;
        public TextView messengerTextView;
        public CircleImageView messengerImageView;
        public ImageView messageType;
        public int iconNum;
        //        public Boolean seen;
        public String key;
        public View line;

        public MessageViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            messageTextView = (TextView) itemView.findViewById(R.id.messageTextView);
            messengerTextView = (TextView) itemView.findViewById(R.id.messengerTextView);
            messengerImageView = (CircleImageView) itemView.findViewById(R.id.messengerImageView);
            messageType = (ImageView) itemView.findViewById(R.id.messageTypeIcon);
            line = itemView.findViewById(R.id.top_line);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(v.getContext(), "Feed at position " + getAdapterPosition() + ": Clicked." + "Key = " + getKey(), Toast.LENGTH_LONG).show();

            //

            Intent feedIntent = new Intent(v.getContext(), FeedItemActivity.class);
            feedIntent.putExtra("key", getKey());
            v.getContext().startActivity(feedIntent);

            /*if (!seen){
                setSeen(true);
            }

            if (iconNum == FeedItem.FEED_AUCTION) {

                if (seen) {
                    messageType.setImageResource(R.drawable.ic_gavel_red_24dp);
                } else {
                    messageType.setImageResource(R.drawable.ic_gavel_black_24dp);
                }

                Toast.makeText(v.getContext(), "Feed at position " + getAdapterPosition() + ": AUCTION.", Toast.LENGTH_LONG).show();
            } else {
                messageType.setImageResource(R.drawable.ic_chat_black_24dp);
                Toast.makeText(v.getContext(), "Feed at position " + getAdapterPosition() + ": CHAT.", Toast.LENGTH_LONG).show();
            }*/

        }

        public void setIconNum(int icon) {
            this.iconNum = icon;
        }

        public int getIconNum() {
            return this.iconNum;
        }

        /*public void setSeen(Boolean seen) {
            this.seen = seen;
            //todo: set this on firebase also
        }

        public Boolean getSeen() {
            return this.seen;
        }*/

        public void setKey(String key) {
            this.key = key;
        }

        public String getKey() {
            return this.key;
        }

    }

    private static final String TAG = "MainFeedActivity";

    private RecyclerView mMessageRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private ProgressBar mProgressBar;
    private Settings mSettings;
    private View mView;

    // Firebase instance variables
    private DatabaseReference mFirebaseDatabaseReference;
    private FirebaseRecyclerAdapter<FeedItem, MessageViewHolder> mFirebaseAdapter;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_feed);

        Log.d(TAG, "onCreate:started");

        mSettings = new Settings(this);
        mView = findViewById(R.id.feed_overlay);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        int redActionButtonSize = getResources().getDimensionPixelSize(R.dimen.red_action_button_size);
        int redActionButtonMargin = getResources().getDimensionPixelOffset(R.dimen.action_button_margin);
        int redActionButtonContentSize = getResources().getDimensionPixelSize(R.dimen.red_action_button_content_size);
        int redActionButtonContentMargin = getResources().getDimensionPixelSize(R.dimen.red_action_button_content_margin);
        int redActionMenuRadius = getResources().getDimensionPixelSize(R.dimen.red_action_menu_radius);
        int blueSubActionButtonSize = getResources().getDimensionPixelSize(R.dimen.blue_sub_action_button_size);
        int blueSubActionButtonContentMargin = getResources().getDimensionPixelSize(R.dimen.blue_sub_action_button_content_margin);

        final ImageView iconAdd = new ImageView(this);
        iconAdd.setImageDrawable(getResources().getDrawable(R.drawable.ic_add_white_24dp));


        FloatingActionButton.LayoutParams addParams = new FloatingActionButton.LayoutParams(redActionButtonSize, redActionButtonSize);
        addParams.setMargins(redActionButtonMargin,
                redActionButtonMargin,
                redActionButtonMargin,
                redActionButtonMargin);
        iconAdd.setLayoutParams(addParams);

        FloatingActionButton.LayoutParams fabIconAddParams = new FloatingActionButton.LayoutParams(redActionButtonContentSize, redActionButtonContentSize);
        fabIconAddParams.setMargins(redActionButtonContentMargin,
                redActionButtonContentMargin,
                redActionButtonContentMargin,
                redActionButtonContentMargin);

        final FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
                .setContentView(iconAdd, fabIconAddParams)
//                .setBackgroundDrawable(R.drawable.button_action_red_selector) //blue
                .setBackgroundDrawable(R.drawable.button_action_blue_selector)
                .setLayoutParams(addParams)
                .build();

        // Set up customized SubActionButtons for the right center menu
        SubActionButton.Builder subBuilder = new SubActionButton.Builder(this);
        subBuilder.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_action_white_selector));

        FrameLayout.LayoutParams blueContentParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        blueContentParams.setMargins(blueSubActionButtonContentMargin,
                blueSubActionButtonContentMargin,
                blueSubActionButtonContentMargin,
                blueSubActionButtonContentMargin);
        subBuilder.setLayoutParams(blueContentParams);

        // Set custom layout params
        FrameLayout.LayoutParams blueParams = new FrameLayout.LayoutParams(blueSubActionButtonSize, blueSubActionButtonSize);
        subBuilder.setLayoutParams(blueParams);

        ImageView iconGavel = new ImageView(this);
        ImageView iconChat = new ImageView(this);

        iconGavel.setImageDrawable(getResources().getDrawable(R.drawable.ic_gavel_red_24dp));
        iconChat.setImageDrawable(getResources().getDrawable(R.drawable.ic_chat_red_24dp));

        //Building the buttons
        //SubActionButton button1 = itemBuilder.setContentView(itemIcon).build();
        SubActionButton gavelSubButton = subBuilder.setContentView(iconGavel, blueContentParams).build();
        SubActionButton chatSubButton = subBuilder.setContentView(iconChat, blueContentParams).build();

        final FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(gavelSubButton)
                .addSubActionView(chatSubButton)
                .setRadius(redActionMenuRadius)
                .attachTo(actionButton)
                .build();

        // Listen menu open and close events to animate the button content view
        actionMenu.setStateChangeListener(new FloatingActionMenu.MenuStateChangeListener() {
            @Override
            public void onMenuOpened(FloatingActionMenu menu) {
                // Rotate the icon of rightLowerButton 45 degrees clockwise
                iconAdd.setRotation(0);
                PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 45);
                ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(iconAdd, pvhR);
                animation.start();

                mView.setVisibility(View.VISIBLE);

                Toast.makeText(getApplicationContext(), "Welcome " + mSettings.getUserName(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onMenuClosed(FloatingActionMenu menu) {
                // Rotate the icon of rightLowerButton 45 degrees counter-clockwise
                iconAdd.setRotation(45);
                PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 0);
                ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(iconAdd, pvhR);
                animation.start();

                mView.setVisibility(View.INVISIBLE);
            }
        });

        //Handling the subButtons
        gavelSubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*FirebaseDatabase database = FirebaseDatabase.getInstance();

                //java.lang.NullPointerException: Can't pass null for argument 'pathString' in child()
                DatabaseReference myDataRef = database.getReference("messages").child(mSettings.getUserId());
                DatabaseReference myFeedRef = myDataRef.push();

                Map<String,Object> value = new HashMap<>();
                value.put("text","New Auction Text");
                value.put("title","New Auction Title");
                value.put("type",FeedItem.FEED_AUCTION);
//                value.put("icon",true);

                myFeedRef.setValue(value);
                Toast.makeText(getApplicationContext(), "Added a new Auction" , Toast.LENGTH_LONG).show();*/

                startActivity(new Intent(MainFeedActivity.this, NewAuctionActivity.class));

                actionMenu.close(true);
            }
        });

        chatSubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*FirebaseDatabase database = FirebaseDatabase.getInstance();

                //java.lang.NullPointerException: Can't pass null for argument 'pathString' in child()
                DatabaseReference myDataRef = database.getReference(FeedItem.DATABASE_REFERENCE_NAME);
                DatabaseReference myFeedRef = myDataRef.push();

                Map<String,Object> value = new HashMap<>();
                value.put("text","New Chat Text");
                value.put("title","New Chat Title");
                value.put("type",FeedItem.FEED_CHAT);
                value.put("seen",false);
                value.put("key",myFeedRef.getKey());
                value.put("authorId",mSettings.getUserId());

                myFeedRef.setValue(value);*/
                Toast.makeText(getApplicationContext(), "Open a new activity to handle this action", Toast.LENGTH_LONG).show();
                actionMenu.close(true);
            }
        });

        //handling the overlay
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionMenu.close(true);
                v.setVisibility(View.INVISIBLE);
            }
        });

        mView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                actionMenu.close(true);
                v.setVisibility(View.INVISIBLE);
                return false;
            }
        });

        mView.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                actionMenu.close(true);
                v.setVisibility(View.INVISIBLE);
                return false;
            }
        });

        Log.d(TAG, "onCreate:making the recyclerView");
        // Initialize ProgressBar and RecyclerView and FirebaseAnalytics
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mMessageRecyclerView = (RecyclerView) findViewById(R.id.feedRecyclerView);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setStackFromEnd(false); //start from the top
//        mLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL); //todo: tried it and it works
        mMessageRecyclerView.setLayoutManager(mLinearLayoutManager);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);


        // New child entries
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mFirebaseAdapter = new FirebaseRecyclerAdapter<FeedItem,
                MessageViewHolder>(
                FeedItem.class,
                R.layout.feed_item,
                MessageViewHolder.class,
                mFirebaseDatabaseReference.child(FeedItem.DATABASE_REFERENCE_NAME)) {

            @Override
            protected void populateViewHolder(final MessageViewHolder viewHolder,
                                              final FeedItem feedItem, int position) {
                mProgressBar.setVisibility(ProgressBar.INVISIBLE);
                Log.d(TAG, "onCreate:populateViewHolder:progressBar:invisible");

                if (position == 0) {
                    viewHolder.line.setVisibility(View.INVISIBLE);
                }

                viewHolder.messageTextView.setText(trimText(feedItem.getText()));
                viewHolder.messengerTextView.setText(feedItem.getTitle());
                viewHolder.messageType.setColorFilter(android.R.color.black);

//                viewHolder.setSeen(feedItem.getSeen());
                viewHolder.setIconNum(feedItem.getType());
                viewHolder.setKey(feedItem.getKey());

                Log.d(TAG, "onCreate:populateViewHolder: text => " + feedItem.getText() + " & title => " + feedItem.getTitle());

                Log.d(TAG, "onCreate:populateViewHolder: type => " + feedItem.getType());

                Log.d(TAG, "onCreate:populateViewHolder: key => " + feedItem.getKey());

                if (feedItem.getType() == FeedItem.FEED_AUCTION) {
                    //todo: make sure it was NOT seen before drawing with the red icon
                    Log.d(TAG, "onCreate:populateViewHolder:feedType " + feedItem.getType());

                    viewHolder.messageType.setImageResource(R.drawable.ic_gavel_red_24dp);

                    mFirebaseDatabaseReference.child(Favorites.DATABASE_REFERENCE_NAME)
                            .child(mSettings.getUserId()).child(feedItem.getKey())
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        if (dataSnapshot.getValue() instanceof HashMap) {
                                            Favorites favSetting = dataSnapshot.getValue(Favorites.class);
                                            Log.d(TAG, "onCreate:populateViewHolder:onDataChange:pushId " + favSetting.getSeen().get(0));
                                            if (favSetting.getSeen().contains(feedItem.getKey())) {
                                                viewHolder.messageType.setImageResource(R.drawable.ic_gavel_black_24dp);
                                            }

                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });



                    /*if (feedItem.getSeen()){
                        viewHolder.messageType.setImageResource(R.drawable.ic_gavel_black_24dp);
                    } else {
                        viewHolder.messageType.setImageResource(R.drawable.ic_gavel_red_24dp);
                    }

                    as soon as you created an auction this is created for every user
                    favorites
                        userId
                            pushId for a message
                                seen : true
                                joined : true
                            pushId for another message
                                seen : false
                                joined: false

                    if 'data snapshot' does not exist then that means that the user never opened the auction
                            */

                } else if (feedItem.getType() == FeedItem.FEED_CHAT) {
                    //todo: make sure it was NOT seen before drawing with the red icon
                    Log.d(TAG, "onCreate:populateViewHolder:feedType " + feedItem.getType());
                    viewHolder.messageType.setImageResource(R.drawable.ic_chat_red_24dp);
                }

                if (feedItem.getPhotoUrl() == null) {
                    viewHolder.messengerImageView
                            .setImageDrawable(ContextCompat
                                    .getDrawable(MainFeedActivity.this,
                                            R.drawable.ic_people_black_24dp)); //ic_account_circle_black_36dp
                } else {
                    Glide.with(MainFeedActivity.this)
                            .load(feedItem.getPhotoUrl())
                            .into(viewHolder.messengerImageView);
                }
            }
        };

        mFirebaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                Log.d(TAG, "onCreate:onItemRangeInserted");
                super.onItemRangeInserted(positionStart, itemCount);
                int friendlyMessageCount = mFirebaseAdapter.getItemCount();
                int lastVisiblePosition =
                        mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
                // If the recycler view is initially being loaded or the
                // user is at the bottom of the list, scroll to the bottom
                // of the list to show the newly added message.
                if (lastVisiblePosition == -1 ||
                        (positionStart >= (friendlyMessageCount - 1) &&
                                lastVisiblePosition == (positionStart - 1))) {
                    mMessageRecyclerView.scrollToPosition(positionStart);
                }
            }
        });

        mMessageRecyclerView.setLayoutManager(mLinearLayoutManager);
        mMessageRecyclerView.setAdapter(mFirebaseAdapter);
        Log.d(TAG, "set Layout manager and adapter");
        // End of New Child Entries

    }

    @Override
    public boolean onCreateOptionsMenu(Menu feedMenu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_auction, feedMenu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem feedMenuItem) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = feedMenuItem.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(feedMenuItem);
    }


    public static String truncateText(String text, int max) {
        return text.substring(0, Math.min(text.length(), max)) + "...";

    }

    public static String trimText(String text) {
        if (text.length() >= 80) {
            return truncateText(text, 50);
        }
        return text;
    }

}
