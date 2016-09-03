package com.adilpatel.vitalengine.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Adil on 7/21/16.
 */
public class TeamObject implements Parcelable {

    public TeamObject(){

    }

    public TeamObject (Parcel in){
        this.StaffPic = in.readInt();
        this.staffName= in.readString();
        this.staffSpecialty = in.readString();
        this.staffLocation = in.readString();
    }


    int staffType;
    int StaffPic;

    public int getStaffType() {
        return staffType;
    }

    public void setStaffType(int staffType) {
        this.staffType = staffType;
    }

    public int getStaffPic() {
        return StaffPic;
    }

    public void setStaffPic(int staffPic) {
        StaffPic = staffPic;
    }

    public String getStaffSpecialty() {
        return staffSpecialty;
    }

    public void setStaffSpecialty(String staffSpecialty) {
        this.staffSpecialty = staffSpecialty;
    }

    public String getStaffLocation() {
        return staffLocation;
    }

    public void setStaffLocation(String staffLocation) {
        this.staffLocation = staffLocation;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    String staffSpecialty = "";
    String staffLocation = "";
    String staffName = "";

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(StaffPic );
        dest.writeString(staffName);
        dest.writeString(staffSpecialty);
        dest.writeString(staffLocation);

    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public TeamObject createFromParcel(Parcel in) {
            return new TeamObject(in);
        }

        public TeamObject[] newArray(int size) {
            return new TeamObject[size];
        }
    };
}
