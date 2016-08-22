package com.androidalien.blogcount;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.WearableListenerService;



public class phoneListenerService extends WearableListenerService {

    private static final String TAG = "BlogCount";

    private static final String COUNT_PATH = "/count";
    private static final String COUNT_KEY = "com.androidalien.blogcount.COUNT_KEY";
    public static final String ACTION_RECEIVE = "ACTION_RECEIVE";
    public static final String DATA_COUNT = "DATA_COUNT";


    @Override
    public void onDataChanged(DataEventBuffer dataEventBuffer) {
        super.onDataChanged(dataEventBuffer);

        for (DataEvent dataEvent : dataEventBuffer) {

            if (dataEvent.getType() == DataEvent.TYPE_CHANGED) {
                DataItem item = dataEvent.getDataItem();

                switch (item.getUri().getPath()) {

                    case COUNT_PATH:
                        DataMap dataMap = DataMapItem.fromDataItem(item).getDataMap();

                        int count = dataMap.getInt(COUNT_KEY);

                        Intent intent = new Intent();
                        intent.setAction(ACTION_RECEIVE);
                        intent.putExtra(DATA_COUNT, count);
                        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

                        Log.d(TAG, "data received. Count - " + count);
                        break;


                }

            }
        }
    }
}
