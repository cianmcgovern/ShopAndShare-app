#include "mattofile.h"
#include "logger.h"
#include "constants.h"

matToFile::matToFile(cv::Mat *input)
{
	if (!input->data) {
		Logger::getLogger()->write(6, "Image data sent to matToFile is empty, EXITING!!!");
		exit(1);
	}
	this->image = *input;
	this->writeMat();
}

void matToFile::writeMat()
{
	cv::imwrite(constants::bgElimImage, this->image);
}
