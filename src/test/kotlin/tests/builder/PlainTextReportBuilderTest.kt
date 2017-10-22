package tests.builder

import com.maxwittig.reportgenerator.builder.PlainTextReportBuilder
import com.maxwittig.reportgenerator.builder.ReportType
import org.junit.Test


class PlainTextReportBuilderTest : ReportBuilderTest() {
    @Test
    fun dailyReportTest() {
        val tasks = getRandomTimekeeperTaskArrayList()
        val reportBuilder = PlainTextReportBuilder(tasks, ReportType.DAILY, todaysDate = getRandomDateInTheYear())
        println(reportBuilder.getReport())

    }
}