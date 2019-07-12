package com.kazakov.newyou.app.repository;

import com.kazakov.newyou.app.model.table.SensorsRecordsBatch;
import com.kazakov.newyou.app.model.table.base.Entity;
import com.kazakov.newyou.app.service.database.DatabaseService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.inject.Inject;

public class NewYouRepo {

    private DatabaseService databaseService;

    @Inject
    public NewYouRepo(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    public <T extends Entity> int create(T item) {
        int success = -1;

        try {
            success = databaseService.getSpecificDao(item.getClass()).create(item);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }

    public <T extends Entity> int create(List<T> items) {
        int success = -1;

        try {
            Entity o = items.stream().findAny().get();
            success = databaseService.getSpecificDao(o.getClass()).callBatchTasks(() -> {
                int res = -1;
                for (Entity item : items) {
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

    public <T extends Entity> int delete(T item) {

        int index = -1;

        try {
            index = databaseService.getSpecificDao(item.getClass()).delete(item);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return index;
    }

    public <T extends Entity> int delete(List<T> items) {
        return intResultOperation(() -> {
            try {
                Entity e = items.stream().findAny().get();
                return databaseService.getSpecificDao(e.getClass())
                        .deleteIds(items.stream().map(Entity::getId).collect(Collectors.toList()));
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

    public <T extends Entity> int refresh(T item) {
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
