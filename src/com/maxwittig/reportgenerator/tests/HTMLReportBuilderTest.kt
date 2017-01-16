package com.maxwittig.reportgenerator.tests

import com.maxwittig.reportgenerator.ReportType
import com.maxwittig.reportgenerator.builder.HTMLReportBuilder
import com.maxwittig.reportgenerator.models.TimekeeperTask
import org.junit.Test
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ThreadLocalRandom

class HTMLReportBuilderTest
{
    private val wordList : List<String> = File("data/wordlist.txt").readText().split("\n")

    private fun getRandomTimekeeperTaskArrayList() : ArrayList<TimekeeperTask>
    {
        val completeTaskList = ArrayList<TimekeeperTask>()
        var i = 0
        while (i < 10000)
        {
            i++
            val startDate = getRandomDateInTheYear()
            val duration = getRandomDuration()
            val endDate = Date(startDate.time + duration)
            val task = TimekeeperTask(getRandomWordFromList(), getRandomWordFromList(), startDate, endDate, duration)
            completeTaskList.add(task)
        }
        completeTaskList.sort { task1, task2 -> task1.startTime.compareTo(task2.startTime) }
        return completeTaskList
    }

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

    private fun getRandomWordFromList() : String
    {
        return wordList[Random().nextInt(wordList.size)]
    }

    private fun getRandomDateInTheYear() : Date
    {
        val year = "2017"
        val format = SimpleDateFormat("yyyy")
        val yearDate = format.parse(year)
        val yearStartSeconds = yearDate.time
        val yearLengthMilliSeconds = 31557600000
        val randomDateInMilliseconds = (Random().nextDouble()*(yearLengthMilliSeconds)).toLong()
        return Date(yearStartSeconds+randomDateInMilliseconds)
    }

    private fun getRandomDuration() : Long
    {
        return ThreadLocalRandom.current().nextLong(1, 86400000)
    }
}