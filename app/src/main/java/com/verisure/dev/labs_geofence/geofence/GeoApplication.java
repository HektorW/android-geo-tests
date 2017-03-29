package com.verisure.dev.labs_geofence.geofence;

import android.app.Application;

/**
 * Created by hekwal on 2017-03-29.
 */

public class GeoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        BeaconScanner.startMonitoring(getApplicationContext());
    }
}
