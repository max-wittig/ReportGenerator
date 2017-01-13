package com.maxwittig.reportgenerator.builder

import com.googlecode.jatl.Html
import com.maxwittig.reportgenerator.models.TimekeeperTask
import com.maxwittig.reportgenerator.utils.getTimeStringFromSeconds
import java.io.StringWriter
import java.text.SimpleDateFormat
import java.util.*

class HTMLReportBuilder(timekeeperTasks : ArrayList<TimekeeperTask>) : ReportBuilder(timekeeperTasks)
{
    val stringWriter = StringWriter()
    val html = Html(stringWriter)
    val format = SimpleDateFormat("HH:mm:ss")

    override fun getReport(): String
    {
        stringWriter.buffer.setLength(0)
        addHead()
        addTodayTaskTable()
        html.br()
        html.p().text("Time worked today: " + getTotalTimeOfTasksToday()).end()
        html.br()
        html.p().text("Projects worked on today: " + getProjectsWorkedOnToday()).end()
        html.endAll()

        return stringWriter.buffer.toString()
    }

    private fun addHead()
    {
        val head = html.head()
        head.style().type("text/css").text("td{ text-align: center;} thead{ background: lightgray } tbody{ background: #f2f2f2} table { width: 100%;}").end()
        head.end()
    }

    private fun addTodayTaskTable()
    {
        val body = html.body()
        val table = body.table()
        val tableHeadArray : Array<String> = arrayOf("Start", "End", "Duration", "ProjectName", "TaskName")
        val tHead = table.thead()
        for(element in tableHeadArray)
        {
            tHead.td().text(element).end()
        }
        tHead.end()
        val tBody = table.tbody()

        for(task in todayTasks)
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
        body.end()
    }

}