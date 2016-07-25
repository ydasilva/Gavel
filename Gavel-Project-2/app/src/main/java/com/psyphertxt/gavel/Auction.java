package com.psyphertxt.gavel;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by youssoufdasilva on 7/21/16.
 */
public class Auction {
    public static final String DATABASE_REFERENCE_NAME = "auctions";

    private String mAuctionTitle;
    private String mAuctionDescription;
    private String mAuctionAuthorId;
    private String mAuctionStartPrice;
    private String mAuctionEndDate;
    private ArrayList<String> mAuctionParticipantsId;

    public String getAuctionTitle() {
        return mAuctionTitle;
    }

    public void setAuctionTitle(String mAuctionTitle) {
        this.mAuctionTitle = mAuctionTitle;
    }

    public String getAuctionDescription() {
        return mAuctionDescription;
    }

    public void setAuctionDescription(String mAuctionDescription) {
        this.mAuctionDescription = mAuctionDescription;
    }

    public String getAuctionAuthorId() {
        return mAuctionAuthorId;
    }

    public void setAuctionAuthorId(String mAuctionAuthorId) {
        this.mAuctionAuthorId = mAuctionAuthorId;
    }

    public String getAuctionStartPrice() {
        return mAuctionStartPrice;
    }

    public void setAuctionStartPrice(String mAuctionStartPrice) {
        this.mAuctionStartPrice = mAuctionStartPrice;
    }

    public String getAuctionEndDate() {
        return mAuctionEndDate;
    }

    public void setAuctionEndDate(String mAuctionEndDate) {
        this.mAuctionEndDate = mAuctionEndDate;
    }

    public ArrayList<String> getAuctionParticipantsId() {
        return mAuctionParticipantsId;
    }

    public void setAuctionParticipantsId(ArrayList<String> mAuctionParticipantsId) {
        this.mAuctionParticipantsId = mAuctionParticipantsId;
    }
}
