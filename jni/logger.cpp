#include "logger.h"
#include <iostream>
#ifdef ANDROID
#include <android/log.h>
#endif

Logger *Logger::logger = NULL;

Logger::Logger()
{
#ifdef __linux
	output.open("log.txt", std::ios::out | std::ios::app);
	output << "------------------------------------------" << '\n';
	output.close();
#endif
}

Logger *Logger::getLogger()
{
	if (!logger) {
		logger = new Logger();
		return logger;
	} else {
		return logger;
	}
}
// Log level 3 is debug, 6 is fatal
int Logger::write(int logLevel, std::string input)
{
#ifdef ANDROID
	const char *x = input.c_str();
	std::string strtag = "ShopAndStore";
	const char *tag = strtag.c_str();
	__android_log_write(logLevel, tag, x);
#elif __linux
	output.open("log.txt", std::ios::out | std::ios::app);
	if (logLevel == 3)
		output << "[DEBUG] " + input << '\n';
	else if (logLevel == 6)
		output << "[ERROR] " + input << '\n';
	output.close();
	std::cout << input << std::endl;
#endif
	return 0;
}
