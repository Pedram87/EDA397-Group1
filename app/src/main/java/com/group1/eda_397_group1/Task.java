package com.group1.eda_397_group1;

import java.io.Serializable;

/**
 * Created by jesper on 2016-04-19.
 */
public class Task implements Serializable {

    private String id;
    private String name;
    private int duration;
    private String ownerID;
    private String pairprogramer1ID;
    private String pairprogramer2IdD;


    Task(String name, int duration, String ownerID, String pairprogramer1ID, String pairProgramer2ID){
        this.setName(name);
        this.setDuration(duration);
        this.setOwnerID(ownerID);
        this.setPairprogramer1ID(pairprogramer1ID);
        this.setPairprogramer2(pairProgramer2ID);
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public int getDuration() {

        return duration;
    }

    public void setDuration(int duration) {

        this.duration = duration;
    }

    public String getOwnerID() {

        return ownerID;
    }

    public void setOwnerID(String ownerID) {

        this.ownerID = ownerID;
    }

    public String getPairprogramer1ID() {

        return pairprogramer1ID;
    }

    public void setPairprogramer1ID(String pairprogramer1ID) {
        this.pairprogramer1ID = pairprogramer1ID;
    }

    public String getPairprogramer2D() {

        return pairprogramer2IdD;
    }

    public void setPairprogramer2(String pairprogramer2) {
        this.pairprogramer2IdD = pairprogramer2;
    }

    @Override
    public String toString() {
        return "Task[" +
                "name='" + name + '\'' +
                ", duration=" + duration +
                ", owner=" + ownerID +
                ", pairprogramer1=" + pairprogramer1ID +
                ", pairprogramer2=" + pairprogramer2IdD +
                ']';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
