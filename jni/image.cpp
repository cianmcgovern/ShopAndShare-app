#include "image.h"
#include "filetomat.h"
#include "preprocessimage.h"
#include "bgelim.h"
#include "logger.h"
#include <tesseract/baseapi.h>

Image::Image(const char *dir)
{
	image = this->toMat(dir);
	//this->preProcess();
	this->mbgElim();
	tesseract::TessBaseAPI *tmp = new tesseract::TessBaseAPI();
        Logger::getLogger()->write(3,"Inside image constructor");
	tmp->Init(dir,"NULL");
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
