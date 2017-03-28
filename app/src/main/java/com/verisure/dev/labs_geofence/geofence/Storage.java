package com.verisure.dev.labs_geofence.geofence;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by hekwal on 2017-03-27.
 */

public class Storage {
    static final String LOG_ENTRIES_FILENAME = "LOG_ENTRIES_FILENAME";
    static final String WIFI_SSID_FILENAME = "WIFI_SSID_FILENAME";

    static void writeToFile (Context ctx, String filename, String content)  {
        try {
            FileOutputStream fos = ctx.openFileOutput(filename, Context.MODE_PRIVATE);
            fos.write(content.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static String[] readFile (Context ctx, String filename) {
        ArrayList<String> arrayList = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                    ctx.openFileInput(filename)
                )
            );

            String chunk;
            while ((chunk = reader.readLine()) != null) {
                arrayList.add(chunk);
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return arrayList.toArray(new String[arrayList.size()]);
    }

    public static void writeEntry (Context ctx, LogEntry entry) { writeEntry(ctx, entry.toString()); }

    public static void writeEntry (Context ctx, String entry) {
        try {
            PrintWriter writer = new PrintWriter(
                ctx.openFileOutput(LOG_ENTRIES_FILENAME, Context.MODE_PRIVATE | Context.MODE_APPEND)
            );
            writer.println(entry);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void wipeEntries (Context ctx) {
        try {
            FileOutputStream fos = ctx.openFileOutput(LOG_ENTRIES_FILENAME, Context.MODE_PRIVATE);
            fos.write("".getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String[] getEntries (Context ctx) {
        return readFile(ctx, LOG_ENTRIES_FILENAME);
    }

    public static void writeConnectedWIFI (Context ctx, String wifiSsid) {
        writeToFile(ctx, WIFI_SSID_FILENAME, wifiSsid);
    }

    public static String getConnectedWIFI (Context ctx) {
        String[] content = readFile(ctx, WIFI_SSID_FILENAME);
        if (content.length > 0) return content[0];
        return "";
    }
}
