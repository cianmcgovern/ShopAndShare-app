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
    char result[100];
    strcpy(result,dir);
    strcat(result,"/image.jpg");
    constants::originalImage = result;
    Logger::getLogger()->write(3,constants::originalImage);
    char result2[100];
    strcpy(result2,dir);
    strcat(result2,"/bgimage.tiff");

    constants::bgElimImage = result2;
    char result4[100];
    strcpy(result4,dir);
    strcat(result4,"/");
    constants::dataPath = result4;
    char result3[100];
    strcpy(result3,dir);
    strcat(result3,"/tessdata/eng.traineddata");
    constants::dataPathFull = result3;

    Image *im = new Image();
    delete im;

    length=1;

    //EXPERIMENTAL
    std::ofstream results;
    results.open("/data/data/com.cianmcgovern.android.ShopAndShare/files/results",std::ios::out | std::ios::app);
    for(std::vector< std::string >::iterator vProductIt=Result::getInstance()->getResults().begin();vProductIt<Result::getInstance()->getResults().end();vProductIt++){
        std::string x = *vProductIt;
        results << x.c_str() << '\n';
        length++;
    }
    results.close();
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
    Logger::getLogger()->write(3,"getProducts called");
    jobjectArray jproducts;
    jproducts = (jobjectArray)env->NewObjectArray(length,env->FindClass("java/lang/String"),0);

    int x=0;
    std::string product;
    std::vector<std::string>::iterator vProductIt=Result::getInstance()->getResults().begin();

    Logger::getLogger()->write(3,"Beginning loop");
    for(vProductIt;vProductIt<Result::getInstance()->getResults().end();vProductIt++){
        Logger::getLogger()->write(3,"In loop");
        product = *vProductIt;
        env->SetObjectArrayElement(jproducts,x,env->NewStringUTF(product.c_str()));
        x++;
    }
    Logger::getLogger()->write(3,"Outside of loop");
    return jproducts;
}
