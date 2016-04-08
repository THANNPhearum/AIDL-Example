package com.gg.server;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by phearum on 4/8/16.
 */
public class MainObject implements Parcelable {

    private String mMessage;

    public MainObject(Parcel source) {
        mMessage = source.readString();
    }

    public MainObject(String message) {
        mMessage = message;
    }

    public String getMessage() {
        return mMessage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mMessage);
    }

    public static final Creator<MainObject> CREATOR = new Creator<MainObject>() {
        @Override
        public MainObject[] newArray(int size) {
            return new MainObject[size];
        }

        @Override
        public MainObject createFromParcel(Parcel source) {
            return new MainObject(source);
        }
    };
}
