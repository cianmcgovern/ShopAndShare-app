#include <iostream>
#include "logger.h"
#include "preprocessimage.h"

PreprocessImage::PreprocessImage()
{
}

PreprocessImage::PreprocessImage(cv::Mat input)
{
	if (input.data) {
		image = input;
		process();
	} else {
		std::cout << "No image specified" << std::endl;
	}
}

int PreprocessImage::process()
{
	cv::Mat *tmp;

	*tmp = image;
	newImage = sharpenImage(tmp);
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

// Sharpens the image using: sharpened_pixel = 5*current-left-right-up-down
cv::Mat PreprocessImage::sharpenImage(cv::Mat *input)
{
	cv::Mat tmp;

	tmp.create(input->size(), input->type());

	for (int j = 1; j < input->rows - 1; j++) {
		const uchar *previous = input->ptr<const uchar>(j - 1); //previous row
		const uchar *current = input->ptr<const uchar>(j);      //current
		const uchar *next = input->ptr<const uchar>(j + 1);     //next row

		uchar *output = tmp.ptr<uchar>(j);                      //output row

		for (int i = 1; i < input->rows - 1; i++)
			// Saturate cast brings pixel values back into 0-255 8 bit range
			*output++ = cv::saturate_cast<uchar>(5 * current[i] - current[i - 1] - current[i + 1] - previous[i] - next[i]);
	}

	tmp.row(0).setTo(cv::Scalar(0));
	tmp.row(tmp.rows - 1).setTo(cv::Scalar(0));
	tmp.col(0).setTo(cv::Scalar(0));
	tmp.col(tmp.cols - 1).setTo(cv::Scalar(0));

	return tmp;
}
