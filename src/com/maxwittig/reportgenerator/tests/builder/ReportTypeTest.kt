package com.maxwittig.reportgenerator.tests.builder

import com.maxwittig.reportgenerator.builder.ReportType
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate

class ReportTypeTest
{
    @Test
    fun getCurrentReportTypeTest()
    {
        val yearlyDate = LocalDate.of(2016, 12, 31) //end of the year
        val monthlyDate = LocalDate.of(2016, 11, 30) //end of the month
        val sundayDate = LocalDate.of(2016, 12, 25) //sunday
        val dailyDate = LocalDate.of(2016, 1, 5) //nothing special
        assertEquals(ReportType.getCurrentReportType(true, true, true, localDate = yearlyDate), ReportType.YEARLY)
        assertEquals(ReportType.getCurrentReportType(true, true, true, localDate = monthlyDate), ReportType.MONTHLY)
        assertEquals(ReportType.getCurrentReportType(true, true, true, localDate = sundayDate), ReportType.WEEKLY)
        assertEquals(ReportType.getCurrentReportType(true, true, true, localDate = dailyDate), ReportType.DAILY)
    }
}