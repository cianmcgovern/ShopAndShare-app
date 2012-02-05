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
#ifndef LOGGER_H
#define LOGGER_H
#include <iostream>
#include <fstream>
class Logger
{
public:
static Logger *getLogger();
void write(int, std::string);
void write(int, double);
~Logger();

private:
Logger();
Logger(Logger const&)
{
};
Logger& operator=(Logger const&)
{
};
std::ofstream output;
static Logger *logger;
};
#endif // LOGGER_H
