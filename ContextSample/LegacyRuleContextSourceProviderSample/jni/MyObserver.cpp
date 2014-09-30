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
 
#include "MyObserver.h"
#include <android/log.h> /* Android Logging */

#  define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)

#define LOG_TAG "MyObserver"

MyObserver::MyObserver(ISourcePublisher* pISourcePublish, const DataSetIdent& dataSet) : m_publisher(pISourcePublish), m_dataSet(dataSet)
{
}

MyObserver::~MyObserver(void)
{
}

void MyObserver::ItemReceived( const Item& newItem )
{
    LOGI("MyObserver::ItemReceived: ");
	LOGI(newItem.value.c_str());
	Item item;
    item.value = newItem.value;
    item.dataSet = m_dataSet;
    if(m_publisher) m_publisher->PublishContextUpdate( m_dataSet, item );
}

void MyObserver::SubscriptionStatus( const DataSetIdent& ident, const CFSubscriptionState& status)
{
	LOGI("MyObserver::SubscriptionStatus: ");
	LOGI(status.message.c_str());
}