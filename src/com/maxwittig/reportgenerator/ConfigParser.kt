package com.maxwittig.reportgenerator
import com.google.gson.JsonParser
import com.maxwittig.reportgenerator.models.MailSettings
import java.io.File

class ConfigParser(file : File)
{
    private val file : File = file

    init
    {
        if(!file.exists())
        {
            print("File doesn't exist!")
            System.exit(1)
        }
    }

    fun getMailSettings() : MailSettings
    {
        val jsonObject = JsonParser().parse(file.readText()).asJsonObject
        val host = jsonObject.get("smtpHost").asString
        val port = jsonObject.get("port").asInt
        val fromAddress = jsonObject.get("fromAddress").asString
        val toAddress = jsonObject.get("toAddress").asString
        val password = jsonObject.get("password").asString
        return MailSettings(host, port, fromAddress, password, fromAddress, toAddress)
    }


}