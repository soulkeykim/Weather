package com.soulkey.android.weather.fragment;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.soulkey.android.weather.WTApp;

import butterknife.ButterKnife;

/**
 * Common functionality for all fragments in the app
 */
public abstract class BaseFragment extends RxFragment {

    /**
     * @return id of the layout to use in this fragment
     */
    @LayoutRes
    protected abstract int getLayoutId();

    protected WTApp app;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (WTApp) getActivity().getApplicationContext();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(getLayoutId(), container, false);

        ButterKnife.bind(this, view);
        onCreateView(view, savedInstanceState);

        return view;
    }

    /**
     * @see #onCreateView(LayoutInflater, ViewGroup, Bundle)
     */
    protected void onCreateView(View rootView, Bundle savedInstanceState) {
        // Hook for subclasses to override
    }

    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}