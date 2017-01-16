package com.maxwittig.reportgenerator

import com.maxwittig.reportgenerator.utils.isLastDayOfTheMonth
import com.maxwittig.reportgenerator.utils.isLastDayOfTheWeek


enum class ReportType
{
    DAILY,  //current day
    WEEKLY, //every week + current day --> don't do, if month over!
    MONTHLY; //every month + current day
    companion object
    {
        fun getCurrentReportType(weeklyEnabled : Boolean, monthlyEnabled : Boolean) : ReportType
        {
            if(monthlyEnabled && isLastDayOfTheMonth())
            {
                return MONTHLY
            }
            else
            if(weeklyEnabled && isLastDayOfTheWeek())
            {
                return WEEKLY
            }
            return DAILY
        }
    }
}