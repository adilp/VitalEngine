package com.adilpatel.vitalengine;

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

