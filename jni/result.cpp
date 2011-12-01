#include "result.h"
#include <stdio.h>
#include <iostream>

Result *Result::mResult=NULL;

Result::Result()
{
}

Result *Result::getResult()
{
    if(!mResult){
        mResult=new Result();
        return mResult;
    }
    else{
        return mResult;
    }
}

void Result::setProduct(std::string itemIn[],int length)
{
    for(int i=0;i<length;i++){
        pd.push_back(itemIn[i]);
    }
}

void Result::setPrice(std::string mPrice[],int length)
{
    for(int i=0;i<length;i++){
        pr.push_back(mPrice[i]);
    }
}

std::vector<std::string> Result::getPrice()
{
    return this->pr;
}

std::vector<std::string> Result::getProduct()
{
    return this->pd;
}
