package com.kazakov.newyou.app.repository;

import com.kazakov.newyou.app.model.json.SensorsRecord;
import com.kazakov.newyou.app.service.database.DatabaseService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import javax.inject.Inject;

public class NewYouRepo {

    private DatabaseService databaseService;

    @Inject
    public NewYouRepo(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    public int create(Object item) {
        int success = -1;

        try {
            success = databaseService.getSpecificDao(item.getClass()).create(item);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }

    public int create(List<Object> items) {
        int success = -1;

        try {
            Object o = items.stream().findAny().get();
            success = databaseService.getSpecificDao(o.getClass()).callBatchTasks(() -> {
                int res = -1;
                for (Object item : items) {
                    res = create(item);
                    if (res != 1) {
                        throw new IllegalStateException(
                                String.format("Cannot create record %s", item));
                    }
                }
                return res;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    public int delete(Object item) {

        int index = -1;

        try {
            index = databaseService.getSpecificDao(item.getClass()).delete(item);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return index;
    }

    public int delete(List<SensorsRecord> items) {
        return intResultOperation(() -> {
            try {
                Object o = items.stream().findAny().get();
                return databaseService.getSpecificDao(o.getClass()).delete(items);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return -1;
        });
    }

    public <T> Optional<T> findById(int id, Class clazz) {

        try {
            return Optional.of((T) databaseService.getSpecificDao(clazz).queryForId(id));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public <T> List<T> findAll(Class clazz) {

        List<T> items = new ArrayList<>();

        try {
            items = (List<T>) databaseService.getSpecificDao(clazz).queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }

    public int refresh(Object item) {
        int success = -1;

        try {
            success = databaseService.getSpecificDao(item.getClass()).refresh(item);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }

    private int intResultOperation(Supplier<Integer> operation){
        int index;
        index = operation.get();
        return index;
    }
}
