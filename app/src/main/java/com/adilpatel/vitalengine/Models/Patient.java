package com.adilpatel.vitalengine.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Adil on 7/27/16.
 */
public class Patient implements Parcelable {

    public Patient(){

    }


    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getFor() {
        return For;
    }

    public void setFor(String aFor) {
        For = aFor;
    }

    String DOB;
    String For;




    protected Patient(Parcel in) {
        name = in.readString();
        DOB = in.readString();
        For = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(DOB);
        dest.writeString(For);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Patient> CREATOR = new Parcelable.Creator<Patient>() {
        @Override
        public Patient createFromParcel(Parcel in) {
            return new Patient(in);
        }

        @Override
        public Patient[] newArray(int size) {
            return new Patient[size];
        }
    };
}
