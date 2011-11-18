/*
 * Interface to JNI
 */

#include <iostream>
#include "com_cianmcgovern_android_ShopAndStore_ShopAndStore.h"
#include "image.h"
#include "logger.h"
#include "constants.h"
#include <opencv2/highgui/highgui.hpp>

const char *dir;

int main()
{
	char result[1000];

	strcpy(result, dir);
	strcat(result, "/image.tiff");
	constants::originalImage = result;
	char otherresult[1000];
	strcpy(otherresult, dir);
	strcat(otherresult, "/image.tiff");
	constants::bgElimImage = otherresult;
	Image *im = new Image();
}

JNIEXPORT void JNICALL Java_com_cianmcgovern_android_ShopAndStore_ShopAndStore_callnativecode(JNIEnv *env, jobject jobj, jstring path)
{
	dir = env->GetStringUTFChars(path, 0);
	main();
}
