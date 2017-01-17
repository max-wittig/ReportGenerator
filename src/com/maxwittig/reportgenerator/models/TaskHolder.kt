package com.maxwittig.reportgenerator.models

import java.util.*

class TaskHolder
{
    val tasks = ArrayList<TimekeeperTask>()
    get

    fun addTask(task : TimekeeperTask)
    {
        val existingCurrentTaskName = task.taskName
        var existingCurrentTask = getTask(existingCurrentTaskName)
        if(existingCurrentTask == null)
        {
            tasks.add(task)
        }
        else
        {
            existingCurrentTask.duration += task.duration
        }

        tasks.sort { task1, task2 -> task1.duration.compareTo(task2.duration) }
    }

    private fun getTask(taskName : String) : TimekeeperTask?
    {
        for(currentTask in tasks)
        {
            if(currentTask.taskName == taskName)
            {
                return currentTask
            }
        }

        return null
    }
}
