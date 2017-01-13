package com.maxwittig.reportgenerator.main

import com.maxwittig.reportgenerator.builder.HTMLReportBuilder
import com.maxwittig.reportgenerator.builder.PlainTextReportBuilder
import com.maxwittig.reportgenerator.builder.ReportType
import com.maxwittig.reportgenerator.handler.MailHandler
import com.maxwittig.reportgenerator.parser.ConfigParser
import com.maxwittig.reportgenerator.parser.TimekeeperParser
import java.io.File

fun main(args: Array<String>)
{
    if(args.size < 2)
    {
        println("Missing arguments")
        println("Required arguments: <configFileLocation> <timekeeper.json file location> <reportType>")
        return
    }
    val configFile = File(args[0])
    val timekeeperFile = File(args[1])
    var reportTypeArgumentString : String = "PLAIN"
    if(args.size == 3)
    {
        reportTypeArgumentString = args[2]
    }

    val reportType : ReportType = ReportType.valueOf(reportTypeArgumentString.toUpperCase())

    if(configFile.exists() && timekeeperFile.exists())
    {
        val parser = ConfigParser(configFile)
        val mailSettings = parser.getMailSettings()
        val mailSender = MailHandler(mailSettings)
        val timekeeperParser = TimekeeperParser(timekeeperFile)
        if(reportType == ReportType.PLAIN)
        {
            val reportBuilder = PlainTextReportBuilder(timekeeperParser.getTasks())
            mailSender.sendPlainMail(mailSettings.toAddress, reportBuilder.getReport())
        }
        else if(reportType == ReportType.HTML)
        {
            val reportBuilder = HTMLReportBuilder(timekeeperParser.getTasks())
            mailSender.sendHTMLMail(mailSettings.toAddress, reportBuilder.getReport())
        }

        println("Mail send successfully!")
    }
}