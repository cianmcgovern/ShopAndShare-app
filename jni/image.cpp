#include "image.h"
#include "filetomat.h"
#include "preprocessimage.h"

Image::Image(const char *dir)
{
	image = this->toMat(dir);
	this->preProcess();
}

bool Image::preProcess()
{
	PreprocessImage *pm = new PreprocessImage(image);

	image = pm->getImage();
	delete pm;
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
