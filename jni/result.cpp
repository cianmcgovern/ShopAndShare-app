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
