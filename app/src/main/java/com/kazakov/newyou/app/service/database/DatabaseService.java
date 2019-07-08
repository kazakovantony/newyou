package com.kazakov.newyou.app.service.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.kazakov.newyou.app.model.json.SensorsRecord;

import java.sql.SQLException;

public class DatabaseService extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "NewYou.db";
    private static final int DATABASE_VERSION = 1;

    private Dao<SensorsRecord, Integer> sensorsRecordDao;

    public DatabaseService(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, SensorsRecord.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, SensorsRecord.class, true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Dao<SensorsRecord, Integer> getSensorsRecordDao() {
        if (sensorsRecordDao == null) {
            try {
                sensorsRecordDao = getDao(SensorsRecord.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return sensorsRecordDao;
    }
}
