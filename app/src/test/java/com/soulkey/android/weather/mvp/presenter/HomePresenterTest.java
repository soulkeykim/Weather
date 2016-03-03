package com.soulkey.android.weather.mvp.presenter;

import com.google.android.gms.location.places.Place;
import com.soulkey.android.weather.WTApp;
import com.soulkey.android.weather.WTTestRunner;
import com.soulkey.android.weather.data.DataProvider;
import com.soulkey.android.weather.model.HistoryModel;
import com.soulkey.android.weather.mvp.view.HistoryMvpView;
import com.soulkey.android.weather.mvp.view.HomeMvpView;
import com.soulkey.weather.model.Weather;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RuntimeEnvironment;

import java.util.concurrent.TimeUnit;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;
import rx.TestSchedulerRule;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(WTTestRunner.class)
public class HomePresenterTest {

    private static final String LAST_CITY = "Sydney";
    private static final double LAT = 10.00;
    private static final double LON = 20.00;

    @Rule public TestSchedulerRule mTestSchedulerRule = new TestSchedulerRule();

    @Test(expected = IllegalArgumentException.class)
    public void testHomePresenterThrowsExceptionWithNullView() {
        new HomePresenter(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testHomePresenterThrowsExceptionWithNullComponent() {
        new HomePresenter(mock(HomeMvpView.class), null);
    }

    @Test
    public void testHomePresenterCreatedWithValidViewAndComponent() {
        MockHomeMvpView mockMvpView = mock(MockHomeMvpView.class);
        HomePresenter presenter = new HomePresenter(mockMvpView, WTApp.from(RuntimeEnvironment.application).getAppServicesComponent());

        assertThat(presenter).isNotNull();
        assertThat(presenter.getView()).isEqualTo(mockMvpView);
    }

    @Test
    public void testFetchWeatherWithString() {
        MockHomeMvpView mockMvpView = mock(MockHomeMvpView.class);
        DataProvider mockDataProvider = mock(DataProvider.class);
        when(mockDataProvider.getLastCity()).thenReturn(LAST_CITY);
        Weather mockWeather = mock(Weather.class);
        when(mockDataProvider.fetchWeather(LAST_CITY)).thenReturn(Observable.just(mockWeather));
        HistoryModel mockHistoryModel = mock(HistoryModel.class);
        when(mockDataProvider.addHistory(mockWeather)).thenReturn(Observable.just(mockHistoryModel));
        HomePresenter presenter = new HomePresenter(mockMvpView, WTApp.from(RuntimeEnvironment.application).getAppServicesComponent());
        presenter.mDataProvider = mockDataProvider;

        presenter.fetchWeather();

        verify(mockDataProvider).setLastCity(anyString());

        mTestSchedulerRule.getScheduler().advanceTimeBy(1, TimeUnit.DAYS);

        verify(mockMvpView).showWeatherInfo(mockWeather);
        verify(mockDataProvider).addHistory(mockWeather);
        verify(mockMvpView).showHistoryAdded();
    }

    @Ignore("Not yet support jni - java.lang.UnsatisfiedLinkError: no realm-jni in java.library.path")
    @Test
    public void testFetchWeatherWithLatLong() {
        MockHomeMvpView mockMvpView = mock(MockHomeMvpView.class);
        DataProvider mockDataProvider = mock(DataProvider.class);
        when(mockDataProvider.getLastCity()).thenReturn(LAST_CITY);
        Weather mockWeather = mock(Weather.class);
        when(mockWeather.getCityName()).thenReturn(LAST_CITY);
        when(mockDataProvider.fetchWeather(LAT, LON)).thenReturn(Observable.just(mockWeather));
        HistoryModel mockHistoryModel = mock(HistoryModel.class);
        when(mockDataProvider.addHistory(mockWeather)).thenReturn(Observable.just(mockHistoryModel));
        HomePresenter presenter = new HomePresenter(mockMvpView, WTApp.from(RuntimeEnvironment.application).getAppServicesComponent());
        presenter.mDataProvider = mockDataProvider;

        presenter.fetchWeather(LAT, LON);

        mTestSchedulerRule.getScheduler().advanceTimeBy(1, TimeUnit.DAYS);

        verify(mockDataProvider).setLastCity(LAST_CITY);
        verify(mockMvpView).showWeatherInfo(mockWeather);
        verify(mockDataProvider).addHistory(mockWeather);
        verify(mockMvpView).showHistoryAdded();
    }

    @Test
    public void testFetchWeatherWithPlace() {
        MockHomeMvpView mockMvpView = mock(MockHomeMvpView.class);
        DataProvider mockDataProvider = mock(DataProvider.class);
        when(mockDataProvider.getLastCity()).thenReturn(LAST_CITY);
        Weather mockWeather = mock(Weather.class);
        when(mockDataProvider.fetchWeather(LAST_CITY)).thenReturn(Observable.just(mockWeather));
        HistoryModel mockHistoryModel = mock(HistoryModel.class);
        when(mockDataProvider.addHistory(mockWeather)).thenReturn(Observable.just(mockHistoryModel));
        HomePresenter presenter = new HomePresenter(mockMvpView, WTApp.from(RuntimeEnvironment.application).getAppServicesComponent());
        presenter.mDataProvider = mockDataProvider;

        Place mockPlace = mock(Place.class);
        when(mockPlace.getAddress()).thenReturn(LAST_CITY);
        presenter.fetchWeather(mockPlace);

        verify(mockMvpView).showLoading();

        mTestSchedulerRule.getScheduler().advanceTimeBy(1, TimeUnit.DAYS);

        verify(mockDataProvider).setLastCity(anyString());
        verify(mockMvpView).showWeatherInfo(mockWeather);
        verify(mockDataProvider).addHistory(mockWeather);
        verify(mockMvpView).showHistoryAdded();
    }


    private class MockHomeMvpView extends MockMvpView implements HomeMvpView {
        @Override
        public void showWeatherInfo(Weather weather) {
        }

        @Override
        public void showLoading() {
        }

        @Override
        public void hideLoading() {
        }

        @Override
        public void showFailureMessage() {
        }

        @Override
        public void showHistoryAdded() {
        }
    }

}
