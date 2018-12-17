package com.kazakov.newyou.app.view.component;

import android.app.Application;

import com.google.gson.Gson;
import com.kazakov.newyou.app.App;
import com.kazakov.newyou.app.listener.ServiceConnectionListener;
import com.kazakov.newyou.app.model.WorkoutState;
import com.kazakov.newyou.app.service.DataService;
import com.kazakov.newyou.app.service.JsonService;
import com.kazakov.newyou.app.service.PredictorService;
import com.kazakov.newyou.app.service.WatchServiceProvider;
import com.kazakov.newyou.app.service.event.EventService;
import com.noodle.Noodle;

import org.mockito.Mockito;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

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
        return Mockito.mock(EventService.class);
    }

    @Provides
    @Singleton
    ServiceConnectionListener provideServiceConnectionListener() {
        return Mockito.mock(ServiceConnectionListener.class);
    }

    @Provides
    @Singleton
    WorkoutState provideWorkoutState() {
        return Mockito.mock(WorkoutState.class);
    }

    @Provides
    @Singleton
    WatchServiceProvider provideWatchConnectionService() {
        return Mockito.mock(WatchServiceProvider.class);
    }

    @Provides
    @Singleton
    Noodle provideNoodle() {
        return Mockito.mock(Noodle.class);
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return Mockito.mock(Gson.class);
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        return Mockito.mock(OkHttpClient.class);
    }

    @Provides
    @Singleton
    PredictorService providePredictorService() {
        return Mockito.mock(PredictorService.class);
    }

    @Provides
    @Singleton
    DataService provideDataService() {
        return Mockito.mock(DataService.class);
    }

    @Provides
    @Singleton
    JsonService provideJson() {
        return Mockito.mock(JsonService.class);
    }

    public void setApp(App app) {
        this.app = app;
    }
}