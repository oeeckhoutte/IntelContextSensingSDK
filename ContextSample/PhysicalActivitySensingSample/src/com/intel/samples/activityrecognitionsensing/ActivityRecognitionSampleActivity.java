package com.intel.samples.activityrecognitionsensing;

import android.app.Activity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.intel.common.Error;
import com.intel.identity.AccessToken;
import com.intel.identity.Callback;
import com.intel.context.Sensing;
import com.intel.context.error.ContextError;
import com.intel.context.item.ContextType;
import com.intel.context.sensing.ContextTypeListener;
import com.intel.context.sensing.InitCallback;
import com.intel.exception.ContextProviderException;
import com.intel.identity.Auth;

public class ActivityRecognitionSampleActivity extends Activity {
    private final static String LOG_TAG = ActivityRecognitionSampleActivity.class.getName();    
    private Button loginButton;
    private Button startSensingButton;
    private Button stopSensingButton;
    private Button startDaemonButton;
    private Button stopDaemonButton;
    private Auth myAuth;
    private Sensing mySensing;
    private ContextTypeListener mActivityRecognitionListener;
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        myAuth = ActivityRecognitionSampleApplication.getInstance().getAuth();
        mySensing = ActivityRecognitionSampleApplication.getInstance().getSensing();
        mActivityRecognitionListener = ActivityRecognitionSampleApplication.getInstance().getActivityRecognitionListener();
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
                    "context:post:device:sensor context:post:device:information",
                    new MyLoginCallback()); 
        } else {
            Toast.makeText(getApplicationContext(), "Already Logged In", Toast.LENGTH_SHORT).show();
            Log.i(LOG_TAG, "Already Logged In");
        }
    }
    
    private void startDaemon() {
        mySensing.start(new InitCallback() {
            public void onSuccess() {
                Toast.makeText(getApplicationContext(),
                        "Context Sensing Daemon Started" , Toast.LENGTH_SHORT).show();
            }
            public void onError(ContextError error) {
                Toast.makeText(getApplicationContext(),
                        "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    
    private void startSensing() {
    	Bundle settings = null;
        try {
            mySensing.enableSensing(ContextType.ACTIVITY_RECOGNITION, settings);
            mySensing.addContextTypeListener(ContextType.ACTIVITY_RECOGNITION, mActivityRecognitionListener);
        } catch (ContextProviderException e) {
            Toast.makeText(getApplicationContext(), "Error enabling / adding listener to provider: " + e.getMessage(), Toast.LENGTH_LONG).show();                    
            Log.e(LOG_TAG, "Error enabling provider");
        }
    }
    
    private void stopSensing(){
        try {
            mySensing.removeContextTypeListener(mActivityRecognitionListener);   
            mySensing.disableSensing(ContextType.ACTIVITY_RECOGNITION);
                Toast.makeText(getApplicationContext(), "Activity Recognition State Sensing Disabled" , Toast.LENGTH_SHORT).show();
        } catch (ContextProviderException e) {
            Toast.makeText(getApplicationContext(), "Error disabling provider: " + e.getMessage() , Toast.LENGTH_LONG).show();
            Log.e(LOG_TAG, "Error disabling provider: " + e.getMessage());
        } catch (NullPointerException e) {
            Toast.makeText(getApplicationContext(), "Context Sensing not started", Toast.LENGTH_LONG).show();
            Log.e(LOG_TAG, "Context Sensing not started");
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
        
        public void onSuccess(Object response) {
            if (response instanceof AccessToken) {
                Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();
                Log.i(LOG_TAG, "Login Success, Token: " + ((AccessToken)response).getToken());
            }
        }

        public void onFail(Error error) {
            Toast.makeText(getApplicationContext(), "Login Error: " + error.getDescription(), Toast.LENGTH_LONG).show();
        }
    }
}