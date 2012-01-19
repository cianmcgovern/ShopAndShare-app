#include <tesseract/baseapi.h>
#include <tesseract/imgs.h>
#include <tiffio.h>
#include <vector>
#include <stdlib.h>
#include <sstream>
#include <cctype>
#include <string>
#include <iostream>
#include "analyse.h"
#include "logger.h"
#include "constants.h"
#include "result.h"

void read_tiff_image(TIFF *tif, IMAGE *image)
{
	tdata_t buf;
	uint32 image_width, image_height;
	uint16 photometric;
	inT16 bpp;
	inT16 samples_per_pixel = 0;

	TIFFGetField(tif, TIFFTAG_IMAGEWIDTH, &image_width);
	TIFFGetField(tif, TIFFTAG_IMAGELENGTH, &image_height);
	if (!TIFFGetField(tif, TIFFTAG_BITSPERSAMPLE, &bpp))
		bpp = 1;  // Binary is default if no value provided.
	TIFFGetField(tif, TIFFTAG_SAMPLESPERPIXEL, &samples_per_pixel);
	TIFFGetField(tif, TIFFTAG_PHOTOMETRIC, &photometric);
	if (samples_per_pixel > 1)
		bpp *= samples_per_pixel;
	// Tesseract's internal representation is 0-is-black,
	// so if the photometric is 1 (min is black) then high-valued pixels
	// are 1 (white), otherwise they are 0 (black).
	uinT8 high_value = photometric == 1;
	image->create(image_width, image_height, bpp);
	IMAGELINE line;
	line.init(image_width);

	buf = _TIFFmalloc(TIFFScanlineSize(tif));
	int bytes_per_line = (image_width * bpp + 7) / 8;
	uinT8 *dest_buf = image->get_buffer();
	// This will go badly wrong with one of the more exotic tiff formats,
	// but the majority will work OK.
	for (int y = 0; y < image_height; ++y) {
		TIFFReadScanline(tif, buf, y);
		memcpy(dest_buf, buf, bytes_per_line);
		dest_buf += bytes_per_line;
	}
	if (high_value == 0)
		invert_image(image);
	_TIFFfree(buf);
}

double strToDouble(std::string input)
{
    std::istringstream iss(input);
    iss.clear();
    double num;
    iss >> num;
    Logger::getLogger()->write(3,num);
    return num;
}

void analyse::splitTessResult(std::string result)
{
    int newLine;
    std::vector< std::string > a;
    std::vector< std::string >::iterator x;

    // Make sure vector a is empty
    int counter=0;
    x=a.begin();
    while(x!=a.end()){
        a.erase(x);
        x++;
    }


    Logger::getLogger()->write(3, "Putting tesseract results into iterator");

    // Add each line to a
    while(true){
        newLine = result.find("\n");
        if(newLine<1){
            break;
        }
        a.push_back(result.substr(0,newLine));
        result=result.substr(newLine+1,result.length());
    }

    counter=0;
    x=a.begin();
    while(x!=a.end()){
        Logger::getLogger()->write(3,*x);
        counter++;
        x++;
    }

    Logger::getLogger()->write(3,"Placing results into arrays");
    int ctr = 0;
    std::string lines[counter];
    x=a.begin();
    while(x!=a.end()){
        lines[ctr]=*x;
        ctr++;
        x++;
    }

    // Store results in result object
    Result::getInstance()->setResults(lines,ctr);
}


analyse::analyse(const char *dir)
{
	dir = imageLoc;
	checkDataFileExists();
	callInit();
}

int analyse::callInit()
{
	tesseract::TessBaseAPI tmp;

	Logger::getLogger()->write(3, "After tesseract api creation");
	if (tmp.Init(constants::dataPath, "eng") != 0) {
		Logger::getLogger()->write(6, "Tesseract API not initialised");
		exit(1);
	}
	Logger::getLogger()->write(3, "Setting tesseract page mode");
        tmp.SetPageSegMode(tesseract::PSM_SINGLE_COLUMN);
        tmp.SetVariable("tessedit_char_whitelist",".0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ/ ");

	this->getImage();

	int bytes_per_line = check_legal_image_size(image.get_xsize(),
						    image.get_ysize(),
						    image.get_bpp());
	tmp.SetImage(image.get_buffer(), image.get_xsize(), image.get_ysize(), image.get_bpp() / 8, bytes_per_line);
        std::string result=tmp.GetUTF8Text();
        Logger::getLogger()->write(3, "Tesseract result:");
        Logger::getLogger()->write(3, result);
        if(result.find_last_of(".")<0){
            Logger::getLogger()->write(6,"Couldn't find decimal place in any string in Tesseract Result");
            exit(1);
        }
        this->splitTessResult(result);
	return 0;
}

void analyse::checkDataFileExists()
{
	std::ifstream ifile(constants::dataPathFull);
	if (ifile) {
		Logger::getLogger()->write(3, "Tesseract data file exists");
	} else {
		Logger::getLogger()->write(6, "Tesseract data file doesn't exist EXITING");
		exit(1);
	}
}

void analyse::getImage()
{
	TIFF *archive = NULL;
        archive = TIFFOpen(constants::bgElimImage, "r");
	read_tiff_image(archive, &image);
	TIFFClose(archive);
}
