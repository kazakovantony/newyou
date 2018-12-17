package com.kazakov.newyou.app;

import com.kazakov.newyou.app.component.NewYouComponent;
import com.kazakov.newyou.app.view.component.DaggerNewYouTestComponent;
import com.kazakov.newyou.app.view.component.MockNewYouModule;

public class MockApp extends App {

    @Override
    protected NewYouComponent createComponent() {
        MockNewYouModule module = new MockNewYouModule();
        module.setApp(this);
        return DaggerNewYouTestComponent.builder().mockNewYouModule(module).build();
    }
}
