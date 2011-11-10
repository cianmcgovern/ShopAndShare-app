#include <iostream>
#include <opencv2/highgui/highgui.hpp>
#include "image.h"

int main(int argc, char *argv[])
{
	if (argc <= 1) {
		std::cout << "Usage: ShopAndStore <PATH_TO_IMAGE>" << std::endl;
		exit(1);
	}

	std::cout << argv[1] << std::endl;
	Image *image = new Image(argv[1]);
	cv::namedWindow("win");
	cv::imshow("win", image->getImage());
	cv::waitKey(0);
	delete image;
}
