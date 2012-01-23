#include "result.h"
#include <stdio.h>
#include <iostream>

Result *Result::mResult=NULL;

Result::Result()
{
}

Result *Result::getInstance()
{
    if(!mResult){
        mResult=new Result();
        return mResult;
    }
    else{
        return mResult;
    }
}

void Result::setResults(std::string itemIn[],int length)
{
    // Empty contents of results iterator before putting new items in
    if(!pd.empty())
        pd.clear();

    for(int i=0;i<length;i++){
        pd.push_back(itemIn[i]);
    }
}

std::vector<std::string> Result::getResults()
{
    return this->pd;
}
