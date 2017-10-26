package com.nrs.rsrey.bloodbank.utils;

import android.arch.lifecycle.LiveData;

import com.nrs.rsrey.bloodbank.data.AppDatabase;
import com.nrs.rsrey.bloodbank.data.BloodGroupEntity;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;


public class DbUtil {

    private final AppDatabase mAppDatabase;

    @Inject
    public DbUtil(AppDatabase appDatabase) {
        this.mAppDatabase = appDatabase;
    }

    public LiveData<List<BloodGroupEntity>> getBloodList() {
        return mAppDatabase.getBloodDao().getBloodList();
    }

    public LiveData<BloodGroupEntity> getBloodListById(int id) {
        return mAppDatabase.getBloodDao().getBloodListById(id);
    }

    public LiveData<List<BloodGroupEntity>> getApprovedBloodList() {
        return mAppDatabase.getBloodDao().getApprovedList();
    }

    public LiveData<List<BloodGroupEntity>> searchBloodByGroup(String group) {
        return mAppDatabase.getBloodDao().getBloodListByBloodGroup(group);
    }

    public void insert(BloodGroupEntity... bloodGroupEntities) {
        Single<long[]> single = Single.fromCallable(() -> mAppDatabase.getBloodDao().insertBlood(bloodGroupEntities)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        single.subscribe(new SingleObserver<long[]>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(long[] longs) {
                for (long aLong : longs) {
                    Timber.d(String.valueOf(aLong));
                }
            }

            @Override
            public void onError(Throwable e) {
                Timber.d(e.getMessage());
            }
        });
    }

    public void delete(BloodGroupEntity... bloodGroupEntities) {
        Completable completable = Completable.fromCallable(() -> {
            mAppDatabase.getBloodDao().deleteBlood(bloodGroupEntities);
            return null;
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        completable.subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
                Timber.d("Delete successful");
            }

            @Override
            public void onError(Throwable e) {
                Timber.d(e.getMessage());
            }
        });
    }

    public void update(BloodGroupEntity... bloodGroupEntities) {
        Single<Integer> single = Single.fromCallable(() -> mAppDatabase.getBloodDao().updateBlood(bloodGroupEntities)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        single.subscribe(new SingleObserver<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(Integer integer) {
                Timber.d(String.valueOf(integer));
            }

            @Override
            public void onError(Throwable e) {
                Timber.d(e.getMessage());
            }
        });
    }
}
