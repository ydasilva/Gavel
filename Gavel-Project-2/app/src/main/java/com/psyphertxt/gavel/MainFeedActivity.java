package com.psyphertxt.gavel;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainFeedActivity extends AppCompatActivity {

    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        public TextView messageTextView;
        public TextView messengerTextView;
        public CircleImageView messengerImageView;
        public ImageView messageType;

        public MessageViewHolder(View v) {
            super(v);
            messageTextView = (TextView) itemView.findViewById(R.id.messageTextView);
            messengerTextView = (TextView) itemView.findViewById(R.id.messengerTextView);
            messengerImageView = (CircleImageView) itemView.findViewById(R.id.messengerImageView);
            messageType = (ImageView) itemView.findViewById(R.id.messageTypeIcon);
        }
    }

    private static final String TAG = "MainFeedActivity";

    public static final String MESSAGES_CHILD = "messages";

    private RecyclerView mMessageRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private ProgressBar mProgressBar;
    private Settings mSettings;

    // Firebase instance variables
    private DatabaseReference mFirebaseDatabaseReference;
    private FirebaseRecyclerAdapter<FeedItem, MessageViewHolder> mFirebaseAdapter;
    private FirebaseAnalytics mFirebaseAnalytics;

//TODO: change the colors of the fab buttons

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_feed);

        Log.d(TAG, "onCreate:started");

        mSettings = new Settings(this);

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
                .setBackgroundDrawable(R.drawable.button_action_red_selector)
                .setLayoutParams(addParams)
                .build();

        // Set up customized SubActionButtons for the right center menu
        SubActionButton.Builder subBuilder = new SubActionButton.Builder(this);
        subBuilder.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_action_blue_selector));

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

        iconGavel.setImageDrawable(getResources().getDrawable(R.drawable.ic_gavel_white_24dp));
        iconChat.setImageDrawable(getResources().getDrawable(R.drawable.ic_chat_white_24dp));

        final FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(subBuilder.setContentView(iconGavel, blueContentParams).build())
                .addSubActionView(subBuilder.setContentView(iconChat, blueContentParams).build())
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

                //change ationButtonbackground to red
//                actionButton.setBackground(getDrawable(R.drawable.button_action_red_selector));

                Toast.makeText(getApplicationContext(), "Welcome " + mSettings.getUserName() , Toast.LENGTH_LONG).show();
            }

            @Override
            public void onMenuClosed(FloatingActionMenu menu) {
                // Rotate the icon of rightLowerButton 45 degrees counter-clockwise
                iconAdd.setRotation(45);
                PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 0);
                ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(iconAdd, pvhR);
                animation.start();

                //change ationButtonbackground to blue
                iconAdd.setColorFilter(Color.argb(5, 0, 0, 0));
            }
        });

        Log.d(TAG, "onCreate:making the recyclerView");
        // Initialize ProgressBar and RecyclerView and FirebaseAnalytics
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mMessageRecyclerView = (RecyclerView) findViewById(R.id.feedRecyclerView);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setStackFromEnd(false); //start from the top
        mMessageRecyclerView.setLayoutManager(mLinearLayoutManager);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        // New child entries
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mFirebaseAdapter = new FirebaseRecyclerAdapter<FeedItem,
                MessageViewHolder>(
                FeedItem.class,
                R.layout.feed_item,
                MessageViewHolder.class,
                mFirebaseDatabaseReference.child(MESSAGES_CHILD)) {

            @Override
            protected void populateViewHolder(MessageViewHolder viewHolder,
                                              FeedItem feedItem, int position) {
                mProgressBar.setVisibility(ProgressBar.INVISIBLE);
                Log.d(TAG, "onCreate:populateViewHolder:progressBar:invisible");

                viewHolder.messageTextView.setText(feedItem.getText());
                viewHolder.messengerTextView.setText(feedItem.getTitle());
                viewHolder.messageType.setColorFilter(android.R.color.black);

                Log.d(TAG, "onCreate:populateViewHolder: text => " + feedItem.getText() + " & title => " + feedItem.getTitle());
                if (feedItem.getFeedType() == feedItem.FEED_AUCTION){
                    Log.d(TAG, "onCreate:populateViewHolder:feedType " + feedItem.getFeedType());
                    viewHolder.messageType.setImageResource(R.drawable.ic_gavel_white_24dp);
                    viewHolder.messageType.setColorFilter(R.color.colorPrimaryDark);
                    //imageView.setColorFilter(Color.argb(255, 255, 255, 255)); //set tint here
//                    viewHolder.messageType.setImageDrawable(getResources().getDrawable(R.drawable.ic_gavel_white_24dp));
                } else if (feedItem.getFeedType() == feedItem.FEED_CHAT){
                    Log.d(TAG, "onCreate:populateViewHolder:feedType " + feedItem.getFeedType());
                    viewHolder.messageType.setImageResource(R.drawable.ic_chat_white_24dp);
                    viewHolder.messageType.setColorFilter(R.color.colorPrimaryDark);
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

}
