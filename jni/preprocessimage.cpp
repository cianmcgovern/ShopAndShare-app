#include <iostream>
#include "logger.h"
#include "preprocessimage.h"

PreprocessImage::PreprocessImage()
{
}

PreprocessImage::PreprocessImage(cv::Mat input)
{
	if (input.data) {
		sharp_orig_weight = 1.6;
		sharp_new_weight = -0.6;
		image = input;
		process();
	} else {
		std::cout << "No image specified" << std::endl;
	}
}

PreprocessImage::PreprocessImage(cv::Mat input, double orig, double neww)
{
	if (input.data && orig != 0 && neww != 0) {
		sharp_orig_weight = orig;
		sharp_new_weight = neww;
		image = input;
		process();
	} else {
		std::cout << "No image specified" << std::endl;
	}
}

int PreprocessImage::process()
{
	cv::Mat tmp;

	tmp = grayConvert();
	newImage = sharpenImage(&tmp);
	if (!newImage.data)
		return 1;
	else
		return 0;
}

cv::Mat PreprocessImage::grayConvert()
{
	cv::Mat tmp;

	cv::cvtColor(image, tmp, CV_RGB2GRAY);
	Logger::getLogger()->write(3, "Converted to grey");
	return tmp;
}

cv::Mat PreprocessImage::getImage()
{
	return newImage;
}

cv::Mat PreprocessImage::sharpenImage(cv::Mat *input)
{
	cv::Mat tmp;

	cv::GaussianBlur(*input, tmp, cv::Size(0, 0), 3);
	cv::addWeighted(*input, sharp_orig_weight, tmp, sharp_new_weight, 0, tmp);
	return tmp;
}

int PreprocessImage::setSharpWeights(double *orig, double *neww)
{
	if (orig == 0 || neww == 0)
		return 1;

	sharp_orig_weight = *orig;
	sharp_new_weight = *neww;

	return 0;
}
