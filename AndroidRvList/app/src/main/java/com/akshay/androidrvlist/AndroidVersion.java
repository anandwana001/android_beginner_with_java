package com.akshay.androidrvlist;

/**
 * Created by akshaynandwana on
 * 12, September, 2019
 **/
public class AndroidVersion {

    private String versionName;
    private int mImageResourceId;

    public AndroidVersion(String versionName, int mImageResourceId) {
        this.versionName = versionName;
        this.mImageResourceId = mImageResourceId;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public int getmImageResourceId() {
        return mImageResourceId;
    }

    public void setmImageResourceId(int mImageResourceId) {
        this.mImageResourceId = mImageResourceId;
    }
}
