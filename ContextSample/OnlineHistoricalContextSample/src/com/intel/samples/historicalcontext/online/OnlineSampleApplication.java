package com.intel.samples.historicalcontext.online;

import com.intel.common.Settings.Environment;
import com.intel.identity.Auth;

import android.app.Application;

public class OnlineSampleApplication extends Application {
	private Auth mAuth;

	private static OnlineSampleApplication mInstance;

	public void onCreate() {
		super.onCreate();
		mInstance = this;
		mAuth = new Auth(getApplicationContext(), Settings.API_KEY,
				Settings.SECRET, Environment.PROD);

	}

	public static OnlineSampleApplication getInstance() {
		return mInstance;
	}

	public Auth getAuth() {
		return mAuth;
	}

}
