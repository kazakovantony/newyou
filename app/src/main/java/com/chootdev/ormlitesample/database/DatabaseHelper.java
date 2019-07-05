package com.chootdev.ormlitesample.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.chootdev.ormlitesample.models.Student;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by Choota.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "Student.db";
    private static final int DATABASE_VERSION = 1;

    // database access objects
    private Dao<Student, Integer> studentDao;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Student.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Student.class, true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Dao<Student, Integer> getStudentDao() {
        if (studentDao == null) {
            try {
                studentDao = getDao(Student.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return studentDao;
    }
}
