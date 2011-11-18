#include <iostream>
#include <opencv2/highgui/highgui.hpp>
#include "image.h"
#include "constants.h"

int main(int argc, char *argv[])
{
	if (argc <= 1) {
		std::cout << "Usage: ShopAndStore <PATH_TO_IMAGE>" << std::endl;
		exit(1);
	}

	std::cout << argv[1] << std::endl;;
	constants::originalImage = argv[1];
	constants::bgElimImage = "image.tiff";
	Image *image = new Image();
	cv::namedWindow("win");
	cv::imshow("win", image->getImage());
	cv::waitKey(0);
	delete image;
}
