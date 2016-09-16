package com.adilpatel.vitalengine.ActivitiesPackage.ChatStory;

/**
 * Created by Adil on 8/16/16.
 */
public class chatMessageModel {

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    String message;
    String time;
    int direction;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    String sender;

    public int isAnnoted() {
        return isAnnoted;
    }

    public void setIsAnnoted(int isAnnoted) {
        this.isAnnoted = isAnnoted;
    }

    int isAnnoted;

}
