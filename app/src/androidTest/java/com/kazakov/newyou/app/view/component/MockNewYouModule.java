package com.kazakov.newyou.app.view.component;

import android.app.Application;

import com.google.gson.Gson;
import com.kazakov.newyou.app.App;
import com.kazakov.newyou.app.BuildConfig;
import com.kazakov.newyou.app.listener.ServiceConnectionListener;
import com.kazakov.newyou.app.model.SensorsRecord;
import com.kazakov.newyou.app.model.WorkoutState;
import com.kazakov.newyou.app.service.DataService;
import com.kazakov.newyou.app.service.JsonService;
import com.kazakov.newyou.app.service.PredictorService;
import com.kazakov.newyou.app.service.WatchConnectionProvider;
import com.kazakov.newyou.app.service.WatchConnectionService;
import com.kazakov.newyou.app.service.WatchConnectionServiceStub;
import com.kazakov.newyou.app.service.WatchServiceHolder;
import com.kazakov.newyou.app.service.event.EventService;
import com.noodle.Noodle;


import org.mockito.Mockito;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

import static org.mockito.Mockito.*;

@Module
public class MockNewYouModule {

    App app;

    @Provides
    @Singleton
    App provideNowDoThisApp() {
        return app;
    }

    @Provides
    @Singleton
    Application provideApplication(App app) {
        return app;
    }

    @Provides
    @Singleton
    EventService provideEventService() {
        return new EventService();
    }

    @Provides
    @Singleton
    ServiceConnectionListener provideServiceConnectionListener() {
        return new ServiceConnectionListener();
    }

    @Provides
    @Singleton
    WorkoutState provideWorkoutState() {
        return new WorkoutState();
    }

    @Provides
    @Singleton
    WatchServiceHolder provideWatchConnectionService() {
        return new WatchServiceHolder();
    }

    @Provides
    @Singleton
    Noodle provideNoodle() {
        return Noodle.with(app.getBaseContext())
                .addType(SensorsRecord.class).build();
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new Gson();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(){

        OkHttpClient okHttpClient = mock(OkHttpClient.class);
        try {
            when(okHttpClient.newCall(any()).execute()).thenReturn(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return okHttpClient;//Mockito.mock(OkHttpClient.class);
    }

    @Provides
    @Singleton
    PredictorService providePredictorService() {
        return new PredictorService(null, null);
    }

    @Provides
    @Singleton
    DataService provideDataService(Noodle noodle) {
        return new DataService(noodle);
    }

    @Provides
    @Singleton
    JsonService provideJson() {
        return new JsonService(new Gson());
    }

    @Provides
    @Singleton
    WatchConnectionProvider watchConnectionProvider() {
        return new WatchConnectionProvider(() -> WatchConnectionServiceStub.class);
    }

    public void setApp(App app) {
        this.app = app;
    }
}