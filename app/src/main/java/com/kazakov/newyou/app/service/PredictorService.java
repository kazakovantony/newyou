package com.kazakov.newyou.app.service;

import android.os.StrictMode;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kazakov.newyou.app.model.json.PredictionResult;
import com.kazakov.newyou.app.model.json.SensorsRecord;
import com.kazakov.newyou.app.model.table.ActualExercise;
import com.kazakov.newyou.app.model.table.PredictedExercise;
import com.kazakov.newyou.app.model.table.SensorsRecordsBatch;
import com.kazakov.newyou.app.model.table.SensorsRecordsBatchPredictedExercise;
import com.kazakov.newyou.app.model.table.Workout;
import com.kazakov.newyou.app.repository.NewYouRepo;
import com.kazakov.newyou.app.service.converter.SensorsRecordsBatchConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PredictorService {

    NewYouRepo newYouRepo;
    SensorsRecordsBatchConverter converter;

    private static final Logger LOGGER = LoggerFactory.getLogger(PredictorService.class);

    static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    static final String PREDICT_URL = "http://109.86.165.173:5000/prediction/movings";

    OkHttpClient httpClient;
    Gson gson;

    @Inject
    public PredictorService(OkHttpClient httpClient, Gson gson, NewYouRepo newYouRepo, SensorsRecordsBatchConverter converter) {
        this.httpClient = httpClient;
        this.gson = gson;
        this.newYouRepo = newYouRepo;
        this.converter = converter;
    }

    public List<PredictionResult> predict(List<SensorsRecord> data) {
        enableHttpCallFromUiThread();
        String strJson = gson.toJson(data);
        Request request = createRequest(strJson);
        Response response;
        String res = null;
        try {
            response = httpClient.newCall(request).execute();
            LOGGER.debug("Prediction response status: {}", response.code());
            res = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return desirializePredictionResults(res);
    }

    private List<PredictionResult> desirializePredictionResults(String res) {
        Type listType = new TypeToken<List<PredictionResult>>() {
        }.getType();
        return gson.fromJson(res, listType);
    }

    private Request createRequest(String strJson) {
        RequestBody body = RequestBody.create(JSON, strJson);
        return new Request.Builder()
                .url(PREDICT_URL)
                .post(body)
                .build();
    }

    private void enableHttpCallFromUiThread() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public List<PredictedExercise> getPredictedExercise(Workout workout, List<PredictionResult> predictedGymActivity) {
        List<PredictedExercise> exercises = converter.createPredictedExercises(workout, predictedGymActivity);
        newYouRepo.create(exercises);
        return exercises;
    }

    public List<SensorsRecordsBatchPredictedExercise> getPredictedExercises(List<PredictedExercise> exercises, List<SensorsRecordsBatch> forPredict) {
        List<SensorsRecordsBatchPredictedExercise> predictedExercises = converter.createSensorsRecordsBatchPredictedExercises(exercises, forPredict);
        newYouRepo.create(predictedExercises);
        return predictedExercises;
    }

    public List<ActualExercise> getActualExercise(Workout workout, List<PredictionResult> predictedGymActivity) {
        List<ActualExercise> exercises = converter.createActualExercises(workout, predictedGymActivity);
        newYouRepo.create(exercises);
        return exercises;
    }
}
