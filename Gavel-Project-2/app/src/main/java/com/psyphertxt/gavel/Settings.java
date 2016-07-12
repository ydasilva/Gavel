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
        return mSharedPreferences.getString("userId","");
    }
}
