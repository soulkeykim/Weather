package com.soulkey.android.weather.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.soulkey.android.weather.TestUtils;
import com.soulkey.android.weather.WTTestRunner;
import com.soulkey.android.weather.module.AppServicesComponent;
import com.soulkey.android.weather.mvp.presenter.HomePresenter;
import com.soulkey.android.weather.mvp.presenter.Presenter;
import com.soulkey.android.weather.mvp.view.HomeMvpView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.util.ActivityController;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.robolectric.Robolectric.buildActivity;

@RunWith(WTTestRunner.class)
public class PresentableActivityTest {

    @Test(expected = IllegalStateException.class)
    public void testThrowsExceptionIfNoPresenterProvided() {
        Robolectric.setupActivity(PresentableActivityWithNoPresenter.class);
    }

    @Test
    public void testDelegatesToPresenter() {
        final ActivityController<PresentableActivityWithMockPresenter> controller
                = buildActivity(PresentableActivityWithMockPresenter.class);

        final Presenter presenter = controller.setup().get().mPresenter;
        controller.saveInstanceState(mock(Bundle.class))
                .pause()
                .stop()
                .destroy()
                .get();

        verify(presenter).onCreate(any(Bundle.class));
        verify(presenter).onStart();
        verify(presenter).onResume();
        verify(presenter).onSaveInstanceState(any(Bundle.class));
        verify(presenter).onPause();
        verify(presenter).onStop();
        verify(presenter).onDestroy();
    }

    static class PresentableActivityWithMockPresenter extends PresentableActivity {
        @Override
        protected Presenter createPresenter(AppServicesComponent component) {
            return spy(new HomePresenter(TestUtils.createView(HomeMvpView.class), component));
        }

        @Override
        protected int getLayoutResource() {
            return 0;
        }
    }

    static class PresentableActivityWithNoPresenter extends PresentableActivityWithMockPresenter {
        @NonNull
        @Override
        protected Presenter createPresenter(AppServicesComponent component) {
            return null;
        }
    }
}