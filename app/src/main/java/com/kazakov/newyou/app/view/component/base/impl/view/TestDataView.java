package com.kazakov.newyou.app.view.component.base.impl.view;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.kazakov.newyou.app.App;
import com.kazakov.newyou.app.R;
import com.kazakov.newyou.app.listener.ServiceConnectionListener;
import com.kazakov.newyou.app.model.GymActivity;
import com.kazakov.newyou.app.model.json.SensorsRecord;
import com.kazakov.newyou.app.model.WorkoutState;
import com.kazakov.newyou.app.repository.SensoryRecordRepo;
import com.kazakov.newyou.app.service.JsonService;
import com.kazakov.newyou.app.service.PredictorService;
import com.kazakov.newyou.app.service.WatchConnectionProvider;
import com.kazakov.newyou.app.service.WatchServiceHolder;
import com.kazakov.newyou.app.service.event.base.impl.DataReceiveEvent;
import com.kazakov.newyou.app.service.event.EventService;
import com.kazakov.newyou.app.view.component.base.impl.PageAdapter;

import java.util.List;

import javax.inject.Inject;

public class TestDataView extends AppCompatActivity {

    @Inject
    EventService eventService;
    ListView listView;
    ArrayAdapter<String> workouts;
    @Inject
    SensoryRecordRepo sensoryRecordRepo;
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
   // Toolbar toolbar;
    ViewPager viewPager;
    TabLayout tabLayout;
    TabItem workoutTab;
    TabItem predictionTab;
    PageAdapter pageAdapter;


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
        tabLayout = findViewById(R.id.tablayout);

     //   setSupportActionBar(toolbar);
        workoutTab = findViewById(R.id.tabWorkout);
        predictionTab = findViewById(R.id.tabPrediction);
        viewPager = findViewById(R.id.viewPager);

        pageAdapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapter);

        App.getComponent(this).inject(this);

        workoutState.setBound(bindService(new Intent(this, watchConnectionProvider.take()),
                serviceConnectionListener, Context.BIND_AUTO_CREATE));
        initWorkoutsView();
        serviceConnectionListener.setWatchServiceHolder(watchConnectionServiceHolder);
        bindHandlersOnEvents();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 1) {
                    //toolbar.setBackgroundColor(ContextCompat.getColor(MainActivity.this,
                      //      R.color.colorAccent));
                    tabLayout.setBackgroundColor(ContextCompat.getColor(TestDataView.this,
                            R.color.colorAccent));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        getWindow().setStatusBarColor(ContextCompat.getColor(TestDataView.this,
                                R.color.colorAccent));
                    }
                } else if (tab.getPosition() == 2) {
                    //toolbar.setBackgroundColor(ContextCompat.getColor(TestDataView.this,
                      //      android.R.color.darker_gray));
                    tabLayout.setBackgroundColor(ContextCompat.getColor(TestDataView.this,
                            android.R.color.darker_gray));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        getWindow().setStatusBarColor(ContextCompat.getColor(TestDataView.this,
                                android.R.color.darker_gray));
                    }
                } else {

                   // toolbar.setBackgroundColor(ContextCompat.getColor(MainActivity.this,
                     //       R.color.colorPrimary));
                    tabLayout.setBackgroundColor(ContextCompat.getColor(TestDataView.this,
                            R.color.colorPrimary));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        getWindow().setStatusBarColor(ContextCompat.getColor(TestDataView.this,
                                R.color.colorPrimaryDark));
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    private void bindHandlersOnEvents() {
        eventService.addEventHandler(this::dataReceiveHandle, DataReceiveEvent.class);
       // eventService.addEventHandler(this::updateTextViewHandle, UpdateViewEvent.class);
    }

    private void initWorkoutsView() {
        //part of workout tab
       // workouts = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1);
        //listView = findViewById(R.id.workouts);
        //listView.setAdapter(workouts);
    }

    private void dataReceiveHandle(DataReceiveEvent dataReceiveEvent) {
        //runOnUiThread(messageAdapter.addMessageTask(dataReceiveEvent.getMessage()));
        //messagesView.setSelection(messageAdapter.getCount() - 1);
        List<SensorsRecord> sensorsRecords = jsonService
                .deserializeJsonArray(SensorsRecord[].class, dataReceiveEvent.getMessage());
        sensoryRecordRepo.create(sensorsRecords);
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