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
