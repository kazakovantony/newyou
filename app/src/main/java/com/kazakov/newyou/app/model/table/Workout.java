package com.kazakov.newyou.app.model.table;

import com.j256.ormlite.field.DatabaseField;

import java.util.List;

public class Workout {

    @DatabaseField(generatedId = true)
    public long id;

    public List<Exercise> exercises;
}
