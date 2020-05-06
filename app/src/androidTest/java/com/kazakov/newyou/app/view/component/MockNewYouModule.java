package com.kazakov.newyou.app.view.component;

import android.app.Application;

import androidx.test.platform.app.InstrumentationRegistry;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kazakov.newyou.app.App;
import com.kazakov.newyou.app.constants.TestConstant;
import com.kazakov.newyou.app.listener.ServiceConnectionListener;
import com.kazakov.newyou.app.model.WorkoutState;
import com.kazakov.newyou.app.model.json.PredictionResult;
import com.kazakov.newyou.app.repository.NewYouRepo;
import com.kazakov.newyou.app.service.JsonService;
import com.kazakov.newyou.app.service.PredictorService;
import com.kazakov.newyou.app.service.WatchConnectionProvider;
import com.kazakov.newyou.app.service.WatchConnectionServiceStub;
import com.kazakov.newyou.app.service.WatchServiceHolder;
import com.kazakov.newyou.app.service.WorkoutService;
import com.kazakov.newyou.app.service.converter.SensorsRecordsBatchConverter;
import com.kazakov.newyou.app.service.database.DatabaseService;
import com.kazakov.newyou.app.service.event.EventService;
import com.kazakov.newyou.app.service.holders.WorkoutController;
import com.kazakov.newyou.app.utils.FileUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Module
public class MockNewYouModule {

    private static final Logger LOGGER = LoggerFactory.getLogger(MockNewYouModule.class);

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
        return new Gson();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {

        OkHttpClient okHttpClient = mock(OkHttpClient.class);
        try {
            when(okHttpClient.newCall(any()).execute()).thenReturn(null);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return okHttpClient;
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
            LOGGER.error(e.getMessage(), e);
        }
        return predictorService;
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
    WorkoutService workoutServiceProvider(){
        WorkoutService workoutService = mock(WorkoutService.class);
        doNothing().when(workoutService).startActivity();
        doNothing().when(workoutService).stopWorkout();
        return workoutService;
    }

    @Provides
    @Singleton
    DatabaseService provideDatabase(Application app){
        try {
            FileUtils.copy(InstrumentationRegistry.getInstrumentation().getTargetContext()
                    .getDatabasePath(DatabaseService.DATABASE_NAME), TestConstant.TEST_DB_SUFFIX.getValue());
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return new DatabaseService(app, TestConstant.TEST_DB_SUFFIX.getValue());
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


    @Provides
    @Singleton
    WorkoutController workoutController(WorkoutService workoutService, PredictorService predictorService,
                                        SensorsRecordsBatchConverter converter){
        return new WorkoutController(workoutService, predictorService, converter);
    }

    public void setApp(App app) {
        this.app = app;
    }
}