package com.kazakov.newyou.app.model.table;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "sensors_records_batch_actual_exercise")
public class SensorsRecordsBatchActualExercise {

    @DatabaseField(generatedId = true)
    protected int id;

    @DatabaseField(canBeNull = false, foreign = true, columnName = "actual_exercise")
    private ActualExercise exercise;

    @DatabaseField(canBeNull = false, foreign = true, columnName = "sensors_records_batch")
    private SensorsRecordsBatch sensorsRecordsBatch;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ActualExercise getExercise() {
        return exercise;
    }

    public void setExercise(ActualExercise exercise) {
        this.exercise = exercise;
    }

    public SensorsRecordsBatch getSensorsRecordsBatch() {
        return sensorsRecordsBatch;
    }

    public void setSensorsRecordsBatch(SensorsRecordsBatch sensorsRecordsBatch) {
        this.sensorsRecordsBatch = sensorsRecordsBatch;
    }
}
