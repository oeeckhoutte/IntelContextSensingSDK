package com.intel.samples.ringermodeprovider;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;

import com.intel.context.provider.IProviderPublisher;
import com.intel.context.provider.IStateProvider;
import com.intel.context.item.Custom;
import com.intel.exception.ContextProviderException;

public class RingerModeProvider implements IStateProvider {
    private final static String LOG_TAG = RingerModeProvider.class.getName();
    private IProviderPublisher mPublisher = null;
    private Context mContext = null;
    private BroadcastReceiver ringerModeReceiver = null;

    public void start(Context context, IProviderPublisher publisher,
            Bundle settings) throws ContextProviderException {
        mContext = context;
        mPublisher = publisher;
        ringerModeReceiver = new RingerModeReceiver();
        IntentFilter filter = new IntentFilter(AudioManager.RINGER_MODE_CHANGED_ACTION);
        context.registerReceiver(ringerModeReceiver, filter);
    }

    public void stop() {
        mContext.unregisterReceiver(ringerModeReceiver);
        Log.d(LOG_TAG, "Custom Provider Stopped");
    }
    
    private class RingerModeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle received = intent.getExtras();
            
            String stateValue = null;
            int ringerMode = (Integer) received.get(android.media.AudioManager.EXTRA_RINGER_MODE);

            switch (ringerMode){
            case android.media.AudioManager.RINGER_MODE_NORMAL:
                stateValue = "NORMAL";
                break;
            case android.media.AudioManager.RINGER_MODE_SILENT:
                stateValue = "SILENT";
                break;
            case android.media.AudioManager.RINGER_MODE_VIBRATE:
                stateValue = "VIBRATE";
                break;
            }

            Custom state = new Custom("urn:x-intel:context:type:custom:ringermode");
            JSONObject value = new JSONObject();
            try {
                value.put("RingerMode", stateValue);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            state.setCustomValue(value);
            mPublisher.updateState(state);
        }
    }    
}
