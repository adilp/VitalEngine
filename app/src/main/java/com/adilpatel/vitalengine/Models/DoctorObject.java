package com.adilpatel.vitalengine.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Adil on 7/20/16.
 */
public class DoctorObject implements Parcelable {

    private List<Object> mChildrenList;

    public DoctorObject() {


    }

    public DoctorObject(String name) {
        Docname = name;

    }

    public DoctorObject (Parcel in){
        this.DocPic = in.readInt();
        this.Docname= in.readString();
        this.Docspecialty = in.readString();
        this.DocLocation = in.readString();
    }

    String Docname = "";

    public Integer getDocId() {
        return DocId;
    }

    public void setDocId(Integer docId) {
        DocId = docId;
    }

    int DocId;

    public String getDocname() {
        return Docname;
    }

    public void setDocname(String docname) {
        Docname = docname;
    }

    public int getDocPic() {
        return DocPic;
    }

    public void setDocPic(int docPic) {
        DocPic = docPic;
    }

    public String getDocspecialty() {
        return Docspecialty;
    }

    public void setDocspecialty(String docspecialty) {
        Docspecialty = docspecialty;
    }

    public String getDocLocation() {
        return DocLocation;
    }

    public void setDocLocation(String docLocation) {
        DocLocation = docLocation;
    }

    int DocPic;
    String Docspecialty = "";
    String DocLocation = "";





    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(DocPic );
        dest.writeString(Docname);
        dest.writeString(Docspecialty);
        dest.writeString(DocLocation);

    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public DoctorObject createFromParcel(Parcel in) {
            return new DoctorObject(in);
        }

        public DoctorObject[] newArray(int size) {
            return new DoctorObject[size];
        }
    };

}
