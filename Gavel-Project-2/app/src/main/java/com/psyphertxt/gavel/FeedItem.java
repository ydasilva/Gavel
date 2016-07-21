package com.psyphertxt.gavel;

/**
 * Created by youssoufdasilva on 7/12/16.
 */
public class FeedItem {

    public static final String DATABASE_REFERENCE_NAME = "feed";
    public static final int FEED_AUCTION = 0;
    public static final int FEED_CHAT = 1;

    private String mTitle;
    private String mText;
    private int mType;
    private Boolean mSeen;
    private String mKey;
    private String mAuthorId;

    private String mPhotoUrl;

    public FeedItem(){

    }

    public FeedItem(String title, String text, int type, Boolean seen) {
        mTitle = title;
        mText = text;
        mType = type;
        mSeen = seen;
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


    public void setKey(String key){
        mKey = key;
    }

    public String getKey(){
        return mKey;
    }


    public String getAuthorId() {
        return mAuthorId;
    }

    public void setAuthorId(String authorId) {
        this.mAuthorId = authorId;
    }


    public String getPhotoUrl() {
        return mPhotoUrl;
    }

    public void setPhotoUrl(String mPhotoUrl) {
        this.mPhotoUrl = mPhotoUrl;
    }


}
