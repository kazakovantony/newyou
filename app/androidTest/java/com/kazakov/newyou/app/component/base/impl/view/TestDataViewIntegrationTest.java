package com.kazakov.newyou.app.component.base.impl.view;


import com.kazakov.newyou.app.view.component.base.impl.view.TestDataView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import javax.inject.Inject;

//mock on http client to respond with activity prediction result
//call watch listener to send workout activity data
//run activity prediction scenario
//regression testing will real prediction will be on server after build process
//use di for integration tests

@RunWith(MockitoJUnitRunner.class)
public class TestDataViewIntegrationTest {

    private static final String WORKOUT_ACTIVITY_DATA_FILE = "workout-activity.json";

    private TestDataView testDataView;

    @Inject
    public TestDataViewIntegrationTest(TestDataView testDataView) {
        this.testDataView = testDataView;
    }

    @Test
    public void givenActivityData_whenReceived_thenStore(){

    }

    @Test
    public void givenActivityData_whenPredict_thenPredict(){

    }
}
