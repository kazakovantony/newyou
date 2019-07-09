package com.kazakov.newyou.app.service.converter;

import com.kazakov.newyou.app.model.json.SensorsRecord;
import com.kazakov.newyou.app.model.table.SensorsRecordsBatch;
import com.kazakov.newyou.app.service.JsonService;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;


public class SensorsRecordsBatchConverter {

    @Inject
    JsonService jsonService;

    public SensorsRecordsBatch convert(String sensorsRecords) {
        SensorsRecordsBatch sensorsRecordsBatch = new SensorsRecordsBatch();
        sensorsRecordsBatch.setSensorsRecords(sensorsRecords);
        return sensorsRecordsBatch;
    }

    public List<SensorsRecord> convert(List<SensorsRecordsBatch> sensorsRecords) {
        return sensorsRecords.stream().map(item -> jsonService
                .deserializeJsonArray(SensorsRecord[].class, item.getSensorsRecords()))
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }
}
