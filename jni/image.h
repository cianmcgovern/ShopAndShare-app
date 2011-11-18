#ifndef IMAGE_H
#define IMAGE_H

#include <opencv2/core/core.hpp>

class Image
{
public:
Image();
cv::Mat getImage();
private:
void mbgElim();
cv::Mat image;
cv::Mat toMat();
void callAnalyse();
void toFile(cv::Mat *);
};

#endif // IMAGE_H
