#ifndef LOGGER_H
#define LOGGER_H
#include <iostream>
#include <fstream>
class Logger
{
public:
static Logger *getLogger();
int write(int, std::string);
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
