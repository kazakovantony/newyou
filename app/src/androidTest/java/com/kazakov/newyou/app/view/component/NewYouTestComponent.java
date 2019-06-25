package com.kazakov.newyou.app.view.component;

import com.kazakov.newyou.app.component.NewYouComponent;
import com.kazakov.newyou.app.view.component.base.impl.view.PredictionView;
import com.kazakov.newyou.app.view.component.base.impl.view.TestDataViewTest;
import com.kazakov.newyou.app.view.component.base.impl.view.WorkoutView;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = MockNewYouModule.class)
public interface NewYouTestComponent extends NewYouComponent {
    void inject(TestDataViewTest testDataViewTest);
}
