package com.soulkey.android.weather.manager;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import com.soulkey.android.weather.R;
import com.soulkey.android.weather.mvp.view.MvpView;

import java.lang.ref.WeakReference;

public class DialogManager {

    public static final int NO_FIELD = 0;

    protected WeakReference<AlertDialog> mCurrentAlertDialogRef;

    public void runDefaultErrorHandling(@Nullable Context context, @NonNull MvpView.ErrorType errorType, @Nullable DialogInterface.OnClickListener onRetryListener) {
        if (errorType == null) {
            throw new IllegalArgumentException("errorType cannot be null");
        }

        switch (errorType) {
            case NETWORK:
                showNetworkErrorDialog(context, onRetryListener);
                break;

            case NO_NETWORK:
                showNoNetworkErrorDialog(context, onRetryListener);
                break;

            case GENERAL:
                showGeneralErrorDialog(context);
                break;
        }

    }

    public AlertDialog showNetworkErrorDialog(@Nullable Context context, @Nullable DialogInterface.OnClickListener onRetryListener) {
        return showAlertDialog(
                context,
                R.string.dialog_title_error_network,
                R.string.dialog_body_error_network,
                R.string.dialog_action_retry,
                R.string.dialog_action_cancel,
                onRetryListener,
                null,
                NO_FIELD
        );
    }

    public AlertDialog showNoNetworkErrorDialog(@Nullable Context context, @Nullable DialogInterface.OnClickListener onRetryListener) {
        return showAlertDialog(
                context,
                R.string.dialog_title_error_no_network,
                R.string.dialog_body_error_no_network,
                R.string.dialog_action_retry,
                R.string.dialog_action_cancel,
                onRetryListener,
                null,
                NO_FIELD
        );
    }

    public AlertDialog showGeneralErrorDialog(@Nullable Context context) {
        return showAlertDialog(
                context,
                R.string.dialog_title_error_general,
                R.string.dialog_body_error_general,
                NO_FIELD,
                R.string.dialog_action_close,
                null,
                null,
                NO_FIELD
        );
    }

    public AlertDialog showAlertDialog(@Nullable Context context, @StringRes int title, @StringRes int message, @StringRes int positive, @StringRes int negative, @Nullable DialogInterface.OnClickListener positiveListener, @Nullable DialogInterface.OnClickListener negativeListener, @LayoutRes int viewResId) {
        if (context == null) {
            return null;
        }
        return showAlertDialog(
                context,
                title == NO_FIELD ? null : context.getString(title),
                message == NO_FIELD ? null : context.getString(message),
                positive == NO_FIELD ? null : context.getString(positive),
                negative == NO_FIELD ? null : context.getString(negative),
                positiveListener,
                negativeListener,
                viewResId
        );
    }

    public AlertDialog showAlertDialog(@Nullable Context context, @Nullable String title, @Nullable String message,
                                       @Nullable String positive, @Nullable String negative, @Nullable DialogInterface.OnClickListener positiveListener,
                                       @Nullable DialogInterface.OnClickListener negativeListener, @LayoutRes int viewResId) {

        AlertDialog currentAlertDialog = mCurrentAlertDialogRef == null ? null : mCurrentAlertDialogRef.get();

        if (context != null && (currentAlertDialog == null || !currentAlertDialog.isShowing())) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialog);

            if (!TextUtils.isEmpty(title)) {
                builder.setTitle(title);
            }

            if (!TextUtils.isEmpty(message)) {
                builder.setMessage(message);
            }

            if (!TextUtils.isEmpty(positive)) {
                builder.setPositiveButton(positive, positiveListener);
            }

            if (!TextUtils.isEmpty(negative)) {
                builder.setNegativeButton(negative, negativeListener);
            }

            if (viewResId != NO_FIELD) {
                builder.setView(viewResId);
            }

            currentAlertDialog = builder.create();
            currentAlertDialog.show();
            mCurrentAlertDialogRef = new WeakReference<>(currentAlertDialog);
            return currentAlertDialog;
        }
        return null;
    }
}