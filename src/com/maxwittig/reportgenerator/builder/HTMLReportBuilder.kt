package com.maxwittig.reportgenerator.builder

import com.googlecode.jatl.Html
import com.maxwittig.reportgenerator.models.ProjectHolder
import com.maxwittig.reportgenerator.models.TimekeeperTask
import com.maxwittig.reportgenerator.utils.FileUtils
import com.maxwittig.reportgenerator.utils.getTimeStringFromMilliSeconds
import java.io.StringWriter
import java.text.SimpleDateFormat
import java.util.*

class HTMLReportBuilder(timekeeperTasks : ArrayList<TimekeeperTask>, val reportType: ReportType, todaysDate : Date = Date()) : ReportBuilder(timekeeperTasks, reportType, todaysDate = todaysDate)
{
    val stringWriter = StringWriter()
    val html = Html(stringWriter)

    override fun getReport(): String
    {
        stringWriter.buffer.setLength(0)
        addHead()
        val body = html.body()

        if(reportType == ReportType.YEARLY && yearlyTasks.isNotEmpty())
        {
            body.h1().text("Your " + reportType.toString().toLowerCase() + " report").end()
            addYearlyHTML(body)
        }
        else
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

        if(dailyTasks.isNotEmpty())
        {
            body.h1().text("Your daily report").end()
            addTodayHTML(body)
        }

        body.end()
        html.endAll()

        return stringWriter.buffer.toString()
    }

    private fun addYearlyHTML(body: Html)
    {
        addHTML(yearlyTasks, body, reportType, taskString = "Your yearly tasks")
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
        addHTML(monthlyTasks, body, reportType, taskString = "Your 10 longest tasks this month")
    }

    private fun addWeeklyHTML(body : Html)
    {
        addHTML(weeklyTasks, body, reportType, format = SimpleDateFormat("EEEE dd.MM.yyyy - HH:mm:ss"))
    }

    private fun addTodayHTML(body : Html)
    {
        addHTML(dailyTasks, body, ReportType.DAILY)
    }

    /**
     * called by every reportType. Adds taskTable and projectTable to Body
     */
    private fun addHTML(tasks: ArrayList<TimekeeperTask>, body: Html, currentReportType: ReportType,
                        taskString : String = currentReportType.reportTypeName + " tasks",
                        format: SimpleDateFormat = SimpleDateFormat("dd.MM.yyyy - HH:mm:ss"))
    {
        body.h3().text(taskString)
        addTaskTable(tasks, body, format)
        body.br().end()
        body.h3().text(currentReportType.reportTypeName + " projects")
        addProjectTable(tasks, body, createProjectHolder(tasks), currentReportType)
    }

    private fun addProjectTable(tasks: ArrayList<TimekeeperTask>, htmlElement: Html, projectHolder : ProjectHolder, currentReportType: ReportType)
    {
        val table = htmlElement.table()
        addTableHead(table, arrayOf("ProjectName", "Time"))
        val tBody = table.tbody()
        for(project in projectHolder.projects)
        {
            val tr = tBody.tr()
            //add projectname
            tr.td().text(project.name).end()
            //add duration
            tr.td().text(getTimeStringFromMilliSeconds(project.totalTime*1000)).end()
            tr.end()
        }

        val todayTimeRow = tBody.tr()
        todayTimeRow.classAttr("totalTime")
        val totalTimeTableData = todayTimeRow.td()
        totalTimeTableData.text("Total Time").end()
        todayTimeRow.td().text(getTotalTimeOfTasks(tasks)).end()
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
            if(task.shownInTaskList)
            {
                val row = tBody.tr()
                row.td().text(format.format(task.startTime)).end()
                row.td().text(format.format(task.endTime)).end()
                row.td().text(getTimeStringFromMilliSeconds(task.duration * 1000)).end()
                row.td().text(task.projectName).end()
                row.td().text(task.taskName).end()
                row.end()
            }
        }
        tBody.end()
        table.end()
    }

}