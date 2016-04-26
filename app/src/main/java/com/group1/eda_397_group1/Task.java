package com.group1.eda_397_group1;

import java.io.Serializable;

/**
 * Created by jesper on 2016-04-19.
 */
public class Task implements Serializable {

    private String name;
    private int duration;
    private User owner;
    private User pairprogramer1;
    private User pairprogramer2;


    Task(String name, int duration, User owner, User pairProgramer1, User pairProgramer2){
        this.setName(name);
        this.setDuration(duration);
        this.setOwner(owner);
        this.setPairprogramer1(pairProgramer1);
        this.setPairprogramer2(pairProgramer2);
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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public User getPairprogramer1() {
        return pairprogramer1;
    }

    public void setPairprogramer1(User pairprogramer1) {
        this.pairprogramer1 = pairprogramer1;
    }

    public User getPairprogramer2() {
        return pairprogramer2;
    }

    public void setPairprogramer2(User pairprogramer2) {
        this.pairprogramer2 = pairprogramer2;
    }

    @Override
    public String toString() {
        return "Task[" +
                "name='" + name + '\'' +
                ", duration=" + duration +
                ", owner=" + owner +
                ", pairprogramer1=" + pairprogramer1 +
                ", pairprogramer2=" + pairprogramer2 +
                ']';
    }
}
