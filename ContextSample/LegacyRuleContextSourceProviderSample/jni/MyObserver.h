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
 
#include "cac/clientapi/iitemobserver.h"
#include "cac/sourceapi/isourcepublisher.h"

using namespace ContextFramework; 

class MyObserver :  public IItemObserver
{
public:

    MyObserver(ISourcePublisher* pISourcePublish, const DataSetIdent& dataSet);
    ~MyObserver(void);

    virtual void ItemReceived( const Item& newItem );
	virtual void SubscriptionStatus( const DataSetIdent& ident, const CFSubscriptionState& status);
private:
	ISourcePublisher* m_publisher;
	DataSetIdent m_dataSet;
};
