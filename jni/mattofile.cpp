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
