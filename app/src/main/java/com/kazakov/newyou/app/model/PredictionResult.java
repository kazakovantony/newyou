package com.kazakov.newyou.app.model;

import com.google.gson.annotations.Expose;

public class PredictionResult {

    @Expose
    String activity;
    @Expose
    String duration;
    @Expose
    int number_of_repeats;

    public PredictionResult(String activity, String duration, int number_of_repeats) {
        this.activity = activity;
        this.duration = duration;
        this.number_of_repeats = number_of_repeats;
    }

    @Override
    public String toString() {
        return "PredictionResult{" +
                "activity='" + activity + '\'' +
                ", duration='" + duration + '\'' +
                ", number_of_repeats=" + number_of_repeats +
                '}';
    }
}
