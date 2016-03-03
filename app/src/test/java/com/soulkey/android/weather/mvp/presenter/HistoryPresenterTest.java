package com.soulkey.android.weather.mvp.presenter;

import com.soulkey.android.weather.WTApp;
import com.soulkey.android.weather.WTTestRunner;
import com.soulkey.android.weather.data.DataProvider;
import com.soulkey.android.weather.model.HistoryModel;
import com.soulkey.android.weather.mvp.view.HistoryMvpView;

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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(WTTestRunner.class)
public class HistoryPresenterTest {

    private static final String LAST_CITY = "Sydney";

    @Rule public TestSchedulerRule mTestSchedulerRule = new TestSchedulerRule();

    @Test(expected = IllegalArgumentException.class)
    public void testHistoryPresenterThrowsExceptionWithNullView() {
        new HistoryPresenter(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testHistoryPresenterThrowsExceptionWithNullComponent() {
        new HistoryPresenter(mock(HistoryMvpView.class), null);
    }

    @Test
    public void testHistoryPresenterCreatedWithValidViewAndComponent() {
        MockHistoryMvpView mockMvpView = mock(MockHistoryMvpView.class);
        HistoryPresenter presenter = new HistoryPresenter(mockMvpView, WTApp.from(RuntimeEnvironment.application).getAppServicesComponent());

        assertThat(presenter).isNotNull();
        assertThat(presenter.getView()).isEqualTo(mockMvpView);
    }

    @Ignore("Not yet support jni - java.lang.UnsatisfiedLinkError: no realm-jni in java.library.path")
    @Test
    public void testOnDeleteAllClicked() {
        Realm realm = Realm.getInstance(RuntimeEnvironment.application);
        realm.beginTransaction();
        realm.copyToRealm(new HistoryModel());
        realm.commitTransaction();

        RealmResults<HistoryModel> realmResults = realm.where(HistoryModel.class).findAll();
        assertThat(realmResults.size()).isEqualTo(1);

        MockHistoryMvpView mockMvpView = mock(MockHistoryMvpView.class);
        HistoryPresenter presenter = new HistoryPresenter(mockMvpView, WTApp.from(RuntimeEnvironment.application).getAppServicesComponent());

        presenter.onDeleteAllClicked();
    }

    @Test
    public void testSelectHistory() {
        MockHistoryMvpView mockMvpView = mock(MockHistoryMvpView.class);
        DataProvider mockDataProvider = mock(DataProvider.class);
        HistoryPresenter presenter = new HistoryPresenter(mockMvpView, WTApp.from(RuntimeEnvironment.application).getAppServicesComponent());

        presenter.mDataProvider = mockDataProvider;

        presenter.selectHistory(LAST_CITY);

        verify(mockDataProvider).setLastCity(LAST_CITY);
        verify(mockMvpView).showHomeScreen();
    }

    @Ignore("Not yet support jni - java.lang.UnsatisfiedLinkError: no realm-jni in java.library.path")
    @Test
    public void testDeleteHistory() {
        MockHistoryMvpView mockMvpView = mock(MockHistoryMvpView.class);
        DataProvider mockDataProvider = mock(DataProvider.class);
        when(mockDataProvider.deleteHistory(LAST_CITY)).thenReturn(Observable.just(mock(HistoryModel.class)));
        HistoryPresenter presenter = new HistoryPresenter(mockMvpView, WTApp.from(RuntimeEnvironment.application).getAppServicesComponent());

        presenter.mDataProvider = mockDataProvider;

        presenter.deleteHistory(LAST_CITY);

        mTestSchedulerRule.getScheduler().advanceTimeBy(1, TimeUnit.DAYS);

        verify(mockMvpView).showHistoryDeleted();
    }

    private class MockHistoryMvpView extends MockMvpView implements HistoryMvpView {

        @Override
        public void showHomeScreen() {
        }

        @Override
        public void showHistoryDeleted() {
        }
    }

    }
