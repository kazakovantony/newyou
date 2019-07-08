package com.kazakov.newyou.app.service;

import android.os.StrictMode;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kazakov.newyou.app.model.json.PredictionResult;
import com.kazakov.newyou.app.model.json.SensorsRecord;

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

    static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    static final String PREDICT_URL = "http://109.86.165.173:5000/prediction/movings";

    OkHttpClient httpClient;
    Gson gson;

    @Inject
    public PredictorService(OkHttpClient httpClient, Gson gson) {
        this.httpClient = httpClient;
        this.gson = gson;
    }

    public List<PredictionResult> predict(List<SensorsRecord> data) throws IOException {

        enableHttpCallFromUiThread();

        String strJson = gson.toJson(data);

        Request request = createRequest(strJson);
        Response response = httpClient.newCall(request).execute();

        String res = response.body().string();
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
}
