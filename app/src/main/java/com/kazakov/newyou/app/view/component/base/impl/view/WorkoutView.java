package com.kazakov.newyou.app.view.component.base.impl.view;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.kazakov.newyou.app.R;
import com.kazakov.newyou.app.model.PredictionResult;
import com.kazakov.newyou.app.model.SensorsRecord;
import com.kazakov.newyou.app.model.Workout;
import com.kazakov.newyou.app.model.WorkoutState;
import com.kazakov.newyou.app.service.DataService;
import com.kazakov.newyou.app.service.PredictorService;
import com.kazakov.newyou.app.service.WatchServiceHolder;
import com.kazakov.newyou.app.service.event.EventService;
import com.kazakov.newyou.app.service.event.base.impl.UpdateViewEvent;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

public class WorkoutView extends Fragment {

    static final String START = "START";

    @Inject
    WorkoutState workoutState;
    @Inject
    WatchServiceHolder watchConnectionServiceHolder;
    @Inject
    EventService eventService;
    @Inject
    DataService dataService;
    @Inject
    PredictorService predictorService;

    ArrayAdapter<String> workouts;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_workout, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }

    private void stopWorkout() throws IOException {
        if (watchConnectionServiceHolder.getWatchConnectionService().closeConnection()) {
            ((Button) getActivity().findViewById(R.id.buttonChangeMode)).setText(R.string.buttonStart);
            workoutState.setActive(false);
            doPredict();
        }
    }

    private void startWorkout() throws InterruptedException {
        watchConnectionServiceHolder.getWatchConnectionService().setEventService(eventService);
        watchConnectionServiceHolder.getWatchConnectionService().findPeers();
        TimeUnit.SECONDS.sleep(10);
        if (!watchConnectionServiceHolder.getWatchConnectionService().sendData(START)) {
            Toast.makeText(getActivity().getApplicationContext(),
                    R.string.watchIsNotReachable, Toast.LENGTH_LONG).show();
        } else {
            workoutState.setActive(true);
            ((Button) getActivity().findViewById(R.id.buttonChangeMode)).setText(R.string.buttonStop);
        }
    }

    public void buttonChangeMode(View view) throws IOException, InterruptedException {

        if (workoutState.isBound()) {
            if (workoutState.isActive()) {
                stopWorkout();
            } else {
                startWorkout();
            }
        } else {
            Toast.makeText(getActivity().getApplicationContext(),
                    R.string.watchIsNotReachable, Toast.LENGTH_LONG).show();
        }
    }

    private void updateTextViewHandle(UpdateViewEvent event) {
        getActivity().runOnUiThread(() -> workouts.add(event.getUpdateText()));
    }

    private void doPredict() throws IOException {
        List<SensorsRecord> forPredict = dataService.extractDataByType(SensorsRecord.class);
        List<PredictionResult> predictedGymActivity = predictorService.predict(forPredict);
        dataService.deleteData(forPredict, SensorsRecord.class);
        dataService.storeWorkout(Workout.create(predictedGymActivity));
        updateTextViewHandle(new UpdateViewEvent(predictedGymActivity.toString()));// should extract all workouts from db
    }
}