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

#ifndef SIMPLE_CONTEXT_SOURCE_H
#define SIMPLE_CONTEXT_SOURCE_H

#include "core/icfobjectimpl.h"
#include "cac/types/export.h"
#include "cac/types/cfstring.h"
#include "cac/sourceapi/sourcefactory.h"
#include "cac/sourceapi/isourceaccess.h"
#include "SimpleContextSourceThread.h"

using namespace ContextFramework;

class SimpleContextSource : public ISourceAccess
{ 
    CONTEXTFRAMEWORK_DECLARE_ICFOBJECT_FIELDS;

public:
    SimpleContextSource();
    ~SimpleContextSource();
    bool ActivateContext(   const SubscriptionTuplet&, 
                            const DataSetIdent&, 
                            ISourcePublisher*, 
                            CFError**);
    bool DeactivateContext( const DataSetIdent&, 
                            CFError**);
    void UpdateContext();

private:
    ISourcePublisher*       m_pISourcePublish;
    DataSetIdent            m_dataSet;
    int                     m_cnt;
	SimpleContextSourceThread* m_thread;

    ContextFramework::String GetRandomNumber();

}; /* SimpleContextSource */

#endif	/* SIMPLE_CONTEXT_SOURCE_H */
