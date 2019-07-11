package com.kazakov.newyou.app.model.table;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;

public class Workout {

    @DatabaseField(generatedId = true)
    private long id;

    @ForeignCollectionField(eager = false)
    private ForeignCollection<PredictedExercise> predictedExercises;

    @ForeignCollectionField(eager = false)
    private ForeignCollection<ActualExercise> actualExercises;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
