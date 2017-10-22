package com.nrs.rsrey.bloodbank.utils;

import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.nrs.rsrey.bloodbank.data.AppDatabase;
import com.nrs.rsrey.bloodbank.data.BloodGroupEntity;

import java.util.List;


public class DbUtil {

    private static final String TAG = DbUtil.class.getSimpleName();

    private final AppDatabase mAppDatabase;

    public DbUtil(AppDatabase appDatabase) {
        this.mAppDatabase = appDatabase;
    }

    public LiveData<List<BloodGroupEntity>> getBloodList(){
        return mAppDatabase.getBloodDao().getBloodList();
    }

    public LiveData<BloodGroupEntity> getBloodListById(int id){
        return mAppDatabase.getBloodDao().getBloodListById(id);
    }

    public LiveData<List<BloodGroupEntity>> searchBloodByGroup(String group){
        return mAppDatabase.getBloodDao().getBloodListByBloodGroup(group);
    }

    public void insert(BloodGroupEntity... bloodGroupEntities){
        new InsertTask(mAppDatabase).execute(bloodGroupEntities);
    }

    public void delete(BloodGroupEntity... bloodGroupEntities){
        new DeleteTask(mAppDatabase).execute(bloodGroupEntities);
    }

    public void update(BloodGroupEntity... bloodGroupEntities){
        new UpdateTask(mAppDatabase).execute(bloodGroupEntities);
    }

    static class InsertTask extends AsyncTask<BloodGroupEntity,Void,long[]>{

        private AppDatabase innerAppDatabase;

        InsertTask(AppDatabase appDatabase){
            innerAppDatabase = appDatabase;
        }

        @Override
        protected long[] doInBackground(BloodGroupEntity... bloodGroupEntities) {
            return innerAppDatabase.getBloodDao().insertBlood(bloodGroupEntities);
        }

        @Override
        protected void onPostExecute(long[] longs) {
            super.onPostExecute(longs);
            for (long aLong : longs) {
                Log.d(TAG, String.valueOf(aLong));
            }
        }
    }

    static class UpdateTask extends AsyncTask<BloodGroupEntity,Void,Integer>{

        private AppDatabase mAppDatabase;

        UpdateTask(AppDatabase appDatabase){
            mAppDatabase = appDatabase;
        }

        @Override
        protected Integer doInBackground(BloodGroupEntity... bloodGroupEntities) {
            return mAppDatabase.getBloodDao().updateBlood(bloodGroupEntities);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            Log.d(TAG, String.valueOf(integer));
        }
    }

    static class DeleteTask extends AsyncTask<BloodGroupEntity,Void,Void>{

        private AppDatabase mAppDatabase;

        DeleteTask(AppDatabase appDatabase){
            mAppDatabase = appDatabase;
        }

        @Override
        protected Void doInBackground(BloodGroupEntity... bloodGroupEntities) {
            mAppDatabase.getBloodDao().deleteBlood(bloodGroupEntities);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d(TAG,"Deleted");
        }
    }
}
