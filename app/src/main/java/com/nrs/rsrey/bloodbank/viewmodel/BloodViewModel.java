package com.nrs.rsrey.bloodbank.viewmodel;


import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.nrs.rsrey.bloodbank.MyApplication;
import com.nrs.rsrey.bloodbank.data.BloodGroupEntity;
import com.nrs.rsrey.bloodbank.utils.DbUtil;

import java.util.List;

public class BloodViewModel extends AndroidViewModel {

    private LiveData<List<BloodGroupEntity>> mBloodList;
    private DbUtil mDbutil;

    public BloodViewModel(Application application) {
        super(application);
        mDbutil = ((MyApplication) application).getDbUtil();
        mBloodList = mDbutil.getBloodList();
    }

    public LiveData<List<BloodGroupEntity>> getBloodList() {
        return mBloodList;
    }

    public LiveData<BloodGroupEntity> getBloodById(int id) {
        return mDbutil.getBloodListById(id);
    }

    public LiveData<List<BloodGroupEntity>> getApprovedBloodList() {
        return mDbutil.getApprovedBloodList();
    }

    public LiveData<List<BloodGroupEntity>> searchBloodByGroup(String group) {
        return mDbutil.searchBloodByGroup(group);
    }

    public void insertBlood(BloodGroupEntity... bloodGroupEntities) {
        mDbutil.insert(bloodGroupEntities);
    }

    public void updateBlood(BloodGroupEntity... bloodGroupEntities) {
        mDbutil.update(bloodGroupEntities);
    }

    public void deleteBlood(BloodGroupEntity... bloodGroupEntities) {
        mDbutil.delete(bloodGroupEntities);
    }
}
