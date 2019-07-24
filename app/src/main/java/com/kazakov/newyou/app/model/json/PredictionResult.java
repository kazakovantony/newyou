package com.kazakov.newyou.app.model.json;

import com.google.gson.annotations.Expose;
import com.kazakov.newyou.app.model.GymActivity;

public class PredictionResult {

    @Expose
    public GymActivity activity;
    @Expose
    public String duration;
    @Expose
    public int numberOfRepeats;

    public PredictionResult(GymActivity activity, String  duration, int numberOfRepeats) {
        this.activity = activity;
        this.duration = duration;
        this.numberOfRepeats = numberOfRepeats;
    }

    public GymActivity getActivity() {
        return activity;
    }

    public String  getDuration() {
        return duration;
    }

    public int getNumberOfRepeats() {
        return numberOfRepeats;
    }

    @Override
    public String toString() {
        return "PredictionResult{" +
                "activity='" + activity + '\'' +
                ", duration='" + duration + '\'' +
                ", numberOfRepeats=" + numberOfRepeats +
                '}';
    }
}
