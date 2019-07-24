package com.kazakov.newyou.app.model.json;

import com.google.gson.annotations.Expose;
import com.kazakov.newyou.app.model.GymActivity;

import java.time.LocalDateTime;

public class PredictionResult {

    @Expose
    public GymActivity activity;
    @Expose
    public String duration;
    @Expose
    public int number_of_repeats;

    public PredictionResult(GymActivity activity, String  duration, int number_of_repeats) {
        this.activity = activity;
        this.duration = duration;
        this.number_of_repeats = number_of_repeats;
    }

    public GymActivity getActivity() {
        return activity;
    }

    public String  getDuration() {
        return duration;
    }

    public int getNumber_of_repeats() {
        return number_of_repeats;
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
