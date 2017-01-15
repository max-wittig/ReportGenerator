package com.maxwittig.reportgenerator.builder

import com.maxwittig.reportgenerator.ReportType
import com.maxwittig.reportgenerator.models.TimekeeperTask
import com.maxwittig.reportgenerator.utils.getTimeStringFromSeconds
import com.maxwittig.reportgenerator.utils.isSameDay
import com.maxwittig.reportgenerator.utils.isSameMonth
import java.util.*


abstract class ReportBuilder(private val timekeeperTasks : ArrayList<TimekeeperTask>, private val reportType: ReportType)
{
    protected val todaysDate = Date()
    protected val todayTasks = ArrayList<TimekeeperTask>()
    protected val monthlyTasks = ArrayList<TimekeeperTask>()
    protected val weeklyTasks = ArrayList<TimekeeperTask>()

    init
    {
        parseTodayTasks()
        if(reportType == ReportType.MONTHLY)
        {
            parseMonthlyTasks()
        }
        else
        if(reportType == ReportType.WEEKLY)
        {
            parseWeeklyTasks()
        }
    }

    private fun parseWeeklyTasks()
    {
        for(task in timekeeperTasks)
        {
            if(isSameMonth(todaysDate, task.startTime))
                monthlyTasks.add(task)
        }
    }

    private fun parseMonthlyTasks()
    {
        for(task in timekeeperTasks)
        {
            if(isSameMonth(todaysDate, task.startTime))
                monthlyTasks.add(task)
        }
    }

    private fun parseTodayTasks()
    {
        for(task in timekeeperTasks)
        {
            if(isSameDay(todaysDate, task.startTime))
                todayTasks.add(task)
        }
    }

    protected fun getTotalTimeOfTasksToday() : String
    {
        return getTotalTimeOfTasks(todayTasks)
    }

    protected fun getTotalTimeOfTasksMonthly() : String
    {
        return getTotalTimeOfTasks(monthlyTasks)
    }

    protected fun getTotalTimeOfTasksWeekly() : String
    {
        return getTotalTimeOfTasks(weeklyTasks)
    }

    private fun getTotalTimeOfTasks(tasks: ArrayList<TimekeeperTask>) : String
    {
        var totalTime : Long = 0
        for(task in tasks)
        {
            totalTime += task.duration
        }
        return getTimeStringFromSeconds(totalTime)
    }

    protected fun getMonthlyProjectTimeHashMap() : HashMap<String, Long>
    {
        return getProjectTimeHashMap(monthlyTasks)
    }

    protected fun getWeeklyProjectTimeHashMap() : HashMap<String, Long>
    {
        return getProjectTimeHashMap(weeklyTasks)
    }

    protected fun getDailyProjectTimeHashMap() : HashMap<String, Long>
    {
        return getProjectTimeHashMap(todayTasks)
    }

    private fun getProjectTimeHashMap(tasks : ArrayList<TimekeeperTask>) : HashMap<String, Long>
    {
        val hashMap = HashMap<String,Long>()
        for(task in tasks)
        {
            hashMap.putIfAbsent(task.projectName, 0)
            hashMap.put(task.projectName, hashMap.get(task.projectName)!! + task.duration)
        }

        return hashMap
    }

    abstract fun getReport() : String

    fun shouldSendMail() : Boolean
    {
        if(todayTasks.isEmpty() && reportType == ReportType.DAILY)
        {
            return false
        }

        if(weeklyTasks.isEmpty() && reportType == ReportType.WEEKLY)
        {
            return false
        }

        if(monthlyTasks.isEmpty() && reportType == ReportType.MONTHLY)
        {
            return false
        }
        return true
    }
}