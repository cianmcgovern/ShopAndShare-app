#ifndef FILETOMAT_H
#define FILETOMAT_H
#include <opencv2/core/core.hpp>

#include "logger.h"

class fileToMat
{
public:
fileToMat(const char *);
cv::Mat getImage();

private:
const char *path;
cv::Mat image;
void checkFileExists(const char *);
void toMat();
};

#endif // FILETOMAT_H
