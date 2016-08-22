package com.androidalien.blogcount;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.wearable.view.WatchViewStub;
import android.widget.TextView;

import com.androidalien.blogcount.R;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends Activity {

    private TextView mTvCount;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTvCount = (TextView) stub.findViewById(R.id.tv_phone_count);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(phoneListenerService.ACTION_RECEIVE);
        LocalBroadcastManager.getInstance(this).registerReceiver(wearableBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(wearableBroadcastReceiver);
    }

    private BroadcastReceiver wearableBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equalsIgnoreCase(phoneListenerService.ACTION_RECEIVE)) {
                int count = intent.getIntExtra(phoneListenerService.DATA_COUNT, 0);
                mTvCount.setText(String.valueOf(count));
            }
        }
    };
}
