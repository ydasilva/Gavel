package com.psyphertxt.gavel;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by youssoufdasilva on 7/12/16.
 */
public class Settings {
    private SharedPreferences mSharedPreferences;
    private Context mContext;

    public Settings (Context context){
        mContext = context;
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public String getUserId (){
        return mSharedPreferences.getString(Config.USER_ID,Config.EMPTY_STRING);
    }

    public void setUserId(String userId){
        mSharedPreferences.edit().putString(Config.USER_ID,userId).apply();
    }

    public String getUserNumber (){
        return mSharedPreferences.getString(Config.USER_NUMBER,Config.EMPTY_STRING);
    }

    public void setUserNumber(String userNumber){
        mSharedPreferences.edit().putString(Config.USER_NUMBER,userNumber).apply();
    }

    public String getUserName (){
        return mSharedPreferences.getString(Config.USER_NAME,Config.EMPTY_STRING);
    }

    public void setUserName(String userName){
        mSharedPreferences.edit().putString(Config.USER_NAME,userName).apply();
    }

}
