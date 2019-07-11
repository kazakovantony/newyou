package com.kazakov.newyou.app.component;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kazakov.newyou.app.App;
import com.kazakov.newyou.app.listener.ServiceConnectionListener;
import com.kazakov.newyou.app.model.WorkoutState;
import com.kazakov.newyou.app.model.table.SensorsRecordsBatch;
import com.kazakov.newyou.app.repository.NewYouRepo;
import com.kazakov.newyou.app.service.JsonService;
import com.kazakov.newyou.app.service.PredictorService;
import com.kazakov.newyou.app.service.WatchConnectionProvider;
import com.kazakov.newyou.app.service.WatchConnectionService;
import com.kazakov.newyou.app.service.WatchServiceHolder;
import com.kazakov.newyou.app.service.converter.SensorsRecordsBatchConverter;
import com.kazakov.newyou.app.service.database.DatabaseService;
import com.kazakov.newyou.app.service.event.EventService;

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
    WatchServiceHolder provideWatchConnectionService() {
        return new WatchServiceHolder();
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
    JsonService provideJson() {
        return new JsonService(new Gson());
    }

    @Provides
    @Singleton
    WatchConnectionProvider watchConnectionProvider() {
        return new WatchConnectionProvider(() -> WatchConnectionService.class);
    }

    @Provides
    @Singleton
    DatabaseService provideDatabase(Application app) {
        return new DatabaseService(app);
    }

    @Provides
    @Singleton
    NewYouRepo provideRepo(DatabaseService databaseService) {
        return new NewYouRepo(databaseService);
    }

    @Provides
    @Singleton
    SensorsRecordsBatchConverter provideSensorsRecordsBatchConverter(JsonService jsonService) {
        return new SensorsRecordsBatchConverter(jsonService);
    }

    public void setApp(App app) {
        this.app = app;
    }
}
