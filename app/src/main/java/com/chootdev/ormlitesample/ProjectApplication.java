package com.chootdev.ormlitesample;

import android.app.Application;

import com.chootdev.ormlitesample.database.DatabaseManager;

/**
 * Created by Choota.
 */

public class ProjectApplication extends Application {

    // this is project application class
    @Override
    public void onCreate() {
        super.onCreate();

        DatabaseManager.init(this);
    }
}
