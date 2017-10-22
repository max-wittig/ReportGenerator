package com.maxwittig.reportgenerator.builder

import com.maxwittig.reportgenerator.models.ProjectHolder
import com.maxwittig.reportgenerator.models.TimekeeperTask
import com.maxwittig.reportgenerator.utils.*
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.util.*


abstract class ReportBuilder(private val timekeeperTasks: MutableList<TimekeeperTask>, private val reportType: ReportType,
                             protected val todaysDate: Date = Date()) {
    protected var dailyTasks : MutableList<TimekeeperTask> = ArrayList()
    protected var monthlyTasks : MutableList<TimekeeperTask> = ArrayList()
    protected var weeklyTasks : MutableList<TimekeeperTask> = ArrayList()
    protected var yearlyTasks : MutableList<TimekeeperTask> = ArrayList()

    init {
        //dailyTasks are always parsed
        dailyTasks = getParsedDailyTasks()
        when(reportType) {
            ReportType.YEARLY -> yearlyTasks = getParsedYearlyTasks()
            ReportType.MONTHLY -> monthlyTasks = getParsedMonthlyTasks()
            ReportType.WEEKLY -> weeklyTasks = getParsedWeeklyTasks()
            else -> println("Only dailed tasks are parsed")
        }
    }

    private fun getParsedWeeklyTasks(): MutableList<TimekeeperTask> {
        val tasks : MutableList<TimekeeperTask> = ArrayList()
        for (task in timekeeperTasks) {
            if (isSameWeek(todaysDate, task.startTime)) {
                tasks.add(task.clone())
            }
        }

        return getLongestTaskPerDayInTheWeek(tasks)
    }


    private fun getParsedYearlyTasks(): MutableList<TimekeeperTask> {
        val tasks : MutableList<TimekeeperTask> = ArrayList()
        for (task in timekeeperTasks) {
            if (isSameYear(todaysDate, task.startTime)) {
                tasks.add(task.clone())
            }
        }
        return getLongestTasksInYear(tasks)
    }

    private fun getLongestTasksInYear(tasks: MutableList<TimekeeperTask>): MutableList<TimekeeperTask> {
        tasks.sortedBy { it.duration }
        val taskLimit = 10
        if (tasks.size > taskLimit) {
            for (currentLongestTask in tasks) {
                if (tasks.indexOf(currentLongestTask) >= taskLimit)
                    break
                currentLongestTask.shownInTaskList = true

            }
        }
        return tasks
    }

    /**
     * helper method, called by getParsedWeeklyTasks()
     * returns longest task per day of the week
     */
    private fun getLongestTaskPerDayInTheWeek(tasks: MutableList<TimekeeperTask>): MutableList<TimekeeperTask> {
        for (currentDay in DayOfWeek.values()) {
            var longestTask: TimekeeperTask? = null
            val dayTaskList = getTasksPerDay(tasks, currentDay)
            val longestDuration = -1
            for (currentTask in dayTaskList) {
                if (currentTask.duration > longestDuration) {
                    longestTask = currentTask
                }
            }

            if (longestTask != null) {
                longestTask.shownInTaskList = true
            }
        }
        return tasks
    }

    /**
     * helper method for getLongestTaskPerDayInTheWeek()
     * @returns ArrayList<TimeKeeperTask> all tasks which are from a specific weekday
     */
    private fun getTasksPerDay(tasks: MutableList<TimekeeperTask>, dayOfWeek: DayOfWeek): MutableList<TimekeeperTask> {
        val taskList : MutableList<TimekeeperTask> = ArrayList()
        for (currentTask in tasks) {
            //if same day
            if (SimpleDateFormat("EEEE").format(currentTask.startTime).toUpperCase() == dayOfWeek.toString().toUpperCase()) {
                taskList.add(currentTask)
            }
        }

        taskList.sortedBy { it.startTime }
        //taskList.sortedBy({ task1, task2 -> task1.startTime.compareTo(task2.startTime)})
        return taskList
    }

    /**
     * parses monthly tasks
     * @returns ArrayList<TimekeeperTask> Top 10 tasks of the month
     * tasks are not removed --> whole month ArrayList is returned back,
     * but shownInTaskList is set to true for the task which are in the top 10 duration in a month
     */
    private fun getParsedMonthlyTasks(): MutableList<TimekeeperTask> {
        val tasks = ArrayList<TimekeeperTask>()
        for (task in timekeeperTasks) {
            if (isSameMonth(todaysDate, task.startTime)) {
                tasks.add(task.clone())
            }
        }

        //sort after duration and only show longest 10
        tasks.sortedBy { it.duration }

        val taskLimit = 10
        if (tasks.size > taskLimit) {
            for (currentLongestTask in tasks) {
                if (tasks.indexOf(currentLongestTask) >= taskLimit)
                    break
                currentLongestTask.shownInTaskList = true

            }
        }

        tasks.sortedBy { it.startTime }
        return tasks
    }

    /**
     * task.shownInTaskList = true, because daily tasks are not filtered and every task from the current day
     * is shown
     */
    private fun getParsedDailyTasks(): MutableList<TimekeeperTask> {
        val tasks : MutableList<TimekeeperTask> = ArrayList()
        for (task in timekeeperTasks) {
            if (isSameDay(todaysDate, task.startTime)) {
                val currentTask = task.clone()
                currentTask.shownInTaskList = true
                tasks.add(currentTask)
            }
        }
        return tasks
    }

    fun getTotalTimeOfTasks(tasks: MutableList<TimekeeperTask>): String {
        var totalTime: Long = 0
        for (task in tasks) {
            totalTime += task.duration
        }
        return getTimeStringFromMilliSeconds(totalTime * 1000)
    }


    fun createProjectHolder(tasks: MutableList<TimekeeperTask>): ProjectHolder {
        val projectHolder = ProjectHolder()
        for (task in tasks) {
            projectHolder.addTime(task)
        }

        return projectHolder
    }

    abstract fun getReport(): String

    fun shouldSendMail(): Boolean {
        if (dailyTasks.isEmpty() && reportType == ReportType.DAILY) {
            return false
        }

        if (weeklyTasks.isEmpty() && reportType == ReportType.WEEKLY) {
            return false
        }

        if (monthlyTasks.isEmpty() && reportType == ReportType.MONTHLY) {
            return false
        }

        if (yearlyTasks.isEmpty() && reportType == ReportType.YEARLY) {
            return false
        }
        return true
    }
}