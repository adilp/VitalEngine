package com.adilpatel.vitalengine.expand;

/**
 * Created by Adil on 7/20/16.
 */
public class StaffObject {


    String Staffname = "";

    public String getStaffType() {
        return staffType;
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
}
