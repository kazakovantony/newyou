package com.kazakov.newyou.app.component;

import com.kazakov.newyou.app.view.component.base.impl.view.TestDataView;

import org.junit.Rule;
import org.junit.runner.RunWith;

import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class NewYouTestModule extends NewYouModule {

    @Rule
    private final ActivityTestRule<TestDataView> testDataView;

    public NewYouTestModule() {
        testDataView = new ActivityTestRule<>(TestDataView.class);
    }
}
