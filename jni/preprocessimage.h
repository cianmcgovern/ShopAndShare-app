#include <opencv2/core/core.hpp>
#include <opencv2/imgproc/imgproc.hpp>

#ifndef PREPROCESSIMAGE_H
#define PREPROCESSIMAGE_H

class PreprocessImage
{
public:
PreprocessImage();
PreprocessImage(cv::Mat);
PreprocessImage(cv::Mat, double, double);
cv::Mat getImage();
int setSharpWeights(double *, double *);
private:
cv::Mat image;
cv::Mat newImage;
double sharp_orig_weight, sharp_new_weight;
int process();
cv::Mat grayConvert();
cv::Mat sharpenImage(cv::Mat *);
};

#endif // PREPROCESSIMAGE_H
