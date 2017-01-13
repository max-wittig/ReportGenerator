package com.maxwittig.reportgenerator.builder

import com.maxwittig.reportgenerator.models.TimekeeperTask
import com.maxwittig.reportgenerator.utils.isSameDay
import com.maxwittig.reportgenerator.utils.getTimeStringFromSeconds
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*


abstract class ReportBuilder(private val timekeeperTasks : ArrayList<TimekeeperTask>)
{
    protected val todaysDate = Date()
    protected val todayTasks = ArrayList<TimekeeperTask>()

    init
    {
        parseTodayTasks()
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
        var totalTime : Long = 0
        for(task in todayTasks)
        {
            totalTime += task.duration
        }

        return getTimeStringFromSeconds(totalTime)
    }

    protected fun getProjectsWorkedOnToday() : String
    {
        val projectsList = ArrayList<String>()
        for(task in todayTasks)
        {
            if(!projectsList.contains(task.projectName))
            {
                projectsList.add(task.projectName)
            }
        }

        return stringListToString(projectsList)
    }

    protected fun stringListToString(stringList : ArrayList<String>) : String
    {
        val sb = StringBuilder()
        for(s in stringList)
        {
            sb.append(s)
            if(stringList[stringList.lastIndex] != s)
                sb.append(", ")
        }
        return sb.toString()
    }

    abstract fun getReport() : String
}