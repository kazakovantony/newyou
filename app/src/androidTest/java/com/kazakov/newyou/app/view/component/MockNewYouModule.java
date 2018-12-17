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
    WatchServiceProvider provideWatchConnectionService() {
        return new WatchServiceProvider();
    }

    @Provides
    @Singleton
    Noodle provideNoodle() {
        return new Noodle(null);
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new Gson();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        return Mockito.mock(OkHttpClient.class);
    }

    @Provides
    @Singleton
    PredictorService providePredictorService() {
        return new PredictorService(null, null);
    }

    @Provides
    @Singleton
    DataService provideDataService() {
        return new DataService(null);
    }

    @Provides
    @Singleton
    JsonService provideJson() {
        return new JsonService(null);
    }

    public void setApp(App app) {
        this.app = app;
    }
}