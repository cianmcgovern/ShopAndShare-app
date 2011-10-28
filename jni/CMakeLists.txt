project( testing )
find_package( OpenCV REQUIRED )
add_executable( testing testing.cpp preprocessimage.cpp preprocessimage.h )
target_link_libraries( testing ${OpenCV_LIBS} )
