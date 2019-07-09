package com.kazakov.newyou.app.model.table;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;

public class Workout {

    @DatabaseField(generatedId = true)
    private long id;

    @ForeignCollectionField(eager = false)
    private ForeignCollection<Exercise> exercises;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ForeignCollection<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(ForeignCollection<Exercise> exercises) {
        this.exercises = exercises;
    }
}
