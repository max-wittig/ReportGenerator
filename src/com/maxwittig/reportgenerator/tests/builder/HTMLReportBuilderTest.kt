package com.maxwittig.reportgenerator.tests.builder

import com.maxwittig.reportgenerator.builder.HTMLReportBuilder
import com.maxwittig.reportgenerator.builder.ReportType
import org.junit.Test
import java.io.File

class HTMLReportBuilderTest : ReportBuilderTest()
{

    @Test
    fun weeklyReportTest()
    {
        val taskList = getRandomTimekeeperTaskArrayList()
        val now = getRandomDateInTheYear()
        val reportBuilder = HTMLReportBuilder(taskList, ReportType.WEEKLY, todaysDate = now)
        File("data/testResults/weekly.html").writeText(reportBuilder.getReport())
    }

    @Test
    fun monthlyReportTest()
    {
        val taskList = getRandomTimekeeperTaskArrayList()
        val now = getRandomDateInTheYear()
        val reportBuilder = HTMLReportBuilder(taskList, ReportType.MONTHLY, todaysDate = now)
        File("data/testResults/monthly.html").writeText(reportBuilder.getReport())
    }

    @Test
    fun dailyReportTest()
    {
        val taskList = getRandomTimekeeperTaskArrayList()
        val now = getRandomDateInTheYear()
        val reportBuilder = HTMLReportBuilder(taskList, ReportType.DAILY, todaysDate = now)
        File("data/testResults/daily.html").writeText(reportBuilder.getReport())
    }

    @Test
    fun yearlyReportTest()
    {
        val taskList = getRandomTimekeeperTaskArrayList()
        val now = getRandomDateInTheYear()
        val reportBuilder = HTMLReportBuilder(taskList, ReportType.YEARLY, todaysDate = now)
        File("data/testResults/yearly.html").writeText(reportBuilder.getReport())
    }


}