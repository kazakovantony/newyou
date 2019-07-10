package com.kazakov.newyou.app.model.json;

import com.google.gson.annotations.Expose;
import com.kazakov.newyou.app.model.GymActivity;

public class PredictionResult {

    @Expose
    public GymActivity activity;
    @Expose
    public String duration;
    @Expose
    public int number_of_repeats;

    @Override
    public String toString() {
        return "PredictionResult{" +
                "activity='" + activity + '\'' +
                ", duration='" + duration + '\'' +
                ", number_of_repeats=" + number_of_repeats +
                '}';
    }
}
