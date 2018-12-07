package com.kazakov.newyou.app.view.component.base.impl.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kazakov.newyou.app.App;
import com.kazakov.newyou.app.R;
import com.kazakov.newyou.app.listener.ServiceConnectionListener;
import com.kazakov.newyou.app.model.GymActivity;
import com.kazakov.newyou.app.model.PredictionResult;
import com.kazakov.newyou.app.model.SensorsRecord;
import com.kazakov.newyou.app.model.Workout;
import com.kazakov.newyou.app.model.WorkoutState;
import com.kazakov.newyou.app.service.DataService;
import com.kazakov.newyou.app.service.JsonService;
import com.kazakov.newyou.app.service.PredictorService;
import com.kazakov.newyou.app.service.WatchConnectionService;
import com.kazakov.newyou.app.service.WatchServiceProvider;
import com.kazakov.newyou.app.service.event.base.impl.DataReceiveEvent;
import com.kazakov.newyou.app.service.event.EventService;
import com.kazakov.newyou.app.service.event.base.impl.UpdateViewEvent;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

public class TestDataView extends Activity {

    static final String START = "START";
    @Inject
    EventService eventService;
    ListView listView;
    ArrayAdapter<String> workouts;
    @Inject
    DataService dataService;
    @Inject
    PredictorService predictorService;
    @Inject
    WatchServiceProvider watchConnectionServiceProvider;
    @Inject
    WorkoutState workoutState;
    @Inject
    ServiceConnectionListener serviceConnectionListener;
    @Inject
    JsonService jsonService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        App.getComponent(this).inject(this);

        workoutState.setBound(bindService(new Intent(TestDataView.this, WatchConnectionService.class),
                serviceConnectionListener, Context.BIND_AUTO_CREATE));
        initWorkoutsView();
        serviceConnectionListener.setWatchServiceProvider(watchConnectionServiceProvider);
        bindHandlersOnEvents();
    }

    private void bindHandlersOnEvents() {
        eventService.addEventHandler(this::dataReceiveHandle, DataReceiveEvent.class);
        eventService.addEventHandler(this::updateTextViewHandle, UpdateViewEvent.class);
    }

    private void initWorkoutsView() {
        workouts = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1);
        listView = findViewById(R.id.workouts);
        listView.setAdapter(workouts);
    }

    public void buttonChangeMode(View view) throws IOException, InterruptedException {

        if (workoutState.isBound()) {
            if (workoutState.isActive()) {
                stopWorkout();
            } else {
                startWorkout();
            }
        } else {
            Toast.makeText(getApplicationContext(),
                    R.string.watchIsNotReachable, Toast.LENGTH_LONG).show();
        }
    }

    private void stopWorkout() throws IOException {
        if (watchConnectionServiceProvider.getWatchConnectionService().closeConnection()) {
            ((Button) findViewById(R.id.buttonChangeMode)).setText(R.string.buttonStart);
            workoutState.setActive(false);
            doPredict();
        }
    }

    private void startWorkout() throws InterruptedException {
        watchConnectionServiceProvider.getWatchConnectionService().setEventService(eventService);
        watchConnectionServiceProvider.getWatchConnectionService().findPeers();
        TimeUnit.SECONDS.sleep(10);
        if (!watchConnectionServiceProvider.getWatchConnectionService().sendData(START)) {
            Toast.makeText(getApplicationContext(),
                    R.string.watchIsNotReachable, Toast.LENGTH_LONG).show();
        } else {
            workoutState.setActive(true);
            ((Button) findViewById(R.id.buttonChangeMode)).setText(R.string.buttonStop);
        }
    }

    private void doPredict() throws IOException {
        List<SensorsRecord> forPredict = dataService.extractSensorsData();
        List<PredictionResult> predictedGymActivity = predictorService.predict(forPredict);
        dataService.deleteSensorData(forPredict);
        dataService.storeWorkout(Workout.create(predictedGymActivity));
        updateTextViewHandle(new UpdateViewEvent(predictedGymActivity.toString()));// should extract all workouts from db
    }

    private void dataReceiveHandle(DataReceiveEvent dataReceiveEvent) {
        //runOnUiThread(messageAdapter.addMessageTask(dataReceiveEvent.getMessage()));
        //messagesView.setSelection(messageAdapter.getCount() - 1);
        List<SensorsRecord> sensorsRecords = jsonService
                .deserializeJsonArray(SensorsRecord[].class, dataReceiveEvent.getMessage());
        dataService.storeSensorsData(sensorsRecords);
    }

    private void updateTextViewHandle(UpdateViewEvent event) {
        runOnUiThread(() -> workouts.add(event.getUpdateText()));
    }

    private String getTextViewText(int id) {
        return ((TextView) findViewById(id)).getText().toString();
    }

    private GymActivity getCurrentActivity(View view) {
        /*switch (view.getId()) {
            case R.id.pushUps:
                return GymActivity.PUSH_UPS;
            case R.id.abs:
                return GymActivity.ABS;
            case R.id.pause:
                return GymActivity.PAUSE;
            case R.id.sitUps:
                return GymActivity.SIT_UPS;
            default:
        */
        throw new IllegalArgumentException();
        //}
    }
}