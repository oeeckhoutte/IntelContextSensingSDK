package com.intel.samples.contextsensingapi;

import com.intel.common.Settings.Environment;
import com.intel.context.Sensing;
import com.intel.context.error.ContextError;
import com.intel.context.item.Item;
import com.intel.context.sensing.ContextTypeListener;
import com.intel.context.sensing.SensingEvent;
import com.intel.context.sensing.SensingStatusListener;
import com.intel.identity.Auth;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

public class ContextSensingAPISampleApplication extends Application {
    private Auth mAuth;
    private Sensing mSensing;
    private static ContextSensingAPISampleApplication mInstance;
    private ContextTypeListener mStateListener;

    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mAuth = new Auth(getApplicationContext(),
                Settings.API_KEY, Settings.SECRET,
                Environment.PROD);
        mSensing = new Sensing(getApplicationContext(), new MySensingListener());
        mStateListener = new MyStateListener();      
    }
    
    public static ContextSensingAPISampleApplication getInstance() {
        return mInstance;
    }

    public Sensing getSensing() {
        return mSensing;
    }

    public Auth getAuth(){
        return mAuth;
    }

    public ContextTypeListener getStateListener() {
        return mStateListener;
    }

    private class MySensingListener implements SensingStatusListener {        
        
        private final String LOG_TAG = MySensingListener.class.getName();

        MySensingListener() {}
        
        @Override
        public void onEvent(SensingEvent event) {
            Toast.makeText(getApplicationContext(),
                    "Event: " + event.getDescription(), Toast.LENGTH_SHORT).show();
            Log.i(LOG_TAG, "Event: " + event.getDescription());
        }

        @Override
        public void onFail(ContextError error) {
            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            Log.e(LOG_TAG, "Context Sensing error: " + error.getMessage());            
        }
    }

    private class MyStateListener implements ContextTypeListener {

        private final String LOG_TAG = MyStateListener.class.getName();
        
        public void onReceive(Item state) {
            Log.d(LOG_TAG, "New State: " + state.getContextType());
            Toast.makeText(getApplicationContext(),
                    "New State: " + state.getContextType(), Toast.LENGTH_LONG).show();
            
        }

        public void onError(ContextError error) {
            Toast.makeText(getApplicationContext(),
                    "Listener Status: " + error.getMessage(), Toast.LENGTH_LONG).show();
            Log.e(LOG_TAG, "Error: " + error.getMessage());
        }
    }
}
