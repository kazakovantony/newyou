package com.kazakov.newyou.app.component;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NewYouTestModule.class})
public interface NewYouTestComponent {
}
