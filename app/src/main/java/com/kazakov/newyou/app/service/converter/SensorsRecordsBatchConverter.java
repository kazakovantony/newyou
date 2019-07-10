package com.kazakov.newyou.app.service.converter;

import com.kazakov.newyou.app.model.json.PredictionResult;
import com.kazakov.newyou.app.model.json.SensorsRecord;
import com.kazakov.newyou.app.model.table.Exercise;
import com.kazakov.newyou.app.model.table.PredictedExercise;
import com.kazakov.newyou.app.model.table.SensorsRecordsBatch;
import com.kazakov.newyou.app.model.table.Workout;
import com.kazakov.newyou.app.service.JsonService;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;


public class SensorsRecordsBatchConverter {

    JsonService jsonService;

    @Inject
    public SensorsRecordsBatchConverter(JsonService jsonService) {
        this.jsonService = jsonService;
    }

    public SensorsRecordsBatch convert(String sensorsRecords) {
        SensorsRecordsBatch sensorsRecordsBatch = new SensorsRecordsBatch();
        sensorsRecordsBatch.setSensorsRecords(sensorsRecords);
        return sensorsRecordsBatch;
    }

    public List<SensorsRecord> convertToRecords(List<SensorsRecordsBatch> sensorsRecords) {
        return sensorsRecords.stream().map(item -> jsonService
                .deserializeJsonArray(SensorsRecord[].class, item.getSensorsRecords()))
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    public Workout createWorkout() {
        return new Workout();
    }

    public List<Exercise> createExercises(Workout workout, List<PredictionResult> predictedGymActivity,
                                          List<SensorsRecordsBatch> sensorsRecordsBatch) {
        return predictedGymActivity.stream().map(p -> createExercise(p, workout, sensorsRecordsBatch))
                .collect(Collectors.toList());
    }

    private PredictedExercise createExercise(PredictionResult predictionResult, Workout workout,
                                             List<SensorsRecordsBatch> sensorsRecordsBatch) {
        PredictedExercise predictedExercise = new PredictedExercise();
        predictedExercise.setIterationAmount(predictionResult.number_of_repeats);
        predictedExercise.setType(predictionResult.activity);
        predictedExercise.setDuration(predictionResult.duration);
        predictedExercise.setWorkout(workout);
        predictedExercise.setSensorsRecordsBatch(sensorsRecordsBatch);
        return predictedExercise;
    }
}
