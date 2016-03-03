package com.soulkey.android.weather.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.soulkey.android.weather.R;
import com.soulkey.android.weather.manager.LocationManager;
import com.soulkey.android.weather.module.AppServicesComponent;
import com.soulkey.android.weather.mvp.presenter.HistoryPresenter;
import com.soulkey.android.weather.mvp.presenter.HomePresenter;
import com.soulkey.android.weather.mvp.view.HomeMvpView;
import com.soulkey.android.weather.util.ResourceUtils;
import com.soulkey.android.weather.util.ViewUtils;
import com.soulkey.android.weather.util.WeatherUtils;
import com.soulkey.weather.model.Weather;

import java.text.DateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.OnClick;
import timber.log.Timber;

import static com.soulkey.android.weather.mvp.presenter.HomePresenter.FETCH_WEATHER_REQUEST_CODE;
import static com.soulkey.android.weather.mvp.presenter.HomePresenter.HISTORY_REQUEST_CODE;

public class HomeActivity extends PresentableActivity<HomePresenter> implements HomeMvpView, PlaceSelectionListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    @Bind(R.id.last_updated_tv) TextView mLastUpdatedTv;
    @Bind(R.id.infomation_tv) TextView mInformationTv;
    @Bind(R.id.icon_iv) ImageView mIconIv;
    @Bind(R.id.main_weather_tv) TextView mMainWeatherTv;
    @Bind(R.id.progress_v) View mProgressV;

    private GoogleApiClient mGoogleApiClient;

    @Override
    protected HomePresenter createPresenter(AppServicesComponent component) {
        return new HomePresenter(this, component);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.act_home;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initPlaceAutocompleteFragment();
        initGoogleApiClient();
    }

    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showWeatherInfo(Weather weather) {
        hideLoading();
        mInformationTv.setText(String.format("%s, %s\nTemp : %s%s\nHumidity : %s\nSunrise : %s\nSunset : %s",
                weather.getCityName(), weather.getCountry(),
                weather.getTemp(), WeatherUtils.getUnitSymbol(), weather.getHumidity(),
                weather.getSunRise(), weather.getSunSet()));
        mIconIv.setImageResource(ResourceUtils.getImageId(this, weather.getIcon()));
        mMainWeatherTv.setText(weather.getMain());
        mLastUpdatedTv.setText(getString(R.string.last_updated, DateFormat.getDateTimeInstance().format(new Date())));
    }

    @Override
    public void showError(ErrorType errorType, final int requestCode) {
        mDialogManager.runDefaultErrorHandling(getContext(), errorType, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (requestCode == FETCH_WEATHER_REQUEST_CODE) {
                    getPresenter().fetchWeather();
                }
            }
        });
    }

    @Override
    public void showLoading() {
        ViewUtils.show(mProgressV);
    }

    @Override
    public void hideLoading() {
        ViewUtils.hide(mProgressV);
    }

    @Override
    public void showFailureMessage() {
        hideLoading();
        Toast.makeText(this, getString(R.string.failed_to_get_location), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showHistoryAdded() {
        Timber.d("Added History");
    }

    @Override
    public void onPlaceSelected(Place place) {
        getPresenter().fetchWeather(place);
    }

    @Override
    public void onError(Status status) {
        Toast.makeText(this, getString(R.string.place_selection_failed, status.getStatusMessage()), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnected(Bundle connectionHint) {
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == LocationManager.REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                if (!mGoogleApiClient.isConnecting() && !mGoogleApiClient.isConnected()) {
                    mGoogleApiClient.connect();
                } else {
                    getPresenter().fetchWeather();
                }
            } else {
                hideLoading();
            }
        } else if (requestCode == HISTORY_REQUEST_CODE) {
            if (resultCode == HistoryPresenter.HISTORY_SELECTED_REQUEST_CODE) {
                showLoading();
                getPresenter().fetchWeather();
            }
        }
    }

    @OnClick(R.id.gps_btn)
    public void onGpsClicked() {
        getPresenter().onGpsClicked(getLastLocation());
    }

    @OnClick(R.id.history_btn)
    public void onHistoryClicked() {
        startActivityForResult(new Intent(getContext(), HistoryActivity.class), HISTORY_REQUEST_CODE, null);
    }

    private void initPlaceAutocompleteFragment() {
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        autocompleteFragment.setOnPlaceSelectedListener(this);
        autocompleteFragment.setHint(getString(R.string.search_hint));
    }

    private void initGoogleApiClient() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                    .addConnectionCallbacks((HomeActivity) getContext())
                    .addOnConnectionFailedListener((HomeActivity) getContext())
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    private Location getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            getPresenter().requestGpsPermission();
            return null;
        }

        return LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
    }
}
