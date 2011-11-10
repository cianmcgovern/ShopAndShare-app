#include "image.h"
#include "filetomat.h"
#include "preprocessimage.h"
#include "bgelim.h"

Image::Image(const char *dir)
{
	image = this->toMat(dir);
	//this->preProcess();
	this->mbgElim();
}

bool Image::preProcess()
{
	PreprocessImage *pm = new PreprocessImage(image);

	image = pm->getImage();
	delete pm;
}

bool Image::mbgElim()
{
	bgElim *bge = new bgElim(&image);

	image = bge->getbgElimImage();
	delete bge;
}

cv::Mat Image::toMat(const char *dir)
{
	fileToMat *fm = new fileToMat(dir);

	return fm->getImage();
	delete fm;
}

cv::Mat Image::getImage()
{
	return image;
}
