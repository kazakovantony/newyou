package com.itbulls.newyou.app.model;

public class WorkoutState {

    //String athleteName;
    //int iterationAmount;
    //GymActivity gymActivity;
    boolean isBound;
    boolean isActive;


    /*public synchronized String getAthleteName() {
        return athleteName;
    }*/

    /*public synchronized void setAthleteName(String athleteName) {
        this.athleteName = athleteName;
    }*/

    /*public synchronized int getIterationAmount() {
        return iterationAmount;
    }

    public synchronized void setIterationAmount(int iterationAmount) {
        this.iterationAmount = iterationAmount;
    }

    public synchronized GymActivity getGymActivity() {
        return gymActivity;
    }

    public synchronized void setGymActivity(GymActivity gymActivity) {
        this.gymActivity = gymActivity;
    }*/

    public synchronized boolean isBound() {
        return isBound;
    }

    public synchronized void setBound(boolean bound) {
        isBound = bound;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
