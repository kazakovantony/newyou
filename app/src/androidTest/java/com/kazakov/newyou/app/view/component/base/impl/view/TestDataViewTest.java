package com.kazakov.newyou.app.view.component.base.impl.view;

import android.app.Instrumentation;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.kazakov.newyou.app.App;
import com.kazakov.newyou.app.R;
import com.kazakov.newyou.app.model.GymActivity;
import com.kazakov.newyou.app.model.json.PredictionResult;
import com.kazakov.newyou.app.model.json.SensorsRecord;
import com.kazakov.newyou.app.model.table.SensorsRecordsBatch;
import com.kazakov.newyou.app.model.table.Workout;
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
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.inject.Inject;

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
            TestDataView.class, true, false);

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
        if (!records.isEmpty()) {
            newYouRepo.delete(records);
        }
    }

    @Test
    public void Given_WorkoutActivityData_When_Received_Then_StoreToDataBase_Do_Predict() throws IOException {
        activityRule.launchActivity(new Intent());
        String json = FileUtils.readFile(InstrumentationRegistry.getInstrumentation().getContext(),
                com.kazakov.newyou.app.test.R.raw.workoutactivity);
        ToggleButton changeMode = activityRule.getActivity().findViewById(R.id.toggleButton);
        activityRule.getActivity().runOnUiThread(changeMode::performClick);

        TabLayout tabLayout = activityRule.getActivity().findViewById(R.id.tablayout);
        eventService.triggerEvent(new DataReceiveEvent(json.getBytes()));
        Workout w = activityRule.getActivity().w;

        List<SensorsRecordsBatch> recordsBatches = newYouRepo.findMatches(SensorsRecordsBatch.class, w, "workout");
        List<SensorsRecord> batches = recordsBatches.stream()
                .map(i -> deserialize(i.getSensorsRecords())).flatMap(List::stream)
                .collect(Collectors.toList());
        assertEquals(batches, jsonService.deserializeJsonArray(SensorsRecord[].class, json));
        activityRule.getActivity().runOnUiThread(changeMode::performClick);
        InstrumentationRegistry.getInstrumentation().waitForIdleSync();
        TabLayout.Tab tab = tabLayout.getTabAt(1);
        activityRule.getActivity().runOnUiThread(tab::select);
        InstrumentationRegistry.getInstrumentation().waitForIdleSync();
        getResults();


        // prediction logic goes here
        // mock prediction service
        // click stop button
        // check predict layout structure
        // do relearning
        //spring test profile for test configuration

    }

    public List<PredictionResult> getResults() {
        TableLayout tableLayout = activityRule.getActivity().findViewById(R.id.tableLayout);
        List<PredictionResult> results = new LinkedList<>();
        IntStream.range(1, tableLayout.getChildCount() - 1)
                .forEach(idx -> results.add((getPredictionResultFromRowByIndex(idx,tableLayout))));
        return results;
    }

    private PredictionResult getPredictionResultFromRowByIndex(int i,TableLayout layout) {
        TableRow row = (TableRow) layout.getChildAt(i);
        TextSwitcher t = (TextSwitcher) row.getChildAt(1);
        TextView currentlyShownTextView = (TextView) t.getCurrentView();
        String activity = currentlyShownTextView.getText().toString();

        String duration = ((TextView)row.getChildAt(3)).getText().toString();

        TextSwitcher te = (TextSwitcher) row.getChildAt(5);
        TextView cur = (TextView) te.getCurrentView();
        String numbersOfRepeats = cur.getText().toString();

        return new PredictionResult(GymActivity.valueOf(activity), duration, Integer.parseInt(numbersOfRepeats));

    }
    private List<SensorsRecord> deserialize(String json) {
        return jsonService.deserializeJsonArray(SensorsRecord[].class, json);
    }
}
