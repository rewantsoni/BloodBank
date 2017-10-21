package com.nrs.rsrey.bloodbank;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.nrs.rsrey.bloodbank.data.AppDatabase;

public class MyApplication extends Application{

    private AppDatabase mAppDatabase;
    private static final String DATABSE_NAME = "bloodDb";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public AppDatabase getAppDatabase(){
        if(mAppDatabase==null){
            mAppDatabase = Room.databaseBuilder(this,AppDatabase.class,DATABSE_NAME).build();
        }
        return mAppDatabase;
    }
}
