package com.nrs.rsrey.bloodbank;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.nrs.rsrey.bloodbank.data.AppDatabase;

public class MyApplication extends Application{

    private static final String DATABASE_NAME = "bloodDb";
    private AppDatabase mAppDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public AppDatabase getAppDatabase(){
        if(mAppDatabase==null){
            mAppDatabase = Room.databaseBuilder(this, AppDatabase.class, DATABASE_NAME).build();
        }
        return mAppDatabase;
    }
}
