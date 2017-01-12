package com.maxwittig.main

import com.maxwittig.handler.MailHandler
import com.maxwittig.ConfigParser
import com.maxwittig.ReportBuilder
import com.maxwittig.parser.TimekeeperParser
import java.io.File

fun main(args: Array<String>)
{
    val configFile = File(args[0])
    val timekeeperFile = File(args[1])

    val parser = ConfigParser(configFile)
    val mailSettings = parser.getMailSettings()
    val mailSender = MailHandler(mailSettings)
    val timekeeperParser = TimekeeperParser(timekeeperFile)
    val reportBuilder = ReportBuilder(timekeeperParser.getTasks())
    mailSender.sendMail(mailSettings.toAddress, reportBuilder.getReport())
}