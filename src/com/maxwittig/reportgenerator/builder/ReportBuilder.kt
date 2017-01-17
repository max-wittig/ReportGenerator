package com.maxwittig.reportgenerator.builder

import com.maxwittig.reportgenerator.builder.ReportType
import com.maxwittig.reportgenerator.models.ProjectHolder
import com.maxwittig.reportgenerator.models.TaskHolder
import com.maxwittig.reportgenerator.models.TimekeeperTask
import com.maxwittig.reportgenerator.utils.*
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.util.*


abstract class ReportBuilder(private val timekeeperTasks : ArrayList<TimekeeperTask>, private val reportType: ReportType, protected val todaysDate : Date = Date())
{
    protected var dailyTasks = ArrayList<TimekeeperTask>()
    protected var monthlyTasks = ArrayList<TimekeeperTask>()
    protected var weeklyTasks = ArrayList<TimekeeperTask>()
    protected var yearlyTasks = ArrayList<TimekeeperTask>()

    init
    {
        //dailyTasks are always parsed
        dailyTasks = getParsedDailyTasks()
        if(reportType == ReportType.YEARLY)
        {
            yearlyTasks = getParsedYearlyTasks()
        }
        else
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


    private fun getParsedYearlyTasks() : ArrayList<TimekeeperTask>
    {
        val tasks = ArrayList<TimekeeperTask>()
        for(task in timekeeperTasks)
        {
            if(isSameYear(todaysDate, task.startTime))
            {
                tasks.add(task)
            }
        }
        return getLongestTasksInYear(tasks)
    }

    private fun getLongestTasksInYear(tasks: ArrayList<TimekeeperTask>) : ArrayList<TimekeeperTask>
    {
        //grouped tasks
        val taskHolder = getGroupedTaskHolder(tasks)
        var index = 0
        for(currentTask in taskHolder.tasks)
        {
            currentTask.shownInTaskList = true
            index++
            if(index >= 10)
                break
        }
        return tasks
    }

    private fun getGroupedTaskHolder(tasks: ArrayList<TimekeeperTask>) : TaskHolder
    {
        val taskHolder = TaskHolder()
        for(currentTask in tasks)
        {
            taskHolder.addTask(currentTask)
        }
        return taskHolder
    }

    /**
     * helper method, called by getParsedWeeklyTasks()
     * returns longest task per day of the week
     */
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

    /**
     * helper method for getLongestTaskPerDayInTheWeek()
     * @returns ArrayList<TimeKeeperTask> all tasks which are from a specific weekday
     */
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

    /**
     * parses monthly tasks
     * @returns ArrayList<TimekeeperTask> Top 10 tasks of the month
     * tasks are not removed --> whole month ArrayList is returned back,
     * but shownInTaskList is set to true for the task which are in the top 10 duration in a month
     */
    private fun getParsedMonthlyTasks() : ArrayList<TimekeeperTask>
    {
        val tasks = ArrayList<TimekeeperTask>()
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

    /**
     * task.shownInTaskList = true, because daily tasks are not filtered and every task from the current day
     * is shown
     */
    private fun getParsedDailyTasks() : ArrayList<TimekeeperTask>
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

    fun getTotalTimeOfTasks(tasks: ArrayList<TimekeeperTask>) : String
    {
        var totalTime : Long = 0
        for(task in tasks)
        {
            totalTime += task.duration
        }
        return getTimeStringFromMilliSeconds(totalTime*1000)
    }


    fun createProjectHolder(tasks : ArrayList<TimekeeperTask>) : ProjectHolder
    {
        val projectHolder = ProjectHolder()
        for(task in tasks)
        {
            projectHolder.addTime(task)
        }

        return projectHolder
    }

    abstract fun getReport() : String

    fun shouldSendMail() : Boolean
    {
        if(dailyTasks.isEmpty() && reportType == ReportType.DAILY)
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

        if(yearlyTasks.isEmpty() && reportType == ReportType.YEARLY)
        {
            return false
        }
        return true
    }
}