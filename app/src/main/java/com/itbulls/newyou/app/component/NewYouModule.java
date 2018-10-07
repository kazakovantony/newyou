package com.itbulls.newyou.app.component;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.itbulls.newyou.app.App;
import com.itbulls.newyou.app.listener.ServiceConnectionListener;
import com.itbulls.newyou.app.model.SensorsRecord;
import com.itbulls.newyou.app.model.Workout;
import com.itbulls.newyou.app.model.WorkoutState;
import com.itbulls.newyou.app.service.DataService;
import com.itbulls.newyou.app.service.JsonService;
import com.itbulls.newyou.app.service.PredictorService;
import com.itbulls.newyou.app.service.WatchServiceProvider;
import com.itbulls.newyou.app.service.event.EventService;
import com.noodle.Noodle;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

import static java.util.concurrent.TimeUnit.SECONDS;

@Module
public class NewYouModule {

    final App app;

    public NewYouModule(App app) {
        this.app = app;
    }

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
}
