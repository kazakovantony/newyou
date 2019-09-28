package com.kazakov.newyou.app.service.converter;

import com.kazakov.newyou.app.model.json.PredictionResult;
import com.kazakov.newyou.app.model.json.SensorsRecord;
import com.kazakov.newyou.app.model.table.ActualExercise;
import com.kazakov.newyou.app.model.table.PredictedExercise;
import com.kazakov.newyou.app.model.table.SensorsRecordsBatch;
import com.kazakov.newyou.app.model.table.SensorsRecordsBatchPredictedExercise;
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

    public SensorsRecordsBatch convert(String sensorsRecords, Workout workout) {
        SensorsRecordsBatch sensorsRecordsBatch = new SensorsRecordsBatch();
        sensorsRecordsBatch.setSensorsRecords(sensorsRecords);
        sensorsRecordsBatch.setWorkout(workout);
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

    public List<PredictedExercise> createPredictedExercises(Workout workout, List<PredictionResult> predictedGymActivity) {
        return predictedGymActivity.stream().map(p -> createExercise(p, workout))
                .collect(Collectors.toList());
    }

    private PredictedExercise createExercise(PredictionResult predictionResult, Workout workout) {
        PredictedExercise predictedExercise = new PredictedExercise();
        predictedExercise.setIterationAmount(predictionResult.numberOfRepeats);
        predictedExercise.setType(predictionResult.activity);
        predictedExercise.setDuration(predictionResult.duration);
        predictedExercise.setWorkout(workout);
        return predictedExercise;
    }

    private ActualExercise createActualExercise(PredictionResult predictionResult, Workout workout){
        ActualExercise actualExercise= new ActualExercise();
        actualExercise.setIterationAmount(predictionResult.numberOfRepeats);
        actualExercise.setType(predictionResult.activity);
        actualExercise.setDuration(predictionResult.duration);
        actualExercise.setWorkout(workout);
        return actualExercise;
    }

    public List<ActualExercise> createActualExercises(Workout workout, List<PredictionResult> predictedGymActivity){
        return predictedGymActivity.stream().map(p -> createActualExercise(p, workout))
                .collect(Collectors.toList());
    }

    public List<SensorsRecordsBatchPredictedExercise> createSensorsRecordsBatchPredictedExercises(List<PredictedExercise> exercises,
                                                                                                  List<SensorsRecordsBatch> forPredict) {
        return exercises.stream()
                .map(exercise -> createSensorsRecordsBatchPredictedExercises(exercise, forPredict))
                .flatMap(List::stream).collect(Collectors.toList());
    }

    private List<SensorsRecordsBatchPredictedExercise> createSensorsRecordsBatchPredictedExercises(PredictedExercise predictedExercise,
                                                                                                   List<SensorsRecordsBatch> forPredict) {
        return forPredict.stream().map(batch -> createSensorsRecordsBatchPredictedExercise(batch, predictedExercise)).collect(Collectors.toList());
    }

    private SensorsRecordsBatchPredictedExercise createSensorsRecordsBatchPredictedExercise(SensorsRecordsBatch batch, PredictedExercise predictedExercise) {
        SensorsRecordsBatchPredictedExercise sensorsRecordsBatchPredictedExercise = new SensorsRecordsBatchPredictedExercise();
        sensorsRecordsBatchPredictedExercise.setExercise(predictedExercise);
        sensorsRecordsBatchPredictedExercise.setSensorsRecordsBatch(batch);
        return sensorsRecordsBatchPredictedExercise;
    }
}
