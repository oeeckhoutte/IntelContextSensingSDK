#include "SimpleContextSourceThread.h"
#include "cac/types/cfstring.h"
#include <android/log.h> /* Android Logging */

#  define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)

#define LOG_TAG "SimpleContextSourceThread"


SimpleContextSourceThread::SimpleContextSourceThread(
    ContextFramework::ISourcePublisher *pISourcePublish,
	DataSetIdent& dataSet,
	int interval
    ) : m_publisher(pISourcePublish), m_dataSet(dataSet)
{
	time_t time = interval;
	SetTimerEventInterval(interval);
	
}



bool SimpleContextSourceThread::ProcessEvent()
{
	LOGI("SimpleContextSourceThread::ProcessEvent- Publishing item");
	Item item;
    item.value = GetRandomNumber();
    item.dataSet = m_dataSet;
    if(m_publisher) 
	{
		m_publisher->PublishContextUpdate( m_dataSet, item );
		LOGI("SimpleContextSourceThread::ProcessEvent - Published");
	}
	return true;

}

ContextFramework::String SimpleContextSourceThread::GetRandomNumber()
{
    int         random_number;
    char        buffer[1000];

    /* generate a random number: */
    random_number = rand() % 10 + 1;
	sprintf (buffer, "{ \"value\": [{ \"randomNumber\": %i }] }", random_number);
    return ContextFramework::String(buffer);
}

