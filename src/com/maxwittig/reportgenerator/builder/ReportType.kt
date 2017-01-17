package com.maxwittig.reportgenerator.builder

import com.maxwittig.reportgenerator.utils.isLastDayOfTheMonth
import com.maxwittig.reportgenerator.utils.isLastDayOfTheWeek


enum class ReportType(val reportTypeName : String)
{

    DAILY("Daily"),  //current day
    WEEKLY("Weekly"), //every week + current day --> don't do, if month over!
    MONTHLY("Monthly"), //every month + current day
    YEARLY("Yearly");
    companion object
    {
        fun getCurrentReportType(weeklyEnabled : Boolean, monthlyEnabled : Boolean, yearlyEnabled : Boolean) : ReportType
        {
            if(yearlyEnabled && isLastDayOfTheWeek())
            {
                return YEARLY
            }
            else
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