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
