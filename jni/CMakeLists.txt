project( testing )
find_package( OpenCV REQUIRED )
add_executable( ShopAndStore testing.cpp preprocessimage.cpp preprocessimage.h logger.h logger.cpp filetomat.cpp image.cpp )
target_link_libraries( ShopAndStore ${OpenCV_LIBS} )
