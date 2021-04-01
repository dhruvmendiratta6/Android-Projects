package com.example.android.quakereport;

import java.security.MessageDigest;

public class EarthQuake {
    private double mMagnitude;
    private String mCity;
    private String mDayDate;
    private long mTimeInMs;
    private String mUrl;

    public EarthQuake(double magnitude, String city,  long timeInMs, String Url){
        mMagnitude = magnitude;
        mCity = city;
        mTimeInMs = timeInMs;
        mUrl = Url;
    }

    public double getmagnitude() {
        return mMagnitude;
    }

    public String getCity(){
        return mCity;
    }

    public long gettimeInMs(){
        return mTimeInMs;
    }

    public String getUrl(){
        return mUrl;
    }

}

