package tests.builder

import com.maxwittig.reportgenerator.builder.HTMLReportBuilder
import com.maxwittig.reportgenerator.builder.ReportType
import org.junit.Before
import org.junit.Test
import java.io.File

class HTMLReportBuilderTest : ReportBuilderTest() {
    private val testResultFolder = "data/testResults/"

    @Before
    fun before() {
        File(testResultFolder).mkdirs()
    }

    @Test
    fun weeklyReportTest() {
        val taskList = getRandomTimekeeperTaskArrayList()
        val now = getRandomDateInTheYear()
        val reportBuilder = HTMLReportBuilder(taskList, ReportType.WEEKLY, todaysDate = now)
        val weeklyReport = File("data/testResults/weekly.html")
        weeklyReport.writeText(reportBuilder.getReport())
    }

    @Test
    fun monthlyReportTest() {
        val taskList = getRandomTimekeeperTaskArrayList()
        val now = getRandomDateInTheYear()
        val reportBuilder = HTMLReportBuilder(taskList, ReportType.MONTHLY, todaysDate = now)
        val monthlyReport = File("data/testResults/monthly.html")
        monthlyReport.writeText(reportBuilder.getReport())
    }

    @Test
    fun dailyReportTest() {
        val taskList = getRandomTimekeeperTaskArrayList()
        val now = getRandomDateInTheYear()
        val reportBuilder = HTMLReportBuilder(taskList, ReportType.DAILY, todaysDate = now)
        val dailyReport = File("data/testResults/daily.html")
        dailyReport.writeText(reportBuilder.getReport())
    }

    @Test
    fun yearlyReportTest() {
        val taskList = getRandomTimekeeperTaskArrayList()
        val now = getRandomDateInTheYear()
        val reportBuilder = HTMLReportBuilder(taskList, ReportType.YEARLY, todaysDate = now)
        val yearlyReport = File("data/testResults/yearly.html")
        yearlyReport.writeText(reportBuilder.getReport())
    }


}