package com.maxwittig.configbuilder

import com.google.gson.Gson
import com.google.gson.JsonObject
import java.io.File

class ConfigWriter(val file : File)
{
    fun write(fromAddress : String, toAddress : String, password : String, smtpHost : String, port : Int,
              dailyReport : Boolean, weeklyReport : Boolean, monthlyReport : Boolean, yearlyReport : Boolean)
    {
        val root = JsonObject()
        root.addProperty("fromAddress", fromAddress)
        root.addProperty("toAddress", toAddress)
        root.addProperty("password", password)
        root.addProperty("smtpHost", smtpHost)
        root.addProperty("port", port)
        root.addProperty("dailyReport", dailyReport)
        root.addProperty("weeklyReport", weeklyReport)
        root.addProperty("monthlyReport", monthlyReport)
        root.addProperty("yearlyReport", yearlyReport)
        val gsonString = Gson().toJson(root)
        file.writeText(gsonString)
    }
}