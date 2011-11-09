LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

include /home/cian/Development/OpenCV-2.3.1/share/OpenCV/OpenCV.mk

LOCAL_LDLIBS := -llog
LOCAL_MODULE := ShopAndStore
LOCAL_SRC_FILES := ShopAndStore.cpp preprocessimage.cpp logger.cpp filetomat.cpp image.cpp
LOCAL_CPPFLAGS := -g #debug
include $(BUILD_SHARED_LIBRARY)
