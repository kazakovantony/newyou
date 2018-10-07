package com.itbulls.newyou.app;

import android.app.Application;
import android.content.Context;

import com.itbulls.newyou.app.component.DaggerNewYouComponent;
import com.itbulls.newyou.app.component.NewYouComponent;
import com.itbulls.newyou.app.component.NewYouModule;

public class App extends Application {

    private NewYouComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerNewYouComponent.builder()
                .newYouModule(new NewYouModule(this))
                .build();
    }

    public static NewYouComponent getComponent(Context context) {
        return ((App) context.getApplicationContext()).component;
    }
}
