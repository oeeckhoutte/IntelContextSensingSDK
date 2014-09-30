#ifndef SIMPLE_CONTEXT_SOURCE_THREAD_H
#define SIMPLE_CONTEXT_SOURCE_THREAD_H

#include "threadutil/eventprocessingthread.h"
#include "cac/sourceapi/isourcepublisher.h"


using namespace ContextFramework;

class SimpleContextSourceThread : public CUThread::CEventProcessingThread
{
public:
	SimpleContextSourceThread(
        ContextFramework::ISourcePublisher*,
		DataSetIdent& dataSet,
		int interval
        );


	
protected:
    /* CEventProcessingThread Interface */
    virtual bool ProcessEvent();

private:
	ContextFramework::String GetRandomNumber();
	
	ContextFramework::ISourcePublisher* m_publisher;
	DataSetIdent            m_dataSet;
	
};

#endif //SIMPLE_CONTEXT_SOURCE_THREAD_H
