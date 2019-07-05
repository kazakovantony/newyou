package com.chootdev.ormlitesample.activities;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.chootdev.ormlitesample.R;
import com.chootdev.ormlitesample.database.DatabaseManager;
import com.chootdev.ormlitesample.models.Student;
import com.chootdev.ormlitesample.repo.StudentRepo;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    private StudentRepo studentRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.init();
        this.saveData();

        this.retrieveData();
    }

    private void init() {
        DatabaseManager.init(MainActivity.this);
        studentRepo = new StudentRepo();
    }

    private void saveData() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Student s1 = new Student("Pasan", "10", "Colombo", "Malabe");
                Student s2 = new Student("Pasan1", "101", "Colombo1", "Malabe1");
                Student s3 = new Student("Pasan2", "102", "Colombo2", "Malabe2");
                Student s4 = new Student("Pasan3", "103", "Colombo3", "Malabe3");

                studentRepo.create(s1);
                studentRepo.create(s2);
                studentRepo.create(s3);
                studentRepo.create(s4);

            }
        }, 500);
    }

    private void retrieveData() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                List<Student> items = (List<Student>) studentRepo.findAll();
                for (Student item : items) {
                    System.out.println(item.toString());
                }
            }
        }, 5000);

    }
}
