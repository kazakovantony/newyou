package com.kazakov.newyou.app.view.component;

import android.app.Application;

import androidx.test.platform.app.InstrumentationRegistry;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kazakov.newyou.app.App;
import com.kazakov.newyou.app.listener.ServiceConnectionListener;
import com.kazakov.newyou.app.model.json.PredictionResult;
import com.kazakov.newyou.app.model.json.SensorsRecord;
import com.kazakov.newyou.app.model.WorkoutState;
import com.kazakov.newyou.app.service.DataService;
import com.kazakov.newyou.app.service.JsonService;
import com.kazakov.newyou.app.service.PredictorService;
import com.kazakov.newyou.app.service.WatchConnectionProvider;
import com.kazakov.newyou.app.service.WatchConnectionServiceStub;
import com.kazakov.newyou.app.service.WatchServiceHolder;
import com.kazakov.newyou.app.service.WorkoutService;
import com.kazakov.newyou.app.service.event.EventService;
import com.kazakov.newyou.app.utils.FileUtils;
import com.noodle.Noodle;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

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
    OkHttpClient provideOkHttpClient() {

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
    PredictorService providePredictorService(Gson gson) {
        PredictorService predictorService = mock(PredictorService.class);
        try {
            String json = FileUtils.readFile(InstrumentationRegistry.getInstrumentation().getContext(),
                    com.kazakov.newyou.app.test.R.raw.activitypredictionresult);
            Type listType = new TypeToken<List<PredictionResult>>() {
            }.getType();
            List<PredictionResult> predictionResult = gson.fromJson(json, listType);
            when(predictorService.predict(any())).thenReturn(predictionResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return predictorService;
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

    @Provides
    @Singleton
    WorkoutService workoutServiceProvider(WatchServiceHolder watchServiceHolder,
                                          WorkoutState workoutState, EventService eventService
    ) {
        WorkoutService workoutService = mock(WorkoutService.class);
        doNothing().when(workoutService).startWorkout();
        try {
            doNothing().when(workoutService).stopWorkout();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return workoutService;
    }

    public void setApp(App app) {
        this.app = app;
    }
}