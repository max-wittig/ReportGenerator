package com.maxwittig.reportgenerator.parser
import com.google.gson.JsonParser
import com.maxwittig.reportgenerator.models.Settings
import java.io.File

class ConfigParser(private val file : File)
{
    init
    {
        if(!file.exists())
        {
            print("File doesn't exist!")
            System.exit(1)
        }
    }

    fun getSettings() : Settings
    {
        val jsonObject = JsonParser().parse(file.readText()).asJsonObject
        val host = jsonObject.get("smtpHost").asString
        val port = jsonObject.get("port").asInt
        val fromAddress = jsonObject.get("fromAddress").asString
        val toAddress = jsonObject.get("toAddress").asString
        val password = jsonObject.get("password").asString
        val weeklyReport = jsonObject.get("weeklyReport").asBoolean
        val monthlyReport = jsonObject.get("monthlyReport").asBoolean
        val yearlyReport = jsonObject.get("yearlyReport").asBoolean
        val dailyReport = jsonObject.get("dailyReport").asBoolean
        return Settings(host, port, fromAddress, password, fromAddress, toAddress, dailyReport, weeklyReport, monthlyReport, yearlyReport)
    }


}