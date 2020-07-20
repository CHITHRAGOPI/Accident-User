package com.example.accident.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class GlobalPreference {

    private SharedPreferences mSharedPreferences;
    private Context mContext;
    private SharedPreferences.Editor mEditor;

    public GlobalPreference(Context mContext) {
        this.mContext = mContext;
        mSharedPreferences = mContext.getSharedPreferences(Constants.SHARED_PREF, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }

    public void addIp(String ip) {
        mEditor.putString(Constants.IP, ip);
        mEditor.apply();
    }

    public void saveCredentials(String name,String username,String password, String address,String imei,String phone,String id,String vehicleNo) {
        mEditor.putString(Constants.NAME,name);
        mEditor.putString(Constants.USERNAME,username);
        mEditor.putString(Constants.PASSWORD,password);
        mEditor.putString(Constants.ADDRESS,address);
        mEditor.putString(Constants.IMEI,imei);
        mEditor.putString(Constants.PHONE,phone);
        mEditor.putString(Constants.ID,id);
        mEditor.putString(Constants.VEHICLE_NO,vehicleNo);

        mEditor.apply();
    }




    public void setLoginStatus(Boolean value){
        mEditor.putBoolean(Constants.LOGIN_CHECK,value);
        mEditor.apply();
    }
    public boolean getLoginStatus(){
     return  mSharedPreferences.getBoolean(Constants.LOGIN_CHECK,false);
    }

    public String getName() {
        return mSharedPreferences.getString(Constants.NAME, "");
    }

    public String getAddress() {
        return mSharedPreferences.getString(Constants.ADDRESS, "");
    }

    public String getPhone() {
        return mSharedPreferences.getString(Constants.PHONE, "");
    }





    public String getPassword() {
        return mSharedPreferences.getString(Constants.PASSWORD, "");
    }

    public  String getUsername(){
        return mSharedPreferences.getString(Constants.USERNAME,"");
    }

    public String getID(){
        return mSharedPreferences.getString(Constants.ID,"");
    }


    public String getVehicle(){
        return mSharedPreferences.getString(Constants.VEHICLE_NO,"");
    }

    public String getImei(){
        return mSharedPreferences.getString(Constants.IMEI,"");
    }

    public String RetrieveIp() {
        return mSharedPreferences.getString(Constants.IP, "");
    }

}
