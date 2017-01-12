package com.maxwittig

import com.maxwittig.models.TimekeeperTask
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

class ReportBuilder(timekeeperTasks : ArrayList<TimekeeperTask>)
{
    private val timekeeperTasks = timekeeperTasks
    private val todaysDate = Date()
    private var todayTasks = ArrayList<TimekeeperTask>()

    fun getReport() : String
    {
        parseTodayTasks()

        val stringBuilder = StringBuilder()

        stringBuilder.append("Time worked today: " + getTotalTimeOfTasksToday())
        stringBuilder.append("\n")
        stringBuilder.append("Projects worked on today: " + getProjectsWorkedOnToday())
        return stringBuilder.toString()
    }

    private fun parseTodayTasks()
    {
        for(task in timekeeperTasks)
        {
            if(isSameDay(todaysDate, task.startTime))
                todayTasks.add(task)
        }
    }

    private fun getTotalTimeOfTasksToday() : String
    {
        var totalTime : Long = 0
        for(task in todayTasks)
        {
            totalTime += task.duration
        }


        return LocalTime.ofSecondOfDay(totalTime).format(DateTimeFormatter.ISO_TIME).toString()
    }

    private fun getProjectsWorkedOnToday() : String
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

    private fun stringListToString(stringList : ArrayList<String>) : String
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


}