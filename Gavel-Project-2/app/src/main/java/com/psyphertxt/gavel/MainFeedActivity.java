package com.psyphertxt.gavel;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

public class MainFeedActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_feed);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        final ImageView iconAdd = new ImageView(this);
        iconAdd.setImageDrawable(getResources().getDrawable(R.drawable.ic_add_white_24dp));
//        iconAdd.setImageResource(R.drawable.ic_add_white_24dp);

        FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
                .setContentView(iconAdd)
                .setBackgroundDrawable(R.drawable.button_action_red_selector)
                .build();

        ImageView iconGavel = new ImageView(this);
        iconGavel.setImageDrawable(getResources().getDrawable(R.drawable.ic_gavel_white_24dp));
//        iconGavel.setImageResource(R.drawable.ic_gavel_white_24dp);

        ImageView iconChat = new ImageView(this);
        iconChat.setImageDrawable(getResources().getDrawable(R.drawable.ic_chat_white_24dp));
//        iconChat.setImageResource(R.drawable.ic_chat_white_24dp);

        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
        itemBuilder.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_action_red_selector));

        SubActionButton buttonGavel = itemBuilder.setContentView(iconGavel)
                .build();
        SubActionButton buttonChat = itemBuilder.setContentView(iconChat).build();

        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(buttonGavel)
                .addSubActionView(buttonChat)
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
            }

            @Override
            public void onMenuClosed(FloatingActionMenu menu) {
                // Rotate the icon of rightLowerButton 45 degrees counter-clockwise
                iconAdd.setRotation(45);
                PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 0);
                ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(iconAdd, pvhR);
                animation.start();
            }
        });

    }

}
