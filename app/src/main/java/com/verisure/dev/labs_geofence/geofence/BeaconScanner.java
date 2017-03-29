package com.verisure.dev.labs_geofence.geofence;

import android.content.Context;
import android.support.v4.app.NotificationCompat;

import com.estimote.coresdk.observation.region.beacon.BeaconRegion;
import com.estimote.coresdk.recognition.packets.Beacon;
import com.estimote.coresdk.recognition.packets.Eddystone;
import com.estimote.coresdk.recognition.packets.EstimoteTelemetry;
import com.estimote.coresdk.recognition.packets.Nearable;
import com.estimote.coresdk.service.BeaconManager;

import java.util.List;
import java.util.UUID;

/**
 * Created by hekwal on 2017-03-29.
 */

public class BeaconScanner {
    final static String name = "Hektor mint";
    final static String uuid = "B9407F30-F5F8-466E-AFF9-25556B57FE6D";
    final static int major = 12879;
    final static int minor = 43059;

    private static BeaconManager beaconManager;

    private static BeaconManager getInstance (Context ctx) {
        if (beaconManager == null) beaconManager = new BeaconManager(ctx);
        return beaconManager;
    }

    static void startMonitoring (final Context ctx) {
        beaconManager = getInstance(ctx);

        beaconManager.setMonitoringListener(new BeaconManager.BeaconMonitoringListener() {
            @Override
            public void onEnteredRegion(BeaconRegion beaconRegion, List<Beacon> list) {
                NotificationHandler.showNotification(
                    ctx,
                    new NotificationCompat.Builder(ctx)
                        .setContentTitle(
                            String.format("Enter: %s", beaconRegion.getIdentifier())
                        )
                        .setContentText(beaconRegion.getProximityUUID().toString())
                        .setSmallIcon(R.drawable.ic_bluetooth_connected_black_24dp)
                );
            }

            @Override
            public void onExitedRegion(BeaconRegion beaconRegion) {
                NotificationHandler.showNotification(
                    ctx,
                    new NotificationCompat.Builder(ctx)
                        .setContentTitle(
                            String.format("Exit: %s", beaconRegion.getIdentifier())
                        )
                        .setContentText(beaconRegion.getProximityUUID().toString())
                        .setSmallIcon(R.drawable.ic_bluetooth_connected_black_24dp)
                );
            }
        });

        beaconManager.setTelemetryListener(new BeaconManager.TelemetryListener() {
            @Override
            public void onTelemetriesFound(List<EstimoteTelemetry> list) {
//                NotificationHandler.showNotification(ctx, "Telemetry", "Size: " + list.size());
            }
        });

        beaconManager.setEddystoneListener(new BeaconManager.EddystoneListener() {
            @Override
            public void onEddystonesFound(List<Eddystone> list) {
//                NotificationHandler.showNotification(ctx, "Eddystone", "Size: " + list.size());
            }
        });

        beaconManager.setRangingListener(new BeaconManager.BeaconRangingListener() {
            @Override
            public void onBeaconsDiscovered(BeaconRegion beaconRegion, List<Beacon> list) {
//                NotificationHandler.showNotification(ctx, "Ranging", beaconRegion.getIdentifier());
            }
        });

        beaconManager.setNearableListener(new BeaconManager.NearableListener() {
            @Override
            public void onNearablesDiscovered(List<Nearable> list) {
//                NotificationHandler.showNotification(ctx, "Nearable", "Size: " + list.size());
            }
        });

        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startTelemetryDiscovery();

                beaconManager.startMonitoring(new BeaconRegion(
                    name,
                    null,
                    null,
                    null
                ));

                beaconManager.startRanging(new BeaconRegion(name, null, null, null));

                beaconManager.startEddystoneDiscovery();
                beaconManager.startNearableDiscovery();
            }
        });
    }
}
