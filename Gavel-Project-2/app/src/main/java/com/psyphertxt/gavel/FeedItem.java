package com.psyphertxt.gavel;

/**
 * Created by youssoufdasilva on 7/12/16.
 */
public class FeedItem {

    public static final int FEED_AUCTION = 0;
    public static final int FEED_CHAT = 1;

    private String mTitle;
    private String mText;
    private int mType;
    private Boolean mSeen;

    private String mPhotoUrl;

    public FeedItem(){

    }

    public FeedItem(String title, String text, int type, Boolean seen, String photoUrl) {
        mTitle = title;
        mText = text;
        mType = type;
        mSeen = seen;
        mPhotoUrl = photoUrl;
    }


    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getText() {
        return mText;
    }

    public void setText(String mText) {
        this.mText = mText;
    }

    public int getType() {
        return mType;
    }

    public void setType(int mType) {
        this.mType = mType;
    }

    public Boolean getSeen() {
        return mSeen;
    }

    public void setSeen(Boolean mSeen) {
        this.mSeen = mSeen;
    }

    public String getPhotoUrl() {
        return mPhotoUrl;
    }

    public void setPhotoUrl(String mPhotoUrl) {
        this.mPhotoUrl = mPhotoUrl;
    }

//    public Boolean isSeen(){
//        return getIcon();
//    }
//
//    public void makeSeen(){
//        setIcon(true);
//    }
}
