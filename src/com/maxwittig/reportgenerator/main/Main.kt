package com.maxwittig.reportgenerator.main

import com.maxwittig.reportgenerator.builder.HTMLReportBuilder
import com.maxwittig.reportgenerator.builder.MailType
import com.maxwittig.reportgenerator.builder.PlainTextReportBuilder
import com.maxwittig.reportgenerator.builder.ReportType
import com.maxwittig.reportgenerator.handler.MailHandler
import com.maxwittig.reportgenerator.parser.ConfigParser
import com.maxwittig.reportgenerator.parser.TimekeeperParser
import java.io.File

fun main(args: Array<String>)
{
    if (args.size < 2)
    {
        throw IllegalArgumentException("Required arguments: <configFileLocation> <timekeeper.json_file_location> <mailType>[html||plan]")
    }
    val configFile = File(args[0])
    val timekeeperFile = File(args[1])
    var reportTypeArgumentString: String = "HTML"
    if (args.size == 3)
    {
        reportTypeArgumentString = args[2]
    }

    val mailType: MailType = MailType.valueOf(reportTypeArgumentString.toUpperCase())

    if (configFile.exists() && timekeeperFile.exists())
    {
        val parser = ConfigParser(configFile)
        val settings = parser.getSettings()
        val mailSender = MailHandler(settings)
        val timekeeperParser = TimekeeperParser(timekeeperFile)
        val reportType = ReportType.MONTHLY //ReportType.getCurrentReportType(settings.weeklyReportEnabled, settings.monthlyReportEnabled)
        if (mailType == MailType.PLAIN)
        {
            val reportBuilder = PlainTextReportBuilder(timekeeperParser.getTasks(), reportType)
            //check if mail should be send: e.g. do not send, if today is empty, but send regardless of today, if monthly report
            if (reportBuilder.shouldSendMail())
            {
                mailSender.sendPlainMail(settings.toAddress, reportBuilder.getReport())
            }
        }
        else if (mailType == MailType.HTML)
        {
            val reportBuilder = HTMLReportBuilder(timekeeperParser.getTasks(), reportType)
            if (reportBuilder.shouldSendMail())
            {
                mailSender.sendHTMLMail(settings.toAddress, reportBuilder.getReport())
            }
        }

        println("Mail send successfully!")
    }
}