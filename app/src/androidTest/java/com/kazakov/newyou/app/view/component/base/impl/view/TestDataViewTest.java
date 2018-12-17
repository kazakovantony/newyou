package com.kazakov.newyou.app.view.component.base.impl.view;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;


@RunWith(AndroidJUnit4.class)
public class TestDataViewTest {

    @Rule
    public ActivityTestRule<TestDataView> activityRule = new ActivityTestRule<>(
            TestDataView.class,true,false);

    @Before
    public void setUp() {
        System.out.println("Before");
//        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
  //      DemoApplication app
   //             = (DemoApplication) instrumentation.getTargetContext().getApplicationContext();
     //   TestComponent component = (TestComponent) app.component();
       // component.inject(this);
    }

    @Test
    public void today() {
        boolean t = true;
        System.out.println(t);
        assert t;
        //Mockito.when(clock.getNow()).thenReturn(new DateTime(2008, 9, 23, 0, 0, 0));

        //activityRule.launchActivity(new Intent());

        //onView(withId(R.id.date))
        //  .check(matches(withText("2008-09-23")));
    }


}
