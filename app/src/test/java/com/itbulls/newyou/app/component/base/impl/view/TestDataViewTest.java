package com.itbulls.newyou.app.component.base.impl.view;

import android.widget.ListView;

import com.google.gson.Gson;
import com.itbulls.newyou.app.listener.ServiceConnectionListener;
import com.itbulls.newyou.app.model.WorkoutState;
import com.itbulls.newyou.app.service.DataService;
import com.itbulls.newyou.app.service.JsonService;
import com.itbulls.newyou.app.service.PredictorService;
import com.itbulls.newyou.app.service.WatchServiceProvider;
import com.itbulls.newyou.app.service.event.EventService;
import com.itbulls.newyou.app.view.component.base.impl.MessageAdapter;
import com.itbulls.newyou.app.view.component.base.impl.view.TestDataView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TestDataViewTest {

    @Mock
    EventService eventService;
    @Mock
    ListView messagesView;
    @Mock
    MessageAdapter messageAdapter;
    @Mock
    DataService dataService;
    @Mock
    PredictorService predictorService;
    @Mock
    WatchServiceProvider watchConnectionServiceProvider;
    @Mock
    WorkoutState workoutState;
    @Mock
    ServiceConnectionListener serviceConnectionListener;
    @Mock
    Gson gson;
    @Mock
    JsonService jsonService;
    @InjectMocks
    TestDataView testDataView = new TestDataView();

    @Test
    public void shouldStart() {
    }
}
