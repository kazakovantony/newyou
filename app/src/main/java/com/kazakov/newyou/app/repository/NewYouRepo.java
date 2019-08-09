package com.kazakov.newyou.app.repository;

import com.kazakov.newyou.app.model.table.base.Entity;
import com.kazakov.newyou.app.service.database.DatabaseService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.inject.Inject;

import io.vavr.control.Try;

public class NewYouRepo {

    private static final Logger LOGGER = LoggerFactory.getLogger(NewYouRepo.class);

    private DatabaseService databaseService;

    @Inject
    public NewYouRepo(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    public <T extends Entity> int create(T item) {
        return Try.of(() -> databaseService.getSpecificDao(item.getClass()).create(item))
                .onFailure((e) -> LOGGER.error(e.getMessage(), e))
                .recover(SQLException.class, -1).get();
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
            LOGGER.error(e.getMessage(), e);
        }
        return success;
    }

    public <T extends Entity> int delete(T item) {
        return Try.of(() -> databaseService.getSpecificDao(item.getClass()).delete(item))
                .onFailure((e) -> LOGGER.error(e.getMessage(), e))
                .recover(SQLException.class, -1).get();
    }

    public <T extends Entity> int delete(List<T> items) {
        Entity e = items.stream().findAny().get();
        return intResultOperation(() -> {
            Try<Integer> recover = Try.of(() -> databaseService.getSpecificDao(e.getClass())
                    .deleteIds(items.stream().map(Entity::getId).collect(Collectors.toList())))
                    .onFailure((ex) -> LOGGER.error(ex.getMessage(), ex)).recover(SQLException.class, -1);
            return recover.get();
        });
    }

    public <T> List<T> findById(int id, Class clazz) {
        List<T> items = (List<T>) Try.of(() -> databaseService.getSpecificDao(clazz).queryForId(id))
                .onFailure((e) -> LOGGER.error(e.getMessage(), e))
                .recover(SQLException.class, Collections.emptyList());
        return items;
    }

    public <T> List<T> findAll(Class clazz) {
        List<T> items = (List<T>) Try.of(() -> databaseService.getSpecificDao(clazz).queryForAll())
                .onFailure((e) -> LOGGER.error(e.getMessage(), e))
                .recover(SQLException.class, Collections.emptyList()).get();
        return items;
    }

    public <T extends Entity> int refresh(T item) {
        int success = intResultOperation(() -> {
            return Try.of(() -> databaseService.getSpecificDao(item.getClass()).refresh(item))
                    .onFailure((e) -> LOGGER.error(e.getMessage(), e))
                    .recover(SQLException.class, -1).get();

        });
        return success;
    }

    private int intResultOperation(Supplier<Integer> operation) {
        int index;
        index = operation.get();
        return index;
    }

    public <T> List<T> findMatches(Class clazz, Object o, String collumName) {
        List<T> items = Try.of(() -> (List<T>) databaseService.getSpecificDao(clazz)
                .query(databaseService.getSpecificDao(clazz).queryBuilder().where().eq(collumName, o).prepare()))
                .onFailure((e) -> LOGGER.error(e.getMessage(), e))
                .recover(SQLException.class, Collections.emptyList())
                .get();
        return items;
    }
}
