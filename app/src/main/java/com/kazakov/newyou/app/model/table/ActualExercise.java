package com.kazakov.newyou.app.model.table;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "actual_exercise")
public class ActualExercise extends Exercise {

    @ForeignCollectionField(eager = false)
    private ForeignCollection<SensorsRecordsBatchActualExercise> sensorsRecordsBatchActualExercises;

    public ForeignCollection<SensorsRecordsBatchActualExercise> getSensorsRecordsBatchActualExercises() {
        return sensorsRecordsBatchActualExercises;
    }

    public void setSensorsRecordsBatchActualExercises(ForeignCollection<SensorsRecordsBatchActualExercise> sensorsRecordsBatchActualExercises) {
        this.sensorsRecordsBatchActualExercises = sensorsRecordsBatchActualExercises;
    }
}
