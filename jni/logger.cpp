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
#include "logger.h"
#include <iostream>
#include <stdio.h>

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
void Logger::write(int logLevel, std::string input)
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
}

void Logger::write(int logLevel, double input)
{
#ifdef ANDROID
    char buf[10];
    sprintf(buf,"%d",input);
    std::string strtag = "ShopAndStore";
    const char *tag = strtag.c_str();
    __android_log_write(logLevel, tag, buf);

#elif __linux
    output.open("log.txt",std::ios::out | std::ios::app);
    if (logLevel == 3)
            output << "[DEBUG] " << input << '\n';
    else if (logLevel == 6)
            output << "[ERROR] " << input << '\n';
    output.close();
    std::cout << input << std::endl;
#endif
}
