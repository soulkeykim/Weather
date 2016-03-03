package com.soulkey.android.weather.manager;

import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;

import java.util.concurrent.TimeUnit;

public class LocationManager implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    public static final long SECOND_IN_MILLISECONDS = TimeUnit.SECONDS.toMillis(1);

    public static final int REQUEST_CODE = 1000;

    private static final int NO_PROVIDERS_ENABLED = -1;

    private static final int NETWORK_PROVIDER_ONLY_ENABLED = 1;

    private static final int GPS_PROVIDERS_ONLY_ENABLED = 2;

    private static final int ALL_PROVIDERS_ENABLED = 3;

    private static final String PROVIDER_GPS = "gps";

    private static final String PROVIDER_NETWORK = "network";

    private static final int LOCATION_UPDATE_INTERVAL_SECONDS = 10;

    private static final int LOCATION_MAX_UPDATE_INTERVAL_SECONDS = 5;

    protected GoogleApiClient mGoogleApiClient;

    LocationRequest mLocationRequest;

    Context mContext;

    public LocationManager(Context context) {
        mContext = context;
    }

    public void requestLocationSetting(ResultCallback<LocationSettingsResult> resultResultCallback) {
        if (!isGpsAvailable()) {
            if (mGoogleApiClient == null) {
                configure();
            }

            if (mGoogleApiClient != null && !mGoogleApiClient.isConnected()) {
                mGoogleApiClient.connect();
            }

            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(getLocationRequest());
            builder.setAlwaysShow(true);

            PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
            result.setResultCallback(resultResultCallback);
        }
    }

    private void configure() {
        mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    public boolean isGpsAvailable() {
        return NO_PROVIDERS_ENABLED != checkLocationProviders();
    }

    private int checkLocationProviders() {
        int result = NO_PROVIDERS_ENABLED;
        String locationProviders = Settings.System.getString(mContext.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        if (locationProviders == null || locationProviders.trim().length() == 0) {
            result = NO_PROVIDERS_ENABLED;
        } else {
            boolean gpsEnabled = false;
            boolean networkEnabled = false;
            String[] providersEnabled = locationProviders.split(",");
            for (String currentProvider : providersEnabled) {
                if (currentProvider.equals(PROVIDER_GPS)) {
                    result = GPS_PROVIDERS_ONLY_ENABLED;
                    gpsEnabled = true;
                } else if (currentProvider.equals(PROVIDER_NETWORK)) {
                    result = NETWORK_PROVIDER_ONLY_ENABLED;
                    networkEnabled = true;
                }
            }
            if (gpsEnabled && networkEnabled) {
                result = ALL_PROVIDERS_ENABLED;
            }
        }

        return result;
    }

    private LocationRequest getLocationRequest() {
        if (mLocationRequest == null) {
            mLocationRequest = LocationRequest.create();
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

            long updateIntervalMilliSecs = LOCATION_UPDATE_INTERVAL_SECONDS * SECOND_IN_MILLISECONDS;
            mLocationRequest.setInterval(updateIntervalMilliSecs);

            long updateMaxIntervalMilliSecs = LOCATION_MAX_UPDATE_INTERVAL_SECONDS * SECOND_IN_MILLISECONDS;
            mLocationRequest.setFastestInterval(updateMaxIntervalMilliSecs);
        }

        return mLocationRequest;
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}