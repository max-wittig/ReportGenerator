package tests.builder

import com.maxwittig.reportgenerator.models.TimekeeperTask
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


abstract class ReportBuilderTest {
    private val wordList: List<String> = File("data/wordlist.txt").readText().split("\n")

    protected fun getRandomTimekeeperTaskArrayList(): MutableList<TimekeeperTask> {
        val completeTaskList: MutableList<TimekeeperTask> = ArrayList()
        var i = 0
        while (i < 10000) {
            i++
            val startDate = getRandomDateInTheYear()
            val duration = getRandomDuration()
            val endDate = Date(startDate.time + duration)
            val task = TimekeeperTask(getRandomWordFromList(), getRandomWordFromList(), startDate, endDate, duration)
            completeTaskList.add(task)
        }
        completeTaskList.sortedBy { it.startTime }
        return completeTaskList
    }

    private fun getRandomWordFromList(): String {
        return wordList[Random().nextInt(wordList.size)]
    }

    protected fun getRandomDateInTheYear(): Date {
        val year = "2017"
        val format = SimpleDateFormat("yyyy")
        val yearDate = format.parse(year)
        val yearStartSeconds = yearDate.time
        val yearLengthMilliSeconds = 31557600000
        val randomDateInMilliseconds = (Random().nextDouble() * (yearLengthMilliSeconds)).toLong()
        return Date(yearStartSeconds + randomDateInMilliseconds)
    }

    private fun getRandomDuration(): Long {
        return (Random().nextDouble() * (86400)).toLong()
    }
}