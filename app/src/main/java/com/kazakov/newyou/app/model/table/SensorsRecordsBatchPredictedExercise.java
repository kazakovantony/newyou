package com.kazakov.newyou.app.model.table;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.kazakov.newyou.app.model.table.base.Entity;

@DatabaseTable(tableName = "sensors_records_batch_predicted_exercise")
public class SensorsRecordsBatchPredictedExercise implements Entity {

    @DatabaseField(generatedId = true)
    protected int id;

    @DatabaseField(canBeNull = false, foreign = true, columnName = "predicted_exercise")
    private PredictedExercise exercise;

    @DatabaseField(canBeNull = false, foreign = true, columnName = "sensors_records_batch")
    private SensorsRecordsBatch sensorsRecordsBatch;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PredictedExercise getExercise() {
        return exercise;
    }

    public void setExercise(PredictedExercise exercise) {
        this.exercise = exercise;
    }

    public SensorsRecordsBatch getSensorsRecordsBatch() {
        return sensorsRecordsBatch;
    }

    public void setSensorsRecordsBatch(SensorsRecordsBatch sensorsRecordsBatch) {
        this.sensorsRecordsBatch = sensorsRecordsBatch;
    }
}
