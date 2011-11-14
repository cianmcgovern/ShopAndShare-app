#ifdef ANDROID
/*
 * Interface to JNI
 */

#include <iostream>
#include "com_cianmcgovern_android_ShopAndStore_ShopAndStore.h"
#include "image.h"
#include <opencv2/highgui/highgui.hpp>

const char *dir;

int main()
{
	Image *im = new Image(dir);
}

JNIEXPORT void JNICALL Java_com_cianmcgovern_android_ShopAndStore_ShopAndStore_callnativecode(JNIEnv *env, jobject jobj, jstring path)
{
	dir = env->GetStringUTFChars(path, 0);
	main();
}
#endif
