package com.intel.sample.legacyrulecontextsource;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.intel.context.item.Custom;
import com.intel.context.provider.IProviderPublisher;
import com.intel.context.service.DataSetIdent;
import com.intel.context.service.ISourcePublisher;

public class RuleContextSourcePublisher implements ISourcePublisher {

    private IProviderPublisher mProviderPublisher;
    private static final String RULE_URI = "urn:x-intel:context:type:custom:rulecontext";
    private static final String TAG = RuleContextSourcePublisher.class.getName();
    
    /**
     * Rule Context source publisher constructor
     * @param publisher CSP SDK publisher
     */
    public RuleContextSourcePublisher(IProviderPublisher publisher) {
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
            Custom item = new Custom(RULE_URI);
            JSONObject object = new JSONObject(arg1.value);
            Log.e(TAG, "Item: " + object.toString());
            item.setCustomValue(object);
            mProviderPublisher.updateState(item);
        } catch (JSONException e) {
            Log.e(TAG, "Error parsing value in rule publisher: " + e.getMessage());
        }
    }
}
