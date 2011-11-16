LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_MODULE := tess
LOCAL_SRC_FILES := libtess.so
include $(PREBUILT_SHARED_LIBRARY)
