package com.maxwittig.reportgenerator.builder

import com.maxwittig.reportgenerator.builder.ReportType
import com.maxwittig.reportgenerator.models.TimekeeperTask
import com.maxwittig.reportgenerator.utils.getTimeStringFromMilliSeconds
import com.maxwittig.reportgenerator.utils.isSameDay
import com.maxwittig.reportgenerator.utils.isSameMonth
import com.maxwittig.reportgenerator.utils.isSameWeek
import java.text.SimpleDateFormat
import java.time.DayOfWeek
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
            {
                tasks.add(task)
            }
        }

        return getLongestTaskPerDayInTheWeek(tasks)
    }

    private fun getLongestTaskPerDayInTheWeek(tasks: ArrayList<TimekeeperTask>) : ArrayList<TimekeeperTask>
    {
        for(currentDay in DayOfWeek.values())
        {
            var longestTask : TimekeeperTask? = null
            val dayTaskList = getTasksPerDay(tasks, currentDay)
            val longestDuration = -1
            for(currentTask in dayTaskList)
            {
                if(currentTask.duration > longestDuration)
                {
                    longestTask = currentTask
                }
            }

            if(longestTask != null)
            {
                longestTask.shownInTaskList = true
            }
        }
        return tasks
    }

    private fun getTasksPerDay(tasks: ArrayList<TimekeeperTask>, dayOfWeek: DayOfWeek) : ArrayList<TimekeeperTask>
    {
        val taskList = ArrayList<TimekeeperTask>()
        for(currentTask in tasks)
        {
            //if same day
            if(SimpleDateFormat("EEEE").format(currentTask.startTime).toUpperCase() == dayOfWeek.toString().toUpperCase())
            {
                taskList.add(currentTask)
            }
        }
        taskList.sort({ task1, task2 ->  task1.startTime.compareTo(task2.startTime)})
        return taskList
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
        tasks.sort{ task2, task1 -> task1.duration.compareTo(task2.duration)}
        val taskLimit = 10
        if(tasks.size > taskLimit)
        {
            for(currentLongestTask in tasks)
            {
                if(tasks.indexOf(currentLongestTask)+1 >= taskLimit)
                    break
                currentLongestTask.shownInTaskList = true

            }
        }

        tasks.sort { task1, task2 -> task1.startTime.compareTo(task2.startTime) }
        return tasks
    }

    private fun getParsedTodayTasks() : ArrayList<TimekeeperTask>
    {
        val tasks = ArrayList<TimekeeperTask>()
        for(task in timekeeperTasks)
        {
            if(isSameDay(todaysDate, task.startTime))
            {
                task.shownInTaskList = true
                tasks.add(task)
            }
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
        return getTimeStringFromMilliSeconds(totalTime*1000)
    }


    fun getProjectTimeHashMap(tasks : ArrayList<TimekeeperTask>) : Map<String, Long>
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