package com.kazakov.newyou.app.model;

public class WorkoutState {

    boolean isBound;
    boolean isActive;

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
