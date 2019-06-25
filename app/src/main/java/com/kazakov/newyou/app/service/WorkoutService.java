package com.kazakov.newyou.app.service;

import com.kazakov.newyou.app.model.WorkoutState;
import com.kazakov.newyou.app.service.event.EventService;

import java.io.IOException;

import javax.inject.Inject;

public class WorkoutService {

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

    public void stopWorkout() throws IOException {
        if (watchConnectionServiceHolder.getWatchConnectionService().closeConnection()) {
            //  ((Button) getActivity().findViewById(R.id.buttonChangeMode)).setText(R.string.buttonStart);
            workoutState.setActive(false);
        //    doPredict();
        }
    }

    public void startWorkout() {
        watchConnectionServiceHolder.getWatchConnectionService().setEventService(eventService); // it should be done during init, not start workout
        watchConnectionServiceHolder.getWatchConnectionService().findPeers(); // it should be done during init, not start workout
        if (!watchConnectionServiceHolder.getWatchConnectionService().sendData("START")) {
    //        Toast.makeText(getActivity().getApplicationContext(),
      //              R.string.watchIsNotReachable, Toast.LENGTH_LONG).show();
        } else {
            workoutState.setActive(true);
            //((Button) getActivity().findViewById(R.id.buttonChangeMode)).setText(R.string.buttonStop);
        }
    }
}
