#ifndef MATTOFILE_H
#define MATTOFILE_H

#include <opencv2/core/core.hpp>
#include <opencv2/imgproc/imgproc.hpp>
#include <opencv2/highgui/highgui.hpp>

class matToFile
{
public:
matToFile(cv::Mat *);

private:
cv::Mat image;
void writeMat();
};

#endif // MATTOFILE_H
