package com.psyphertxt.gavel;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by youssoufdasilva on 7/24/16.
 */
public class BidFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public BidFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static BidFragment newInstance() {
        BidFragment fragment = new BidFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, 1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_bid, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));

        Button liveBtn = (Button) rootView.findViewById(R.id.btnLive_fragmentAuction);
        liveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Starting Live Video",Toast.LENGTH_LONG).show();

//                start live video here
                /*
                button should display 'request live video if not author's account
                */
            }

        });
        return rootView;
    }
}
