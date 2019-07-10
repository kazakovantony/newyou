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
import android.widget.FrameLayout;
import android.widget.ToggleButton;

import com.kazakov.newyou.app.App;
import com.kazakov.newyou.app.R;
import com.kazakov.newyou.app.model.json.PredictionResult;
import com.kazakov.newyou.app.model.table.Exercise;
import com.kazakov.newyou.app.model.table.SensorsRecordsBatch;
import com.kazakov.newyou.app.model.WorkoutState;
import com.kazakov.newyou.app.model.table.Workout;
import com.kazakov.newyou.app.repository.NewYouRepo;
import com.kazakov.newyou.app.service.PredictorService;
import com.kazakov.newyou.app.service.WatchServiceHolder;
import com.kazakov.newyou.app.service.WorkoutService;
import com.kazakov.newyou.app.service.converter.SensorsRecordsBatchConverter;
import com.kazakov.newyou.app.service.event.EventService;
import com.kazakov.newyou.app.service.event.base.impl.UpdateViewEvent;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

public class WorkoutView extends Fragment {

    @Inject
    WorkoutState workoutState;
    @Inject
    WatchServiceHolder watchConnectionServiceHolder;
    @Inject
    EventService eventService;
    @Inject
    NewYouRepo newYouRepo;
    @Inject
    PredictorService predictorService;
    @Inject
    WorkoutService workoutService;
    @Inject
    SensorsRecordsBatchConverter converter;

    ArrayAdapter<String> workouts;

    FrameLayout currentFrame;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        App.getComponent(getContext()).inject(this);
        setHasOptionsMenu(true);

        currentFrame = (FrameLayout) inflater.inflate(R.layout.fragment_workout, container, false);

        final ToggleButton toggle = currentFrame.findViewById(R.id.toggleButton);

        toggle.setOnCheckedChangeListener((buttonView, isChecked) -> {
            try {
                if (isChecked) {
                    workoutService.stopWorkout();
                    doPredict();
                } else {
                    workoutService.startWorkout();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return currentFrame;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }

    private void updateTextViewHandle(UpdateViewEvent event) {
        getActivity().runOnUiThread(() -> workouts.add(event.getUpdateText()));
    }

    private void doPredict() throws IOException {
        List<SensorsRecordsBatch> forPredict = newYouRepo.findAll(SensorsRecordsBatch.class); //here should be only not predicted yet
        List<PredictionResult> predictedGymActivity = predictorService
                .predict(converter.convertToRecords(forPredict));
        newYouRepo.delete(forPredict);
        Workout workout = converter.createWorkout();
        newYouRepo.create(workout);
        newYouRepo.refresh(workout);
        List<Exercise> exercises = converter.createExercises(workout, predictedGymActivity, forPredict);
        newYouRepo.create(exercises);
        updateTextViewHandle(new UpdateViewEvent(predictedGymActivity.toString()));// should extract all workouts from db
    }
}