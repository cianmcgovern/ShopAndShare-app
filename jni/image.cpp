#include "image.h"
#include "filetomat.h"
#include "bgelim.h"
#include "analyse.h"
#include "mattofile.h"
#include "constants.h"

Image::Image()
{
	image = this->toMat();
    this->mbgElim();
	this->toFile(&image);
	this->callAnalyse();
}

void Image::mbgElim()
{
	bgElim *bge = new bgElim(&image);

	image = bge->getbgElimImage();
	delete bge;
}

cv::Mat Image::toMat()
{
	fileToMat *fm = new fileToMat();
    cv::Mat result = fm->getImage();
    delete fm;
    return result;
}

cv::Mat Image::getImage()
{
	return image;
}

void Image::callAnalyse()
{
	analyse *tess = new analyse(constants::bgElimImage);

	delete tess;
}

void Image::toFile(cv::Mat *input)
{
    matToFile *m = new matToFile(input);
    delete m;
}
