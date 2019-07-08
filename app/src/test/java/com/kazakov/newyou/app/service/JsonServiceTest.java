package com.kazakov.newyou.app.service;

import com.google.gson.Gson;
import com.kazakov.newyou.app.model.json.SensorsRecord;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class JsonServiceTest {

    JsonService jsonService = new JsonService(new Gson());

    @Test
    public void deserializeJsonArray() {

        SensorsRecord sensorsRecord = new SensorsRecord();
        sensorsRecord.time = 9000909;
        sensorsRecord.ax = 90;
        sensorsRecord.az = 100;
        sensorsRecord.ay = 90001912;
        sensorsRecord.magn_z = 901;

        List<SensorsRecord> test = new ArrayList<>();
        test.add(sensorsRecord);
        String json = jsonService.gson.toJson(test);

        List<SensorsRecord> result = jsonService.deserializeJsonArray(SensorsRecord[].class, json);
        assertEquals(test, result);
    }
}