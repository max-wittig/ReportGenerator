package com.maxwittig.reportgenerator.builder

import com.maxwittig.reportgenerator.utils.isLastDayOfTheMonth
import com.maxwittig.reportgenerator.utils.isLastDayOfTheWeek
import com.maxwittig.reportgenerator.utils.isLastDayOfTheYear
import java.time.LocalDate


enum class ReportType(val reportTypeName : String)
{

    DAILY("Daily"),  //current day
    WEEKLY("Weekly"), //every week + current day --> don't do, if month over!
    MONTHLY("Monthly"), //every month + current day
    YEARLY("Yearly");
    companion object
    {
        fun getCurrentReportType(weeklyEnabled : Boolean, monthlyEnabled : Boolean, yearlyEnabled : Boolean, localDate: LocalDate = LocalDate.now()) : ReportType
        {
            if(yearlyEnabled && isLastDayOfTheYear(now = localDate))
            {
                return YEARLY
            }
            else
            if(monthlyEnabled && isLastDayOfTheMonth(now = localDate))
            {
                return MONTHLY
            }
            else
            if(weeklyEnabled && isLastDayOfTheWeek(now = localDate))
            {
                return WEEKLY
            }
            return DAILY
        }
    }
}