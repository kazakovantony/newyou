package com.chootdev.ormlitesample.repo;

import com.chootdev.ormlitesample.database.DatabaseHelper;
import com.chootdev.ormlitesample.database.DatabaseManager;
import com.chootdev.ormlitesample.models.Student;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Choota on 2/6/18.
 */

public class StudentRepo implements Crud {

    private DatabaseHelper helper;

    public StudentRepo() {
        this.helper = DatabaseManager.getInstance().getHelper();
    }

    @Override
    public int create(Object item) {

        int index = -1;
        Student object = (Student) item;

        try {
            index = helper.getStudentDao().create(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return index;
    }

    @Override
    public int update(Object item) {

        int index = -1;
        Student object = (Student) item;

        try {
            index = helper.getStudentDao().update(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return index;
    }

    @Override
    public int delete(Object item) {

        int index = -1;
        Student object = (Student) item;

        try {
            index = helper.getStudentDao().delete(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return index;
    }

    @Override
    public Object findById(int id) {

        Student object = null;

        try {
            object = helper.getStudentDao().queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return object;
    }

    @Override
    public List<?> findAll() {

        List<Student> items = null;

        try {
            items = helper.getStudentDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }
}
