package com.verisure.dev.labs_geofence.geofence;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

/**
 * Created by hekwal on 2017-03-27.
 */

public class NotificationHandler {

    static void showNotification (Context ctx, LogEntry entry) {
        showNotification(
            ctx,
            new NotificationCompat.Builder(ctx)
                .setContentTitle(entry.source)
                .setContentText(
                    entry.extra.isEmpty()
                        ? entry.location
                        : String.format("%s, %s", entry.location, entry.extra)
                )
                .setSmallIcon(R.drawable.ic_wifi_black_24dp)
        );
    }

    static void showNotification (Context ctx, String title, String text) {
        showNotification(ctx, new NotificationCompat.Builder(ctx)
            .setSmallIcon(R.drawable.ic_stat_layout)
            .setContentTitle(title)
            .setContentText(text)
        );
    }

    static void showNotification (Context ctx, NotificationCompat.Builder notificationBuilder) {
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(ctx);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(new Intent(ctx, MainActivity.class));

        notificationBuilder.setContentIntent(
            stackBuilder.getPendingIntent(
                0,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        );

        ((NotificationManager)ctx.getSystemService(ctx.NOTIFICATION_SERVICE))
            .notify(
                0,
                notificationBuilder
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setAutoCancel(true)
                    .setLights(Color.YELLOW, 3000, 3000)
                    .setVibrate(new long[] { 300, 300, 300, 300 })
                    .addAction(
                        R.drawable.ic_bluetooth_connected_black_24dp,
                        "Rate",
                        PendingIntent.getActivity(
                            ctx,
                            0,
                            new Intent(ctx, MainActivity.class),
                            PendingIntent.FLAG_UPDATE_CURRENT
                        )
                    )
                    .build()
            );
    }
}
