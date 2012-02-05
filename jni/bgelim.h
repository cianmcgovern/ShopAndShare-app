#ifndef BGELIM_H
#define BGELIM_H

#include <opencv2/core/core.hpp>

class bgElim
{
public:
bgElim(cv::Mat *);
bgElim(cv::Mat *, int);
bgElim(cv::Mat *, int, cv::Vec3b);
cv::Mat getbgElimImage();

private:
cv::Vec3b target;
int minDist;
cv::Mat newImage, image;
void createIterators();
void iteratePixels();
int getDistance(const cv::Vec3b&) const;
void process();
cv::Mat_<cv::Vec3b>::const_iterator it;
cv::Mat_<cv::Vec3b>::const_iterator itend;
cv::Mat_<uchar>::iterator itout;
};

#endif // BGELIM_H
