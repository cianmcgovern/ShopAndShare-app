/*
 * Interface to JNI
 */

#include <iostream>
#include "com_cianmcgovern_android_ShopAndShare_LoadingPage.h"
#include "image.h"
#include "logger.h"
#include "constants.h"
#include "result.h"
#include <opencv2/highgui/highgui.hpp>
#include <string>
#include <vector>

const char *dir;

char *concat(const char*,const char*,const char*);
int length=0;

int main()
{
    char result[200];
    strcpy(result,dir);
    strcat(result,"/image.jpg");
    constants::originalImage = result;
    Logger::getLogger()->write(3,constants::originalImage);
    char result2[200];
    strcpy(result2,dir);
    strcat(result2,"/bgimage.tiff");

    constants::bgElimImage = result2;
    char result4[200];
    strcpy(result4,dir);
    strcat(result4,"/");
    constants::dataPath = result4;
    char result3[300];
    strcpy(result3,dir);
    strcat(result3,"/tessdata/eng.traineddata");
    constants::dataPathFull = result3;

    Image *im = new Image();

    length=0;
    for(std::vector< std::string >::iterator vProductIt=Result::getInstance()->getResults().begin();vProductIt<Result::getInstance()->getResults().end();vProductIt++){
        length++;
    }
}

char *concat(char* result,const char* one, const char* two)
{
    strcpy(result,one);
    strcat(result,two);
    return result;
}

JNIEXPORT void JNICALL Java_com_cianmcgovern_android_ShopAndShare_LoadingPage_callnativecode(JNIEnv *env, jobject jobj, jstring path)
{
    dir = env->GetStringUTFChars(path, 0);
    main();
}

JNIEXPORT jobjectArray JNICALL Java_com_cianmcgovern_android_ShopAndShare_LoadingPage_getProducts(JNIEnv *env, jobject jobj)
{
    jobjectArray jproducts;
    jproducts = (jobjectArray)env->NewObjectArray(length,env->FindClass("java/lang/String"),env->NewStringUTF(""));

    int x=0;
    for(std::vector<std::string>::iterator vProductIt=Result::getInstance()->getResults().begin();vProductIt<Result::getInstance()->getResults().end();vProductIt++){
        std::string product = *vProductIt;
        env->SetObjectArrayElement(jproducts,x,env->NewStringUTF(product.c_str()));
        x++;
    }

    return jproducts;
}
