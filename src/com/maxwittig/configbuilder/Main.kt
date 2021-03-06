package com.maxwittig.configbuilder

import java.io.File

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        throw IllegalArgumentException("Missing argument: file")
    }
    val file = File(args[0])
    val configWriter = ConfigWriter(file)
    print("Input your address to send from: ")
    val fromAddress = readLine()

    print("Input the address to send to: ")
    val toAddress = readLine()

    print("Input your Email Password: ")
    val password = readLine()

    print("Input your smtpHost to send from: ")
    val smtpHost = readLine()

    print("Input the port of the smtpHost to send from: ")
    val portString = readLine()

    print("Would you like to receive daily reports?: (y/n)")
    val dailyReportString = readLine()

    print("Would you like to receive weekly reports?: (y/n)")
    val weeklyReportString = readLine()

    print("Would you like to receive monthly reports?: (y/n)")
    val monthlyReportString = readLine()

    print("Would you like to receive yearly reports?: (y/n)")
    val yearlyReportString = readLine()

    if (portString != null && smtpHost != null && password != null && toAddress != null && fromAddress != null
            && dailyReportString != null && weeklyReportString != null && monthlyReportString != null
            && yearlyReportString != null) {
        val dailyReport = dailyReportString.toLowerCase() == "y"
        val weeklyReport = weeklyReportString.toLowerCase() == "y"
        val monthlyReport = monthlyReportString.toLowerCase() == "y"
        val yearlyReport = yearlyReportString.toLowerCase() == "y"

        val port = portString.toInt()
        configWriter.write(fromAddress, toAddress, password, smtpHost, port, dailyReport, weeklyReport, monthlyReport, yearlyReport)
        println(file.name + " created. This is now your config for the ReportGenerator")
    }
}