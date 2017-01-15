package com.maxwittig.reportgenerator.builder
import com.maxwittig.reportgenerator.ReportType
import com.maxwittig.reportgenerator.models.TimekeeperTask
import java.util.*

class PlainTextReportBuilder(timekeeperTasks : ArrayList<TimekeeperTask>, reportType: ReportType) : ReportBuilder(timekeeperTasks, reportType)
{
    override fun getReport() : String
    {
        val stringBuilder = StringBuilder()
        stringBuilder.append("Time worked today: " + getTotalTimeOfTasksToday())
        stringBuilder.append("\n")
        stringBuilder.append("Projects worked on today: " + getDailyProjectTimeHashMap())
        return stringBuilder.toString()
    }

}