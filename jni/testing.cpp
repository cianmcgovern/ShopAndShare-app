#include <iostream>
#include "preprocessimage.h"
#include <opencv2/highgui/highgui.hpp>

int main()
{
    double orig = 3.8;
    double neww = -0.4;
    cv::Mat image;
    image=cv::imread("test.jpg");

    PreprocessImage ppimage(&image);
    //ppimage.setSharpWeights(&orig,&neww);
    ppimage.process();
    cv::imwrite("newtest.jpg",ppimage.getImage());
}
