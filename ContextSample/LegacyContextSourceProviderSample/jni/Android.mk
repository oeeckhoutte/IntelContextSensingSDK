LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)
TARGET_PLATFORM=android-8
LOCAL_C_INCLUDES :=  $(CAC_ROOT)/include/commonutils $(CAC_ROOT)/include/exceptionsnotallowed $(CAC_ROOT)/include/ 
LOCAL_MODULE := libSimpleContextSource
LOCAL_SRC_FILES := SimpleContextSource.cpp  SimpleContextSourceThread.cpp
LOCAL_LDLIBS := -llog
LOCAL_SHARED_LIBRARIES := libcu libcfcommon
LOCAL_STATIC_LIBRARIES := 
include $(BUILD_SHARED_LIBRARY)

include $(CAC_ROOT)/lib/android/commonlibs/cfcommon/Android.mk $(CAC_ROOT)/lib/android/commonlibs/cu/Android.mk