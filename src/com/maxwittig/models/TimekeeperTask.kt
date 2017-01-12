package com.maxwittig.models

import java.util.*

class TimekeeperTask(taskName : String, projectName : String, startTime : Date, endTime : Date, duration : Int)
{
    val taskName = taskName
        get
    val projectName = projectName
        get
    val startTime = startTime
        get
    val endTime = endTime
        get
    val duration = duration
        get
}
