package com.maxwittig.reportgenerator.parser

import com.google.gson.JsonParser
import com.maxwittig.reportgenerator.models.TimekeeperTask
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class TimekeeperParser(private val file : File)
{
    private val timekeeperDateFormat = SimpleDateFormat("dd.MM.yyyy - HH:mm:ss")

    init
    {
        if(!file.exists())
        {
            println(file.name + " doesn't exist!")
            System.exit(1)
        }
    }

    fun getTasks() : ArrayList<TimekeeperTask>
    {
        val returnList = ArrayList<TimekeeperTask>()
        val jsonObject = JsonParser().parse(file.readText()).asJsonObject
        val taskArray = jsonObject.getAsJsonArray("saveObjectArray")
        for(taskJsonElement in taskArray)
        {
            val taskObject = taskJsonElement.asJsonObject
            val projectName = taskObject.get("projectName").asString
            val taskName = taskObject.get("taskName").asString
            val startTime : Date = timekeeperDateFormat.parse(taskObject.get("startTime").asString)
            val endTime : Date = timekeeperDateFormat.parse(taskObject.get("endTime").asString)
            val duration = taskObject.get("durationInSec").asLong
            val task = TimekeeperTask(taskName, projectName, startTime, endTime, duration)
            returnList.add(task)
        }
        return returnList
    }
}