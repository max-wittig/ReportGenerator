package com.maxwittig.reportgenerator.main

import com.maxwittig.reportgenerator.handler.MailHandler
import com.maxwittig.reportgenerator.ConfigParser
import com.maxwittig.reportgenerator.ReportBuilder
import com.maxwittig.reportgenerator.parser.TimekeeperParser
import java.io.File

fun main(args: Array<String>)
{
    if(args.size < 2)
    {
        println("Missing arguments")
        return
    }
    val configFile = File(args[0])
    val timekeeperFile = File(args[1])

    val parser = ConfigParser(configFile)
    val mailSettings = parser.getMailSettings()
    val mailSender = MailHandler(mailSettings)
    val timekeeperParser = TimekeeperParser(timekeeperFile)
    val reportBuilder = ReportBuilder(timekeeperParser.getTasks())
    mailSender.sendMail(mailSettings.toAddress, reportBuilder.getReport())
}