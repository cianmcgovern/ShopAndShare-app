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
#ifndef ANALYSE_H
#define ANALYSE_H

#include <tesseract/baseapi.h>
#include <tesseract/imgs.h>
#include <tiffio.h>
#include <vector>
#include <string>

class analyse
{
public:
analyse(const char *);

private:
IMAGE image;
const char *imageLoc;
int callInit();
void checkDataFileExists();
void getImage();
void splitTessResult(std::string);
};

#endif // ANALYSE_H
