package com.verisure.dev.labs_geofence.geofence;

import android.util.Log;

import java.util.Date;

/**
 * Created by hekwal on 2017-03-27.
 */

public class LogEntry {
    public static final String WIFI = "Wifi";
    public static final String GPS = "GPS";

    static final String SPLIT_CHAR = ";";

    public String source;
    public String location;
    public Date date;
    public String extra;

    public LogEntry (String source, String location) {
        this(source, location, new Date());
    }

    public LogEntry (String source, String location, String extra) {
        this(source, location, new Date(), extra);
    }

    public LogEntry (String source, String location, Date date) {
        this(source, location, date, "");
    }

    public LogEntry (String source, String location, Date date, String extra) {
        this.source = source;
        this.location = location;
        this.date = date;
        this.extra = extra;
    }

    public static LogEntry fromString (String data) {
        String[] split = data.split(SPLIT_CHAR);

        if (split.length < 3) {
            return null;
        }

        return new LogEntry(
            split[0],
            split[1],
            dateFromString(split[2]),
            split.length < 4
                ? ""
                : split[3]
        );
    }

    public String toString () {
        return (
            this.source +
                SPLIT_CHAR +
            this.location +
                SPLIT_CHAR +
            stringFromDate(this.date) +
                SPLIT_CHAR +
            this.extra
        );
    }

    static String stringFromDate (Date date) {
        return String.valueOf(date.getTime());
    }

    static Date dateFromString (String dateStr) {
        return new Date(Long.parseLong(dateStr));
    }

    static LogEntry wifiEntry (String ssid, String switchState) {
        return new LogEntry(
            LogEntry.WIFI,
            ssid,
            switchState
        );
    }
}
