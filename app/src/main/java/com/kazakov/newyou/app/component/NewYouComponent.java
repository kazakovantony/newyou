package com.kazakov.newyou.app.component;

import android.app.Application;

import com.kazakov.newyou.app.service.WatchServiceHolder;
import com.kazakov.newyou.app.view.component.base.impl.view.TestDataView;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NewYouModule.class})
public interface NewYouComponent {
    Application application();
    void inject(TestDataView nowDoThisActivity);
    void inject(WatchServiceHolder watchServiceHolder);
}
