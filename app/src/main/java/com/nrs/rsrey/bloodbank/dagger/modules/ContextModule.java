package com.nrs.rsrey.bloodbank.dagger.modules;

import android.content.Context;

import com.nrs.rsrey.bloodbank.dagger.qualifiers.ApplicationQualifier;

import org.jetbrains.annotations.NotNull;

import dagger.Module;
import dagger.Provides;

@Module
public class ContextModule {

    private final Context mContext;

    public ContextModule(Context mContext) {
        this.mContext = mContext;
    }

    @NotNull
    @Provides
    @ApplicationQualifier
    Context provideContext() {
        return this.mContext;
    }
}
