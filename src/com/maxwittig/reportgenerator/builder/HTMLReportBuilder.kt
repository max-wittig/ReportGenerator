package com.maxwittig.reportgenerator.builder

import com.googlecode.jatl.Html
import com.maxwittig.reportgenerator.ReportType
import com.maxwittig.reportgenerator.models.TimekeeperTask
import com.maxwittig.reportgenerator.utils.getTimeStringFromSeconds
import java.io.File
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
            body.h3().text("Your 10 longest tasks this month").end()
            addMonthlyHTML(body)
        }
        else
        if(reportType == ReportType.WEEKLY && weeklyTasks.isNotEmpty())
        {
            body.h1().text("Your " + reportType.toString().toLowerCase() + " report").end()
            addWeeklyHTML(body)
        }

        body.br().end()
        body.hr().end()

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
        head.style().type("text/css").text(File("src/com/maxwittig/reportgenerator/builder/css/reportStyle.css").readText()).end()
        head.end()
    }

    private fun addMonthlyHTML(body : Html)
    {
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
        addTaskTable(todayTasks, body, format)
        body.br().end()
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
            tr.td().text(getTimeStringFromSeconds(hashMap.get(key)!!)).end()
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
            row.td().text(getTimeStringFromSeconds(task.duration)).end()
            row.td().text(task.projectName).end()
            row.td().text(task.taskName).end()
            row.end()
        }
        tBody.end()
        table.end()
    }

}