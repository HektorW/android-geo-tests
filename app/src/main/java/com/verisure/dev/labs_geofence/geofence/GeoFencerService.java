package com.verisure.dev.labs_geofence.geofence;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;

/**
 * Created by hekwal on 2017-03-28.
 */

public class GeoFencerService extends IntentService implements
    GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener {

    final static String GEOFENCE_ID = "LOL";
    final static float GEOFENCE_RADIUS_M = 50.0f;
    final static double VERISURE_LAT = 55.613731;
    final static double VERISURE_LONG = 12.996786;
    final static double HOME_LAT = 55.608386;
    final static double HOME_LONG = 13.038804;


    public GeoFencerService () {
        this("Lol");
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public GeoFencerService(String name) {
        super(name);
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }

    GeofencingRequest getGeoFencingRequest () {
        return new GeofencingRequest.Builder()
            .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            .addGeofence(
                new Geofence.Builder()
                    .setRequestId(GEOFENCE_ID)
                    .setCircularRegion(
                        VERISURE_LAT,
                        VERISURE_LONG,
                        GEOFENCE_RADIUS_M
                    )
                    .setExpirationDuration(Geofence.NEVER_EXPIRE)
                    .setTransitionTypes(
                        Geofence.GEOFENCE_TRANSITION_ENTER |
                            Geofence.GEOFENCE_TRANSITION_EXIT
                    )
                    .build()
            )
            .build();
    }
}
