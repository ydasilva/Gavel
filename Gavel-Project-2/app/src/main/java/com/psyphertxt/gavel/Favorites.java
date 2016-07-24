package com.psyphertxt.gavel;

import java.util.ArrayList;

/**
 * Created by youssoufdasilva on 7/21/16.
 */
public class Favorites {
    public static final String DATABASE_REFERENCE_NAME = "favorites";

    private Boolean seen;
    private Boolean joined;

    public Boolean getSeen() {
        return seen;
    }

    public void setSeen(Boolean seen) {
        this.seen = seen;
    }

    public Boolean getJoined() {
        return joined;
    }

    public void setJoined(Boolean joined) {
        this.joined = joined;
    }
}
