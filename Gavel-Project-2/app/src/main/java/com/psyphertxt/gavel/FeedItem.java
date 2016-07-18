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
    private Boolean mIcon;
    private String mPhotoUrl;

    public FeedItem(){

    }

    public FeedItem(String title, String text, int type, String photoUrl) {
        mTitle = title;
        mText = text;
        mType = type;
        mIcon = false;
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

    public Boolean getIcon() {
        return mIcon;
    }

    public void setIcon(Boolean mSeen) {
        this.mIcon = mSeen;
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
