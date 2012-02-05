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
#ifndef IMAGE_H
#define IMAGE_H

#include <opencv2/core/core.hpp>

class Image
{
public:
Image();
cv::Mat getImage();
private:
void mbgElim();
cv::Mat image;
cv::Mat toMat();
void callAnalyse();
void toFile(cv::Mat *);
};

#endif // IMAGE_H
