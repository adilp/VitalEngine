package com.adilpatel.vitalengine.Models;

/**
 * Created by Adil on 6/11/16.
 */
public class MessageData {


        String message = "";
        boolean isRead = false;
        int images;
        String name = "";
        String subject = "";
        String type = "";
        int id = -1;

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

        public boolean isRead() {
            return isRead;
        }

        public void setRead(boolean isRead) {
            this.isRead = isRead;
        }

        public void setImage (int images){
            this.images = images;
        }

        public int getImage(){
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

