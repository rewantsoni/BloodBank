package com.nrs.rsrey.bloodbank.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(indices = {@Index(value = {"id", "bloodGroup"})})
public class BloodGroupEntity implements Parcelable {
    public static final Creator<BloodGroupEntity> CREATOR = new Creator<BloodGroupEntity>() {
        @Override
        public BloodGroupEntity createFromParcel(Parcel in) {
            return new BloodGroupEntity(in);
        }

        @Override
        public BloodGroupEntity[] newArray(int size) {
            return new BloodGroupEntity[size];
        }
    };
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String contactNo;
    private String hospitalName;
    private String bloodGroup;
    private int approved;

    public BloodGroupEntity() {
    }


    @Ignore
    protected BloodGroupEntity(Parcel in) {
        id = in.readInt();
        name = in.readString();
        contactNo = in.readString();
        hospitalName = in.readString();
        bloodGroup = in.readString();
        approved = in.readInt();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public int getApproved() {
        return approved;
    }

    public void setApproved(int approved) {
        this.approved = approved;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(contactNo);
        dest.writeString(hospitalName);
        dest.writeString(bloodGroup);
        dest.writeInt(approved);
    }
}
