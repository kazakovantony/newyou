package com.kazakov.newyou.app.view.component.base.impl.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.kazakov.newyou.app.service.WatchServiceHolder;
import com.kazakov.newyou.app.service.event.EventService;
import com.kazakov.newyou.app.service.event.base.impl.UpdateViewEvent;
import com.kazakov.newyou.app.service.holders.WorkoutController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class WorkoutView extends Fragment {

    private static final Logger LOGGER = LoggerFactory.getLogger(WorkoutView.class);

    @Inject
    WatchServiceHolder watchConnectionServiceHolder;
    @Inject
    EventService eventService;

    @Inject
    WorkoutController workoutController;
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
            if (isChecked) {
                workoutController.stopActivity();
                workoutController.doPredict();
            } else {
                //create workout -> save identifier into ram
                workoutController.startWorkout();
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
}