package com.kazakov.newyou.app.service;

import com.kazakov.newyou.app.model.WorkoutState;
import com.kazakov.newyou.app.model.table.SensorsRecordsBatch;
import com.kazakov.newyou.app.model.table.Workout;
import com.kazakov.newyou.app.repository.NewYouRepo;
import com.kazakov.newyou.app.service.converter.SensorsRecordsBatchConverter;
import com.kazakov.newyou.app.service.event.EventService;

import java.util.List;

import javax.inject.Inject;

public class WorkoutService {

    NewYouRepo newYouRepo;
    SensorsRecordsBatchConverter converter;
    private Workout workout;

    WatchServiceHolder watchConnectionServiceHolder;
    WorkoutState workoutState;
    EventService eventService;

    @Inject
    public WorkoutService(WatchServiceHolder watchConnectionServiceHolder, WorkoutState workoutState,
                          EventService eventService) {
        this.watchConnectionServiceHolder = watchConnectionServiceHolder;
        this.workoutState = workoutState;
        this.eventService = eventService;
    }

    public Workout getWorkout() {
        return workout;
    }

    public void stopWorkout() {
        if (watchConnectionServiceHolder.getWatchConnectionService().closeConnection()) {
            workoutState.setActive(false);
        }
    }

    public void initWorkout() {
        watchConnectionServiceHolder.getWatchConnectionService().setEventService(eventService);
        watchConnectionServiceHolder.getWatchConnectionService().findPeers();
    }

    public void startActivity() {
        if (!watchConnectionServiceHolder.getWatchConnectionService().sendData("START")) {

        } else {
            workoutState.setActive(true);
            workout = converter.createWorkout();
            newYouRepo.create(workout);
        }
    }

    public List<SensorsRecordsBatch> getDataForPredict() {
        return newYouRepo.findMatches(SensorsRecordsBatch.class, workout, "workout");

    }

    public void refreshWorkout() {
        newYouRepo.refresh(workout);
    }
}
