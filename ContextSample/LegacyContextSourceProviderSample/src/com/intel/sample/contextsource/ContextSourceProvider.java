package com.intel.sample.contextsource;

import android.content.Context;
import android.os.Bundle;

import com.intel.context.provider.IProviderPublisher;
import com.intel.context.provider.IStateProvider;
import com.intel.context.provider.cxx.cdf.ProviderNativeContextSource;
import com.intel.exception.ContextProviderException;

public class ContextSourceProvider implements IStateProvider {

    ProviderNativeContextSource nativeProvider;
    
    /**
     * Provider constructor
     */
    public ContextSourceProvider() {
        String libraryName = "libSimpleContextSource.so";
        String identifier = "urn:x-intel:context:type:custom:source";
        String options = "";
        nativeProvider = new ProviderNativeContextSource(libraryName, 
                identifier, 
                options, 
                ContextSourcePublisher.class.getName()); 
    }

    @Override
    public void start(Context context, IProviderPublisher publisher,
            Bundle settings) throws ContextProviderException {
        nativeProvider.start(context, publisher, settings);
    }

    @Override
    public void stop() {
        nativeProvider.stop();
    }

}
