#ifndef RESULT_H
#define RESULT_H

#include <string>
#include <vector>

class Result
{
public:
    static Result* getResult();
    void setProduct(std::string[],int);
    void setPrice(std::string[],int);
    std::vector<std::string> getPrice();
    std::vector<std::string> getProduct();
    ~Result();

private:
    Result();
    static Result* mResult;
    std::vector<std::string> pd;
    std::vector<std::string> pr;
    Result(Result const&)
    {
    };
    Result& operator=(Result const&)
    {
    };
};

#endif // RESULT_H
