/*-------------------------------------------------------------------------
 * INTEL CONFIDENTIAL
 *
 * Copyright 2010 Intel Corporation All Rights Reserved.
 *
 * This source code and all documentation related to the source code 
 * ("Material") contains trade secrets and proprietary and confidential 
 * information of Intel and its suppliers and licensors. The Material is 
 * deemed highly confidential, and is protected by worldwide copyright and 
 * trade secret laws and treaty provisions. No part of the Material may be 
 * used, copied, reproduced, modified, published, uploaded, posted, 
 * transmitted, distributed, or disclosed in any way without Intel's prior 
 * express written permission.
 *
 * No license under any patent, copyright, trade secret or other 
 * intellectual property right is granted to or conferred upon you by 
 * disclosure or delivery of the Materials, either expressly, by 
 * implication, inducement, estoppel or otherwise. Any license under such 
 * intellectual property rights must be express and approved by Intel in 
 * writing.
 *-------------------------------------------------------------------------
 */
#include "SimpleContextSource.h"
#include <commonutils/utils/warnings.h>
#include <sstream>
#include <stdio.h>
#include <stdlib.h>

#include <android/log.h> /* Android Logging */

#  define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)

#define LOG_TAG "SimpleContextSource"

using namespace ContextFramework;

CONTEXTFRAMEWORK_IMPLEMENT_ICFOBJECT( SimpleContextSource )

SimpleContextSource::SimpleContextSource() :
    CONTEXTFRAMEWORK_INIT_ICFOBJECT_FIELDS 
{
}

SimpleContextSource::~SimpleContextSource()
{
}

bool SimpleContextSource::ActivateContext(
    const SubscriptionTuplet&   tuplet,
    const DataSetIdent&         dataSet,
    ISourcePublisher*           pISourcePublish,
    CFError**                   error   
    )
{
    LOGI("SimpleContextSource::ActivateContext");
	m_dataSet = dataSet;
    m_pISourcePublish = pISourcePublish;
	m_thread = new SimpleContextSourceThread(pISourcePublish, m_dataSet, 5);	
	m_thread->Start();
    return true;
}

bool SimpleContextSource::DeactivateContext(
    const DataSetIdent& dataSet,
    CFError**           error              
    )
{
    LOGI("SimpleContextSource::DeactivateContext");
	UNREFERENCED_PARAMETER(dataSet);
    UNREFERENCED_PARAMETER(error);
	m_thread->SignalStop( true );
	delete m_thread;
    return true;
}

void SimpleContextSource::UpdateContext()
{
    Item item;
    item.value = GetRandomNumber();
    item.dataSet = m_dataSet;
    if(m_pISourcePublish) m_pISourcePublish->PublishContextUpdate( m_dataSet, item );
}

CFSOURCE_API ContextFramework::ISourceAccess* CreateSourceInstance(
    ContextFramework::ISourceEngineAccess*  engine,
    ContextFramework::CFError**             error
    )
{
    LOGI("Context Source::CreateSourceInstance");
	return new SimpleContextSource();
}

CFSOURCE_API bool FinalizeSource()
{
    return true;
}

/* Additional Method */
ContextFramework::String SimpleContextSource::GetRandomNumber()
{
    int         random_number;
    char        buffer[1000];

    /* generate a random number: */
    random_number = rand() % 10 + 1;
	sprintf (buffer, "{ \"randomNumber\": %i }", random_number);
    return ContextFramework::String(buffer);
}