package com.itbulls.newyou.app.component;

import android.app.Application;

import com.itbulls.newyou.app.service.WatchServiceProvider;
import com.itbulls.newyou.app.view.component.base.impl.view.TestDataView;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NewYouModule.class})
public interface NewYouComponent {
    Application application();
    void inject(TestDataView nowDoThisActivity);
    void inject(WatchServiceProvider watchServiceProvider);
}
