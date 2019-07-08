package com.kazakov.newyou.app.model.table;


import com.j256.ormlite.field.DatabaseField;

public class ActualExercise extends Exercise {
    @DatabaseField(generatedId = true)
    private int id;

}
