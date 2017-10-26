package com.nrs.rsrey.bloodbank.dagger.modules;


import android.arch.persistence.room.Room;
import android.content.Context;

import com.nrs.rsrey.bloodbank.dagger.qualifiers.ApplicationQualifier;
import com.nrs.rsrey.bloodbank.data.AppDatabase;

import org.jetbrains.annotations.NotNull;

import dagger.Module;
import dagger.Provides;

@Module(includes = {ContextModule.class})
public class DatabaseModule {

    private static final String DATABASE_NAME = "notesDb";

    @NotNull
    @Provides
    String getDatabaseName() {
        return DATABASE_NAME;
    }

    @NotNull
    @Provides
    AppDatabase getNoteDatabase(@NotNull @ApplicationQualifier Context context, @NotNull String databaseName) {
        return Room.databaseBuilder(context, AppDatabase.class, databaseName).build();
    }

}