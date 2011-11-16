LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_MODULE := lept
LOCAL_SRC_FILES := liblept.so
include $(PREBUILT_SHARED_LIBRARY)
