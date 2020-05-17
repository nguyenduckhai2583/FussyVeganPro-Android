package com.fussyvegan.scanner;

import android.app.Application;

import io.realm.Realm;

public class MyApplication extends Application {

    private static MyApplication mSelf;

    public static MyApplication self() {
        return mSelf;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mSelf = this;
        Realm.init(this);
    }
}
