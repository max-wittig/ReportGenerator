package com.maxwittig.reportgenerator.models

import java.util.*

class TimekeeperTask(taskName : String, projectName : String, startTime : Date, endTime : Date, duration : Long)
{
    val taskName = taskName
        get
    val projectName = projectName
        get
    val startTime = startTime
        get
    val endTime = endTime
        get
    var duration = duration
        get
        set
    var shownInTaskList = false
        get
        set
}
