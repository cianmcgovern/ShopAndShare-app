CC=g++
LIBS = -lopencv_core -lopencv_imgproc -lopencv_calib3d -lopencv_video -lopencv_features2d -lopencv_ml -lopencv_highgui -lopencv_objdetect -lopencv_contrib -lopencv_legacy -ltesseract_api -ltiff
FLAGS = -O0 -Wall -ansi -pedantic
SRC = testing.cpp bgelim.cpp filetomat.cpp image.cpp logger.cpp analyse.cpp mattofile.cpp constants.cpp
OBJS = testing.o bgelim.o filetomat.o image.o logger.o analyse.o mattofile.o constants.cpp
EXEC = ShopAndStore

all:	$(EXEC)

$(EXEC):	$(OBJS)
		$(CC) $(FLAGS) $(OBJS) -o $(EXEC) $(LIBS)

.cpp.o:
	$(CC) -c $(FLAGS) $*.cpp

clean:
		rm -rf *.o *.gch $(EXEC)
