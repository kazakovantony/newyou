package com.kazakov.newyou.app;

import android.app.Application;
import android.content.Context;

import com.kazakov.newyou.app.component.DaggerNewYouComponent;
import com.kazakov.newyou.app.component.NewYouComponent;
import com.kazakov.newyou.app.component.NewYouModule;

public class App extends Application {

    private NewYouComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        NewYouModule module = new NewYouModule();
        module.setApp(this);
        component = DaggerNewYouComponent.builder()
                .newYouModule(module)
                .build();
    }

    public static NewYouComponent getComponent(Context context) {
        return ((App) context.getApplicationContext()).component;
    }
}
