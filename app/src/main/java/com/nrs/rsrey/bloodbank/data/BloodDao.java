package com.nrs.rsrey.bloodbank.data;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface BloodDao {
    @Query("Select * from bloodgroupentity")
    LiveData<List<BloodGroupEntity>> getBloodList();

    @Query("Select * from bloodgroupentity where id = :id")
    LiveData<BloodGroupEntity> getBloodListById(int id);

    @Query("Select * from bloodgroupentity where bloodgroup like :bloodGroup")
    LiveData<List<BloodGroupEntity>> getBloodListByBloodGroup(String bloodGroup);

    @Insert
    long[] insertBlood(BloodGroupEntity... bloodGroupEntities);

    @Delete
    void deleteBlood(BloodGroupEntity... bloodGroupEntities);

    @Update
    int updateBlood(BloodGroupEntity... bloodGroupEntities);

    @Query("Update bloodgroupentity set approved = 1 where id = :id")
    void aprroveEntry(int id);
}
