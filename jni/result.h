#ifndef RESULT_H
#define RESULT_H

#include <string>
#include <vector>

class Result
{
public:
    static Result* getInstance();
    void setResults(std::string[],int);
    std::vector<std::string> getResults();
    ~Result();

private:
    Result();
    static Result* mResult;
    std::vector<std::string> pd;
    Result(Result const&)
    {
    };
    Result& operator=(Result const&)
    {
    };
};

#endif // RESULT_H
