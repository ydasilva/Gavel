package com.psyphertxt.gavel;

import java.util.ArrayList;

/**
 * Created by youssoufdasilva on 7/21/16.
 */
public class Favorites {
    public static final String DATABASE_REFERENCE_NAME = "favorites";

    private ArrayList<String> seen;
    private ArrayList<String> joined;

    public ArrayList<String> getSeen() {
        return seen;
    }

    public void setSeen(ArrayList<String> seen) {
        this.seen = seen;
    }

    public ArrayList<String> getJoined() {
        return joined;
    }

    public void setJoined(ArrayList<String> joined) {
        this.joined = joined;
    }
}
