package com.semicolstudio.android.agsemi;

/*
 * Copyright (c) 2561. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

import android.content.Context;
import android.content.SharedPreferences;

public class UserManager {

    Context mContext;
    String SesName = "AgcApSettings";
    SharedPreferences sharedPreferenc;
    SharedPreferences.Editor editored;

    public UserManager(Context context) {
        this.mContext = context;
        this.sharedPreferenc = mContext.getSharedPreferences(SesName, mContext.MODE_PRIVATE);
        this.editored = sharedPreferenc.edit();
    }

    public void createSession(Integer sMemberID, String sUser, String sPass) {
        editored.putBoolean(SesName+"_LoginStatus", true);
        editored.putInt(SesName+"_UserID", sMemberID);
        editored.putString(SesName+"_UserName", sUser);
        editored.putString(SesName+"_UserPass", sPass);
        editored.commit();
    }

    public void deleteSession() {
        editored.clear();
        editored.commit();
    }

    public boolean getLoginStatus() {
        return sharedPreferenc.getBoolean(SesName+"_LoginStatus", false);
    }
    public Integer getUserID() {
        return sharedPreferenc.getInt(SesName+"_UserID", 0);
    }
    public String getUserName() {
        return sharedPreferenc.getString(SesName+"_UserName", null);
    }
    public String getUserPass() {
        return sharedPreferenc.getString(SesName+"_UserPass", null);
    }
}
