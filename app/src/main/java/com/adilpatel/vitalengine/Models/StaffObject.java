package com.adilpatel.vitalengine.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Adil on 7/20/16.
 */
public class StaffObject implements Parcelable {


    String Staffname = "";

    public String getStaffType() {
        return staffType;
    }

    public StaffObject(String name) {
        Staffname = name;

    }



    public void setStaffType(String staffType) {
        this.staffType = staffType;
    }

    public String getStaffname() {
        return Staffname;
    }

    public void setStaffname(String staffname) {
        Staffname = staffname;
    }

    public int getStaffPic() {
        return StaffPic;
    }

    public void setStaffPic(int staffPic) {
        StaffPic = staffPic;
    }

    public String getStaffSpecialty() {
        return StaffSpecialty;
    }

    public void setStaffSpecialty(String staffSpecialty) {
        StaffSpecialty = staffSpecialty;
    }

    public String getStaffLocation() {
        return StaffLocation;
    }

    public void setStaffLocation(String staffLocation) {
        StaffLocation = staffLocation;
    }

    int StaffPic;
    String StaffSpecialty = "";
    String StaffLocation = "";
    String staffType = "";

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    int staffId;



    protected StaffObject(Parcel in) {
        Staffname = in.readString();
        StaffPic = in.readInt();
        StaffSpecialty = in.readString();
        StaffLocation = in.readString();
        staffType = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Staffname);
        dest.writeInt(StaffPic);
        dest.writeString(StaffSpecialty);
        dest.writeString(StaffLocation);
        dest.writeString(staffType);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<StaffObject> CREATOR = new Parcelable.Creator<StaffObject>() {
        @Override
        public StaffObject createFromParcel(Parcel in) {
            return new StaffObject(in);
        }

        @Override
        public StaffObject[] newArray(int size) {
            return new StaffObject[size];
        }
    };
}
