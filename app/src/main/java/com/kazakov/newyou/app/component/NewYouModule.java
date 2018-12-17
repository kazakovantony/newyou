package com.kazakov.newyou.app.component;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kazakov.newyou.app.App;
import com.kazakov.newyou.app.listener.ServiceConnectionListener;
import com.kazakov.newyou.app.model.SensorsRecord;
import com.kazakov.newyou.app.model.Workout;
import com.kazakov.newyou.app.model.WorkoutState;
import com.kazakov.newyou.app.service.DataService;
import com.kazakov.newyou.app.service.JsonService;
import com.kazakov.newyou.app.service.PredictorService;
import com.kazakov.newyou.app.service.WatchServiceProvider;
import com.kazakov.newyou.app.service.event.EventService;
import com.noodle.Noodle;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

import static java.util.concurrent.TimeUnit.SECONDS;

@Module
public class NewYouModule {

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
    Noodle provideNoodle(App app) {
        return Noodle.with(app.getBaseContext())
                .addType(SensorsRecord.class)
                .addType(Workout.class).build();
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation().create();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(Application app) {
        OkHttpClient client = new OkHttpClient();

        return client.newBuilder().connectTimeout(60, SECONDS)
                .readTimeout(60, SECONDS).writeTimeout(60, SECONDS).build();
    }

    @Provides
    @Singleton
    PredictorService providePredictorService(Gson gson, OkHttpClient okHttpClient) {
        return new PredictorService(okHttpClient, gson);
    }

    @Provides
    @Singleton
    DataService provideDataService(WorkoutState workoutState, Noodle noodle) {
        return new DataService(noodle);
    }

    @Provides
    @Singleton
    JsonService provideJson(WorkoutState workoutState, Noodle noodle) {
        return new JsonService(new Gson());
    }

    public void setApp(App app) {
        this.app = app;
    }
}