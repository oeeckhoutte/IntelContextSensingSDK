package com.intel.samples.contextsensingapiflow.listener;


import android.util.Log;
import android.content.Context;
import android.widget.Toast;

import com.intel.context.error.ContextError;
import com.intel.context.item.AudioClassification;
import com.intel.context.item.Item;
import com.intel.samples.contextsensingapiflow.ContextSensingApiFlowSampleActivity;
import com.intel.samples.contextsensingapiflow.ContextSensingApiFlowSampleApplication;

public class AudioClassificationListener implements IApplicationListener {

    private final String LOG_TAG = ContextSensingApiFlowSampleApplication.class.getName();
    private Context mContext;
    private Item mLastKnownItem;
    
    public AudioClassificationListener(Context context) {
        mContext = context;
    }
    
    public void onReceive(Item state) {
        if (state instanceof AudioClassification) {
            // Cast the incoming context state to an ActivityRecognition Item.
            AudioClassification audioRecognitionState = (AudioClassification) state;
            mLastKnownItem = audioRecognitionState;
            
            StringBuilder sb = new StringBuilder();
            
            sb.append("Speech: " + audioRecognitionState.getSpeech() + "% chance\n");
            sb.append("CrowdChatter: " + audioRecognitionState.getCrowdChatter() + "% chance\n");
            sb.append("Music: " + audioRecognitionState.getMusic() + "% chance\n");
            sb.append("Motion: " + audioRecognitionState.getMotion() + "% chance\n");
            sb.append("Mechanical: " + audioRecognitionState.getMechanical() + "% chance\n");
                
            sb.append(audioRecognitionState.getDateTime());
            Log.d(LOG_TAG, "New Audio State: " + sb.toString());
            final String audioText = sb.toString();
            ContextSensingApiFlowSampleActivity.mAudioText.post(new Runnable() {
                
                @Override
                public void run() {
                    ContextSensingApiFlowSampleActivity.mAudioText.setText(audioText);
                    
                }
            });
        }
    }

    public void onError(ContextError error) {
        Toast.makeText(mContext,
                "Listener Status: " + error.getMessage(), Toast.LENGTH_LONG).show();
        Log.e(LOG_TAG, "Error: " + error.getMessage());
    }

    public void setLastKnownItem(Item item) {
        if (item == null ) {
            mLastKnownItem = null;
        } else if (item instanceof AudioClassification) {
            mLastKnownItem = item;
        }
    }

    public Item getLastKnownItem() {
        return mLastKnownItem;
    }
}
