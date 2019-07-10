package com.kazakov.newyou.app.service.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.kazakov.newyou.app.model.table.ActualExercise;
import com.kazakov.newyou.app.model.table.PredictedExercise;
import com.kazakov.newyou.app.model.table.SensorsRecordsBatch;
import com.kazakov.newyou.app.model.table.Workout;

import java.sql.SQLException;
import java.util.Map;

public class DatabaseService extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "NewYou.db";
    private static final int DATABASE_VERSION = 1;

    private Map<Class, Dao<Object, Integer>> cachedDaos;

    public DatabaseService(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, SensorsRecordsBatch.class);
            TableUtils.createTable(connectionSource, PredictedExercise.class);
            TableUtils.createTable(connectionSource, ActualExercise.class);
            TableUtils.createTable(connectionSource, Workout.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, SensorsRecordsBatch.class, true);
            TableUtils.dropTable(connectionSource, PredictedExercise.class, true);
            TableUtils.dropTable(connectionSource, ActualExercise.class, true);
            TableUtils.dropTable(connectionSource, PredictedExercise.class, true);
            TableUtils.createTable(connectionSource, Workout.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public <T> Dao<T, Integer> getSpecificDao(Class clazz) {
        return (Dao<T, Integer>) (cachedDaos.computeIfAbsent(clazz, this::takeDao));
    }

    private Dao<Object, Integer> takeDao(Class clazz) {
        try {
            return getDao(clazz);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException("Dao level error");
    }
}