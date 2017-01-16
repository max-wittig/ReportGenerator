package com.maxwittig.reportgenerator.builder

import com.googlecode.jatl.Html
import com.maxwittig.reportgenerator.ReportType
import com.maxwittig.reportgenerator.models.TimekeeperTask
import com.maxwittig.reportgenerator.utils.FileUtils
import com.maxwittig.reportgenerator.utils.getTimeStringFromMilliSeconds
import java.io.StringWriter
import java.text.SimpleDateFormat
import java.util.*

class HTMLReportBuilder(timekeeperTasks : ArrayList<TimekeeperTask>,val reportType: ReportType, todaysDate : Date = Date()) : ReportBuilder(timekeeperTasks, reportType, todaysDate = todaysDate)
{
    val stringWriter = StringWriter()
    val html = Html(stringWriter)

    override fun getReport(): String
    {
        stringWriter.buffer.setLength(0)
        addHead()
        val body = html.body()

        if(reportType == ReportType.MONTHLY && monthlyTasks.isNotEmpty())
        {
            body.h1().text("Your " + reportType.toString().toLowerCase() + " report").end()
            addMonthlyHTML(body)
        }
        else
        if(reportType == ReportType.WEEKLY && weeklyTasks.isNotEmpty())
        {
            body.h1().text("Your " + reportType.toString().toLowerCase() + " report").end()
            addWeeklyHTML(body)
        }

        if(reportType != ReportType.DAILY)
        {
            body.br().end()
            body.hr().end()
        }

        if(todayTasks.isNotEmpty())
        {
            body.h1().text("Your daily report").end()
            addTodayHTML(body)
        }

        body.end()
        html.endAll()

        return stringWriter.buffer.toString()
    }

    private fun addHead()
    {
        val head = html.head()
        val css = FileUtils.getFileContentFromJar("/com/maxwittig/reportgenerator/builder/css/reportStyle.css")
        head.style().type("text/css").text(css).end()
        head.end()
    }

    private fun addMonthlyHTML(body : Html)
    {
        body.h3().text("Your 10 longest tasks this month").end()
        val format = SimpleDateFormat("dd.MM.yyyy - HH:mm:ss")
        addTaskTable(monthlyTasks, body, format)
        body.br()
        addProjectTable(body, getMonthlyProjectTimeHashMap())
    }

    private fun addWeeklyHTML(body : Html)
    {

    }

    private fun addTodayHTML(body : Html)
    {
        val format = SimpleDateFormat("dd.MM.yyyy - HH:mm:ss")
        body.h3().text("Daily Tasks")
        addTaskTable(todayTasks, body, format)
        body.br().end()
        body.h3().text("Daily Projects")
        addProjectTable(body, getDailyProjectTimeHashMap())
    }

    private fun addProjectTable(htmlElement: Html, hashMap: HashMap<String,Long>)
    {
        val table = htmlElement.table()
        addTableHead(table, arrayOf("ProjectName", "TotalTime"))
        val tBody = table.tbody()
        for(key in hashMap.keys)
        {
            val tr = tBody.tr()
            //add projectname
            tr.td().text(key).end()
            //add duration
            tr.td().text(getTimeStringFromMilliSeconds(hashMap.get(key)!!*1000)).end()
            tr.end()
        }

        val todayTimeRow = tBody.tr()
        todayTimeRow.td().text("Total Time").end()

        if(reportType == ReportType.MONTHLY)
        {
            todayTimeRow.td().text(getTotalTimeOfTasksMonthly()).end()
        }
        else
        if(reportType == ReportType.WEEKLY)
        {
            todayTimeRow.td().text(getTotalTimeOfTasksWeekly()).end()
        }
        else
        if(reportType == ReportType.DAILY)
        {
            todayTimeRow.td().text(getTotalTimeOfTasksToday()).end()
        }

        todayTimeRow.end()
        tBody.end()
        table.end()
    }

    private fun addTableHead(table: Html, tableHeadArray : Array<String>)
    {
        val tHead = table.thead()
        for(element in tableHeadArray)
        {
            tHead.td().text(element).end()
        }
        tHead.end()
    }

    private fun addTaskTable(tasks : ArrayList<TimekeeperTask>, htmlElement: Html, format : SimpleDateFormat)
    {
        val table = htmlElement.table()
        addTableHead(table, arrayOf("Start", "End", "Duration", "ProjectName", "TaskName"))
        val tBody = table.tbody()

        for(task in tasks)
        {
            val row = tBody.tr()
            row.td().text(format.format(task.startTime)).end()
            row.td().text(format.format(task.endTime)).end()
            row.td().text(getTimeStringFromMilliSeconds(task.duration*1000)).end()
            row.td().text(task.projectName).end()
            row.td().text(task.taskName).end()
            row.end()
        }
        tBody.end()
        table.end()
    }

}