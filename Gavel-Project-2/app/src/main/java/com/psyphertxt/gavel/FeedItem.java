package com.psyphertxt.gavel;

/**
 * Created by youssoufdasilva on 7/12/16.
 */
public class FeedItem {

    public static final String FEED_AUCTION = "auction";
    public static final String FEED_CHAT = "chat";

    private String mTitle;
    private String mText;
    private String mFeedType;
    private Boolean mSeen;
    private String mPhotoUrl;

    public FeedItem(){

    }

    public FeedItem(String title, String text, String type, String photoUrl) {
        mTitle = title;
        mText = text;
        mFeedType = type;
        mSeen = false;
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

    public String getFeedType() {
        return mFeedType;
    }

    public void setFeedType(String mFeedType) {
        this.mFeedType = mFeedType;
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

    public Boolean isSeen(){
        return getSeen();
    }

    public void makeSeen(){
        setSeen(true);
    }
}
