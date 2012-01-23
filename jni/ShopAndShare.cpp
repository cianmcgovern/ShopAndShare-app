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

    std::vector<std::string> finalResults = Result::getInstance()->getResults();

    //EXPERIMENTAL
    std::ofstream results;
    results.open("/data/data/com.cianmcgovern.android.ShopAndShare/files/results",std::ios::out | std::ios::app);
    for(std::vector< std::string >::iterator vProductIt=finalResults.begin();vProductIt<finalResults.end();vProductIt++){
        std::string x = *vProductIt;
        results << x.c_str() << '\n';
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
