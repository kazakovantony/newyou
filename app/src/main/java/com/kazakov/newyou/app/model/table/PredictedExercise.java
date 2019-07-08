package com.kazakov.newyou.app.model.table;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.kazakov.newyou.app.model.GymActivity;

@DatabaseTable(tableName = "predicted_exercise")
public class PredictedExercise extends Exercise {

    @DatabaseField(generatedId = true)
    private int id;

    private GymActivity gymActivity;

    @DatabaseField(columnName = "iteration_amount")
    private int iterationAmount;

}
