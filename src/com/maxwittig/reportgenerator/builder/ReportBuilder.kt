package com.maxwittig.reportgenerator.builder

import com.maxwittig.reportgenerator.ReportType
import com.maxwittig.reportgenerator.models.TimekeeperTask
import com.maxwittig.reportgenerator.utils.getTimeStringFromSeconds
import com.maxwittig.reportgenerator.utils.isSameDay
import com.maxwittig.reportgenerator.utils.isSameMonth
import com.maxwittig.reportgenerator.utils.isSameWeek
import java.util.*


abstract class ReportBuilder(private val timekeeperTasks : ArrayList<TimekeeperTask>, private val reportType: ReportType, protected val todaysDate : Date = Date())
{
    protected var todayTasks = ArrayList<TimekeeperTask>()
    protected var monthlyTasks = ArrayList<TimekeeperTask>()
    protected var weeklyTasks = ArrayList<TimekeeperTask>()

    init
    {
        todayTasks = getParsedTodayTasks()
        if(reportType == ReportType.MONTHLY)
        {
            monthlyTasks = getParsedMonthlyTasks()
        }
        else
        if(reportType == ReportType.WEEKLY)
        {
            weeklyTasks = getParsedWeeklyTasks()
        }
    }

    private fun getParsedWeeklyTasks() : ArrayList<TimekeeperTask>
    {
        val tasks = ArrayList<TimekeeperTask>()
        for(task in timekeeperTasks)
        {
            if(isSameWeek(todaysDate, task.startTime))
                tasks.add(task)
        }
        return tasks
    }

    private fun getParsedMonthlyTasks() : ArrayList<TimekeeperTask>
    {
        var tasks = ArrayList<TimekeeperTask>()
        for(task in timekeeperTasks)
        {
            if(isSameMonth(todaysDate, task.startTime))
            {
                tasks.add(task)
            }
        }
        //sort after duration and only show longest 10
        tasks.sort({ task2, task1 -> task1.duration.compareTo(task2.duration)})
        if(tasks.size > 10)
        {
           tasks = ArrayList<TimekeeperTask>(tasks.subList(0, 10))
        }

        return tasks
    }

    private fun getParsedTodayTasks() : ArrayList<TimekeeperTask>
    {
        val tasks = ArrayList<TimekeeperTask>()
        for(task in timekeeperTasks)
        {
            if(isSameDay(todaysDate, task.startTime))
                tasks.add(task)
        }
        return tasks
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