package com.fussyvegan.scanner;

import android.app.Application;
import androidx.multidex.MultiDex;

import com.fussyvegan.scanner.model.FilterRestaurant;

import java.util.logging.Filter;

import javax.inject.Singleton;

import io.realm.Realm;

public class MyApplication extends Application {

    private static MyApplication mSelf;

    public static FilterRestaurant mFilterRestaurant ;
    public static MyApplication self() {
        return mSelf;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mSelf = this;
        Realm.init(this);
        MultiDex.install(this);

    }
    public static FilterRestaurant getInstance() {
        if (mFilterRestaurant == null) {
            mFilterRestaurant = new FilterRestaurant();
        }
        return mFilterRestaurant;
    }
}
