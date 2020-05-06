package com.kazakov.newyou.app.service.holders;

import com.kazakov.newyou.app.model.json.PredictionResult;
import com.kazakov.newyou.app.model.table.ActualExercise;
import com.kazakov.newyou.app.model.table.PredictedExercise;
import com.kazakov.newyou.app.model.table.Workout;
import com.kazakov.newyou.app.service.PredictorService;
import com.kazakov.newyou.app.service.WorkoutService;
import com.kazakov.newyou.app.service.converter.SensorsRecordsBatchConverter;
import com.kazakov.newyou.app.service.performance.annotation.Time;

import java.util.List;

import javax.inject.Inject;

public class WorkoutController {

    WorkoutService workoutService;
    PredictorService predictorService;
    SensorsRecordsBatchConverter converter;

    public List<PredictionResult> getSavedResults() {
        return savedResults;
    }

    private List<PredictionResult> savedResults;

    public void setSavedResults(List<PredictionResult> savedResults) {
        this.savedResults = savedResults;

    }

    @Inject
    public WorkoutController(WorkoutService workoutService, PredictorService predictorService,
                             SensorsRecordsBatchConverter converter) {
        this.workoutService = workoutService;
        this.predictorService = predictorService;
        this.converter = converter;
    }

    public void startWorkout() {
        workoutService.initWorkout();
        workoutService.startActivity();
    }

    public void stopActivity() {
        workoutService.stopWorkout();
    }

    public List<PredictionResult> getPredictedResult() {
        return predictorService.predict(converter.convertToRecords(workoutService.getDataForPredict()));
    }

    @Time
    public void doPredict() {
        List<PredictionResult> predictedGymActivity = predictorService.predict(converter.convertToRecords(workoutService.getDataForPredict()));
        List<PredictedExercise> exercises = predictorService.getPredictedExercise(workoutService.getWorkout(), predictedGymActivity);
        predictorService.getPredictedExercises(exercises, workoutService.getDataForPredict());
    }

    public List<PredictedExercise> getPredictedExercise(List<PredictionResult> predictionResults) {
        Workout w = converter.createWorkout();
        return predictorService.getPredictedExercise(w, predictionResults);
    }

    public List<ActualExercise> getActualExercises(List<PredictionResult> predictionResults) {
        Workout w = converter.createWorkout();
        return predictorService.getActualExercise(w, predictionResults);
    }
}
