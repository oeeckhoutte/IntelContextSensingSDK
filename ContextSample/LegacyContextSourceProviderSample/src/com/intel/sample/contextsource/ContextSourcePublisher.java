package com.intel.sample.contextsource;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.intel.context.item.Custom;
import com.intel.context.provider.IProviderPublisher;
import com.intel.context.service.DataSetIdent;
import com.intel.context.service.ISourcePublisher;

public class ContextSourcePublisher implements ISourcePublisher {

    private IProviderPublisher mProviderPublisher;
    private static final String SOURCE_URI = "urn:x-intel:context:type:custom:source";
    private static final String TAG = ContextSourcePublisher.class.getName();
    
    /**
     * Rule Context source publisher constructor
     * @param publisher CSP SDK publisher
     */
    public ContextSourcePublisher(IProviderPublisher publisher) {
        mProviderPublisher = publisher;
    }
    
    @Override
    public IBinder asBinder() {
        return null;
    }

    /**
     * Convert from Legacy CDF item to CSP SDK Item
     */
    @Override
    public void PublishContextUpdate(DataSetIdent arg0,
            com.intel.context.service.Item arg1) throws RemoteException {
        try {
            Custom item = new Custom(SOURCE_URI);
            JSONObject object = new JSONObject(arg1.value);
            Log.e(TAG, "Item: " + object.toString());
            item.setCustomValue(object);
            mProviderPublisher.updateState(item);
        } catch (JSONException e) {
            Log.e(TAG, "Error parsing value in source publisher: " + e.getMessage());
        }
    }
}
