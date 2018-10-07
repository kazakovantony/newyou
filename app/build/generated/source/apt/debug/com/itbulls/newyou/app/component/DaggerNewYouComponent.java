// Generated by dagger.internal.codegen.ComponentProcessor (https://google.github.io/dagger).
package com.itbulls.newyou.app.component;

import android.app.Application;
import com.google.gson.Gson;
import com.itbulls.newyou.app.App;
import com.itbulls.newyou.app.listener.ServiceConnectionListener;
import com.itbulls.newyou.app.model.WorkoutState;
import com.itbulls.newyou.app.service.DataService;
import com.itbulls.newyou.app.service.JsonService;
import com.itbulls.newyou.app.service.PredictorService;
import com.itbulls.newyou.app.service.WatchServiceProvider;
import com.itbulls.newyou.app.service.event.EventService;
import com.itbulls.newyou.app.view.component.base.impl.view.TestDataView;
import com.itbulls.newyou.app.view.component.base.impl.view.TestDataView_MembersInjector;
import com.noodle.Noodle;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import okhttp3.OkHttpClient;

public final class DaggerNewYouComponent implements NewYouComponent {
  private Provider<App> provideNowDoThisAppProvider;

  private Provider<Application> provideApplicationProvider;

  private Provider<EventService> provideEventServiceProvider;

  private Provider<WorkoutState> provideWorkoutStateProvider;

  private Provider<Noodle> provideNoodleProvider;

  private Provider<DataService> provideDataServiceProvider;

  private Provider<Gson> provideGsonProvider;

  private Provider<OkHttpClient> provideOkHttpClientProvider;

  private Provider<PredictorService> providePredictorServiceProvider;

  private Provider<WatchServiceProvider> provideWatchConnectionServiceProvider;

  private Provider<ServiceConnectionListener> provideServiceConnectionListenerProvider;

  private Provider<JsonService> provideJsonProvider;

  private DaggerNewYouComponent(Builder builder) {
    initialize(builder);
  }

  public static Builder builder() {
    return new Builder();
  }

  @SuppressWarnings("unchecked")
  private void initialize(final Builder builder) {
    this.provideNowDoThisAppProvider =
        DoubleCheck.provider(NewYouModule_ProvideNowDoThisAppFactory.create(builder.newYouModule));
    this.provideApplicationProvider =
        DoubleCheck.provider(
            NewYouModule_ProvideApplicationFactory.create(
                builder.newYouModule, provideNowDoThisAppProvider));
    this.provideEventServiceProvider =
        DoubleCheck.provider(NewYouModule_ProvideEventServiceFactory.create(builder.newYouModule));
    this.provideWorkoutStateProvider =
        DoubleCheck.provider(NewYouModule_ProvideWorkoutStateFactory.create(builder.newYouModule));
    this.provideNoodleProvider =
        DoubleCheck.provider(
            NewYouModule_ProvideNoodleFactory.create(
                builder.newYouModule, provideNowDoThisAppProvider));
    this.provideDataServiceProvider =
        DoubleCheck.provider(
            NewYouModule_ProvideDataServiceFactory.create(
                builder.newYouModule, provideWorkoutStateProvider, provideNoodleProvider));
    this.provideGsonProvider =
        DoubleCheck.provider(NewYouModule_ProvideGsonFactory.create(builder.newYouModule));
    this.provideOkHttpClientProvider =
        DoubleCheck.provider(
            NewYouModule_ProvideOkHttpClientFactory.create(
                builder.newYouModule, provideApplicationProvider));
    this.providePredictorServiceProvider =
        DoubleCheck.provider(
            NewYouModule_ProvidePredictorServiceFactory.create(
                builder.newYouModule, provideGsonProvider, provideOkHttpClientProvider));
    this.provideWatchConnectionServiceProvider =
        DoubleCheck.provider(
            NewYouModule_ProvideWatchConnectionServiceFactory.create(builder.newYouModule));
    this.provideServiceConnectionListenerProvider =
        DoubleCheck.provider(
            NewYouModule_ProvideServiceConnectionListenerFactory.create(builder.newYouModule));
    this.provideJsonProvider =
        DoubleCheck.provider(
            NewYouModule_ProvideJsonFactory.create(
                builder.newYouModule, provideWorkoutStateProvider, provideNoodleProvider));
  }

  @Override
  public Application application() {
    return provideApplicationProvider.get();
  }

  @Override
  public void inject(TestDataView nowDoThisActivity) {
    injectTestDataView(nowDoThisActivity);
  }

  @Override
  public void inject(WatchServiceProvider watchServiceProvider) {}

  private TestDataView injectTestDataView(TestDataView instance) {
    TestDataView_MembersInjector.injectEventService(instance, provideEventServiceProvider.get());
    TestDataView_MembersInjector.injectDataService(instance, provideDataServiceProvider.get());
    TestDataView_MembersInjector.injectPredictorService(
        instance, providePredictorServiceProvider.get());
    TestDataView_MembersInjector.injectWatchConnectionServiceProvider(
        instance, provideWatchConnectionServiceProvider.get());
    TestDataView_MembersInjector.injectWorkoutState(instance, provideWorkoutStateProvider.get());
    TestDataView_MembersInjector.injectServiceConnectionListener(
        instance, provideServiceConnectionListenerProvider.get());
    TestDataView_MembersInjector.injectJsonService(instance, provideJsonProvider.get());
    return instance;
  }

  public static final class Builder {
    private NewYouModule newYouModule;

    private Builder() {}

    public NewYouComponent build() {
      if (newYouModule == null) {
        throw new IllegalStateException(NewYouModule.class.getCanonicalName() + " must be set");
      }
      return new DaggerNewYouComponent(this);
    }

    public Builder newYouModule(NewYouModule newYouModule) {
      this.newYouModule = Preconditions.checkNotNull(newYouModule);
      return this;
    }
  }
}
