package com.nrs.rsrey.bloodbank.data;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {BloodGroupEntity.class},version = 1)
public abstract class AppDatabase extends RoomDatabase{
    public abstract BloodDao getBloodDao();
}
