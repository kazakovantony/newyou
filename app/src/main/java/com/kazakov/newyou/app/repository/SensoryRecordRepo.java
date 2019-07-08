package com.kazakov.newyou.app.repository;

import com.kazakov.newyou.app.model.json.SensorsRecord;
import com.kazakov.newyou.app.service.database.DatabaseService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import javax.inject.Inject;

public class SensoryRecordRepo {

    private DatabaseService databaseService;

    @Inject
    public SensoryRecordRepo(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    public int create(SensorsRecord item) {
        int success = -1;

        try {
            success = databaseService.getSensorsRecordDao().create(item);
            databaseService.getSensorsRecordDao().create(item);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }

    public int create(List<SensorsRecord> items) {
        int success = -1;

        try {
            success = databaseService.getSensorsRecordDao().callBatchTasks(() -> {
                int res = -1;
                for (SensorsRecord sensorsRecord : items) {
                    res = create(sensorsRecord);
                    if (res != 1) {
                        throw new IllegalStateException(
                                String.format("Cannot create record %s", sensorsRecord));
                    }
                }
                return res;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    public int delete(SensorsRecord item) {

        int index = -1;

        try {
            index = databaseService.getSensorsRecordDao().delete(item);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return index;
    }

    public int delete(List<SensorsRecord> items) {
        return intResultOperation(() -> {
            try {
                return databaseService.getSensorsRecordDao().delete(items);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return -1;
        });
    }

    public Optional<SensorsRecord> findById(int id) {

        try {
            return Optional.of(databaseService.getSensorsRecordDao().queryForId(id));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public List<SensorsRecord> findAll() {

        List<SensorsRecord> items = new ArrayList<>();

        try {
            items = databaseService.getSensorsRecordDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }

    private int intResultOperation(Supplier<Integer> operation){
        int index;
        index = operation.get();
        return index;
    }
}
