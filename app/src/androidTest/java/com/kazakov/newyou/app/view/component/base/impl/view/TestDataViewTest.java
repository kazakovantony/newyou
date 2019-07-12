package com.kazakov.newyou.app.view.component.base.impl.view;

import android.app.Instrumentation;
import android.content.Intent;
import android.widget.ToggleButton;

import com.kazakov.newyou.app.App;
import com.kazakov.newyou.app.R;
import com.kazakov.newyou.app.model.json.SensorsRecord;
import com.kazakov.newyou.app.model.table.SensorsRecordsBatch;
import com.kazakov.newyou.app.repository.NewYouRepo;
import com.kazakov.newyou.app.service.JsonService;
import com.kazakov.newyou.app.service.PredictorService;
import com.kazakov.newyou.app.service.event.EventService;
import com.kazakov.newyou.app.service.event.base.impl.DataReceiveEvent;
import com.kazakov.newyou.app.utils.FileUtils;
import com.kazakov.newyou.app.view.component.NewYouTestComponent;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import static org.junit.Assert.assertEquals;


@RunWith(AndroidJUnit4.class)
public class TestDataViewTest {

    @Inject
    EventService eventService;
    @Inject
    NewYouRepo newYouRepo;
    @Inject
    JsonService jsonService;
    @Inject
    PredictorService predictorService;

    @Rule
    public ActivityTestRule<TestDataView> activityRule = new ActivityTestRule<>(
            TestDataView.class,true,false);

    @Before
    public void setUp() {
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        NewYouTestComponent component = (NewYouTestComponent) App
               .getComponent(instrumentation.getTargetContext().getApplicationContext());
        component.inject(this);
    }

    @After
    public void cleanUp() {
        List<SensorsRecordsBatch> records = newYouRepo.findAll(SensorsRecordsBatch.class);
        newYouRepo.delete(records);
    }

    @Test
    public void Given_WorkoutActivityData_When_Received_Then_StoreToDataBase_Do_Predict() throws IOException {
        activityRule.launchActivity(new Intent());
        String json = FileUtils.readFile(InstrumentationRegistry.getInstrumentation().getContext(),
                com.kazakov.newyou.app.test.R.raw.workoutactivity);
        ToggleButton changeMode = activityRule.getActivity().findViewById(R.id.toggleButton);
        activityRule.getActivity().runOnUiThread(changeMode::performClick); // start workout
        eventService.triggerEvent(new DataReceiveEvent(json.getBytes())); // emlulate data receiving
        List<SensorsRecordsBatch> recordsBatches = newYouRepo.findAll(SensorsRecordsBatch.class);
        List<SensorsRecord> batches = recordsBatches.stream().map(i -> deserialize(i.getSensorsRecords())).flatMap(List::stream)
                .collect(Collectors.toList());
        assertEquals(batches, jsonService.deserializeJsonArray(SensorsRecord[].class, json)); // it is redundant, check exists during stop workout
        activityRule.getActivity().runOnUiThread(changeMode::performClick); // stop workout

        //migrate to sql lite json data type

        // prediction logic goes here
        // mock prediction service
        // click stop button
        // check predict layout structure
        // do relearning
        //spring test profile for test configuration

    }

    private List<SensorsRecord> deserialize(String json) {
        return jsonService.deserializeJsonArray(SensorsRecord[].class, json);
    }
}
