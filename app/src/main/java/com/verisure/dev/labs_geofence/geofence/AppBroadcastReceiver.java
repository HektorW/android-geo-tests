package com.verisure.dev.labs_geofence.geofence;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by hekwal on 2017-03-27.
 */

public class AppBroadcastReceiver extends BroadcastReceiver {
    final static String TAG = "AppBroadcastReceiver";
    final static String WIFI_CONNECTED = "Connected";
    final static String WIFI_DISCONNECTED = "Disconnected";
    final static String WIFI_SWITCHED = "Switched";

    static void logIntent (Intent intent) {
        Log.d(TAG, intent.getAction());

        Bundle bundle = intent.getExtras();
        if (bundle == null) return;

        for (String key : bundle.keySet()) {
            Log.d(
                TAG,
                String.format(
                    "%s, %s",
                    key,
                    bundle.get(key).toString()
                )
            );
        }

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        logIntent(intent);

        switch (intent.getAction()) {
            case "android.net.wifi.STATE_CHANGE": handleWifiStateChange(context, intent); break;
        }
    }

    void handleWifiStateChange (Context context, Intent intent) {
        NetworkInfo networkInfo = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
        String lastWifiSsid = Storage.getConnectedWIFI(context);
        String wifiSsid = networkInfo.getExtraInfo();

        boolean isConnectedToWifi =
            networkInfo.isConnectedOrConnecting() &&
            !wifiSsid.equals("<unknown ssid>");
        boolean wasConnectedToWifi = !lastWifiSsid.isEmpty();

        Log.i(TAG, String.format("LastWifiSsid: %s, wifiSsid: %s, isConnectedToWifi: %b", lastWifiSsid, wifiSsid, isConnectedToWifi));

        if (!isConnectedToWifi && !wasConnectedToWifi) {
            // Not connected, wasn't connected
            Log.i(TAG, "Not connected, wasn't connected");
            return;
        }

        if (!isConnectedToWifi && wasConnectedToWifi) {
            // Disconnected from wifi
            logWifiStateChanged(context, wifiSsid, WIFI_DISCONNECTED);
            return;
        }


        if (isConnectedToWifi && !wasConnectedToWifi) {
            // Connected to new wifi
            logWifiStateChanged(context, wifiSsid, WIFI_CONNECTED);
            return;
        }

        if (!wifiSsid.equals(lastWifiSsid)) {
            // Switched wifi
            logWifiStateChanged(context, wifiSsid, WIFI_SWITCHED);
            return;
        }

        Log.i(TAG, "Unhandled wifi state change");
    }

    static void logWifiStateChanged(Context ctx, String ssid, String stateChange) {
        Log.i(TAG, String.format("%s, %s", stateChange, ssid));
        LogEntry entry = LogEntry.wifiEntry(ssid, stateChange);
        Storage.writeConnectedWIFI(
            ctx,
            stateChange == WIFI_DISCONNECTED
                ? ""
                : ssid
        );
        Storage.writeEntry(ctx, entry);
        NotificationHandler.showNotification(ctx, entry);
    }
}
