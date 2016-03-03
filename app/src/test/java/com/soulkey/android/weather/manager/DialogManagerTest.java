package com.soulkey.android.weather.manager;

import android.content.DialogInterface;

import com.soulkey.android.weather.mvp.view.MvpView;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.robolectric.RuntimeEnvironment.application;

public class DialogManagerTest {

    private DialogManager mManager;
    private DialogInterface.OnClickListener mListener;

    @Before
    public void setup() {
        mManager = spy(new DialogManager());
        mListener = spy(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
    }

    @Test
    public void testConstructor() {
        new DialogManager(); // For code coverage
    }

    @Test
    public void testRunDefaultErrorHandlingWithNullErrorType() {
        try {
            mManager.runDefaultErrorHandling(application, null, mListener);
            failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
        } catch (IllegalArgumentException e) {
            assertThat(e).hasMessage("errorType cannot be null");
        }
    }

    @Test
    public void testRunDefaultErrorHandlingWithNetwork() {
        mManager.runDefaultErrorHandling(application, MvpView.ErrorType.NETWORK, mListener);
        verify(mManager).showNetworkErrorDialog(application, mListener);
    }

    @Test
    public void testRunDefaultErrorHandlingWithNoNetwork() {
        mManager.runDefaultErrorHandling(application, MvpView.ErrorType.NO_NETWORK, mListener);
        verify(mManager).showNoNetworkErrorDialog(application, mListener);
    }

    @Test
    public void testRunDefaultErrorHandlingWithServer() {
        mManager.runDefaultErrorHandling(application, MvpView.ErrorType.GENERAL, mListener);
        verify(mManager).showGeneralErrorDialog(application);
    }

    @Test
    public void testShowAlertDialogWithNullContext() {
        mManager.showAlertDialog(null, DialogManager.NO_FIELD, DialogManager.NO_FIELD, DialogManager.NO_FIELD, DialogManager.NO_FIELD, null, null, DialogManager.NO_FIELD);
    }

    @Test
    public void testShowAlertDialogStringsWithNullContext() {
        assertThat(mManager.showAlertDialog(null, "A", "B", "C", "D", null, null, DialogManager.NO_FIELD)).isNull();
    }

    @Test
    public void testShowAlertDialogStringsWithPrevDialog() {
        mManager.showAlertDialog(application, "A", "B", "C", "D", mListener, null, DialogManager.NO_FIELD);
        assertThat(mManager.showAlertDialog(application, "A", "B", "C", "D", null, null, DialogManager.NO_FIELD)).isNull();
    }
}

