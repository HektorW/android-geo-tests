package com.verisure.dev.labs_geofence.geofence;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;

/**
 * Created by hekwal on 2017-03-27.
 */

public class LogEntriesListAdapter extends ArrayAdapter<String> {
    String[] data;

    public LogEntriesListAdapter(Context context, String[] data) {
        super(context, R.layout.log_entry_row, data);
        this.data = data;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView != null
            ? convertView
            : LayoutInflater.from(this.getContext())
                .inflate(R.layout.log_entry_row, null);

        LogEntry entry = LogEntry.fromString(this.getItem(position));
        if (entry != null) {
            ((TextView)v.findViewById(R.id.sourceTV))
                .setText(entry.source);

            ((TextView)v.findViewById(R.id.locationTV))
                .setText(entry.location);

            ((TextView)v.findViewById(R.id.dateTV))
                .setText(
                    new SimpleDateFormat("d MMM - HH:mm:ss")
                        .format(entry.date)
                );

            ((TextView)v.findViewById(R.id.extraTV))
                .setText(
                    entry.extra.isEmpty()
                        ? "<no extra>"
                        : entry.extra
                );
            v.findViewById(R.id.extraTV)
                .setVisibility(
                    entry.extra.isEmpty()
                        ? View.INVISIBLE
                        : View.VISIBLE
                );
        }

        return v;
    }
}
