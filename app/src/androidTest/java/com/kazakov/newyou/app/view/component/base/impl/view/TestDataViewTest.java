package com.kazakov.newyou.app.view.component.base.impl.view;

import android.app.Instrumentation;
import android.content.Intent;
import android.widget.Button;

import com.kazakov.newyou.app.App;
import com.kazakov.newyou.app.R;
import com.kazakov.newyou.app.model.PredictionResult;
import com.kazakov.newyou.app.model.SensorsRecord;
import com.kazakov.newyou.app.service.DataService;
import com.kazakov.newyou.app.service.JsonService;
import com.kazakov.newyou.app.service.PredictorService;
import com.kazakov.newyou.app.service.event.EventService;
import com.kazakov.newyou.app.service.event.base.impl.DataReceiveEvent;
import com.kazakov.newyou.app.view.component.NewYouTestComponent;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

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
    DataService dataService;
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
        dataService.deleteCollection(SensorsRecord.class);
    }

    @Test
    public void Given_WorkoutActivityData_When_Received_Then_StoreToDataBase_Do_Predict() throws IOException {
        activityRule.launchActivity(new Intent());
        String json = readFile();
        eventService.triggerEvent(new DataReceiveEvent(json.getBytes()));
        List<SensorsRecord> sensorsRecordList = dataService.extractDataByType(SensorsRecord.class);
        assertEquals(sensorsRecordList.size(), jsonService.deserializeJsonArray(SensorsRecord[].class, json).size());

        Button changeMode = activityRule.getActivity().findViewById(R.id.toggleButton);
        changeMode.callOnClick();

        // prediction logic goes here
        // mock prediction service
        // click stop button
        // check predict layout structure
        // do relearning

    }

    private String readFile() throws IOException {
        InputStream is =
                InstrumentationRegistry.getInstrumentation().getContext().getResources()
                        .openRawResource(com.kazakov.newyou.app.test.R.raw.workoutactivity);
        String result = IOUtils.toString(is);
        IOUtils.closeQuietly(is);
        return result;
    }
}
