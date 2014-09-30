package com.intel.sample.legacyrulecontextsource;

import android.content.Context;
import android.os.Bundle;

import com.intel.context.provider.IProviderPublisher;
import com.intel.context.provider.IStateProvider;
import com.intel.context.provider.cxx.cdf.ProviderNativeContextSource;
import com.intel.exception.ContextProviderException;

public class RuleContextSourceProvider implements IStateProvider {

    ProviderNativeContextSource nativeProvider;
    
    /**
     * Provider constructor
     */
    public RuleContextSourceProvider() {
        String libraryName = "libRuleSimpleContextSource.so";
        String identifier = "urn:x-intel:context:type:custom:rulecontext";
        String options = "";
        nativeProvider = new ProviderNativeContextSource(libraryName, 
                identifier, 
                options, 
                RuleContextSourcePublisher.class.getName()); 
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
