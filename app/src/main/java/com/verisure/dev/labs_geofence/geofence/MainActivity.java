package com.verisure.dev.labs_geofence.geofence;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity {

    GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onResume() {
        super.onResume();
        this.populateLogList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.options_wipe:
                Storage.wipeEntries(this);
                populateLogList();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void populateLogList () {
        String[] entries = Storage.getEntries(this);
        ((ListView)findViewById(R.id.listView))
            .setAdapter(new LogEntriesListAdapter(this, entries));
    }

    void scheduleNotification(long timeout, final NotificationCompat.Builder notificationBuilder) {
        new android.os.Handler().postDelayed(
            new Runnable() {
                public void run() {
                    NotificationHandler.showNotification(
                        getApplicationContext(),
                        notificationBuilder
                    );
                }
            },
            timeout
        );
    }
}
