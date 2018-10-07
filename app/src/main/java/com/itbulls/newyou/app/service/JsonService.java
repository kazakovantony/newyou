package com.itbulls.newyou.app.service;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

public class JsonService {

    final Gson gson;

    @Inject
    public JsonService(Gson gson) {
        this.gson = gson;
    }

    public <T> List<T> deserializeJsonArray(Class<T[]> clazz, String json) {

        final T[] jsonToObject = gson.fromJson(json, clazz);
        return Arrays.asList(jsonToObject);
    }
}
