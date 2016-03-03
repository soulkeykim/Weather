package com.soulkey.android.weather.fragment;

import android.os.Bundle;

import com.soulkey.android.weather.R;
import com.soulkey.android.weather.WTApp;
import com.soulkey.android.weather.WTTestRunner;
import com.soulkey.android.weather.module.AppServicesComponent;
import com.soulkey.android.weather.mvp.presenter.Presenter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(WTTestRunner.class)
public class PresentableFragmentTest {

    @Test(expected = IllegalStateException.class)
    public void testThrowsExceptionIfNoPresenterProvided() {
        DummyFragment frag = DummyFragment.withNullPresenter();
        SupportFragmentTestUtil.startFragment(frag);
        frag.onCreate(mock(Bundle.class));
    }

    @Test
    public void testDelegatesToPresenter() {
        DummyFragment frag = DummyFragment.withMockPresenter();

        SupportFragmentTestUtil.startFragment(frag);

        verify(frag.presenter).onCreate(any(Bundle.class));
        verify(frag.presenter).onStart();
        verify(frag.presenter).onResume();

        frag.onSaveInstanceState(mock(Bundle.class));
        verify(frag.presenter).onSaveInstanceState(any(Bundle.class));

        frag.onPause();
        verify(frag.presenter).onPause();

        frag.onStop();
        verify(frag.presenter).onStop();

        frag.onDestroy();
        verify(frag.presenter).onDestroy();
    }


    public static class DummyFragment extends PresentableFragment<Presenter> {

        static DummyFragment withMockPresenter() {
            DummyFragment frag = withNullPresenter();
            frag.presenter = mock(Presenter.class);
            return frag;
        }

        static DummyFragment withNullPresenter() {
            DummyFragment frag = new DummyFragment();
            frag.app = (WTApp) RuntimeEnvironment.application;
            return frag;
        }

        Presenter presenter;

        @Override
        protected Presenter createPresenter(AppServicesComponent servicesComponent, Bundle savedInstanceState) {
            return presenter;
        }

        @Override
        protected int getLayoutId() {
            return R.layout.act_home;
        }
    }
}