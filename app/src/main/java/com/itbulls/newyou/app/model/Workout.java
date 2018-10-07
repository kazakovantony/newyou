package com.itbulls.newyou.app.model;

import com.google.gson.annotations.Expose;
import com.noodle.Id;

import java.util.List;

public class Workout {

    @Id
    public long id;
    @Expose
    public List<PredictionResult> activities;

    public static Workout create(List<PredictionResult> activities) {
        Workout result = new Workout();
        result.activities = activities;
        return result;
    }
}
