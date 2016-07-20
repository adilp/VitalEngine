package com.adilpatel.vitalengine.expand;

import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;


import java.util.List;

/**
 * Created by Adil on 7/20/16.
 */
public class DoctorObject implements ParentListItem {

    private List<Object> mChildrenList;

    public DoctorObject(String name, StaffObject ChildrenList) {
        Docname = name;
        mChildrenList = (List<Object>) ChildrenList;
    }

    String Docname = "";

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
    public List<?> getChildItemList() {
        return null;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }
}
