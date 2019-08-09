package com.kazakov.newyou.app.service.holders;

import com.kazakov.newyou.app.model.json.PredictionResult;
import com.kazakov.newyou.app.model.table.PredictedExercise;
import com.kazakov.newyou.app.service.PredictorService;
import com.kazakov.newyou.app.service.WorkoutService;
import com.kazakov.newyou.app.service.converter.SensorsRecordsBatchConverter;
import com.kazakov.newyou.app.service.performance.annotation.Time;

import java.util.List;

import javax.inject.Inject;

public class WorkoutController {

    @Inject
    WorkoutService workoutService;
    @Inject
    PredictorService predictorService;
    @Inject
    SensorsRecordsBatchConverter converter;

    @Inject
    public WorkoutController(){}

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
        List<PredictedExercise> exercises = predictorService.getPredictedExersise(workoutService.getWorkout(), predictedGymActivity);
        predictorService.getPredictedExercises(exercises, workoutService.getDataForPredict());
    }


}
