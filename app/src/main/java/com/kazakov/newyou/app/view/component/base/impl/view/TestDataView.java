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
import com.kazakov.newyou.app.service.WatchConnectionProvider;
import com.kazakov.newyou.app.service.WatchConnectionService;
import com.kazakov.newyou.app.service.WatchServiceHolder;
import com.kazakov.newyou.app.service.event.base.impl.DataReceiveEvent;
import com.kazakov.newyou.app.service.event.EventService;
import com.kazakov.newyou.app.service.event.base.impl.UpdateViewEvent;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

public class TestDataView extends Activity {

    @Inject
    EventService eventService;
    ListView listView;
    ArrayAdapter<String> workouts;
    @Inject
    DataService dataService;
    @Inject
    PredictorService predictorService;
    @Inject
    WatchServiceHolder watchConnectionServiceHolder;
    @Inject
    WorkoutState workoutState;
    @Inject
    ServiceConnectionListener serviceConnectionListener;
    @Inject
    JsonService jsonService;
    @Inject
    WatchConnectionProvider watchConnectionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
/*
refactor it to show these steps:
        1 inject dependencies
        2 init views
        3 bind event handlers
        4 init watch data listener
        4.1 init android connection
        4.2 init Watch Connection Service during android connection -> initiated by android -> spy should created (current implementation requires provider)
        4.3 on receiving connection success with gear we get listener (SocketListener) with onReceive event
*/

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        App.getComponent(this).inject(this);

        workoutState.setBound(bindService(new Intent(this, watchConnectionProvider.take()),
                serviceConnectionListener, Context.BIND_AUTO_CREATE));
        initWorkoutsView();
        serviceConnectionListener.setWatchServiceHolder(watchConnectionServiceHolder);
        bindHandlersOnEvents();
    }

    private void bindHandlersOnEvents() {
        eventService.addEventHandler(this::dataReceiveHandle, DataReceiveEvent.class);
       // eventService.addEventHandler(this::updateTextViewHandle, UpdateViewEvent.class);
    }

    private void initWorkoutsView() {
        workouts = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1);
        listView = findViewById(R.id.workouts);
        listView.setAdapter(workouts);
    }

    private void dataReceiveHandle(DataReceiveEvent dataReceiveEvent) {
        //runOnUiThread(messageAdapter.addMessageTask(dataReceiveEvent.getMessage()));
        //messagesView.setSelection(messageAdapter.getCount() - 1);
        List<SensorsRecord> sensorsRecords = jsonService
                .deserializeJsonArray(SensorsRecord[].class, dataReceiveEvent.getMessage());
        dataService.storeSensorsData(sensorsRecords);
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