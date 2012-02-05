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
#include "filetomat.h"
#include <iostream>
#include <opencv2/highgui/highgui.hpp>
#include <string>
#include "constants.h"

fileToMat::fileToMat()
{
	Logger::getLogger()->write(3, "Inside fileToMat constructor");
	checkFileExists();
	toMat();
}


void fileToMat::checkFileExists()
{
	std::ifstream ifile(constants::originalImage);

	if (ifile) {
		Logger::getLogger()->write(3, "File exists");
		path = constants::originalImage;
	} else {
                Logger::getLogger()->write(6, "Image file doesn't exist EXITING");
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
