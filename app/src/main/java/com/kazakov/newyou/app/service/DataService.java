package com.kazakov.newyou.app.service;

import com.kazakov.newyou.app.model.SensorsRecord;
import com.kazakov.newyou.app.model.Workout;
import com.noodle.Noodle;

import java.util.List;

import javax.inject.Inject;

public class DataService {

    Noodle noodle;

    @Inject
    public DataService(Noodle noodle) {
        this.noodle = noodle;
    }

    public void storeSensorsData(List<SensorsRecord> sensorData) {
        sensorData.forEach(e -> noodle.collectionOf(SensorsRecord.class).put(e).now());
    }

    public List<SensorsRecord> extractSensorsData() {
        return noodle.collectionOf(SensorsRecord.class).all().now();
    }

    public void storeTraine(Workout workout) {
        noodle.collectionOf(Workout.class).put(workout).now();
    }

    public void deleteSensorData(List<SensorsRecord> forDelete) {
        forDelete.forEach(e -> noodle.collectionOf(SensorsRecord.class).delete(e.id).now());
    }

    public void storeWorkout(Workout workout) {
        noodle.collectionOf(Workout.class).put(workout).now();
    }
}

