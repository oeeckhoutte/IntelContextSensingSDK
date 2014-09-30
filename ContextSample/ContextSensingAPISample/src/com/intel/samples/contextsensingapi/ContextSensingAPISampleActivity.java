package com.intel.samples.contextsensingapi;

import android.app.Activity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.intel.common.Error;
import com.intel.identity.Callback;
import com.intel.context.Sensing;
import com.intel.context.error.ContextError;
import com.intel.context.item.ContextType;
import com.intel.context.sensing.ContextTypeListener;
import com.intel.context.sensing.InitCallback;
import com.intel.exception.ContextProviderException;
import com.intel.identity.Auth;

public class ContextSensingAPISampleActivity extends Activity {
    private final static String LOG_TAG = ContextSensingAPISampleActivity.class.getName();
    private Sensing mySensing;
    private Auth myAuth;
    private ContextTypeListener myStateListener;
    private Button loginButton;
    private Button startDaemonButton;
    private Button startSensingButton;
    private Button addListenerButton;
    private Button removeListenerButton;
    private Button stopSensingButton;
    private Button stopDaemonButton;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mySensing = ContextSensingAPISampleApplication.getInstance().getSensing();
        myAuth = ContextSensingAPISampleApplication.getInstance().getAuth();
        myStateListener = ContextSensingAPISampleApplication.getInstance().getStateListener();
        configureUI();
    }

    public void onDestroy() {
        super.onDestroy();
    }
    
    private void configureUI(){
        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new OnClickListener(){
            public void onClick(View v) {
                login();
            }});
        
        startDaemonButton = (Button) findViewById(R.id.startSensingDaemonButton);
        startDaemonButton.setOnClickListener(new OnClickListener(){
            public void onClick(View v) {
                startDaemon();
            }
        });

        startSensingButton = (Button) findViewById(R.id.startSensingButton);
        startSensingButton.setOnClickListener(new OnClickListener(){
            public void onClick(View v) {
                startSensing();
            }});
        
        addListenerButton = (Button) findViewById(R.id.addListenerButton);
        addListenerButton.setOnClickListener(new OnClickListener(){
            public void onClick(View v) {
                addListener();
            }});

        removeListenerButton = (Button) findViewById(R.id.removeListenerButton);
        removeListenerButton.setOnClickListener(new OnClickListener(){
            public void onClick(View v) {
                removeListener();
            }});

        
        stopSensingButton = (Button) findViewById(R.id.stopSensingButton);
        stopSensingButton.setOnClickListener(new OnClickListener(){
            public void onClick(View v) {
                stopSensing();
            }});
        
        stopDaemonButton = (Button) findViewById(R.id.stopSensingDaemonButton);
        stopDaemonButton.setOnClickListener(new OnClickListener(){
            public void onClick(View v) {
                stopDaemon();
            }
        });
    }
    
    private void login() {
    	if (!myAuth.isLoggedIn()) {
            myAuth.login(Settings.REDIRECT_URI,                        
                    "user:details user:scope profile:full profile:basic profile:full:write location:basic location:enhanced context:developer-specific context:location:detailed context:post:location:detailed context:geolocation:detailed context:time:detailed context:device:applications:running context:post:device:applications:running context:device:telephony context:post:device:telephony context:device:calendar context:post:device:calendar context:device:status:battery context:post:device:status:battery context:media:consumption context:post:media:consumption context:post:device:information",
                    new MyLoginCallback());
        } else {
            Toast.makeText(getApplicationContext(), "Already Logged In", Toast.LENGTH_SHORT).show();
            Log.i(LOG_TAG, "Already Logged In");
        }
    }
    
    private void startDaemon() {
        mySensing.start(new InitCallback() {
            
            @Override
            public void onSuccess() {
                Toast.makeText(getApplicationContext(),
                        "Context Sensing Daemon Started" , Toast.LENGTH_SHORT).show();
            }
            
            @Override
            public void onError(ContextError error) {
                Toast.makeText(getApplicationContext(),
                        "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    
    private void startSensing() {
        Bundle bundle = null;
        try {
            mySensing.enableSensing(ContextType.APPS, bundle);
            Toast.makeText(getApplicationContext(), "State Sensing Enabled" , Toast.LENGTH_SHORT).show();
        } catch (ContextProviderException e) {
            Toast.makeText(getApplicationContext(), "Error enabling sensing: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    
    private void addListener() {
        try {
            mySensing.addContextTypeListener(ContextType.APPS, myStateListener);
            Toast.makeText(getApplicationContext(), "Add Listener Success", Toast.LENGTH_SHORT).show();
        } catch (ContextProviderException e) {
            Log.e(LOG_TAG, "Add Listener error: " + e.getMessage());
        }
    }
    
    private void removeListener() {
    	try {
			mySensing.removeContextTypeListener(myStateListener);
			Toast.makeText(getApplicationContext(), "Remove Listener Success", Toast.LENGTH_SHORT).show();
		} catch (ContextProviderException e) {
			Log.e(LOG_TAG, "Remove Listener error: " + e.getMessage());
		}
    }
    
    private void stopSensing(){
        try {
            mySensing.disableSensing(ContextType.APPS);
            Toast.makeText(getApplicationContext(), "State Sensing Disabled" , Toast.LENGTH_SHORT).show();
        } catch (NullPointerException e) {
            Toast.makeText(getApplicationContext(), "No Context Sensing instance", Toast.LENGTH_LONG).show();
            Log.e(LOG_TAG, "No Context Sensing instance. " + e.getMessage());
            e.printStackTrace();
        } catch (ContextProviderException e) {
            e.printStackTrace();
        }
    }
    
    private void stopDaemon() {
        try {
            mySensing.stop();
        } catch (RuntimeException e) {
            Toast.makeText(getApplicationContext(),
                    "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e(LOG_TAG, "Error: " + e.getMessage());
        }
    }
    
    private class MyLoginCallback implements Callback {

        MyLoginCallback() {}
        
        public void onSuccess(Object accessToken) {
            Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();
        }

        public void onFail(Error error) {
            Toast.makeText(getApplicationContext(), "Login Error: " + error.getDescription(), Toast.LENGTH_LONG).show();
        }
    }

}