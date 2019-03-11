package com.kazakov.newyou.app.component.base.impl.view;

import android.widget.ListView;

import com.kazakov.newyou.app.listener.ServiceConnectionListener;
import com.kazakov.newyou.app.model.WorkoutState;
import com.kazakov.newyou.app.service.DataService;
import com.kazakov.newyou.app.service.JsonService;
import com.kazakov.newyou.app.service.PredictorService;
import com.kazakov.newyou.app.service.WatchServiceHolder;
import com.kazakov.newyou.app.service.event.EventService;
import com.kazakov.newyou.app.view.component.base.impl.MessageAdapter;
import com.kazakov.newyou.app.view.component.base.impl.view.TestDataView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TestDataViewUnitTest {

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
    WatchServiceHolder watchConnectionServiceProvider;
    @Mock
    WorkoutState workoutState;
    @Mock
    ServiceConnectionListener serviceConnectionListener;
    @Mock
    JsonService jsonService;
    @InjectMocks
    TestDataView testDataView = new TestDataView();

    @Test
    public void shouldStart() {
        assert true;
    }
}
