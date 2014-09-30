package com.intel.samples.callsensing;

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

public class CallSampleActivity extends Activity {
    private final static String LOG_TAG = CallSampleActivity.class.getName();
    private Auth myAuth;
    private Sensing mySensing;
    private ContextTypeListener mListener;
    private Button loginButton;
    private Button startSensingButton;
    private Button stopSensingButton;
    private Button startDaemonButton;
    private Button stopDaemonButton;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        myAuth = CallSampleApplication.getInstance().getAuth();
        mySensing = CallSampleApplication.getInstance().getSensing();
        mListener = CallSampleApplication.getInstance().getCallsListener();
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
            }
        });

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
            }
        });
        
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
                    "user:details context:device:telephony context:post:device:telephony context:post:device:information",
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
        try {                 
            Bundle bundle = null;
            mySensing.enableSensing(ContextType.CALL, bundle);
            Toast.makeText(getApplicationContext(), "Call State Sensing Enabled" , Toast.LENGTH_SHORT).show();
            mySensing.addContextTypeListener(ContextType.CALL, mListener);
            Toast.makeText(getApplicationContext(), "Add Listener Success", Toast.LENGTH_SHORT).show();
        } catch (ContextProviderException e) {                 
            Toast.makeText(getApplicationContext(), "Error enabling provider: " + e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e(LOG_TAG, "Error Enabling provider / Adding Listener: " + e.getMessage());
        }
    }
    
    private void stopSensing(){
        try {
            mySensing.disableSensing(ContextType.CALL);
            Toast.makeText(getApplicationContext(), "Call State Sensing Disabled" , Toast.LENGTH_SHORT).show();
        } catch (ContextProviderException e) {
            Toast.makeText(getApplicationContext(), "Error disabling provider: " + e.getMessage() , Toast.LENGTH_LONG).show();
            Log.e(LOG_TAG, "Error disabling provider: " + e.getMessage());
        } catch (RuntimeException e) {
            Toast.makeText(getApplicationContext(),
                    "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e(LOG_TAG, "Error: " + e.getMessage());
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
            Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();
            Log.i(LOG_TAG, "Login Success, Token: " + ((AccessToken)response).getToken());
        }

        public void onFail(Error error) {
            Toast.makeText(getApplicationContext(), "Login Error: " + error.getDescription(), Toast.LENGTH_LONG).show();
        }
    }

}