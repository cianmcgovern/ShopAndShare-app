#-------------------------------------------------------------------------------
# Copyright (c) 2012 Cian Mc Govern.
# All rights reserved. This program and the accompanying materials
# are made available under the terms of the GNU Public License v3.0
# which accompanies this distribution, and is available at
# http://www.gnu.org/licenses/gpl.html
# 
# Contributors:
#     Cian Mc Govern - initial API and implementation
#-------------------------------------------------------------------------------
#include "image.h"
#include "filetomat.h"
#include "bgelim.h"
#include "analyse.h"
#include "mattofile.h"
#include "constants.h"

Image::Image()
{
	image = this->toMat();
        //this->mbgElim();
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

	return fm->getImage();
	delete fm;
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
	new matToFile(input);
}
