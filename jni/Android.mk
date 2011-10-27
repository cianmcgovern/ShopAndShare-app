LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

include $(OPENCV_PACKAGE_DIR)/share/OpenCV/OpenCV.mk

LOCAL_MODULE := ShopAndStore
LOCAL_SRC_FILES := ShopAndStore.cpp

include $(BUILD_SHARED_LIBRARY)
