package com.kazakov.newyou.app.model.table;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "predicted_exercise")
public class PredictedExercise extends Exercise {

    @ForeignCollectionField(eager = false)
    private ForeignCollection<SensorsRecordsBatchPredictedExercise> sensorsRecordsBatchPredictedExercises;

    public ForeignCollection<SensorsRecordsBatchPredictedExercise> getSensorsRecordsBatchPredictedExercises() {
        return sensorsRecordsBatchPredictedExercises;
    }

    public void setSensorsRecordsBatchPredictedExercises(ForeignCollection<SensorsRecordsBatchPredictedExercise> sensorsRecordsBatchPredictedExercises) {
        this.sensorsRecordsBatchPredictedExercises = sensorsRecordsBatchPredictedExercises;
    }
}
