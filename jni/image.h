#ifndef IMAGE_H
#define IMAGE_H

#include <opencv2/core/core.hpp>

class Image
{
public:
Image(const char *);
cv::Mat getImage();
private:
bool preProcess();
cv::Mat image;
cv::Mat toMat(const char *);
};

#endif // IMAGE_H
