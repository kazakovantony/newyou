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

    public <T> List<T> extractDataByType(Class<T> type) {
        return noodle.collectionOf(type).all().now();
    }

    public void storeTraine(Workout workout) {
        noodle.collectionOf(Workout.class).put(workout).now();
    }

    public <T> void deleteData(List<T> forDelete, Class<T> type) {
        forDelete.forEach(e -> {
            try {
                noodle.collectionOf(type).delete(type.getField("id").getLong(e)).now();
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            } catch (NoSuchFieldException e1) {
                e1.printStackTrace();
            }
        });
    }

    public void storeWorkout(Workout workout) {
        noodle.collectionOf(Workout.class).put(workout).now();
    }

    public <T> void deleteCollection(Class<T> type) {
        deleteData(extractDataByType(type), type);
    }
}

