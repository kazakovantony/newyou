package com.kazakov.newyou.app.model.table;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.kazakov.newyou.app.model.table.base.Entity;

public class Workout implements Entity {

    @DatabaseField(generatedId = true)
    private int id;

    @ForeignCollectionField(eager = false)
    private ForeignCollection<PredictedExercise> predictedExercises;

    @ForeignCollectionField(eager = false)
    private ForeignCollection<ActualExercise> actualExercises;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ForeignCollection<PredictedExercise> getPredictedExercises() {
        return predictedExercises;
    }

    public void setPredictedExercises(ForeignCollection<PredictedExercise> predictedExercises) {
        this.predictedExercises = predictedExercises;
    }

    public ForeignCollection<ActualExercise> getActualExercises() {
        return actualExercises;
    }

    public void setActualExercises(ForeignCollection<ActualExercise> actualExercises) {
        this.actualExercises = actualExercises;
    }
}
