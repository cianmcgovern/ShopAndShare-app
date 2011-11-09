#include "filetomat.h"
#include <iostream>
#include <opencv2/highgui/highgui.hpp>
#include <string>

fileToMat::fileToMat(const char *input)
{
	Logger::getLogger()->write(3, "Inside fileToMat constructor");
	checkFileExists(input);
	toMat();
}


void fileToMat::checkFileExists(const char *input)
{
	std::ifstream ifile(input);

	if (ifile) {
		Logger::getLogger()->write(3, "File exists");
		path = input;
	} else {
		Logger::getLogger()->write(6, "File doesn't exist EXITING");
		exit(1);
	}
}

void fileToMat::toMat()
{
	if (cv::imread(path).data) {
		Logger::getLogger()->write(3, "Image converted to Mat successfully");
		image = cv::imread(path);
	} else {
		Logger::getLogger()->write(6, "Image not converted to Mat EXITING!!!");
		exit(1);
	}
}

cv::Mat fileToMat::getImage()
{
	return image;
}
