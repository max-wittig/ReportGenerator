package com.maxwittig.reportgenerator.builder
import com.maxwittig.reportgenerator.models.TimekeeperTask
import com.maxwittig.reportgenerator.utils.getTimeStringFromMilliSeconds
import java.util.*

class PlainTextReportBuilder(timekeeperTasks : ArrayList<TimekeeperTask>,val reportType: ReportType, todaysDate : Date = Date()) : ReportBuilder(timekeeperTasks, reportType, todaysDate = todaysDate)
{
    override fun getReport() : String
    {
        val stringBuilder = StringBuilder()
        val taskListToUse = getTaskList()
        stringBuilder.append("Time worked today: " + getTotalTimeOfTasks(dailyTasks))
        stringBuilder.append("\n")
        stringBuilder.append("Projects worked on " + reportType.reportTypeName + "\n:")
        stringBuilder.append(getProjectsListPlain(taskListToUse))
        return stringBuilder.toString()
    }

    private fun getTaskList() : ArrayList<TimekeeperTask>
    {
        when(reportType)
        {
            ReportType.DAILY -> return dailyTasks
            ReportType.MONTHLY -> return monthlyTasks
            ReportType.YEARLY -> return yearlyTasks
            ReportType.WEEKLY -> return weeklyTasks
            ReportType.NONE -> return ArrayList()
        }
    }

    private fun getProjectsListPlain(taskList: ArrayList<TimekeeperTask>) : String
    {
        val stringBuilder = StringBuilder()
        for(currentProject in createProjectHolder(taskList).projects)
        {
            stringBuilder.append(currentProject.name)
            stringBuilder.append("\t")
            stringBuilder.append(getTimeStringFromMilliSeconds(currentProject.totalTime*1000))
            stringBuilder.append("\n")
        }
        return stringBuilder.toString()
    }

}