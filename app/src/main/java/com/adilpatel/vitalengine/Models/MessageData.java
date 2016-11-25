package com.adilpatel.vitalengine.Models;

import android.graphics.Bitmap;

/**
 * Created by Adil on 6/11/16.
 */
public class MessageData {


    String message = "";
    int isRead = -1;
    Bitmap images;
    String name = "";
    String subject = "";
    String type = "";
    int id = -1;

    public String getLastDate() {
        return lastDate;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    String lastDate = "";


    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    String toUser = "";

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    String photoURL = "";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    String patient = "";

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    String time = "";

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int isRead() {
        return isRead;
    }

    public void setRead(int isRead) {
        this.isRead = isRead;
    }

    public void setImage (Bitmap images){
        this.images = images;
    }

    public Bitmap getImage(){
        return images;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getSubject(){
        return subject;
    }
    public void setSubject(String subject){
        this.subject = subject;
    }

    public String getType(){
        return type;
    }

    public void setType(String type){
        this.type = type;
    }

}

