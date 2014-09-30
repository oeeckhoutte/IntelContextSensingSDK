package com.intel.samples.runningappssensing;

import com.intel.common.Settings.Environment;
import com.intel.context.Sensing;
import com.intel.context.error.ContextError;
import com.intel.context.item.AppsRunning;
import com.intel.context.item.Item;
import com.intel.context.item.Pedometer;
import com.intel.context.sensing.ContextTypeListener;
import com.intel.context.sensing.SensingEvent;
import com.intel.context.sensing.SensingStatusListener;
import com.intel.identity.Auth;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

public class RunningAppsSampleApplication extends Application {
    private Auth mAuth;
    private Sensing mSensing;
    private static RunningAppsSampleApplication mInstance;
    private ContextTypeListener mAppsListener;

    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mAuth = new Auth(getApplicationContext(),
                Settings.API_KEY, Settings.SECRET,
                Environment.PROD);
        mSensing = new Sensing(getApplicationContext(), new MySensingListener());
        mAppsListener = new AppsListener();      
    }
    
    public static RunningAppsSampleApplication getInstance() {
        return mInstance;
    }

    public Sensing getSensing() {
        return mSensing;
    }

    public Auth getAuth(){
        return mAuth;
    }

    public ContextTypeListener getAppsListener() {
        return mAppsListener;
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

    private class AppsListener implements ContextTypeListener {

        private final String LOG_TAG = AppsListener.class.getName();
        
        public void onReceive(Item state) {
            if (state instanceof Pedometer) {
                Pedometer applicationsState = (Pedometer) state; 
                Log.d(LOG_TAG, "current steps: " 
                       + applicationsState.getSteps());
                Toast.makeText(getApplicationContext(),
                        "New Apps State: " + applicationsState.getSteps(), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Invalid state: " + state.getContextType(), Toast.LENGTH_LONG).show();
            }
        }

        public void onError(ContextError error) {
            Toast.makeText(getApplicationContext(),
                    "Listener Status: " + error.getMessage(), Toast.LENGTH_LONG).show();
            Log.e(LOG_TAG, "Error: " + error.getMessage());
        }
    }
}
